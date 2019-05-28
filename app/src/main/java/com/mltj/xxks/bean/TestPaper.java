package com.mltj.xxks.bean;

import java.io.Serializable;

public class TestPaper implements Serializable {
    private int id;
    private String examinationPaperCategory;
    private String title;
    private String introduction;
    private int passLine;
    private int totalScore;
    private int duration;
    private int isRelease;
    private int userId;
    private String createDate;
    private long longCreateDate;
    private boolean allowSubmission;


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

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public long getLongCreateDate() {
        return longCreateDate;
    }

    public void setLongCreateDate(long longCreateDate) {
        this.longCreateDate = longCreateDate;
    }

    public boolean isAllowSubmission() {
        return allowSubmission;
    }

    public void setAllowSubmission(boolean allowSubmission) {
        this.allowSubmission = allowSubmission;
    }
}
