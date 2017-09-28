package com.me.data.model.mine;

import com.me.data.model.mall.OrderDetailItemBean;
import com.me.data.model.mall.OrderVOBean;

import java.util.List;

/**
 * 我的发票
 * Created by mayunfei on 17-5-20.
 */

public class MyInvoiceResponseBean {


    /**
     * pageNo : 1
     * pageSize : -1
     * count : 125
     * first : 1
     * last : 6
     * prev : 1
     * next : 2
     * firstPage : true
     * lastPage : false
     * list : [{"orderVO":{"id":766034,"orderNo":"BE08134418005488E7","memberId":54,"memberName":"zhangfuzhou","source":"1","orderType":1,"orderTotalPrice":120,"actuallyPayPrice":0,"goodsTotalPrice":200,"freightPrice":0,"isUseCoupon":null,"isInvoice":null,"isInvoiceDicName":null,"orderStatus":1,"payStatus":1,"isSplit":null,"userRemark":null,"orderRemark":null,"isAdminOrder":0,"orderTime":"2017-05-08 13:44:19","payTime":"2017-05-08 13:44:20","returnType":null,"activityPrice":null,"orderStatusDicName":null,"orderTypeDicName":"普通订单","payStatusDicName":null,"consigneeMobile":null,"orderTimeStart":null,"orderTimeEnd":null,"payTimeStart":null,"payTimeEnd":null,"consigneeName":null,"consignee_phone":null,"payment":1,"paymentDicName":"在线支付","customerNo":null,"fromOrderNo":null,"goodsType":null,"actuallyPayPriceString":"0.00","freightPriceString":null,"goodsTotalPriceString":null,"orderTotalPriceString":"120.00","fromDetailList":null,"targetDetailList":null,"activityPriceString":null,"remainingTimeString":null,"ipAddress":null},"orderDetailVOList":[{"id":1707773,"orderId":766034,"orderNo":"BE08134418005488E7","orderDetailNo":"1494222258643","shopId":1,"categoryId":123,"goodsId":138,"goodsType":2,"goodsCode":"82240d5f5ccf44c89b22b92886c5d892","goodsName":"专题：增值税发票管理全攻略","goodsSalePrice":200,"goodsPrice":200,"goodsNum":1,"logisticsStatus":2,"goodsStatus":0,"isGift":0,"mainImgUrl":"/goods/images/20161222/1482391861336071713.png","goodsDate":"2016-01-01 00:00:00","insureYear":0,"shortDesc":"","categoryName":null,"goodsStatusDicName":null,"logisticsStatusDicName":null,"goodsTypeDicName":null,"returnNum":null,"payTimeStr":null,"orderDetailPaymentDicName":null,"payTime":null,"shopName":null,"openLessonStatus":null,"ifCanPay":0,"returnId":null,"isPackage":1,"autoStart":1,"aliasName":"","channel":-1,"goodsPriceString":null,"goodsSalePriceString":null,"serviceStatus":null,"canReturnNum":null}],"orderConsigneeVO":null,"ordersDetailList":null,"orderUpgradeVO":null,"orderDetailVO":null,"toCourseList":[],"toCourse":{"id":null,"orderId":null,"orderNum":null,"shopId":null,"categoryId":null,"goodsId":null,"goodsType":null,"goodsCode":null,"goodsName":null,"salePrice":null,"categoryName":null,"goodsImg":null},"memberName":null,"upgradeDtoList":[],"orderPaymentList":null,"upgradeReturnList":null,"orderPrice":null,"upgradeProcessMoney":null,"orderCoupon":null,"isAllClassFlag":null,"orderSpecialCoupon":null,"invoice":null,"isInvoiceCancelTwice":0,"customer":null,"orderLogisticsVOList":null}]
     * orderBy :
     * funcName : page
     * funcParam :
     * message :
     * disabled : true
     * firstResult : 0
     * html : <ul>
     <li class="disabled"><a href="javascript:">&#171; 上一页</a></li>
     <li class="active"><a href="javascript:">1</a></li>
     <li><a href="javascript:" onclick="page(2,-1,'');">2</a></li>
     <li><a href="javascript:" onclick="page(3,-1,'');">3</a></li>
     <li><a href="javascript:" onclick="page(4,-1,'');">4</a></li>
     <li><a href="javascript:" onclick="page(5,-1,'');">5</a></li>
     <li><a href="javascript:" onclick="page(6,-1,'');">6</a></li>
     <li><a href="javascript:" onclick="page(2,-1,'');">下一页 &#187;</a></li>
     <li class="disabled controls"><a href="javascript:">当前 第<input type="text" value="1" onkeypress="var c=event.keyCode||event.which||event.charCode;if(c==13)page(this.value,-1,'');" onclick="this.select();"/> 页 / 每页 <input type="text" value="-1" onkeypress="var c=event.keyCode||event.which||event.charCode;if(c==13)page(1,this.value,'');" onclick="this.select();"/> 条，共 125 条</a></li>
     </ul>
     <div style="clear:both;"></div>
     * totalPage : 6
     * notCount : false
     * maxResults : -1
     */

    private int pageNo;
    private int pageSize;
    private int count;
    private int first;
    private int last;
    private int prev;
    private int next;
    private boolean firstPage;
    private boolean lastPage;
    private String orderBy;
    private String funcName;
    private String funcParam;
    private String message;
    private boolean disabled;
    private int firstResult;
    private String html;
    private int totalPage;
    private boolean notCount;
    private int maxResults;
    private List<ListBean> list;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public int getLast() {
        return last;
    }

    public void setLast(int last) {
        this.last = last;
    }

    public int getPrev() {
        return prev;
    }

    public void setPrev(int prev) {
        this.prev = prev;
    }

    public int getNext() {
        return next;
    }

    public void setNext(int next) {
        this.next = next;
    }

    public boolean isFirstPage() {
        return firstPage;
    }

    public void setFirstPage(boolean firstPage) {
        this.firstPage = firstPage;
    }

    public boolean isLastPage() {
        return lastPage;
    }

    public void setLastPage(boolean lastPage) {
        this.lastPage = lastPage;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getFuncName() {
        return funcName;
    }

    public void setFuncName(String funcName) {
        this.funcName = funcName;
    }

    public String getFuncParam() {
        return funcParam;
    }

    public void setFuncParam(String funcParam) {
        this.funcParam = funcParam;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public int getFirstResult() {
        return firstResult;
    }

    public void setFirstResult(int firstResult) {
        this.firstResult = firstResult;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public boolean isNotCount() {
        return notCount;
    }

    public void setNotCount(boolean notCount) {
        this.notCount = notCount;
    }

    public int getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * orderVO : {"id":766034,"orderNo":"BE08134418005488E7","memberId":54,"memberName":"zhangfuzhou","source":"1","orderType":1,"orderTotalPrice":120,"actuallyPayPrice":0,"goodsTotalPrice":200,"freightPrice":0,"isUseCoupon":null,"isInvoice":null,"isInvoiceDicName":null,"orderStatus":1,"payStatus":1,"isSplit":null,"userRemark":null,"orderRemark":null,"isAdminOrder":0,"orderTime":"2017-05-08 13:44:19","payTime":"2017-05-08 13:44:20","returnType":null,"activityPrice":null,"orderStatusDicName":null,"orderTypeDicName":"普通订单","payStatusDicName":null,"consigneeMobile":null,"orderTimeStart":null,"orderTimeEnd":null,"payTimeStart":null,"payTimeEnd":null,"consigneeName":null,"consignee_phone":null,"payment":1,"paymentDicName":"在线支付","customerNo":null,"fromOrderNo":null,"goodsType":null,"actuallyPayPriceString":"0.00","freightPriceString":null,"goodsTotalPriceString":null,"orderTotalPriceString":"120.00","fromDetailList":null,"targetDetailList":null,"activityPriceString":null,"remainingTimeString":null,"ipAddress":null}
         * orderDetailVOList : [{"id":1707773,"orderId":766034,"orderNo":"BE08134418005488E7","orderDetailNo":"1494222258643","shopId":1,"categoryId":123,"goodsId":138,"goodsType":2,"goodsCode":"82240d5f5ccf44c89b22b92886c5d892","goodsName":"专题：增值税发票管理全攻略","goodsSalePrice":200,"goodsPrice":200,"goodsNum":1,"logisticsStatus":2,"goodsStatus":0,"isGift":0,"mainImgUrl":"/goods/images/20161222/1482391861336071713.png","goodsDate":"2016-01-01 00:00:00","insureYear":0,"shortDesc":"","categoryName":null,"goodsStatusDicName":null,"logisticsStatusDicName":null,"goodsTypeDicName":null,"returnNum":null,"payTimeStr":null,"orderDetailPaymentDicName":null,"payTime":null,"shopName":null,"openLessonStatus":null,"ifCanPay":0,"returnId":null,"isPackage":1,"autoStart":1,"aliasName":"","channel":-1,"goodsPriceString":null,"goodsSalePriceString":null,"serviceStatus":null,"canReturnNum":null}]
         * orderConsigneeVO : null
         * ordersDetailList : null
         * orderUpgradeVO : null
         * orderDetailVO : null
         * toCourseList : []
         * toCourse : {"id":null,"orderId":null,"orderNum":null,"shopId":null,"categoryId":null,"goodsId":null,"goodsType":null,"goodsCode":null,"goodsName":null,"salePrice":null,"categoryName":null,"goodsImg":null}
         * memberName : null
         * upgradeDtoList : []
         * orderPaymentList : null
         * upgradeReturnList : null
         * orderPrice : null
         * upgradeProcessMoney : null
         * orderCoupon : null
         * isAllClassFlag : null
         * orderSpecialCoupon : null
         * invoice : null
         * isInvoiceCancelTwice : 0
         * customer : null
         * orderLogisticsVOList : null
         */

        private InvoiceBean invoice;
        private OrderVOBean orderVO;
        private int isInvoiceCancelTwice;
        private List<OrderDetailItemBean> orderDetailVOList;

        public InvoiceBean getInvoice() {
            return invoice;
        }

        public void setInvoice(InvoiceBean invoice) {
            this.invoice = invoice;
        }

        public OrderVOBean getOrderVO() {
            return orderVO;
        }

        public void setOrderVO(OrderVOBean orderVO) {
            this.orderVO = orderVO;
        }


        public int getIsInvoiceCancelTwice() {
            return isInvoiceCancelTwice;
        }

        public void setIsInvoiceCancelTwice(int isInvoiceCancelTwice) {
            this.isInvoiceCancelTwice = isInvoiceCancelTwice;
        }

        public List<OrderDetailItemBean> getOrderDetailVOList() {
            return orderDetailVOList;
        }

        public void setOrderDetailVOList(List<OrderDetailItemBean> orderDetailVOList) {
            this.orderDetailVOList = orderDetailVOList;
        }



    }
}
