package com.me.data.model.mall;

import java.io.Serializable;

/**
 * Created by xunice on 10/03/2017.
 */

public class GoodsInfoBean implements Serializable{
    private String id;
    private String goodsId;
    private String popularLevel;
    private double salePrice;
    private String shopId;
    private String upDownStatus;
    private String upTime;
    private String updatedate;
    private String userId;
    private String isFrontSale;
    private String createdate;

    private GoodsInfoDetailBean goodsDetail;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getPopularLevel() {
        return popularLevel;
    }

    public void setPopularLevel(String popularLevel) {
        this.popularLevel = popularLevel;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getUpDownStatus() {
        return upDownStatus;
    }

    public void setUpDownStatus(String upDownStatus) {
        this.upDownStatus = upDownStatus;
    }

    public String getUpTime() {
        return upTime;
    }

    public void setUpTime(String upTime) {
        this.upTime = upTime;
    }

    public String getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(String updatedate) {
        this.updatedate = updatedate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIsFrontSale() {
        return isFrontSale;
    }

    public void setIsFrontSale(String isFrontSale) {
        this.isFrontSale = isFrontSale;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public GoodsInfoDetailBean getGoodsDetail() {
        return goodsDetail;
    }

    public void setGoodsDetail(GoodsInfoDetailBean goodsDetail) {
        this.goodsDetail = goodsDetail;
    }
}
