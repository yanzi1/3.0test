package com.me.fanyin.zbme.views.main.activity;

import android.content.Intent;

import com.me.data.mvp.MvpView;

/**
 * Created by wyc
 */

public interface CourseTypeChangeView extends MvpView {
	void initAdapter();
	void showViewStatus(int status);
	void finishActivity();
	Intent getTheIntent();
}
