package com.mltj.xxks.bean;

import java.io.Serializable;

public class TestPaperResponse2 extends BasicResponse implements Serializable {

    private TestPaper data;

    public TestPaper getData() {
        return data;
    }

    public void setData(TestPaper data) {
        this.data = data;
    }

}
