package com.mltj.xxks.bean;

import java.util.ArrayList;

public class WrongResponse extends BasicResponse {
    private WrongResponse.Response data;

    public WrongResponse.Response getData() {
        return data;
    }

    public void setData(WrongResponse.Response data) {
        this.data = data;
    }

    public class Response{
        private int pageIndex;
        private int pageSize;
        private ArrayList<Wrong> results;
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

        public ArrayList<Wrong> getResults() {
            return results;
        }

        public void setResults(ArrayList<Wrong> results) {
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
