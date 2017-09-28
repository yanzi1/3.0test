package com.me.data.model;

import com.yunqing.core.db.annotations.Id;
import com.yunqing.core.db.annotations.Table;

/**
 * Created by xd on 2017/6/8.
 */

@Table(name="t_welcome")
public class WelcomeRes {


    /**
     * id : 12
     * sourceType : 2
     * appId : 2
     * status : 1
     * devicesType : 1
     * sourceLink : xxx
     * isNewRecord : false
     * startTime : 1495083107000
     * effectTime : 1494996705000
     * sourceUrl : http://localhost/cloudclass-admin/upload/20170517/1495001894491.mp4
     * createDate : 2017-05-17 12:51:52
     * version : 4.0
     */
    @Id
    private int dbId;
    private String id;
    private String sourceType;
    private int appId;
    private int status;
    private int devicesType;
    private String sourceLink;
    private boolean isNewRecord;
    private long startTime;
    private long effectTime;
    private String sourceUrl;
    private String createDate;
    private String version;
    private String path;
    private String adSeconds;
    private String adVersion;
    private String type;
    private String pclassific;
    private String venderId;
    private String goodsId;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPclassific() {
        return pclassific;
    }

    public void setPclassific(String pclassific) {
        this.pclassific = pclassific;
    }

    public String getVenderId() {
        return venderId;
    }

    public void setVenderId(String venderId) {
        this.venderId = venderId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getAdSeconds() {
        return adSeconds;
    }

    public void setAdSeconds(String adSeconds) {
        this.adSeconds = adSeconds;
    }

    public String getAdVersion() {
        return adVersion;
    }

    public void setAdVersion(String adVersion) {
        this.adVersion = adVersion;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getDevicesType() {
        return devicesType;
    }

    public void setDevicesType(int devicesType) {
        this.devicesType = devicesType;
    }

    public String getSourceLink() {
        return sourceLink;
    }

    public void setSourceLink(String sourceLink) {
        this.sourceLink = sourceLink;
    }

    public boolean isIsNewRecord() {
        return isNewRecord;
    }

    public void setIsNewRecord(boolean isNewRecord) {
        this.isNewRecord = isNewRecord;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEffectTime() {
        return effectTime;
    }

    public void setEffectTime(long effectTime) {
        this.effectTime = effectTime;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
