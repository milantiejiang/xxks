package com.mltj.xxks.bean;

import java.io.Serializable;

public class DateCategory implements Serializable {
    private String categoryCnName;
    private String categoryEnName;
    private int categoryId;
    private String createDate;
    private int id;
    private String remark;
    private String typeCnName;
    private String typeEnName;
    private int typeId;

    public String getCategoryCnName() {
        return categoryCnName;
    }

    public void setCategoryCnName(String categoryCnName) {
        this.categoryCnName = categoryCnName;
    }

    public String getCategoryEnName() {
        return categoryEnName;
    }

    public void setCategoryEnName(String categoryEnName) {
        this.categoryEnName = categoryEnName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTypeCnName() {
        return typeCnName;
    }

    public void setTypeCnName(String typeCnName) {
        this.typeCnName = typeCnName;
    }

    public String getTypeEnName() {
        return typeEnName;
    }

    public void setTypeEnName(String typeEnName) {
        this.typeEnName = typeEnName;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }
}
