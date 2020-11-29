package com.x.x17fun.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.x.x17fun.R;
import com.x.x17fun.base.App;
import com.x.x17fun.base.BaseActivity;
import com.x.x17fun.custom.statubar.StatusBarCompat;
import com.x.x17fun.data.DataService;
import com.x.x17fun.utils.DeeSpUtil;


public class SplashActivity extends BaseActivity {

    /**
     * 登录
     */
    private TextView mLogin;
    /**
     * 注册
     */
    private TextView mRegist;
    private boolean mFirstLaunch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        StatusBarCompat.changeToLightStatusBar(this);
        setContentView(R.layout.activity_splash);

        initView();
        initData();
    }

    @Override
    protected void initView() {
        mLogin = (TextView) findViewById(R.id.login);
        mRegist = (TextView) findViewById(R.id.regist);

        mLogin.setOnClickListener(v -> {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
        });
        mRegist.setOnClickListener(v -> {
            Intent intent = new Intent(SplashActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void initData() {
        //检验是否登录
        if (!"".equals(DeeSpUtil.getInstance().getString("userId")) &&
                !"".equals(DeeSpUtil.getInstance().getString("ticket"))){

            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();

        }else{
            mLogin.setVisibility(View.VISIBLE);
            mRegist.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void initLogic() {

    }
}
