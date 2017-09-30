package com.me.fanyin.zbme.views.exam.activity.exampaperlist.bean;

import java.util.List;

/**
 * Created by wyc on 2015/12/30.
 */
public class MyCourse {
    private boolean isMust;
    private String courseTypeName;// 课程类别
    private List<Course> courseList;// 课程列表

    public String getCourseTypeName() {
        return courseTypeName;
    }

    public void setCourseTypeName(String courseTypeName) {
        this.courseTypeName = courseTypeName;
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }

    public boolean isMust() {
        return isMust;
    }

    public void setIsMust(boolean isMust) {
        this.isMust = isMust;
    }
}
