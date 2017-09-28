package com.me.data.model.mine;

/**
 * Created by jjr on 2017/6/3.
 *
 * 学员对应考季下收藏的课程bean
 */

public class CollectCourse {

    /**
     * sSubjectId : 136
     * lectureId : 296
     * subjectId : 141
     * memberId : 322
     * lectureOrder : 第08讲
     * courseId : 173
     * createDate : 2017-05-23 17:35:15
     * lectureName : 二维码测试
     * courseName : 张志凤基础班
     */

    private String sSubjectId;
    private String lectureId;
    private String subjectId;
    private String memberId;
    private String lectureOrder;
    private String courseId;
    private String createDate;
    private String lectureName;
    private String courseName;

    public String getSSubjectId() {
        return sSubjectId;
    }

    public void setSSubjectId(String sSubjectId) {
        this.sSubjectId = sSubjectId;
    }

    public String getLectureId() {
        return lectureId;
    }

    public void setLectureId(String lectureId) {
        this.lectureId = lectureId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getLectureOrder() {
        return lectureOrder;
    }

    public void setLectureOrder(String lectureOrder) {
        this.lectureOrder = lectureOrder;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
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
