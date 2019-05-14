package com.mltj.xxks.fragment.main;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.mltj.xxks.R;
import com.mltj.xxks.fragment.BasiceFragment;

import java.util.ArrayList;

public class StudyFragment2 extends BasiceFragment {
    TabLayout tablayout;

    ViewPager viewPager;

    ArrayList<Fragment> fragmentList;
    private ArrayList<String> titles = new ArrayList<>();

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_study2;
    }

    @Override
    protected void initView(View view) {
        titles.clear();
        titles.add("文件制度");
        titles.add("题库学习");
        tablayout = view.findViewById(R.id.tablayout);
        viewPager = view.findViewById(R.id.viewpager);
        fragmentList = new ArrayList<>();
        fragmentList.add(new WjzdFragment());
        fragmentList.add(new TkxxFragment());
        viewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
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
}
