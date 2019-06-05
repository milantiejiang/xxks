package com.mltj.xxks.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class Ans implements Serializable {
    private String rightAns;
    private ArrayList<String> myAns;

    public String getRightAns() {
        return rightAns;
    }

    public void setRightAns(String rightAns) {
        this.rightAns = rightAns;
    }

    public ArrayList<String> getMyAns() {
        return myAns;
    }

    public void setMyAns(ArrayList<String> myAns) {
        this.myAns = myAns;
    }
}
