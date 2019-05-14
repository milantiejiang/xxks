package com.mltj.xxks.widget;

import android.app.Activity;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lcodecore.tkrefreshlayout.IHeaderView;
import com.lcodecore.tkrefreshlayout.OnAnimEndListener;
import com.mltj.xxks.R;

/**
 * Created by 米兰铁匠 on 2018/4/13.
 */

public class RefreshHeaderView extends FrameLayout implements IHeaderView {

    private TextView refreshTextView;
    private ProgressBar loadingView;
    private Activity activity;

    public RefreshHeaderView(Activity context) {
        this(context, null);
        this.activity = context;
    }

    public RefreshHeaderView(Activity context, AttributeSet attrs) {
        this(context, attrs, 0);
        this.activity = context;
    }

    public RefreshHeaderView(Activity context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.activity = context;
        init();
    }

    private void init() {
        View rootView = View.inflate(getContext(), R.layout.view_pull_to_fresh_header, null);
        loadingView =  rootView.findViewById(R.id.iv_loading);
        addView(rootView);
    }

    public void setTextColor(@ColorInt int color) {
        refreshTextView.setTextColor(color);
    }

    public void setPullDownStr(String pullDownStr) {
        this.pullDownStr = pullDownStr;
    }

    public void setReleaseRefreshStr(String releaseRefreshStr) {
        this.releaseRefreshStr = releaseRefreshStr;
    }

    public void setRefreshingStr(String refreshingStr) {
        this.refreshingStr = refreshingStr;
    }

    public void setFinishingStr(String finishingStr) {
        this.finishingStr = finishingStr;
    }

    private String pullDownStr = "下拉刷新";
    private String releaseRefreshStr = "放开以刷新";
    private String refreshingStr = "正在刷新";
    private String finishingStr = "刷新完成";

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void onPullingDown(float fraction, float maxHeadHeight, float headHeight) {

    }

    @Override
    public void onPullReleasing(float fraction, float maxHeadHeight, float headHeight) {

    }

    @Override
    public void startAnim(float maxHeadHeight, float headHeight) {

    }

    @Override
    public void onFinish(OnAnimEndListener listener) {
        listener.onAnimEnd();
    }

    @Override
    public void reset() {
    }
}
