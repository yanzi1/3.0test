package com.me.data.model.exam.newmodle;

/**
 * Created by fzw on 2017/6/13 0013.
 * 相关知识点视频播放id
 */

public class KnowledgeVoVideo {

    private String lectureId;//讲次id
    private String courseId;//班次id
    private String startTime;//开始时间
    private String endTime;//结束时间

    public String getLectureId() {
        return lectureId;
    }

    public void setLectureId(String lectureId) {
        this.lectureId = lectureId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
