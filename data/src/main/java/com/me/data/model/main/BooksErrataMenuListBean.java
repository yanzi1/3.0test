package com.me.data.model.main;

import java.util.List;

/**
 * Created by jjr on 2017/6/9.
 */

public class BooksErrataMenuListBean {

    private List<SubjectListBean> subjectList;

    public List<SubjectListBean> getSubjectList() {
        return subjectList;
    }

    public void setSubjectList(List<SubjectListBean> subjectList) {
        this.subjectList = subjectList;
    }

    public static class SubjectListBean {
        /**
         * id : 175
         * selected : true
         * seasonList : [{"id":"195","selected":"true","sort":"1","name":"APP审计2018全国","year":"2018","areaId":"1"},{"id":"183","selected":"false","sort":"2","bookList":[{"id":"204","selected":"true","sort":"2","name":"APP用书-注会-审计2017轻松过关一","type":"1"}],"name":"APP审计2017全国","year":"2017","areaId":"1"}]
         * sort : 2
         * name : APP审计
         */

        private String id;
        private String selected;
        private String sort;
        private String name;
        private List<SeasonListBean> seasonList;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSelected() {
            return selected;
        }

        public void setSelected(String selected) {
            this.selected = selected;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<SeasonListBean> getSeasonList() {
            return seasonList;
        }

        public void setSeasonList(List<SeasonListBean> seasonList) {
            this.seasonList = seasonList;
        }

        public static class SeasonListBean {
            /**
             * id : 195
             * selected : true
             * sort : 1
             * name : APP审计2018全国
             * year : 2018
             * areaId : 1
             * bookList : [{"id":"204","selected":"true","sort":"2","name":"APP用书-注会-审计2017轻松过关一","type":"1"}]
             */

            private String id;
            private String selected;
            private String sort;
            private String name;
            private String year;
            private String areaId;
            private List<BookListBean> bookList;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getSelected() {
                return selected;
            }

            public void setSelected(String selected) {
                this.selected = selected;
            }

            public String getSort() {
                return sort;
            }

            public void setSort(String sort) {
                this.sort = sort;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getYear() {
                return year;
            }

            public void setYear(String year) {
                this.year = year;
            }

            public String getAreaId() {
                return areaId;
            }

            public void setAreaId(String areaId) {
                this.areaId = areaId;
            }

            public List<BookListBean> getBookList() {
                return bookList;
            }

            public void setBookList(List<BookListBean> bookList) {
                this.bookList = bookList;
            }

            public static class BookListBean {
                /**
                 * id : 204
                 * selected : true
                 * sort : 2
                 * name : APP用书-注会-审计2017轻松过关一
                 * type : 1
                 */

                private String id;
                private String selected;
                private String sort;
                private String name;
                private String type;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getSelected() {
                    return selected;
                }

                public void setSelected(String selected) {
                    this.selected = selected;
                }

                public String getSort() {
                    return sort;
                }

                public void setSort(String sort) {
                    this.sort = sort;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }
            }
        }
    }
}
