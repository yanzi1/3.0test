package com.me.data.model.exam.newmodle;

import java.util.HashMap;
import java.util.List;

/**
 * Created by fzw on 2017/6/5 0005.
 * 做题记录继续做题modle
 */

public class ContinueRecordExam {

    /**
     * status 智能系统添加字段 -- 试卷状态：0：正常开始做题，1：继续做题，2：已经交卷，可判断去答题报告，3：没有绑定试卷 可给提示信息
     * memberId 学员id
     * examRecordId 做题记录id
     * isCollectQuesVo : 是否已经收藏试题
     * isCollectVideoVo : 是否关联视频
     * detailMap 学员已经作答的答题详情map key为试题id value为MemberExamDetailVo
     * paperVo 试卷对象
     * timeCost 已做了多久
     * examModel 考试模式 1 联系 2 月考 3 竞赛 4 听课答题  5 机考  6 闯关  7 智能入门测试 8 智能节点考试答题 9 节点练习模式
     * paperStatus 智能系统  试卷做题状态 0：开始做卷，1：继续做卷，2：试卷已提交
     */
    private int status;
    private String memberId;
    private String examRecordId;
    private String examModel;
    private List<CollectQuesVo> isCollectQuesVo;
    private String isCollectVideoVo;
    private HashMap<Long,MemberExamDetailVo> detailMap;
    private PaperVo paperVo;
    private long timeCost;
    private int paperStatus;

    public int getPaperStatus() {
        return paperStatus;
    }

    public void setPaperStatus(int paperStatus) {
        this.paperStatus = paperStatus;
    }

    public String getExamModel() {
        return examModel;
    }

    public void setExamModel(String examModel) {
        this.examModel = examModel;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    private HashMap<String,String> videoMap;

    public HashMap<String, String> getVideoMap() {
        return videoMap;
    }

    public void setVideoMap(HashMap<String, String> videoMap) {
        this.videoMap = videoMap;
    }

    public long getTimeCost() {
        return timeCost;
    }

    public void setTimeCost(long timeCost) {
        this.timeCost = timeCost;
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

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getExamRecordId() {
        return examRecordId;
    }

    public void setExamRecordId(String examRecordId) {
        this.examRecordId = examRecordId;
    }

    public HashMap<Long, MemberExamDetailVo> getDetailMap() {
        return detailMap;
    }

    public void setDetailMap(HashMap<Long, MemberExamDetailVo> detailMap) {
        this.detailMap = detailMap;
    }

    public PaperVo getPaperVo() {
        return paperVo;
    }

    public void setPaperVo(PaperVo paperVo) {
        this.paperVo = paperVo;
    }
}
