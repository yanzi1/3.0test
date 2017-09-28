package com.me.fanyin.zbme.widget.dropdown;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by mayunfei on 17-5-12.
 */

public class CouDanImageView extends android.support.v7.widget.AppCompatImageView {
    public CouDanImageView(Context context) {
        super(context);
    }

    public CouDanImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CouDanImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
