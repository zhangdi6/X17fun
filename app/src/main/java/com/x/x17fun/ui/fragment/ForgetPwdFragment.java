package com.x.x17fun.ui.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.alipay.tscenter.biz.rpc.vkeydfp.result.BaseResult;
import com.x.x17fun.R;
import com.x.x17fun.base.AppContant;
import com.x.x17fun.base.BaseFragment;
import com.x.x17fun.custom.statubar.StatusBarCompat;
import com.x.x17fun.ui.activity.RegisterActivity;
import com.x.x17fun.utils.AdiUtils;
import com.x.x17fun.utils.ParamsUtils;
import com.x.x17fun.utils.ResUtils;
import com.x.x17fun.utils.SHA1Utils;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForgetPwdFragment extends BaseFragment implements View.OnClickListener {


    private TextView title_right;
    private TextView btn_next;
    private EditText confimPwd;
    private EditText mPwd;
    private EditText mEtphone;
    private EditText mEtVerify;
    private View line_left;
    private TextView tv_left;
    private TextView tv_right;
    private LinearLayout mRightLayout;
    private LinearLayout mLeftLayout;
    private TextView getVerify;
    private TextView title_left;
    private View line_right;

    public static ForgetPwdFragment getInstance() {
        return new ForgetPwdFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        StatusBarCompat.setStatusBarColor(getActivity(), ResUtils.getColor(getActivity(), R.color.blue_nomal));
        StatusBarCompat.cancelLightStatusBar(getActivity());
        View inflate = inflater.inflate(R.layout.fragment_forget_pwd, container, false);

        initView(inflate);

        return inflate;
    }


    private void initView(View inflate) {

        ImageView back = inflate.findViewById(R.id.back);
         back.setOnClickListener(this);
         title_left = inflate.findViewById(R.id.left_title);
         tv_left = (TextView)inflate.findViewById(R.id.left_tv);
         line_left = (View)inflate.findViewById(R.id.line_left);
         title_right = inflate.findViewById(R.id.right_title);
         tv_right = (TextView)inflate.findViewById(R.id.right_tv);
         line_right = (View)inflate.findViewById(R.id.line_right);
         mRightLayout = (LinearLayout) inflate.findViewById(R.id.phone_layout);
         mLeftLayout = (LinearLayout)inflate.findViewById(R.id.verify_layout);
         mEtVerify = (EditText)inflate.findViewById(R.id.et_verify);
         mEtphone = (EditText)inflate.findViewById(R.id.et_phone2);
         getVerify = (TextView)inflate.findViewById(R.id.getVerify);
         mPwd = (EditText)inflate.findViewById(R.id.et_phone);
         confimPwd = (EditText)inflate.findViewById(R.id.et_pwd);
         btn_next = (TextView)inflate.findViewById(R.id.btn_next);
        TextView call = (TextView) inflate.findViewById(R.id.call);
         call.setOnClickListener(this);
        TextView go_to_register = (TextView) inflate.findViewById(R.id.go_to_register);
         go_to_register.setOnClickListener(this);
         AdiUtils.showDeleteButton(mEtphone,1,null);
         AdiUtils.showDeleteButton(mEtVerify,3,null);
         AdiUtils.showDeleteButton(mPwd,2,null);
         AdiUtils.showDeleteButton(confimPwd,2,null);

        getVerify.setOnClickListener(v -> {
            if (AdiUtils.isPhone(mEtphone.getText().toString())){
                getVerify();

            }
        });
         changeToLeft();
    }

    private void time() {
        CountDownTimer countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                getVerify.setText("重新发送("+millisUntilFinished/1000+"秒)");
                getVerify.setEnabled(false);
            }

            @Override
            public void onFinish() {
                getVerify.setText("重新发送");
                getVerify.setEnabled(true);
            }
        };
        countDownTimer.start();
    }

    private void getVerify() {
       /* initVerify("forget", mEtphone.getText().toString(), new IBaseCallBack<String>() {
            @Override
            public void onSuccess(String data) {
                time();
                AdiUtils.showToast(data);
            }

            @Override
            public void onFail(String msg) {
                AdiUtils.showToast(msg);
            }
        });*/
    }

    //默认左边tab
    private void changeToLeft(){
        mLeftLayout.setVisibility(View.VISIBLE);
        mRightLayout.setVisibility(View.GONE);

        title_left.setTextColor(Color.parseColor("#333333"));
        title_right.setTextColor(Color.parseColor("#FDAE86"));
        btn_next.setEnabled(false);
        btn_next.setText("下一步");
        btn_next.setBackgroundResource(R.drawable.text_bg_gray_30);
        btn_next.setTextColor(Color.WHITE);
        mEtphone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (AdiUtils.isPhoneNoToast(mEtphone.getText().toString())&&AdiUtils.isVerifyNoToast(mEtVerify.getText().toString())){
                    btn_next.setEnabled(true);
                    btn_next.setText("下一步");
                    btn_next.setBackgroundResource(R.drawable.text_bg_yellow);
                    btn_next.setTextColor(Color.WHITE);
                    btn_next.setOnClickListener(v -> changeToRight());
                }else{
                    btn_next.setEnabled(false);
                    btn_next.setText("下一步");
                    btn_next.setBackgroundResource(R.drawable.text_bg_gray_30);
                    btn_next.setTextColor(Color.WHITE);
                }
            }
        });
        mEtVerify.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (AdiUtils.isPhoneNoToast(mEtphone.getText().toString())&&AdiUtils.isVerifyNoToast(mEtVerify.getText().toString())){
                    btn_next.setEnabled(true);
                    btn_next.setText("下一步");
                    btn_next.setBackgroundResource(R.drawable.text_bg_yellow);
                    btn_next.setTextColor(Color.WHITE);
                    btn_next.setOnClickListener(v -> changeToRight());
                }else{
                    btn_next.setEnabled(false);
                    btn_next.setText("下一步");
                    btn_next.setBackgroundResource(R.drawable.text_bg_gray_30);
                    btn_next.setTextColor(Color.WHITE);
                }
            }
        });
    }

    //选择右边tab
    private void changeToRight() {
        mRightLayout.setVisibility(View.VISIBLE);
        mLeftLayout.setVisibility(View.GONE);
        title_right.setTextColor(Color.parseColor("#333333"));
        title_left.setTextColor(Color.parseColor("#FDAE86"));
        btn_next.setEnabled(true);
        btn_next.setText("完成");
        btn_next.setBackgroundResource(R.drawable.text_bg_yellow);
        btn_next.setOnClickListener(v -> {
            if (AdiUtils.isPassword(mPwd.getText().toString())&&AdiUtils.isPassword(confimPwd.getText().toString())){
                if (mPwd.getText().toString().equals(confimPwd.getText().toString())){
                    checkUser(mEtphone.getText().toString(),mEtVerify.getText().toString(),mPwd.getText().toString());
                }else{
                    AdiUtils.showToast("两次密码输入不一致");
                }
            }else{
                AdiUtils.showToast("请输入正确的密码");
            }
        });

    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        StatusBarCompat.setStatusBarColor(getActivity(), Color.WHITE);
        StatusBarCompat.changeToLightStatusBar(getActivity());
    }

    //重设密码
    private void checkUser(String phone, String verify , String password) {

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("phone", phone);
        hashMap.put("verificationCode", verify);
        hashMap.put("password", password);
        hashMap.put("app_id", AppContant.APPID);
        hashMap.put("nonce", "1");
        String sign = ParamsUtils.getSign(hashMap);
        try {
            hashMap.put("sign", SHA1Utils.strToSHA1(sign));

        } catch (Exception e) {
            e.printStackTrace();
        }

      /*  DataService.getService().resetLoginPwd(hashMap)
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
                            AdiUtils.showToast("修改成功");
                            getActivity().onBackPressed();
                        } else {
                           AdiUtils.showToast(baseResult.getResult_msg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        AdiUtils.showToast(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });*/
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                pop();
                break;
                //致电客服
            case R.id.call:


                break;
            case R.id.go_to_register:
                Intent intent = new Intent(getActivity(), RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }
}
