package com.me.fanyin.zbme.views.main.fragemnt.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by wyc on 17-6-19.
 */

public class LoveBookImageView extends android.support.v7.widget.AppCompatImageView {
    public LoveBookImageView(Context context) {
        super(context);
    }

    public LoveBookImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LoveBookImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


        int childWidthSize = getMeasuredWidth();
        int childHeightSize = (int) (childWidthSize * 1.0 / 4 * 3);
        setMeasuredDimension(childWidthSize, childHeightSize);

    }
}
