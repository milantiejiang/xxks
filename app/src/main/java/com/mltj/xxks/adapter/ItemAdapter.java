package com.mltj.xxks.adapter;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mltj.xxks.bean.QuestionBean;
import com.mltj.xxks.bean.QuestionBeanResponse2;
import com.mltj.xxks.fragment.card.QuestionItemFragment;

import java.util.ArrayList;

/**
 * @Description: ViewPager的数据适配器
 * @author hzc
 */
public class ItemAdapter extends FragmentStatePagerAdapter {
	Context context;
	ArrayList<QuestionBean> list;
	QuestionBeanResponse2.TestPaper testPaper;

	public ItemAdapter(FragmentManager fm, Context context, QuestionBeanResponse2.TestPaper testPaper) {
		super(fm);
		this.context=context;
		this.list=testPaper.getQuestionBankList();
		this.testPaper=testPaper;
	}
	@Override
	public Fragment getItem(int arg0) {
		return new QuestionItemFragment(context,arg0,list.get(arg0),testPaper.getShowAnswerAnalysis());
	}
 

	@Override
	public int getCount() {
		if(list!=null){
			return list.size();
		}
		return 0;
	}
}
