package com.mltj.xxks.fragment.main;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.mltj.xxks.R;
import com.mltj.xxks.adapter.NewsAdapter;
import com.mltj.xxks.bean.DateCategory;
import com.mltj.xxks.bean.News;
import com.mltj.xxks.fragment.BasiceFragment;
import com.mltj.xxks.fragment.currency.CurrencyFragment;
import com.mltj.xxks.util.Contents;
import com.mltj.xxks.util.SpUtils;

import java.util.ArrayList;


public class NewsFragment extends BasiceFragment implements NewsAdapter.ItemClickListener {
    TabLayout tablayout;

    ViewPager viewPager;

    ArrayList<Fragment> fragmentList;
    private ArrayList<DateCategory> titles = (ArrayList<DateCategory>) SpUtils.getDataList(getActivity(), Contents.KEY_DATA_CATEGORY_NEWS, DateCategory.class);

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_news;
    }

    @Override
    protected void initView(View view) {
        tablayout = view.findViewById(R.id.tablayout);
        viewPager = view.findViewById(R.id.viewpager);
        fragmentList = new ArrayList<>();

        for(int i=0;i<titles.size();i++){
            fragmentList.add(new CurrencyFragment(1,titles.get(i).getId()));
        }


        viewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @Override
            public int getCount() {
                return titles.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles.get(position).getCategoryCnName();
            }

            @Override
            public Fragment getItem(int i) {
                return fragmentList.get(i);
            }
        });
        tablayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onItemClick(News log) {

    }
}
