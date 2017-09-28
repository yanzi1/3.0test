package com.me.data.model.mine;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jjr on 2017/5/20.
 */

public class PersonalInformationBean implements Serializable{

    /**
     * graduationDate : 2017-01-01 00:00:00
     * username : apptest02
     * trueName :
     * mobilePhone :
     * email : 15712802600@163.com
     * planCredentials : [{"id":"3","name":"会计从业","checked":"true"},{"id":"2","name":"税务师","checked":"true"},{"id":"1","name":"注册会计师专业阶段","checked":"false"},{"id":"7","name":"中级职称","checked":"true"},{"id":"6","name":"初级职称","checked":"true"},{"id":"5","name":"注册会计师综合阶段","checked":"false"},{"id":"4","name":"基金从业","checked":"true"},{"id":"8","name":"高级职称","checked":"true"}]
     * profession : [{"id":"3","name":"审计","checked":"false"},{"id":"2","name":"会计","checked":"false"},{"id":"1","name":"出纳","checked":"false"},{"id":"5","name":"其他","checked":"false"},{"id":"4","name":"财务主管","checked":"false"}]
     * avatarImageUrl :
     * companyType : [{"id":"3","name":"事业单位","checked":"false"},{"id":"2","name":"行政机关","checked":"false"},{"id":"1","name":"国企","checked":"false"},{"id":"6","name":"其他","checked":"false"},{"id":"5","name":"外企","checked":"false"},{"id":"4","name":"私企","checked":"false"}]
     * educational : [{"id":"D","name":"硕士","checked":"false"},{"id":"E","name":"博士","checked":"false"},{"id":"F","name":"其他","checked":"false"},{"id":"A","name":"大专及以下","checked":"false"},{"id":"B","name":"大专","checked":"false"},{"id":"C","name":"本科","checked":"true"}]
     * credentials : [{"id":"3","name":"经济师","checked":"true"},{"id":"2","name":"税务师","checked":"true"},{"id":"1","name":"会计师","checked":"false"},{"id":"5","name":"其他","checked":"false"},{"id":"4","name":"审计师","checked":"true"}]
     * major : [{"id":"3","name":"财务管理类","checked":"false"},{"id":"2","name":"审计类","checked":"false"},{"id":"1","name":"会计类","checked":"false"},{"id":"5","name":"其他","checked":"false"},{"id":"4","name":"经济类","checked":"true"}]
     */

    private String graduationDate;
    private String username;
    private String trueName;
    private String mobilePhone;
    private String email;
    private String avatarImageUrl;
    private List<PlanCredentialsBean> planCredentials;
    private List<ProfessionBean> profession;
    private List<CompanyTypeBean> companyType;
    private List<EducationalBean> educational;
    private List<CredentialsBean> credentials;
    private List<MajorBean> major;

    public String getGraduationDate() {
        return graduationDate;
    }

    public void setGraduationDate(String graduationDate) {
        this.graduationDate = graduationDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatarImageUrl() {
        return avatarImageUrl;
    }

    public void setAvatarImageUrl(String avatarImageUrl) {
        this.avatarImageUrl = avatarImageUrl;
    }

    public List<PlanCredentialsBean> getPlanCredentials() {
        return planCredentials;
    }

    public void setPlanCredentials(List<PlanCredentialsBean> planCredentials) {
        this.planCredentials = planCredentials;
    }

    public List<ProfessionBean> getProfession() {
        return profession;
    }

    public void setProfession(List<ProfessionBean> profession) {
        this.profession = profession;
    }

    public List<CompanyTypeBean> getCompanyType() {
        return companyType;
    }

    public void setCompanyType(List<CompanyTypeBean> companyType) {
        this.companyType = companyType;
    }

    public List<EducationalBean> getEducational() {
        return educational;
    }

    public void setEducational(List<EducationalBean> educational) {
        this.educational = educational;
    }

    public List<CredentialsBean> getCredentials() {
        return credentials;
    }

    public void setCredentials(List<CredentialsBean> credentials) {
        this.credentials = credentials;
    }

    public List<MajorBean> getMajor() {
        return major;
    }

    public void setMajor(List<MajorBean> major) {
        this.major = major;
    }

    public static class PlanCredentialsBean {
        /**
         * id : 3
         * name : 会计从业
         * checked : true
         */

        private String id;
        private String name;
        private String checked;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getChecked() {
            return checked;
        }

        public void setChecked(String checked) {
            this.checked = checked;
        }
    }

    public static class ProfessionBean {
        /**
         * id : 3
         * name : 审计
         * checked : false
         */

        private String id;
        private String name;
        private String checked;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getChecked() {
            return checked;
        }

        public void setChecked(String checked) {
            this.checked = checked;
        }
    }

    public static class CompanyTypeBean {
        /**
         * id : 3
         * name : 事业单位
         * checked : false
         */

        private String id;
        private String name;
        private String checked;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getChecked() {
            return checked;
        }

        public void setChecked(String checked) {
            this.checked = checked;
        }
    }

    public static class EducationalBean {
        /**
         * id : D
         * name : 硕士
         * checked : false
         */

        private String id;
        private String name;
        private String checked;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getChecked() {
            return checked;
        }

        public void setChecked(String checked) {
            this.checked = checked;
        }
    }

    public static class CredentialsBean {
        /**
         * id : 3
         * name : 经济师
         * checked : true
         */

        private String id;
        private String name;
        private String checked;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getChecked() {
            return checked;
        }

        public void setChecked(String checked) {
            this.checked = checked;
        }
    }

    public static class MajorBean {
        /**
         * id : 3
         * name : 财务管理类
         * checked : false
         */

        private String id;
        private String name;
        private String checked;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getChecked() {
            return checked;
        }

        public void setChecked(String checked) {
            this.checked = checked;
        }
    }
}
