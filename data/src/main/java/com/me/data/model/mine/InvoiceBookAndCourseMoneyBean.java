package com.me.data.model.mine;

import java.util.List;

/**
 * 获取发票明细中订单的图书费用+培训费用
 * Created by mayunfei on 17-6-12.
 */

public class InvoiceBookAndCourseMoneyBean {


    private int invoiceOfOpenStatus;
    private List<InvoiceTitleBean> titleList;
    private List<MoneyListBean> moneyList;
    private AddressBean addressInfo;

    public int getInvoiceOfOpenStatus() {
        return invoiceOfOpenStatus;
    }

    public void setInvoiceOfOpenStatus(int invoiceOfOpenStatus) {
        this.invoiceOfOpenStatus = invoiceOfOpenStatus;
    }

    public List<InvoiceTitleBean> getTitleList() {
        return titleList;
    }

    public void setTitleList(List<InvoiceTitleBean> titleList) {
        this.titleList = titleList;
    }

    public List<MoneyListBean> getMoneyList() {
        return moneyList;
    }

    public void setMoneyList(List<MoneyListBean> moneyList) {
        this.moneyList = moneyList;
    }

    public static class TitleListBean {
        /**
         * id : 133
         * title : 新增的发票title2
         * memberId : 333
         * memberName : apptest02
         */

        private String id;
        private String title;
        private String memberId;
        private String memberName;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        public String getMemberName() {
            return memberName;
        }

        public void setMemberName(String memberName) {
            this.memberName = memberName;
        }
    }

    public static class MoneyListBean {
        /**
         * detail : 2
         * money : 0.0
         * detailDicName : 图书(￥0.0)
         */

        private String detail;
        private String money;
        private String detailDicName;

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getDetailDicName() {
            return detailDicName;
        }

        public void setDetailDicName(String detailDicName) {
            this.detailDicName = detailDicName;
        }
    }

    public AddressBean getAddressInfo() {
        return addressInfo;
    }

    public void setAddressInfo(AddressBean addressInfo) {
        this.addressInfo = addressInfo;
    }
}
