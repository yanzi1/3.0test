package com.me.data.model.play;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dell on 2017/6/6.
 */

public class ClassHomeContent implements Serializable {

    private String forumId;
    private String forumName;
    private String mouldCode;
    private List<ClassHomeContentype> list;

    public String getForumId() {
        return forumId;
    }

    public void setForumId(String forumId) {
        this.forumId = forumId;
    }

    public String getForumName() {
        return forumName;
    }

    public void setForumName(String forumName) {
        this.forumName = forumName;
    }

    public String getMouldCode() {
        return mouldCode;
    }

    public void setMouldCode(String mouldCode) {
        this.mouldCode = mouldCode;
    }

    public List<ClassHomeContentype> getList() {
        return list;
    }

    public void setList(List<ClassHomeContentype> list) {
        this.list = list;
    }
}
