package com.me.data.model.main;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jjr on 2017/6/17.
 */

public class MastersCourseItemBean {

    /**
     * video-url : http://v.dongaocloud.com/2a86/2a86/533/697/3a7c7e54bced20ac285579f2f15c00cd.mp4
     * video-id : 173
     * video-title : 发出存货成本的计量方法
     */

    @SerializedName("video-url")
    private String videourl;
    @SerializedName("video-id")
    private String videoid;
    @SerializedName("video-title")
    private String videotitle;

    public String getVideoUrl() {
        return videourl;
    }

    public void setVideoUrl(String videourl) {
        this.videourl = videourl;
    }

    public String getVideoId() {
        return videoid;
    }

    public void setVideoId(String videoid) {
        this.videoid = videoid;
    }

    public String getVideoTitle() {
        return videotitle;
    }

    public void setVideoTitle(String videotitle) {
        this.videotitle = videotitle;
    }
}
