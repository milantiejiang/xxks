package com.mltj.xxks.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.mltj.xxks.R;

import butterknife.BindView;
import butterknife.OnClick;

public class ResultActivity extends BasiceActivity implements View.OnClickListener{
    @BindView(R.id.score)
    TextView score;

    public static void start(Context context,int score){
        Intent intent=new Intent(context,ResultActivity.class);
        intent.putExtra("score",score);
        context.startActivity(intent);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_result;
    }

    @Override
    protected void init() {
        score.setText(getIntent().getIntExtra("score",0)+"分");
    }

    @OnClick({R.id.back})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            default:
                break;
        }
    }
}
