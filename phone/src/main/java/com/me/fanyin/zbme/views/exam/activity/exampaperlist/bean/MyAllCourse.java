package com.me.fanyin.zbme.views.exam.activity.exampaperlist.bean;

import com.yunqing.core.db.annotations.Id;
import com.yunqing.core.db.annotations.Table;

import java.util.List;

/**
 * Created by wyc on 2015/1/7.
 */
@Table(name="d_myallcourse")
public class MyAllCourse {
    @Id
    private int dbId;
    private boolean isMust;
    private String creditType;// 学分单位
    private List<MyCourse> courseTypeList;// 课程类别列表

    //添加json字符串，存储课程列表所有数据
    private String allDta;
    private String userId;//用户ID
    private String currYear;//当前年份

    public String getCurrYear() {
        return currYear;
    }

    public void setCurrYear(String currYear) {
        this.currYear = currYear;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAllDta() {
        return allDta;
    }

    public void setAllDta(String allDta) {
        this.allDta = allDta;
    }

    public int getDbId() {
        return dbId;
    }

    public void setDbId(int dbId) {
        this.dbId = dbId;
    }

    public String getCreditType() {
        return creditType;
    }

    public void setCreditType(String creditType) {
        this.creditType = creditType;
    }

    public List<MyCourse> getCourseTypeList() {
        return courseTypeList;
    }

    public void setCourseTypeList(List<MyCourse> courseTypeList) {
        this.courseTypeList = courseTypeList;
    }

    public boolean isMust() {
        return isMust;
    }

    public void setIsMust(boolean isMust) {
        this.isMust = isMust;
    }
}
