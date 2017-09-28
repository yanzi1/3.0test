package com.me.data.model.main;

import com.yunqing.core.db.annotations.Id;
import com.yunqing.core.db.annotations.Table;

import java.io.Serializable;

@Table(name="t_main_ad")
public class MainADBean implements Serializable {
    @Id
    private int dbId;
    private String id;
    private String startDate;
    private String status;
    private String devicesType;
    private String endDate;
    private String adImg;
    private String createDate;
    private String adLink;
    private String isShow;
    private String version;
    //new 
    private String pclassific;
    private int type;//内敛外链2：内联 1：外链

    public String getPclassific() {
        return pclassific;
    }

    public void setPclassific(String pclassific) {
        this.pclassific = pclassific;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getDbId() {
        return dbId;
    }

    public void setDbId(int dbId) {
        this.dbId = dbId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDevicesType() {
        return devicesType;
    }

    public void setDevicesType(String devicesType) {
        this.devicesType = devicesType;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getAdImg() {
        return adImg;
    }

    public void setAdImg(String adImg) {
        this.adImg = adImg;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getAdLink() {
        return adLink;
    }

    public void setAdLink(String adLink) {
        this.adLink = adLink;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
