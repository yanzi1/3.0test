package com.me.share.bean;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;

/**
 * Created by xd on 2017/3/27.
 */

public class ShareResponseBean {
    private int status;
    private Platform platform;
    private int action;
    private HashMap<String,Object> hashMap;
    private Throwable throwable;

    public ShareResponseBean(int status, Platform platform, int action, HashMap<String, Object> hashMap) {
        this.status = status;
        this.platform = platform;
        this.action = action;
        this.hashMap = hashMap;
    }

    public ShareResponseBean(int status,Platform platform, int action, Throwable throwable) {
        this.platform = platform;
        this.action = action;
        this.throwable = throwable;
        this.status = status;
    }

    public ShareResponseBean(int status,Platform platform, int action ) {
        this.platform = platform;
        this.action = action;
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public HashMap<String, Object> getHashMap() {
        return hashMap;
    }

    public void setHashMap(HashMap<String, Object> hashMap) {
        this.hashMap = hashMap;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }
}
