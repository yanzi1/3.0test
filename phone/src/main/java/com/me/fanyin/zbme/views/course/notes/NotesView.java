package com.me.fanyin.zbme.views.course.notes;

import com.me.data.mvp.MvpView;

/**
 * Created by wenpeng on 30/03/2017.
 */

public interface NotesView extends MvpView {
	void showResult(String result);
	void postResult(String result);
	void showCourseResult(String result);
	void showPostImgError();
}
