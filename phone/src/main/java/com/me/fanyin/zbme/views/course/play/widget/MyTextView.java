package com.me.fanyin.zbme.views.course.play.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class MyTextView extends AppCompatTextView {

	public MyTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public MyTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyTextView(Context context) {
		super(context);
	}

	/**
	 * 此方法默认返回值为false，在此处将其返回为true就可以使TextView一创建就具有焦点
	 */
	@Override
	public boolean isFocused() {
		return true;
	}

}
