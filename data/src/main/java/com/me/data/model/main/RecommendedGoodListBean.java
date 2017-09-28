package com.me.data.model.main;

import java.util.List;

/**
 * Created by jjr on 2017/6/1.
 *
 * 推荐商品的集合bean类(课程、图书)
 */

public class RecommendedGoodListBean {

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 559
         * author :
         * title : APP会计黄飞鸿老师预习班
         * des :
         * link :
         * image : /goods/images/20170527/1495889500016094978.jpg
         * tab : 0.0
         * publishDate : 2017-06-01 15:40:36
         */

        private String id;
        private String author;
        private String title;
        private String des;
        private String link;
        private String image;
        private String tab;
        private String publishDate;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getTab() {
            return tab;
        }

        public void setTab(String tab) {
            this.tab = tab;
        }

        public String getPublishDate() {
            return publishDate;
        }

        public void setPublishDate(String publishDate) {
            this.publishDate = publishDate;
        }
    }
}
