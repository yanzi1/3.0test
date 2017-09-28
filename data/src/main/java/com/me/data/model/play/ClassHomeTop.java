package com.me.data.model.play;

import java.io.Serializable;

/**
 * Created by dell on 2017/6/6.
 */

public class ClassHomeTop implements Serializable{

    private String days;
    private String link;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
