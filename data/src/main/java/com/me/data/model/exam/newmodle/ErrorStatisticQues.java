package com.me.data.model.exam.newmodle;

/**
 * Created by fzw on 2017/6/13 0013.
 */

public class ErrorStatisticQues {


    /**
     * count : 错题次数（默认展示count，倒序）
     * importance : 星级，数字是几代表几星
     * kpId : 知识点Id
     * kpName : 知识点名称
     * questionCount : 做错的试题数（举例：同一道题错两次count=2，questionCount=1）
     * sSubjectId : 考季id
     */

    private String count;
    private String importance;
    private String kpId;
    private String kpName;
    private String questionCount;
    private String sSubjectId;

    /**
     * 自增字段
     * @return
     */
    private boolean hasVideo;//是否有视频
    private ErrorStatisticVideo errorStatisticVideo;

    public ErrorStatisticVideo getErrorStatisticVideo() {
        return errorStatisticVideo;
    }

    public void setErrorStatisticVideo(ErrorStatisticVideo errorStatisticVideo) {
        this.errorStatisticVideo = errorStatisticVideo;
    }

    public boolean isHasVideo() {
        return hasVideo;
    }

    public void setHasVideo(boolean hasVideo) {
        this.hasVideo = hasVideo;
    }

    public String getsSubjectId() {

        return sSubjectId;
    }

    public void setsSubjectId(String sSubjectId) {
        this.sSubjectId = sSubjectId;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getImportance() {
        return importance;
    }

    public void setImportance(String importance) {
        this.importance = importance;
    }

    public String getKpId() {
        return kpId;
    }

    public void setKpId(String kpId) {
        this.kpId = kpId;
    }

    public String getKpName() {
        return kpName;
    }

    public void setKpName(String kpName) {
        this.kpName = kpName;
    }

    public String getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(String questionCount) {
        this.questionCount = questionCount;
    }

}
