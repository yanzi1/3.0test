package com.me.data.model.exam.newmodle;

import java.util.HashMap;
import java.util.List;

/**
 * Created by fzw on 2017/6/6 0006.
 * 我的错题试卷
 */

public class MyWrongExamPaper {
    /**
     * paperVo 试卷对象
     * totalPage 总页数
     * isCollectQuesVo 是否已经收藏试题
     * isCollectVideoVo 是否关联视频
     */
    private PaperVo paperVo;
    private int count;
    private List<CollectQuesVo> isCollectQuesVo;
    private String isCollectVideoVo;

    private HashMap<String,String> videoMap;

    public HashMap<String, String> getVideoMap() {
        return videoMap;
    }

    public void setVideoMap(HashMap<String, String> videoMap) {
        this.videoMap = videoMap;
    }

    public List<CollectQuesVo> getIsCollectQuesVo() {
        return isCollectQuesVo;
    }

    public void setIsCollectQuesVo(List<CollectQuesVo> isCollectQuesVo) {
        this.isCollectQuesVo = isCollectQuesVo;
    }

    public String getIsCollectVideoVo() {
        return isCollectVideoVo;
    }

    public void setIsCollectVideoVo(String isCollectVideoVo) {
        this.isCollectVideoVo = isCollectVideoVo;
    }

    public PaperVo getPaperVo() {
        return paperVo;
    }

    public void setPaperVo(PaperVo paperVo) {
        this.paperVo = paperVo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
