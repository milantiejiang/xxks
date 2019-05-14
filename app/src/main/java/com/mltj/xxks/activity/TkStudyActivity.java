package com.mltj.xxks.activity;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.LoadingView;
import com.mltj.xxks.R;
import com.mltj.xxks.adapter.TikuAdapter;
import com.mltj.xxks.bean.BasicResponse;
import com.mltj.xxks.bean.Wrong;
import com.mltj.xxks.bean.WrongResponse;
import com.mltj.xxks.net.ApiService;
import com.mltj.xxks.net.RetrofitUtil;
import com.mltj.xxks.util.Contents;
import com.mltj.xxks.util.UserSPUtil;
import com.mltj.xxks.util.Util;
import com.mltj.xxks.widget.ExpandableLayoutListView;
import com.mltj.xxks.widget.RefreshHeaderView;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TkStudyActivity extends BasiceActivity implements View.OnClickListener{
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    @BindView(R.id.expandableLayout)
    ExpandableLayoutListView expandableLayout;
    @BindView(R.id.hint)
    TextView hint;

    TikuAdapter tikuAdapter;
    ArrayList<Wrong> tkArrylist = new ArrayList<>();

    int mPagerIndex = 1;
    int mPageSize = 10;
    long startTime;
    long endTime;

    @Override
    protected int initLayout() {
        return R.layout.activity_tk_study;
    }

    @Override
    protected void init() {
        Calendar calendar = Calendar.getInstance();
        startTime = calendar.getTimeInMillis();
        refreshLayout.setHeaderView(new RefreshHeaderView(this));
        refreshLayout.setBottomView(new LoadingView(this));
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                getData(1);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                getData(mPagerIndex);
            }
        });
        tikuAdapter = new TikuAdapter(this, tkArrylist);
        expandableLayout.setAdapter(tikuAdapter);
        getData(1);
    }

    private void getData(int pageIndex) {
        ApiService apiService = RetrofitUtil.getRetrofitInstance(this).create(ApiService.class);
        Call<String> call1 = apiService.getTk(pageIndex, mPageSize);
        call1.enqueue(new Callback<String>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String json = response.body();
                Gson gson = new Gson();
                try {
                    WrongResponse object = gson.fromJson(json, WrongResponse.class);
                    if (object != null) {
                        WrongResponse.Response rp = object.getData();
                        if (rp != null) {
                            int total = rp.getTotal();
                            int pageIndex = rp.getPageIndex();
                            int pageSize = rp.getPageSize();
                            if (total > pageIndex * pageSize) {
                                refreshLayout.setEnableLoadmore(true);
                            } else {
                                refreshLayout.setEnableLoadmore(false);
                            }
                            mPagerIndex = pageIndex + 1;
                            if (pageIndex == 1) {
                                refreshLayout.finishRefreshing();
                                tkArrylist.clear();
                                tkArrylist.addAll(rp.getResults());
                            } else if (pageIndex > 1) {
                                refreshLayout.finishLoadmore();
                                tkArrylist.addAll(rp.getResults());
                            }
                            tikuAdapter.notifyDataSetChanged();
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
        Call<String> call1 = apiService.insertStudyRecord(42,mPageSize,duration/1000, 0,
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
}
