package com.x.x17fun.ui.activity;

import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.x.x17fun.R;
import com.x.x17fun.adapter.PhotoAdapter;
import com.x.x17fun.base.BaseActivity;
import com.x.x17fun.custom.statubar.StatusBarCompat;

import java.util.ArrayList;

public class PhotoActivityActivity extends BaseActivity {

    private ImageView mBack;
    /**
     *
     */
    private TextView mTitle;
    private ImageView mImg;
    private ViewPager mVp;
    private String img = "";
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, Color.parseColor("#f5f5f5"));
        StatusBarCompat.changeToLightStatusBar(this);
        setContentView(R.layout.activity_photo_activity);
         img = getIntent().getStringExtra("img");
         position = getIntent().getIntExtra("po",0);
        initView();
        initData();
    }

    @Override
    protected void initView() {

        mBack = (ImageView) findViewById(R.id.back);
        mTitle = (TextView) findViewById(R.id.title);
        mImg = (ImageView) findViewById(R.id.img);
        mVp = (ViewPager) findViewById(R.id.vp);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {

            if (img.contains(",")){
                mVp.setVisibility(View.VISIBLE);
                mImg.setVisibility(View.GONE);
                String[] split = img.split(",");

                mTitle.setText(position+1+"/"+split.length);
                ArrayList<ImageView> objects = new ArrayList<>();
                for (int i = 0; i < split.length; i++) {
                    ImageView imageView = new ImageView(this);
                    /*Glide.with(this).load(split[i]).into(imageView);*/
                    objects.add(imageView);
                }
                PhotoAdapter adapter = new PhotoAdapter(objects,split);
                mVp.setAdapter(adapter);
                mVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int i, float v, int i1) {

                    }

                    @Override
                    public void onPageSelected(int i) {
                        mTitle.setText(i+1+"/"+split.length);
                    }

                    @Override
                    public void onPageScrollStateChanged(int i) {

                    }
                });
                mVp.setCurrentItem(position);

            }else{
                mVp.setVisibility(View.GONE);
                mImg.setVisibility(View.VISIBLE);
                mTitle.setText("查看大图");
                Glide.with(this).load(img).into(mImg);
            }

    }

    @Override
    protected void initLogic() {

    }
}
