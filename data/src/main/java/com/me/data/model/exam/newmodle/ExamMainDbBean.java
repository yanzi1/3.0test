package com.me.data.model.exam.newmodle;

import com.yunqing.core.db.annotations.Id;
import com.yunqing.core.db.annotations.Table;

/**
 * Created by fzw on 2017/6/9 0009.
 */
@Table(name="t_exam_main")
public class ExamMainDbBean {
    @Id
    private int id;
    private String subjectId;
    private String ssubjectId;
    private String dataJson;
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSsubjectId() {
        return ssubjectId;
    }

    public void setSsubjectId(String ssubjectId) {
        this.ssubjectId = ssubjectId;
    }

    public String getDataJson() {
        return dataJson;
    }

    public void setDataJson(String dataJson) {
        this.dataJson = dataJson;
    }
}
