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
import com.x.x17fun.ui.activity.UpdateLoginPwdActivity;
import com.x.x17fun.utils.AdiUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class IdSalfFragment extends BaseFragment implements View.OnClickListener {

    private View view;
    private ImageView mBack;
    /**
     * 176****1412
     */
    private TextView mPhone;
    private TextView mPassword;
    private TextView pwd;

    public static IdSalfFragment getInstance() {
        return new IdSalfFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_id_salf, container, false);
        initView(inflate);
        return inflate;
    }

    private void initView(View inflate) {
        mBack = (ImageView) inflate.findViewById(R.id.back);
        pwd = (TextView) inflate.findViewById(R.id.pwd);
        mBack.setOnClickListener(this);
        mPhone = (TextView) inflate.findViewById(R.id.phone);
        mPhone.setOnClickListener(this);
        mPassword = (TextView) inflate.findViewById(R.id.password);
        mPassword.setOnClickListener(this);

        UserSyncEntity userSyncEntity = App.userMsg();
        if (userSyncEntity!=null && userSyncEntity.getUserInfo()
                !=null && userSyncEntity.getUserInfo().getPhone()
                !=null && !userSyncEntity.getUserInfo().getPhone().equals("")){
            mPhone.setText(AdiUtils.secretPhone(userSyncEntity.getUserInfo().getPhone()));
        }else{
            mPhone.setText("暂无");
        }

        if (userSyncEntity.getUserInfo().getPassword().equals("true")){
            pwd.setText("修改登录密码");
        }else{
            pwd.setText("设置登录密码");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.back:
                pop();
                break;
            case R.id.phone:
                /*start(UpdaePhoneFragment.getInstance());*/
                break;
            case R.id.password:
                startToActivity(getContext(),UpdateLoginPwdActivity.class);
                break;
        }
    }
}
