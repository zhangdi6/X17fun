package com.x.x17fun.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.x.x17fun.R;
import com.x.x17fun.data.DataService;
import com.x.x17fun.entity.BaseResult;
import com.x.x17fun.entity.UserSyncEntity;
import com.x.x17fun.utils.AdiUtils;
import com.x.x17fun.utils.DeeSpUtil;
import com.x.x17fun.utils.ParamsUtils;
import com.x.x17fun.utils.SHA1Utils;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.yokeyword.fragmentation.SupportActivity;


// Created by Ardy on 2020/5/7.
@SuppressLint("RestrictedApi")

public abstract class BaseActivity extends SupportActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        setTheme(R.style.AppTheme_NoActionBar);

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
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
    }

    public void startToActivity(Context context, Class<?> aClass) {
        Intent intent = new Intent(context, aClass);
        startActivity(intent);
    }

    public void startToActivity(Context context, Class<?> aClass,String tag) {
        Intent intent = new Intent(context, aClass);
        intent.putExtra("type",tag);
        startActivity(intent);
    }

    public void syncUser(IBaseCallBack<UserSyncEntity> callBack) {

        //更新用户信息
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("userId", DeeSpUtil.getInstance().getString("userId"));
        hashMap.put("ticket", DeeSpUtil.getInstance().getString("ticket"));
     /*   String sign = ParamsUtils.getSign(hashMap);
        try {
            hashMap.put("sign", SHA1Utils.strToSHA1(sign));
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        DataService.getHomeService().sysnUser(hashMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult<UserSyncEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<UserSyncEntity> baseResult) {

                        if (baseResult != null && baseResult.getResult_code() == 0) {
                            Log.d("bbbbb", baseResult.toString());
                            callBack.onSuccess(baseResult.getData());
                        } else if (baseResult.getResult_code()== -3){
                            AdiUtils.loginOut();
                        }else {
                            callBack.onFail(baseResult.getResult_msg()==null?"请求失败":baseResult.getResult_msg());
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("bbbbb", e.getMessage());
                        callBack.onFail(e.getMessage()==null?"请求失败":e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    public void initVerify(String type, String phone , IBaseCallBack<String> callBack) {

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("phone", phone);
        hashMap.put("type", type);
       /* hashMap.put("app_id", AppContant.APPID);
        hashMap.put("nonce", "1");
        String sign = ParamsUtils.getSign(hashMap);
        try {
            hashMap.put("sign", SHA1Utils.strToSHA1(sign));

        } catch (Exception e) {
            e.printStackTrace();
        }*/

        DataService.getLoginService().get(hashMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult baseResult) {
                        if (baseResult.getResult_code() == 0) {
                            callBack.onSuccess("验证码发送成功");
                        } else if (baseResult.getResult_code()==-7){
                            callBack.onSuccess(baseResult.getResult_msg());
                        }else {
                            callBack.onFail(baseResult.getResult_msg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onFail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
