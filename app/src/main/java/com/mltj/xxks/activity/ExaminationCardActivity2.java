package com.mltj.xxks.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mltj.xxks.R;
import com.mltj.xxks.adapter.AnswerAdapter;
import com.mltj.xxks.adapter.ItemAdapter2;
import com.mltj.xxks.bean.Ans;
import com.mltj.xxks.bean.Answer;
import com.mltj.xxks.bean.BasicResponse;
import com.mltj.xxks.bean.DateCategory;
import com.mltj.xxks.bean.MessageEvent;
import com.mltj.xxks.bean.QuestionBean;
import com.mltj.xxks.bean.QuestionBeanResponse2;
import com.mltj.xxks.bean.QuestionBeanResponse3;
import com.mltj.xxks.bean.TestPaperResponse2;
import com.mltj.xxks.net.ApiService;
import com.mltj.xxks.net.RetrofitUtil;
import com.mltj.xxks.util.Contents;
import com.mltj.xxks.util.SpUtils;
import com.mltj.xxks.util.SpaceItemDecoration;
import com.mltj.xxks.util.UserSPUtil;
import com.mltj.xxks.util.Util;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExaminationCardActivity2 extends BasiceActivity implements View.OnClickListener {
    public static final String EVENT_PAGER_JUMP = "EVENT_PAGER_JUMP";
    private ArrayList<DateCategory> tm = (ArrayList<DateCategory>) SpUtils.getDataList(this, Contents.KEY_DATA_CATEGORY_TM, DateCategory.class);
    public ArrayList<QuestionBean> questionlist = new ArrayList<QuestionBean>();
    public ArrayList<QuestionBean> rightAnswers=new ArrayList<>();
    public HashMap<Integer, Answer> answerMap = new HashMap<>();
    public HashMap<Integer, Boolean> rewindingMap = new HashMap<>();
    public HashMap<Integer, Ans> wrongAnsers=new HashMap<>();

    @BindView(R.id.sj_coutdown)
    TextView sjCountdown;
    @BindView(R.id.pager)
    TextView pager;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.rl_pre)
    RelativeLayout rlPre;
    @BindView(R.id.next)
    TextView next;
    @BindView(R.id.tj)
    TextView tj;
    @BindView(R.id.pre)
    TextView pre;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.sj_title)
    TextView sjTitle;

    int testPagerType = -1;
    private ItemAdapter2 pagerAdapter;
    public static int currentIndex = 0;
    private CountDownTimer mTimer;
    Dialog dialog;

    long startTime;
    long endTime;
    QuestionBeanResponse2.TestPaper tp = null;

    public static void start(Context context) {
        Intent intent = new Intent(context, ExaminationCardActivity2.class);
        context.startActivity(intent);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_examination_card2;
    }

    @Override
    protected void init() {
        Calendar calendar = Calendar.getInstance();
        startTime = calendar.getTimeInMillis();
        getTM();
        EventBus.getDefault().register(this);
//        startCounter();
    }


    @OnClick({R.id.next, R.id.pre, R.id.submit, R.id.rl_pre, R.id.back, R.id.close, R.id.dtk, R.id.tj,R.id.ll_more})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tj:
                calculation();
                break;
            case R.id.next:
                jumpToNext();
                break;
            case R.id.pre:
                jumpToPre();
                break;
            case R.id.back:
                Util.showLogoutDialog(ExaminationCardActivity2.this);
                break;
            case R.id.close:
                finish();
                break;
            case R.id.dtk:
                showBottomDialog(ExaminationCardActivity2.this, questionlist.size());
                break;
            case R.id.ll_more:
                Intent intent=new Intent(ExaminationCardActivity2.this,WrongActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void calculation() {
        for (int i = 0; i < questionlist.size(); i++) {
            String ans = questionlist.get(i).getReferenceAnswer();
            Answer answer = answerMap.get(i);
            if (answer != null) {
                if (answer.getAnswers() != null) {
                    boolean f=matcheAns(ans, answer.getAnswers());
                    rewindingMap.put(i, f);
                    if(!f){
                        Ans as=new Ans();
                        as.setMyAns(answer.getAnswers());
                        as.setRightAns(ans);
                        wrongAnsers.put(i,as);
                    }
                }
            } else {
                rewindingMap.put(i, false);
                Ans as=new Ans();
                as.setMyAns(new ArrayList<String>());
                as.setRightAns(ans);
                wrongAnsers.put(i,as);
            }
        }
        rewind();
    }

    private boolean matcheAns(String ans, ArrayList<String> myans) {
        String[] a = ans.split("\\|");
        for (int i = 0; i < a.length; i++) {
            String ma = myans.get(i);
            if (ma != null) {
                if (!a[i].equals(ma)) {
                    return false;
                }
            }
        }
        return true;
    }


    private ArrayList<Answer> getAns() {
        ArrayList<Answer> temp = new ArrayList<>();
        for (Map.Entry<Integer, Answer> entry : answerMap.entrySet()) {
            temp.add(entry.getValue());
        }
        return temp;
    }

    private void rewind() {
        for (int i = 0; i < questionlist.size(); i++) {
            Log.d("Answer", i + ":" + questionlist.get(i).getReferenceAnswer());
        }
        if (answerMap.size() == 0) {
            Toast.makeText(this, "请答题", Toast.LENGTH_LONG).show();
            return;
        }
        for (Map.Entry<Integer, Answer> entry : answerMap.entrySet()) {
            Log.d("MyAnswer", entry.getKey() + ":" + entry.getValue().getAnswers().toString());
        }
        int k = 0;
        StringBuffer wrongStr = new StringBuffer();
        for (Map.Entry<Integer, Boolean> entry : rewindingMap.entrySet()) {
            Log.d("Result", entry.getKey() + ":" + entry.getValue());
            if (entry.getValue()) {
                k++;
                rightAnswers.add(questionlist.get(entry.getKey()));
            } else {
                int id = questionlist.get(entry.getKey()).getId();
                wrongStr.append(id).append(",");
            }
        }

        int s = 0;
        if (rightAnswers != null) {
            for(int i=0;i<rightAnswers.size();i++){
                s += rightAnswers.get(i).getSingleScore();
            }
        }
        Log.d("Score", s + "");
        sendData(s);
    }

    public void jumpToNext() {
        int position = viewpager.getCurrentItem();
        viewpager.setCurrentItem(position + 1);
    }

    public void jumpToPre() {
        int position = viewpager.getCurrentItem();
        viewpager.setCurrentItem(position - 1);
    }

    private void sendData(final int score) {
        Calendar calendar = Calendar.getInstance();
        endTime = calendar.getTimeInMillis();
        long duration = endTime - startTime;
        Log.d("Duration Time", Util.getStandardTime(duration));
        ApiService apiService = RetrofitUtil.getRetrofitInstance(this).create(ApiService.class);
        Call<String> call1 = apiService.pushCt(UserSPUtil.getInstance(this).getInt(Contents.KEY_USER_ID), rightAnswers);
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
                        ResultActivity.start(ExaminationCardActivity2.this, score,wrongAnsers);
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void getTestPager(int type) {
        ApiService apiService = RetrofitUtil.getRetrofitInstance(this).create(ApiService.class);
        Call<String> call1 = apiService.getExaminationPaperByType("", type, UserSPUtil.getInstance(this).getInt(Contents.KEY_USER_ID));
        call1.enqueue(new Callback<String>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String json = response.body();
                Gson gson = new Gson();
                TestPaperResponse2 object = gson.fromJson(json, TestPaperResponse2.class);
                if (object != null) {
                    if (object.getData() != null) {
//                        testPaper = object.getData();
//                        sjTitle.setText(testPaper.getTitle());
//                        title.setText(testPaper.getExaminationPaperCategory());
//                        getData(testPaper.getId());
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
    }

    private void getTM() {
        ApiService apiService = RetrofitUtil.getRetrofitInstance(this).create(ApiService.class);
        Call<String> call1 = apiService.getTm3(UserSPUtil.getInstance(this).getInt(Contents.KEY_USER_ID));
        call1.enqueue(new Callback<String>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String json = response.body();
                Gson gson = new Gson();
                QuestionBeanResponse3 object = gson.fromJson(json, QuestionBeanResponse3.class);
                ArrayList<QuestionBean> list=new ArrayList<>();
                if (object != null) {
                    list = object.getData();
                    if (list != null) {
                        questionlist.addAll(list);
                    }
                }
                pager.setText("1/" + questionlist.size());
                viewpager.setCurrentItem(0);
                pagerAdapter = new ItemAdapter2(getSupportFragmentManager(), ExaminationCardActivity2.this,list);
                viewpager.setAdapter(pagerAdapter);
                viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onPageSelected(int p) {
                        int i = p + 1;
                        currentIndex = i;
                        pager.setText(i + "/" + questionlist.size());
                        if (i == questionlist.size()) {
                            pre.setClickable(true);
                            rlPre.setBackgroundColor(getColor(R.color.withe));
                            pre.setTextColor(getColor(R.color.black));
                            pre.setText("上一题");
//                            next.setText("提交");
                            next.setTextColor(getColor(R.color.gray));
                        } else if (i == 1) {
                            pre.setClickable(false);
                            rlPre.setBackgroundColor(getColor(R.color.gray1));
                            pre.setTextColor(getColor(R.color.gray));
                            pre.setText("上一题");
                            next.setText("下一题");
                            next.setTextColor(getColor(R.color.withe));
                        } else {
                            pre.setClickable(true);
                            rlPre.setBackgroundColor(getColor(R.color.withe));
                            pre.setTextColor(getColor(R.color.black));
                            pre.setText("上一题");
                            next.setText("下一题");
                            next.setTextColor(getColor(R.color.withe));
                        }
                    }

                    @Override
                    public void onPageScrolled(int arg0, float arg1, int arg2) {

                    }

                    @Override
                    public void onPageScrollStateChanged(int position) {
//                        currentIndex = position;
                    }
                });
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
    }

    // 开始计时
    public void startCounter() {
        if (mTimer == null) {
            //10分钟倒计时
            mTimer = new CountDownTimer((long) (10 * 60 * 1000), 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    sjCountdown.setText(Util.getStandardTime(millisUntilFinished));
                }

                @Override
                public void onFinish() {
                    if (!ExaminationCardActivity2.this.isFinishing()) {
                        finish();
                    }
                }
            };
            mTimer.start();
        }
    }

    private void showBottomDialog(Context context, int size) {
        dialog = new Dialog(context, R.style.time_dialog);
        dialog.setCancelable(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_bottom_dialog);
        RecyclerView recyclerView = dialog.findViewById(R.id.recyclerview);
        AnswerAdapter answerAdapter = new AnswerAdapter(context, answerMap,size);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.addItemDecoration(new SpaceItemDecoration(10));
        recyclerView.setAdapter(answerAdapter);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = dm.widthPixels;
        window.setAttributes(lp);
        dialog.show();
    }

    public HashMap<Integer, Answer> getAnswerMap() {
        return answerMap;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        if (messageEvent.getMessage().equals(EVENT_PAGER_JUMP)) {
            viewpager.setCurrentItem(Integer.valueOf(messageEvent.getValue()));
            if (dialog != null) {
                dialog.dismiss();
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Util.showLogoutDialog(ExaminationCardActivity2.this);
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }
}
