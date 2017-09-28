package com.me.fanyin.zbme.views.mine.settings;

import android.text.TextUtils;

import com.me.data.model.BaseRes;
import com.me.data.mvp.BasePersenter;
import com.me.data.remote.ApiService;
import com.me.data.remote.rxjava.RxUtils;
import com.me.data.remote.rxjava.SimpleRxSubscriber;
import com.me.fanyin.zbme.R;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by jjr on 2017/5/19.
 */

class FeedbackPresenter extends BasePersenter<FeedbackView> {

    @Inject
    FeedbackPresenter() {

    }

    @Override
    public void getData() {
        String feedbackTitle = getMvpView().getFeedbackTitle();
        String feedbackContent = getMvpView().getFeedbackContent();
        if (TextUtils.isEmpty(feedbackTitle) || TextUtils.isEmpty(feedbackContent)) return;
        Disposable subscribe = ApiService.submitFeedback(feedbackTitle, feedbackContent)
                .compose(RxUtils.<BaseRes<String>>ioMain())
                .compose(RxUtils.<BaseRes<String>>showDialogLoading(getMvpView()))
                .compose(RxUtils.<String>retrofit())
                .compose(RxUtils.<String>hideDialogLoading(getMvpView()))
                .subscribeWith(new SimpleRxSubscriber<String>(getMvpView()) {
                    @Override
                    public void doOnNext(String s) {
                        getMvpView().submitSuccessful();
                    }

                    @Override
                    public void doOnComplete() {

                    }

                    @Override
                    public void onNetWorkError() {
                        getMvpView().showError(getMvpView().context().getString(R.string.app_nonetwork_message));
                    }

                    @Override
                    public void onApiError(int code, String msg) {
                        getMvpView().showError(msg);
                    }
                });
        addSubscription(subscribe);
    }
}
