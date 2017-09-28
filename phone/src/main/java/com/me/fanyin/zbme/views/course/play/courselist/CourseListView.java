package com.me.fanyin.zbme.views.course.play.courselist;

import com.me.data.model.play.CourseList;
import com.me.data.model.play.CourseListBean;
import com.me.data.model.play.Subject;
import com.me.data.mvp.MvpView;

import java.util.List;

/**
 * Created by wenpeng on 30/03/2017.
 */

public interface CourseListView extends MvpView {
	void initAdapter(List<CourseList> courseList);//课程目录
	void insertData(CourseListBean courseList);
	void subjectDatas(List<Subject> list);//科目列表
	void continuePlay(String courseId,String cwId,String cwName,String sSubjectId);
	void continuePlayError();
	void emptyData();
	void showDataError();
}
