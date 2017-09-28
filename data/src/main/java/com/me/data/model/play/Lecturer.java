package com.me.data.model.play;

import java.io.Serializable;

/**
 * Created by wenpeng on 2016/5/30 0030.
 * 讲
 */
public class Lecturer implements Serializable {

    private String id;
    private String picPath;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
