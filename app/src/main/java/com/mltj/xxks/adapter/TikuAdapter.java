package com.mltj.xxks.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mltj.xxks.R;
import com.mltj.xxks.bean.QuestionOptionBean;
import com.mltj.xxks.bean.Wrong;

import java.util.ArrayList;
import java.util.List;

/**
 * @author milantiejiang
 */
public class TikuAdapter extends BaseAdapter {
    private Context context;
    private List<Wrong> data;

    public TikuAdapter(Context context, List<Wrong> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_tiku, parent, false);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        final Wrong wrong=data.get(position);
        int type=Integer.valueOf(wrong.getQuestionType());
        if(type==1){
            viewHolder.subject_type.setText("单选题");
        }else if(type==2){
            viewHolder.subject_type.setText("多选题");
        }else if(type==3){
            viewHolder.subject_type.setText("填空题");
        }else {
            viewHolder.subject_type.setText("判断题");
        }
        viewHolder.subject.setText(wrong.getQuestionContent());
        StringBuffer optionsStr=new StringBuffer();
        ArrayList<QuestionOptionBean> list= (ArrayList<QuestionOptionBean>) wrong.getQuestionOptions();
        if(list!=null){
            for(int i=0;i<list.size();i++){
                QuestionOptionBean questionOptionBean=list.get(i);
                optionsStr.append(questionOptionBean.getOptionCode()+","+questionOptionBean.getOptionName()+" ");
            }
        }
        viewHolder.options.setText(optionsStr);
        viewHolder.answer.setText("正确答案："+wrong.getReferenceAnswer());

        return convertView;
    }


    public class ViewHolder {
        private TextView subject_type;
        private TextView subject;
        private TextView options;
        private TextView answer;

        public ViewHolder(View itemView) {
            subject_type=itemView.findViewById(R.id.subject_type);
            subject=itemView.findViewById(R.id.subject);
            options=itemView.findViewById(R.id.options);
            answer=itemView.findViewById(R.id.answer);
        }

        public TextView getSubject_type() {
            return subject_type;
        }

        public void setSubject_type(TextView subject_type) {
            this.subject_type = subject_type;
        }

        public TextView getSubject() {
            return subject;
        }

        public void setSubject(TextView subject) {
            this.subject = subject;
        }

        public TextView getOptions() {
            return options;
        }

        public void setOptions(TextView options) {
            this.options = options;
        }

        public TextView getAnswer() {
            return answer;
        }

        public void setAnswer(TextView answer) {
            this.answer = answer;
        }
    }

}
