package com.mltj.xxks.adapter;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mltj.xxks.R;
import com.mltj.xxks.bean.QuestionOptionBean;

import java.util.List;

public class OptionsListAdapter extends BaseAdapter {
    private Context mContext;
    ListView lv;
    int index;
    public List<QuestionOptionBean> options;

    public OptionsListAdapter(Context context, List<QuestionOptionBean> options, ListView lv, int index) {
        this.mContext = context;
        this.options = options;
        this.lv = lv;

    }

    @Override
    public int getCount() {
        if(options!=null){
            return options.size();
        }
        return 0;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {

        return true;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(mContext).inflate(
                R.layout.list_item_option, null);
        RelativeLayout rl_check_bg = view.findViewById(R.id.rl_check_bg);
        CheckedTextView ctv = (CheckedTextView) view.findViewById(R.id.ctv_name);
        TextView option = (TextView) view.findViewById(R.id.tv_option);

        ctv.setText(options.get(position).getOptionCode());
        option.setText(options.get(position).getOptionName());
        updateBackground(position, rl_check_bg);
        return view;

    }

    public void updateBackground(int position, View view) {
        int backgroundId;
        if (lv.isItemChecked(position)) {
            backgroundId = R.drawable.option_btn_single_checked;
        } else {
            backgroundId = R.drawable.option_btn_single_normal;
        }
        Drawable background = mContext.getResources().getDrawable(backgroundId);
        view.setBackgroundDrawable(background);
    }

}
