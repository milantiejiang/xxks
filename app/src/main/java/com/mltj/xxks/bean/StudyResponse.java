package com.mltj.xxks.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class StudyResponse extends BasicResponse implements Serializable {
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
        private int total;
        private ArrayList<Study> results;

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

        public ArrayList<Study> getResults() {
            return results;
        }

        public void setResults(ArrayList<Study> results) {
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
