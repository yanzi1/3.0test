package com.me.data.model.user;

import java.io.Serializable;

/**
 * Created by xunice on 10/03/2017.
 */
public class UserBean implements Serializable {

    private String id;
    private String accessToken;
    private String username;
    private String mobilePhone;
    private String email;
    private String avatarImageUrl;
    private String nowIntegral;
    private String level;
    private String growthValue;
    private String levelName;

    public String getNowIntegral() {
        return nowIntegral;
    }

    public void setNowIntegral(String nowIntegral) {
        this.nowIntegral = nowIntegral;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getGrowthValue() {
        return growthValue;
    }

    public void setGrowthValue(String growthValue) {
        this.growthValue = growthValue;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
}
