package com.me.data.model.course;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by xd on 2017/7/11.
 */

public class CourseStatiscalBean implements Serializable {


    /**
     * type : 1
     * week : {"total":"123","list":[{"data":"20min","name":"asdf","precent":"0.1","sSubjectId":"1"},{"data":"20min","name":"asdf","precent":"0.1","sSubjectId":"1"},{"data":"20min","name":"asdf","precent":"0.1","sSubjectId":"1"}]}
     * month : {"total":"123","list":[{"data":"20min","name":"asdf","precent":"0.1","sSubjectId":"1"}]}
     */

    private int type;
    private DateBean week;
    private DateBean month;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public DateBean getWeek() {
        return week;
    }

    public void setWeek(DateBean week) {
        this.week = week;
    }

    public DateBean getMonth() {
        return month;
    }

    public void setMonth(DateBean month) {
        this.month = month;
    }

    public static class DateBean implements Serializable{
        /**
         * total : 123
         * list : [{"data":"20min","name":"asdf","precent":"0.1","sSubjectId":"1"},{"data":"20min","name":"asdf","precent":"0.1","sSubjectId":"1"},{"data":"20min","name":"asdf","precent":"0.1","sSubjectId":"1"}]
         */

        private String total;
        private ArrayList<StatiscalItemBean> list;

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public ArrayList<StatiscalItemBean> getList() {
            return list;
        }

        public void setList(ArrayList<StatiscalItemBean> list) {
            this.list = list;
        }

        public static class StatiscalItemBean implements Serializable {
            /**
             * data : 20min
             * name : asdf
             * precent : 0.1
             * sSubjectId : 1
             */

            private String data;
            private String name;
            private float precent;
            private String sSubjectId;

            public String getData() {
                return data;
            }

            public void setData(String data) {
                this.data = data;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public float getPrecent() {
                return precent;
            }

            public void setPrecent(float precent) {
                this.precent = precent;
            }

            public String getSSubjectId() {
                return sSubjectId;
            }

            public void setSSubjectId(String sSubjectId) {
                this.sSubjectId = sSubjectId;
            }
        }
    }
}
