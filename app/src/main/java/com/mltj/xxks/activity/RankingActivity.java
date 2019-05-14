package com.mltj.xxks.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.mltj.xxks.R;
import com.mltj.xxks.bean.DateCategory;
import com.mltj.xxks.bean.MessageEvent;
import com.mltj.xxks.fragment.main.CompanyRankingFragment;
import com.mltj.xxks.fragment.main.DepmentRankingFragment;
import com.mltj.xxks.fragment.main.TotalRankingFragment;
import com.mltj.xxks.util.Contents;
import com.mltj.xxks.util.SpUtils;
import com.mltj.xxks.util.UserSPUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class RankingActivity extends BasiceActivity implements View.OnClickListener {
    @BindView(R.id.selcet)
    TextView selcet;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

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

        titles.add("总榜");
        titles.add("公司榜");
        titles.add("部门榜");
        fragmentList = new ArrayList<>();

        fragmentList.add(new TotalRankingFragment(mcompany,mdep));
        fragmentList.add(new CompanyRankingFragment(mcompany,mdep));
        fragmentList.add(new DepmentRankingFragment(mcompany,mdep));

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

    }

    private void showSelcet() {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(RankingActivity.this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
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
}