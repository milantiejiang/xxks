package com.mltj.xxks.util;

import android.widget.TextView;

import java.util.ArrayList;

public class ClickController {
    private TextView mTv;
    private int editHeight = 40;//默认输入框高度
    public ArrayList<String> mInputList = new ArrayList<>();
    public ClickController(TextView tv) {
        this.mTv = tv;

    }

    public int getEditHeight() {
        return editHeight;
    }

    public void setEditHeight(int editHeight) {
        this.editHeight = editHeight;
    }

    public ArrayList<String> getmInputList() {
        return mInputList;
    }

    /**
     * 添加内容
     * @param str
     */
    public void addContent(String str) {
        mInputList.add(str);
    }

    //填充缓存的数据
    public void setData(String str, Object o, int i) {
        mInputList.set(i, str);
        mTv.invalidate();
    }
}
