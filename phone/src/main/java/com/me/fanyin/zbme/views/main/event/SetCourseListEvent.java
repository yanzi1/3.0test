package com.me.fanyin.zbme.views.main.event;

import com.me.data.model.main.CaptureResultBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jjr on 2017/6/7.
 */

public class SetCourseListEvent implements Serializable {

    private List<CaptureResultBean.CourseVoBean> list;

    public SetCourseListEvent(List<CaptureResultBean.CourseVoBean> list) {
        this.list = list;
    }

    public List<CaptureResultBean.CourseVoBean> getList() {
        return list;
    }

    public void setList(List<CaptureResultBean.CourseVoBean> list) {
        this.list = list;
    }
}
