package com.me.data.model.play;


import java.util.List;

public class UploadVideo{

    private String memberId;
    private List<VideoLogs> listenList;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public List<VideoLogs> getListenList() {
        return listenList;
    }

    public void setListenList(List<VideoLogs> listenList) {
        this.listenList = listenList;
    }
}
