package com.me.data.model.mall;

import com.me.data.cartview.base.BaseItemData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xunice on 10/03/2017.
 */

public class PromoBean extends BaseItemData implements Serializable{

    private boolean fulfill;
    private String id;
    private double jianPrice;
    private String name;
    private double promoPrice;
    private int type;
    private String typeName;
    private List<ProductBean> products;
    private String venderId;

    private int checkedNum;
    private int noCheckedNum;
    private int count;


    /**买赠**/
    private int giftNum;
    private List<GiftBean> allGifts; //所有的赠品 满赠数据为空
    private List<GiftBean> gifts; //选择的赠品

    /**满赠卡劵**/
    private String opt;
    private List<CouponBean> allCoupons; //所有卡券
    private List<CouponBean> coupons; //卡券


    private boolean checked; //新增的字段

    private List<String> choiseList; //本地字段

    public List<String> getChoiseList() {
        if(choiseList == null){
           choiseList = new ArrayList<>();
        }
        return choiseList;
    }

    public void setChoiseList(List<String> choiseList) {
        this.choiseList = choiseList;
    }

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

    public int getNoCheckedNum() {
        return noCheckedNum;
    }

    public void setNoCheckedNum(int noCheckedNum) {
        this.noCheckedNum = noCheckedNum;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getGiftNum() {
        return giftNum;
    }

    public void setGiftNum(int giftNum) {
        this.giftNum = giftNum;
    }

    public List<GiftBean> getAllGifts() {
        return allGifts;
    }

    public void setAllGifts(List<GiftBean> allGifts) {
        this.allGifts = allGifts;
    }

    public List<GiftBean> getGifts() {
        return gifts;
    }

    public void setGifts(List<GiftBean> gifts) {
        this.gifts = gifts;
    }

    public String getOpt() {
        return opt;
    }

    public void setOpt(String opt) {
        this.opt = opt;
    }

    public List<CouponBean> getAllCoupons() {
        return allCoupons;
    }

    public void setAllCoupons(List<CouponBean> allCoupons) {
        this.allCoupons = allCoupons;
    }

    public List<CouponBean> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<CouponBean> coupons) {
        this.coupons = coupons;
    }

    public boolean isFulfill() {
        return fulfill;
    }

    public void setFulfill(boolean fulfill) {
        this.fulfill = fulfill;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getJianPrice() {
        return jianPrice;
    }

    public void setJianPrice(double jianPrice) {
        this.jianPrice = jianPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPromoPrice() {
        return promoPrice;
    }

    public void setPromoPrice(double promoPrice) {
        this.promoPrice = promoPrice;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<ProductBean> getProducts() {
        return products;
    }

    public void setProducts(List<ProductBean> products) {
        this.products = products;
    }

    public String getVenderId() {
        return venderId;
    }

    public void setVenderId(String venderId) {
        this.venderId = venderId;
    }
}
