package com.me.data.model.mall;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xunice on 10/03/2017.
 */

public class GoodsInfoDetailBean implements Serializable{

    private List<PropertyBean> goodsAttributeValueList;
    private GoodsBaseBean goodsBase;
    private List<GoodsPicBean> goodsPicList;
    private List<GoodsProbationBean> goodsProbationList;
    private List<GoodsDescBean> goodsDescriptionList;
    private List<GoodsPayMentBean> goodsPaymentList;


    public List<PropertyBean> getGoodsAttributeValueList() {
        return goodsAttributeValueList;
    }

    public void setGoodsAttributeValueList(List<PropertyBean> goodsAttributeValueList) {
        this.goodsAttributeValueList = goodsAttributeValueList;
    }

    public GoodsBaseBean getGoodsBase() {
        return goodsBase;
    }

    public void setGoodsBase(GoodsBaseBean goodsBase) {
        this.goodsBase = goodsBase;
    }

    public List<GoodsPicBean> getGoodsPicList() {
        return goodsPicList;
    }

    public void setGoodsPicList(List<GoodsPicBean> goodsPicList) {
        this.goodsPicList = goodsPicList;
    }

    public List<GoodsProbationBean> getGoodsProbationList() {
        return goodsProbationList;
    }

    public void setGoodsProbationList(List<GoodsProbationBean> goodsProbationList) {
        this.goodsProbationList = goodsProbationList;
    }

    public List<GoodsDescBean> getGoodsDescriptionList() {
        return goodsDescriptionList;
    }

    public void setGoodsDescriptionList(List<GoodsDescBean> goodsDescriptionList) {
        this.goodsDescriptionList = goodsDescriptionList;
    }

    public List<GoodsPayMentBean> getGoodsPaymentList() {
        return goodsPaymentList;
    }

    public void setGoodsPaymentList(List<GoodsPayMentBean> goodsPaymentList) {
        this.goodsPaymentList = goodsPaymentList;
    }
}
