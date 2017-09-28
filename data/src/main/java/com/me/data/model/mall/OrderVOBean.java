package com.me.data.model.mall;

import java.io.Serializable;

/**
 * Created by mayunfei on 17-5-19.
 */

public class OrderVOBean implements Serializable{

    /**
     * activityPrice : 0
     * activityPriceString : 0.00
     * actuallyPayPrice : 0
     * actuallyPayPriceString : 0.00
     * freightPrice : 20
     * freightPriceString : 20.00
     * fromOrderNo :
     * goodsTotalPrice : 1550
     * goodsTotalPriceString : 1550.00
     * id : 4826
     * ipAddress : 172.16.208.104
     * isAdminOrder : 0
     * memberId : 76
     * memberName : 15098500903
     * orderNo : BD251602360076702E
     * orderStatus : 1
     * orderStatusDicName : 已付款
     * orderTime : 1493107356000
     * orderTotalPrice : 1570
     * orderTotalPriceString : 1570.00
     * orderType : 1
     * orderTypeDicName : 普通订单
     * payStatus : 1
     * payStatusDicName : 已支付
     * payTime : 1493107357000
     * payment : 1
     * paymentDicName : 在线支付
     * source : 1
     */

    private double activityPrice;
    private String activityPriceString;
    private double actuallyPayPrice;
    private String actuallyPayPriceString;
    private double freightPrice;
    private String freightPriceString;
    private String fromOrderNo;
    private double goodsTotalPrice;
    private String goodsTotalPriceString;
    private long id;
    private String ipAddress;
    private int isAdminOrder;
    private int memberId;
    private String memberName;
    private String orderNo;
    private int orderStatus;
    private String orderStatusDicName;
    private String orderTime;
    private double orderTotalPrice;
    private String orderTotalPriceString;
    private int orderType;
    private String orderTypeDicName;
    private int payStatus;
    private String payStatusDicName;
    private long payTime;
    private int payment;
    private String paymentDicName;
    private String source;
    private String remainingTimeString;

    public double getActivityPrice() {
        return activityPrice;
    }

    public void setActivityPrice(double activityPrice) {
        this.activityPrice = activityPrice;
    }

    public String getActivityPriceString() {
        return activityPriceString;
    }

    public void setActivityPriceString(String activityPriceString) {
        this.activityPriceString = activityPriceString;
    }

    public double getActuallyPayPrice() {
        return actuallyPayPrice;
    }

    public void setActuallyPayPrice(double actuallyPayPrice) {
        this.actuallyPayPrice = actuallyPayPrice;
    }

    public String getActuallyPayPriceString() {
        return actuallyPayPriceString;
    }

    public void setActuallyPayPriceString(String actuallyPayPriceString) {
        this.actuallyPayPriceString = actuallyPayPriceString;
    }

    public double getFreightPrice() {
        return freightPrice;
    }

    public void setFreightPrice(double freightPrice) {
        this.freightPrice = freightPrice;
    }

    public String getFreightPriceString() {
        return freightPriceString;
    }

    public void setFreightPriceString(String freightPriceString) {
        this.freightPriceString = freightPriceString;
    }

    public String getFromOrderNo() {
        return fromOrderNo;
    }

    public void setFromOrderNo(String fromOrderNo) {
        this.fromOrderNo = fromOrderNo;
    }

    public double getGoodsTotalPrice() {
        return goodsTotalPrice;
    }

    public void setGoodsTotalPrice(double goodsTotalPrice) {
        this.goodsTotalPrice = goodsTotalPrice;
    }

    public String getGoodsTotalPriceString() {
        return goodsTotalPriceString;
    }

    public void setGoodsTotalPriceString(String goodsTotalPriceString) {
        this.goodsTotalPriceString = goodsTotalPriceString;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getIsAdminOrder() {
        return isAdminOrder;
    }

    public void setIsAdminOrder(int isAdminOrder) {
        this.isAdminOrder = isAdminOrder;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatusDicName() {
        return orderStatusDicName;
    }

    public void setOrderStatusDicName(String orderStatusDicName) {
        this.orderStatusDicName = orderStatusDicName;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public double getOrderTotalPrice() {
        return orderTotalPrice;
    }

    public void setOrderTotalPrice(double orderTotalPrice) {
        this.orderTotalPrice = orderTotalPrice;
    }

    public String getOrderTotalPriceString() {
        return orderTotalPriceString;
    }

    public void setOrderTotalPriceString(String orderTotalPriceString) {
        this.orderTotalPriceString = orderTotalPriceString;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public String getOrderTypeDicName() {
        return orderTypeDicName;
    }

    public void setOrderTypeDicName(String orderTypeDicName) {
        this.orderTypeDicName = orderTypeDicName;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public String getPayStatusDicName() {
        return payStatusDicName;
    }

    public void setPayStatusDicName(String payStatusDicName) {
        this.payStatusDicName = payStatusDicName;
    }

    public long getPayTime() {
        return payTime;
    }

    public void setPayTime(long payTime) {
        this.payTime = payTime;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public String getPaymentDicName() {
        return paymentDicName;
    }

    public void setPaymentDicName(String paymentDicName) {
        this.paymentDicName = paymentDicName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getRemainingTimeString() {
        return remainingTimeString;
    }

    public void setRemainingTimeString(String remainingTimeString) {
        this.remainingTimeString = remainingTimeString;
    }
}
