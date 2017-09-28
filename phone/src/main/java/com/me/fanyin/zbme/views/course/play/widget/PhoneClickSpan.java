package com.me.fanyin.zbme.views.course.play.widget;

import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

/**
 * Created by dell on 2017/7/13.
 */

public class PhoneClickSpan extends ClickableSpan {

    public interface OnLinkClickListener {
        void onLinkClick(View view);
    }

    private OnLinkClickListener listener;

    public PhoneClickSpan(OnLinkClickListener listener) {
        super();
        this.listener = listener;
    }

    @Override
    public void onClick(View widget) {
        listener.onLinkClick(widget);
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setColor(Color.argb(255, 255, 255, 255)); // 设置字体颜色
//        ds.setUnderlineText(false); //去掉下划线
    }

}
