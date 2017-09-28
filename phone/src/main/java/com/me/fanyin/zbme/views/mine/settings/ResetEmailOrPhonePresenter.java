package com.me.fanyin.zbme.views.mine.settings;

import com.me.data.model.BaseRes;
import com.me.data.mvp.BasePersenter;
import com.me.data.remote.ApiService;
import com.me.data.remote.rxjava.RxUtils;
import com.me.data.remote.utils.ParamsUtils;
import com.me.fanyin.zbme.views.user.CheckExceptionUtils;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by xd on 2017/5/23.
 */

public class ResetEmailOrPhonePresenter extends BasePersenter<ResetEmailOrPhoneView> {

    @Inject
    public ResetEmailOrPhonePresenter() {
    }

    @Override
    public void getData() {

    }

    public void requestVerificationCode(String phoneOrEmail, String userSentTypePhone, String userTypeGetBackPw) {
        Disposable subscribe = ApiService.requestVerificationCode(ParamsUtils.requestVerificationCode(phoneOrEmail,userSentTypePhone,userTypeGetBackPw))
                .compose(RxUtils.<BaseRes<String>>showDialogLoading(getMvpView()))
                .compose(RxUtils.<String>retrofit())
                .compose(RxUtils.<String>hideDialogLoading(getMvpView()))
                .subscribe(new Consumer<String>() {
                               @Override
                               public void accept(String obj) throws Exception {
                                   getMvpView().requestVerifyCodeSuccess();
                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception {
                                   getMvpView().requestVerifyCodeFail(CheckExceptionUtils.getExceptionMsg(throwable,getMvpView().context()));

                               }
                           }
                );
        addSubscription(subscribe);
    }

    public void verifyTheCode(String phone_or_email, String verifyCode) {
        Disposable subscribe = ApiService.verifyTheCode(ParamsUtils.verifyTheCode(phone_or_email,verifyCode))
                .compose(RxUtils.<BaseRes<String>>showDialogLoading(getMvpView()))
                .compose(RxUtils.<String>retrofit())
                .compose(RxUtils.<String>hideDialogLoading(getMvpView()))
                .subscribe(new Consumer<String>() {
                               @Override
                               public void accept(String obj) throws Exception {
                                   getMvpView().verifyTheCodeSuccess();
                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception {
                                   getMvpView().verifyTheCodeFail(CheckExceptionUtils.getExceptionMsg(throwable,getMvpView().context()));
                               }
                           }
                );
        addSubscription(subscribe);
    }

    public void modifyEmail(String userId, String email, String verifyCode) {
        Disposable subscribe = ApiService.modifyEmail(ParamsUtils.modifyEmail(userId,email,verifyCode))
                .compose(RxUtils.<BaseRes<String>>showLoading(getMvpView()))
                .compose(RxUtils.<String>retrofit())
                .compose(RxUtils.<String>hideLoading(getMvpView()))
                .subscribe(new Consumer<String>() {
                               @Override
                               public void accept(String obj) throws Exception {
                                   getMvpView().modifySuccess();
                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception {
                                   getMvpView().modifyFail(CheckExceptionUtils.getExceptionMsg(throwable,getMvpView().context()));
                               }
                           }
                );
        addSubscription(subscribe);
    }

    public void modifyPhone(String userId, String phone, String verifyCode) {
        Disposable subscribe = ApiService.modifyPhone(ParamsUtils.modifyPhone(userId,phone,verifyCode))
                .compose(RxUtils.<BaseRes<String>>showLoading(getMvpView()))
                .compose(RxUtils.<String>retrofit())
                .compose(RxUtils.<String>hideLoading(getMvpView()))
                .subscribe(new Consumer<String>() {
                               @Override
                               public void accept(String obj) throws Exception {
                                   getMvpView().modifySuccess();
                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception {
                                   getMvpView().modifyFail(CheckExceptionUtils.getExceptionMsg(throwable,getMvpView().context()));

                               }
                           }
                );
        addSubscription(subscribe);
    }
}
