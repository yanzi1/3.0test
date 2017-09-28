package com.me.fanyin.zbme.views.mine.settings;

import com.me.data.local.SharedPrefHelper;
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

public class ResetPswPresenter extends BasePersenter<ResetPswView> {

    @Inject
    public ResetPswPresenter() {
    }

    @Override
    public void getData() {

    }

    public void modifyPsw(String userId, String oldPswText, final String pswText) {
        Disposable subscribe = ApiService.modifyPsw(ParamsUtils.modifyPsw(userId,oldPswText,pswText))
                .compose(RxUtils.<BaseRes<String>>showLoading(getMvpView()))
                .compose(RxUtils.<String>retrofit())
                .compose(RxUtils.<String>hideLoading(getMvpView()))
                .subscribe(new Consumer<String>() {
                               @Override
                               public void accept(String result) throws Exception {
                                   SharedPrefHelper.getInstance().setLoginPassword(pswText);
                                   getMvpView().modifySuccess();
                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception {
                                   getMvpView().showError(CheckExceptionUtils.getExceptionMsg(throwable,getMvpView().context()));

                               }
                           }

                );
        addSubscription(subscribe);
    }
}
