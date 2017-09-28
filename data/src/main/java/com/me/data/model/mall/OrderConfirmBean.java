package com.me.data.model.mall;

import java.io.Serializable;

/**
 * Created by xunice on 10/03/2017.
 */

public class OrderConfirmBean implements Serializable{

    private String consigneeId;
    private String payment;
    private String couponId;
    private String cashCouponId;
    private String learningPrice;
    private String cashAccountPrice;
    private String orderRemark;
    private String ipAddress;
    private String jobNum;

    public String getConsigneeId() {
        return consigneeId;
    }

    public void setConsigneeId(String consigneeId) {
        this.consigneeId = consigneeId;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getCashCouponId() {
        return cashCouponId;
    }

    public void setCashCouponId(String cashCouponId) {
        this.cashCouponId = cashCouponId;
    }

    public String getOrderRemark() {
        return orderRemark;
    }

    public void setOrderRemark(String orderRemark) {
        this.orderRemark = orderRemark;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getJobNum() {
        return jobNum;
    }

    public void setJobNum(String jobNum) {
        this.jobNum = jobNum;
    }

    public String getLearningPrice() {
        return learningPrice;
    }

    public void setLearningPrice(String learningPrice) {
        this.learningPrice = learningPrice;
    }

    public String getCashAccountPrice() {
        return cashAccountPrice;
    }

    public void setCashAccountPrice(String cashAccountPrice) {
        this.cashAccountPrice = cashAccountPrice;
    }
}
