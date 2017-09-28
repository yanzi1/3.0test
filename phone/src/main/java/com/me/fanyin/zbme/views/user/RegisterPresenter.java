package com.me.fanyin.zbme.views.user;

import com.me.data.model.BaseRes;
import com.me.data.model.user.UserBean;
import com.me.data.mvp.BasePersenter;
import com.me.data.remote.ApiService;
import com.me.data.remote.rxjava.RxUtils;
import com.me.data.remote.utils.ParamsUtils;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by xd on 2017/3/30.
 */

public class RegisterPresenter extends BasePersenter<RegisterView> {

    @Inject
    public RegisterPresenter() {
    }

    @Override
    public void getData() {

    }

    public void requestVerificationCode(String cell_phone_number, String userSentTypePhone, String userTypeRegister) {
        Disposable subscribe = ApiService.requestVerificationCode(ParamsUtils.requestVerificationCode(cell_phone_number,userSentTypePhone,userTypeRegister))
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
                                   getMvpView().requestVerifyCodeFail(throwable);
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

    public void register(String cell_phone_number, String pswText, String verifyCode) {
        Disposable subscribe = ApiService.register(ParamsUtils.register(cell_phone_number,pswText,verifyCode))
                .compose(RxUtils.<BaseRes<UserBean>>showDialogLoading(getMvpView()))
                .compose(RxUtils.<UserBean>retrofit())
                .compose(RxUtils.<UserBean>hideDialogLoading(getMvpView()))
                .subscribe(new Consumer<UserBean>() {
                               @Override
                               public void accept(UserBean userBean) throws Exception {
                                   getMvpView().registerSuccess(userBean);
                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception {
                                   getMvpView().registerFial(throwable);
                               }
                           }
                );
        addSubscription(subscribe);
    }
}
