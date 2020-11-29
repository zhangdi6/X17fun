package com.x.x17fun.ui.activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.x.x17fun.R;
import com.x.x17fun.adapter.DiningVpAdapter;
import com.x.x17fun.base.BaseActivity;
import com.x.x17fun.custom.statubar.StatusBarCompat;
import com.x.x17fun.ui.fragment.DiniHallFragment;
import com.x.x17fun.ui.fragment.DiningFragment;
import com.x.x17fun.utils.ResUtils;

import java.util.ArrayList;

public class DiniingHallActivityActivity extends BaseActivity  {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dining_hall);
        loadRootFragment(R.id.frag, DiniHallFragment.newInstance());

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
