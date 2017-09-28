package com.me.data.model.intel;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.yunqing.core.db.annotations.Id;
import com.yunqing.core.db.annotations.Table;

import java.io.Serializable;

@Table(name="t_intel_weektask")
public class IntelWeekTask implements MultiItemEntity,Serializable{
    @Id
    private int dbId;
    private String nodeId;//考点id
    private String nodeName;//考点名称
    private int studyStatus=-1;//学习状态(0:未掌握 1.已掌握 2.未学习 )
    private int masterStatus=-1;
    private String duration;//时长
    private float star;//星级
    //自己加的
    private String openMsg;
    private String openDate;
    private int openState;//开课状态 1.还未到开课时间 2.还未入学测试 3.还没有内容 4.正常
    private String sujectId;
    //后续添加 用于区分子父位置
    private int position;
    private int mainPosition;

    public String getSujectId() {
        return sujectId;
    }

    public int getMasterStatus() {
        return studyStatus==-1?masterStatus:studyStatus;
    }

    public void setMasterStatus(int masterStatus) {
        this.masterStatus = masterStatus;
    }

    public void setSujectId(String sujectId) {
        this.sujectId = sujectId;
    }

    public int getDbId() {
        return dbId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getMainPosition() {
        return mainPosition;
    }

    public void setMainPosition(int mainPosition) {
        this.mainPosition = mainPosition;
    }

    public int getOpenState() {
        return openState;
    }

    public void setOpenState(int openState) {
        this.openState = openState;
    }

    public void setDbId(int dbId) {
        this.dbId = dbId;
    }

    public String getKpId() {
        return nodeId;
    }

    public void setKpId(String kpId) {
        this.nodeId = kpId;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public int getStudyStatus() {
        return studyStatus==-1?masterStatus:studyStatus;
    }

    public void setStudyStatus(int studyStatus) {
        this.studyStatus = studyStatus;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public float getStar() {
        return star;
    }

    public void setStar(float star) {
        this.star = star;
    }

    public String getOpenMsg() {
        return openMsg;
    }

    public void setOpenMsg(String openMsg) {
        this.openMsg = openMsg;
    }

    public String getOpenDate() {
        return openDate;
    }

    public void setOpenDate(String openDate) {
        this.openDate = openDate;
    }

    @Override
    public int getItemType() {
        return 1;
    }
}
