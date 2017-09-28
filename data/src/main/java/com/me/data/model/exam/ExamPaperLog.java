package com.me.data.model.exam;

import com.yunqing.core.db.annotations.Id;
import com.yunqing.core.db.annotations.Table;

import java.io.Serializable;

/**
 * 答题记录
 * wyc
 */

@Table(name="t_exampaper_log")
public class ExamPaperLog implements Serializable {
    @Id
    private String dbId;

    private String userId; //用户Id
    private String examId; //考试类型
    private String subjectId; //考试科目
    private String typeId; //知识点id/考点id（当为能力评估、历年真题时，可为空）
    private String sectionId; //章节ID
    private String classId;//班次id（如果非随堂练习，次字段位@“NULL"）
    private String examinationId; //试卷Id
    private String content; //试卷json
    public ExamPaperLog(){}

    public ExamPaperLog(String userId, String examId, String subjectId, String typeId, String sectionId, String examinationId, String answers){
        this.userId=userId;
        this.examId=examId;
        this.subjectId=subjectId;
        this.typeId=typeId;
        this.examinationId=examinationId;
        this.content=answers;
        this.sectionId=sectionId;
    }

    public String getDbId() {
        return dbId;
    }

    public void setDbId(String dbId) {
        this.dbId = dbId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
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
}
