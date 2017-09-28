package com.me.data.model.exam.newmodle;

import java.util.HashMap;
import java.util.List;

/**
 * Created by fzw on 2017/6/13 0013.
 * 错题统计
 */

public class ErrorStatistic {

    private List<ErrorStatisticQues> errorList;//知识点列表
    private HashMap<String,ErrorStatisticVideo> videoMap;//视频列表

    public List<ErrorStatisticQues> getErrorList() {
        return errorList;
    }

    public void setErrorList(List<ErrorStatisticQues> errorList) {
        this.errorList = errorList;
    }

    public HashMap<String, ErrorStatisticVideo> getVideoMap() {
        return videoMap;
    }

    public void setVideoMap(HashMap<String, ErrorStatisticVideo> videoMap) {
        this.videoMap = videoMap;
    }
}
