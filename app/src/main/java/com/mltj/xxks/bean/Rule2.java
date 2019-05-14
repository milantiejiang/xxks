package com.mltj.xxks.bean;

import java.io.Serializable;

public class Rule2 implements Serializable {
    private int id;
    private int creditType;
    private String creditName;
    private int creditValue1;
    private int creditValue2;
    private int creditValue3;
    private int creditValue4;
    private int upperLimit;
    private int cumulativeDuration;
    private int pass;
    private int excellent;
    private int fullScore;
    private String creditDescription;
    private String integralConfigurationDescription;
    private String userEarnsPoints;
    private int score;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCreditType() {
        return creditType;
    }

    public void setCreditType(int creditType) {
        this.creditType = creditType;
    }

    public String getCreditName() {
        return creditName;
    }

    public void setCreditName(String creditName) {
        this.creditName = creditName;
    }

    public int getCreditValue1() {
        return creditValue1;
    }

    public void setCreditValue1(int creditValue1) {
        this.creditValue1 = creditValue1;
    }

    public int getCreditValue2() {
        return creditValue2;
    }

    public void setCreditValue2(int creditValue2) {
        this.creditValue2 = creditValue2;
    }

    public int getCreditValue3() {
        return creditValue3;
    }

    public void setCreditValue3(int creditValue3) {
        this.creditValue3 = creditValue3;
    }

    public int getCreditValue4() {
        return creditValue4;
    }

    public void setCreditValue4(int creditValue4) {
        this.creditValue4 = creditValue4;
    }

    public int getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(int upperLimit) {
        this.upperLimit = upperLimit;
    }

    public int getCumulativeDuration() {
        return cumulativeDuration;
    }

    public void setCumulativeDuration(int cumulativeDuration) {
        this.cumulativeDuration = cumulativeDuration;
    }

    public int getPass() {
        return pass;
    }

    public void setPass(int pass) {
        this.pass = pass;
    }

    public int getExcellent() {
        return excellent;
    }

    public void setExcellent(int excellent) {
        this.excellent = excellent;
    }

    public int getFullScore() {
        return fullScore;
    }

    public void setFullScore(int fullScore) {
        this.fullScore = fullScore;
    }

    public String getCreditDescription() {
        return creditDescription;
    }

    public void setCreditDescription(String creditDescription) {
        this.creditDescription = creditDescription;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getIntegralConfigurationDescription() {
        return integralConfigurationDescription;
    }

    public void setIntegralConfigurationDescription(String integralConfigurationDescription) {
        this.integralConfigurationDescription = integralConfigurationDescription;
    }

    public String getUserEarnsPoints() {
        return userEarnsPoints;
    }

    public void setUserEarnsPoints(String userEarnsPoints) {
        this.userEarnsPoints = userEarnsPoints;
    }
}
