package com.mltj.xxks.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.v4.content.res.ResourcesCompat;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.mltj.xxks.R;
import com.mltj.xxks.util.Util;

import butterknife.BindView;

public class WelcomeActivity extends BasiceActivity {
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.tv3)
    TextView tv3;

    private CountDownTimer mTimer;

    @Override
    protected int initLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void init() {
        super.init();
        Typeface typeface = ResourcesCompat.getFont(this, R.font.gy);
        tv1.setTypeface(typeface);
        tv2.setTypeface(typeface);
        tv3.setTypeface(typeface);
        Glide.with(WelcomeActivity.this).load(R.drawable.sd).
                diskCacheStrategy(DiskCacheStrategy.SOURCE).
                into(new SimpleTarget<GlideDrawable>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        if (resource.isAnimated()) {
                            resource.setLoopCount(GifDrawable.LOOP_FOREVER);
                            resource.start();
                        }
                        image.setImageDrawable(resource);
                        //动画效果从XMl文件中定义
                        Animation animation = AnimationUtils.loadAnimation(WelcomeActivity.this, R.anim.alpha);
                        tv1.setAnimation(animation);
                        tv2.setAnimation(animation);
                        tv3.setAnimation(animation);
                        startCounter();
                    }
                });


    }

    // 开始计时
    public void startCounter() {
        if (mTimer == null) {
            mTimer = new CountDownTimer((long) (3 * 1000), 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    if (!WelcomeActivity.this.isFinishing()) {
                        if (Util.isLogin(WelcomeActivity.this)) {
                            Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                        finish();
                    }
                }
            };
            mTimer.start();
        }
    }

    @Override
    protected void onDestroy() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        super.onDestroy();
    }
}
