package com.me.data.model.exam.newmodle;

/**
 * Created by fzw on 2017/6/7 0007.
 */

public class CollectQuesVo {

    /**
     * id : 试题id
     * flag : 是否已收藏
     * collectId : 如果已收藏，返回试题收藏记录ID;如果没有该字段不返回
     */

    private String id;
    private boolean flag;
    private String collectId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getCollectId() {
        return collectId;
    }

    public void setCollectId(String collectId) {
        this.collectId = collectId;
    }
}
