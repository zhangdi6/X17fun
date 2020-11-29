package com.x.x17fun.ui.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.baidu.idl.face.platform.FaceConfig;
import com.baidu.idl.face.platform.FaceEnvironment;
import com.baidu.idl.face.platform.FaceSDKManager;
import com.x.x17fun.R;
import com.x.x17fun.base.App;
import com.x.x17fun.base.AppContant;
import com.x.x17fun.base.BaseActivity;
import com.x.x17fun.custom.statubar.StatusBarCompat;
import com.x.x17fun.ui.fragment.UserVerifyFragment;

public class UserVerifyActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        StatusBarCompat.changeToLightStatusBar(this);
        setContentView(R.layout.activity_user_verify);

        initView();
    }

    @Override
    protected void initView() {

        loadRootFragment(R.id.frag, UserVerifyFragment.getInstance());

    }


    @Override
    protected void initData() {

    }

    @Override
    protected void initLogic() {

    }
}
