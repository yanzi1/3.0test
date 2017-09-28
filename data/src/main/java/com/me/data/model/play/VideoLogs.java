package com.me.data.model.play;


/**
 * Created by wenpeng on 2016/5/30 0030.
 * 讲
 */
public class VideoLogs {

    private String courseId;
    private String lectureId;
    private String nodeId;
    private String listenTime;
    private String endTime;
    private String startTime;

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getLectureId() {
        return lectureId;
    }

    public void setLectureId(String lectureId) {
        this.lectureId = lectureId;
    }

    public String getListenTime() {
        return listenTime;
    }

    public void setListenTime(String listenTime) {
        this.listenTime = listenTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}
