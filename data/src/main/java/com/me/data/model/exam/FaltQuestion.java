package com.me.data.model.exam;

import com.yunqing.core.db.annotations.Id;
import com.yunqing.core.db.annotations.Table;

import java.io.Serializable;

/**
 * 收藏
 */
@Table(name="t_falt_question")
public class FaltQuestion implements Serializable {
    @Id
    private int dbId;
    private String userId;//用户ID
    private String year;//年份
    private String typeId; //首页某一个type
    private String examId; //考试类型
    private String subjectId; //考试科目
    private String classId;//班次id（如果非随堂练习，次字段位@“NULL"）
    private String sectionId;//章节id

    private String examinationId; //试卷Id
    private String examinationName;//试卷名称
    private String questionId;
    private String content; //试题json
    private int choiceType; //问题类型 可从试题里面读取，但是在这里定义，方便汇总和整理

    public int getDbId() {
        return dbId;
    }

    public void setDbId(int dbId) {
        this.dbId = dbId;
    }

    public int getChoiceType() {
        return choiceType;
    }

    public void setChoiceType(int choiceType) {
        this.choiceType = choiceType;
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

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
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

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getExaminationId() {
        return examinationId;
    }

    public void setExaminationId(String examinationId) {
        this.examinationId = examinationId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExaminationName() {
        return examinationName;
    }

    public void setExaminationName(String examinationName) {
        this.examinationName = examinationName;
    }
}
