package com.mltj.xxks.bean;

import java.io.Serializable;

public class Ranking implements Serializable {
    private int userId;
    private String name;
    private String sumDuration;

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

    public String getSumDuration() {
        return sumDuration;
    }

    public void setSumDuration(String sumDuration) {
        this.sumDuration = sumDuration;
    }
}
