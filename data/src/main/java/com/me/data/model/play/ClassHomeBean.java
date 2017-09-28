package com.me.data.model.play;

import com.me.data.model.course.CourseStatiscalBean;
import com.yunqing.core.db.annotations.Id;
import com.yunqing.core.db.annotations.Table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2017/6/6.
 */
@Table(name="courseHomeTable")
public class ClassHomeBean implements Serializable{

    @Id
    private int dbId;
    private ClassHomeTop countDownInfo;
    private List<ClassHomeContent> forumList;
    private ClassHomeTop oilInfo;
    private String userId;
    private String content;
    private String examId;
    private String type;
    private ArrayList<CourseStatiscalBean> statistics;
    private LastListenerBean lastListern;

    public LastListenerBean getLastListern() {
        return lastListern;
    }

    public void setLastListern(LastListenerBean lastListern) {
        this.lastListern = lastListern;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<CourseStatiscalBean> getStatistics() {
        return statistics;
    }

    public void setStatistics(ArrayList<CourseStatiscalBean> statistics) {
        this.statistics = statistics;
    }

    public int getDbId() {
        return dbId;
    }

    public void setDbId(int dbId) {
        this.dbId = dbId;
    }

    public ClassHomeTop getOilInfo() {
        return oilInfo;
    }

    public void setOilInfo(ClassHomeTop oilInfo) {
        this.oilInfo = oilInfo;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
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

    public ClassHomeTop getCountDownInfo() {
        return countDownInfo;
    }

    public void setCountDownInfo(ClassHomeTop countDownInfo) {
        this.countDownInfo = countDownInfo;
    }

    public List<ClassHomeContent> getForumList() {
        return forumList;
    }

    public void setForumList(List<ClassHomeContent> forumList) {
        this.forumList = forumList;
    }
}
