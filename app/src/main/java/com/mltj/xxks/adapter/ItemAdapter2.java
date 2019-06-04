package com.mltj.xxks.adapter;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mltj.xxks.bean.QuestionBean;
import com.mltj.xxks.fragment.card.QuestionItemFragment2;

import java.util.ArrayList;

/**
 * @Description: ViewPager的数据适配器
 * @author hzc
 */
public class ItemAdapter2 extends FragmentStatePagerAdapter {
	Context context;
	ArrayList<QuestionBean> list;

	public ItemAdapter2(FragmentManager fm, Context context,ArrayList<QuestionBean> list) {
		super(fm);
		this.context=context;
		this.list=list;
	}
	@Override
	public Fragment getItem(int arg0) {
		return new QuestionItemFragment2(context,arg0,list.get(arg0));
	}
 

	@Override
	public int getCount() {
		if(list!=null){
			return list.size();
		}
		return 0;
	}
  


}
