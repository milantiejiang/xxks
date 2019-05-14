package com.mltj.xxks.bean;

import java.util.ArrayList;

public class RankingResponse extends BasicResponse {
    private ArrayList<Ranking> data;

    public ArrayList<Ranking> getData() {
        return data;
    }

    public void setData(ArrayList<Ranking> data) {
        this.data = data;
    }
}
