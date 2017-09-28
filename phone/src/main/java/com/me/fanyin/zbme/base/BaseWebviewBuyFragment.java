package com.me.fanyin.zbme.base;

import android.graphics.Bitmap;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.alibaba.fastjson.JSON;
import com.me.core.exception.ErrorHandlers;
import com.me.core.utils.NetWorkUtils;
import com.me.core.utils.SystemUtils;
import com.me.data.event.LoginSuccessEvent;
import com.me.data.local.SharedPrefHelper;
import com.me.data.remote.ApiService;
import com.me.data.remote.rxjava.RxUtils;
import com.me.data.remote.utils.ParamsUtils;
import com.me.fanyin.zbme.views.course.play.widget.WVJBWebViewClient;
import com.me.fanyin.zbme.views.user.LoginActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.reactivestreams.Subscription;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * 活动 H5 页面  内有加入购物车逻辑
 *
 * Created by jijiangrui on 2016/12/9.
 */

public class BaseWebviewBuyFragment extends BaseWebviewFragment {

    private String goods;
    public CompositeDisposable mDisposable;
    private boolean isload = false;
    MyWebViewClient myWebViewClient;
    @Override
    protected String setUrl() {
        return null;
    }

    public WebViewClient setWebViewClient() {
        mDisposable = new CompositeDisposable();
        myWebViewClient = new MyWebViewClient(base_wbv, this);
        return myWebViewClient;
    }

    @Override
    public void onDestroy() {
        cancelSubscription(mDisposable);
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(isload) {
            base_wbv.reload();
        }
    }

    @Override
    public void initView() {
        super.initView();
        EventBus.getDefault().register(this);
    }

    class MyWebViewClient extends WVJBWebViewClient{

        WeakReference<BaseWebviewBuyFragment> weakReference;

        public MyWebViewClient(final WebView view, BaseWebviewBuyFragment fragment) {
            super(view,  new WVJBHandler() {
                @Override
                public void request(Object data, WVJBResponseCallback callback) {
                    callback.callback("Response for message from Android!");
                }
            });
            if(SharedPrefHelper.getInstance().isLogin()){
                send(getMDUserInfo(), new WVJBResponseCallback() {
                    @Override
                    public void callback(Object data) {
                    }
                });
            }

            registerHandler("reviceCoupons", new WVJBHandler() {
                @Override
                public void request(Object data, WVJBResponseCallback callback) {
                    String json = getMDUserInfo();
                    if(!SharedPrefHelper.getInstance().isLogin()){
                        gotoActivity(LoginActivity.class);
                    }else{
                        callback.callback(json);
                    }
                }
            });

            registerHandler("isLogin", new WVJBHandler() {
                @Override
                public void request(Object data, WVJBResponseCallback callback) {
                    // ToastBarUtils.showToast(view.getContext(), data.toString());
                    callback.callback(SharedPrefHelper.getInstance().isLogin() ? "YES":"NO");
                }
            });

            registerHandler("addShopCartBatch", new WVJBHandler() {
                @Override
                public void request(Object data, WVJBResponseCallback callback) {
                   // ToastBarUtils.showToast(view.getContext(), data.toString());
                    goods = data.toString();
                    if(SharedPrefHelper.getInstance().isLogin()){
                        addShopCart();
                    }else{
                        gotoActivity(LoginActivity.class);
                    }
                }
            });
            this.weakReference = new WeakReference<BaseWebviewBuyFragment>(fragment);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            BaseWebviewBuyFragment baseWebviewFragment = weakReference.get();
            if (baseWebviewFragment!=null) {
                if (!NetWorkUtils.isNetworkAvailable(view.getContext()))
                    baseWebviewFragment.mStatusLayoutManager.showNetWorkError();
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            BaseWebviewBuyFragment baseWebviewFragment = weakReference.get();
            if (!NetWorkUtils.isNetworkAvailable(view.getContext())) {
                if (baseWebviewFragment.base_pgb != null && baseWebviewFragment.base_pgb.getProgress() >= 60) {
                    baseWebviewFragment.mStatusLayoutManager.showContent();
                } else {
                    baseWebviewFragment.mStatusLayoutManager.showNetWorkError();
                }
            } else {
                baseWebviewFragment.mStatusLayoutManager.showContent();
            }
            isload = true;
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            BaseWebviewBuyFragment baseWebviewFragment = weakReference.get();
            if (baseWebviewFragment!=null){
                if (!NetWorkUtils.isNetworkAvailable(view.getContext())) {
                    if (baseWebviewFragment.base_pgb != null && baseWebviewFragment.base_pgb.getProgress() >= 60) {
                        baseWebviewFragment.mStatusLayoutManager.showContent();
                    } else {
                        baseWebviewFragment.mStatusLayoutManager.showNetWorkError();
                    }
                } else {
                    if (baseWebviewFragment.base_pgb != null && baseWebviewFragment.base_pgb.getProgress() >= 80) {
                        baseWebviewFragment.mStatusLayoutManager.showContent();
                    } else {
                        baseWebviewFragment.mStatusLayoutManager.showError();
                    }
                }
            }

        }
    }

    protected void addSubscription(Disposable disposable) {
        mDisposable.add(disposable);
    }

    public void cancelSubscription(Disposable disposable) {
        if (mDisposable != null && disposable != null && !disposable.isDisposed()) {
            mDisposable.remove(disposable);
        }
    }

    @Subscribe
    public void LoginSuccessEvent(LoginSuccessEvent event) {
        addShopCart();
    }

    public void addShopCart() {
        Disposable subscribe = ApiService.getShopCartBatchs(
                ParamsUtils.getShopCartBatchs(goods))
                .doOnSubscribe(new Consumer<Subscription>() {
                    @Override
                    public void accept(Subscription subscription) throws Exception {
                        loadingDialog.show();
                    }

                })
                .compose(RxUtils.<String>retrofit()) //几种方式实现逻辑前置
                .doFinally(new Action() { //最后都要执行的操作
                    @Override
                    public void run() throws Exception {
                        loadingDialog.dismiss();
                    }
                })
                .subscribe(new Consumer<String>() {
                               @Override
                               public void accept(String addShopCart) throws Exception {

                               }
                           }, ErrorHandlers.displayErrorConsumer(getActivity())
                );
        addSubscription(subscribe);
    }

    private UserBean userBean;
    private String getMDUserInfo() {
        userBean = new UserBean();
            userBean.setType("app");
            userBean.setUserId("");
        String json = JSON.toJSONString(userBean);
        if (userBean.getUserId().isEmpty()) {
            json = "{\"type\":\"app\",\"appVersion\":\"" + SystemUtils.getVersion(getActivity()) + "\" }";
        }
        return json;
    }

}
