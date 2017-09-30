package com.me.fanyin.zbme.views.exam.activity.myfault;


import com.me.fanyin.zbme.views.exam.mvp.MvpView;
import com.me.fanyin.zbme.widget.EmptyViewLayout;

/**
 * Created by wyc on 2016/5/18.
 */
public interface MyFaultFragmentView extends MvpView {
    void initAdapter();
    EmptyViewLayout getEmptyView();
    void setIsRefresh(boolean isRefresh);
}
