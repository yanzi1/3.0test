package com.me.data.model.mall;

import java.util.List;

/**
 * Created by mayunfei on 17-5-8.
 */

public class OrderDetailResponseBean {


    /**
     * orderConsigneeVO : {"address":"北京北京市东城区213123","areaId":2449,"cityId":328,"consigneeId":169,"consigneeMobile":"15099999999","consigneeName":"123123","orderId":4826,"orderNo":"BD251602360076702E","postCode":"2313","provinceId":1}
     * orderLogistics : []
     * orderPaymentVOList : [{"orderId":4826,"orderNo":"BD251602360076702E","payMoney":1570,"payMoneyString":"1570.00","payName":"现金账户","payment":2}]
     * orderVO : {"activityPrice":0,"activityPriceString":"0.00","actuallyPayPrice":0,"actuallyPayPriceString":"0.00","freightPrice":20,"freightPriceString":"20.00","fromOrderNo":"","goodsTotalPrice":1550,"goodsTotalPriceString":"1550.00","id":4826,"ipAddress":"172.16.208.104","isAdminOrder":0,"memberId":76,"memberName":"15098500903","orderNo":"BD251602360076702E","orderStatus":1,"orderStatusDicName":"已付款","orderTime":1493107356000,"orderTotalPrice":1570,"orderTotalPriceString":"1570.00","orderType":1,"orderTypeDicName":"普通订单","payStatus":1,"payStatusDicName":"已支付","payTime":1493107357000,"payment":1,"paymentDicName":"在线支付","source":"1"}
     * superVO : {"detailList":[{"aliasName":"你好","autoStart":1,"canReturnNum":0,"categoryId":97,"channel":-1,"goodsCode":"0770f873825a4abfa65bc0c22c192ed7","goodsDate":1483200000000,"goodsId":277,"goodsName":"课程-换课","goodsNum":1,"goodsPrice":1500,"goodsPriceString":"1500.00","goodsSalePrice":1500,"goodsSalePriceString":"1500.00","goodsStatus":0,"goodsStatusDicName":"已上线","goodsType":2,"goodsTypeDicName":"课程","id":6381,"ifCanPay":0,"insureYear":0,"isGift":0,"isPackage":0,"logisticsStatus":2,"logisticsStatusDicName":"已开课","mainImgUrl":"/goods/images/20161130/1480499210634071084.jpg","orderDetailNo":"1493107356459","orderId":4826,"orderNo":"BD251602360076702E","returnNum":0,"shopId":1,"shortDesc":"你好"},{"aliasName":"2017年注会《经济法》-轻松过关一","autoStart":1,"canReturnNum":0,"categoryId":86,"channel":-1,"goodsCode":"1462d046131349f99e7ff7e96ccd5d30","goodsDate":1483200000000,"goodsId":468,"goodsName":"2017年注会《经济法》-轻松过关一","goodsNum":1,"goodsPrice":50,"goodsPriceString":"50.00","goodsSalePrice":50,"goodsSalePriceString":"50.00","goodsStatus":0,"goodsStatusDicName":"预售","goodsType":3,"goodsTypeDicName":"图书","id":6382,"ifCanPay":0,"insureYear":0,"isGift":0,"isPackage":0,"logisticsStatus":0,"logisticsStatusDicName":"待发货","mainImgUrl":"/goods/images/20170327/1490594557922091198.jpg","orderDetailNo":"1493107356467","orderId":4826,"orderNo":"BD251602360076702E","returnNum":0,"serviceStatus":1,"shopId":1,"shortDesc":"2017年注会《经济法》-轻松过关一"}],"isAllClassFlag":0,"sumGoodsNum":2}
     */

    private OrderConsigneeVOBean orderConsigneeVO;
    private OrderVOBean orderVO;
    private SuperVOBean superVO;
    private List<LogisticsBean> orderLogistics;
    private List<OrderPaymentVOListBean> orderPaymentVOList;

    public OrderConsigneeVOBean getOrderConsigneeVO() {
        return orderConsigneeVO;
    }

    public void setOrderConsigneeVO(OrderConsigneeVOBean orderConsigneeVO) {
        this.orderConsigneeVO = orderConsigneeVO;
    }

    public OrderVOBean getOrderVO() {
        return orderVO;
    }

    public void setOrderVO(OrderVOBean orderVO) {
        this.orderVO = orderVO;
    }

    public SuperVOBean getSuperVO() {
        return superVO;
    }

    public void setSuperVO(SuperVOBean superVO) {
        this.superVO = superVO;
    }

    public List<LogisticsBean> getOrderLogistics() {
        return orderLogistics;
    }

    public void setOrderLogistics(List<LogisticsBean> orderLogistics) {
        this.orderLogistics = orderLogistics;
    }

    public List<OrderPaymentVOListBean> getOrderPaymentVOList() {
        return orderPaymentVOList;
    }

    public void setOrderPaymentVOList(List<OrderPaymentVOListBean> orderPaymentVOList) {
        this.orderPaymentVOList = orderPaymentVOList;
    }


    public static class SuperVOBean {
        /**
         * detailList : [{"aliasName":"你好","autoStart":1,"canReturnNum":0,"categoryId":97,"channel":-1,"goodsCode":"0770f873825a4abfa65bc0c22c192ed7","goodsDate":1483200000000,"goodsId":277,"goodsName":"课程-换课","goodsNum":1,"goodsPrice":1500,"goodsPriceString":"1500.00","goodsSalePrice":1500,"goodsSalePriceString":"1500.00","goodsStatus":0,"goodsStatusDicName":"已上线","goodsType":2,"goodsTypeDicName":"课程","id":6381,"ifCanPay":0,"insureYear":0,"isGift":0,"isPackage":0,"logisticsStatus":2,"logisticsStatusDicName":"已开课","mainImgUrl":"/goods/images/20161130/1480499210634071084.jpg","orderDetailNo":"1493107356459","orderId":4826,"orderNo":"BD251602360076702E","returnNum":0,"shopId":1,"shortDesc":"你好"},{"aliasName":"2017年注会《经济法》-轻松过关一","autoStart":1,"canReturnNum":0,"categoryId":86,"channel":-1,"goodsCode":"1462d046131349f99e7ff7e96ccd5d30","goodsDate":1483200000000,"goodsId":468,"goodsName":"2017年注会《经济法》-轻松过关一","goodsNum":1,"goodsPrice":50,"goodsPriceString":"50.00","goodsSalePrice":50,"goodsSalePriceString":"50.00","goodsStatus":0,"goodsStatusDicName":"预售","goodsType":3,"goodsTypeDicName":"图书","id":6382,"ifCanPay":0,"insureYear":0,"isGift":0,"isPackage":0,"logisticsStatus":0,"logisticsStatusDicName":"待发货","mainImgUrl":"/goods/images/20170327/1490594557922091198.jpg","orderDetailNo":"1493107356467","orderId":4826,"orderNo":"BD251602360076702E","returnNum":0,"serviceStatus":1,"shopId":1,"shortDesc":"2017年注会《经济法》-轻松过关一"}]
         * isAllClassFlag : 0
         * sumGoodsNum : 2
         */

        private int isAllClassFlag;
        private int sumGoodsNum;
        private List<OrderDetailItemBean> detailList;

        public int getIsAllClassFlag() {
            return isAllClassFlag;
        }

        public void setIsAllClassFlag(int isAllClassFlag) {
            this.isAllClassFlag = isAllClassFlag;
        }

        public int getSumGoodsNum() {
            return sumGoodsNum;
        }

        public void setSumGoodsNum(int sumGoodsNum) {
            this.sumGoodsNum = sumGoodsNum;
        }

        public List<OrderDetailItemBean> getDetailList() {
            return detailList;
        }

        public void setDetailList(List<OrderDetailItemBean> detailList) {
            this.detailList = detailList;
        }
    }

    public static class OrderPaymentVOListBean {
        /**
         * orderId : 4826
         * orderNo : BD251602360076702E
         * payMoney : 1570
         * payMoneyString : 1570.00
         * payName : 现金账户
         * payment : 2
         */

        private int orderId;
        private String orderNo;
        private double payMoney;
        private String payMoneyString;
        private String payName;
        private int payment;

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public double getPayMoney() {
            return payMoney;
        }

        public void setPayMoney(double payMoney) {
            this.payMoney = payMoney;
        }

        public String getPayMoneyString() {
            return payMoneyString;
        }

        public void setPayMoneyString(String payMoneyString) {
            this.payMoneyString = payMoneyString;
        }

        public String getPayName() {
            return payName;
        }

        public void setPayName(String payName) {
            this.payName = payName;
        }

        public int getPayment() {
            return payment;
        }

        public void setPayment(int payment) {
            this.payment = payment;
        }
    }
}
