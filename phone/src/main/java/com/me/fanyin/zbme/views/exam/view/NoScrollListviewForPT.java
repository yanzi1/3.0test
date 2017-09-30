package com.me.fanyin.zbme.views.exam.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class NoScrollListviewForPT extends ListView{
    private boolean isWebView;
    private float mTouchDownY;

    public NoScrollListviewForPT(Context context, AttributeSet attrs) {  
        super(context, attrs);
//        setVerticalScrollBarEnabled(false);
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//        boolean isTouch=super.onTouchEvent(ev);
//        switch (ev.getAction()){
//            case MotionEvent.ACTION_MOVE:
//                float deltaY = ev.getY() - mTouchDownY;
//                if (deltaY>0){
//                    isTouch= false;
//                }
//                break;
//            case MotionEvent.ACTION_DOWN:
//                mTouchDownY = ev.getY();
//                break;
//            case MotionEvent.ACTION_UP:
//                break;
//        }
//        boolean isHas= super.onTouchEvent(ev);
//        return isTouch;
//    }

    public NoScrollListviewForPT(Context context) {
        super(context);
    }

    public NoScrollListviewForPT(Context context, AttributeSet attrs, int defStyle) {
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
