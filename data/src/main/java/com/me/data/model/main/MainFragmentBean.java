package com.me.data.model.main;

import com.yunqing.core.db.annotations.Id;
import com.yunqing.core.db.annotations.Table;

import java.io.Serializable;
import java.util.List;

@Table(name="t_maindetailfragment")
public class MainFragmentBean implements Serializable {
    @Id
    private int dbId;
    private List<MainDetailBean> forumList;
    
    private String examId;
    private String content;

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

    public List<MainDetailBean> getForumList() {
        return forumList;
    }

    public void setForumList(List<MainDetailBean> forumList) {
        this.forumList = forumList;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
