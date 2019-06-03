package com.mltj.xxks.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mltj.xxks.R;
import com.mltj.xxks.bean.BasicResponse;
import com.mltj.xxks.bean.Study;
import com.mltj.xxks.net.ApiService;
import com.mltj.xxks.net.RetrofitUtil;
import com.mltj.xxks.util.Contents;
import com.mltj.xxks.util.UserSPUtil;
import com.mltj.xxks.util.Util;
import com.zzhoujay.richtext.RichText;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudyDetailActivity extends BasiceActivity implements View.OnClickListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.webview)
    WebView webview;

    Study study;
    long startTime;
    long endTime;

    public static void start(Context context, Study study) {
        Intent intent = new Intent(context, StudyDetailActivity.class);
        intent.putExtra("object", study);
        context.startActivity(intent);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_study_detail;
    }

    @Override
    protected void init() {
        Calendar calendar = Calendar.getInstance();
        startTime = calendar.getTimeInMillis();
        study = (Study) getIntent().getSerializableExtra("object");
        if (study != null) {
            title.setText(study.getTitle());
//            RichText.initCacheDir(this);
//            tvContent = findViewById(R.id.tv_content);
//            RichText.from(Util.getStudyHtml(study)).bind(this)
//                    .showBorder(false)
//                    .size(ImageHolder.MATCH_PARENT, ImageHolder.WRAP_CONTENT)
//                    .into(tvContent);
            webview.setWebViewClient(new WebViewClient());
            webview.loadDataWithBaseURL(null, study.getContent(), "text/html", "utf-8", null);
        }
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

    private void sendData(long duration) {
        ApiService apiService = RetrofitUtil.getRetrofitInstance(this).create(ApiService.class);
        Call<String> call1 = apiService.insertStudyRecord(42, 1, duration / 1000, study.getId(),
                "", UserSPUtil.getInstance(this).getInt(Contents.KEY_USER_ID));
        call1.enqueue(new Callback<String>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String json = response.body();
                Gson gson = new Gson();
                BasicResponse object = gson.fromJson(json, BasicResponse.class);
                if (object != null) {
                    int code = object.getCode();
                    if (code == 200) {

                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("Duration Time", "onRestart");
        Calendar calendar = Calendar.getInstance();
        startTime = calendar.getTimeInMillis();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Calendar calendar = Calendar.getInstance();
        endTime = calendar.getTimeInMillis();
        long time = endTime - startTime;
        Log.d("Duration Time", Util.getStandardTime(time));
        sendData(time);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RichText.clear(this);
    }

}
