package com.me.data.remote.rxjava;

import android.app.Activity;
import android.content.Intent;

import com.alibaba.fastjson.JSON;
import com.me.core.app.AppManager;
import com.me.core.exception.ApiException;
import com.me.data.exception.LoginException;
import com.me.data.local.SharedPrefHelper;
import com.me.data.model.BaseRes;
import com.me.data.model.user.UserBean;
import com.me.data.mvp.MvpView;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static com.me.core.exception.ApiException.EMPTY_CODE;

/**
 * Created by xunice on 14/03/2017.
 */

public class RxUtils {

    /**
     * 业务成功code
     */
    private static final int SUCCESS_STATUS = 90001;
    private static final int SUCCESS_STATUS_NORMAL = 1000;
    private static final int TOKEN_KICKEDOUT = -7;
    private static final int TOKEN_TIMEOUT = -6;

    //子线程运行，主线程回调
    public static <T> FlowableTransformer<T, T> ioMain() {
        return new FlowableTransformer<T, T>() {

            @Override
            public Publisher<T> apply(Flowable<T> flowable) {

                Flowable<T> tFlowable = flowable
//                        .subscribeOn(Schedulers.io())
//                        .doOnSubscribe(new Consumer<Subscription>() {
//                            @Override
//                            public void accept(Subscription subscription) throws Exception {
//                                Logger.i("subscription");
//                            }
//                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
                //.compose(RxLifecycle.<T, ActivityEvent>bindUntilEvent(context.lifecycle(), ActivityEvent.DESTROY)); //暂时去掉生命周期管理
                return tFlowable;
            }
        };
    }


    public static <T> FlowableTransformer<BaseRes<T>, T> retrofit() {
        return new FlowableTransformer<BaseRes<T>, T>() {
            @Override
            public Publisher<T> apply(Flowable<BaseRes<T>> flowable) {
                return flowable
                        .flatMap(new Function<BaseRes<T>, Publisher<T>>() {
                            @Override
                            public Publisher<T> apply(final BaseRes<T> baseRes) throws Exception {
                                if (baseRes == null || (baseRes instanceof List && ((List) baseRes).size() == 0)) {
                                    return Flowable.error(new ApiException("response's model is null or response's list size is 0"));
                                } else {
                                   // AppManager.getAppManager().finishAllActivity();
                                    if (baseRes.isSuccess()) {//如果返回时"0000"表示数据请求正常
                                        if (baseRes.getObj()!=null)
                                            return Flowable.just(baseRes.getObj());
                                        else
                                            return Flowable.error(new ApiException(EMPTY_CODE, baseRes.getMsg()));
//                                            return Flowable.create(new FlowableOnSubscribe<T>() {
//                                                @Override
//                                                public void subscribe(FlowableEmitter<T> e) throws Exception {
//                                                    e.onNext((T) (baseRes.getObj()+""));
//                                                    e.onComplete();
//                                                }
//                                            },BackpressureStrategy.LATEST);
                                    }else if (baseRes.getCode() == SUCCESS_STATUS || baseRes.getCode() == SUCCESS_STATUS_NORMAL) {//如果返回时"0000"表示数据请求正常
                                        if(baseRes.getObj() != null)
                                            return Flowable.just(baseRes.getObj());
                                        else
                                            return Flowable.just(baseRes.getBody());
                                    }else if (baseRes.getCode() == TOKEN_KICKEDOUT){
                                        SharedPrefHelper.getInstance().logout();
                                        Intent kickedOutIntent=new Intent();
                                        Activity activity = AppManager.getAppManager().currentActivity();
                                        kickedOutIntent.setClassName(activity,"com.dongao.kaoqian.phone.views.MainActivity");
                                        kickedOutIntent.putExtra("tag","tag_main");
                                        kickedOutIntent.putExtra("token_kickedOut",true);
                                        activity.startActivity(kickedOutIntent);
                                        return Flowable.error(new ApiException(baseRes.getCode(), baseRes.getMsg()));//return the response's returnCode and msg
                                    }else if (baseRes.getCode() == TOKEN_TIMEOUT){
                                        SharedPrefHelper.getInstance().logout();
                                        Activity activity = AppManager.getAppManager().currentActivity();
                                        Intent timeOutIntent=new Intent();
                                        timeOutIntent.setClassName(activity,"com.dongao.kaoqian.phone.views.MainActivity");
                                        timeOutIntent.putExtra("tag","tag_main");
                                        timeOutIntent.putExtra("token_timeOut",true);
                                        activity.startActivity(timeOutIntent);
//                                        AppManager.getAppManager().returnToActivity("com.dongao.kaoqian.phone.views.MainActivity");
//                                        Toast.makeText(AppContext.getInstance(),"您的账户已过期，请重新登录",Toast.LENGTH_LONG).show();
                                        return Flowable.error(new ApiException(baseRes.getCode(), ""));
                                    } else {
                                        //如果网络未连接不会调用flatMap,所以网络未连接不会出现ApiException错误
                                        return Flowable.error(new ApiException(baseRes.getCode(), baseRes.getMsg()));//return the response's returnCode and msg
                                    }
                                }
                            }
                        }); //先不直接切换线程
                     //   .compose(RxUtils.<T>ioMain());//线程切换
            }
        };
    }

    /**
     * rxjava 超时抛出异常
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<T, T> rxTimeoutTransformer() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return upstream.timeout(3, TimeUnit.SECONDS);
            }
        };
    }

    /**
     * 登录注册状态校验
     * @return
     */
    public static FlowableTransformer<BaseRes<UserBean>, UserBean> rxStateCheck() {
        return new FlowableTransformer<BaseRes<UserBean>, UserBean>() {
            @Override
            public Publisher<UserBean> apply(Flowable<BaseRes<UserBean>> upstream) {
                return upstream
                        .map(new Function<BaseRes<UserBean>, UserBean>() {
                            @Override
                            public UserBean apply(BaseRes<UserBean> baseRes) throws Exception {
                                int serverCode = baseRes.getCode();
                                if (serverCode != SUCCESS_STATUS) {
                                    throw new LoginException(serverCode);
                                }

                                return baseRes.getObj();
                            }
                        });
            }
        };
    }

    public static void dispose(Disposable disposable){
        if (disposable != null && !disposable.isDisposed()){
            disposable.dispose();
        }
    }

    /**
     * 模拟数据请求成功
     */
    public static <T> Flowable<BaseRes<T>> testSuccess(T body) {
        final BaseRes<T> baseRes = new BaseRes<>();
        baseRes.setCode(SUCCESS_STATUS);
        baseRes.setObj(body);
        return Flowable.timer(1000, TimeUnit.MILLISECONDS)
                .map(new Function<Long, BaseRes<T>>() {
                    @Override
                    public BaseRes<T> apply(@NonNull Long aLong) throws Exception {
                        return baseRes;
                    }
                });
    }

    public static <T> Flowable<BaseRes<T>> testSuccess(String json, Class<T> tClass) {
        T body = JSON.parseObject(json, tClass);
        return testSuccess(body);
    }

    public static <T> Flowable<BaseRes<List<T>>> testSuccess( Class<T> c,String json) {
        List<T> body = JSON.parseArray(json,c);
        return testSuccess(body);
    }

    /**
     * 模拟数据请求失败
     */
    public static <T> Flowable<BaseRes<T>> testFail(Class<T> tClass){
        final BaseRes<T> baseRes = new BaseRes<>();
        baseRes.setCode(0000);
        baseRes.setObj(null);
        baseRes.setMsg("错误信息");
        return Flowable.timer(1000, TimeUnit.MILLISECONDS)
                .map(new Function<Long, BaseRes<T>>() {
                    @Override
                    public BaseRes<T> apply(@NonNull Long aLong) throws Exception {
                        return baseRes;
                    }
                });
    }



    public static <T> FlowableTransformer<T,T> showLoading(final MvpView mvpView){
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return upstream.doOnSubscribe(new Consumer<Subscription>() {
                    @Override
                    public void accept(@NonNull Subscription subscription) throws Exception {
                        mvpView.showLoading();
                    }
                });
            }
        };
    }

    public static <T> FlowableTransformer<T,T> showDialogLoading(final MvpView mvpView){
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return upstream.doOnSubscribe(new Consumer<Subscription>() {
                    @Override
                    public void accept(@NonNull Subscription subscription) throws Exception {
                        mvpView.showDialogLoading();
                    }
                });
            }
        };
    }

    public static <T> FlowableTransformer<T,T> hideLoading(final MvpView mvpView){
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return upstream.doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        mvpView.hideLoading();
                    }
                });
            }
        };
    }

    public static <T> FlowableTransformer<T,T> hideDialogLoading(final MvpView mvpView){
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return upstream.doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        mvpView.hideDialogLoading();
                    }
                });
            }
        };
    }


}