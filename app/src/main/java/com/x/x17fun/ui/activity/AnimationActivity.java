package com.x.x17fun.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.x.x17fun.R;

public class AnimationActivity extends AppCompatActivity {

    private ImageView mImg;
    /**
     * 哈哈哈\n哈哈哈哈
     */
    private TextView mTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        initView();
        initAnimation();
    }

    private void initAnimation() {

        //图片

        ScaleAnimation scaleAnimation =new ScaleAnimation(1,0.5f,1,0.5f);
        scaleAnimation.setDuration(3000);
        scaleAnimation.setFillAfter(true);
        TranslateAnimation translateAnimation =new TranslateAnimation(0,-50,0,200);
        translateAnimation.setDuration(3000);
        translateAnimation.setFillAfter(true);

        AnimationSet animationSet = new AnimationSet(false);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(translateAnimation);

        animationSet.setDuration(3000);
        animationSet.setFillAfter(true);


        //文字

        ScaleAnimation scaleAnimation2 =new ScaleAnimation(1,0.8f,1,0.8f);
        scaleAnimation2.setDuration(3000);
        scaleAnimation2.setFillAfter(true);

        TranslateAnimation translateAnimation2 =new TranslateAnimation(0,100,0,-500);
        translateAnimation2.setDuration(3000);
        translateAnimation2.setFillAfter(true);

        AnimationSet animationSet2 = new AnimationSet(false);
        animationSet2.addAnimation(scaleAnimation2);
        animationSet2.addAnimation(translateAnimation2);
        animationSet2.setDuration(3000);
        animationSet2.setFillAfter(true);

        mImg.startAnimation(animationSet);
        mTv.startAnimation(animationSet2);
    }

    private void initView() {
        mImg = (ImageView) findViewById(R.id.img);
        mTv = (TextView) findViewById(R.id.tv);
    }
}
