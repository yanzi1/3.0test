package com.me.fanyin.zbme.views.main.activity;

import com.me.data.mvp.MvpView;

/**
 * Created by jjr on 2017/5/11.
 */

public interface StudentsAppraiseView extends MvpView {

    String getLectureId();
    void setAppraiseScore(float beautifulScore, float attractScore, float reasonableScore);
    void onRefreshComplete();

    /**
     * 加载完成的状态
     * @param status
     * @see com.chad.library.adapter.base.loadmore.LoadMoreView#STATUS_DEFAULT
     * @see com.chad.library.adapter.base.loadmore.LoadMoreView#STATUS_LOADING
     * @see com.chad.library.adapter.base.loadmore.LoadMoreView#STATUS_FAIL
     * @see com.chad.library.adapter.base.loadmore.LoadMoreView#STATUS_END
     */
    void onLoadMoreComplete(int status);
}
