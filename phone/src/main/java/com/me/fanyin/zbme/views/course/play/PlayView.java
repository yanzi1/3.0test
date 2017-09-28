package com.me.fanyin.zbme.views.course.play;

import android.os.Handler;

import com.me.data.model.play.CourseDetail;
import com.me.data.model.play.CourseWare;
import com.me.data.mvp.MvpView;

/**
 * Created by wenpeng on 30/03/2017.
 */

public interface PlayView extends MvpView {
	void initAdapter(CourseDetail courseDetail);
	void playError(String msg);
	void startPlayVideo(CourseWare courseWare,boolean isOffline,boolean flag);
	String getCourseId();
	void delCwResult();
	void isCollectResult(String result);
	void collectCwResult();
	Handler getHandler();
	void openComment();
	void showCwsError(String message);
	void studyKnow(int position);
	boolean isSmart();
    void showSmartContent(String lectureId);
	void showEmptyPlay(String mes);
}
