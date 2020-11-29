package com.x.x17fun.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.luck.picture.lib.config.PictureConfig;
import com.x.x17fun.R;
import com.x.x17fun.base.BaseActivity;
import com.x.x17fun.base.EventMessage;
import com.x.x17fun.custom.statubar.StatusBarCompat;
import com.x.x17fun.ui.fragment.ChatFragment;

import org.greenrobot.eventbus.EventBus;

public class ChatActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chat);
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        StatusBarCompat.changeToLightStatusBar(this);

        String providerId = getIntent().getStringExtra("providerId");
        String name = getIntent().getStringExtra("name");
        loadRootFragment(R.id.frag, ChatFragment.newInstance(providerId,name));
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initLogic() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PictureConfig.CHOOSE_REQUEST) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    if (data != null) {
                        EventBus.getDefault().post(new EventMessage(EventMessage.EVENT_FEEDBACK, data));
                    }
                    break;
            }

        }
    }
}
