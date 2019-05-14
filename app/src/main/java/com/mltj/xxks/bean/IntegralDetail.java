package com.mltj.xxks.bean;

import java.io.Serializable;

public class IntegralDetail implements Serializable {
    private int id;
    private int userId;
    private int creditType;
    private int score;
    private String createDate;
    private String categoryCnName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCreditType() {
        return creditType;
    }

    public void setCreditType(int creditType) {
        this.creditType = creditType;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCategoryCnName() {
        return categoryCnName;
    }

    public void setCategoryCnName(String categoryCnName) {
        this.categoryCnName = categoryCnName;
    }
}
