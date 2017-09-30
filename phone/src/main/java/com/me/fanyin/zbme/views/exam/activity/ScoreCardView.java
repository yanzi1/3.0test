package com.me.fanyin.zbme.views.exam.activity;

import android.content.Intent;

import com.me.fanyin.zbme.base.PhoneAppContext;
import com.me.fanyin.zbme.views.exam.mvp.MvpView;


/**
 * Created by wyc on 2016/5/6.
 */
public interface ScoreCardView extends MvpView {
    Intent getTheIntent();
    PhoneAppContext getAppContext();
    void showStatueView(int state);
    void finishActivity();
}
