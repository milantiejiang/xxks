package com.mltj.xxks;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.mltj.xxks.bean.BasicResponse;
import com.mltj.xxks.bean.DateCategory;
import com.mltj.xxks.bean.DateCategoryResponse;
import com.mltj.xxks.net.ApiService;
import com.mltj.xxks.net.RetrofitUtil;
import com.mltj.xxks.util.Contents;
import com.mltj.xxks.util.SpUtils;
import com.mltj.xxks.util.UserSPUtil;
import com.mltj.xxks.util.Util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class App extends Application {
    public static int DB_VERSION = 1;
    /**
     * 运用list来保存们每一个activity是关键
     */
    private List<Activity> mList = new LinkedList<Activity>();
    /**
     * 为了实现每次使用该类时不创建新的对象而创建的静态对象
     */
    public static App instance;
    long startTime;
    long endTime;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initBackgroundCallBack();
        getDateCategory();
    }

    private void getDateCategory() {
        ApiService apiService = RetrofitUtil.getRetrofitInstance(this).create(ApiService.class);
        Call<String> call1 = apiService.getDateCategory("", 0);
        call1.enqueue(new Callback<String>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e("WelcomeActivity", "response");
                String json = response.body();
                Gson gson = new Gson();
                DateCategoryResponse object = gson.fromJson(json, DateCategoryResponse.class);
                ArrayList<DateCategory> list = new ArrayList<>();
                ArrayList<DateCategory> newslist = new ArrayList<>();
                ArrayList<DateCategory> stydylist = new ArrayList<>();
                ArrayList<DateCategory> sjlist = new ArrayList<>();
                ArrayList<DateCategory> tmlist = new ArrayList<>();
                ArrayList<DateCategory> companylist = new ArrayList<>();
                if (object != null) {
                    list = object.getData();
                    for (int i = 0; i < list.size(); i++) {
                        DateCategory date = list.get(i);
                        if (date.getTypeId() == 1) {
                            newslist.add(date);
                        } else if (date.getTypeId() == 2) {
                            tmlist.add(date);
                        } else if (date.getTypeId() == 3) {
                            sjlist.add(date);
                        } else if (date.getTypeId() == 4) {
                            stydylist.add(date);
                        } else if (date.getTypeId() == 5) {
                            companylist.add(date);
                        }
                    }
                }
                if (list != null) {
                    SpUtils.setDataList(App.this, Contents.KEY_DATA_CATEGORY_ALL, list);
                }
                if (newslist != null) {
                    SpUtils.setDataList(App.this, Contents.KEY_DATA_CATEGORY_NEWS, newslist);
                }
                if (stydylist != null) {
                    SpUtils.setDataList(App.this, Contents.KEY_DATA_CATEGORY_STUDY, stydylist);
                }
                if (sjlist != null) {
                    SpUtils.setDataList(App.this, Contents.KEY_DATA_CATEGORY_SJ, sjlist);
                }
                if (tmlist != null) {
                    SpUtils.setDataList(App.this, Contents.KEY_DATA_CATEGORY_TM, tmlist);
                }
                if (companylist != null) {
                    SpUtils.setDataList(App.this, Contents.KEY_DATA_CATEGORY_COMPANY, companylist);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("WelcomeActivity", "failure");
            }
        });
    }

    public void addActivity(Activity activity) {
        mList.add(activity);
    }


    public void exit() {
        try {
            for (Activity activity : mList) {
                if (activity != null) {
                    activity.finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            System.exit(0);
        }
    }

    private void sendData(long duration) {
        ApiService apiService = RetrofitUtil.getRetrofitInstance(this).create(ApiService.class);
        Call<String> call1 = apiService.insertStudyRecord(42,1,duration/1000, 0,
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

    private void initBackgroundCallBack() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            }

            @Override
            public void onActivityStarted(Activity activity) {
                //应用从后台回到前台 需要做的操作
                Calendar calendar = Calendar.getInstance();
                startTime = calendar.getTimeInMillis();
                Log.e("App", "onActivityStarted");
            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
                //应用进入后台 需要做的操作
                Calendar calendar = Calendar.getInstance();
                endTime = calendar.getTimeInMillis();
                long time = endTime - startTime;
                Log.e("App", "onActivityStopped" + Util.getStandardTime(time));
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
            }
        });
    }

}
