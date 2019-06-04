package com.mltj.xxks.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class QuestionBeanResponse2 extends BasicResponse implements Serializable {

    private TestPaper data;

    public TestPaper getData() {
        return data;
    }

    public void setData(TestPaper data) {
        this.data = data;
    }

    public static class TestPaper {
        private int id;
        private String examinationPaperCategory;
        private String title;
        private String introduction;
        private int passLine;
        private int totalScore;
        private int duration;
        private boolean showAnswerAnalysis;
        private int singleScore;
        private int countAmmout;
        private int isRelease;
        private int userId;
        private int ownerId;
        private String createDate;
        private String startDate;
        private String endDate;
        private int company;
        private String longCreateDate;
        private String fileExcel;
        private ArrayList<QuestionBean> questionBankList;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getExaminationPaperCategory() {
            return examinationPaperCategory;
        }

        public void setExaminationPaperCategory(String examinationPaperCategory) {
            this.examinationPaperCategory = examinationPaperCategory;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public int getPassLine() {
            return passLine;
        }

        public void setPassLine(int passLine) {
            this.passLine = passLine;
        }

        public int getTotalScore() {
            return totalScore;
        }

        public void setTotalScore(int totalScore) {
            this.totalScore = totalScore;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public boolean getShowAnswerAnalysis() {
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

        public int getIsRelease() {
            return isRelease;
        }

        public void setIsRelease(int isRelease) {
            this.isRelease = isRelease;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getOwnerId() {
            return ownerId;
        }

        public void setOwnerId(int ownerId) {
            this.ownerId = ownerId;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public int getCompany() {
            return company;
        }

        public void setCompany(int company) {
            this.company = company;
        }

        public String getLongCreateDate() {
            return longCreateDate;
        }

        public void setLongCreateDate(String longCreateDate) {
            this.longCreateDate = longCreateDate;
        }

        public String getFileExcel() {
            return fileExcel;
        }

        public void setFileExcel(String fileExcel) {
            this.fileExcel = fileExcel;
        }

        public ArrayList<QuestionBean> getQuestionBankList() {
            return questionBankList;
        }

        public void setQuestionBankList(ArrayList<QuestionBean> questionBankList) {
            this.questionBankList = questionBankList;
        }
    }
}
