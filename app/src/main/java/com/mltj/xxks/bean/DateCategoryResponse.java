package com.mltj.xxks.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class DateCategoryResponse extends BasicResponse implements Serializable {

    private ArrayList<DateCategory> data;

    public ArrayList<DateCategory> getData() {
        return data;
    }

    public void setData(ArrayList<DateCategory> data) {
        this.data = data;
    }

}
