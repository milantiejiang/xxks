package com.mltj.xxks.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mltj.xxks.R;
import com.mltj.xxks.activity.ExaminationCardActivity;
import com.mltj.xxks.bean.TestPaper;
import com.mltj.xxks.util.Util;

import java.util.List;

/**
 * @author milantiejiang
 */
public class TestPaperAdapter extends RecyclerView.Adapter<TestPaperAdapter.ViewHolder> {
    private Context context;
    private List<TestPaper> data;

    public TestPaperAdapter(Context context, List<TestPaper> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_test_paper, viewGroup, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final TestPaper testPaper=data.get(position);
        viewHolder.title.setText(testPaper.getTitle());
        viewHolder.description.setText(testPaper.getIntroduction());
        viewHolder.time.setText(Util.getDateStr(testPaper.getLongCreateDate()));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExaminationCardActivity.start(context,testPaper);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView description;
        private TextView time;

        public ViewHolder(View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            description=itemView.findViewById(R.id.description);
            time=itemView.findViewById(R.id.time);
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

        public TextView getTime() {
            return time;
        }

        public void setTime(TextView time) {
            this.time = time;
        }
    }

}
