package com.me.data.model.play;

import com.yunqing.core.db.annotations.Id;

import java.io.Serializable;

public class Ssubject implements Serializable {

    @Id
    private int dbId;
	private String sSubjectId;
	private String sSubjectYear;
	private String sSubjectName;

    public String getsSubjectId() {
        return sSubjectId;
    }

    public void setsSubjectId(String sSubjectId) {
        this.sSubjectId = sSubjectId;
    }

    public String getsSubjectYear() {
        return sSubjectYear;
    }

    public void setsSubjectYear(String sSubjectYear) {
        this.sSubjectYear = sSubjectYear;
    }

    public String getsSubjectName() {
        return sSubjectName;
    }

    public void setsSubjectName(String sSubjectName) {
        this.sSubjectName = sSubjectName;
    }
}
