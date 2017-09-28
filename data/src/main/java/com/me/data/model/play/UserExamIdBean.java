package com.me.data.model.play;

import com.yunqing.core.db.annotations.Id;
import com.yunqing.core.db.annotations.Table;

/**
 * Created by dell on 2017/6/6.
 */
@Table(name="userExamTable")
public class UserExamIdBean {

    @Id
    private int dbId;
    private String userId;
    private String examId;
    private String examName;

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public int getDbId() {
        return dbId;
    }

    public void setDbId(int dbId) {
        this.dbId = dbId;
    }
}
