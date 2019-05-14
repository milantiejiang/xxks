package com.mltj.xxks.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.mltj.xxks.R;
import com.mltj.xxks.bean.BasicResponse;
import com.mltj.xxks.bean.DateCategory;
import com.mltj.xxks.bean.Regist;
import com.mltj.xxks.net.ApiService;
import com.mltj.xxks.net.RetrofitUtil;
import com.mltj.xxks.util.Contents;
import com.mltj.xxks.util.SpUtils;
import com.mltj.xxks.util.Util;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends BasiceActivity implements View.OnClickListener {
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.password1)
    EditText password1;
    @BindView(R.id.code)
    EditText code;
    @BindView(R.id.company)
    EditText company;

    int mdep=0;
    int mcompanyid=0;
    ProgressDialog progressDialog;
    ArrayList<String> options1Items = new ArrayList<>();
    ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    ArrayList<ArrayList<DateCategory>> deps=new ArrayList<>();
    ArrayList<DateCategory> alls = (ArrayList<DateCategory>) SpUtils.getDataList(this, Contents.KEY_DATA_CATEGORY_ALL, DateCategory.class);

    ArrayList<DateCategory> companys = (ArrayList<DateCategory>) SpUtils.getDataList(this, Contents.KEY_DATA_CATEGORY_COMPANY, DateCategory.class);
    @Override
    protected int initLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected void init() {
        super.init();
        Util.hideSoftKeyboard(this);
        if (companys != null) {
            for (int i = 0; i < companys.size(); i++) {
                options1Items.add(companys.get(i).getCategoryCnName());
            }
        }
        if(companys!=null){
            for (int i = 0; i < companys.size(); i++) {
                ArrayList<String> bm = new ArrayList<>();
                ArrayList<DateCategory> dp = new ArrayList<>();
                int type = companys.get(i).getId();
                if (alls != null) {
                    for (int j = 0; j < alls.size(); j++) {
                        if (alls.get(j).getTypeId() == type) {
                            bm.add(alls.get(j).getCategoryCnName());
                            dp.add(alls.get(j));
                        }
                    }
                }
                options2Items.add(bm);
                deps.add(dp);
            }
        }
        company.setFocusable(false);
    }

    private boolean checkValue() {
        if (phone.getText().toString().equals("")) {
            Toast.makeText(this, "手机号码不能为空", Toast.LENGTH_LONG).show();
            return false;
        } else if (company.getText().toString().equals("")) {
            Toast.makeText(this, "公司部门不能为空", Toast.LENGTH_LONG).show();
            return false;
        } else if (name.getText().toString().equals("")) {
            Toast.makeText(this, "昵称不能为空", Toast.LENGTH_LONG).show();
            return false;
        } else if (password.getText().toString().equals("")) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_LONG).show();
            return false;
        } else {
            if (!password.getText().toString().equals(password1.getText().toString())) {
                Toast.makeText(this, "两次输入的密码不匹配", Toast.LENGTH_LONG).show();
                return false;
            } else if (!Util.phoneNumCheck(phone.getText().toString())) {
                Toast.makeText(this, "手机号码格式不正确", Toast.LENGTH_LONG).show();
                return false;
            } else {
                return true;
            }
        }
    }

    @OnClick({R.id.get_code, R.id.regist, R.id.back, R.id.company})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_code:

                break;
            case R.id.regist:
                if (checkValue()) {
                    regist(getRegist());
                }
                break;
            case R.id.back:
                finish();
                break;
            case R.id.company:
                showSelect();
                break;
            default:
                break;
        }
    }

    private void showSelect() {
        Util.hideSoftKeyboard(this);
        OptionsPickerView pvOptions = new OptionsPickerBuilder(RegisterActivity.this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1)
                        + options2Items.get(options1).get(option2);
                company.setText(tx);
                if(deps!=null){
                    mdep=deps.get(options1).get(option2).getId();
                }
                if(companys!=null){
                    mcompanyid=companys.get(options1).getId();
                }
            }
        }).build();
        pvOptions.setPicker(options1Items, options2Items);
        pvOptions.show();
    }

    private Regist getRegist() {
        Regist regist = new Regist();
        regist.setPhone(phone.getText().toString());
        regist.setName(name.getText().toString());
        regist.setUserName(name.getText().toString());
        regist.setPassword(Util.getMD5(password.getText().toString()));
        regist.setCompany(mcompanyid);
        regist.setDepartment(mdep);
        return regist;
    }

    private void regist(Regist regist) {
        buildProgressDialog(RegisterActivity.this);
        ApiService apiService = RetrofitUtil.getRetrofitInstance(this).create(ApiService.class);
        Call<String> call1 = apiService.regist("", regist);
        call1.enqueue(new Callback<String>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                cancelProgressDialog();
                Log.e("WelcomeActivity", "response");
                String json = response.body();
                Gson gson = new Gson();
                BasicResponse object = gson.fromJson(json, BasicResponse.class);
                if (object != null) {
                    if (object.getCode() == 200) {
                        Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, object.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                cancelProgressDialog();
                Log.e("WelcomeActivity", "failure");
                Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_LONG).show();
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
        progressDialog.setMessage("加载中...");
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
}
