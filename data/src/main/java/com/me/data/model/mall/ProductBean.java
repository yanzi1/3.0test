package com.me.data.model.mall;

import com.me.data.cartview.base.BaseItemData;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xunice on 10/03/2017.
 */

public class ProductBean extends BaseItemData implements Serializable{

    private int categoryId;   //商品分类名称
    private boolean checked;  //商品是否选中
    private int classific;    //商品类别
    private int count;       //添加的商品数量
    private double danJia;   //单价
    private String goodsCode; //商品编码
    private String goodsDate; //商品编码


    private boolean hasCxyh; //商品是否有促销
    private String id;
    private String imgUrl; //商品图片
    private String name;  //商品名称
    private String payments; //可用的支付方式

    private String shortDesc; //商品描述
    private String venderId;  //店铺Id
    private String xiaoJi;    //小计
    private double yuanJia;    //原价

    private String pweight;    //
    private String isPackage;    //
    private int autoStart;
    private String isbn;
    private String channel;


    private List<Cxyh> cxyhs;

    //本地字段
    private boolean isGift = false;
    private boolean isEditChecked = false;

    public boolean isGift() {
        return isGift;
    }

    public void setGift(boolean gift) {
        isGift = gift;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public int getClassific() {
        return classific;
    }

    public void setClassific(int classific) {
        this.classific = classific;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getDanJia() {
        return danJia;
    }

    public void setDanJia(double danJia) {
        this.danJia = danJia;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public boolean isHasCxyh() {
        return hasCxyh;
    }

    public void setHasCxyh(boolean hasCxyh) {
        this.hasCxyh = hasCxyh;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPayments() {
        return payments;
    }

    public void setPayments(String payments) {
        this.payments = payments;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getVenderId() {
        return venderId;
    }

    public void setVenderId(String venderId) {
        this.venderId = venderId;
    }

    public String getXiaoJi() {
        return xiaoJi;
    }

    public void setXiaoJi(String xiaoJi) {
        this.xiaoJi = xiaoJi;
    }

    public List<Cxyh> getCxyhs() {
        return cxyhs;
    }

    public void setCxyhs(List<Cxyh> cxyhs) {
        this.cxyhs = cxyhs;
    }

    public String getGoodsDate() {
        return goodsDate;
    }

    public void setGoodsDate(String goodsDate) {
        this.goodsDate = goodsDate;
    }

    public double getYuanJia() {
        return yuanJia;
    }

    public void setYuanJia(double yuanJia) {
        this.yuanJia = yuanJia;
    }

    public String getPweight() {
        return pweight;
    }

    public void setPweight(String pweight) {
        this.pweight = pweight;
    }

    public String getIsPackage() {
        return isPackage;
    }

    public void setIsPackage(String isPackage) {
        this.isPackage = isPackage;
    }

    public int getAutoStart() {
        return autoStart;
    }

    public void setAutoStart(int autoStart) {
        this.autoStart = autoStart;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }


    public boolean isEditChecked() {
        return isEditChecked;
    }

    public void setEditChecked(boolean editChecked) {
        isEditChecked = editChecked;
    }

    public static class Cxyh extends BaseItemData implements Serializable {
        private String id;
        private String name;
        private boolean checked;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isChecked() {
            return checked;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }
    }

}
