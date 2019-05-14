package com.mltj.xxks.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mltj.xxks.R;
import com.mltj.xxks.bean.MessageEvent;
import com.mltj.xxks.bean.MyScoreResponse;
import com.mltj.xxks.bean.Rule2;
import com.mltj.xxks.bean.Rule2Response;
import com.mltj.xxks.net.ApiService;
import com.mltj.xxks.net.RetrofitUtil;
import com.mltj.xxks.util.Contents;
import com.mltj.xxks.util.UserSPUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IntegralRuleActivity extends BasiceActivity implements View.OnClickListener {
    @BindView(R.id.ydxx_score)
    TextView ydxx;
    @BindView(R.id.login_score)
    TextView login;
    @BindView(R.id.ydxxsc_score)
    TextView ydxxsc;
    @BindView(R.id.mzyk_score)
    TextView mzyk;
    @BindView(R.id.mryl_score)
    TextView mryl;
    @BindView(R.id.gksp_score)
    TextView gksp;
    @BindView(R.id.gkspsc_score)
    TextView gkspsc;
    @BindView(R.id.kszt_score)
    TextView kszt;
    @BindView(R.id.score)
    TextView score;

    @Override
    protected int initLayout() {
        return R.layout.activity_integral_rule;
    }

    @Override
    protected void init() {
        super.init();
        getMyscore();
        getData(1);
    }

    private void getMyscore() {
        ApiService apiService = RetrofitUtil.getRetrofitInstance(this).create(ApiService.class);
        Call<String> call1 = apiService.findUserCreditResultByUserId(UserSPUtil.getInstance(this).getInt(Contents.KEY_USER_ID));
        call1.enqueue(new Callback<String>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String json = response.body();
                Gson gson = new Gson();
                try {
                    MyScoreResponse object = gson.fromJson(json, MyScoreResponse.class);
                    if (object != null) {
                        String s = object.getData();
                        if (s != null) {
                            score.setText(s);
                        }
                    }
                } catch (Exception e) {

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }


    private void getData(int pageIndex) {
        ApiService apiService = RetrofitUtil.getRetrofitInstance(this).create(ApiService.class);
        Call<String> call1 = apiService.getRule2(UserSPUtil.getInstance(this).getInt(Contents.KEY_USER_ID));
        call1.enqueue(new Callback<String>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String json = response.body();
                Gson gson = new Gson();
                try {
                    Rule2Response object = gson.fromJson(json, Rule2Response.class);
                    if (object != null) {
                        ArrayList<Rule2> rp = object.getData();
                        if (rp != null) {

                            for (int i = 0; i < rp.size(); i++) {
                                Rule2 rule = rp.get(i);
                                switch (rule.getCreditType()) {
                                    case 38:
                                        //登录
                                        login.setText(rule.getIntegralConfigurationDescription());
                                        break;
                                    case 39:
                                        //每日一练
                                        mryl.setText(rule.getIntegralConfigurationDescription());
                                        break;
                                    case 40:
                                        //每周一考
                                        mzyk.setText(rule.getIntegralConfigurationDescription());
                                        break;
                                    case 41:
                                        //专题考试
                                        kszt.setText(rule.getIntegralConfigurationDescription());
                                        break;
                                    case 42:
                                        //阅读学习时长
                                        ydxxsc.setText(rule.getIntegralConfigurationDescription());
                                        break;
                                    case 43:
                                        //观看视频时长
                                        gkspsc.setText(rule.getIntegralConfigurationDescription());
                                        break;
                                    case 44:
                                        //阅读学习
                                        ydxx.setText(rule.getIntegralConfigurationDescription());
                                        break;
                                    case 45:
                                        //观看视频
                                        gksp.setText(rule.getIntegralConfigurationDescription());
                                        break;
                                    default:
                                        break;
                                }

                            }
                        }
                    }
                } catch (Exception e) {

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }

    @OnClick({R.id.back, R.id.go_gksp, R.id.go_gkspsc, R.id.go_kszt, R.id.go_login, R.id.go_mryl, R.id.go_mzyk, R.id.go_ydxx, R.id.go_ydxxsc, R.id.jfmx})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.go_gksp:
                finish();
                MessageEvent messageEvent = new MessageEvent("go_video");
                EventBus.getDefault().post(messageEvent);
                break;
            case R.id.go_gkspsc:
                finish();
                MessageEvent messageEvent1 = new MessageEvent("go_video");
                EventBus.getDefault().post(messageEvent1);
                break;
            case R.id.go_kszt:
                finish();
                ExaminationCardActivity.start(this, 17);
                break;
            case R.id.go_ydxx:
                finish();
                MessageEvent messageEvent2 = new MessageEvent("go_study");
                EventBus.getDefault().post(messageEvent2);
                break;
            case R.id.go_login:
                break;
            case R.id.go_mryl:
                ExaminationCardActivity.start(this, 14);
                break;
            case R.id.go_mzyk:
                ExaminationCardActivity.start(this, 15);
                break;
            case R.id.go_ydxxsc:
                finish();
                MessageEvent messageEvent3 = new MessageEvent("go_study");
                EventBus.getDefault().post(messageEvent3);
                break;
            case R.id.jfmx:
                Intent intent = new Intent(IntegralRuleActivity.this, IntegralDetailActivity.class);
                startActivity(intent);
                break;
            case R.id.back:
                finish();
                break;
            default:
                break;
        }
    }
}
