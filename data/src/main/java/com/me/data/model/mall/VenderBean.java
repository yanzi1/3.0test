package com.me.data.model.mall;

import com.me.data.cartview.base.BaseItemData;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xunice on 10/03/2017.
 */

public class VenderBean extends BaseItemData implements Serializable{

    private boolean checked;
    private String id;
    private double jianPrice;
    private String name;

    private int noCheckedNum;
    private List<PromoBean> promos;

    private boolean isEditChecked = false;

    public int getNoCheckedNum() {
        return noCheckedNum;
    }

    public void setNoCheckedNum(int noCheckedNum) {
        this.noCheckedNum = noCheckedNum;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
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

    public List<PromoBean> getPromos() {
        return promos;
    }

    public void setPromos(List<PromoBean> promos) {
        this.promos = promos;
    }

    public boolean isEditChecked() {
        return isEditChecked;
    }

    public void setEditChecked(boolean editChecked) {
        isEditChecked = editChecked;
    }
}
