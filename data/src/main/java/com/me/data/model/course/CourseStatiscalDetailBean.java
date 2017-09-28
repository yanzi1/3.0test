package com.me.data.model.course;

import java.util.List;

/**
 * Created by xd on 2017/7/13.
 */

public class CourseStatiscalDetailBean {

    /**
     * month : {"yDatas":["20min","50min","80min"],"xDatas":[{"xData":"7-13","isToday":true}],"total":"120分钟","itemData":[{"date":"2017-3-1","studyLength":"10分钟"}]}
     * week : {"yDatas":["20min","50min","80min"],"xDatas":[{"xData":"7-13","isToday":true}],"total":"120分钟","itemData":[{"date":"2017-3-1","studyLength":"10分钟"}]}
     */

    private DateBean month;
    private DateBean week;

    public DateBean getMonth() {
        return month;
    }

    public void setMonth(DateBean month) {
        this.month = month;
    }

    public DateBean getWeek() {
        return week;
    }

    public void setWeek(DateBean week) {
        this.week = week;
    }

    public static class DateBean {
        /**
         * yDatas : ["20min","50min","80min"]
         * xDatas : [{"xData":"7-13","isToday":true}]
         * total : 120分钟
         * itemData : [{"date":"2017-3-1","studyLength":"10分钟"}]
         */

        private String total;
        private List<String> yDatas;
        private List<XDatasBean> xDatas;
        private List<ItemDataBean> itemData;

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public List<String> getYDatas() {
            return yDatas;
        }

        public void setYDatas(List<String> yDatas) {
            this.yDatas = yDatas;
        }

        public List<XDatasBean> getXDatas() {
            return xDatas;
        }

        public void setXDatas(List<XDatasBean> xDatas) {
            this.xDatas = xDatas;
        }

        public List<ItemDataBean> getItemData() {
            return itemData;
        }

        public void setItemData(List<ItemDataBean> itemData) {
            this.itemData = itemData;
        }

        public static class XDatasBean {
            /**
             * xData : 7-13
             * isToday : true
             */

            private String xData;
            private boolean isToday;

            public String getXData() {
                return xData;
            }

            public void setXData(String xData) {
                this.xData = xData;
            }

            public boolean isIsToday() {
                return isToday;
            }

            public void setIsToday(boolean isToday) {
                this.isToday = isToday;
            }
        }

        public static class ItemDataBean {
            /**
             * date : 2017-3-1
             * studyLength : 10分钟
             */

            private String date;
            private float studyLength;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public float getStudyLength() {
                return studyLength;
            }

            public void setStudyLength(float studyLength) {
                this.studyLength = studyLength;
            }
        }
    }
}
