package com.mltj.xxks.bean;

import java.io.Serializable;

public class BasicResponse implements Serializable {
    private int code;
    private boolean sucesss;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSucesss() {
        return sucesss;
    }

    public void setSucesss(boolean sucesss) {
        this.sucesss = sucesss;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
