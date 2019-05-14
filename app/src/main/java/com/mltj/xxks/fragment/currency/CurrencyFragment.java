package com.mltj.xxks.fragment.currency;

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
import com.mltj.xxks.adapter.NewsAdapter;
import com.mltj.xxks.adapter.StudyAdapter;
import com.mltj.xxks.bean.News;
import com.mltj.xxks.bean.NewsResponse;
import com.mltj.xxks.bean.Study;
import com.mltj.xxks.bean.StudyResponse;
import com.mltj.xxks.fragment.BasiceFragment;
import com.mltj.xxks.net.ApiService;
import com.mltj.xxks.net.RetrofitUtil;
import com.mltj.xxks.util.SpaceItemDecoration;
import com.mltj.xxks.widget.RefreshHeaderView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@SuppressLint("ValidFragment")
public class CurrencyFragment extends BasiceFragment {
    int id;
    int type;
    int mPagerIndex = 1;
    int mPageSize = 10;
    TwinklingRefreshLayout refreshLayout;

    RecyclerView recyclerview;

    TextView hint;

    NewsAdapter newsAdapter;
    ArrayList<News> newsArrayList = new ArrayList<>();

    StudyAdapter studyAdapter;
    ArrayList<Study> studyArrayList = new ArrayList<>();

    @SuppressLint("ValidFragment")
    public CurrencyFragment(int id, int type) {
        this.id = id;
        this.type = type;
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_news_tuijina;
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
                if (id == 1) {
                    getNewsData(1);
                } else {
                    getStudysData(1);
                }
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                if (id == 1) {
                    getNewsData(mPagerIndex);
                } else {
                    getStudysData(mPagerIndex);
                }
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.addItemDecoration(new SpaceItemDecoration(1));
        if (id == 1) {
            newsAdapter = new NewsAdapter(getActivity(), newsArrayList);
            recyclerview.setAdapter(newsAdapter);
            getNewsData(mPagerIndex);
        } else {
            studyAdapter = new StudyAdapter(getActivity(), studyArrayList);
            recyclerview.setAdapter(studyAdapter);
            getStudysData(mPagerIndex);
        }
    }

    private void getNewsData(int pageIndex) {
        ApiService apiService = RetrofitUtil.getRetrofitInstance(getActivity()).create(ApiService.class);
        Call<String> call1 = apiService.getNews(false, type, "", pageIndex, mPageSize, "");
        call1.enqueue(new Callback<String>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String json = response.body();
                Gson gson = new Gson();
                try {
                    NewsResponse object = gson.fromJson(json, NewsResponse.class);
                    if (object != null) {
                        NewsResponse.Response rp = object.getData();
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
                                newsArrayList.clear();
                                newsArrayList.addAll(rp.getResults());
                            } else if (pageIndex > 1) {
                                refreshLayout.finishLoadmore();
                                newsArrayList.addAll(rp.getResults());
                            }
                            newsAdapter.notifyDataSetChanged();
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

    private void getStudysData(int pageIndex) {
        ApiService apiService = RetrofitUtil.getRetrofitInstance(getActivity()).create(ApiService.class);
        Call<String> call1 = apiService.getStudys(0,1, type, "", pageIndex, mPageSize, "");
        call1.enqueue(new Callback<String>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String json = response.body();
                Gson gson = new Gson();
                try {
                    StudyResponse object = gson.fromJson(json, StudyResponse.class);
                    if (object != null) {
                        StudyResponse.Response rp = object.getData();
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
                                studyArrayList.clear();
                                studyArrayList.addAll(rp.getResults());
                            } else if (pageIndex > 1) {
                                refreshLayout.finishLoadmore();
                                studyArrayList.addAll(rp.getResults());
                            }
                            studyAdapter.notifyDataSetChanged();
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
