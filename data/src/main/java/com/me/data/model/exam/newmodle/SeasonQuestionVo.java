package com.me.data.model.exam.newmodle;

import java.util.List;

/**
 * Created by fzw on 2017/5/25 0025.
 * 试题对象
 */

public class SeasonQuestionVo {

    /**
     * questionId : 试题ID
     * examId : 项目ID
     * subjectId : 科目ID
     * sSubjectId : 考季ID
     * isGroup : 是否题帽题
     * groupId : 题帽ID
     * difficultyLevel : 难易程度：1易2中3难
     * shortTitle : 试题标题
     * choicetypeId : 题型ID
     * status : 试题状态
     * title :  题干
     * rightAnswers : 正确答案
     * solutions :  解题思路
     * analysis :  试题解析
     * optionList : 试题选项列表
     * kpIds : 试题在该考季关联的知识点（,分隔）
     * classifyNames : 机考
     * subObjType : 主客观类型：1主观，2客观
     * answerStrategy : 答案录入形式：数据字典
     * choicetypeName : 题型名称
     * bookId : 书id
     * chapterId : 章Id
     * pageNum : 页码
     * knowledgeVoList : 试题关联的知识点vo列表
     * questionNum : 试题在书中的题号
     * questionList : 	题帽题小题列表，列表中的对象为SeasonQuestionVo类型
     *
     * SeasonQuestionVo 未标注字段
     * seq : null
     * updateBy : null
     * defaultScoreValue : null
     * auditor : null
     * auditDate : null
     * createBy : admin
     * createDate : null
     * updateDate : null
     * cyAnswerType : null
     * cySubtopicNum : null
     * cyQuestionId : null
     * cyOperationVideo : null
     * optionType : null
     * classifyIds : null
     * seaquStatus : null
     * seaquUpdateBy : null
     * seaquUpdateDate : null
     * choicetypeVo : null
     * isRight : null
     * myAnswer : null
     * isCollected : null
     * collectId : null
     * lectureId : null
     */

    private long questionId;
    private long examId;
    private long subjectId;
    private long sSubjectId;
    private int isGroup;
    private String groupId;
    private Object seq;
    private int difficultyLevel;
    private float defaultScoreValue;
    private String shortTitle;
    private long choicetypeId;
    private int status;
    private Object auditor;
    private Object auditDate;
    private String createBy;
    private String createDate;
    private String updateBy;
    private String updateDate;
    private String title;
    private String rightAnswers;
    private String solutions;
    private String analysis;
    private Object cyAnswerType;
    private Object cySubtopicNum;
    private Object cyQuestionId;
    private Object cyOperationVideo;
    private Object optionType;
    private String kpIds;
    private String classifyIds;
    private String classifyNames;
    private String choicetypeName;
    private int subObjType;
    private int answerStrategy;
    private int seaquStatus;
    private String seaquUpdateBy;
    private String seaquUpdateDate;
    private long bookId;
    private long chapterId;
    private int pageNum;
    private int questionNum;
    private List<QuestionInfoOptionVo> optionList;
    private List<KnowledgeVo> knowledgeVoList;
    private List<SeasonQuestionVo> questionList;
    private Object choicetypeVo;
    private String isRight;
    private String myAnswer;
    private boolean isCollected;
    private String collectId;
    private Object lectureId;
    /**
     * 自增字段
     */
    //是否显示了答案
    private boolean isHaveShowAns;
    //错题集合
    private List<SeasonQuestionVo> wrongQuestionList;
    /**
     * 参数记录题冒题走过后的高度，临时记录
     */
    private int compreHeight;
    private int currentTypeQuesCount;//这道题所属题型共有几道题
    private int indexInCurrentTypeList;//这道题在该类型题目中的角标
    private int currentTypeQuesCountWrong;//查看错题时这道题所属题型共有几道题
    private CacheMemberExamVo cacheMemberExamVo;//用于提交试卷的modle
    private String errorId;//我的错题删除时使用的id
    private boolean isHaveVideo;//是否有关联视频讲解
    private String videoId;//关联的视频讲次id
    private int childIndex;//如果是题冒题的小题，则该题在题冒题中的角标
    private String largeSegmentName;//大题名称 (如果该题是题冒题小题，则此值为大题的名称)
    private long answerTime;//作答时间
    private int childQuesCount;//如果是题冒题，则其含有几个小题
    private int childIndexInWrong;//如果是题冒题的小题，则该题在错题解析中的角标
    private int childQuesCountInWrong;//如果是题冒题的小题，则在错题解析时该大题中有几道小题是错误的

    public int getChildQuesCountInWrong() {
        return childQuesCountInWrong;
    }

    public void setChildQuesCountInWrong(int childQuesCountInWrong) {
        this.childQuesCountInWrong = childQuesCountInWrong;
    }

    public int getChildIndexInWrong() {
        return childIndexInWrong;
    }

    public void setChildIndexInWrong(int childIndexInWrong) {
        this.childIndexInWrong = childIndexInWrong;
    }

    public int getChildQuesCount() {
        return childQuesCount;
    }

    public void setChildQuesCount(int childQuesCount) {
        this.childQuesCount = childQuesCount;
    }

    public long getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(long answerTime) {
        this.answerTime = answerTime;
    }

    public String getLargeSegmentName() {
        return largeSegmentName;
    }

    public void setLargeSegmentName(String largeSegmentName) {
        this.largeSegmentName = largeSegmentName;
    }

    public int getChildIndex() {
        return childIndex;
    }

    public void setChildIndex(int childIndex) {
        this.childIndex = childIndex;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public boolean isHaveVideo() {
        return isHaveVideo;
    }

    public void setHaveVideo(boolean haveVideo) {
        isHaveVideo = haveVideo;
    }

    public String getErrorId() {
        return errorId;
    }

    public void setErrorId(String errorId) {
        this.errorId = errorId;
    }

    public int getIndexInCurrentTypeList() {
        return indexInCurrentTypeList;
    }

    public void setIndexInCurrentTypeList(int indexInCurrentTypeList) {
        this.indexInCurrentTypeList = indexInCurrentTypeList;
    }

    public CacheMemberExamVo getCacheMemberExamVo() {
        return cacheMemberExamVo;
    }

    public void setCacheMemberExamVo(CacheMemberExamVo cacheMemberExamVo) {
        this.cacheMemberExamVo = cacheMemberExamVo;
    }

    public int getCurrentTypeQuesCountWrong() {
        return currentTypeQuesCountWrong;
    }

    public void setCurrentTypeQuesCountWrong(int currentTypeQuesCountWrong) {
        this.currentTypeQuesCountWrong = currentTypeQuesCountWrong;
    }

    public List<SeasonQuestionVo> getWrongQuestionList() {
        return wrongQuestionList;
    }

    public void setWrongQuestionList(List<SeasonQuestionVo> wrongQuestionList) {
        this.wrongQuestionList = wrongQuestionList;
    }

    public int getCurrentTypeQuesCount() {
        return currentTypeQuesCount;
    }

    public void setCurrentTypeQuesCount(int currentTypeQuesCount) {
        this.currentTypeQuesCount = currentTypeQuesCount;
    }

    public int getCompreHeight() {
        return compreHeight;
    }

    public void setCompreHeight(int compreHeight) {
        this.compreHeight = compreHeight;
    }

    public boolean isHaveShowAns() {
        return isHaveShowAns;
    }

    public void setHaveShowAns(boolean haveShowAns) {
        isHaveShowAns = haveShowAns;
    }

    public long getsSubjectId() {
        return sSubjectId;
    }

    public void setsSubjectId(long sSubjectId) {
        this.sSubjectId = sSubjectId;
    }

    public String getGroupId() {
        return groupId;
    }

    public long getChapterId() {
        return chapterId;
    }

    public void setIsRight(String isRight) {
        this.isRight = isRight;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public void setChapterId(long chapterId) {
        this.chapterId = chapterId;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public void setQuestionNum(int questionNum) {
        this.questionNum = questionNum;
    }

    public List<QuestionInfoOptionVo> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<QuestionInfoOptionVo> optionList) {
        this.optionList = optionList;
    }

    public List<KnowledgeVo> getKnowledgeVoList() {
        return knowledgeVoList;
    }

    public void setKnowledgeVoList(List<KnowledgeVo> knowledgeVoList) {
        this.knowledgeVoList = knowledgeVoList;
    }

    public void setQuestionList(List<SeasonQuestionVo> questionList) {
        this.questionList = questionList;
    }

    public String isRight() {
        return isRight;
    }

    public void setRight(String right) {
        isRight = right;
    }

    public void setMyAnswer(String myAnswer) {
        this.myAnswer = myAnswer;
    }

    public boolean isCollected() {
        return isCollected;
    }

    public void setCollected(boolean collected) {
        isCollected = collected;
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public long getExamId() {
        return examId;
    }

    public void setExamId(long examId) {
        this.examId = examId;
    }

    public long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(long subjectId) {
        this.subjectId = subjectId;
    }

    public long getSSubjectId() {
        return sSubjectId;
    }

    public void setSSubjectId(long sSubjectId) {
        this.sSubjectId = sSubjectId;
    }

    public int getIsGroup() {
        return isGroup;
    }

    public void setIsGroup(int isGroup) {
        this.isGroup = isGroup;
    }


    public Object getSeq() {
        return seq;
    }

    public void setSeq(Object seq) {
        this.seq = seq;
    }

    public int getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(int difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public float getDefaultScoreValue() {
        return defaultScoreValue;
    }

    public void setDefaultScoreValue(float defaultScoreValue) {
        this.defaultScoreValue = defaultScoreValue;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public long getChoicetypeId() {
        return choicetypeId;
    }

    public void setChoicetypeId(long choicetypeId) {
        this.choicetypeId = choicetypeId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getAuditor() {
        return auditor;
    }

    public void setAuditor(Object auditor) {
        this.auditor = auditor;
    }

    public Object getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(Object auditDate) {
        this.auditDate = auditDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRightAnswers() {
        return rightAnswers;
    }

    public void setRightAnswers(String rightAnswers) {
        this.rightAnswers = rightAnswers;
    }

    public String getSolutions() {
        return solutions;
    }

    public void setSolutions(String solutions) {
        this.solutions = solutions;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public Object getCyAnswerType() {
        return cyAnswerType;
    }

    public void setCyAnswerType(Object cyAnswerType) {
        this.cyAnswerType = cyAnswerType;
    }

    public Object getCySubtopicNum() {
        return cySubtopicNum;
    }

    public void setCySubtopicNum(Object cySubtopicNum) {
        this.cySubtopicNum = cySubtopicNum;
    }

    public Object getCyQuestionId() {
        return cyQuestionId;
    }

    public void setCyQuestionId(Object cyQuestionId) {
        this.cyQuestionId = cyQuestionId;
    }

    public Object getCyOperationVideo() {
        return cyOperationVideo;
    }

    public void setCyOperationVideo(Object cyOperationVideo) {
        this.cyOperationVideo = cyOperationVideo;
    }

    public Object getOptionType() {
        return optionType;
    }

    public void setOptionType(Object optionType) {
        this.optionType = optionType;
    }

    public String getKpIds() {
        return kpIds;
    }

    public void setKpIds(String kpIds) {
        this.kpIds = kpIds;
    }

    public String getClassifyIds() {
        return classifyIds;
    }

    public void setClassifyIds(String classifyIds) {
        this.classifyIds = classifyIds;
    }

    public String getClassifyNames() {
        return classifyNames;
    }

    public void setClassifyNames(String classifyNames) {
        this.classifyNames = classifyNames;
    }

    public String getChoicetypeName() {
        return choicetypeName;
    }

    public void setChoicetypeName(String choicetypeName) {
        this.choicetypeName = choicetypeName;
    }

    public int getSubObjType() {
        return subObjType;
    }

    public void setSubObjType(int subObjType) {
        this.subObjType = subObjType;
    }

    public int getAnswerStrategy() {
        return answerStrategy;
    }

    public void setAnswerStrategy(int answerStrategy) {
        this.answerStrategy = answerStrategy;
    }

    public int getSeaquStatus() {
        return seaquStatus;
    }

    public void setSeaquStatus(int seaquStatus) {
        this.seaquStatus = seaquStatus;
    }

    public String getSeaquUpdateBy() {
        return seaquUpdateBy;
    }

    public void setSeaquUpdateBy(String seaquUpdateBy) {
        this.seaquUpdateBy = seaquUpdateBy;
    }

    public String getSeaquUpdateDate() {
        return seaquUpdateDate;
    }

    public void setSeaquUpdateDate(String seaquUpdateDate) {
        this.seaquUpdateDate = seaquUpdateDate;
    }

    public Object getBookId() {
        return bookId;
    }



    public Object getPageNum() {
        return pageNum;
    }


    public Object getQuestionNum() {
        return questionNum;
    }


    public List<SeasonQuestionVo> getQuestionList() {
        return questionList;
    }


    public Object getChoicetypeVo() {
        return choicetypeVo;
    }

    public void setChoicetypeVo(Object choicetypeVo) {
        this.choicetypeVo = choicetypeVo;
    }

    public Object getIsRight() {
        return isRight;
    }


    public String getMyAnswer() {
        return myAnswer;
    }


    public boolean isIsCollected() {
        return isCollected;
    }

    public void setIsCollected(boolean isCollected) {
        this.isCollected = isCollected;
    }

    public String getCollectId() {
        return collectId;
    }

    public void setCollectId(String collectId) {
        this.collectId = collectId;
    }

    public Object getLectureId() {
        return lectureId;
    }

    public void setLectureId(Object lectureId) {
        this.lectureId = lectureId;
    }
}
