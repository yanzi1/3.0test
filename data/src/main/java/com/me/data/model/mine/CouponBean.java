package com.me.data.model.mine;

import java.io.Serializable;

/**
 * 优惠券 实体
 * Created by mayunfei on 17-5-23.
 */

public class CouponBean implements Serializable{

    /**
     * batchId : 6
     * batchNo : 1477383246192
     * begintdate : 1477324800000
     * couponKey : 14931005496640
     * couponName : 优惠现金券scx1024
     * couponNo : QAC21064FFB89B7AB1
     * createddate : 1493100550000
     * enddate : 1509379200000
     * id : 18051
     * isDelete : 0
     * ruleId : 11
     * ruleType : 1
     * status : 1
     * typeName : 优惠券（现金）
     * value : 100
     */

    private int batchId;
    private String batchNo;
    private long begintdate;
    private String couponKey;
    private String couponName;
    private String couponNo;
    private long createddate;
    private long enddate;
    private int id;
    private int isDelete;
    private int ruleId;
    private int ruleType;
    private int status;
    private String typeName;
    private double value;
    private String showValue;

    public int getBatchId() {
        return batchId;
    }

    public void setBatchId(int batchId) {
        this.batchId = batchId;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public long getBegintdate() {
        return begintdate;
    }

    public void setBegintdate(long begintdate) {
        this.begintdate = begintdate;
    }

    public String getCouponKey() {
        return couponKey;
    }

    public void setCouponKey(String couponKey) {
        this.couponKey = couponKey;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getCouponNo() {
        return couponNo;
    }

    public void setCouponNo(String couponNo) {
        this.couponNo = couponNo;
    }

    public long getCreateddate() {
        return createddate;
    }

    public void setCreateddate(long createddate) {
        this.createddate = createddate;
    }

    public long getEnddate() {
        return enddate;
    }

    public void setEnddate(long enddate) {
        this.enddate = enddate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public int getRuleId() {
        return ruleId;
    }

    public void setRuleId(int ruleId) {
        this.ruleId = ruleId;
    }

    public int getRuleType() {
        return ruleType;
    }

    public void setRuleType(int ruleType) {
        this.ruleType = ruleType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getShowValue() {
        return showValue;
    }

    public void setShowValue(String showValue) {
        this.showValue = showValue;
    }
}
