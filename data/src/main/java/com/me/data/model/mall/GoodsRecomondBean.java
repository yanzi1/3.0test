package com.me.data.model.mall;

/**
 * Created by xunice on 31/05/2017.
 */

public class GoodsRecomondBean {
  /*  {
        "goodsId": "549",
            "sort": "",
            "goodsCatId": "97",
            "createdate": "2017-06-21 14:14:40",
            "rcGoodsId": "549",
            "mainPicUrlOfPc": "/goods/images/20170610/1497091524343058435.jpg",
            "isDelete": "0",
            "id": "104",
            "shopId": "43",
            "userId": "192",
            "salePrice": "100.0",
            "updatedate": "2017-06-21 14:14:40",
            "goodsName": "APP会计2017-精品保障班",
            "classific": "2"
    }*/

    private String goodsId;
    private int classific;
    private String goodsName;
    private String mainPicUrlOfPc;
    private double salePrice;
    private String shopId;

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public int getClassific() {
        return classific;
    }

    public void setClassific(int classific) {
        this.classific = classific;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getMainPicUrlOfPc() {
        return mainPicUrlOfPc;
    }

    public void setMainPicUrlOfPc(String mainPicUrlOfPc) {
        this.mainPicUrlOfPc = mainPicUrlOfPc;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }
}
