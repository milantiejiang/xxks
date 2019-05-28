package com.mltj.xxks.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.githang.statusbar.StatusBarCompat;
import com.google.gson.Gson;
import com.mltj.xxks.R;
import com.mltj.xxks.bean.User;
import com.mltj.xxks.bean.UserResponse;
import com.mltj.xxks.net.ApiService;
import com.mltj.xxks.net.RetrofitUtil;
import com.mltj.xxks.util.Contents;
import com.mltj.xxks.util.UserSPUtil;
import com.mltj.xxks.util.Util;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BasiceActivity implements View.OnClickListener {

    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.ll_code)
    LinearLayout llCode;
    @BindView(R.id.code)
    EditText code;
    @BindView(R.id.get_code)
    Button getCode;
    @BindView(R.id.rg)
    RadioGroup rg;
    @BindView(R.id.password_login)
    RadioButton passwordLogin;
    @BindView(R.id.code_login)
    RadioButton codeLogin;

    private CountDownTimer mTimer;
    ProgressDialog progressDialog;

    @Override
    protected int initLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void init() {
        passwordLogin.setChecked(true);
        passwordLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    password.setVisibility(View.VISIBLE);
                    llCode.setVisibility(View.GONE);
                } else {
                    password.setVisibility(View.GONE);
                    llCode.setVisibility(View.VISIBLE);
                }
            }
        });
        codeLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    password.setVisibility(View.GONE);
                    llCode.setVisibility(View.VISIBLE);
                } else {
                    password.setVisibility(View.VISIBLE);
                    llCode.setVisibility(View.GONE);
                }
            }
        });
    }

    private void countdown() {
        if (mTimer == null) {
            mTimer = new CountDownTimer((long) (1 * 60 * 1000), 1000) {

                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    if (!LoginActivity.this.isFinishing()) {
                        getCode.setClickable(true);
                    }
                }
            };
            mTimer.start();
        }
    }

    private boolean checkValue(){
        if(passwordLogin.isChecked()){
            if(phone.getText().toString().equals("")){
                Toast.makeText(this, "手机号码不能为空", Toast.LENGTH_LONG).show();
                return false;
            }else if(password.getText().toString().equals("")){
                Toast.makeText(this, "密码不能为空", Toast.LENGTH_LONG).show();
                return false;
            }else {
//                if(!Util.isMobileNum(phone.getText().toString())){
//                    Toast.makeText(this, "手机号码格式不正确", Toast.LENGTH_LONG).show();
//                    return false;
//                }else {
                    return true;
//                }
            }
        }else {
            if(phone.getText().toString().equals("")){
                Toast.makeText(this, "手机号码不能为空", Toast.LENGTH_LONG).show();
                return false;
            }else if(code.getText().toString().equals("")){
                Toast.makeText(this, "验证码不能为空", Toast.LENGTH_LONG).show();
                return false;
            }else {
//                if(!Util.isMobileNum(phone.getText().toString())){
//                    Toast.makeText(this, "手机号码格式不正确", Toast.LENGTH_LONG).show();
//                    return false;
//                }else {
                    return true;
//                }
            }
        }
    }

    @OnClick({R.id.get_code, R.id.regist, R.id.login})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_code:
                v.setClickable(false);
                countdown();
                break;
            case R.id.regist:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.login:
                Log.e("MD5_1",Util.getMD5("1"));
                Log.e("MD5_2",Util.getMD5("1"));
                if(checkValue()){
                    login(phone.getText().toString(),Util.getMD5(password.getText().toString()));
                }
                break;
            default:
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initStatusBar() {
        StatusBarCompat.setStatusBarColor(this, getColor(R.color.red), true);
    }

    private void login(final String name, final String paswd) {
        buildProgressDialog(LoginActivity.this);
        ApiService apiService = RetrofitUtil.getRetrofitInstance(this).create(ApiService.class);
        Call<String> call1 = apiService.login("", name,paswd);
        call1.enqueue(new Callback<String>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                cancelProgressDialog();
                Log.e("WelcomeActivity", "response");
                String json = response.body();
                Gson gson = new Gson();
                UserResponse object = gson.fromJson(json, UserResponse.class);
                if (object != null) {
                    if(object.getCode()==200){
                        User user=object.getData();
                        if(user!=null){
                            Intent intent1 = new Intent(LoginActivity.this, WelcomeActivity.class);
                            startActivity(intent1);
                            UserSPUtil.getInstance(LoginActivity.this).putString(Contents.KEY_USER_ACCOUNT,name);
                            UserSPUtil.getInstance(LoginActivity.this).putString(Contents.KEY_USER_PASSWORD,paswd);
                            UserSPUtil.getInstance(LoginActivity.this).putInt(Contents.KEY_USER_ID,user.getId());
                            UserSPUtil.getInstance(LoginActivity.this).putString(Contents.KEY_USER_NAME,user.getName());
                            UserSPUtil.getInstance(LoginActivity.this).putString(Contents.KEY_USER_COMPANY,user.getCompanyName());
                            UserSPUtil.getInstance(LoginActivity.this).putInt(Contents.KEY_USER_COMPANY_ID,user.getCompany());
                            UserSPUtil.getInstance(LoginActivity.this).putString(Contents.KEY_USER_DEPMENT,user.getDepartmentName());
                            UserSPUtil.getInstance(LoginActivity.this).putInt(Contents.KEY_USER_DEPMENT_ID,user.getDepartment());
                            finish();
                        }
                    }else {
                        Toast.makeText(LoginActivity.this,object.getMessage(),Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this,"登录失败",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                cancelProgressDialog();
                Log.e("WelcomeActivity", "failure");
                Toast.makeText(LoginActivity.this,"登录失败",Toast.LENGTH_LONG).show();
            }
        });

    }

    /**
     * 加载框
     */
    public void buildProgressDialog(Context context) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }
        progressDialog.setMessage("林以载道，成人达己...");
        progressDialog.setCancelable(true);
        progressDialog.show();
    }

    /**
     * 取消加载框
     */
    public void cancelProgressDialog() {
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }
}

