package com.x.x17fun.ui.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.x.x17fun.R;
import com.x.x17fun.base.BaseActivity;
import com.x.x17fun.custom.statubar.StatusBarCompat;
import com.x.x17fun.ui.fragment.WalletFragment;

public class MyWalletActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        StatusBarCompat.changeToLightStatusBar(this);
        setContentView(R.layout.activity_my_wallet);
        initView();
        initData();
        initLogic();
    }

    @Override
    protected void initView() {
        loadRootFragment(R.id.frag, WalletFragment.getInstance());
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initLogic() {

    }
}
