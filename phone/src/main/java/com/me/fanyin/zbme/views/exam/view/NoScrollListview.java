package com.me.fanyin.zbme.views.exam.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

public class NoScrollListview extends ListView{
    private boolean isWebView;

    public NoScrollListview(Context context, AttributeSet attrs) {  
        super(context, attrs);
        setVerticalScrollBarEnabled(false);
    }

    public NoScrollListview(Context context) {
        super(context);
    }

    public NoScrollListview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setIsWebView(boolean isWebView){
        this.isWebView=isWebView;
    }
    /** 
     * 设置不滚动 
     */
     public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!isWebView){
            int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >>2,
                    MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, expandSpec);
        }else{
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
