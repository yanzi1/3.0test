package com.me.fanyin.zbme.views.exam.activity.exampaperlist;

import android.content.Intent;

import com.me.fanyin.zbme.views.exam.mvp.MvpView;
import com.me.fanyin.zbme.views.exam.view.RefreshLayout;

/**
 * Created by wyc on 2016/6/6.
 */
public interface ExamPaperListView extends MvpView {
    void initAdapter();
    void showContentView(int type);//0:正常 1：网络错误 2：没有数据
    boolean isRefreshNow();//是否正在刷新
    RefreshLayout getRefreshLayout();
    Intent getTheIntent();
    void showTopTextTitle(String title);
    void setNoDataMoreShow(boolean isShow);
}
