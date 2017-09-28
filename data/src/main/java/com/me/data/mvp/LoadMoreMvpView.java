package com.me.data.mvp;

import java.util.List;

/**
 * loadMore
 */
public interface LoadMoreMvpView<T> extends MvpView {
    /**
     * load 加载更多的数据
     */
    void loadMore(List<T> loadMoreList);

    void refreshData(List<T> list);

    /**
     * 下拉刷新 loading 的显示
     */
    void refreshLayoutLoading(boolean showLoading);

    /**
     * 加载完成
     */
    void setLoadMoreComplete();

    /**
     * 数据全部加载完成
     */
    void setLoadMoreEnd(boolean gone);

    /**
     * load more 失败
     */
    void setLoadMoreFail();
}
