package com.me.data.model.course;

/**
 * Created by fishzhang on 2017/9/4.
 */

public class KnowledgePointsStatiscialBean {


    /**
     * comment : 考点2评价
     * courseListenTime : null
     * courseTotalTime : 0.0
     * examNum : null
     * examTotalNum : 0
     * oldExamNum : null
     * oldExamTotalNum : 9
     */

    private String comment;
    private float courseListenTime;
    private float courseTotalTime;
    private int examNum;
    private int examTotalNum;
    private int oldExamNum;
    private int oldExamTotalNum;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public float getCourseListenTime() {
        return courseListenTime;
    }

    public void setCourseListenTime(float courseListenTime) {
        this.courseListenTime = courseListenTime;
    }

    public float getCourseTotalTime() {
        return courseTotalTime;
    }

    public void setCourseTotalTime(float courseTotalTime) {
        this.courseTotalTime = courseTotalTime;
    }

    public int getExamNum() {
        return examNum;
    }

    public void setExamNum(int examNum) {
        this.examNum = examNum;
    }

    public int getExamTotalNum() {
        return examTotalNum;
    }

    public void setExamTotalNum(int examTotalNum) {
        this.examTotalNum = examTotalNum;
    }

    public int getOldExamNum() {
        return oldExamNum;
    }

    public void setOldExamNum(int oldExamNum) {
        this.oldExamNum = oldExamNum;
    }

    public int getOldExamTotalNum() {
        return oldExamTotalNum;
    }

    public void setOldExamTotalNum(int oldExamTotalNum) {
        this.oldExamTotalNum = oldExamTotalNum;
    }
}
