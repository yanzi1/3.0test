package com.me.data.model.mine;

import java.util.List;

/**
 * Created by jjr on 2017/6/2.
 */

public class ErrorsCountAndTypes {


    /**
     * choicetypeList : [{"id":15,"showName":"多选题"},{"id":14,"showName":"单选题"},{"id":17,"showName":"不定项选择题"},{"id":33,"showName":"测试试题类型简介"},{"id":1,"showName":"判断题"}]
     * errorCount : 17
     */

    private int errorCount;
    private List<ChoicetypeBean> choicetypeList;

    public int getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(int errorCount) {
        this.errorCount = errorCount;
    }

    public List<ChoicetypeBean> getChoicetypeList() {
        return choicetypeList;
    }

    public void setChoicetypeList(List<ChoicetypeBean> choicetypeList) {
        this.choicetypeList = choicetypeList;
    }

    public static class ChoicetypeBean {
        /**
         * id : 15
         * showName : 多选题
         */

        private int id;
        private String showName;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getShowName() {
            return showName;
        }

        public void setShowName(String showName) {
            this.showName = showName;
        }
    }
}
