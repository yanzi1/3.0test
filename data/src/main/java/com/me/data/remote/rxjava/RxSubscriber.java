package com.me.data.remote.rxjava;

import android.content.Context;

import com.me.core.exception.ApiException;
import com.me.core.utils.NetWorkUtils;

import java.net.UnknownHostException;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by xunice on 12/03/2017.
 */

public abstract class RxSubscriber<T> extends ResourceSubscriber<T> {

    private Context context;

    protected RxSubscriber(Context context) {
        this.context = context;
    }

    @Override
    public void onNext(T t) {
        doOnNext(t);
    }

    @Override
    public void onError(Throwable t) {
        /**
         *如果网络未连接不会调用flatMap 所以网络未连接不会出现ApiException错误 {@link RxUtils#retrofit()}.
         */
        if (!NetWorkUtils.isNetworkAvailable(context)) {
            onNetWorkError();//network unConnected
        } else {
            if (t instanceof ApiException) {
                onApiError(((ApiException) t).getCode(), t.getMessage());
            } else if (t instanceof UnknownHostException){
                onNetWorkError();//UnknownHostException 1：服务器地址错误；2：网络未连接
            }else {
                onApiError(-1, t.getMessage()); //json 解析错误 404
            }
        }
    }

    @Override
    public void onComplete() {
        doOnComplete();
    }

    public abstract void doOnNext(T o);//doOnNext()

    public abstract void onEmpty();//返回的response为空，或者List数组size为0

    public abstract void onNetWorkError();//网络未连接

    public abstract void onApiError(int code, String msg);//接口调用操作出现异常，比如注册失败（已注册,短信验证码出错,and so on）

    public abstract void doOnComplete();//doOnComplete()

}
