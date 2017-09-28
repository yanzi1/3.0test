package com.me.fanyin.zbme.widget;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;

import com.me.fanyin.zbme.R;

/**
 * 统一下拉刷新
 * Created by mayunfei on 17-6-16.
 */

public class BaseSwipeRefreshLayout extends SwipeRefreshLayout {
    public BaseSwipeRefreshLayout(Context context) {
        super(context);
        init();
    }

    public BaseSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.color_primary));
    }
}
