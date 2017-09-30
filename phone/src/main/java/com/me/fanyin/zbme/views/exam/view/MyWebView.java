package com.me.fanyin.zbme.views.exam.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

/**
 * Created by wyc on 2015/11/19.
 */
public class MyWebView extends WebView{
    // 控制滑动的boolean锁
    public boolean scrollable = true;


    public MyWebView(Context context, AttributeSet attrs, int defStyle,
                     boolean privateBrowsing) {
        super(context, attrs, defStyle, privateBrowsing);
        setBackgroundColor(0);
        // TODO Auto-generated constructor stub
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setBackgroundColor(0);
        // TODO Auto-generated constructor stub
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBackgroundColor(0);
        // TODO Auto-generated constructor stub
    }

    public MyWebView(Context context) {
        super(context);
        setBackgroundColor(0);
        // TODO Auto-generated constructor stub
    }
    public void setScrollable(boolean enable) {
        this.scrollable = enable;

    }
    //
//	 @Override
//	 public boolean onInterceptTouchEvent(MotionEvent event) {
//
//	 return scrollable;
//
//	 }
//
//	@Override
//	public boolean onTouchEvent(MotionEvent event) {
//		  if (event.getAction() == MotionEvent.ACTION_DOWN){
//		        int temp_ScrollY = getScrollY();
//		        scrollTo(getScrollX(), getScrollY() + 1);
//		        scrollTo(getScrollX(), temp_ScrollY);
//		    }
//		    return super.onTouchEvent(event);
//	}

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        return false;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        invalidate();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
