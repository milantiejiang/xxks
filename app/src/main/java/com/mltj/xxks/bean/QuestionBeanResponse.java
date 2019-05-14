package com.mltj.xxks.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class QuestionBeanResponse extends BasicResponse implements Serializable {
    private ArrayList<QuestionBean> data;

    public ArrayList<QuestionBean> getData() {
        return data;
    }

    public void setData(ArrayList<QuestionBean> data) {
        this.data = data;
    }
}
