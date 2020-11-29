package com.x.x17fun.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


import com.x.x17fun.R;
import com.x.x17fun.base.App;
import com.x.x17fun.base.BaseEventMsg;
import com.x.x17fun.base.BaseFragment2;
import com.x.x17fun.base.IBaseCallBack;
import com.x.x17fun.utils.AdiUtils;
import com.x.x17fun.utils.RegexUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdatePayPwdFragment1 extends BaseFragment2 {


    private TextView tv_phone;
    private TextView btn_getverify;
    private EditText et_verify;

    public UpdatePayPwdFragment1() {
        // Required empty public constructor
    }
    //还剩多长时间可以重新发送验证码
    private long mLastResendSecond;
    private Timer mResendTimer;
    @Override
    protected boolean isNeedToAddBackStack() {
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View inflate = inflater.inflate(getLayoutId(), container, false);

        initView(inflate);

        return inflate;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_update_pay_pwd_fragment1;
    }

    private void initView(View inflate) {

        tv_phone = inflate.findViewById(R.id.tv_phone);
        TextView btn_next = inflate.findViewById(R.id.btn_next);
        btn_getverify = inflate.findViewById(R.id.btn_getverify);
        et_verify = inflate.findViewById(R.id.et_verify);
        btn_getverify.setOnClickListener(v -> getVerify());
        btn_next.setOnClickListener(v -> {
            if (et_verify.getText().toString().equals("")){
                AdiUtils.showToast("验证码不能为空");
            }else{
                if (et_verify.getText().toString().length()!=4){
                    AdiUtils.showToast("请输入正确的验证码");
                }else{
                    EventBus.getDefault().post(new BaseEventMsg("1","pay","777",et_verify.getText().toString().trim()));
                }
            }
        });
        String login_phone = (String) App.getCache("pay_phone");
        btn_getverify.setText(login_phone==null||login_phone.equals("")?"获取验证码":(String)App.getCache("pay_phone"));
        //处理重新发送时间
        Object lastSend= App.getCache("pay_code_timestamp");
        if (lastSend==null){
            mLastResendSecond = 0;
        }else {
            mLastResendSecond = 60 - (System.currentTimeMillis() - Long.parseLong((String) lastSend))/1000;
            if (mLastResendSecond<=0){
                App.removeCache("pay_code_timestamp");
                mLastResendSecond = 0;
            }
        }
        if (mLastResendSecond == 0){
            btn_getverify.setEnabled(true);
            btn_getverify.setText("获取验证码");
            tv_phone.setText("验证码即将发送至：" + RegexUtils.hidePhone(App.userMsg().getUserInfo().getPhone()) + " ,请注意查收!");
        }else {
            setupResendTimer();
        }
    }

    @Override
    protected void onActivityBackPress() {
        if (mResendTimer!=null){
            mResendTimer.cancel();
            mResendTimer=null;
        }
        super.onActivityBackPress();
    }

    private void getVerify() {
        if (mResendTimer!=null){
            return;
        }

        initVerify("modify", App.userMsg().getUserInfo().getPhone(), new IBaseCallBack<String>() {
            @Override
            public void onSuccess(String data) {
                mLastResendSecond=60;
                App.setCache("pay_phone",App.userMsg().getUserInfo().getPhone());
                App.setCache("pay_code_timestamp",String.valueOf(System.currentTimeMillis()));
                tv_phone.setText("验证码已发送至："+  RegexUtils.hidePhone(App.userMsg().getUserInfo().getPhone()));
                setupResendTimer();
            }

            @Override
            public void onFail(String msg) {
                AdiUtils.showToast(msg);
            }
        });
    }

    private void setupResendTimer(){
        btn_getverify.setEnabled(false);
        btn_getverify.setText(mLastResendSecond+"S");
        //启动定时器
        mResendTimer = new Timer();
        mResendTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                mLastResendSecond -- ;
                if (mLastResendSecond <=0){
                    mResendTimer.cancel();
                    mResendTimer=null;
                    if (getActivity()!=null){
                        getActivity().runOnUiThread(()->{
                            btn_getverify.setEnabled(true);
                            btn_getverify.setText("获取验证码");
                            tv_phone.setText("验证码即将发送至：" +  RegexUtils.hidePhone(App.userMsg().getUserInfo().getPhone()) + " ,请注意查收!");
                        });
                    }
                }else {
                    if (getActivity()!=null){
                        getActivity().runOnUiThread(()->{
                            btn_getverify.setText(mLastResendSecond+"S");
                            tv_phone.setText("验证码已发送至：" +  RegexUtils.hidePhone(App.userMsg().getUserInfo().getPhone()));
                        });
                    }
                }
            }
        },1000,1000);
    }

}
