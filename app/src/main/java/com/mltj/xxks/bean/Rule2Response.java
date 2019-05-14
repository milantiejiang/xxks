package com.mltj.xxks.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class Rule2Response extends BasicResponse implements Serializable {

    private ArrayList<Rule2> data;

    public ArrayList<Rule2> getData() {
        return data;
    }

    public void setData(ArrayList<Rule2> data) {
        this.data = data;
    }
}
