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
import com.mltj.xxks.activity.NoticeDetailActivity;
import com.mltj.xxks.bean.FileJoin;
import com.mltj.xxks.bean.Notice;
import com.mltj.xxks.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author milantiejiang
 */
public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder> {

    private Context context;
    private List<Notice> data;

    public NoticeAdapter(Context context, List<Notice> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_notice, viewGroup, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final Notice notice=data.get(position);
        viewHolder.date.setText(Util.getDateStr(notice.getLongCreateDate()));
        viewHolder.title.setText(notice.getTitle());
        viewHolder.description.setText(notice.getIntroduction());
        ArrayList<FileJoin> flist=notice.getFileJoins();
        if(flist!=null){
            for(int i=0;i<flist.size();i++){
                String url=flist.get(i).getFileUrl();
                Glide.with(context).load(url).placeholder(Util.getRandomColor()).into(viewHolder.img);
            }
        }else {
            viewHolder.img.setBackgroundColor(Util.getRandomColor());
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoticeDetailActivity.start(context,notice);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView date;
        private ImageView img;
        private TextView title;
        private TextView description;

        public ViewHolder(View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.date);
            img=itemView.findViewById(R.id.img);
            title=itemView.findViewById(R.id.title);
            description=itemView.findViewById(R.id.description);
        }

        public TextView getDate() {
            return date;
        }

        public void setDate(TextView date) {
            this.date = date;
        }

        public ImageView getImg() {
            return img;
        }

        public void setImg(ImageView img) {
            this.img = img;
        }

        public TextView getTitle() {
            return title;
        }

        public void setTitle(TextView title) {
            this.title = title;
        }

        public TextView getDescription() {
            return description;
        }

        public void setDescription(TextView description) {
            this.description = description;
        }
    }

}
