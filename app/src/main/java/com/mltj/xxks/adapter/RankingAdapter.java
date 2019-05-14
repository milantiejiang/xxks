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
import com.mltj.xxks.bean.Ranking;

import java.util.List;

/**
 * @author milantiejiang
 */
public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.ViewHolder> {
    private Context context;
    private List<Ranking> data;

    public RankingAdapter(Context context, List<Ranking> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ranking, viewGroup, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final Ranking ranking=data.get(position);
        if(position==0){
            viewHolder.name.setTextColor(context.getColor(R.color.yellow));
        }else {
            viewHolder.name.setTextColor(context.getColor(R.color.black));
        }
        viewHolder.name.setText(ranking.getName());
        long time=Long.valueOf(ranking.getSumDuration());
        viewHolder.duration.setText(getTime(time));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private String getTime(long t){
        long a=0;
        a=t/1000;
        if(a>60){
            long b=a/60;
            if(b>60){
                long c=b/60;
                return c+"小时";
            }else {
                return b+"分钟";
            }
        }
        return a+"秒";
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView duration;

        public ViewHolder(View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            duration=itemView.findViewById(R.id.duration);
        }

        public TextView getName() {
            return name;
        }

        public void setName(TextView name) {
            this.name = name;
        }

        public TextView getDuration() {
            return duration;
        }

        public void setDuration(TextView duration) {
            this.duration = duration;
        }
    }

}
