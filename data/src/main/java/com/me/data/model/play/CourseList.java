package com.me.data.model.play;


import java.io.Serializable;
import java.util.List;

public class CourseList implements Serializable {

    private String courseType;  //1代表开课 0代表未开课
    private String courseTypeName;  //学习中 未开课
    private List<Course> courseItems;

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public String getCourseTypeName() {
        return courseTypeName;
    }

    public void setCourseTypeName(String courseTypeName) {
        this.courseTypeName = courseTypeName;
    }

    public List<Course> getCourseItems() {
        return courseItems;
    }

    public void setCourseItems(List<Course> courseItems) {
        this.courseItems = courseItems;
    }
}
