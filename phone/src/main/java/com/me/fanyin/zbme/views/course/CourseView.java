package com.me.fanyin.zbme.views.course;

import com.me.data.model.play.ClassHomeBean;
import com.me.data.mvp.MvpView;

/**
 * Created by wenpeng on 30/03/2017.
 */

public interface CourseView extends MvpView {
	void onError(String msg);
	void continuePlay(String courseId,String cwId,String cwName,String sSubjectId);
	void showHomeData(ClassHomeBean result);
	void continuePlayError();
}
