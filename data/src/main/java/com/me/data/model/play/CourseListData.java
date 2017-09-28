package com.me.data.model.play;

import java.util.List;

public class CourseListData {

    private List<Course> noStudyList;
    private List<Course> studyList;

    public List<Course> getNoStudyList() {
        return noStudyList;
    }

    public void setNoStudyList(List<Course> noStudyList) {
        this.noStudyList = noStudyList;
    }

    public List<Course> getStudyList() {
        return studyList;
    }

    public void setStudyList(List<Course> studyList) {
        this.studyList = studyList;
    }
}
