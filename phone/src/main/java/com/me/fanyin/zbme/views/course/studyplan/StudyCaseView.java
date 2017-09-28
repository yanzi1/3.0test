package com.me.fanyin.zbme.views.course.studyplan;

import com.me.data.model.play.StudyPlanBean;
import com.me.data.model.play.Subject;
import com.me.data.mvp.MvpView;

import java.util.List;

/**
 * Created by wenpeng on 30/03/2017.
 */

public interface StudyCaseView extends MvpView {
    void showDatas(StudyPlanBean result);
    void subjectDatas(List<Subject> list);//科目列表
    void emptyData();
    void showOtherError();
}
