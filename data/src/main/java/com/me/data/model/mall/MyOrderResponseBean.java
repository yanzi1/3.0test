package com.me.data.model.mall;

import java.util.List;

/**
 * Created by mayunfei on 17-5-3.
 */

public class MyOrderResponseBean {


    /**
     * count : 231
     * list : [{"isAllClassFlag":0,"orderConsigneeVO":{"address":"北京北京市东城区测试测试","areaId":2449,"cityId":328,"consigneeId":191,"consigneeMobile":"18512365478","consigneeName":"的认同感的","orderId":4685,"orderNo":"BC2718110300875696","postCode":"100000","provinceId":1},"orderDetailVOList":[{"aliasName":"2017年注会《经济法》-轻松过关一","autoStart":1,"categoryId":86,"channel":-1,"goodsCode":"1462d046131349f99e7ff7e96ccd5d30","goodsDate":1483200000000,"goodsId":468,"goodsName":"2017年注会《经济法》-轻松过关一","goodsNum":1,"goodsPrice":50,"goodsSalePrice":50,"goodsStatus":0,"goodsType":3,"id":6198,"ifCanPay":0,"insureYear":0,"isGift":0,"isPackage":0,"logisticsStatus":0,"mainImgUrl":"/goods/images/20170327/1490594557922091198.jpg","orderDetailNo":"1490609463199","orderId":4685,"orderNo":"BC2718110300875696","serviceStatus":1,"shopId":1,"shortDesc":"2017年注会《经济法》-轻松过关一"}],"orderVO":{"activityPrice":0,"actuallyPayPrice":0,"actuallyPayPriceString":"0.00","freightPrice":20,"goodsTotalPrice":50,"id":4685,"ipAddress":"172.16.208.26","isAdminOrder":0,"memberId":87,"memberName":"18519192467","orderNo":"BC2718110300875696","orderStatus":1,"orderStatusDicName":"已付款","orderTime":1490609463000,"orderTotalPrice":70,"orderTotalPriceString":"70.00","orderType":1,"orderTypeDicName":"普通订单","payStatus":1,"payTime":1490609464000,"payment":1,"paymentDicName":"在线支付","remainingTimeString":"已过期","source":"1"}}]
     */

    private int count;
    private List<ListBean> list;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * isAllClassFlag : 0
         * orderConsigneeVO : {"address":"北京北京市东城区测试测试","areaId":2449,"cityId":328,"consigneeId":191,"consigneeMobile":"18512365478","consigneeName":"的认同感的","orderId":4685,"orderNo":"BC2718110300875696","postCode":"100000","provinceId":1}
         * orderDetailVOList : [{"aliasName":"2017年注会《经济法》-轻松过关一","autoStart":1,"categoryId":86,"channel":-1,"goodsCode":"1462d046131349f99e7ff7e96ccd5d30","goodsDate":1483200000000,"goodsId":468,"goodsName":"2017年注会《经济法》-轻松过关一","goodsNum":1,"goodsPrice":50,"goodsSalePrice":50,"goodsStatus":0,"goodsType":3,"id":6198,"ifCanPay":0,"insureYear":0,"isGift":0,"isPackage":0,"logisticsStatus":0,"mainImgUrl":"/goods/images/20170327/1490594557922091198.jpg","orderDetailNo":"1490609463199","orderId":4685,"orderNo":"BC2718110300875696","serviceStatus":1,"shopId":1,"shortDesc":"2017年注会《经济法》-轻松过关一"}]
         * orderVO : {"activityPrice":0,"actuallyPayPrice":0,"actuallyPayPriceString":"0.00","freightPrice":20,"goodsTotalPrice":50,"id":4685,"ipAddress":"172.16.208.26","isAdminOrder":0,"memberId":87,"memberName":"18519192467","orderNo":"BC2718110300875696","orderStatus":1,"orderStatusDicName":"已付款","orderTime":1490609463000,"orderTotalPrice":70,"orderTotalPriceString":"70.00","orderType":1,"orderTypeDicName":"普通订单","payStatus":1,"payTime":1490609464000,"payment":1,"paymentDicName":"在线支付","remainingTimeString":"已过期","source":"1"}
         */
        private int isAllClassFlag;
        private OrderConsigneeVOBean orderConsigneeVO;
        private OrderVOBean orderVO;
        private List<OrderDetailItemBean> orderDetailVOList;

        public int getIsAllClassFlag() {
            return isAllClassFlag;
        }

        public void setIsAllClassFlag(int isAllClassFlag) {
            this.isAllClassFlag = isAllClassFlag;
        }

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

        public List<OrderDetailItemBean> getOrderDetailVOList() {
            return orderDetailVOList;
        }

        public void setOrderDetailVOList(List<OrderDetailItemBean> orderDetailVOList) {
            this.orderDetailVOList = orderDetailVOList;
        }

        public static class OrderConsigneeVOBean {
            /**
             * address : 北京北京市东城区测试测试
             * areaId : 2449
             * cityId : 328
             * consigneeId : 191
             * consigneeMobile : 18512365478
             * consigneeName : 的认同感的
             * orderId : 4685
             * orderNo : BC2718110300875696
             * postCode : 100000
             * provinceId : 1
             */

            private String address;
            private int areaId;
            private int cityId;
            private int consigneeId;
            private String consigneeMobile;
            private String consigneeName;
            private int orderId;
            private String orderNo;
            private String postCode;
            private int provinceId;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public int getAreaId() {
                return areaId;
            }

            public void setAreaId(int areaId) {
                this.areaId = areaId;
            }

            public int getCityId() {
                return cityId;
            }

            public void setCityId(int cityId) {
                this.cityId = cityId;
            }

            public int getConsigneeId() {
                return consigneeId;
            }

            public void setConsigneeId(int consigneeId) {
                this.consigneeId = consigneeId;
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

            public String getPostCode() {
                return postCode;
            }

            public void setPostCode(String postCode) {
                this.postCode = postCode;
            }

            public int getProvinceId() {
                return provinceId;
            }

            public void setProvinceId(int provinceId) {
                this.provinceId = provinceId;
            }
        }


    }
}
