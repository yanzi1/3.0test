package com.me.data.model.play;


import com.yunqing.core.db.annotations.Id;
import com.yunqing.core.db.annotations.Table;

import java.io.Serializable;

@Table(name="courseListTable")
public class Course implements Serializable {

    @Id
    private int dbId;

    private String id;  //课程id
    private String name;    //课程名称
    private String progressSuggested;   //课件进度提示
    private String updateTime;  //更新时间
    private String computerExamFlag;
    private String cwNumber;
    private String endLectureTime;
    private String endLectureOrder;
    private String endLectureId;
    private Lecturer lecturer;
    private String courseBean;

    public String getEndLectureOrder() {
        return endLectureOrder;
    }

    public void setEndLectureOrder(String endLectureOrder) {
        this.endLectureOrder = endLectureOrder;
    }

    public int getDbId() {
        return dbId;
    }

    public void setDbId(int dbId) {
        this.dbId = dbId;
    }

    public String getCourseBean() {
        return courseBean;
    }

    public void setCourseBean(String courseBean) {
        this.courseBean = courseBean;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProgressSuggested() {
        return progressSuggested;
    }

    public void setProgressSuggested(String progressSuggested) {
        this.progressSuggested = progressSuggested;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getComputerExamFlag() {
        return computerExamFlag;
    }

    public void setComputerExamFlag(String computerExamFlag) {
        this.computerExamFlag = computerExamFlag;
    }

    public String getCwNumber() {
        return cwNumber;
    }

    public void setCwNumber(String cwNumber) {
        this.cwNumber = cwNumber;
    }

    public String getEndLectureTime() {
        return endLectureTime;
    }

    public void setEndLectureTime(String endLectureTime) {
        this.endLectureTime = endLectureTime;
    }

    public String getEndLectureId() {
        return endLectureId;
    }

    public void setEndLectureId(String endLectureId) {
        this.endLectureId = endLectureId;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }
}
