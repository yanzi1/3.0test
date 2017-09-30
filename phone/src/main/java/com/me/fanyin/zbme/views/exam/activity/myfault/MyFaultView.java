package com.me.fanyin.zbme.views.exam.activity.myfault;


import android.content.Intent;

import com.me.fanyin.zbme.views.exam.mvp.MvpView;


/**
 * Created by wyc on 2016/5/10.
 */
public interface MyFaultView extends MvpView {
    void initAdapter();
    Intent getTheIntent();
}
