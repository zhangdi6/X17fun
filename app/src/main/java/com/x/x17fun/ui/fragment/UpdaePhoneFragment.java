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
import com.x.x17fun.utils.AdiUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdaePhoneFragment extends BaseFragment implements View.OnClickListener {

    private View view;
    private ImageView mBack;
    /**
     * 176****1412
     */
    private TextView mPhone;
    /**
     * 更换手机号
     */
    private TextView mUpdate;

    public static UpdaePhoneFragment getInstance() {
        return new UpdaePhoneFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_updae_phone, container, false);
        initView(inflate);
        return inflate;
    }

    private void initView(View inflate) {

        mBack = (ImageView) inflate.findViewById(R.id.back);
        mBack.setOnClickListener(this);
        mPhone = (TextView) inflate.findViewById(R.id.phone);
        mUpdate = (TextView) inflate.findViewById(R.id.update);
        mUpdate.setOnClickListener(this);

        UserSyncEntity userSyncEntity = App.userMsg();
        if (userSyncEntity!=null && userSyncEntity.getUserInfo()
                !=null && userSyncEntity.getUserInfo().getPhone()
                !=null && !userSyncEntity.getUserInfo().getPhone().equals("")){
            mPhone.setText(AdiUtils.secretPhone(userSyncEntity.getUserInfo().getPhone()));
        }else{
            mPhone.setText("暂无");
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
            case R.id.update:
                start(BandNewPhoneFragment.getInstance());
                break;
        }
    }
}
