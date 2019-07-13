package com.mltj.xxks.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class Ranking2Response extends BasicResponse implements Serializable {

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
        private ArrayList<Ranking2> results;
        private int total;
        private int companyRanking;
        private int departmentRanking;


        public int getCompanyRanking() {
            return companyRanking;
        }

        public void setCompanyRanking(int companyRanking) {
            this.companyRanking = companyRanking;
        }

        public int getDepartmentRanking() {
            return departmentRanking;
        }

        public void setDepartmentRanking(int departmentRanking) {
            this.departmentRanking = departmentRanking;
        }

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

        public ArrayList<Ranking2> getResults() {
            return results;
        }

        public void setResults(ArrayList<Ranking2> results) {
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
