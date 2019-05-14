package com.mltj.xxks.fragment.main;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.LoadingView;
import com.mltj.xxks.R;
import com.mltj.xxks.adapter.NoticeAdapter;
import com.mltj.xxks.bean.Notice;
import com.mltj.xxks.bean.NoticeResponse;
import com.mltj.xxks.fragment.BasiceFragment;
import com.mltj.xxks.net.ApiService;
import com.mltj.xxks.net.RetrofitUtil;
import com.mltj.xxks.util.SpaceItemDecoration;
import com.mltj.xxks.widget.RefreshHeaderView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends BasiceFragment {
    int mPagerIndex = 1;
    int mPageSize = 10;
    TwinklingRefreshLayout refreshLayout;

    RecyclerView recyclerview;

    TextView hint;

    NoticeAdapter noticeAdapter;
    ArrayList<Notice> noticeArrayList = new ArrayList<>();
    Handler timerhandler;
    Runnable myTimerRun;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_home;
    }


    @Override
    protected void initView(View view) {
        recyclerview = view.findViewById(R.id.recyclerview);
        hint = view.findViewById(R.id.hint);
        refreshLayout = view.findViewById(R.id.refreshLayout);
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
        noticeAdapter = new NoticeAdapter(getActivity(), noticeArrayList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.addItemDecoration(new SpaceItemDecoration(25));
        recyclerview.setAdapter(noticeAdapter);
        getData(1);
        startTimer();
    }

    private void startTimer() {
        timerhandler = new Handler();
        myTimerRun = new Runnable() {

            @Override
            public void run() {
                refreshLayout.startRefresh();
                timerhandler.postDelayed(this, 2*60*1000);

            }

        };
        timerhandler.postDelayed(myTimerRun, 2*60*1000);
    }

    private void getData(int pageIndex) {
        ApiService apiService = RetrofitUtil.getRetrofitInstance(getActivity()).create(ApiService.class);
        Call<String> call1 = apiService.getGongGao("", pageIndex, mPageSize, "");
        call1.enqueue(new Callback<String>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String json = response.body();
                Gson gson = new Gson();
                try {
                    NoticeResponse object = gson.fromJson(json, NoticeResponse.class);
                    if (object != null) {
                        NoticeResponse.Response rp = object.getData();
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
                                noticeArrayList.clear();
                                noticeArrayList.addAll(rp.getResults());
                            } else if (pageIndex > 1) {
                                refreshLayout.finishLoadmore();
                                noticeArrayList.addAll(rp.getResults());
                            }
                            noticeAdapter.notifyDataSetChanged();
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(timerhandler!=null&&myTimerRun!=null){
            timerhandler.removeCallbacks(myTimerRun);
        }
    }
}
