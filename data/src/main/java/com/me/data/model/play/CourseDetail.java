package com.me.data.model.play;

import com.yunqing.core.db.annotations.Id;
import com.yunqing.core.db.annotations.Table;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fengzongwei on 2016/5/30 0030.
 * 课程详情
 */
@Table(name = "t_play_courseDetail")
public class CourseDetail implements Serializable{
    @Id
    private int dbId;//数据库Id
//    private String sectionFlag;//是否包含章节
    private List<CourseChapter> resultList;//章节集合
    private String description;//课程介绍

    //自增字段存入数据库
    private String userId;//用户id
    private String subjectId;//科目Id
    private String sSubjectId;//考季id
    private String sSubjectName;//考季名称
    private String picPath;//课程图片
    private String id;//课程Id
    private String name;//课程name
    private String contentJson;//详情数据json
    private int cwCount;//该课程下讲的个数

    public String getsSubjectName() {
        return sSubjectName;
    }

    public void setsSubjectName(String sSubjectName) {
        this.sSubjectName = sSubjectName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getsSubjectId() {
        return sSubjectId;
    }

    public void setsSubjectId(String sSubjectId) {
        this.sSubjectId = sSubjectId;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<CourseChapter> getResultList() {
        return resultList;
    }

    public void setResultList(List<CourseChapter> resultList) {
        this.resultList = resultList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCwCount() {
        return cwCount;
    }

    public void setCwCount(int cwCount) {
        this.cwCount = cwCount;
    }

    public int getDbId() {
        return dbId;
    }

    public void setDbId(int dbId) {
        this.dbId = dbId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getContentJson() {
        return contentJson;
    }

    public void setContentJson(String contentJson) {
        this.contentJson = contentJson;
    }

//    public String getSectionFlag() {
//        return sectionFlag;
//    }
//
//    public void setSectionFlag(String sectionFlag) {
//        this.sectionFlag = sectionFlag;
//    }

}
