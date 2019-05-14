package com.mltj.xxks.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class TestPaperResponse extends BasicResponse implements Serializable {

    private Response data;

    public Response getData() {
        return data;
    }

    public void setData(Response data) {
        this.data = data;
    }

    public class Response{
        private int pageIndex;
        private int pageSize;
        private ArrayList<TestPaper> results;
        private int total;



        public int getPageIndex() {
            return pageIndex;
        }

        public void setPageIndex(int pageIndex) {
            this.pageIndex = pageIndex;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public ArrayList<TestPaper> getResults() {
            return results;
        }

        public void setResults(ArrayList<TestPaper> results) {
            this.results = results;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }
    }


}
