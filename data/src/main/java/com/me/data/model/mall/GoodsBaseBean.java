package com.me.data.model.mall;

import com.me.data.cartview.base.BaseItemData;

import java.io.Serializable;

/**
 * Created by xunice on 10/03/2017.
 */

public class GoodsBaseBean extends BaseItemData implements Serializable{

    private String aliasName;
    private String auditStatus;
    private String autoStart;
    private String brandId;
    private String categoryId;
    private String classific;
    private String createdate;
    private String goodsCode;
    private String goodsDate;
    private String goodsName;
    private String goodsStatus;

    private String id;
    private String insureYear;
    private String isDelete;
    private String isDelivery;
    private String isPackage;
    private String mainPicUrlOfPc;

    private double originalPrice;
    private String shortDesc;
    private String userId;

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getAutoStart() {
        return autoStart;
    }

    public void setAutoStart(String autoStart) {
        this.autoStart = autoStart;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getClassific() {
        return classific;
    }

    public void setClassific(String classific) {
        this.classific = classific;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getGoodsDate() {
        return goodsDate;
    }

    public void setGoodsDate(String goodsDate) {
        this.goodsDate = goodsDate;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInsureYear() {
        return insureYear;
    }

    public void setInsureYear(String insureYear) {
        this.insureYear = insureYear;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public String getIsDelivery() {
        return isDelivery;
    }

    public void setIsDelivery(String isDelivery) {
        this.isDelivery = isDelivery;
    }

    public String getIsPackage() {
        return isPackage;
    }

    public void setIsPackage(String isPackage) {
        this.isPackage = isPackage;
    }

    public String getMainPicUrlOfPc() {
        return mainPicUrlOfPc;
    }

    public void setMainPicUrlOfPc(String mainPicUrlOfPc) {
        this.mainPicUrlOfPc = mainPicUrlOfPc;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGoodsStatus() {
        return goodsStatus;
    }

    public void setGoodsStatus(String goodsStatus) {
        this.goodsStatus = goodsStatus;
    }
}
