package com.x.x17fun.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.x.x17fun.R;
import com.x.x17fun.base.BaseFragment;
import com.x.x17fun.utils.AdiUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends BaseFragment {

    private View view;
    private ImageView mBack;
    private TextView mIdAndSalf;
    private TextView mPayManage;
    private TextView loginout;

    public static SettingFragment getInstance() {
        return new SettingFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_setting, container, false);
        initView(inflate);
        return inflate;
    }

    private void initView(View inflate) {

        mBack = (ImageView) inflate.findViewById(R.id.back);
        mBack.setOnClickListener(v -> getActivity().finish());
        mIdAndSalf = (TextView) inflate.findViewById(R.id.id_and_salf);
        loginout = (TextView) inflate.findViewById(R.id.loginout);
        mIdAndSalf.setOnClickListener(v -> start(IdSalfFragment.getInstance()));
        mPayManage = (TextView) inflate.findViewById(R.id.pay_manage);
        mPayManage.setOnClickListener(v -> start(PayManageFragment.getInstance()));
        loginout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdiUtils.loginOutWithoutMsg();
            }
        });
    }
}
