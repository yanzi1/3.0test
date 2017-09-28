package com.me.data.model.intel;

import com.yunqing.core.db.annotations.Id;
import com.yunqing.core.db.annotations.Table;

import java.io.Serializable;
import java.util.List;

@Table(name="t_intel_open")
public class IntelOpenInfo implements Serializable {
    @Id
    private int dbId;
    private String des;
    private int stage;
    private List<IntelWeekSubject> subjectList;

    public int getDbId() {
        return dbId;
    }

    public void setDbId(int dbId) {
        this.dbId = dbId;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public List<IntelWeekSubject> getSubjectList() {
        return subjectList;
    }

    public void setSubjectList(List<IntelWeekSubject> subjectList) {
        this.subjectList = subjectList;
    }
}
