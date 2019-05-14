package com.mltj.xxks.bean;

import java.util.ArrayList;

public class ScoreRankingResponse extends BasicResponse {
    private ArrayList<ScoreRanking> data;

    public ArrayList<ScoreRanking> getData() {
        return data;
    }

    public void setData(ArrayList<ScoreRanking> data) {
        this.data = data;
    }
}
