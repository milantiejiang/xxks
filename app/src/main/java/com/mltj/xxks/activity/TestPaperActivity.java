package com.mltj.xxks.activity;

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
import com.mltj.xxks.adapter.TestPaperAdapter;
import com.mltj.xxks.bean.TestPaper;
import com.mltj.xxks.bean.TestPaperResponse;
import com.mltj.xxks.net.ApiService;
import com.mltj.xxks.net.RetrofitUtil;
import com.mltj.xxks.util.SpaceItemDecoration;
import com.mltj.xxks.widget.RefreshHeaderView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestPaperActivity extends BasiceActivity implements View.OnClickListener {
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.hint)
    TextView hint;

    int mPagerIndex = 1;
    int mPageSize = 10;
    TestPaperAdapter testPaperAdapter;
    ArrayList<TestPaper> testPaperArrayList = new ArrayList<>();

    @Override
    protected int initLayout() {
        return R.layout.activity_test_paper;
    }

    @Override
    protected void init() {
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
        testPaperAdapter = new TestPaperAdapter(this, testPaperArrayList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.addItemDecoration(new SpaceItemDecoration(20));
        recyclerview.setAdapter(testPaperAdapter);
        getData(1);
    }

    private void getData(int pageIndex) {
        ApiService apiService = RetrofitUtil.getRetrofitInstance(this).create(ApiService.class);
        Call<String> call1 = apiService.getShiJuan("", pageIndex, mPageSize, "");
        call1.enqueue(new Callback<String>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String json = response.body();
                Gson gson = new Gson();
                try {
                    TestPaperResponse object = gson.fromJson(json, TestPaperResponse.class);
                    if (object != null) {
                        TestPaperResponse.Response rp = object.getData();
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
                                testPaperArrayList.clear();
                                testPaperArrayList.addAll(rp.getResults());
                            } else if (pageIndex > 1) {
                                refreshLayout.finishLoadmore();
                                testPaperArrayList.addAll(rp.getResults());
                            }
                            testPaperAdapter.notifyDataSetChanged();
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
}
