package com.me.data.model.main;

import com.yunqing.core.db.annotations.Id;
import com.yunqing.core.db.annotations.Table;

import java.io.Serializable;
import java.util.List;

@Table(name="t_maintypebean")
public class MainTypeBean implements Serializable {
    @Id
    private int dbId;
    private List<MainTypeDetailBean> list;
    //用于存储数据库
    private String userId;
    private String content;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<MainTypeDetailBean> getList() {
        return list;
    }

    public void setList(List<MainTypeDetailBean> list) {
        this.list = list;
    }
    
    public MainTypeBean(){}
}
