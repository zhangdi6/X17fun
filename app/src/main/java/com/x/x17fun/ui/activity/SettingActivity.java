package com.x.x17fun.ui.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.x.x17fun.R;
import com.x.x17fun.base.BaseActivity;
import com.x.x17fun.custom.statubar.StatusBarCompat;
import com.x.x17fun.ui.fragment.SaleProductManageFragment;
import com.x.x17fun.ui.fragment.SettingFragment;

public class SettingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        StatusBarCompat.changeToLightStatusBar(this);
        setContentView(R.layout.activity_setting);
        initView();
    }

    @Override
    protected void initView() {
        loadRootFragment(R.id.frag, SettingFragment.getInstance());
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initLogic() {

    }
}
