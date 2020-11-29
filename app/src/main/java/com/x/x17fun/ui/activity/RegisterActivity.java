package com.x.x17fun.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.luck.picture.lib.config.PictureConfig;
import com.x.x17fun.R;
import com.x.x17fun.base.App;
import com.x.x17fun.base.AppContant;
import com.x.x17fun.base.BaseActivity;
import com.x.x17fun.base.EventMessage;
import com.x.x17fun.base.IBaseCallBack;
import com.x.x17fun.custom.statubar.StatusBarCompat;
import com.x.x17fun.data.DataService;
import com.x.x17fun.entity.BaseResult;
import com.x.x17fun.entity.UserLoginEntity;
import com.x.x17fun.ui.fragment.SetUserProfirtFragment;
import com.x.x17fun.ui.fragment.SettingPwdFragment;
import com.x.x17fun.utils.AdiUtils;
import com.x.x17fun.utils.DeeSpUtil;
import com.x.x17fun.utils.ParamsUtils;
import com.x.x17fun.utils.SHA1Utils;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBack;
    /**
     * 6~16位含数字、字母
     */
    private EditText mEtPwd;
    /**
     * 6~16位含数字、字母
     */
    private EditText mEtConfimPwd;
    /**
     * 输入手机号
     */
    private EditText mEtPhone;
    /**
     * 输入短信验证码
     */
    private EditText mEtVerify;
    /**
     * 获取验证码
     */
    private TextView mGetVerify;
    private CheckBox mCb;
    /**
     * 我已阅读并同意
     */
    private TextView mTv;
    /**
     * 《伊甸城隐私协议》
     */
    private TextView mTvAgreeement;
    private LinearLayout mLinearLayout;
    /**
     * 完成
     */
    private TextView mRegister;
    /**
     * 去登录？
     */
    private TextView mGoToLogin;
    private TextView tv_agreeement3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        StatusBarCompat.changeToLightStatusBar(this);
        setContentView(R.layout.activity_register);
        initView();
    }

    protected void initView() {
        mBack = (ImageView) findViewById(R.id.back);
        mBack.setOnClickListener(this);
        mEtPwd = (EditText) findViewById(R.id.et_pwd);
        mEtConfimPwd = (EditText) findViewById(R.id.et_confim_pwd);
        mEtPhone = (EditText) findViewById(R.id.et_phone);
        mEtVerify = (EditText) findViewById(R.id.et_verify);
        mGetVerify = (TextView) findViewById(R.id.getVerify);
        mGetVerify.setOnClickListener(this);
        mCb = (CheckBox) findViewById(R.id.cb);
        mTv = (TextView) findViewById(R.id.tv);
        mTvAgreeement = (TextView) findViewById(R.id.tv_agreeement);

        mRegister = (TextView) findViewById(R.id.register);
        mRegister.setOnClickListener(this);
        mGoToLogin = (TextView) findViewById(R.id.go_to_login);
        mGoToLogin.setOnClickListener(this);
       /* mTvAgreeement.setOnClickListener(v -> {

                Intent intent = new Intent(RegisterActivity.this, NormalWebActivity.class);
                intent.putExtra("url", AppContant.WebUrl+"bCommercialAgreement.html");
                startActivity(intent);

        });*/

        AdiUtils.showDeleteButton(mEtPhone, 1, null);
        AdiUtils.showDeleteButton(mEtPwd, 2, null);
        AdiUtils.showDeleteButton(mEtConfimPwd, 2, null);
        AdiUtils.showDeleteButton(mEtVerify, 3, null);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initLogic() {

    }


    private void getVerify() {

        initVerify("login", mEtPhone.getText().toString(), new IBaseCallBack<String>() {
            @Override
            public void onSuccess(String data) {
                AdiUtils.showToast(data);
                time();

            }

            @Override
            public void onFail(String msg) {
                AdiUtils.showToast(msg);
            }
        });
    }

    private void time(){
        CountDownTimer countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mGetVerify.setText("重新发送("+millisUntilFinished/1000+"秒)");
                mGetVerify.setEnabled(false);
            }

            @Override
            public void onFinish() {
                mGetVerify.setText("重新发送");
                mGetVerify.setEnabled(true);
            }
        };
        countDownTimer.start();
    }
    private void initRegister(String phone, String verify, String password) {

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("phone", phone);
        hashMap.put("verificationCode", verify);
      /*  hashMap.put("app_id", AppContant.APPID);
        hashMap.put("nonce", "1");
        String sign = ParamsUtils.getSign(hashMap);
        try {

            hashMap.put("sign", SHA1Utils.strToSHA1(sign));

        } catch (Exception e) {
            e.printStackTrace();
        }*/

        DataService.getLoginService().register(hashMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<com.x.x17fun.entity.BaseResult<UserLoginEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<UserLoginEntity> baseResult) {
                        Log.e("Amout", baseResult.toString());
                        if (baseResult.getResult_code() == 0) {

                            //新用户。跳转编辑资料界面
                            if ("new".equals(baseResult.getData().getUserType())){
                                DeeSpUtil.getInstance().putString("ticket",baseResult.getData().getTicket());
                                DeeSpUtil.getInstance().putString("userId",baseResult.getData().getUserId());
                                initSetPwd(password);
                            }else{
                                showToast("您不是新用户，请直接前往登录");
                                finish();
                            }

                        } else{
                            AdiUtils.showToast(baseResult.getResult_msg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("Amout", e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        /*DataService.getService().register(hashMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult<UserLoginEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<UserLoginEntity> baseResult) {

                        Log.e("lll", baseResult.toString());

                        if (baseResult != null && baseResult.getResult_code() == 0) {
                            AdiUtils.showToast("注册成功");
                            App.getSp().putString("userId",baseResult.getData().getUserId());
                            App.getSp().putString("ticket",baseResult.getData().getTicket());
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            AdiUtils.showToast(baseResult.getResult_msg());
                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.d("111", e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });*/
    }

    private void initSetPwd(String msg) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("password", msg);
        hashMap.put("userId", DeeSpUtil.getInstance().getString("userId"));
        hashMap.put("ticket", DeeSpUtil.getInstance().getString("ticket"));
      /*  hashMap.put("app_id", AppContant.APPID);
        hashMap.put("nonce", "1");
        String sign = ParamsUtils.getSign(hashMap);
        try {

            hashMap.put("sign", SHA1Utils.strToSHA1(sign));

        } catch (Exception e) {
            e.printStackTrace();
        }*/

        DataService.getLoginService().initPassword(hashMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult baseResult) {
                        Log.e("Amout", baseResult.toString());
                        if (baseResult.getResult_code() == 0) {
                            AdiUtils.showToast("注册成功");
                            loadRootFragment(R.id.frag, SetUserProfirtFragment.getInstance());
                        } else{
                            AdiUtils.showToast(baseResult.getResult_msg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("Amout", e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode== PictureConfig.CHOOSE_REQUEST){
            switch (resultCode) {
                case Activity.RESULT_OK:
                    Log.d("aaa","开始发送"+data.toString());
                    if (data!=null){
                        Log.d("aaa","开始发送");
                        EventBus.getDefault().post(new EventMessage(EventMessage.EVENT_FEEDBACK,data));
                    }
                    break;
            }

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.back:
                finish();
                break;
            case R.id.getVerify:
                if (AdiUtils.isPhone(mEtPhone.getText().toString())) {
                    getVerify();
                }

                break;
            case R.id.register:

                if (mEtPwd.getText().toString().equals(mEtConfimPwd.getText().toString())) {
                    if (AdiUtils.isPhone(mEtPhone.getText().toString())) {
                        if (AdiUtils.isPassword(mEtPwd.getText().toString())) {
                            if (AdiUtils.isVerify(mEtVerify.getText().toString())) {
                                if (mCb.isChecked()){
                                    initRegister(mEtPhone.getText().toString(),
                                            mEtVerify.getText().toString(), mEtPwd.getText().toString());
                                }else{
                                    AdiUtils.showToast("请先阅读并勾选一起饭隐私协议");
                                }

                            }
                        }
                    }
                } else {
                    AdiUtils.showToast("两次密码输入不一致");
                }

                break;
            case R.id.go_to_login:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
        }
    }
}
