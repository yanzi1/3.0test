package com.me.data.model.exam;

import com.yunqing.core.db.annotations.Id;
import com.yunqing.core.db.annotations.Table;

import java.io.Serializable;
import java.util.List;

@Table(name="t_question")
public class Question implements Serializable {

	@Id
	private int dbId;
	private String userId;//用户ID
	private String typeId; //首页某一个type
	private String examId; //考试类型
	private String subjectId; //考试科目
	private String examinationId; //试卷Id
	private String questionId;
	private  int choiceType; //题目类型
	private  String title; //题目描述或者标题
	private  String score; //分数
	private  String realAnswer; //真实答案
	private  String userAnswer; //本地答案
	private boolean isCorrect;//是否正确
	private String quizAnalyze;//解析
	private String solutions;//解题思路：扫码看题1.5.0
	private  List<Option> optionList; //选项
	//添加题冒题ID
	private String groupId;

	private List<Question> questionList;
	private String type;
	
	//试卷名称
	private String examinationName;

	public String getExaminationName() {
		return examinationName;
	}

	public void setExaminationName(String examinationName) {
		this.examinationName = examinationName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	private List<RelevantPoint> pointList;//相关知识点

	public String getUserAnswer() {
		return userAnswer;
	}
	
	

	public void setUserAnswer(String userAnswer) {
		this.userAnswer = userAnswer;
	}

	public boolean isCorrect() {
		return isCorrect;
	}

	public void setIsCorrect(boolean isCorrect) {
		this.isCorrect = isCorrect;
	}

	public List<RelevantPoint> getPointList() {
		return pointList;
	}

	public void setPointList(List<RelevantPoint> pointList) {
		this.pointList = pointList;
	}

	/**
	 * 参数记录题冒题走过后的高度，临时记录
	 */
	private int compreHeight;

	private int lvHeight;//计算的在有webview的情况下的listview的高度

	public int getLvHeight() {
		return lvHeight;
	}

	public void setLvHeight(int lvHeight) {
		this.lvHeight = lvHeight;
	}

	public int getCompreHeight() {
		return compreHeight;
	}

	public void setCompreHeight(int compreHeight) {
		this.compreHeight = compreHeight;
	}

	public Question() {
		super();
	}


	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public int getDbId() {
		return dbId;
	}

	public void setDbId(int dbId) {
		this.dbId = dbId;
	}

	public String getQuizAnalyze() {
		return quizAnalyze;
	}

	public void setQuizAnalyze(String quizAnalyze) {
		this.quizAnalyze = quizAnalyze;
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public int getChoiceType() {
		return choiceType;
	}

	public void setChoiceType(int choiceType) {
		this.choiceType = choiceType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getRealAnswer() {
		return realAnswer;
	}

	public void setRealAnswer(String realAnswer) {
		this.realAnswer = realAnswer;
	}


	public List<Option> getOptionList() {
		return optionList;
	}

	public void setOptionList(List<Option> optionList) {
		this.optionList = optionList;
	}

	
	public List<Question> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(List<Question> questionList) {
		this.questionList = questionList;
	}

	public String getSolutions() {
		return solutions;
	}

	public void setSolutions(String solutions) {
		this.solutions = solutions;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public String getExamId() {
		return examId;
	}

	public void setExamId(String examId) {
		this.examId = examId;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getExaminationId() {
		return examinationId;
	}

	public void setExaminationId(String examinationId) {
		this.examinationId = examinationId;
	}
}
