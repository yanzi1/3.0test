package com.me.fanyin.zbme.views.exam.fragment;

import android.os.Bundle;
import android.view.View;

import com.me.fanyin.zbme.views.exam.mvp.MvpView;


/**
 * Created by wyc on 2016/5/6.
 */
public interface ScoreCardFragmentView  extends MvpView {
   void setView(View view);
    void finishActivity();
    Bundle getArgumentData();
}
