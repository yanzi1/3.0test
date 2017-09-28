package com.me.data.model.mine;

/**
 * Created by jjr on 2017/6/27.
 */

public class VersionInfo {

    /**
     * id : 2
     * loadAddress : dfg
     * appId : 2
     * releaseDate : 2017-05-17 14:18:47
     * description : 123
     * devicesType : 1
     * explainAddress : http://help.dongao.com/
     * appproject : {"appName":"da-cloudclass-app"}
     * versionCode : 1
     * createDate : 2017-05-16 14:18:56
     * isUpdate : false
     * version : 1.0.0
     */

    private String id;
    private String loadAddress;
    private String appId;
    private String releaseDate;
    private String description;
    private String devicesType;
    private String explainAddress;
    private AppprojectBean appproject;
    private String versionCode;
    private String createDate;
    private String isUpdate;
    private String version;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoadAddress() {
        return loadAddress;
    }

    public void setLoadAddress(String loadAddress) {
        this.loadAddress = loadAddress;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDevicesType() {
        return devicesType;
    }

    public void setDevicesType(String devicesType) {
        this.devicesType = devicesType;
    }

    public String getExplainAddress() {
        return explainAddress;
    }

    public void setExplainAddress(String explainAddress) {
        this.explainAddress = explainAddress;
    }

    public AppprojectBean getAppproject() {
        return appproject;
    }

    public void setAppproject(AppprojectBean appproject) {
        this.appproject = appproject;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(String isUpdate) {
        this.isUpdate = isUpdate;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public static class AppprojectBean {
        /**
         * appName : da-cloudclass-app
         */

        private String appName;

        public String getAppName() {
            return appName;
        }

        public void setAppName(String appName) {
            this.appName = appName;
        }
    }
}
