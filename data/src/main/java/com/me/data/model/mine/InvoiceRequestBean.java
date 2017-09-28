package com.me.data.model.mine;

import com.me.data.common.Constants;

import java.util.List;

/**
 * Created by mayunfei on 17-6-12.
 */

public class InvoiceRequestBean {


    /**
     * orderid : 766040
     * orderno : BE101519200054A35D
     * header : 1
     * title : 123
     * type : 1
     * sendAddress : null
     * sendExpress : null
     * sendExpressno : null
     * logisticsNo : null
     * applyTime : 2017-05-10 16:28:31
     * updateTime : null
     * reason : null
     * status : 1
     * memberId : 54
     * memberName : zhangfuzhou
     * moblie : 15022222222
     * titleId : 150
     * invoiceList : null
     * postCode : null
     * receiveName : null
     * remark : 123
     * applyNum : 1
     * downUrl : null
     * batchNo : null
     */

    private long orderid;
    private String orderno;
    private int header = Constants.INVOICE_HEADER_NO; //未选抬头
    private String title;
    private int type = Constants.INVOICE_TYPE_NO; //电子发票还是纸质发票
    private String sendAddress;
    private int memberId;
    private String moblie;
    private long titleId = -1;
    private List<InvoiceBookAndCourseMoneyBean.MoneyListBean> invoiceList;
    private String postCode;
    private String receiveName;
    private String remark;
    private int applyNum;
    private String registNumber;

    public long getOrderid() {
        return orderid;
    }

    public void setOrderid(long orderid) {
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

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getMoblie() {
        return moblie;
    }

    public void setMoblie(String moblie) {
        this.moblie = moblie;
    }

    public long getTitleId() {
        return titleId;
    }

    public void setTitleId(long titleId) {
        this.titleId = titleId;
    }

    public List<InvoiceBookAndCourseMoneyBean.MoneyListBean> getInvoiceList() {
        return invoiceList;
    }

    public void setInvoiceList(List<InvoiceBookAndCourseMoneyBean.MoneyListBean> invoiceList) {
        this.invoiceList = invoiceList;
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

    public String getRegistNumber() {
        return registNumber;
    }

    public void setRegistNumber(String registNumber) {
        this.registNumber = registNumber;
    }
}
