package com.me.fanyin.zbme.views.main.fragemnt;

import com.me.data.mvp.MvpView;

import java.util.Map;

/**
 * Created by jjr on 2017/3/30.
 */

public interface SubpageFragmentView extends MvpView {

    Map<String,String> getParams();
    int getMouldCode();
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
