package com.me.data.model.mine;

import java.io.Serializable;

/**
 * Created by mayunfei on 17-5-2.
 */

public class AddressBean implements Serializable{


    /**
     * address : 测试测试
     * addressArea : 北京北京市东城区
     * area : 2449
     * city : 328
     * consigneeMobile : 18512365478
     * consigneeName : 的认同感的
     * id : 191
     * isDefault : 1
     * memberId : 87
     * postCode : 100000
     * province : 1
     * remark : 公司
     */

    private String address;
    private String addressArea;
    private int area;
    private int city;
    private String consigneeMobile;
    private String consigneeName;
    private int id = -1;
    private int isDefault;
    private int memberId;
    private String postCode;
    private int province;
    private String remark;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressArea() {
        return addressArea;
    }

    public void setAddressArea(String addressArea) {
        this.addressArea = addressArea;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public String getConsigneeMobile() {
        return consigneeMobile;
    }

    public void setConsigneeMobile(String consigneeMobile) {
        this.consigneeMobile = consigneeMobile;
    }

    public String getConsigneeName() {
        return consigneeName;
    }

    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public int getProvince() {
        return province;
    }

    public void setProvince(int province) {
        this.province = province;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "AddressBean{" +
                "address='" + address + '\'' +
                ", addressArea='" + addressArea + '\'' +
                ", area=" + area +
                ", city=" + city +
                ", consigneeMobile='" + consigneeMobile + '\'' +
                ", consigneeName='" + consigneeName + '\'' +
                ", id=" + id +
                ", isDefault=" + isDefault +
                ", memberId=" + memberId +
                ", postCode='" + postCode + '\'' +
                ", province=" + province +
                ", remark='" + remark + '\'' +
                '}';
    }
}
