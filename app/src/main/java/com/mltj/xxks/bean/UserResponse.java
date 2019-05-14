package com.mltj.xxks.bean;

import java.io.Serializable;

public class UserResponse extends BasicResponse implements Serializable {
    private User data;

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }
}
