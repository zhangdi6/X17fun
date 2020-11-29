package com.x.x17fun.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.x.x17fun.R;
import com.x.x17fun.base.BaseActivity;
import com.x.x17fun.custom.statubar.StatusBarCompat;
import com.x.x17fun.entity.TodayEntity;
import com.x.x17fun.entity.TomorrowEntity;
import com.x.x17fun.ui.fragment.ProductDetailFragment;

import java.io.Serializable;

public class ProductDetailActivity extends BaseActivity {


    private TodayEntity.TodayPbulishedListBean id1 ;
    private TomorrowEntity.TomorrowPublishedListBean id2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_product_detail);

        StatusBarCompat.setStatusBarColor(this,Color.WHITE);
        StatusBarCompat.changeToLightStatusBar(this);
        Intent intent = getIntent();
        if (intent!=null){
            int type = intent.getIntExtra("type", 0);
            double lat = intent.getDoubleExtra("lat", 0);
            double aLong = intent.getDoubleExtra("long", 0);
            if (type==1){
                 id1 = (TodayEntity.TodayPbulishedListBean) intent.getSerializableExtra("bean");
                Log.e("sss1",id1.toString());
                loadRootFragment(R.id.frag, ProductDetailFragment.getInstance(id1,type,lat,aLong));
            }else{
                 id2 = (TomorrowEntity.TomorrowPublishedListBean) intent.getSerializableExtra("bean");
                loadRootFragment(R.id.frag, ProductDetailFragment.getInstance2(id2,type,lat,aLong));
            }

        }
        initView();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initLogic() {

    }
}
