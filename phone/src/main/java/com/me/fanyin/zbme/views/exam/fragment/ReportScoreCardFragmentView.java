package com.me.fanyin.zbme.views.exam.fragment;

import android.content.Intent;
import android.view.View;

import com.me.fanyin.zbme.base.PhoneAppContext;
import com.me.fanyin.zbme.views.exam.mvp.MvpView;

/**
 * Created by wyc on 2016/5/6.
 */
public interface ReportScoreCardFragmentView extends MvpView {
    void setView(View view);
    void finishActivity();
    PhoneAppContext getAppContext();
    Intent getTheIntent();
}
