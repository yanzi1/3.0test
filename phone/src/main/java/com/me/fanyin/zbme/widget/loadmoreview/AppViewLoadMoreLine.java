package com.me.fanyin.zbme.widget.loadmoreview;

import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.me.fanyin.zbme.R;

/**
 * Created by jjr on 2017/7/7.
 */

public class AppViewLoadMoreLine extends LoadMoreView {
    @Override
    public int getLayoutId() {
        return R.layout.app_view_load_more_line;
    }

    @Override
    protected int getLoadingViewId() {
        return R.id.load_more_loading_view;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.load_more_load_fail_view;
    }

    @Override
    protected int getLoadEndViewId() {
        return R.id.load_more_load_end_view;
    }
}
