package com.mltj.xxks.activity;

import android.view.View;
import android.widget.TextView;

import com.mltj.xxks.R;
import com.mltj.xxks.util.Util;

import butterknife.BindView;
import butterknife.OnClick;

public class AboutActivity extends BasiceActivity implements View.OnClickListener{
    @BindView(R.id.version)
    TextView version;
    @BindView(R.id.name)
    TextView name;

    @Override
    protected int initLayout() {
        return R.layout.activity_about;
    }

    @Override
    protected void init() {
        version.setText(Util.versionName(this));
        name.setText("考试学习");
    }

    @OnClick({R.id.back})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            default:
                break;
        }
    }
}
