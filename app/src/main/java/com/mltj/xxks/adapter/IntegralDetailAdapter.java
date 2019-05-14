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
import com.mltj.xxks.bean.IntegralDetail;

import java.util.List;

/**
 * @author milantiejiang
 */
public class IntegralDetailAdapter extends RecyclerView.Adapter<IntegralDetailAdapter.ViewHolder> {
    private Context context;
    private List<IntegralDetail> data;

    public IntegralDetailAdapter(Context context, List<IntegralDetail> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_integral_detail, viewGroup, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final IntegralDetail detail=data.get(position);
        viewHolder.name.setText(detail.getCategoryCnName());
        viewHolder.time.setText(detail.getCreateDate());
        viewHolder.score.setText("+"+detail.getScore());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView time;
        private TextView score;

        public ViewHolder(View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            time=itemView.findViewById(R.id.time);
            score=itemView.findViewById(R.id.score);
        }

        public TextView getName() {
            return name;
        }

        public void setName(TextView name) {
            this.name = name;
        }

        public TextView getTime() {
            return time;
        }

        public void setTime(TextView time) {
            this.time = time;
        }

        public TextView getScore() {
            return score;
        }

        public void setScore(TextView score) {
            this.score = score;
        }
    }

}
