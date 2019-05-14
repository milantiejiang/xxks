package com.mltj.xxks.fragment.main;

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
import com.mltj.xxks.fragment.BasiceFragment;
import com.mltj.xxks.net.ApiService;
import com.mltj.xxks.net.RetrofitUtil;
import com.mltj.xxks.util.Contents;
import com.mltj.xxks.util.UserSPUtil;
import com.mltj.xxks.util.Util;
import com.mltj.xxks.widget.ExpandableLayoutListView;
import com.mltj.xxks.widget.RefreshHeaderView;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TkxxFragment extends BasiceFragment {
    TwinklingRefreshLayout refreshLayout;
    ExpandableLayoutListView expandableLayout;
    TextView hint;

    TikuAdapter tikuAdapter;
    ArrayList<Wrong> tkArrylist = new ArrayList<>();

    int mPagerIndex = 1;
    int mPageSize = 10;
    long startTime;
    long endTime;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_tk_study;
    }

    @Override
    protected void initView(View view) {
        refreshLayout=view.findViewById(R.id.refreshLayout);
        expandableLayout=view.findViewById(R.id.expandableLayout);
        hint=view.findViewById(R.id.hint);
        Calendar calendar = Calendar.getInstance();
        startTime = calendar.getTimeInMillis();
        refreshLayout.setHeaderView(new RefreshHeaderView(getActivity()));
        refreshLayout.setBottomView(new LoadingView(getActivity()));
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
        tikuAdapter = new TikuAdapter(getActivity(), tkArrylist);
        expandableLayout.setAdapter(tikuAdapter);
        getData(1);
    }

    private void getData(int pageIndex) {
        ApiService apiService = RetrofitUtil.getRetrofitInstance(getActivity()).create(ApiService.class);
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

    private void sendData(long duration) {
        ApiService apiService = RetrofitUtil.getRetrofitInstance(getActivity()).create(ApiService.class);
        Call<String> call1 = apiService.insertStudyRecord(42,mPageSize,duration/1000, 0,
                "", UserSPUtil.getInstance(getActivity()).getInt(Contents.KEY_USER_ID));
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
    public void onPause() {
        super.onPause();
        Calendar calendar = Calendar.getInstance();
        endTime = calendar.getTimeInMillis();
        long time = endTime - startTime;
        Log.d("Duration Time", Util.getStandardTime(time));
        sendData(time);
    }
}
