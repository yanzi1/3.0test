package com.me.fanyin.zbme.views.user;

import android.support.v7.widget.Toolbar;

import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.base.BaseActivity;

/**
 * 登录
 */
public class LoginsActivity extends BaseActivity {

    @Override
    public void initView() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("登录");
        setSupportActionBar(toolbar);
    }

    @Override
    public void initData() {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.user_login_activity;
    }
}
