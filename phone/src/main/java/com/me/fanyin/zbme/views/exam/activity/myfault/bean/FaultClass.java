package com.me.fanyin.zbme.views.exam.activity.myfault.bean;

import java.io.Serializable;

/**
 * Created by dell on 2016/5/18.
 */
public class FaultClass implements Serializable{
    private String className;
    private String classId;//分类ID

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }
}
