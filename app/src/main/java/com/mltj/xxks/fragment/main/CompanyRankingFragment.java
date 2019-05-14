package com.mltj.xxks.fragment.main;

import android.annotation.SuppressLint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.LoadingView;
import com.mltj.xxks.R;
import com.mltj.xxks.adapter.Ranking2Adapter;
import com.mltj.xxks.bean.MessageEvent;
import com.mltj.xxks.bean.Ranking2;
import com.mltj.xxks.bean.Ranking2Response;
import com.mltj.xxks.fragment.BasiceFragment;
import com.mltj.xxks.net.ApiService;
import com.mltj.xxks.net.RetrofitUtil;
import com.mltj.xxks.util.Contents;
import com.mltj.xxks.util.SpaceItemDecoration;
import com.mltj.xxks.util.UserSPUtil;
import com.mltj.xxks.widget.RefreshHeaderView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("ValidFragment")
public class CompanyRankingFragment extends BasiceFragment {

    TwinklingRefreshLayout refreshLayout;
    RecyclerView recyclerview;

    TextView hint;

    Ranking2Adapter rankingAdapter;
    ArrayList<Ranking2> rankingArrayList = new ArrayList<>();
    int companyid = 0;
    int depid = 0;
    int mPagerIndex = 1;
    int mPageSize = 10;

    @SuppressLint("ValidFragment")
    public CompanyRankingFragment(int company, int dep) {
        this.companyid = company;
        this.depid = dep;
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_total_ranking;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        EventBus.getDefault().register(this);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        recyclerview = view.findViewById(R.id.recyclerview);
        hint = view.findViewById(R.id.hint);
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
        rankingAdapter = new Ranking2Adapter(getActivity(), rankingArrayList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.addItemDecoration(new SpaceItemDecoration(20));
        recyclerview.setAdapter(rankingAdapter);
        refreshLayout.startRefresh();
    }

    private void getData(int index) {
        ApiService apiService = RetrofitUtil.getRetrofitInstance(getActivity()).create(ApiService.class);
        Call<String> call1 = apiService.getPointsLeaderboard(companyid,
                depid,
                2,
                index, mPageSize, "", UserSPUtil.getInstance(getActivity()).getInt(Contents.KEY_USER_ID));
        call1.enqueue(new Callback<String>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String json = response.body();
                Gson gson = new Gson();
                try {
                    Ranking2Response object = gson.fromJson(json, Ranking2Response.class);
                    if (object != null) {
                        Ranking2Response.Response rp = object.getData();
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
                                rankingArrayList.clear();
                                rankingArrayList.addAll(rp.getResults());
                            } else if (pageIndex > 1) {
                                refreshLayout.finishLoadmore();
                                rankingArrayList.addAll(rp.getResults());
                            }
                            rankingAdapter.notifyDataSetChanged();
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        String v = messageEvent.getValue();
        companyid = Integer.valueOf(v.split(",")[0]);
        depid = Integer.valueOf(v.split(",")[1]);
//        getData(mPagerIndex);
        refreshLayout.startRefresh();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

}
