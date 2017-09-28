package com.me.fanyin.zbme.views.main.view.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.me.fanyin.zbme.R;

/**
 * Created by mayunfei on 17-8-3.
 */

public class BottomNavigatorView extends LinearLayoutCompat {
    OnBottomNavigatorViewItemClickListener mOnBottomNavigatorViewItemClickListener;

    public interface OnBottomNavigatorViewItemClickListener {
        void onBottomNavigatorViewItemClick(int position, View view);
    }

    public BottomNavigatorView(Context context) {
        this(context, null);
    }

    public BottomNavigatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomNavigatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setOrientation(HORIZONTAL);
        inflate(context, R.layout.layout_bottom_navigator, this);

        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            final int finalI = i;
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnBottomNavigatorViewItemClickListener.onBottomNavigatorViewItemClick(finalI, v);
                }
            });
        }
    }

    public void select(int position) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (i == position) {
                selectChild(child, true);
            } else {
                selectChild(child, false);
            }
        }
    }

    private void selectChild(View child, boolean select) {
        if (child instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) child;
            group.setSelected(select);
            for (int i = 0; i < group.getChildCount(); i++) {
                selectChild(group.getChildAt(i), select);
            }
        } else {
            child.setSelected(select);
        }
    }

    public void setOnBottomNavigatorViewItemClickListener(OnBottomNavigatorViewItemClickListener listener) {
        this.mOnBottomNavigatorViewItemClickListener = listener;
    }
}
