package com.mltj.xxks.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.mltj.xxks.R;
import com.mltj.xxks.bean.Ans;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class ResultActivity extends BasiceActivity implements View.OnClickListener{
    @BindView(R.id.score)
    TextView score;
    @BindView(R.id.wrong)
    TextView wrong;

    public static void start(Context context, int score, HashMap<Integer, Ans> wrongans){
        Intent intent=new Intent(context,ResultActivity.class);
        intent.putExtra("score",score);
        Bundle bundle=new Bundle();
        bundle.putSerializable("wrongans",wrongans);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void start(Context context, int score){
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
        if(getIntent().getExtras()!=null){
            HashMap<Integer,Ans> map= (HashMap<Integer, Ans>) getIntent().getExtras().get("wrongans");
            if(map!=null){
                StringBuffer str=new StringBuffer();
                for (Map.Entry<Integer, Ans> entry : map.entrySet()) {
                    str.append("第").append(entry.getKey()+1).append("题:\n")
                            .append("  您的答案：").append(entry.getValue().getMyAns()).append("\n")
                            .append("  正确答案：").append(entry.getValue().getRightAns()).append("\n");
                }
                wrong.setText("您答错的题：\n"+str);
                wrong.setMovementMethod(ScrollingMovementMethod.getInstance());
            }
        }
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
