package com.mltj.xxks.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author milantiejiang
 */
public abstract class BasiceFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(setLayoutResourceID(), null);
        initView(view);
        initData();
        return view;
    }

    protected abstract int setLayoutResourceID();

    protected void initView(View view) {
    }

    protected void initData() {

    }


}
