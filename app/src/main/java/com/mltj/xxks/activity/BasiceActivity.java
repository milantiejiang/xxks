package com.mltj.xxks.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;

import com.githang.statusbar.StatusBarCompat;
import com.mltj.xxks.App;
import com.mltj.xxks.R;

import butterknife.ButterKnife;

public class BasiceActivity extends FragmentActivity {
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.instance.addActivity(this);
        setContentView(initLayout());
        ButterKnife.bind(this);
        initStatusBar();
        init();
    }

    protected int initLayout() {
        return 0;
    }

    protected void init() {

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void initStatusBar() {
        StatusBarCompat.setStatusBarColor(this, getColor(R.color.withe), true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
