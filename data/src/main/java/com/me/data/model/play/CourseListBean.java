package com.me.data.model.play;

import com.yunqing.core.db.annotations.Id;
import com.yunqing.core.db.annotations.Table;

import java.util.List;

@Table(name="courseListBean")
public class CourseListBean{
    @Id
    private int dbId;
    private String userId;
    private String content;
    private String sSubjectId;
    private List<CourseList> courseList;

    public int getDbId() {
        return dbId;
    }

    public void setDbId(int dbId) {
        this.dbId = dbId;
    }

    public String getsSubjectId() {
        return sSubjectId;
    }

    public void setsSubjectId(String sSubjectId) {
        this.sSubjectId = sSubjectId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<CourseList> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<CourseList> courseList) {
        this.courseList = courseList;
    }
}
