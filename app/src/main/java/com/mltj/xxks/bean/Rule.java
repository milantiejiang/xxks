package com.mltj.xxks.bean;

import java.io.Serializable;

public class Rule implements Serializable {
    private String pass;
    private String excellent;
    private int credit_value2;
    private int credit_value1;
    private int credit_value4;
    private int credit_value3;
    private int upper_limit;
    private String full_score;
    private int credit_type;
    private String cumulative_duration;
    private String credit_name;
    private int id;
    private String credit_description;

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getExcellent() {
        return excellent;
    }

    public void setExcellent(String excellent) {
        this.excellent = excellent;
    }

    public int getCredit_value2() {
        return credit_value2;
    }

    public void setCredit_value2(int credit_value2) {
        this.credit_value2 = credit_value2;
    }

    public int getCredit_value1() {
        return credit_value1;
    }

    public void setCredit_value1(int credit_value1) {
        this.credit_value1 = credit_value1;
    }

    public int getCredit_value4() {
        return credit_value4;
    }

    public void setCredit_value4(int credit_value4) {
        this.credit_value4 = credit_value4;
    }

    public int getCredit_value3() {
        return credit_value3;
    }

    public void setCredit_value3(int credit_value3) {
        this.credit_value3 = credit_value3;
    }

    public int getUpper_limit() {
        return upper_limit;
    }

    public void setUpper_limit(int upper_limit) {
        this.upper_limit = upper_limit;
    }

    public String getFull_score() {
        return full_score;
    }

    public void setFull_score(String full_score) {
        this.full_score = full_score;
    }

    public int getCredit_type() {
        return credit_type;
    }

    public void setCredit_type(int credit_type) {
        this.credit_type = credit_type;
    }

    public String getCumulative_duration() {
        return cumulative_duration;
    }

    public void setCumulative_duration(String cumulative_duration) {
        this.cumulative_duration = cumulative_duration;
    }

    public String getCredit_name() {
        return credit_name;
    }

    public void setCredit_name(String credit_name) {
        this.credit_name = credit_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCredit_description() {
        return credit_description;
    }

    public void setCredit_description(String credit_description) {
        this.credit_description = credit_description;
    }
}
