package com.mltj.xxks.activity;

import android.content.Intent;
import android.view.View;

import com.mltj.xxks.App;
import com.mltj.xxks.R;
import com.mltj.xxks.util.UserSPUtil;

import butterknife.OnClick;

public class SettingsActivity extends BasiceActivity implements View.OnClickListener {
    @Override
    protected int initLayout() {
        return R.layout.activity_setting;
    }

    @Override
    protected void init() {
        super.init();

    }

    @OnClick({R.id.ll_loginout,R.id.ll_about,R.id.back,R.id.ll_change_password})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_loginout:
                UserSPUtil.getInstance(SettingsActivity.this).clear();
                App.instance.exit();
                Intent intent=new Intent(SettingsActivity.this,LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_about:
                Intent intent1=new Intent(SettingsActivity.this,AboutActivity.class);
                startActivity(intent1);
                break;
            case R.id.ll_change_password:
                Intent intent2=new Intent(SettingsActivity.this,ChangePasswordActivity.class);
                startActivity(intent2);
                break;
            case R.id.back:
                finish();
                break;
            default:
                break;
        }
    }
}
