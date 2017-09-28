package com.me.data.model.exam;

import com.yunqing.core.db.annotations.Id;
import com.yunqing.core.db.annotations.Table;

import java.io.Serializable;

/**
 * 答题记录
 * wyc
 */

@Table(name="t_answer_log")
public class AnswerLog implements Serializable {
    @Id
    private int dbId;

    private String userId; //用户Id
    private String year;
    private String examId; //考试类型
    private String subjectId; //考试科目
    private String typeId; //试卷类型（随堂，课后。。。4）
    private String sectionId; //章节ID
    private String classId;//班次id（如果非随堂练习，次字段位@“NULL"）
    private String examinationId; //试卷Id
    private String examinationTitle;

    private int totalQuestions;//总题数
    private int finishedQuestions; //finishedQuestions
    private int answerRightNums; //答对问题数
    private int answerErrorNums; //答错问题数
    private String content; //试卷json

    private int currentIndex; //做到哪一题了
    private String updateTime; //最后更新的时间，时间倒序
    private String score;//分数
    private int childIndex; //题冒题小题索引
    private boolean isFinished;//是否答完题
    private int usedTime;

    public int getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(int usedTime) {
        this.usedTime = usedTime;
    }

    /**
     * wyc
     * 用时
     */
    
    

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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

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

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getExaminationId() {
        return examinationId;
    }

    public void setExaminationId(String examinationId) {
        this.examinationId = examinationId;
    }

    public String getExaminationTitle() {
        return examinationTitle;
    }

    public void setExaminationTitle(String examinationTitle) {
        this.examinationTitle = examinationTitle;
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

    public int getAnswerRightNums() {
        return answerRightNums;
    }

    public void setAnswerRightNums(int answerRightNums) {
        this.answerRightNums = answerRightNums;
    }

    public int getAnswerErrorNums() {
        return answerErrorNums;
    }

    public void setAnswerErrorNums(int answerErrorNums) {
        this.answerErrorNums = answerErrorNums;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public int getChildIndex() {
        return childIndex;
    }

    public void setChildIndex(int childIndex) {
        this.childIndex = childIndex;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setIsFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }
}
