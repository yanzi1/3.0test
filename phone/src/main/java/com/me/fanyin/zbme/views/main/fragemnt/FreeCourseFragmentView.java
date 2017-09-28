package com.me.fanyin.zbme.views.main.fragemnt;

import com.me.data.model.main.StudentsEvaluateInfo;
import com.me.data.mvp.MvpView;

/**
 * Created by jjr on 2017/5/17.
 */

public interface FreeCourseFragmentView extends MvpView {

    String getLectureId();
    void resetView(StudentsEvaluateInfo studentsEvaluateInfo);

}
