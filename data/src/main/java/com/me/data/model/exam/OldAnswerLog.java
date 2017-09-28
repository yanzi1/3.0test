package com.me.data.model.exam;

/**
 * Created by dell on 2016/6/16.
 */
public class OldAnswerLog {
    private int uid;//试卷ID
    private int userId;//用户ID
    private int subjectId;//科目Id
    private int year;//年份
    private int examId;//考试ID
    private int examType;//试卷类型
    private String subject;//试卷名称
    private String description;
    private long examTime;
    private long limitTime;
    private int checkStatue;
    private int examinationHistoryId;
    private boolean saved;
    private boolean readed;
    private boolean downloaded;
    private boolean submmited;
    private long updateTime;
    private boolean existRecord;
    public int getUserId() {
        return userId;
    }
    
    //新添加的专门为已经提交的做题记录做的
    private int rightnum;//正确题数
    private int errornum;//错题数
    
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getUid() {
        return uid;
    }
    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public int getSubjectId() {
        return subjectId;
    }
    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }
    public int getExamId() {
        return examId;
    }
    public void setExamId(int examId) {
        this.examId = examId;
    }
    public int getExamType() {
        return examType;
    }

    public boolean isSubmmited() {
        return submmited;
    }
    public void setSubmmited(boolean submmited) {
        this.submmited = submmited;
    }
    public boolean isReaded() {
        return readed;
    }
    public void setReaded(boolean readed) {
        this.readed = readed;
    }
    public boolean isDownloaded() {
        return downloaded;
    }

    public boolean isExistRecord() {
        return existRecord;
    }
    public void setExistRecord(boolean existRecord) {
        this.existRecord = existRecord;
    }
    public void setDownloaded(boolean downloaded) {
        this.downloaded = downloaded;
    }
    public void setExamType(int examType) {
        this.examType = examType;
    }
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public long getExamTime() {
        return examTime;
    }
    public void setExamTime(long examTime) {
        this.examTime = examTime;
    }
    public long getLimitTime() {
        return limitTime;
    }
    public void setLimitTime(long limitTime) {
        this.limitTime = limitTime;
    }
    public int getCheckStatue() {
        return checkStatue;
    }
    public void setCheckStatue(int checkStatue) {
        this.checkStatue = checkStatue;
    }
    public int getExaminationHistoryId() {
        return examinationHistoryId;
    }
    public void setExaminationHistoryId(int examinationHistoryId) {
        this.examinationHistoryId = examinationHistoryId;
    }
    public boolean isSaved() {
        return saved;
    }
    public void setSaved(boolean saved) {
        this.saved = saved;
    }


    public long getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public int getRightnum() {
        return rightnum;
    }

    public void setRightnum(int rightnum) {
        this.rightnum = rightnum;
    }

    public int getErrornum() {
        return errornum;
    }

    public void setErrornum(int errornum) {
        this.errornum = errornum;
    }
}
