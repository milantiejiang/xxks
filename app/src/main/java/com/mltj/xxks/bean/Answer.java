package com.mltj.xxks.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class Answer implements Serializable {
    private int id;
    private int position;
    private int type;
    private long[] selectoption;
    private ArrayList<String> answers;
    private String question;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long[] getSelectoption() {
        return selectoption;
    }

    public void setSelectoption(long[] selectoption) {
        this.selectoption = selectoption;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
