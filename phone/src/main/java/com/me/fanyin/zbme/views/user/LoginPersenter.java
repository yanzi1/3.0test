package com.me.fanyin.zbme.views.user;

import com.me.data.local.SharedPrefHelper;
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
 * Created by xunice on 13/03/2017.
 */

public class LoginPersenter extends BasePersenter<LoginView> {


    @Inject
    LoginPersenter() {

    }

    @Override
    public void getData() {

    }

    public void login(String username,final String password,String ip) {
        Disposable subscribe = ApiService.login(ParamsUtils.login(username,password,ip))
                .compose(RxUtils.<BaseRes<UserBean>>showDialogLoading(getMvpView()))
                .compose(RxUtils.<UserBean>retrofit())
                .compose(RxUtils.<UserBean>hideDialogLoading(getMvpView()))
                .subscribe(new Consumer<UserBean>() {
                       @Override
                       public void accept(UserBean userBean) throws Exception {
                           saveUserInfoToSp(userBean);
                           SharedPrefHelper.getInstance()
                                   .setLoginPassword(password);
                           SharedPrefHelper.getInstance()
                                   .setUserAvatarImageUrl(userBean.getAvatarImageUrl());
                           getMvpView().loginSuccess(userBean);

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


    public void thirdPartyLogin(String weiboId,String ip){
        Disposable subscribe = ApiService.thirdPartyLogin(ParamsUtils.thirdPartyLogin(weiboId,ip))
                .compose(RxUtils.<UserBean>retrofit())
                .compose(RxUtils.<UserBean>hideDialogLoading(getMvpView()))
                .subscribe(new Consumer<UserBean>() {
                               @Override
                               public void accept(UserBean userBean) throws Exception {
                                   saveUserInfoToSp(userBean);
                                   getMvpView().thirdPartySuccess();
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

    private void saveUserInfoToSp(UserBean userBean){
        SharedPrefHelper.getInstance()
                .setAccessToken(userBean.getAccessToken());
        SharedPrefHelper.getInstance()
                .setLoginUsername(userBean.getUsername());
        SharedPrefHelper.getInstance()
                .setUserEmail(userBean.getEmail());
        SharedPrefHelper.getInstance()
                .setUserPhoneNumber(userBean.getMobilePhone());
        SharedPrefHelper.getInstance()
                .setUserId(userBean.getId());
        SharedPrefHelper.getInstance()
                .setLevelName(userBean.getLevelName());
        SharedPrefHelper.getInstance()
                .setLevel(userBean.getLevel());
        SharedPrefHelper.getInstance()
                .setNowIntegral(userBean.getNowIntegral());
        SharedPrefHelper.getInstance()
                .setGrowthValue(userBean.getGrowthValue());
        SharedPrefHelper.getInstance().setIsLogin(true);
    }
}
