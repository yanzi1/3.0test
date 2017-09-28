package com.me.data.event;

/**
 * Created by wp on 2017/5/24.
 */

public class PostStudyLogEvent {
    private String type;//1,smart 2，其他

    public PostStudyLogEvent(String type){
        this.type=type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
