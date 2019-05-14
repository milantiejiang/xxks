package com.mltj.xxks.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mltj.xxks.R;
import com.mltj.xxks.bean.BasicResponse;
import com.mltj.xxks.bean.News;
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

public class NewsDetailActivity extends BasiceActivity implements View.OnClickListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.webview)
    WebView webview;

    News news;
    long startTime;
    long endTime;

    public static void start(Context context, News news) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtra("object", news);
        context.startActivity(intent);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_news_detail;
    }

    @Override
    protected void init() {
        Calendar calendar = Calendar.getInstance();
        startTime = calendar.getTimeInMillis();
        news = (News) getIntent().getSerializableExtra("object");
        if (news != null) {

            title.setText(news.getTitle());
//            RichText.initCacheDir(this);
//            tvContent = findViewById(R.id.tv_content);
//            RichText.from(Util.getNewsHtml(news)).bind(this)
//                    .showBorder(false)
//                    .size(ImageHolder.MATCH_PARENT, ImageHolder.WRAP_CONTENT)
//                    .into(tvContent);
            if(news.getLinkUrl().equals("")){
                news.setLinkUrl("https://baijiahao.baidu.com/s?id=1631497470428912049&wfr=spider&for=pc");
            }
            WebSettings webSettings = webview.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setJavaScriptEnabled(true);
            webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
            //关键点
            webSettings.setUseWideViewPort(true);
            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            webSettings.setDisplayZoomControls(false);
            // 设置支持javascript脚本
            webSettings.setJavaScriptEnabled(true);
            // 允许访问文件
            webSettings.setAllowFileAccess(true);
            // 设置显示缩放按钮
            webSettings.setBuiltInZoomControls(true);
            // 支持缩放
            webSettings.setSupportZoom(true);
            webSettings.setLoadWithOverviewMode(true);
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int mDensity = metrics.densityDpi;
            Log.d("maomao", "densityDpi = " + mDensity);
            if (mDensity == 240) {
                webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
            } else if (mDensity == 160) {
                webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
            } else if(mDensity == 120) {
                webSettings.setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
            }else if(mDensity == DisplayMetrics.DENSITY_XHIGH){
                webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
            }else if (mDensity == DisplayMetrics.DENSITY_TV){
                webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
            }else{
                webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
            }
            /**

             * 用WebView显示图片，可使用这个参数 设置网页布局类型： 1、LayoutAlgorithm.NARROW_COLUMNS ：

             * 适应内容大小 2、LayoutAlgorithm.SINGLE_COLUMN:适应屏幕，内容将自动缩放

             */
            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
            webSettings.setUseWideViewPort(true);
            webview.loadUrl(news.getLinkUrl());
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
        Call<String> call1 = apiService.insertStudyRecord(42,1,duration/1000, news.getId(),
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
