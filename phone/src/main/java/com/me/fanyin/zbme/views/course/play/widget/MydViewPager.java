package com.me.fanyin.zbme.views.course.play.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by fzw on 2017/7/20 0020.
 */

public class MydViewPager extends ViewPager{

    public boolean isIntercept = true;

    public boolean isSplideTable = false;

    public MydViewPager(Context context){
        super(context);
    }

    public MydViewPager(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_MOVE:
                if(isSplideTable)
                    return isIntercept;
                break;
            default:
                isSplideTable = false;
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
