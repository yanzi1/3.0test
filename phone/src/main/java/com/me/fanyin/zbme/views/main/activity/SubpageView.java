package com.me.fanyin.zbme.views.main.activity;

import com.me.data.mvp.MvpView;

/**
 * Created by jjr on 2017/5/9.
 */

interface SubpageView extends MvpView{

    String getExamId();
    String getForumId();
    int getMouldCode();
    void setTabVisibility(boolean tabVisibility);

}
