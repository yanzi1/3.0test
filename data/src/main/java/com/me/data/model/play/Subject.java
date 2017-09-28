package com.me.data.model.play;

import com.yunqing.core.db.annotations.Id;
import com.yunqing.core.db.annotations.Table;

import java.io.Serializable;
import java.util.List;

@Table(name="class_subject")
public class Subject implements Serializable {

    @Id
    private int dbId;
	private String subjectId;
	private String year;
	private String subjectName;
    private List<Ssubject> memberSeasonSubjectList;
    private String userId;
    private String content;
    private String examId;
    private String openState;
    private String openMsg;

    public String getOpenState() {
        return openState;
    }

    public void setOpenState(String openState) {
        this.openState = openState;
    }

    public String getOpenMsg() {
        return openMsg;
    }

    public void setOpenMsg(String openMsg) {
        this.openMsg = openMsg;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Ssubject> getMemberSeasonSubjectList() {
        return memberSeasonSubjectList;
    }

    public void setMemberSeasonSubjectList(List<Ssubject> memberSeasonSubjectList) {
        this.memberSeasonSubjectList = memberSeasonSubjectList;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
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
}
