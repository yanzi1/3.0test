package com.me.fanyin.zbme.views.mine.settings;

import com.me.data.mvp.MvpView;

/**
 * Created by xd on 2017/5/23.
 */

public interface ResetEmailOrPhoneView extends MvpView {
    void requestVerifyCodeSuccess();

    void requestVerifyCodeFail(String message);

    void modifySuccess();

    void modifyFail(String message);

    void verifyTheCodeSuccess();

    void verifyTheCodeFail(String msg);
}
