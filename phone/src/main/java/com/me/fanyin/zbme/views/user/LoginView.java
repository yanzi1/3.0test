package com.me.fanyin.zbme.views.user;

import com.me.data.model.user.UserBean;
import com.me.data.mvp.MvpView;

/**
 * Created by xunice on 13/03/2017.
 */

public interface LoginView extends MvpView {
    void loginSuccess(UserBean userBean);

    void thirdPartySuccess();
}
