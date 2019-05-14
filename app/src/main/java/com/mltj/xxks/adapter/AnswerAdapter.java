package com.mltj.xxks.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mltj.xxks.R;
import com.mltj.xxks.activity.ExaminationCardActivity;
import com.mltj.xxks.bean.Answer;
import com.mltj.xxks.bean.MessageEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * @author milantiejiang
 */
public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.ViewHolder> {

    private Context context;
    private List<Answer> data=new ArrayList<>();

    public AnswerAdapter(Context context,int size) {
        this.context = context;
        initData(size);
    }

    private void initData(int size){
        data.clear();
        for(int i=0;i<size;i++){
            Answer answer=new Answer();
            answer.setPosition(i+1);
            data.add(answer);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_answer, viewGroup, false);
        return new ViewHolder(view);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        final Answer answer=data.get(position);
        viewHolder.num.setText(answer.getPosition()+"");
        Answer a=((ExaminationCardActivity)context).getAnswerMap().get(position);
        if(a!=null){
            viewHolder.num.setTextColor(context.getColor(R.color.withe));
        }else {
            viewHolder.num.setTextColor(context.getColor(R.color.black));
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageEvent event=new MessageEvent(ExaminationCardActivity.EVENT_PAGER_JUMP);
                event.setValue(position+"");
                EventBus.getDefault().post(event);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView num;

        public ViewHolder(View itemView) {
            super(itemView);
            num=itemView.findViewById(R.id.num);

        }

        public TextView getNum() {
            return num;
        }

        public void setNum(TextView num) {
            this.num = num;
        }
    }

}
