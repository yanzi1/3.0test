package com.me.fanyin.zbme.views.main.activity;

import com.me.data.model.main.BooksErrataMenuListBean;
import com.me.data.mvp.MvpView;

import java.util.List;

/**
 * Created by jjr on 2017/6/12.
 */

interface BooksErrataView extends MvpView{

    String getBookId();
    String getExamId();
    void initMenu(List<BooksErrataMenuListBean.SubjectListBean> list);
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
