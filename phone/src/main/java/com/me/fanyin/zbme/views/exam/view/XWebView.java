package com.me.fanyin.zbme.views.exam.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

/**
 * Created by xunice on 15/11/23.
 */
public class XWebView extends WebView {
    public Context context;

    public interface PlayFinish{
        void After();
    }
    PlayFinish df;
    public void setDf(PlayFinish playFinish) {
        this.df = playFinish;
    }
    public XWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        setBackgroundColor(0);
        setVerticalScrollBarEnabled(false);

    }
    public XWebView(Context context) {
        super(context);
        this.context=context;
    }
    //onDraw表示显示完毕
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        df.After();
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        invalidate();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    /**
     * 设置不能点击
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        return false;
    }

   /* public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN){

            int temp_ScrollY = getScrollY();
            scrollTo(getScrollX(), getScrollY() + 1);
            scrollTo(getScrollX(), temp_ScrollY);

        }

      return super.onTouchEvent(event);
    }*/

}
