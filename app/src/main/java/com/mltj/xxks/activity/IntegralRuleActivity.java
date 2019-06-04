package com.mltj.xxks.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ProgressBar;
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
//    @BindView(R.id.ydxx_score)
//    TextView ydxx;
    @BindView(R.id.login_score)
    TextView login;
    @BindView(R.id.ydxxsc_score)
    TextView ydxxsc;
    @BindView(R.id.mzyk_score)
    TextView mzyk;
    @BindView(R.id.mryl_score)
    TextView mryl;
//    @BindView(R.id.gksp_score)
//    TextView gksp;
//    @BindView(R.id.gkspsc_score)
//    TextView gkspsc;
    @BindView(R.id.kszt_score)
    TextView kszt;
    @BindView(R.id.score)
    TextView score;

//    @BindView(R.id.ydxx_score_ts)
//    TextView ydxxts;
    @BindView(R.id.login_score_ts)
    TextView logints;
    @BindView(R.id.ydxxsc_score_ts)
    TextView ydxxscts;
    @BindView(R.id.mzyk_score_ts)
    TextView mzykts;
    @BindView(R.id.mryl_score_ts)
    TextView mrylts;
//    @BindView(R.id.gksp_score_ts)
//    TextView gkspts;
//    @BindView(R.id.gkspsc_score_ts)
//    TextView gkspscts;
    @BindView(R.id.kszt_score_ts)
    TextView ksztts;

    @BindView(R.id.go_login)
    TextView goLogin;
//    @BindView(R.id.go_gksp)
//    TextView goGksp;
//    @BindView(R.id.go_gkspsc)
//    TextView goGkspse;
    @BindView(R.id.go_kszt)
    TextView goKszt;
    @BindView(R.id.go_mryl)
    TextView goMryl;
    @BindView(R.id.go_mzyk)
    TextView goMzyk;
//    @BindView(R.id.go_ydxx)
//    TextView goYdxx;
    @BindView(R.id.go_ydxxsc)
    TextView goYdxxsc;


//    @BindView(R.id.progress_gksp)
//    ProgressBar progressgksp;
//    @BindView(R.id.progress_gkspsc)
//    ProgressBar progressgkspsc;
    @BindView(R.id.progress_kszt)
    ProgressBar progresskszt;
    @BindView(R.id.progress_login)
    ProgressBar progresslogin;
    @BindView(R.id.progress_mryl)
    ProgressBar progressmryl;
    @BindView(R.id.progress_mzyk)
    ProgressBar progressmzyk;
//    @BindView(R.id.progress_ydxx)
//    ProgressBar progressydxx;
    @BindView(R.id.progress_ydxxsc)
    ProgressBar progressydxxsc;

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
        int userid=UserSPUtil.getInstance(this).getInt(Contents.KEY_USER_ID);
        ApiService apiService = RetrofitUtil.getRetrofitInstance(this).create(ApiService.class);
        Call<String> call1 = apiService.getRule2(userid);
        call1.enqueue(new Callback<String>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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
                                        logints.setText(rule.getIntegralConfigurationDescription());
                                        login.setText(rule.getUserEarnsPoints()+"/"+"上限"+rule.getUpperLimit()+"分");
                                        progresslogin.setProgress(calculateProgress(rule));
                                        if(rule.getScore()==rule.getUpperLimit()){
                                            goLogin.setClickable(false);
                                            goLogin.setBackground(getDrawable(R.drawable.ellipse_gray_enable_bg));
                                        }
                                        break;
                                    case 39:
                                        //智能答题
                                        mrylts.setText(rule.getIntegralConfigurationDescription());
                                        mryl.setText(rule.getUserEarnsPoints()+"/"+"上限"+rule.getUpperLimit()+"分");
                                        progressmryl.setProgress(calculateProgress(rule));
                                        if(rule.getScore()==rule.getUpperLimit()){
                                            goMryl.setClickable(false);
                                            goMryl.setBackground(getDrawable(R.drawable.ellipse_gray_enable_bg));
                                        }
                                        break;
                                    case 40:
                                        //每周一考
                                        mzykts.setText(rule.getIntegralConfigurationDescription());
                                        mzyk.setText(rule.getUserEarnsPoints()+"/"+"上限"+rule.getUpperLimit()+"分");
                                        progressmzyk.setProgress(calculateProgress(rule));
                                        if(rule.getScore()==rule.getUpperLimit()){
                                            goMzyk.setClickable(false);
                                            goMzyk.setBackground(getDrawable(R.drawable.ellipse_gray_enable_bg));
                                        }
                                        break;
                                    case 41:
                                        //专题考试
                                        ksztts.setText(rule.getIntegralConfigurationDescription());
                                        kszt.setText(rule.getUserEarnsPoints()+"/"+"上限"+rule.getUpperLimit()+"分");
                                        progresskszt.setProgress(calculateProgress(rule));
                                        if(rule.getScore()==rule.getUpperLimit()){
                                            goKszt.setClickable(false);
                                            goKszt.setBackground(getDrawable(R.drawable.ellipse_gray_enable_bg));
                                        }
                                        break;
                                    case 42:
                                        //阅读学习时长
                                        ydxxscts.setText(rule.getIntegralConfigurationDescription());
                                        ydxxsc.setText(rule.getUserEarnsPoints()+"/"+"上限"+rule.getUpperLimit()+"分");
                                        progressydxxsc.setProgress(calculateProgress(rule));
                                        if(rule.getScore()==rule.getUpperLimit()){
                                            goYdxxsc.setClickable(false);
                                            goYdxxsc.setBackground(getDrawable(R.drawable.ellipse_gray_enable_bg));
                                        }
                                        break;
                                    case 43:
                                        //观看视频时长
//                                        gkspscts.setText(rule.getIntegralConfigurationDescription());
//                                        gkspsc.setText(rule.getUserEarnsPoints()+"/"+"上限"+rule.getUpperLimit()+"分");
//                                        progressmzyk.setProgress(calculateProgress(rule));
//                                        if(rule.getScore()==rule.getUpperLimit()){
//                                            goGkspse.setClickable(false);
//                                            goGkspse.setBackground(getDrawable(R.drawable.ellipse_gray_enable_bg));
//                                        }
                                        break;
                                    case 44:
                                        //阅读学习
//                                        ydxxts.setText(rule.getIntegralConfigurationDescription());
//                                        ydxx.setText(rule.getUserEarnsPoints()+"/"+"上限"+rule.getUpperLimit()+"分");
//                                        progressmzyk.setProgress(calculateProgress(rule));
//                                        if(rule.getScore()==rule.getUpperLimit()){
//                                            goYdxx.setClickable(false);
//                                            goYdxx.setBackground(getDrawable(R.drawable.ellipse_gray_enable_bg));
//                                        }
                                        break;
                                    case 45:
                                        //观看视频
//                                        gkspts.setText(rule.getIntegralConfigurationDescription());
//                                        gksp.setText(rule.getUserEarnsPoints()+"/"+"上限"+rule.getUpperLimit()+"分");
//                                        progressmzyk.setProgress(calculateProgress(rule));
//                                        if(rule.getScore()==rule.getUpperLimit()){
//                                            goGksp.setClickable(false);
//                                            goGksp.setBackground(getDrawable(R.drawable.ellipse_gray_enable_bg));
//                                        }
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

    private int calculateProgress(Rule2 rule){
        float s=rule.getScore();
        float ul=rule.getUpperLimit();
        float count=s/ul;
//        BigDecimal bigDecimal=new BigDecimal(count);
//        int r=bigDecimal.setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
        return (int) (count*100);
    }

    @OnClick({R.id.back, R.id.go_kszt, R.id.go_login, R.id.go_mryl, R.id.go_mzyk, R.id.jfmx,R.id.go_ydxxsc})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.go_kszt:
                finish();
                ExaminationCardActivity.start(this, 17);
                break;
            case R.id.go_login:
                break;
            case R.id.go_mryl:
                ExaminationCardActivity.start(this,14);
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
