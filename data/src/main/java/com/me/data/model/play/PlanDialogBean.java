package com.me.data.model.play;


import java.io.Serializable;

public class PlanDialogBean implements Serializable {

    private String name;
    private int mipmap;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMipmap() {
        return mipmap;
    }

    public void setMipmap(int mipmap) {
        this.mipmap = mipmap;
    }
}
