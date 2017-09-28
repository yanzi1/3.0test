package com.me.data.model.play;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fengzongwei on 2016/5/30 0030.
 * 课程章节
 */
public class CourseChapter implements Serializable {

    private String id;//章节ID
    private String name;//章节名字
    private List<CourseWare> pcClientCourseWareList;//课件集合

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

    public List<CourseWare> getPcClientCourseWareList() {
        return pcClientCourseWareList;
    }

    public void setPcClientCourseWareList(List<CourseWare> pcClientCourseWareList) {
        this.pcClientCourseWareList = pcClientCourseWareList;
    }
}
