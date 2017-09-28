package com.me.data.model.play;

/**
 * Created by dell on 2016/12/6.
 */
public class PlayParamsBean {


    /**
     * app : biz-course-v1.0
     * type : 1
     * vid : 20101560
     * key : JjsOFOnyi8Y10bjmg/CYsGmPwGRO8CgcGFPrhzP7T4E=
     * code : 0
     * message :
     */

    private String app;
    private String type;
    private String vid;
    private String key;
    private String code;
    private String message;
    private String userId;
    private String cwId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCwId() {
        return cwId;
    }

    public void setCwId(String cwId) {
        this.cwId = cwId;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
