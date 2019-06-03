package com.mltj.xxks.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.gson.Gson;
import com.mltj.xxks.R;
import com.mltj.xxks.bean.BasicResponse;
import com.mltj.xxks.bean.FileJoin;
import com.mltj.xxks.bean.Study;
import com.mltj.xxks.net.ApiService;
import com.mltj.xxks.net.RetrofitUtil;
import com.mltj.xxks.util.Contents;
import com.mltj.xxks.util.UserSPUtil;
import com.mltj.xxks.util.Util;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoDetailActivity extends BasiceActivity implements View.OnClickListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.videoview)
    VideoView videoView;
    @BindView(R.id.seekbar)
    SeekBar seekBar;
    @BindView(R.id.ll_video_control_tab)
    LinearLayout videoControlTab;
    @BindView(R.id.video_time)
    TextView videoTime;
    @BindView(R.id.video_whole_time)
    TextView videoWholeTime;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.description)
    TextView description;

    boolean onPrepared = false;
    String url = "http://39.105.138.210:8081/study/pic/movie/1.mp4";

    Study study;
    long startTime;
    long endTime;
    ProgressDialog progressDialog;

    public static void start(Context context, Study study) {
        Intent intent = new Intent(context, VideoDetailActivity.class);
        intent.putExtra("object", study);
        context.startActivity(intent);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_video_detail;
    }

    @Override
    protected void init() {
        Calendar calendar = Calendar.getInstance();
        startTime = calendar.getTimeInMillis();
        study = (Study) getIntent().getSerializableExtra("object");
        if (study != null) {
            ArrayList<FileJoin> fileJoins = study.getFileJoins();
            if (fileJoins != null && fileJoins.size() > 0) {
                url = fileJoins.get(0).getFileUrl();
            }
            title.setText(study.getTitle());
            date.setText(Util.getDateStr(study.getLongCreateDate()));
            description.setText(study.getContent());
        }

        videoView.setVideoPath(url);
        videoView.requestFocus();
        videoView.start();
        videoView.setMediaController(new MediaController(this, true));
        buildProgressDialog(this);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                cancelProgressDialog();
            }
        });
//        videoListener();
    }

    private void videoListener() {
        videoView.setMediaController(new MediaController(this));
        videoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (onPrepared) {
                    videoControlTab.setVisibility(View.VISIBLE);
                }
                if (videoView.isPlaying()) {
                    videoView.pause();
                } else {
                    videoView.start();
                }
                return false;
            }
        });
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                onPrepared = true;
                videoControlTab.setVisibility(View.VISIBLE);
                seekBar.setMax(mp.getDuration());
            }
        });

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();
                if (videoView != null && videoView.isPlaying()) {
                    // 设置当前播放的位置
                    videoView.seekTo(progress);
                }
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
        Call<String> call1 = apiService.insertStudyRecord(43,1,duration/1000, study.getId(),
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
}
