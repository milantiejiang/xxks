package com.mltj.xxks.bean;

import java.io.Serializable;

public class ScoreRanking implements Serializable {
    private int userId;
    private String name;
    private String sumTotalScore;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSumTotalScore() {
        return sumTotalScore;
    }

    public void setSumTotalScore(String sumTotalScore) {
        this.sumTotalScore = sumTotalScore;
    }
}
