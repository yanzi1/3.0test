package com.me.data.event;

import java.util.List;

/**
 * Created by xunice on 10/03/2017.
 */

public class EventBusApp {
    public static class SyncFailure {

    }

    public static class AuthenticationError {

    }

    public static class FinishStackActivity {

    }

    public static class GoodsDetailBeanScribe {
        public com.me.data.model.mall.GoodsDetailBean goodsDetailBean;
        public GoodsDetailBeanScribe(com.me.data.model.mall.GoodsDetailBean goodsDetailBean){
            this.goodsDetailBean = goodsDetailBean;
        }
    }

    public static class ShopCartRefresh {
            public String id;
            public ShopCartRefresh(String id){
                this.id = id;
            }
    }

    public static class ShopCartRefreshVender {
        public ShopCartRefreshVender(){
        }
    }

    public static class ShopCartRefreshProduct {
        public String id;
        public ShopCartRefreshProduct(String id){
            this.id = id;
        }
    }

    public static class ShopCartRefreshType {
        public int type;
        public List<String> giftList;
        public String venderId;
        public String promoId;
        public String pid;
        public int pcount;
        public String pclassific;

        public ShopCartRefreshType(int type){
            this.type = type;
        }

        public ShopCartRefreshType(int type,String venderId){
            this.type = type;
            this.venderId = venderId;
        }

        public ShopCartRefreshType(int type,List<String> giftList,String venderId,String promoId){
            this.type = type;
            this.giftList = giftList;
            this.venderId = venderId;
            this.promoId = promoId;
        }

        public ShopCartRefreshType(int type,String venderId,String promoId,String pid){
            this.type = type;
            this.pid = pid;
            this.venderId = venderId;
            this.promoId = promoId;
        }

        public ShopCartRefreshType(int type,String venderId,String pid){
            this.type = type;
            this.venderId = venderId;
            this.pid = pid;
        }

        public ShopCartRefreshType(int type,String venderId,String pid,int pcount,String pclassific){
            this.type = type;
            this.venderId = venderId;
            this.pid = pid;
            this.pcount = pcount;
            this.pclassific = pclassific;
        }
    }

    public static class PayFail {

    }
}
