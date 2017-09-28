package com.me.data.model.exam.newmodle;

/**
 * Created by fzw on 2017/6/13 0013.
 */

public class ErrorStatisticVideo {

    /**
     * endTime : 讲次播放结束时间
     * lectureId : 讲次id
     * startTime : 讲次播放开始时间
     * courseId : 班次id
     */

    private String endTime;
    private String lectureId;
    private String startTime;
    private String courseId;

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getLectureId() {
        return lectureId;
    }

    public void setLectureId(String lectureId) {
        this.lectureId = lectureId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}
