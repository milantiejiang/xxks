package com.mltj.xxks.bean;

import java.util.List;

/**
 * @Description:问题的实体类
 * @author：hzc
 */
public class QuestionBean extends BaseBean {
    private String createDate;
    private int fraction;
    private int id;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String questionContent;
    private List<QuestionOptionBean> questionOptions;
    private String questionType;
    private String referenceAnswer;
    private String topicAnalysis;
    private String topicCategory;
    private int userId;
    private boolean showAnswerAnalysis;
    private int singleScore;
    private int countAmmout;

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getFraction() {
        return fraction;
    }

    public void setFraction(int fraction) {
        this.fraction = fraction;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public List<QuestionOptionBean> getQuestionOptions() {
        return questionOptions;
    }

    public void setQuestionOptions(List<QuestionOptionBean> questionOptions) {
        this.questionOptions = questionOptions;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getReferenceAnswer() {
        return referenceAnswer;
    }

    public void setReferenceAnswer(String referenceAnswer) {
        this.referenceAnswer = referenceAnswer;
    }

    public String getTopicAnalysis() {
        return topicAnalysis;
    }

    public void setTopicAnalysis(String topicAnalysis) {
        this.topicAnalysis = topicAnalysis;
    }

    public String getTopicCategory() {
        return topicCategory;
    }

    public void setTopicCategory(String topicCategory) {
        this.topicCategory = topicCategory;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isShowAnswerAnalysis() {
        return showAnswerAnalysis;
    }

    public void setShowAnswerAnalysis(boolean showAnswerAnalysis) {
        this.showAnswerAnalysis = showAnswerAnalysis;
    }

    public int getSingleScore() {
        return singleScore;
    }

    public void setSingleScore(int singleScore) {
        this.singleScore = singleScore;
    }

    public int getCountAmmout() {
        return countAmmout;
    }

    public void setCountAmmout(int countAmmout) {
        this.countAmmout = countAmmout;
    }
}
