package com.me.data.model.mine;

import java.util.List;

/**
 * Created by mayunfei on 17-5-20.
 */

public class InvoiceBean {

    /**
     * deviceType : null
     * uniqueId : null
     * appName : null
     * osType : null
     * osVersion : null
     * model : null
     * appVersion : null
     * platform : null
     * timeStamp : null
     * appId : null
     * sign : null
     * id : 3123
     * orderid : 766033
     * orderno : BE081136520054834A
     * header : 1
     * title : 12312313
     * type : 0
     * sendAddress : 北京北京市东城区2122333
     * sendExpress : null
     * sendExpressno : null
     * logisticsNo : null
     * applyTime : 2017-05-08 13:43:04
     * updateTime : null
     * reason : null
     * status : 1
     * memberId : 54
     * memberName : zhangfuzhou
     * moblie : 15098555555
     * titleId : 155
     * invoiceList : null
     * postCode : 312312
     * receiveName : 张福州
     * remark :
     * applyNum : 1
     * downUrl : null
     * typeDicName : 普通发票
     * statusDicName : 已申请
     * detailList : [{"id":3999,"invoiceId":3109,"detail":1,"money":50,"detailDicName":"培训费(￥50.0)"},{"id":4000,"invoiceId":3109,"detail":2,"money":0,"detailDicName":"图书(￥0.0)"}]
     */

    private int id;
    private int orderid;
    private String orderno;
    private int header;
    private String title;
    private int type;
    private String sendAddress;
    private String applyTime;
    private int status;
    private int memberId;
    private String memberName;
    private String moblie;
    private int titleId;
    private String postCode;
    private String receiveName;
    private String remark;
    private int applyNum;
    private String downUrl;
    private String typeDicName;
    private String statusDicName;
    private List<DetailListBean> detailList;
    private String registnumber;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public int getHeader() {
        return header;
    }

    public void setHeader(int header) {
        this.header = header;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSendAddress() {
        return sendAddress;
    }

    public void setSendAddress(String sendAddress) {
        this.sendAddress = sendAddress;
    }


    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public String getMoblie() {
        return moblie;
    }

    public void setMoblie(String moblie) {
        this.moblie = moblie;
    }

    public int getTitleId() {
        return titleId;
    }

    public void setTitleId(int titleId) {
        this.titleId = titleId;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getApplyNum() {
        return applyNum;
    }

    public void setApplyNum(int applyNum) {
        this.applyNum = applyNum;
    }

    public String getDownUrl() {
        return downUrl;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }

    public String getTypeDicName() {
        return typeDicName;
    }

    public void setTypeDicName(String typeDicName) {
        this.typeDicName = typeDicName;
    }

    public String getStatusDicName() {
        return statusDicName;
    }

    public void setStatusDicName(String statusDicName) {
        this.statusDicName = statusDicName;
    }

    public List<DetailListBean> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<DetailListBean> detailList) {
        this.detailList = detailList;
    }

    public String getRegistnumber() {
        return registnumber;
    }

    public void setRegistnumber(String registnumber) {
        this.registnumber = registnumber;
    }

    public static class DetailListBean {
        /**
         * id : 3999
         * invoiceId : 3109
         * detail : 1
         * money : 50
         * detailDicName : 培训费(￥50.0)
         */

        private int id;
        private int invoiceId;
        private int detail;
        private double money;
        private String detailDicName;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getInvoiceId() {
            return invoiceId;
        }

        public void setInvoiceId(int invoiceId) {
            this.invoiceId = invoiceId;
        }

        public int getDetail() {
            return detail;
        }

        public void setDetail(int detail) {
            this.detail = detail;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public String getDetailDicName() {
            return detailDicName;
        }

        public void setDetailDicName(String detailDicName) {
            this.detailDicName = detailDicName;
        }
    }
}
