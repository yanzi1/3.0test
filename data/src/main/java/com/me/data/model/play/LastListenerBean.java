package com.me.data.model.play;

import java.io.Serializable;

/**
 * Created by fishzhang on 2017/7/20.
 */

public class LastListenerBean implements Serializable {


    /**
     * startTime : 00:00:02
     * picPath : http://webupload.admin.phoenix.com/biz/jkdfjdkfjdlf.png
     * sSubjectId : 1
     * couseId : 154
     * lectureId : 155
     * lectureOrder : 02讲
     * sSubjectName : 初级会计实务2016全国
     * lectureName : 会计实务真是好
     * courseName : 开发能用视频
     */

    private String startTime;
    private String picPath;
    private String sSubjectId;
    private String couseId;
    private String lectureId;
    private String lectureOrder;
    private String sSubjectName;
    private String lectureName;
    private String courseName;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getSSubjectId() {
        return sSubjectId;
    }

    public void setSSubjectId(String sSubjectId) {
        this.sSubjectId = sSubjectId;
    }

    public String getCouseId() {
        return couseId;
    }

    public void setCouseId(String couseId) {
        this.couseId = couseId;
    }

    public String getLectureId() {
        return lectureId;
    }

    public void setLectureId(String lectureId) {
        this.lectureId = lectureId;
    }

    public String getLectureOrder() {
        return lectureOrder;
    }

    public void setLectureOrder(String lectureOrder) {
        this.lectureOrder = lectureOrder;
    }

    public String getSSubjectName() {
        return sSubjectName;
    }

    public void setSSubjectName(String sSubjectName) {
        this.sSubjectName = sSubjectName;
    }

    public String getLectureName() {
        return lectureName;
    }

    public void setLectureName(String lectureName) {
        this.lectureName = lectureName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
