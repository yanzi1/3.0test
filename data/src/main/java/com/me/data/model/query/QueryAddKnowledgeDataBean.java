package com.me.data.model.query;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dell on 2017/4/27.
 */

public class QueryAddKnowledgeDataBean implements Serializable {
	private List<QueryAddKnowledgeBean> questionList;
	private List<QueryAddKnowledgeBean> knowledgeList;

	public List<QueryAddKnowledgeBean> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(List<QueryAddKnowledgeBean> questionList) {
		this.questionList = questionList;
	}

	public List<QueryAddKnowledgeBean> getKnowledgeList() {
		return knowledgeList;
	}

	public void setKnowledgeList(List<QueryAddKnowledgeBean> knowledgeList) {
		this.knowledgeList = knowledgeList;
	}
}
