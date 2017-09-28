package com.me.data.model.mall;

import com.me.data.model.mine.CouponBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xunice on 22/05/2017.
 */

public class OrderUserAccountBean implements Serializable {

    private List<CouponBean> coupons;
    private List<CouponBean> cashCoupons;
    private AccountCashBean accountCash;
    private AccountCashBean accountCard;


    public List<com.me.data.model.mine.CouponBean> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<CouponBean> coupons) {
        this.coupons = coupons;
    }

    public List<CouponBean> getCashCoupons() {
        return cashCoupons;
    }

    public void setCashCoupons(List<CouponBean> cashCoupons) {
        this.cashCoupons = cashCoupons;
    }

    public AccountCashBean getAccountCash() {
        return accountCash;
    }

    public void setAccountCash(AccountCashBean accountCash) {
        this.accountCash = accountCash;
    }

    public AccountCashBean getAccountCard() {
        return accountCard;
    }

    public void setAccountCard(AccountCashBean accountCard) {
        this.accountCard = accountCard;
    }
}