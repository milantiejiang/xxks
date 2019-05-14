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
import com.mltj.xxks.activity.NewsDetailActivity;
import com.mltj.xxks.bean.FileJoin;
import com.mltj.xxks.bean.News;
import com.mltj.xxks.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author milantiejiang
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private Context context;
    private List<News> data;

    ItemClickListener listener;

    public NewsAdapter(Context context, List<News> data) {
        this.context = context;
        this.data = data;
    }

    public ItemClickListener getListener() {
        return listener;
    }

    public void setListener(ItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_news, viewGroup, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final News news=data.get(position);
        viewHolder.title.setText(news.getTitle());
        viewHolder.date.setText(Util.getDateStr(news.getLongCreateDate()));
        ArrayList<FileJoin> flist=news.getFileJoins();
        if(flist!=null){
            for(int i=0;i<flist.size();i++){
                String url=flist.get(i).getFileUrl();
                Glide.with(context).load(url).placeholder(Util.getRandomColor()).into(viewHolder.icon);
            }
        }else {
            viewHolder.icon.setBackgroundColor(Util.getRandomColor());
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewsDetailActivity.start(context,news);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface ItemClickListener {
        void onItemClick(News log);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView date;
        private ImageView icon;

        public ViewHolder(View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            date=itemView.findViewById(R.id.date);
            icon=itemView.findViewById(R.id.icon);
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
