package com.me.fanyin.zbme.views.exam.activity.exampaperlist.bean;

import com.yunqing.core.db.annotations.Id;
import com.yunqing.core.db.annotations.Table;

import java.io.Serializable;

/**
 *知识点
 * Created by wangyongchao on 2015/7/14 0014.
 *
 */
@Table(name="t_knowledge")
public class Knowledge implements Serializable{

    @Id
    private int dbId;
    private String examinationId;//// 章节Id
    private String examinationName;//经济法(2016) 第一章 法律基本原理 课后作业", // 试卷名称
    private String description;//	试卷描述
    private String totalScore;//试卷总分
    private String passingScore;//及格分数线
    private boolean isSubmit;//是否提交
    private int totalQuestions;//总题数
    private int finishedQuestions;//已做题数
    private int errorQuestions;//错题数
//    private int correctQuestions;// 做对多少题
    private String correctRate;//正确率
    private String updateTime;//更新时间
    private String limitTime;//限制做题时间（分钟）
    private String usedTime;//用时
    private boolean checkStatus;//阅卷状态（）	

    public int getDbId() {
        return dbId;
    }

    public void setDbId(int dbId) {
        this.dbId = dbId;
    }

    public String getExaminationId() {
        return examinationId;
    }

    public void setExaminationId(String examinationId) {
        this.examinationId = examinationId;
    }

    public String getExaminationName() {
        return examinationName;
    }

    public void setExaminationName(String examinationName) {
        this.examinationName = examinationName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(String totalScore) {
        this.totalScore = totalScore;
    }

    public String getPassingScore() {
        return passingScore;
    }

    public void setPassingScore(String passingScore) {
        this.passingScore = passingScore;
    }

    public boolean isSubmit() {
        return isSubmit;
    }

    public void setIsSubmit(boolean isSubmit) {
        this.isSubmit = isSubmit;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public int getFinishedQuestions() {
        return finishedQuestions;
    }

    public void setFinishedQuestions(int finishedQuestions) {
        this.finishedQuestions = finishedQuestions;
    }

    public int getErrorQuestions() {
        return errorQuestions;
    }

    public void setErrorQuestions(int errorQuestions) {
        this.errorQuestions = errorQuestions;
    }

    public String getCorrectRate() {
        return correctRate;
    }

    public void setCorrectRate(String correctRate) {
        this.correctRate = correctRate;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getLimitTime() {
        return limitTime;
    }

    public void setLimitTime(String limitTime) {
        this.limitTime = limitTime;
    }

    public String getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(String usedTime) {
        this.usedTime = usedTime;
    }

    public boolean isCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(boolean checkStatus) {
        this.checkStatus = checkStatus;
    }
}
