package com.me.data.model.main;

import com.me.data.model.exam.newmodle.StartOrContinueExam;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jjr on 2017/6/7.
 */

public class CaptureResultBean implements Serializable{

//    private ExaminationVo examinationVo;
    private StartOrContinueExam examinationVo;
    private List<CourseVoBean> courseVo;

//    public StartOrContinueExam getStartOrContinueExam() {
//        return examinationVo;
//    }
//
//    public void setStartOrContinueExam(StartOrContinueExam startOrContinueExam) {
//        this.examinationVo = startOrContinueExam;
//    }

    public StartOrContinueExam getExaminationVo() {
        return examinationVo;
    }

    public void setExaminationVo(StartOrContinueExam examinationVo) {
        this.examinationVo = examinationVo;
    }

    public List<CourseVoBean> getCourseVo() {
        return courseVo;
    }

    public void setCourseVo(List<CourseVoBean> courseVo) {
        this.courseVo = courseVo;
    }

    public static class CourseVoBean implements Serializable {

        /**
         * startTime : 00:00:00
         * id : 64
         * name : 体验课无章测试(2)
         * endTime : 00:01:28
         * totalTime : 00:02:48
         */

        private String startTime;
        private int id;
        private String name;
        private String endTime;
        private String totalTime;

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getTotalTime() {
            return totalTime;
        }

        public void setTotalTime(String totalTime) {
            this.totalTime = totalTime;
        }
    }

    public static class ExaminationVo implements Serializable {

        private String isCollectQuesVo;
        private String isCollectVideoVo;
        private PaperVo paperVo;

        public String getIsCollectQuesVo() {
            return isCollectQuesVo;
        }

        public void setIsCollectQuesVo(String isCollectQuesVo) {
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

        public static class PaperVo implements Serializable {
            /**
             * canDownload : 0
             * choicetypeList : [{"choicetypeId":"1001","description":"","id":"4029989","name":"单项选择题","paperId":"1651354","questionCount":"37","questionList":[{"groupId":"","id":"181421","isGroup":"0","paperChoiceTypeId":"4029989","paperId":"1651354","questionId":"1190921","score":1.5,"seasonQuestionVo":{"analysis":"daw","answerStrategy":"2","auditDate":"","auditor":"","bookId":"1122","chapterId":"2198","choicetypeId":"1001","choicetypeName":"单项选择题","choicetypeVo":"","classifyIds":"","classifyNames":"","collectId":"","createBy":"teacher1562","createDate":1494319554000,"cyAnswerType":"","cyOperationVideo":"","cyQuestionId":"","cySubtopicNum":"","defaultScoreValue":1.5,"difficultyLevel":"1","examId":"1002","groupId":"","isCollected":"false","isGroup":"0","isRight":"","knowledgeVoList":[{"bookId":"1005","chapterId":"1763","importance":"2","kpId":"42865","pageNum":"4","sSubjectId":"1005","showName":"会计信息质量要求"}],"kpIds":"42865","lectureId":"","myAnswer":"","optionList":[{"description":"重要性","id":"679355","questionId":"1190921","sort":"1","tag":"A"}],"pageNum":"32","questionId":"1190921","questionList":"","questionNum":"1","rightAnswers":"B","sSubjectId":"1005","seaquStatus":"2","seaquUpdateBy":"teacher1562","seaquUpdateDate":1494319554000,"seq":"","shortTitle":"企业应当以实际发生","solutions":"","status":"2","subObjType":"2","subjectId":"1003","title":"企业应当以实际发生的交易或事","updateBy":"teacher1562","updateDate":1494319554000},"sort":"37"}],"score":55,"sort":"80"}]
             * createBy : teacher1562
             * createDate : 1494407633000
             * deleteStatus : 0
             * determinant :
             * difficultyLevel : 2
             * downloadUrl :
             * effectiveDate :
             * endCreateDate :
             * examId : 1002
             * id : 1651354
             * limitedTime : 120
             * maxTotalScore :
             * minTotalScore :
             * moment :
             * name : 第一周 本周自测
             * papertypeId : 1012
             * papertypeName :
             * passingScore : 87.6
             * sSubjectId : 1005
             * source :
             * startCreateDate :
             * status : 2
             * totalScore : 146
             * updateBy : teacher1562
             * updateDate : 1494946922000
             */

            private String canDownload;
            private String createBy;
            private long createDate;
            private String deleteStatus;
            private String determinant;
            private String difficultyLevel;
            private String downloadUrl;
            private String effectiveDate;
            private String endCreateDate;
            private String examId;
            private String id;
            private String limitedTime;
            private String maxTotalScore;
            private String minTotalScore;
            private String moment;
            private String name;
            private String papertypeId;
            private String papertypeName;
            private double passingScore;
            private String sSubjectId;
            private String source;
            private String startCreateDate;
            private String status;
            private int totalScore;
            private String updateBy;
            private long updateDate;
            private List<ChoicetypeListBean> choicetypeList;

            public String getCanDownload() {
                return canDownload;
            }

            public void setCanDownload(String canDownload) {
                this.canDownload = canDownload;
            }

            public String getCreateBy() {
                return createBy;
            }

            public void setCreateBy(String createBy) {
                this.createBy = createBy;
            }

            public long getCreateDate() {
                return createDate;
            }

            public void setCreateDate(long createDate) {
                this.createDate = createDate;
            }

            public String getDeleteStatus() {
                return deleteStatus;
            }

            public void setDeleteStatus(String deleteStatus) {
                this.deleteStatus = deleteStatus;
            }

            public String getDeterminant() {
                return determinant;
            }

            public void setDeterminant(String determinant) {
                this.determinant = determinant;
            }

            public String getDifficultyLevel() {
                return difficultyLevel;
            }

            public void setDifficultyLevel(String difficultyLevel) {
                this.difficultyLevel = difficultyLevel;
            }

            public String getDownloadUrl() {
                return downloadUrl;
            }

            public void setDownloadUrl(String downloadUrl) {
                this.downloadUrl = downloadUrl;
            }

            public String getEffectiveDate() {
                return effectiveDate;
            }

            public void setEffectiveDate(String effectiveDate) {
                this.effectiveDate = effectiveDate;
            }

            public String getEndCreateDate() {
                return endCreateDate;
            }

            public void setEndCreateDate(String endCreateDate) {
                this.endCreateDate = endCreateDate;
            }

            public String getExamId() {
                return examId;
            }

            public void setExamId(String examId) {
                this.examId = examId;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getLimitedTime() {
                return limitedTime;
            }

            public void setLimitedTime(String limitedTime) {
                this.limitedTime = limitedTime;
            }

            public String getMaxTotalScore() {
                return maxTotalScore;
            }

            public void setMaxTotalScore(String maxTotalScore) {
                this.maxTotalScore = maxTotalScore;
            }

            public String getMinTotalScore() {
                return minTotalScore;
            }

            public void setMinTotalScore(String minTotalScore) {
                this.minTotalScore = minTotalScore;
            }

            public String getMoment() {
                return moment;
            }

            public void setMoment(String moment) {
                this.moment = moment;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPapertypeId() {
                return papertypeId;
            }

            public void setPapertypeId(String papertypeId) {
                this.papertypeId = papertypeId;
            }

            public String getPapertypeName() {
                return papertypeName;
            }

            public void setPapertypeName(String papertypeName) {
                this.papertypeName = papertypeName;
            }

            public double getPassingScore() {
                return passingScore;
            }

            public void setPassingScore(double passingScore) {
                this.passingScore = passingScore;
            }

            public String getSSubjectId() {
                return sSubjectId;
            }

            public void setSSubjectId(String sSubjectId) {
                this.sSubjectId = sSubjectId;
            }

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }

            public String getStartCreateDate() {
                return startCreateDate;
            }

            public void setStartCreateDate(String startCreateDate) {
                this.startCreateDate = startCreateDate;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public int getTotalScore() {
                return totalScore;
            }

            public void setTotalScore(int totalScore) {
                this.totalScore = totalScore;
            }

            public String getUpdateBy() {
                return updateBy;
            }

            public void setUpdateBy(String updateBy) {
                this.updateBy = updateBy;
            }

            public long getUpdateDate() {
                return updateDate;
            }

            public void setUpdateDate(long updateDate) {
                this.updateDate = updateDate;
            }

            public List<ChoicetypeListBean> getChoicetypeList() {
                return choicetypeList;
            }

            public void setChoicetypeList(List<ChoicetypeListBean> choicetypeList) {
                this.choicetypeList = choicetypeList;
            }

            public static class ChoicetypeListBean {
                /**
                 * choicetypeId : 1001
                 * description :
                 * id : 4029989
                 * name : 单项选择题
                 * paperId : 1651354
                 * questionCount : 37
                 * questionList : [{"groupId":"","id":"181421","isGroup":"0","paperChoiceTypeId":"4029989","paperId":"1651354","questionId":"1190921","score":1.5,"seasonQuestionVo":{"analysis":"daw","answerStrategy":"2","auditDate":"","auditor":"","bookId":"1122","chapterId":"2198","choicetypeId":"1001","choicetypeName":"单项选择题","choicetypeVo":"","classifyIds":"","classifyNames":"","collectId":"","createBy":"teacher1562","createDate":1494319554000,"cyAnswerType":"","cyOperationVideo":"","cyQuestionId":"","cySubtopicNum":"","defaultScoreValue":1.5,"difficultyLevel":"1","examId":"1002","groupId":"","isCollected":"false","isGroup":"0","isRight":"","knowledgeVoList":[{"bookId":"1005","chapterId":"1763","importance":"2","kpId":"42865","pageNum":"4","sSubjectId":"1005","showName":"会计信息质量要求"}],"kpIds":"42865","lectureId":"","myAnswer":"","optionList":[{"description":"重要性","id":"679355","questionId":"1190921","sort":"1","tag":"A"}],"pageNum":"32","questionId":"1190921","questionList":"","questionNum":"1","rightAnswers":"B","sSubjectId":"1005","seaquStatus":"2","seaquUpdateBy":"teacher1562","seaquUpdateDate":1494319554000,"seq":"","shortTitle":"企业应当以实际发生","solutions":"","status":"2","subObjType":"2","subjectId":"1003","title":"企业应当以实际发生的交易或事","updateBy":"teacher1562","updateDate":1494319554000},"sort":"37"}]
                 * score : 55
                 * sort : 80
                 */

                private String choicetypeId;
                private String description;
                private String id;
                private String name;
                private String paperId;
                private String questionCount;
                private int score;
                private String sort;
                private List<QuestionListBean> questionList;

                public String getChoicetypeId() {
                    return choicetypeId;
                }

                public void setChoicetypeId(String choicetypeId) {
                    this.choicetypeId = choicetypeId;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getPaperId() {
                    return paperId;
                }

                public void setPaperId(String paperId) {
                    this.paperId = paperId;
                }

                public String getQuestionCount() {
                    return questionCount;
                }

                public void setQuestionCount(String questionCount) {
                    this.questionCount = questionCount;
                }

                public int getScore() {
                    return score;
                }

                public void setScore(int score) {
                    this.score = score;
                }

                public String getSort() {
                    return sort;
                }

                public void setSort(String sort) {
                    this.sort = sort;
                }

                public List<QuestionListBean> getQuestionList() {
                    return questionList;
                }

                public void setQuestionList(List<QuestionListBean> questionList) {
                    this.questionList = questionList;
                }

                public static class QuestionListBean {
                    /**
                     * groupId :
                     * id : 181421
                     * isGroup : 0
                     * paperChoiceTypeId : 4029989
                     * paperId : 1651354
                     * questionId : 1190921
                     * score : 1.5
                     * seasonQuestionVo : {"analysis":"daw","answerStrategy":"2","auditDate":"","auditor":"","bookId":"1122","chapterId":"2198","choicetypeId":"1001","choicetypeName":"单项选择题","choicetypeVo":"","classifyIds":"","classifyNames":"","collectId":"","createBy":"teacher1562","createDate":1494319554000,"cyAnswerType":"","cyOperationVideo":"","cyQuestionId":"","cySubtopicNum":"","defaultScoreValue":1.5,"difficultyLevel":"1","examId":"1002","groupId":"","isCollected":"false","isGroup":"0","isRight":"","knowledgeVoList":[{"bookId":"1005","chapterId":"1763","importance":"2","kpId":"42865","pageNum":"4","sSubjectId":"1005","showName":"会计信息质量要求"}],"kpIds":"42865","lectureId":"","myAnswer":"","optionList":[{"description":"重要性","id":"679355","questionId":"1190921","sort":"1","tag":"A"}],"pageNum":"32","questionId":"1190921","questionList":"","questionNum":"1","rightAnswers":"B","sSubjectId":"1005","seaquStatus":"2","seaquUpdateBy":"teacher1562","seaquUpdateDate":1494319554000,"seq":"","shortTitle":"企业应当以实际发生","solutions":"","status":"2","subObjType":"2","subjectId":"1003","title":"企业应当以实际发生的交易或事","updateBy":"teacher1562","updateDate":1494319554000}
                     * sort : 37
                     */

                    private String groupId;
                    private String id;
                    private String isGroup;
                    private String paperChoiceTypeId;
                    private String paperId;
                    private String questionId;
                    private double score;
                    private SeasonQuestionVoBean seasonQuestionVo;
                    private String sort;

                    public String getGroupId() {
                        return groupId;
                    }

                    public void setGroupId(String groupId) {
                        this.groupId = groupId;
                    }

                    public String getId() {
                        return id;
                    }

                    public void setId(String id) {
                        this.id = id;
                    }

                    public String getIsGroup() {
                        return isGroup;
                    }

                    public void setIsGroup(String isGroup) {
                        this.isGroup = isGroup;
                    }

                    public String getPaperChoiceTypeId() {
                        return paperChoiceTypeId;
                    }

                    public void setPaperChoiceTypeId(String paperChoiceTypeId) {
                        this.paperChoiceTypeId = paperChoiceTypeId;
                    }

                    public String getPaperId() {
                        return paperId;
                    }

                    public void setPaperId(String paperId) {
                        this.paperId = paperId;
                    }

                    public String getQuestionId() {
                        return questionId;
                    }

                    public void setQuestionId(String questionId) {
                        this.questionId = questionId;
                    }

                    public double getScore() {
                        return score;
                    }

                    public void setScore(double score) {
                        this.score = score;
                    }

                    public SeasonQuestionVoBean getSeasonQuestionVo() {
                        return seasonQuestionVo;
                    }

                    public void setSeasonQuestionVo(SeasonQuestionVoBean seasonQuestionVo) {
                        this.seasonQuestionVo = seasonQuestionVo;
                    }

                    public String getSort() {
                        return sort;
                    }

                    public void setSort(String sort) {
                        this.sort = sort;
                    }

                    public static class SeasonQuestionVoBean {
                        /**
                         * analysis : daw
                         * answerStrategy : 2
                         * auditDate :
                         * auditor :
                         * bookId : 1122
                         * chapterId : 2198
                         * choicetypeId : 1001
                         * choicetypeName : 单项选择题
                         * choicetypeVo :
                         * classifyIds :
                         * classifyNames :
                         * collectId :
                         * createBy : teacher1562
                         * createDate : 1494319554000
                         * cyAnswerType :
                         * cyOperationVideo :
                         * cyQuestionId :
                         * cySubtopicNum :
                         * defaultScoreValue : 1.5
                         * difficultyLevel : 1
                         * examId : 1002
                         * groupId :
                         * isCollected : false
                         * isGroup : 0
                         * isRight :
                         * knowledgeVoList : [{"bookId":"1005","chapterId":"1763","importance":"2","kpId":"42865","pageNum":"4","sSubjectId":"1005","showName":"会计信息质量要求"}]
                         * kpIds : 42865
                         * lectureId :
                         * myAnswer :
                         * optionList : [{"description":"重要性","id":"679355","questionId":"1190921","sort":"1","tag":"A"}]
                         * pageNum : 32
                         * questionId : 1190921
                         * questionList :
                         * questionNum : 1
                         * rightAnswers : B
                         * sSubjectId : 1005
                         * seaquStatus : 2
                         * seaquUpdateBy : teacher1562
                         * seaquUpdateDate : 1494319554000
                         * seq :
                         * shortTitle : 企业应当以实际发生
                         * solutions :
                         * status : 2
                         * subObjType : 2
                         * subjectId : 1003
                         * title : 企业应当以实际发生的交易或事
                         * updateBy : teacher1562
                         * updateDate : 1494319554000
                         */

                        private String analysis;
                        private String answerStrategy;
                        private String auditDate;
                        private String auditor;
                        private String bookId;
                        private String chapterId;
                        private String choicetypeId;
                        private String choicetypeName;
                        private String choicetypeVo;
                        private String classifyIds;
                        private String classifyNames;
                        private String collectId;
                        private String createBy;
                        private long createDate;
                        private String cyAnswerType;
                        private String cyOperationVideo;
                        private String cyQuestionId;
                        private String cySubtopicNum;
                        private double defaultScoreValue;
                        private String difficultyLevel;
                        private String examId;
                        private String groupId;
                        private String isCollected;
                        private String isGroup;
                        private String isRight;
                        private String kpIds;
                        private String lectureId;
                        private String myAnswer;
                        private String pageNum;
                        private String questionId;
                        private String questionList;
                        private String questionNum;
                        private String rightAnswers;
                        private String sSubjectId;
                        private String seaquStatus;
                        private String seaquUpdateBy;
                        private long seaquUpdateDate;
                        private String seq;
                        private String shortTitle;
                        private String solutions;
                        private String status;
                        private String subObjType;
                        private String subjectId;
                        private String title;
                        private String updateBy;
                        private long updateDate;
                        private List<KnowledgeVoListBean> knowledgeVoList;
                        private List<OptionListBean> optionList;

                        public String getAnalysis() {
                            return analysis;
                        }

                        public void setAnalysis(String analysis) {
                            this.analysis = analysis;
                        }

                        public String getAnswerStrategy() {
                            return answerStrategy;
                        }

                        public void setAnswerStrategy(String answerStrategy) {
                            this.answerStrategy = answerStrategy;
                        }

                        public String getAuditDate() {
                            return auditDate;
                        }

                        public void setAuditDate(String auditDate) {
                            this.auditDate = auditDate;
                        }

                        public String getAuditor() {
                            return auditor;
                        }

                        public void setAuditor(String auditor) {
                            this.auditor = auditor;
                        }

                        public String getBookId() {
                            return bookId;
                        }

                        public void setBookId(String bookId) {
                            this.bookId = bookId;
                        }

                        public String getChapterId() {
                            return chapterId;
                        }

                        public void setChapterId(String chapterId) {
                            this.chapterId = chapterId;
                        }

                        public String getChoicetypeId() {
                            return choicetypeId;
                        }

                        public void setChoicetypeId(String choicetypeId) {
                            this.choicetypeId = choicetypeId;
                        }

                        public String getChoicetypeName() {
                            return choicetypeName;
                        }

                        public void setChoicetypeName(String choicetypeName) {
                            this.choicetypeName = choicetypeName;
                        }

                        public String getChoicetypeVo() {
                            return choicetypeVo;
                        }

                        public void setChoicetypeVo(String choicetypeVo) {
                            this.choicetypeVo = choicetypeVo;
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

                        public String getCollectId() {
                            return collectId;
                        }

                        public void setCollectId(String collectId) {
                            this.collectId = collectId;
                        }

                        public String getCreateBy() {
                            return createBy;
                        }

                        public void setCreateBy(String createBy) {
                            this.createBy = createBy;
                        }

                        public long getCreateDate() {
                            return createDate;
                        }

                        public void setCreateDate(long createDate) {
                            this.createDate = createDate;
                        }

                        public String getCyAnswerType() {
                            return cyAnswerType;
                        }

                        public void setCyAnswerType(String cyAnswerType) {
                            this.cyAnswerType = cyAnswerType;
                        }

                        public String getCyOperationVideo() {
                            return cyOperationVideo;
                        }

                        public void setCyOperationVideo(String cyOperationVideo) {
                            this.cyOperationVideo = cyOperationVideo;
                        }

                        public String getCyQuestionId() {
                            return cyQuestionId;
                        }

                        public void setCyQuestionId(String cyQuestionId) {
                            this.cyQuestionId = cyQuestionId;
                        }

                        public String getCySubtopicNum() {
                            return cySubtopicNum;
                        }

                        public void setCySubtopicNum(String cySubtopicNum) {
                            this.cySubtopicNum = cySubtopicNum;
                        }

                        public double getDefaultScoreValue() {
                            return defaultScoreValue;
                        }

                        public void setDefaultScoreValue(double defaultScoreValue) {
                            this.defaultScoreValue = defaultScoreValue;
                        }

                        public String getDifficultyLevel() {
                            return difficultyLevel;
                        }

                        public void setDifficultyLevel(String difficultyLevel) {
                            this.difficultyLevel = difficultyLevel;
                        }

                        public String getExamId() {
                            return examId;
                        }

                        public void setExamId(String examId) {
                            this.examId = examId;
                        }

                        public String getGroupId() {
                            return groupId;
                        }

                        public void setGroupId(String groupId) {
                            this.groupId = groupId;
                        }

                        public String getIsCollected() {
                            return isCollected;
                        }

                        public void setIsCollected(String isCollected) {
                            this.isCollected = isCollected;
                        }

                        public String getIsGroup() {
                            return isGroup;
                        }

                        public void setIsGroup(String isGroup) {
                            this.isGroup = isGroup;
                        }

                        public String getIsRight() {
                            return isRight;
                        }

                        public void setIsRight(String isRight) {
                            this.isRight = isRight;
                        }

                        public String getKpIds() {
                            return kpIds;
                        }

                        public void setKpIds(String kpIds) {
                            this.kpIds = kpIds;
                        }

                        public String getLectureId() {
                            return lectureId;
                        }

                        public void setLectureId(String lectureId) {
                            this.lectureId = lectureId;
                        }

                        public String getMyAnswer() {
                            return myAnswer;
                        }

                        public void setMyAnswer(String myAnswer) {
                            this.myAnswer = myAnswer;
                        }

                        public String getPageNum() {
                            return pageNum;
                        }

                        public void setPageNum(String pageNum) {
                            this.pageNum = pageNum;
                        }

                        public String getQuestionId() {
                            return questionId;
                        }

                        public void setQuestionId(String questionId) {
                            this.questionId = questionId;
                        }

                        public String getQuestionList() {
                            return questionList;
                        }

                        public void setQuestionList(String questionList) {
                            this.questionList = questionList;
                        }

                        public String getQuestionNum() {
                            return questionNum;
                        }

                        public void setQuestionNum(String questionNum) {
                            this.questionNum = questionNum;
                        }

                        public String getRightAnswers() {
                            return rightAnswers;
                        }

                        public void setRightAnswers(String rightAnswers) {
                            this.rightAnswers = rightAnswers;
                        }

                        public String getSSubjectId() {
                            return sSubjectId;
                        }

                        public void setSSubjectId(String sSubjectId) {
                            this.sSubjectId = sSubjectId;
                        }

                        public String getSeaquStatus() {
                            return seaquStatus;
                        }

                        public void setSeaquStatus(String seaquStatus) {
                            this.seaquStatus = seaquStatus;
                        }

                        public String getSeaquUpdateBy() {
                            return seaquUpdateBy;
                        }

                        public void setSeaquUpdateBy(String seaquUpdateBy) {
                            this.seaquUpdateBy = seaquUpdateBy;
                        }

                        public long getSeaquUpdateDate() {
                            return seaquUpdateDate;
                        }

                        public void setSeaquUpdateDate(long seaquUpdateDate) {
                            this.seaquUpdateDate = seaquUpdateDate;
                        }

                        public String getSeq() {
                            return seq;
                        }

                        public void setSeq(String seq) {
                            this.seq = seq;
                        }

                        public String getShortTitle() {
                            return shortTitle;
                        }

                        public void setShortTitle(String shortTitle) {
                            this.shortTitle = shortTitle;
                        }

                        public String getSolutions() {
                            return solutions;
                        }

                        public void setSolutions(String solutions) {
                            this.solutions = solutions;
                        }

                        public String getStatus() {
                            return status;
                        }

                        public void setStatus(String status) {
                            this.status = status;
                        }

                        public String getSubObjType() {
                            return subObjType;
                        }

                        public void setSubObjType(String subObjType) {
                            this.subObjType = subObjType;
                        }

                        public String getSubjectId() {
                            return subjectId;
                        }

                        public void setSubjectId(String subjectId) {
                            this.subjectId = subjectId;
                        }

                        public String getTitle() {
                            return title;
                        }

                        public void setTitle(String title) {
                            this.title = title;
                        }

                        public String getUpdateBy() {
                            return updateBy;
                        }

                        public void setUpdateBy(String updateBy) {
                            this.updateBy = updateBy;
                        }

                        public long getUpdateDate() {
                            return updateDate;
                        }

                        public void setUpdateDate(long updateDate) {
                            this.updateDate = updateDate;
                        }

                        public List<KnowledgeVoListBean> getKnowledgeVoList() {
                            return knowledgeVoList;
                        }

                        public void setKnowledgeVoList(List<KnowledgeVoListBean> knowledgeVoList) {
                            this.knowledgeVoList = knowledgeVoList;
                        }

                        public List<OptionListBean> getOptionList() {
                            return optionList;
                        }

                        public void setOptionList(List<OptionListBean> optionList) {
                            this.optionList = optionList;
                        }

                        public static class KnowledgeVoListBean {
                            /**
                             * bookId : 1005
                             * chapterId : 1763
                             * importance : 2
                             * kpId : 42865
                             * pageNum : 4
                             * sSubjectId : 1005
                             * showName : 会计信息质量要求
                             */

                            private String bookId;
                            private String chapterId;
                            private String importance;
                            private String kpId;
                            private String pageNum;
                            private String sSubjectId;
                            private String showName;

                            public String getBookId() {
                                return bookId;
                            }

                            public void setBookId(String bookId) {
                                this.bookId = bookId;
                            }

                            public String getChapterId() {
                                return chapterId;
                            }

                            public void setChapterId(String chapterId) {
                                this.chapterId = chapterId;
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

                            public String getPageNum() {
                                return pageNum;
                            }

                            public void setPageNum(String pageNum) {
                                this.pageNum = pageNum;
                            }

                            public String getSSubjectId() {
                                return sSubjectId;
                            }

                            public void setSSubjectId(String sSubjectId) {
                                this.sSubjectId = sSubjectId;
                            }

                            public String getShowName() {
                                return showName;
                            }

                            public void setShowName(String showName) {
                                this.showName = showName;
                            }
                        }

                        public static class OptionListBean {
                            /**
                             * description : 重要性
                             * id : 679355
                             * questionId : 1190921
                             * sort : 1
                             * tag : A
                             */

                            private String description;
                            private String id;
                            private String questionId;
                            private String sort;
                            private String tag;

                            public String getDescription() {
                                return description;
                            }

                            public void setDescription(String description) {
                                this.description = description;
                            }

                            public String getId() {
                                return id;
                            }

                            public void setId(String id) {
                                this.id = id;
                            }

                            public String getQuestionId() {
                                return questionId;
                            }

                            public void setQuestionId(String questionId) {
                                this.questionId = questionId;
                            }

                            public String getSort() {
                                return sort;
                            }

                            public void setSort(String sort) {
                                this.sort = sort;
                            }

                            public String getTag() {
                                return tag;
                            }

                            public void setTag(String tag) {
                                this.tag = tag;
                            }
                        }
                    }
                }
            }
        }

    }

    @Override
    public String toString() {
        return "CaptureResultBean{" +
                "examinationVo=" + examinationVo +
                ", courseVo=" + courseVo +
                '}';
    }
}
