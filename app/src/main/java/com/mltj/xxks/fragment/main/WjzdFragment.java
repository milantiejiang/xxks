package com.mltj.xxks.fragment.main;

import android.annotation.SuppressLint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.LoadingView;
import com.mltj.xxks.R;
import com.mltj.xxks.adapter.StudyAdapter;
import com.mltj.xxks.bean.DateCategory;
import com.mltj.xxks.bean.Study;
import com.mltj.xxks.bean.StudyResponse;
import com.mltj.xxks.fragment.BasiceFragment;
import com.mltj.xxks.net.ApiService;
import com.mltj.xxks.net.RetrofitUtil;
import com.mltj.xxks.util.Contents;
import com.mltj.xxks.util.SpUtils;
import com.mltj.xxks.util.SpaceItemDecoration;
import com.mltj.xxks.util.UserSPUtil;
import com.mltj.xxks.util.Util;
import com.mltj.xxks.widget.RefreshHeaderView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WjzdFragment extends BasiceFragment {
    TextView selcet;
    TwinklingRefreshLayout refreshLayout;
    RecyclerView recyclerview;
    TextView hint;

    int mPagerIndex = 1;
    int mPageSize = 10;
    int mdep=0;
    StudyAdapter studyAdapter;
    ArrayList<Study> studyArrayList = new ArrayList<>();

    ArrayList<String> options1Items=new ArrayList<>();
    ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    ArrayList<ArrayList<DateCategory>> deps=new ArrayList<>();
    ArrayList<DateCategory> alls = (ArrayList<DateCategory>) SpUtils.getDataList(getActivity(), Contents.KEY_DATA_CATEGORY_ALL, DateCategory.class);

    ArrayList<DateCategory> companys = (ArrayList<DateCategory>) SpUtils.getDataList(getActivity(), Contents.KEY_DATA_CATEGORY_COMPANY, DateCategory.class);

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_company_select;
    }

    @Override
    protected void initView(View view) {
        selcet=view.findViewById(R.id.selcet);
        refreshLayout=view.findViewById(R.id.refreshLayout);
        recyclerview=view.findViewById(R.id.recyclerview);
        hint=view.findViewById(R.id.hint);
        options1Items.clear();
        options2Items.clear();
        if (companys != null) {
            for (int i = 0; i < companys.size(); i++) {
                options1Items.add(companys.get(i).getCategoryCnName());
            }
        }
        if(companys!=null){
            for (int i = 0; i < companys.size(); i++) {
                ArrayList<String> bm = new ArrayList<>();
                ArrayList<DateCategory> dp = new ArrayList<>();
                int type = companys.get(i).getId();
                if (alls != null) {
                    for (int j = 0; j < alls.size(); j++) {
                        if (alls.get(j).getTypeId() == type) {
                            bm.add(alls.get(j).getCategoryCnName());
                            dp.add(alls.get(j));
                        }
                    }
                }
                options2Items.add(bm);
                deps.add(dp);
            }
        }
        if(deps!=null){
            mdep= UserSPUtil.getInstance(getActivity()).getInt(Contents.KEY_USER_DEPMENT_ID);
            String tx = UserSPUtil.getInstance(getActivity()).getString(Contents.KEY_USER_COMPANY)
                    + UserSPUtil.getInstance(getActivity()).getString(Contents.KEY_USER_DEPMENT);
            selcet.setText(tx);
            refreshLayout.startRefresh();
        }
        selcet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelcet();
            }
        });
        refreshLayout.setHeaderView(new RefreshHeaderView(getActivity()));
        refreshLayout.setBottomView(new LoadingView(getActivity()));
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                getStudysData(mdep,1);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                getStudysData(mdep,mPagerIndex);
            }
        });
        studyAdapter = new StudyAdapter(getActivity(), studyArrayList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.addItemDecoration(new SpaceItemDecoration(20));
        recyclerview.setAdapter(studyAdapter);
    }

    private void showSelcet(){
        Util.hideSoftKeyboard(getActivity());
        OptionsPickerView pvOptions = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3 , View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1)
                        + options2Items.get(options1).get(option2);
                selcet.setText(tx);
                if(deps!=null){
                    mdep=deps.get(options1).get(option2).getId();
                }
                getStudysData(mdep,1);
            }
        }).build();
        pvOptions.setPicker(options1Items, options2Items);
        pvOptions.show();
    }

    private void getStudysData(int dep,int pageIndex) {
        ApiService apiService = RetrofitUtil.getRetrofitInstance(getActivity()).create(ApiService.class);
        Call<String> call1 = apiService.getStudys(dep,1, -1, "", pageIndex, mPageSize, "");
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
