package com.mltj.xxks.activity;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mltj.xxks.R;
import com.mltj.xxks.bean.UserResponse;
import com.mltj.xxks.net.ApiService;
import com.mltj.xxks.net.RetrofitUtil;
import com.mltj.xxks.util.Contents;
import com.mltj.xxks.util.UserSPUtil;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends BasiceActivity implements View.OnClickListener {
    @BindView(R.id.old_password)
    EditText oldPassword;
    @BindView(R.id.new_password)
    EditText newPassword;


    @Override
    protected int initLayout() {
        return R.layout.activity_change_password;
    }

    @Override
    protected void init() {

    }

    private boolean checkParm(){
        if (oldPassword.getText().toString().equals("")) {
            Toast.makeText(this, "原始密码不能为空", Toast.LENGTH_LONG).show();
            return false;
        } else if (newPassword.getText().toString().equals("")) {
            Toast.makeText(this, "新密码不能为空", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void changepassword(){
        ApiService apiService = RetrofitUtil.getRetrofitInstance(this).create(ApiService.class);
        Call<String> call1 = apiService.updateUserPassWord(oldPassword.getText().toString(),newPassword.getText().toString(),UserSPUtil.getInstance(this).getString(Contents.KEY_USER_NAME));
        call1.enqueue(new Callback<String>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String json = response.body();
                Gson gson = new Gson();
                try {
                    UserResponse object = gson.fromJson(json, UserResponse.class);
                    if (object != null) {
                        Toast.makeText(ChangePasswordActivity.this,"修改成功",Toast.LENGTH_LONG).show();
                        finish();
                    }else {
                        Toast.makeText(ChangePasswordActivity.this,"修改失败",Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(ChangePasswordActivity.this,"修改失败",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(ChangePasswordActivity.this,"修改失败",Toast.LENGTH_LONG).show();
            }
        });
    }

    @OnClick({R.id.back,R.id.changepassword})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.changepassword:
                if(checkParm()){
                    changepassword();
                }
                break;
            case R.id.back:
                finish();
                break;
            default:
                break;
        }
    }
}
