package com.me.fanyin.zbme.views.user;

import com.me.data.model.user.GetBackPwUserInfoBean;
import com.me.data.mvp.MvpView;

/**
 * Created by xd on 2017/4/5.
 */

public interface GetBackPswView extends MvpView{
    void requestVerifyCodeSuccess();

    void verifyTheCodeSuccess();

    void returnAccountInfo(GetBackPwUserInfoBean obj);

    void getBackPwSuccess();

    void requestVerifyCodeFail(String message);

    void verifyTheCodeFail(String message);

    void getBackPswFail(Throwable throwable);
}
