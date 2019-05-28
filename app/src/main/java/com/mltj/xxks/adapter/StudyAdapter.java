package com.mltj.xxks.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mltj.xxks.R;
import com.mltj.xxks.activity.StudyDetailActivity;
import com.mltj.xxks.activity.VideoDetailActivity;
import com.mltj.xxks.bean.FileJoin;
import com.mltj.xxks.bean.Study;
import com.mltj.xxks.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author milantiejiang
 */
public class StudyAdapter extends RecyclerView.Adapter<StudyAdapter.ViewHolder> {

    private Context context;
    private List<Study> data;

    public StudyAdapter(Context context, List<Study> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_study, viewGroup, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final Study study = data.get(position);
        viewHolder.title.setText(study.getTitle());
        viewHolder.date.setText(Util.getDateStr(study.getLongCreateDate()));
        ArrayList<FileJoin> flist=study.getFileJoins();
        if(flist!=null){
            for(int i=0;i<flist.size();i++){
                String url=flist.get(i).getFileUrl();
                Glide.with(context).load(url).placeholder(Util.getRandomColor()).into(viewHolder.icon);
            }
        }else {
            viewHolder.icon.setBackgroundColor(Util.getRandomColor());
            viewHolder.icon.setVisibility(View.GONE);
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(study.getStudyContentType()==2){
                    VideoDetailActivity.start(context,study);
                }else {
                    StudyDetailActivity.start(context,study);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView date;
        private ImageView icon;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            date = itemView.findViewById(R.id.date);
            icon = itemView.findViewById(R.id.icon);
        }

        public TextView getTitle() {
            return title;
        }

        public void setTitle(TextView title) {
            this.title = title;
        }

        public TextView getDate() {
            return date;
        }

        public void setDate(TextView date) {
            this.date = date;
        }

        public ImageView getIcon() {
            return icon;
        }

        public void setIcon(ImageView icon) {
            this.icon = icon;
        }
    }

}
