package com.mltj.xxks.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "video")
public class Video implements Serializable {
    @DatabaseField(useGetSet = true, generatedId = true, columnName = "id")
    private int id;
    @DatabaseField(useGetSet = true, columnName = "icon")
    private String icon;
    @DatabaseField(useGetSet = true, columnName = "title")
    private String title;
    @DatabaseField(useGetSet = true, columnName = "date")
    private long date;
    @DatabaseField(useGetSet = true, columnName = "url")
    private String url;
    @DatabaseField(useGetSet = true, columnName = "describe")
    private String describe;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
