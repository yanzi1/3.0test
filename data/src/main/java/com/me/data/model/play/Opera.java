package com.me.data.model.play;


import java.io.Serializable;

public class Opera implements Serializable {

    private String deviceType;
	private String osVersion;
	private String appVersion;
	private String appName;
    private String netType;
    private String userDns;
    private String userCdn;
    private String deviceIP;

    private String playUrl;
    private String relevantId;
    private String errMsg;
    private String errUrl;

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getNetType() {
        return netType;
    }

    public void setNetType(String netType) {
        this.netType = netType;
    }

    public String getUserDns() {
        return userDns;
    }

    public void setUserDns(String userDns) {
        this.userDns = userDns;
    }

    public String getUserCdn() {
        return userCdn;
    }

    public void setUserCdn(String userCdn) {
        this.userCdn = userCdn;
    }

    public String getDeviceIP() {
        return deviceIP;
    }

    public void setDeviceIP(String deviceIP) {
        this.deviceIP = deviceIP;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    public String getRelevantId() {
        return relevantId;
    }

    public void setRelevantId(String relevantId) {
        this.relevantId = relevantId;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getErrUrl() {
        return errUrl;
    }

    public void setErrUrl(String errUrl) {
        this.errUrl = errUrl;
    }
}
