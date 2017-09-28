package com.me.data.model.main;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.yunqing.core.db.annotations.Id;
import com.yunqing.core.db.annotations.Table;

import java.io.Serializable;

@Table(name="t_maindetailitem_bean")
public class MainDetailItemBean implements Serializable,MultiItemEntity {
    @Id
    private int dbId;
    private String id;
    private String title;//文章标题
    private String image;//文章图片
    private String tab;//文章标签
    private String author;//文章作者
    private String publishDate;//发布时间
    private String link;//文章链接
    private String des;//文章描述
    
    //图书勘误专用
    private String sourceText;//原文本
    private String targetText;//更正后
    
    //测试专用
    private int mouldCode;//模板code	
    
    
    public MainDetailItemBean(){}

    public MainDetailItemBean(String id, String title, String image, String tab, String author, String publishDate, String link, String des) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.tab = tab;
        this.author = author;
        this.publishDate = publishDate;
        this.link = link;
        this.des = des;
    }

    public int getMouldCode() {
        return mouldCode;
    }

    public void setMouldCode(int mouldCode) {
        this.mouldCode = mouldCode;
    }

    public String getSourceText() {
        return sourceText;
    }

    public void setSourceText(String sourceText) {
        this.sourceText = sourceText;
    }

    public String getTargetText() {
        return targetText;
    }

    public void setTargetText(String targetText) {
        this.targetText = targetText;
    }

    public int getDbId() {
        return dbId;
    }

    public void setDbId(int dbId) {
        this.dbId = dbId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    @Override
    public String toString() {
        return "MainDetailItemBean{" +
                "dbId=" + dbId +
                ", id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", tab='" + tab + '\'' +
                ", author='" + author + '\'' +
                ", publishDate=" + publishDate +
                ", link='" + link + '\'' +
                ", des='" + des + '\'' +
                '}';
    }

    @Override
    public int getItemType() {
        return mouldCode;
    }
}
