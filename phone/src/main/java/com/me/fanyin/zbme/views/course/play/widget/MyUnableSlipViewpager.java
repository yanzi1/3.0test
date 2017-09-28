package com.me.fanyin.zbme.views.course.play.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by fengzongwei on 2016/1/4.
 * 无法左右滑动切换的viewpager
 */
public class MyUnableSlipViewpager extends ViewPager {


    private boolean mDisableSroll = true;

    public MyUnableSlipViewpager (Context context) {
        super(context);
    }

    public MyUnableSlipViewpager (Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setDisableScroll(boolean bDisable)
    {
        mDisableSroll = bDisable;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(mDisableSroll)
        {
            return false;
        }
        return super.onInterceptTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(mDisableSroll)
        {
            return false;
        }
        return super.onTouchEvent(ev);
    }

}
