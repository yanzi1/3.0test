package com.me.fanyin.zbme.views.user;

import com.me.data.model.BaseRes;
import com.me.data.model.user.GetBackPwUserInfoBean;
import com.me.data.mvp.BasePersenter;
import com.me.data.remote.ApiService;
import com.me.data.remote.rxjava.RxUtils;
import com.me.data.remote.utils.ParamsUtils;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by xd on 2017/4/5.
 */

public class GetBackPswPresenter extends BasePersenter<GetBackPswView> {

    @Inject
    public GetBackPswPresenter() {
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

    public void verifyTheCode(String cell_phone_number, String verifyCode) {
        Disposable subscribe = ApiService.verifyTheCode(ParamsUtils.verifyTheCode(cell_phone_number,verifyCode))
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

    public void reqeuestAccountInfoByEmail(String email) {
        Disposable subscribe = ApiService.reqeuestAccountInfoByEmail(ParamsUtils.reqeuestAccountInfoByEmail(email))
                .compose(RxUtils.<BaseRes<GetBackPwUserInfoBean>>showDialogLoading(getMvpView()))
                .compose(RxUtils.<GetBackPwUserInfoBean>retrofit())
                .compose(RxUtils.<GetBackPwUserInfoBean>hideDialogLoading(getMvpView()))
                .subscribe(new Consumer<GetBackPwUserInfoBean>() {
                               @Override
                               public void accept(GetBackPwUserInfoBean obj) throws Exception {
                                   getMvpView().returnAccountInfo(obj);
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

    public void reqeuestAccountInfoByPhone(String phone) {
        Disposable subscribe = ApiService.reqeuestAccountInfoByPhone(ParamsUtils.reqeuestAccountInfoByPhone(phone))
                .compose(RxUtils.<BaseRes<GetBackPwUserInfoBean>>showDialogLoading(getMvpView()))
                .compose(RxUtils.<GetBackPwUserInfoBean>retrofit())
                .compose(RxUtils.<GetBackPwUserInfoBean>hideDialogLoading(getMvpView()))
                .subscribe(new Consumer<GetBackPwUserInfoBean>() {
                               @Override
                               public void accept(GetBackPwUserInfoBean obj) throws Exception {
                                   getMvpView().returnAccountInfo(obj);
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

    public void getBackPw(String phoneOrEmail, String sendType, String pswText, String verifyCode) {
        Disposable subscribe = ApiService.getBackPw(ParamsUtils.getBackPw(phoneOrEmail,sendType,pswText,verifyCode))
                .compose(RxUtils.<BaseRes<String>>showDialogLoading(getMvpView()))
                .compose(RxUtils.<String>retrofit())
                .compose(RxUtils.<String>hideDialogLoading(getMvpView()))
                .subscribe(new Consumer<String>() {
                               @Override
                               public void accept(String obj) throws Exception {
                                   getMvpView().getBackPwSuccess();
                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception {
                                   getMvpView().getBackPswFail(throwable);
                               }
                           }
                );
        addSubscription(subscribe);

    }
}
