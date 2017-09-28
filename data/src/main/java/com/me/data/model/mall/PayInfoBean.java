package com.me.data.model.mall;

import java.io.Serializable;

/**
 * Created by xunice on 06/07/2017.
 */

public class PayInfoBean implements Serializable {


    /**
     * sign : A155F0F586F1E3912D78A6DDBC001990
     * result_code : 1
     * result_val : {"signData":"_input_charset=\"utf-8\"&body=\"东奥手机支付\"&it_b_pay=\"30m\"&notify_url=\"http://pay.dongao.com/aliPay/notifyURL/24/38/3\"&out_trade_no=\"38_BF1914424740571AA4\"&partner=\"2088201768879583\"&payment_type=\"1\"&seller_id=\"2088201768879583\"&service=\"mobile.securitypay.pay\"&subject=\"东奥手机支付\"&total_fee=\"270.0\"&sign=\"z4BuoxjUVL3cBNPXc%2FpLSkS%2Fe4a3EfeSHarN%2BY%2BGqbgl8R8j2A51iG94FdZ%2Fz%2FX6j5OtRVn2fRxRmJcTnnLOb9wBBsz7n4PcCxpVzyCin0QaD8SdfqZOB6KyJMXYDmjaFZrWEI082FNOTLjYpCntmxVe%2Bp8nOlq%2FakqE5JKJ3QQ%3D\"&sign_type=\"RSA\""}
     * result_msg : OK
     */

    private String sign;
    private String result_code;
    private String result_val;
    private String result_msg;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public String getResult_val() {
        return result_val;
    }

    public void setResult_val(String result_val) {
        this.result_val = result_val;
    }

    public String getResult_msg() {
        return result_msg;
    }

    public void setResult_msg(String result_msg) {
        this.result_msg = result_msg;
    }
}
