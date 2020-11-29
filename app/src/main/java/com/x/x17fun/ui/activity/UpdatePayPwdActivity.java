package com.x.x17fun.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


import com.x.x17fun.R;
import com.x.x17fun.base.App;
import com.x.x17fun.base.BaseActivity;
import com.x.x17fun.base.BaseActivity2;
import com.x.x17fun.base.BaseEventMsg;
import com.x.x17fun.custom.statubar.StatusBarCompat;
import com.x.x17fun.entity.UserSyncEntity;
import com.x.x17fun.ui.fragment.UpdatePayPwdFragment1;
import com.x.x17fun.ui.fragment.UpdatePayPwdFragment2;
import com.x.x17fun.ui.fragment.UpdatePayPwdFragment3;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class UpdatePayPwdActivity extends BaseActivity2 {

    private FragmentManager fragmentManager;
    private TextView mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        StatusBarCompat.changeToLightStatusBar(this);
        setContentView(R.layout.activity_update_pay_pwd);
        EventBus.getDefault().register(this);
         fragmentManager = getSupportFragmentManager();
        initView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseEventMsg eventMsg){

        if (eventMsg.getType()!=null && eventMsg.getType().equals("pay")){
            if (eventMsg.getMsg()!=null && eventMsg.getMsg().equals("1")){
                Bundle bundle = new Bundle();
                bundle.putString("verify",eventMsg.getParamStr2());
                addFragment(fragmentManager, UpdatePayPwdFragment2.class,R.id.frag,bundle);
            }else if (eventMsg.getMsg()!=null && eventMsg.getMsg().equals("2")){
                Bundle bundle = new Bundle();
                bundle.putString("verify",eventMsg.getParamStr2());
                bundle.putString("vCode",eventMsg.getParamStr());
                addFragment(fragmentManager, UpdatePayPwdFragment3.class,R.id.frag,bundle);
            }else if (eventMsg.getMsg()!=null && eventMsg.getMsg().equals("3")){
                Bundle bundle = new Bundle();
                bundle.putString("verify",eventMsg.getParamStr2());
                addFragment(fragmentManager, UpdatePayPwdFragment2.class,R.id.frag,bundle);
            }
        }

    }



    private void initView() {
        ImageView mBack = (ImageView) findViewById(R.id.back);
        mTitle =  findViewById(R.id.title);
        mBack.setOnClickListener(v -> onBackPressed());
        FrameLayout mFrag = (FrameLayout) findViewById(R.id.frag);
        addFragment(fragmentManager, UpdatePayPwdFragment1.class,R.id.frag,null);

        UserSyncEntity userMsg = App.userMsg();
        if (userMsg.getUserInfo().getPayPassword().equals("true")){
            mTitle.setText("修改支付密码");
        }else{
            mTitle.setText("设置支付密码");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
