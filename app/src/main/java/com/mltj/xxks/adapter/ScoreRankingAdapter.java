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
import com.mltj.xxks.bean.ScoreRanking;

import java.util.List;

/**
 * @author milantiejiang
 */
public class ScoreRankingAdapter extends RecyclerView.Adapter<ScoreRankingAdapter.ViewHolder> {
    private Context context;
    private List<ScoreRanking> data;

    public ScoreRankingAdapter(Context context, List<ScoreRanking> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_score_ranking, viewGroup, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final ScoreRanking ranking=data.get(position);
        if(position==0){
            viewHolder.name.setTextColor(context.getColor(R.color.yellow));
        }else {
            viewHolder.name.setTextColor(context.getColor(R.color.black));
        }
        viewHolder.name.setText(ranking.getName());
        double score=Double.valueOf(ranking.getSumTotalScore());
        viewHolder.duration.setText(score+"分");
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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
