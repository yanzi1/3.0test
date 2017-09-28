package com.me.data.model.exam.newmodle;

/**
 * Created by fzw on 2017/5/25 0025.
 * 知识点对象
 */

public class KnowledgeVo {


    /**
     * sSubjectId : 考季Id
     * bookId : 书Id
     * chapterId : 章节Id
     * pageNum : 页码（仅存储知识点第一次出现的页码）
     * kpId : 	知识点id
     * showName : 知识点名称
     * importance : 重难点（数字是几就是几星）
     */

    private long sSubjectId;
    private long bookId;
    private long chapterId;
    private int pageNum;
    private long kpId;
    private String showName;
    private int importance;

    /**
     * 自增
     * @return
     */
    private KnowledgeVoVideo knowledgeVoVideo;

    public KnowledgeVoVideo getKnowledgeVoVideo() {
        return knowledgeVoVideo;
    }

    public void setKnowledgeVoVideo(KnowledgeVoVideo knowledgeVoVideo) {
        this.knowledgeVoVideo = knowledgeVoVideo;
    }

    public long getSSubjectId() {
        return sSubjectId;
    }

    public void setSSubjectId(long sSubjectId) {
        this.sSubjectId = sSubjectId;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public long getChapterId() {
        return chapterId;
    }

    public void setChapterId(long chapterId) {
        this.chapterId = chapterId;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public long getKpId() {
        return kpId;
    }

    public void setKpId(long kpId) {
        this.kpId = kpId;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public int getImportance() {
        return importance;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }
}
