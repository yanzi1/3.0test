package com.me.data.model.mine;

/**
 * Created by xd on 2017/5/31.
 */

public class MyAccountBean {


    /**
     * amount : 84885.49
     * coupons : {"couponsUsedNum":"1","couponsNotUsedNum":"0","couponsTimedNum":"0"}
     * freezeAmount : 14159.82
     * cash : {"cashCouponsNotUsedNum":"6","cashCouponsTimedNum":"1","cashCouponsUsedNum":"2"}
     */

    private String amount;
    private CouponsBean coupons;
    private String freezeAmount;
    private CashBean cash;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public CouponsBean getCoupons() {
        return coupons;
    }

    public void setCoupons(CouponsBean coupons) {
        this.coupons = coupons;
    }

    public String getFreezeAmount() {
        return freezeAmount;
    }

    public void setFreezeAmount(String freezeAmount) {
        this.freezeAmount = freezeAmount;
    }

    public CashBean getCash() {
        return cash;
    }

    public void setCash(CashBean cash) {
        this.cash = cash;
    }

    public static class CouponsBean {
        /**
         * couponsUsedNum : 1
         * couponsNotUsedNum : 0
         * couponsTimedNum : 0
         */

        private String couponsUsedNum;
        private String couponsNotUsedNum;
        private String couponsTimedNum;

        public String getCouponsUsedNum() {
            return couponsUsedNum;
        }

        public void setCouponsUsedNum(String couponsUsedNum) {
            this.couponsUsedNum = couponsUsedNum;
        }

        public String getCouponsNotUsedNum() {
            return couponsNotUsedNum;
        }

        public void setCouponsNotUsedNum(String couponsNotUsedNum) {
            this.couponsNotUsedNum = couponsNotUsedNum;
        }

        public String getCouponsTimedNum() {
            return couponsTimedNum;
        }

        public void setCouponsTimedNum(String couponsTimedNum) {
            this.couponsTimedNum = couponsTimedNum;
        }
    }

    public static class CashBean {
        /**
         * cashCouponsNotUsedNum : 6
         * cashCouponsTimedNum : 1
         * cashCouponsUsedNum : 2
         */

        private String cashCouponsNotUsedNum;
        private String cashCouponsTimedNum;
        private String cashCouponsUsedNum;

        public String getCashCouponsNotUsedNum() {
            return cashCouponsNotUsedNum;
        }

        public void setCashCouponsNotUsedNum(String cashCouponsNotUsedNum) {
            this.cashCouponsNotUsedNum = cashCouponsNotUsedNum;
        }

        public String getCashCouponsTimedNum() {
            return cashCouponsTimedNum;
        }

        public void setCashCouponsTimedNum(String cashCouponsTimedNum) {
            this.cashCouponsTimedNum = cashCouponsTimedNum;
        }

        public String getCashCouponsUsedNum() {
            return cashCouponsUsedNum;
        }

        public void setCashCouponsUsedNum(String cashCouponsUsedNum) {
            this.cashCouponsUsedNum = cashCouponsUsedNum;
        }
    }
}
