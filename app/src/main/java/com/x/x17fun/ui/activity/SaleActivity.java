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
import com.x.x17fun.ui.fragment.BuyInProductFragment;
import com.x.x17fun.ui.fragment.SaleFragment;
import com.x.x17fun.ui.fragment.SaleOrderManageFragment;
import com.x.x17fun.ui.fragment.SaleProductManageFragment;

import org.greenrobot.eventbus.EventBus;

public class SaleActivity extends BaseActivity {

    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        StatusBarCompat.changeToLightStatusBar(this);
        setContentView(R.layout.activity_sale);
         type = getIntent().getStringExtra("type");
        initView();
    }

    @Override
    protected void initView() {
        if ("2".equals(type)){
            loadRootFragment(R.id.frag, SaleProductManageFragment.getInstance());
        }else if ("1".equals(type)){
            loadRootFragment(R.id.frag, SaleOrderManageFragment.getInstance());
        }else{
            loadRootFragment(R.id.frag, BuyInProductFragment.getInstance());
        }

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initLogic() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1101) {
            if (resultCode == Activity.RESULT_OK) {
                EventBus.getDefault().post(new EventMessage(EventMessage.EVENT_QRCODE, data.getStringExtra("result")));
            }
        } else if (requestCode == PictureConfig.CHOOSE_REQUEST) {
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
