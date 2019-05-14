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
import com.mltj.xxks.adapter.ScoreRankingAdapter;
import com.mltj.xxks.bean.ScoreRanking;
import com.mltj.xxks.bean.ScoreRankingResponse;
import com.mltj.xxks.fragment.BasiceFragment;
import com.mltj.xxks.net.ApiService;
import com.mltj.xxks.net.RetrofitUtil;
import com.mltj.xxks.util.SpaceItemDecoration;
import com.mltj.xxks.widget.RefreshHeaderView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScoreRankingFragment extends BasiceFragment {

    TwinklingRefreshLayout refreshLayout;
    RecyclerView recyclerview;

    TextView hint;

    ScoreRankingAdapter rankingAdapter;
    ArrayList<ScoreRanking> rankingArrayList = new ArrayList<>();

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_time_ranking;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        refreshLayout=view.findViewById(R.id.refreshLayout);
        recyclerview=view.findViewById(R.id.recyclerview);
        hint=view.findViewById(R.id.hint);
        refreshLayout.setHeaderView(new RefreshHeaderView(getActivity()));
        refreshLayout.setBottomView(new LoadingView(getActivity()));
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                getData();
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                getData();
            }
        });
        rankingAdapter = new ScoreRankingAdapter(getActivity(), rankingArrayList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.addItemDecoration(new SpaceItemDecoration(20));
        recyclerview.setAdapter(rankingAdapter);
        getData();
    }

    private void getData() {
        ApiService apiService = RetrofitUtil.getRetrofitInstance(getActivity()).create(ApiService.class);
        Call<String> call1 = apiService.getScoreRanking("");
        call1.enqueue(new Callback<String>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String json = response.body();
                Gson gson = new Gson();
                try {
                    ScoreRankingResponse object = gson.fromJson(json, ScoreRankingResponse.class);
                    if (object != null) {
                        ArrayList<ScoreRanking> list = object.getData();
                        if (list != null) {
                            refreshLayout.finishRefreshing();
                            rankingArrayList.clear();
                            rankingArrayList.addAll(list);
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
}
