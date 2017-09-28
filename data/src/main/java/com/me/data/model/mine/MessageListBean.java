package com.me.data.model.mine;

import java.util.List;

/**
 * Created by jjr on 2017/6/7.
 */

public class MessageListBean {

    /**
     * list : [{"id":"76","content":"测试课程公告222","title":"测试课程公告222","updateDateStr":"2017-05-22","createDateStr":"2017-05-22"},{"id":"75","content":"课程公告111","title":"课程公告111","updateDateStr":"2017-05-22","createDateStr":"2017-05-19"},{"id":"73","content":"测试课程公告1","title":"测试课程公告1","updateDateStr":"2017-05-17","createDateStr":"2017-05-17"},{"id":"72","content":"测试123测试123测试123测试123","title":"测试1234","updateDateStr":"2017-05-17","createDateStr":"2017-05-15"},{"id":"71","content":"sdfasfsa f111","title":"sfasf11","updateDateStr":"2017-05-22","createDateStr":"2017-05-15"}]
     * count : 27
     */

    private String count;
    private List<ListBean> list;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 76
         * content : 测试课程公告222
         * title : 测试课程公告222
         * updateDateStr : 2017-05-22
         * createDateStr : 2017-05-22
         */

        private String id;
        private String content;
        private String title;
        private String updateDateStr;
        private String createDateStr;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUpdateDateStr() {
            return updateDateStr;
        }

        public void setUpdateDateStr(String updateDateStr) {
            this.updateDateStr = updateDateStr;
        }

        public String getCreateDateStr() {
            return createDateStr;
        }

        public void setCreateDateStr(String createDateStr) {
            this.createDateStr = createDateStr;
        }
    }
}
