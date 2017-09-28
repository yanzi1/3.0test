package com.me.data.model.mall;

import java.io.Serializable;
import java.util.List;

/**
 * 物流信息
 * Created by mayunfei on 17-5-26.
 */

public class LogisticsBean implements Serializable{

    /**
     * logisticsMessages : [{"desc":"[北京市] [北京业务部]的东奥已收件 电话:暂无","address":"北京业务部","date":"2017-05-20 15:55:53"},{"desc":"[北京市] 快件到达 [北京业务部]","address":"北京业务部","date":"2017-05-20 20:09:13"},{"desc":"[北京市] 快件离开 [北京业务部]已发往[北京]","address":"北京业务部","date":"2017-05-20 20:09:39"},{"desc":"[北京市] 快件到达 [北京]","address":"北京","date":"2017-05-20 20:58:36"},{"desc":"[北京市] 快件离开 [北京]已发往[沈阳中转]","address":"北京","date":"2017-05-20 21:00:24"},{"desc":"[沈阳市] 快件到达 [沈阳中转]","address":"沈阳中转","date":"2017-05-21 16:03:55"},{"desc":"[沈阳市] 快件离开 [沈阳中转]已发往[沈阳江东分部]","address":"沈阳中转","date":"2017-05-21 16:28:43"},{"desc":"[沈阳市] 快件到达 [沈阳江东分部]","address":"沈阳江东分部","date":"2017-05-22 07:43:16"},{"desc":"[沈阳市] [沈阳江东分部]的吴俊刚正在第1次派件 电话:15566190535 请保持电话畅通、耐心等待","address":"沈阳江东分部","date":"2017-05-22 08:10:39"},{"desc":"[沈阳市] [沈阳江东分部]的派件已签收 感谢使用中通快递,期待再次为您服务!","address":"沈阳江东分部","date":"2017-05-22 12:53:05"}]
     * logisticsNo : 438955699913
     * logisticsCode : ZTO
     * orderDetailVOList : [{"orderDetailPaymentDicName":"","ifCanPay":"0","goodsId":"","payTimeStr":"","autoStart":"1","isPackage":"0","logisticsStatusDicName":"","payTime":"","openLessonStatus":"","id":"7421","categoryName":"","insureYear":"0","orderDetailNo":"1495785079049","logisticsStatus":"0","shopId":"1","returnNum":"","goodsPrice":"1000.0","goodsPriceString":"","goodsStatus":"0","orderId":"5366","goodsType":"3","goodsTypeDicName":"","canReturnNum":"","categoryId":"","goodsCode":"f60d01b3394449b1ae48ad7063b43aa3","shortDesc":"","returnId":"","goodsNum":"1","mainImgUrl":"/goods/images/20170428/1493390399180046404.jpg","aliasName":"初级会计BB","isGift":"0","orderNo":"BE261551191811CB21","goodsDate":"2017-01-01 00:00:00","goodsStatusDicName":"","serviceStatus":"1","goodsSalePriceString":"","shopName":"","goodsSalePrice":"1000.0","channel":"-1","goodsName":"初级会计BB"},{"orderDetailPaymentDicName":"","ifCanPay":"0","goodsId":"","payTimeStr":"","autoStart":"1","isPackage":"0","logisticsStatusDicName":"","payTime":"","openLessonStatus":"","id":"7422","categoryName":"","insureYear":"0","orderDetailNo":"1495785079056","logisticsStatus":"0","shopId":"1","returnNum":"","goodsPrice":"666.0","goodsPriceString":"","goodsStatus":"1","orderId":"5366","goodsType":"3","goodsTypeDicName":"","canReturnNum":"","categoryId":"","goodsCode":"ca6d9bf6d1534151b701d2934c35e60f","shortDesc":"","returnId":"","goodsNum":"1","mainImgUrl":"/goods/images/20161216/1481885116077042682.png","aliasName":"初级职称经济法基础轻三","isGift":"0","orderNo":"BE261551191811CB21","goodsDate":"2017-01-01 00:00:00","goodsStatusDicName":"","serviceStatus":"1","goodsSalePriceString":"","shopName":"","goodsSalePrice":"666.0","channel":"-1","goodsName":"JSS商品测试初级职称经济法基础轻三"}]
     * logistics : 中通快递
     * orderId :
     */

    private String logisticsNo;
    private String logisticsCode;
    private String logistics;
    private String orderId;
    private List<LogisticsMessagesBean> logisticsMessages;
    private List<OrderDetailItemBean> orderDetailVOList;

    public String getLogisticsNo() {
        return logisticsNo;
    }

    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo;
    }

    public String getLogisticsCode() {
        return logisticsCode;
    }

    public void setLogisticsCode(String logisticsCode) {
        this.logisticsCode = logisticsCode;
    }

    public String getLogistics() {
        return logistics;
    }

    public void setLogistics(String logistics) {
        this.logistics = logistics;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public List<LogisticsMessagesBean> getLogisticsMessages() {
        return logisticsMessages;
    }

    public void setLogisticsMessages(List<LogisticsMessagesBean> logisticsMessages) {
        this.logisticsMessages = logisticsMessages;
    }

    public List<OrderDetailItemBean> getOrderDetailVOList() {
        return orderDetailVOList;
    }

    public void setOrderDetailVOList(List<OrderDetailItemBean> orderDetailVOList) {
        this.orderDetailVOList = orderDetailVOList;
    }

    public static class LogisticsMessagesBean implements Serializable{
        /**
         * desc : [北京市] [北京业务部]的东奥已收件 电话:暂无
         * address : 北京业务部
         * date : 2017-05-20 15:55:53
         */

        private String desc;
        private String address;
        private String date;

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }

}
