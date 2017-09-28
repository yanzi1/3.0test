package com.me.data.model.mall;

/**
 * Created by xunice on 18/05/2017.
 */

public class OrderItem {

    private String orderId;
    private String price;
    private String fromType;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFromType() {
        return fromType;
    }

    public void setFromType(String fromType) {
        this.fromType = fromType;
    }
}
