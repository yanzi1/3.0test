package com.me.fanyin.zbme.views.user;

import com.me.data.model.user.UserBean;
import com.me.data.mvp.MvpView;

/**
 * Created by xd on 2017/3/30.
 */

public interface RegisterView extends MvpView {
    void requestVerifyCodeSuccess();

    void verifyTheCodeSuccess();

    void registerSuccess(UserBean userBean);

    void requestVerifyCodeFail(Throwable exception);

    void verifyTheCodeFail(String message);

    void registerFial(Throwable throwable);
}
