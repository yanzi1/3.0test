package com.me.data.model.main;

import java.util.List;

/**
 * Created by jjr on 2017/5/9.
 */

public class TabListBean {

    private List<ListItemBean> list;

    public List<ListItemBean> getList() {
        return list;
    }

    public void setList(List<ListItemBean> list) {
        this.list = list;
    }

    public static class ListItemBean {
        /**
         * link : m.json.dongao.com/kjkt/cjhjzc/dhtab/ksbm/index.json
         * tabId : 1
         * tabName : 考试报名
         */

        private String link;
        private String tabId;
        private String tabName;

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getTabId() {
            return tabId;
        }

        public void setTabId(String tabId) {
            this.tabId = tabId;
        }

        public String getTabName() {
            return tabName;
        }

        public void setTabName(String tabName) {
            this.tabName = tabName;
        }

        @Override
        public String toString() {
            return "ListItemBean{" +
                    "link='" + link + '\'' +
                    ", tabId='" + tabId + '\'' +
                    ", tabName='" + tabName + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "TabListBean{" +
                "list=" + list +
                '}';
    }
}
