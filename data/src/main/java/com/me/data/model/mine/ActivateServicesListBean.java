package com.me.data.model.mine;

import java.util.List;

/**
 * Created by jjr on 2017/5/26.
 */

public class ActivateServicesListBean {

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * explainName : 赠送服务
         * id : 38
         * couponBookExplainDetailDtos : [{"id":"55","explainDes":"赠送题库","explainId":"38"},{"id":"56","explainDes":"赠送公开课","explainId":"38"},{"id":"57","explainDes":"赠送电子书","explainId":"38"},{"id":"58","explainDes":"赠送贼多东西","explainId":"38"},{"id":"59","explainDes":"来了就送不来等着送","explainId":"38"},{"id":"60","explainDes":"来吧来吧来吧来吧来吧来吧来吧来吧来吧来吧来吧来吧来吧来吧来吧来吧来吧来吧来吧来吧来吧来吧来吧来吧来吧来吧来吧来吧来吧来吧来吧来吧来吧来吧来吧来吧来吧来吧","explainId":"38"},{"id":"61","explainDes":"","explainId":"38"}]
         * jsonstr :
         * ruleId : 33
         */

        private String explainName;
        private String id;
        private String jsonstr;
        private String ruleId;
        private List<CouponBookExplainDetailDtosBean> couponBookExplainDetailDtos;

        public String getExplainName() {
            return explainName;
        }

        public void setExplainName(String explainName) {
            this.explainName = explainName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getJsonstr() {
            return jsonstr;
        }

        public void setJsonstr(String jsonstr) {
            this.jsonstr = jsonstr;
        }

        public String getRuleId() {
            return ruleId;
        }

        public void setRuleId(String ruleId) {
            this.ruleId = ruleId;
        }

        public List<CouponBookExplainDetailDtosBean> getCouponBookExplainDetailDtos() {
            return couponBookExplainDetailDtos;
        }

        public void setCouponBookExplainDetailDtos(List<CouponBookExplainDetailDtosBean> couponBookExplainDetailDtos) {
            this.couponBookExplainDetailDtos = couponBookExplainDetailDtos;
        }

        public static class CouponBookExplainDetailDtosBean {
            /**
             * id : 55
             * explainDes : 赠送题库
             * explainId : 38
             */

            private String id;
            private String explainDes;
            private String explainId;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getExplainDes() {
                return explainDes;
            }

            public void setExplainDes(String explainDes) {
                this.explainDes = explainDes;
            }

            public String getExplainId() {
                return explainId;
            }

            public void setExplainId(String explainId) {
                this.explainId = explainId;
            }
        }
    }
}
