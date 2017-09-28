package com.me.fanyin.zbme.views.main.fragemnt;

import com.me.data.mvp.MvpView;

/**
 * Created by wyc 
 */

public interface MainTypeView extends MvpView {
	void initAdapter();
	void showViewStatus(int status);
	String getExamId();
}
