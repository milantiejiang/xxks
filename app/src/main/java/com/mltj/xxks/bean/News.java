package com.mltj.xxks.bean;

import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.ArrayList;

@DatabaseTable(tableName = "news")
public class News implements Serializable {
    private int id;
    private int newsCategory;
    private String title;
    private String introduction;
    private String content;
    private int isRelease;
    private int userId;
    private String createDate;
    private long longCreateDate;
    private ArrayList<FileJoin> fileJoins;
    private String linkUrl;
    private String sourcNews;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNewsCategory() {
        return newsCategory;
    }

    public void setNewsCategory(int newsCategory) {
        this.newsCategory = newsCategory;
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

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getSourcNews() {
        return sourcNews;
    }

    public void setSourcNews(String sourcNews) {
        this.sourcNews = sourcNews;
    }
}
