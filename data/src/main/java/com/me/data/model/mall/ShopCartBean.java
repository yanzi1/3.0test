package com.me.data.model.mall;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xunice on 26/04/2017.
 */

public class ShopCartBean implements Serializable {

    private boolean checked; //是否选中
    private int checkedNum; //选中的商品数量
    private double jieShengPrice; //共节省了多少
    private int total; //总数
    private double zongPrice; //总价
    private List<VenderBean> venders; //店铺

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public int getCheckedNum() {
        return checkedNum;
    }

    public void setCheckedNum(int checkedNum) {
        this.checkedNum = checkedNum;
    }

    public double getJieShengPrice() {
        return jieShengPrice;
    }

    public void setJieShengPrice(double jieShengPrice) {
        this.jieShengPrice = jieShengPrice;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public double getZongPrice() {
        return zongPrice;
    }

    public void setZongPrice(double zongPrice) {
        this.zongPrice = zongPrice;
    }

    public List<VenderBean> getVenders() {
        return venders;
    }

    public void setVenders(List<VenderBean> venders) {
        this.venders = venders;
    }

    /**以下要删除**/
    private int shopId;
    private String shopName;

    private List<CartlistBean> cartlist; //优惠list

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public List<CartlistBean> getCartlist() {
        return cartlist;
    }

    public void setCartlist(List<CartlistBean> cartlist) {
        this.cartlist = cartlist;
    }


    public static class CartlistBean {
        private int id;
        private int shopId;
        private String shopName;
        private int productId;
        private String productName;
        private String price;
        private String defaultPic;
        private int count;
        private boolean isSelect = true;
        private int isFirst = 2;
        private boolean isShopSelect = true;

        public String getDefaultPic() {
            return defaultPic;
        }

        public void setDefaultPic(String defaultPic) {
            this.defaultPic = defaultPic;
        }

        public boolean getIsShopSelect() {
            return isShopSelect;
        }

        public void setShopSelect(boolean shopSelect) {
            isShopSelect = shopSelect;
        }

        public int getIsFirst() {
            return isFirst;
        }

        public void setIsFirst(int isFirst) {
            this.isFirst = isFirst;
        }

        public boolean getIsSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getShopId() {
            return shopId;
        }

        public void setShopId(int shopId) {
            this.shopId = shopId;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }


        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }


    }
}
