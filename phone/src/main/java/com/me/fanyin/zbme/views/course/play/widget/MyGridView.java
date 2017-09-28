package com.me.fanyin.zbme.views.course.play.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;

/**
 * Created by dell on 2017/6/13.
 */

public class MyGridView extends GridView{

    public MyGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    /**
     * 设置不滚动
     */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                View.MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);

    }
}
