package com.me.fanyin.zbme.views.course.play.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by dell on 2017/4/25.
 */

public class MyViewpager extends ViewPager {

    private boolean mDisableSroll = true;

    public MyViewpager(Context context) {
        super(context);
    }

    /**
     * Constructor
     *
     * @param context the context
     * @param attrs   the attribute set
     */
    public MyViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int height = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            if (h > height) height = h;
        }

        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * Determines the height of this view
     *
     * @param measureSpec A measureSpec packed into an int
     * @param view        the base view with already measured height
     * @return The height of the view, honoring constraints from measureSpec
     */
    private int measureHeight(int measureSpec, View view) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            // set the height from the base view if available
            if (view != null) {
                result = view.getMeasuredHeight();
            }
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
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