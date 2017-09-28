package com.me.data.event;

/**
 * Created by xd on 2017/6/21.
 */

public class CouponCanUseEvent {
    private int count;
    private String couponType;

    public CouponCanUseEvent(int count,String couponType) {
        this.count = count;
        this.couponType=couponType;
    }

    public int getCount() {
        return count;
    }

    public String getCouponType() {
        return couponType;
    }
}
