package com.me.data.model.mall;

import java.util.List;

/**
 * Created by mayunfei on 17-5-12.
 */

public class CouDanBean {


    /**
     * activityName : 测试接口促销
     * beginDate : 2017-04-23
     * endDate : 2017-04-28
     * goodsList : [{"createdate":1478572588000,"goodsDetail":{"goodsAttributeValueList":[{"attrCode":"isbn","attrId":7,"attrName":"ISBN","attrValue":"ZKYS025","goodsId":255,"id":1872},{"attrCode":"000000000006","attrId":6,"attrName":"作者","attrValue":"中国注册会计师协会","goodsId":255,"id":1873},{"attrCode":"000000000005","attrId":5,"attrName":"出版时间","attrValue":"","goodsId":255,"id":1874},{"attrCode":"000000000004","attrId":4,"attrName":"出版社","attrValue":"中国财政经济出版社","goodsId":255,"id":1875}],"goodsBase":{"aliasName":"","auditStatus":1,"autoStart":1,"brandId":6,"categoryId":84,"classific":3,"createdate":1478235730000,"goodsCode":"a63793da26d34733938ef9682da59283","goodsDate":1483200000000,"goodsName":"2017年注会《会计》-官方教材","goodsStatus":1,"id":255,"insureYear":0,"isDelete":0,"isDelivery":1,"isPackage":0,"mainPicUrlOfPc":"/goods/images/20161104/1478235640111054963.jpg","originalPrice":54,"shortDesc":"","userId":1717,"weight":0.5},"goodsDescriptionList":[{"goodsDescription":"","goodsId":255,"id":1141,"platform":1},{"goodsDescription":"","goodsId":255,"id":1142,"platform":2}],"goodsIndemnityList":[],"goodsPaymentList":[{"goodsId":255,"id":834,"paymentName":"现金","paymentType":1},{"goodsId":255,"id":835,"paymentName":"现金账户","paymentType":2}],"goodsPicList":[{"goodsId":255,"id":1142,"isMainPic":1,"picUrl":"/goods/images/20161104/1478235640111054963.jpg","platform":1,"sort":0},{"goodsId":255,"id":1143,"isMainPic":1,"picUrl":"/goods/images/20161104/1478235648081090077.jpg","platform":2,"sort":0}],"goodsProbationList":[{"contents":"","goodsId":255,"platform":1},{"contents":"","goodsId":255,"platform":2}],"goodsSeo":{"goodsId":255,"id":571,"metaDescription":"","metaKeyword":"","pageTitle":""},"goodsUpgradeList":[],"packageGoodsList":[]},"goodsId":255,"id":196,"isDelete":0,"isFrontSale":1,"popularLevel":1,"salePrice":54,"shopId":1,"sort":1,"upDownStatus":1,"upTime":1478572588000,"updatedate":1478572588000,"userId":1717},{"createdate":1478572588000,"goodsDetail":{"goodsAttributeValueList":[{"attrCode":"isbn","attrId":7,"attrName":"ISBN","attrValue":"9787514167221","goodsId":7,"id":3676},{"attrCode":"000000000006","attrId":6,"attrName":"作者","attrValue":"尤磊","goodsId":7,"id":3677},{"attrCode":"000000000005","attrId":5,"attrName":"出版时间","attrValue":"","goodsId":7,"id":3678},{"attrCode":"000000000004","attrId":4,"attrName":"出版社","attrValue":"北京大学出版社","goodsId":7,"id":3679}],"goodsBase":{"aliasName":"","auditStatus":1,"autoStart":1,"brandId":9,"categoryId":84,"classific":3,"createdate":1478152468000,"goodsCode":"fdba8d81e3f8437bb053e650d9b12ed9","goodsDate":1451577600000,"goodsName":"2016年注会《会计》轻松过关三","goodsStatus":2,"id":7,"insureYear":0,"isDelete":0,"isDelivery":1,"isPackage":0,"mainPicUrlOfPc":"/goods/images/20161103/1478152440479061887.jpg","originalPrice":16,"shortDesc":"","updatedate":1478574528000,"userId":1717,"weight":0.3},"goodsDescriptionList":[{"goodsDescription":"","goodsId":7,"id":2211,"platform":1},{"goodsDescription":"","goodsId":7,"id":2212,"platform":2}],"goodsIndemnityList":[],"goodsPaymentList":[{"goodsId":7,"id":130,"paymentName":"现金","paymentType":1},{"goodsId":7,"id":131,"paymentName":"现金账户","paymentType":2}],"goodsPicList":[{"goodsId":7,"id":2212,"isMainPic":1,"picUrl":"/goods/images/20161103/1478152440479061887.jpg","platform":1,"sort":0},{"goodsId":7,"id":2213,"isMainPic":1,"picUrl":"/goods/images/20161103/1478152448998063626.jpg","platform":2,"sort":0}],"goodsProbationList":[{"contents":"","goodsId":7,"platform":1},{"contents":"","goodsId":7,"platform":2}],"goodsSeo":{"goodsId":7,"id":1106,"metaDescription":"","metaKeyword":"","pageTitle":""},"goodsUpgradeList":[],"packageGoodsList":[]},"goodsId":7,"id":205,"isDelete":0,"isFrontSale":1,"popularLevel":1,"salePrice":16,"shopId":1,"sort":1,"upDownStatus":1,"upTime":1478572588000,"updatedate":1478572588000,"userId":1717}]
     * shopName : 东奥
     */

    private String activityName;
    private String beginDate;
    private String endDate;
    private String shopName;
    private List<GoodsListItemBean.GoodsListBean> goodsList;

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public List<GoodsListItemBean.GoodsListBean> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<GoodsListItemBean.GoodsListBean> goodsList) {
        this.goodsList = goodsList;
    }


}
