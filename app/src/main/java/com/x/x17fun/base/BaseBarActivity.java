package com.x.x17fun.base;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.telephony.TelephonyManager;
import android.widget.ImageView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.x.x17fun.R;

import java.util.List;


public abstract class BaseBarActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

    }


    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    abstract protected void initView();

    abstract protected void initData();

    abstract protected void initLogic();

    public void loadImg(Object path , ImageView imageView){
        Glide.with(this).load(path).into(imageView);
    }

    public void loadCircleImg(Object path , ImageView imageView){
        RequestOptions requestOptions = new RequestOptions().circleCrop();
        Glide.with(this).load(path).apply(requestOptions).into(imageView);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void startToActivity(Context context, Class<?> aClass) {
        Intent intent = new Intent(context, aClass);
        startActivity(intent);
    }



}
