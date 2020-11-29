package com.x.x17fun.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.luck.picture.lib.config.PictureConfig;
import com.x.x17fun.R;
import com.x.x17fun.base.BaseActivity;
import com.x.x17fun.base.EventMessage;
import com.x.x17fun.custom.statubar.StatusBarCompat;
import com.x.x17fun.ui.fragment.AmoutAndVerifyLoginFragment;

import org.greenrobot.eventbus.EventBus;

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        StatusBarCompat.changeToLightStatusBar(this);
        setContentView(R.layout.activity_login);
        initView();
    }

    @Override
    protected void initView() {

        loadRootFragment(R.id.frag, AmoutAndVerifyLoginFragment.newInstance());
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initLogic() {

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
}
