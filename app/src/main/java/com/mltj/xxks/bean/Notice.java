package com.mltj.xxks.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class Notice implements Serializable {
    private int id;
    private String title;
    private String introduction;
    private String content;
    private int isRelease;
    private int userId;
    private String createDate;
    private long longCreateDate;
    private ArrayList<FileJoin> fileJoins;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public ArrayList<FileJoin> getFileJoins() {
        return fileJoins;
    }

    public void setFileJoins(ArrayList<FileJoin> fileJoins) {
        this.fileJoins = fileJoins;
    }
}
