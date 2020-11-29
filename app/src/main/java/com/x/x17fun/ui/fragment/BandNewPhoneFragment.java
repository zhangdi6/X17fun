package com.x.x17fun.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.x.x17fun.R;
import com.x.x17fun.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class BandNewPhoneFragment extends BaseFragment implements View.OnClickListener {

    private View view;
    private ImageView mBack;
    /**
     * 请输入新手机号
     */
    private EditText mEtPhone;
    /**
     * 请输入验证码
     */
    private EditText mEtVerify;
    /**
     * 获取验证码
     */
    private TextView mGetVerify;
    /**
     * 完成
     */
    private TextView mUpdate;

    public static BandNewPhoneFragment getInstance() {
        return new BandNewPhoneFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_band_new_phone, container, false);
        initView(inflate);
        return inflate;
    }

    private void initView(View inflate) {

        mBack = (ImageView) inflate.findViewById(R.id.back);
        mBack.setOnClickListener(this);
        mEtPhone = (EditText) inflate.findViewById(R.id.et_phone);
        mEtVerify = (EditText) inflate.findViewById(R.id.et_verify);
        mGetVerify = (TextView) inflate.findViewById(R.id.get_verify);
        mGetVerify.setOnClickListener(this);
        mUpdate = (TextView) inflate.findViewById(R.id.update);
        mUpdate.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.back:
                pop();
                break;
            case R.id.get_verify:
                break;
            case R.id.update:
                pop();
                break;
        }
    }
}
