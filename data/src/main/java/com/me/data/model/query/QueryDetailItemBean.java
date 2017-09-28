package com.me.data.model.query;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wyc on 2017/5/22.
 */

public class QueryDetailItemBean implements Serializable{
	private int qaType;//问题出处的类型: 1=试题 2= 图书，3课件，4教辅
	private String lastContent;//学员最后一次提问内容
	private String qaDetailUrl;//	答疑详情url
	private String lectureDetailUrl;//	讲义段详情url(当qaType=3 时,该属性不为空)
	private String questionId;//	试题id(当qaType=1 时,该属性不为空)
	private boolean collect;//	当前学员是否收藏该答疑
	private boolean canLike;//	当前学员能否点赞 
	private boolean like;//	当前学员是否点赞
	private int likeNum;//	当前点赞数
	private boolean canEvaluate;//	当前学员能否评价答疑 
	private boolean canClosely;//	当前学员能否追问答疑
	private boolean canUpdate;//	当前学员能否修改该答疑
	private boolean canCancel;//	当前学员能否取消该答疑
	private List<QueryJudgeBean> evaluateLabels;//评价的详情
	private String title;//标题
	private int childrenNum;
	private int answerStatus;//回答状态:0=未回答1=已回答
	private String memberId;//用户id
	
	public QueryDetailItemBean() {
	}

	public int getAnswerStatus() {
		return answerStatus;
	}

	public void setAnswerStatus(int answerStatus) {
		this.answerStatus = answerStatus;
	}

	public int getLikeNum() {
		return likeNum;
	}

	public void setLikeNum(int likeNum) {
		this.likeNum = likeNum;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getChildrenNum() {
		return childrenNum;
	}

	public void setChildrenNum(int childrenNum) {
		this.childrenNum = childrenNum;
	}

	public int getQaType() {
		return qaType;
	}

	public void setQaType(int qaType) {
		this.qaType = qaType;
	}

	public String getLastContent() {
		return lastContent;
	}

	public void setLastContent(String lastContent) {
		this.lastContent = lastContent;
	}

	public String getQaDetailUrl() {
		return qaDetailUrl;
	}

	public void setQaDetailUrl(String qaDetailUrl) {
		this.qaDetailUrl = qaDetailUrl;
	}

	public String getLectureDetailUrl() {
		return lectureDetailUrl;
	}

	public void setLectureDetailUrl(String lectureDetailUrl) {
		this.lectureDetailUrl = lectureDetailUrl;
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public boolean isCollect() {
		return collect;
	}

	public void setCollect(boolean collect) {
		this.collect = collect;
	}

	public boolean isCanLike() {
		return canLike;
	}

	public void setCanLike(boolean canLike) {
		this.canLike = canLike;
	}

	public boolean isLike() {
		return like;
	}

	public void setLike(boolean like) {
		this.like = like;
	}

	public boolean isCanEvaluate() {
		return canEvaluate;
	}

	public void setCanEvaluate(boolean canEvaluate) {
		this.canEvaluate = canEvaluate;
	}

	public boolean isCanClosely() {
		return canClosely;
	}

	public void setCanClosely(boolean canClosely) {
		this.canClosely = canClosely;
	}

	public boolean isCanUpdate() {
		return canUpdate;
	}

	public void setCanUpdate(boolean canUpdate) {
		this.canUpdate = canUpdate;
	}

	public boolean isCanCancel() {
		return canCancel;
	}

	public void setCanCancel(boolean canCancel) {
		this.canCancel = canCancel;
	}

	public List<QueryJudgeBean> getEvaluateLabels() {
		return evaluateLabels;
	}

	public void setEvaluateLabels(List<QueryJudgeBean> evaluateLabels) {
		this.evaluateLabels = evaluateLabels;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
}
