package com.me.data.remote.rxjava;

import com.me.data.mvp.MvpView;

/**
 * Created by jjr on 2017/6/3.
 */

public abstract class SimpleRxSubscriber<T> extends RxSubscriber<T> {

    private final MvpView mvpView;

    public SimpleRxSubscriber(MvpView mvpView) {
        super(mvpView.context());
        this.mvpView = mvpView;
    }

    @Override
    public void onEmpty() {
        mvpView.showEmptyData();
    }

    @Override
    public void onNetWorkError() {
        mvpView.showNetworkError();
    }

    @Override
    public void onApiError(int code, String msg) {
        if (showErrorToast()) mvpView.showError(msg);
        mvpView.showErrorData();
    }

    public boolean showErrorToast() {
        return false;
    }
}
