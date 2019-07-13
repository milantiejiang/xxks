package com.mltj.xxks.activity;

import android.annotation.SuppressLint;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.mltj.xxks.R;
import com.mltj.xxks.bean.DateCategory;
import com.mltj.xxks.bean.MessageEvent;
import com.mltj.xxks.bean.Ranking2Response;
import com.mltj.xxks.fragment.main.CompanyRankingFragment;
import com.mltj.xxks.fragment.main.DepmentRankingFragment;
import com.mltj.xxks.net.ApiService;
import com.mltj.xxks.net.RetrofitUtil;
import com.mltj.xxks.util.Contents;
import com.mltj.xxks.util.SpUtils;
import com.mltj.xxks.util.UserSPUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RankingActivity extends BasiceActivity implements View.OnClickListener {
    @BindView(R.id.selcet)
    TextView selcet;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.company_pm)
    TextView companypm;
    @BindView(R.id.dep_pm)
    TextView deppm;

    int mdep = 0;
    int mcompany = 0;
    ArrayList<Fragment> fragmentList;
    private ArrayList<String> titles = new ArrayList<>();

    ArrayList<String> options1Items = new ArrayList<>();
    ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    ArrayList<ArrayList<DateCategory>> deps = new ArrayList<>();
    ArrayList<DateCategory> alls = (ArrayList<DateCategory>) SpUtils.getDataList(this, Contents.KEY_DATA_CATEGORY_ALL, DateCategory.class);

    ArrayList<DateCategory> companys = (ArrayList<DateCategory>) SpUtils.getDataList(this, Contents.KEY_DATA_CATEGORY_COMPANY, DateCategory.class);

    @Override
    protected int initLayout() {
        return R.layout.activity_ranking;
    }

    @Override
    protected void init() {
        if (companys != null) {
            for (int i = 0; i < companys.size(); i++) {
                options1Items.add(companys.get(i).getCategoryCnName());
            }
        }
        if (companys != null) {
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
        String tx = UserSPUtil.getInstance(this).getString(Contents.KEY_USER_COMPANY)
                + UserSPUtil.getInstance(this).getString(Contents.KEY_USER_DEPMENT);
        selcet.setText(tx);
        if (deps != null) {
            mdep = UserSPUtil.getInstance(this).getInt(Contents.KEY_USER_DEPMENT_ID);
        }
        if (companys != null) {
            mcompany = UserSPUtil.getInstance(this).getInt(Contents.KEY_USER_COMPANY_ID);
        }
        MessageEvent messageEvent = new MessageEvent("");
        messageEvent.setValue(mcompany + "," + mdep);
        EventBus.getDefault().post(messageEvent);

//        titles.add("总榜");
        titles.add("公司榜");
        titles.add("部门榜");
        fragmentList = new ArrayList<>();

//        fragmentList.add(new TotalRankingFragment(mcompany,mdep));
        fragmentList.add(new CompanyRankingFragment(mcompany, mdep));
        fragmentList.add(new DepmentRankingFragment(mcompany, mdep));

        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return titles.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles.get(position);
            }

            @Override
            public Fragment getItem(int i) {
                return fragmentList.get(i);
            }
        });
        tablayout.setupWithViewPager(viewPager);
        getData(1);
    }

    private void showSelcet() {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(RankingActivity.this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置

                if (options2Items.size() != 0 &&
                        options1Items.size() != 0 &&
                        options2Items.get(options1).size() != 0) {
                    String tx = options1Items.get(options1)
                            + options2Items.get(options1).get(option2);
                    selcet.setText(tx);
                    if (deps != null) {
                        mdep = deps.get(options1).get(option2).getId();
                    }
                    if (companys != null) {
                        mcompany = companys.get(options1).getId();
                    }

                    MessageEvent messageEvent = new MessageEvent("");
                    messageEvent.setValue(mcompany + "," + mdep);
                    EventBus.getDefault().post(messageEvent);
                }
            }
        }).build();
        pvOptions.setPicker(options1Items, options2Items);
        pvOptions.show();
    }

    @OnClick({R.id.back, R.id.selcet})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.selcet:
                showSelcet();
                break;
            case R.id.back:
                finish();
                break;
            default:
                break;
        }
    }

    private void getData(int index) {
        ApiService apiService = RetrofitUtil.getRetrofitInstance(this).create(ApiService.class);
        Call<String> call1 = apiService.getPointsLeaderboard(mcompany,
                mdep,
                3,
                index, 10, "", UserSPUtil.getInstance(this).getInt(Contents.KEY_USER_ID));
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
                            companypm.setText(rp.getCompanyRanking()+"");
                            deppm.setText(rp.getDepartmentRanking()+"");
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
