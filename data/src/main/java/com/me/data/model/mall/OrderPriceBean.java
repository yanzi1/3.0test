package com.me.data.model.mall;

import com.me.data.model.mine.AddressBean;

import java.io.Serializable;

/**
 * Created by xunice on 22/05/2017.
 */

public class OrderPriceBean implements Serializable {

    private String goodsNum;
    private Double goodsPrice;
    private Double freightPrice;
    private Double activityPrice;
    private String couponNum;
    private Double couponPrice;
    private String cashCouponNum;
    private Double cashCouponPrice;
    private Double orderPrice;
    private Double payPrice;
    private Double cashPrice;
    private Double learningPrice;
    private boolean isCan;
    private String noCanMsg;
    private String studyCanUse;

    private String cashAccountCanUse;
    private String grade;

    private AddressBean addressInfo;

    public String getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(String goodsNum) {
        this.goodsNum = goodsNum;
    }

    public Double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Double getFreightPrice() {
        return freightPrice;
    }

    public void setFreightPrice(Double freightPrice) {
        this.freightPrice = freightPrice;
    }

    public Double getActivityPrice() {
        return activityPrice;
    }

    public void setActivityPrice(Double activityPrice) {
        this.activityPrice = activityPrice;
    }

    public String getCouponNum() {
        return couponNum;
    }

    public void setCouponNum(String couponNum) {
        this.couponNum = couponNum;
    }

    public Double getCouponPrice() {
        return couponPrice;
    }

    public void setCouponPrice(Double couponPrice) {
        this.couponPrice = couponPrice;
    }

    public String getCashCouponNum() {
        return cashCouponNum;
    }

    public void setCashCouponNum(String cashCouponNum) {
        this.cashCouponNum = cashCouponNum;
    }

    public Double getCashCouponPrice() {
        return cashCouponPrice;
    }

    public void setCashCouponPrice(Double cashCouponPrice) {
        this.cashCouponPrice = cashCouponPrice;
    }

    public Double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public Double getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(Double payPrice) {
        this.payPrice = payPrice;
    }

    public Double getCashPrice() {
        return cashPrice;
    }

    public void setCashPrice(Double cashPrice) {
        this.cashPrice = cashPrice;
    }

    public Double getLearningPrice() {
        return learningPrice;
    }

    public void setLearningPrice(Double learningPrice) {
        this.learningPrice = learningPrice;
    }

    public boolean isCan() {
        return isCan;
    }

    public void setCan(boolean can) {
        isCan = can;
    }

    public String getNoCanMsg() {
        return noCanMsg;
    }

    public void setNoCanMsg(String noCanMsg) {
        this.noCanMsg = noCanMsg;
    }

    public String getStudyCanUse() {
        return studyCanUse;
    }

    public void setStudyCanUse(String studyCanUse) {
        this.studyCanUse = studyCanUse;
    }

    public String getCashAccountCanUse() {
        return cashAccountCanUse;
    }

    public void setCashAccountCanUse(String cashAccountCanUse) {
        this.cashAccountCanUse = cashAccountCanUse;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public AddressBean getAddressInfo() {
        return addressInfo;
    }

    public void setAddressInfo(AddressBean addressInfo) {
        this.addressInfo = addressInfo;
    }
}
