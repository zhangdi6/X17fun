package com.x.x17fun.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.x.x17fun.R;
import com.x.x17fun.base.App;
import com.x.x17fun.base.BaseFragment;
import com.x.x17fun.entity.UserSyncEntity;
import com.x.x17fun.ui.activity.UpdatePayPwdActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class PayManageFragment extends BaseFragment implements View.OnClickListener {

    private View view;
    private ImageView mBack;
    private TextView mUpdate;
    private TextView mForget;
    private TextView mtag;

    public static PayManageFragment getInstance() {
        return new PayManageFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_pay_manage, container, false);
        initView(inflate);
        return inflate;
    }

    private void initView(View inflate) {
        mBack = (ImageView) inflate.findViewById(R.id.back);

        mtag = (TextView) inflate.findViewById(R.id.tag);
        mtag.setOnClickListener(this);
        mUpdate = (TextView) inflate.findViewById(R.id.update);
        mUpdate.setOnClickListener(this);
        mForget = (TextView) inflate.findViewById(R.id.forget);
        mForget.setOnClickListener(this);
        mBack.setOnClickListener(this);
        UserSyncEntity userMsg = App.userMsg();
        if (userMsg.getUserInfo().getPayPassword().equals("true")){
            mtag.setText("修改支付密码");
        }else{
            mtag.setText("设置支付密码");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.update:
                startToActivity(getContext(), UpdatePayPwdActivity.class);
                break;
            case R.id.forget:

                break;
            case R.id.back:
                pop();
                break;
        }
    }
}
