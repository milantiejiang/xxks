package com.mltj.xxks.activity;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.githang.statusbar.StatusBarCompat;
import com.mltj.xxks.R;
import com.mltj.xxks.bean.MessageEvent;
import com.mltj.xxks.fragment.main.HomeFragment;
import com.mltj.xxks.fragment.main.NewsFragment;
import com.mltj.xxks.fragment.main.StudyFragment;
import com.mltj.xxks.fragment.main.UserFragment;
import com.mltj.xxks.fragment.main.VideoFragment;
import com.mltj.xxks.widget.ControlScrollViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BasiceActivity {
    @BindView(R.id.fragment_vp)
    ControlScrollViewPager mViewPager;
    @BindView(R.id.tabs_rg)
    RadioGroup mTabRadioGroup;

    private List<Fragment> mFragments;
    private FragmentPagerAdapter mAdapter;
    long startTime;
    long endTime;

    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {
        EventBus.getDefault().register(this);
        Calendar calendar = Calendar.getInstance();
        startTime = calendar.getTimeInMillis();
        // init fragment
        mFragments = new ArrayList<>(5);
        mFragments.add(new HomeFragment());
        mFragments.add(new NewsFragment());
        mFragments.add(new StudyFragment());
        mFragments.add(new VideoFragment());
        mFragments.add(new UserFragment());
        // init view pager
        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragments);

        mViewPager.setAdapter(mAdapter);
        // register listener
        mViewPager.addOnPageChangeListener(mPageChangeListener);
        mTabRadioGroup.setOnCheckedChangeListener(mOnCheckedChangeListener);
        mViewPager.setCurrentItem(4);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initStatusBar() {
        StatusBarCompat.setStatusBarColor(this, getColor(R.color.red), true);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        if(messageEvent.getMessage().equals("go_video")){
            mViewPager.setCurrentItem(3);
        }else if(messageEvent.getMessage().equals("go_study")){
            mViewPager.setCurrentItem(2);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewPager.removeOnPageChangeListener(mPageChangeListener);
        Calendar calendar = Calendar.getInstance();
        endTime = calendar.getTimeInMillis();
        long time = endTime - startTime;
        Log.e("MainActivity","打开时长："+time/1000+"秒");
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    private ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            RadioButton radioButton = (RadioButton) mTabRadioGroup.getChildAt(position);
            radioButton.setChecked(true);
            if(position==0){

            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private RadioGroup.OnCheckedChangeListener mOnCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            for (int i = 0; i < group.getChildCount(); i++) {
                if (group.getChildAt(i).getId() == checkedId) {
                    mViewPager.setCurrentItem(i);
                    return;
                }
            }
        }
    };

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> mList;

        public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            this.mList = list;
        }

        @Override
        public Fragment getItem(int position) {
            return this.mList == null ? null : this.mList.get(position);
        }

        @Override
        public int getCount() {
            return this.mList == null ? 0 : this.mList.size();
        }
    }
}
