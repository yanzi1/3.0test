package com.me.data.model.mall;

import java.util.List;

/**
 * 商城首页
 * Created by mayunfei on 17-5-3.
 */

public class GoodsListItemBean {


    /**
     * count : 71
     * goodsList : [{"classific":2,"goodsId":35,"goodsName":"商品-10-颜","mainPicUrlOfPc":"/goods/images/20161026/1477418947260058187.jpg","originalPrice":100,"salePrice":100,"shopId":1}]
     */

    private int count;
    private List<GoodsListBean> goodsList;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<GoodsListBean> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<GoodsListBean> goodsList) {
        this.goodsList = goodsList;
    }

    public static class GoodsListBean {
        /**
         * classific : 2
         * goodsId : 35
         * goodsName : 商品-10-颜
         * mainPicUrlOfPc : /goods/images/20161026/1477418947260058187.jpg
         * originalPrice : 100
         * salePrice : 100
         * shopId : 1
         */

        private int classific;
        private int goodsId;
        private String goodsName;
        private String mainPicUrlOfPc;
        private double originalPrice;
        private double salePrice;
        private int shopId;

        public int getClassific() {
            return classific;
        }

        public void setClassific(int classific) {
            this.classific = classific;
        }

        public int getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(int goodsId) {
            this.goodsId = goodsId;
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

        public double getOriginalPrice() {
            return originalPrice;
        }

        public void setOriginalPrice(double originalPrice) {
            this.originalPrice = originalPrice;
        }

        public double getSalePrice() {
            return salePrice;
        }

        public void setSalePrice(double salePrice) {
            this.salePrice = salePrice;
        }

        public int getShopId() {
            return shopId;
        }

        public void setShopId(int shopId) {
            this.shopId = shopId;
        }
    }
}
