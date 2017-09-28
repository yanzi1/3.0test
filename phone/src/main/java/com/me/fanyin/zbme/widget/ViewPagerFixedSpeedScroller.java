package com.me.fanyin.zbme.widget;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * Created by fzw on 2017/4/25 0025.
 * 修改viewpager滑动速度
 */

public class ViewPagerFixedSpeedScroller extends Scroller{

    private int mDuration = 100;
    public ViewPagerFixedSpeedScroller(Context context) {
        super(context);
    }
    public ViewPagerFixedSpeedScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }
    public ViewPagerFixedSpeedScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

}
