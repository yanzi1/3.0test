package com.me.fanyin.zbme.views.exam.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.me.core.utils.DensityUtils;
import com.me.fanyin.zbme.R;

import io.vov.vitamio.utils.Log;

/**
 * 自定义控件，实现类似猿题库的上下滑动
 * @author wyc
 */
public class CustomRelativeLayout extends RelativeLayout {
    private int screenWidth;
    private int screenHeight;
    private Button skateboard;
    private boolean mScrolling;
    public TouchListener touchListener;
    private Context context;
    private int X;

    public CustomRelativeLayout(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        setBackgroundColor(Color.TRANSPARENT);
        this.context=context;
        init();
    }

    public CustomRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBackgroundColor(Color.TRANSPARENT);
        // TODO Auto-generated constructor stub
        this.context=context;
        init();
    }

    public TouchListener getTouchListener() {
        return touchListener;
    }

    public void setTouchListener(TouchListener touchListener) {
        this.touchListener = touchListener;
    }

    public void init() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
        X= DensityUtils.dip2px(context, 205);
    }

    public void viewInit() {
        skateboard = (Button) findViewById(R.id.btn);
        skateboard.setOnTouchListener(onTouchListener);
        this.setFocusable(true);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mScrolling = false;
                break;
            case MotionEvent.ACTION_MOVE:

                mScrolling = false;
                break;
            case MotionEvent.ACTION_UP:
                mScrolling = false;
                break;

        }
        return mScrolling;
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // TODO Auto-generated method stub
        super.onLayout(changed, l, t, r, b);
        viewInit();

    }

    private int bottom;
    private int right;
    private int left;
    private int top;
    private int dy;
    private int dx;
    private int width;
    private int height;
    private OnTouchListener onTouchListener = new OnTouchListener() {

        int lastX, lastY;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            // TODO Auto-generated method stub
            width=CustomRelativeLayout.this.getWidth();
            height=CustomRelativeLayout.this.getHeight();
            //int action = event.getAction();
            Log.i("jiance", "width=" + width + "  height=" + height);
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    lastX = (int) event.getRawX();
                    lastY = (int) event.getRawY();
                    //touchListener.skateboardDownOrUp(true);
                    break;
                /**
                 * layout(l,t,r,b) l Left position, relative to parent t Top
                 * position, relative to parent r Right position, relative to parent
                 * b Bottom position, relative to parent
                 * */
                case MotionEvent.ACTION_MOVE:

                    /*
                     int dx = (int) event.getRawX() - lastX;
                    int dy = (int) event.getRawY() - lastY;
                    int left = CustomRelativeLayout.this.getLeft() + dx;
                    int top = CustomRelativeLayout.this.getTop() + dy;
                    int right = CustomRelativeLayout.this.getRight() + dx;
                    int bottom = CustomRelativeLayout.this.getBottom() + dy;*/

                    dx = (int) event.getRawX() - lastX;
                    dy = (int) event.getRawY() - lastY;
                    left = CustomRelativeLayout.this.getLeft() + dx;
                    top = CustomRelativeLayout.this.getTop() + dy;
                    right = CustomRelativeLayout.this.getRight() + dx;
                    bottom = CustomRelativeLayout.this.getBottom() + dy;


                    if (left < 0) {
                        left = 0;
                        right = left + CustomRelativeLayout.this.getWidth();
                    }

                    if (right > screenWidth) {
                        right = screenWidth;
                        left = right - CustomRelativeLayout.this.getWidth();
                    }

                    if (top < 0) {
                        top = 0;
                        bottom = top + CustomRelativeLayout.this.getHeight();
                    }
                    /**
                     * 总高度是55+滑动界面的高度目前是40,最上界面是50dp
                     */
                    //转换一下单位130
                    if (top > (screenHeight - X)) {
                        break;
                     } else {
                        touchListener.backTouchState(left, top, right, bottom);
                    }
                    CustomRelativeLayout.this.layout(left, top, right, screenHeight);

                    lastX = (int) event.getRawX();
                    lastY = (int) event.getRawY();
                    break;
                case MotionEvent.ACTION_UP:
                    break;
            }
            return false;
        }

    };

    /***
     * Touch监听事件
     *
     * @author wyc
     */
    public interface TouchListener {

        public void backTouchState(int left, int top, int right, int bottom);
        public void skateboardDownOrUp(boolean downOrup);
    }
}
