package com.me.fanyin.zbme.views.exam.activity.exampaperlist.bean;

/**
 * Created by wyc on 2015/12/30.
 */
public class Course {
    private String cwName;// 课程名称
    private String courseState;//课程状态1.未学习 2 学习中 3 已完成
    private String courseImg;//课程图片
    private String courseTeacher;//课程讲师
    private String courseCredit;//课程学分
    private String cwCode;//课程编码

    private int courseCount;//课节总数
    private int downloadCount;//下载课节数
    private boolean isCheck;
    private String examId;
    private String subjectId;

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getCwName() {
        return cwName;
    }

    public void setCwName(String cwName) {
        this.cwName = cwName;
    }

    public String getCwCode() {
        return cwCode;
    }

    public void setCwCode(String cwCode) {
        this.cwCode = cwCode;
    }

    public String getCourseState() {
        return courseState;
    }

    public void setCourseState(String courseState) {
        this.courseState = courseState;
    }

    public String getCourseImg() {
        return courseImg;
    }

    public void setCourseImg(String courseImg) {
        this.courseImg = courseImg;
    }

    public String getCourseTeacher() {
        return courseTeacher;
    }

    public void setCourseTeacher(String courseTeacher) {
        this.courseTeacher = courseTeacher;
    }

    public String getCourseCredit() {
        return courseCredit;
    }

    public void setCourseCredit(String courseCredit) {
        this.courseCredit = courseCredit;
    }


    public int getCourseCount() {
        return courseCount;
    }

    public void setCourseCount(int courseCount) {
        this.courseCount = courseCount;
    }

    public int getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(int downloadCount) {
        this.downloadCount = downloadCount;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setIsCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }
}
