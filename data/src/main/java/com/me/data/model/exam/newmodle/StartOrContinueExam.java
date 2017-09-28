package com.me.data.model.exam.newmodle;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * Created by fzw on 2017/6/7 0007.
 * 开始或重新做题
 */

public class StartOrContinueExam implements Serializable{

    /**
     * isCollectQuesVo : 是否已经收藏试题
     * isCollectVideoVo : 是否关联视频
     * paperVo : 试卷对象
     */

    private List<CollectQuesVo> isCollectQuesVo;
    private String isCollectVideoVo;
    private PaperVo paperVo;
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
}
