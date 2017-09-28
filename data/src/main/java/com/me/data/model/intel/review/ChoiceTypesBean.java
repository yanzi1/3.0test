package com.me.data.model.intel.review;

import java.util.List;

/**
 * Created by jjr on 2017/9/5.
 */

public class ChoiceTypesBean {

    private List<Choicetype> choicetypes;

    public List<Choicetype> getChoicetypes() {
        return choicetypes;
    }

    public void setChoicetypes(List<Choicetype> choicetypes) {
        this.choicetypes = choicetypes;
    }

    public static class Choicetype {
        /**
         * id : 148
         * showName : 多项选择题
         */

        private String id;
        private String showName;

        public String getId() {
            return id;
        }

        public void setId(String id) {
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
