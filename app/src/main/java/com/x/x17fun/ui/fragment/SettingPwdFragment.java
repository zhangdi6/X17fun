package com.x.x17fun.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.x.x17fun.R;
import com.x.x17fun.base.BaseFragment;
import com.x.x17fun.data.DataService;
import com.x.x17fun.entity.BaseResult;
import com.x.x17fun.entity.UserLoginEntity;
import com.x.x17fun.ui.activity.MainActivity;
import com.x.x17fun.utils.AdiUtils;
import com.x.x17fun.utils.DeeSpUtil;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingPwdFragment extends BaseFragment implements View.OnClickListener {

    private View view;
    private ImageView mBack;
    /**
     * 6~16位含数字、字母
     */
    private EditText mEtPwd;
    /**
     * 6~16位含数字、字母
     */
    private EditText mEtConfimPwd;
    private CheckBox mCb;
    /**
     * 《一起饭隐私协议》
     */
    private TextView mTvAgreeement;
    /**
     * 完成
     */
    private TextView mRegister;

    public static SettingPwdFragment getInstance() {
        return new SettingPwdFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_setting_pwd, container, false);
        initView(inflate);
        return inflate;
    }

    private void initView(View inflate) {

        mBack = (ImageView) inflate.findViewById(R.id.back);
        mBack.setOnClickListener(this);
        mEtPwd = (EditText) inflate.findViewById(R.id.et_pwd);
        mEtConfimPwd = (EditText) inflate.findViewById(R.id.et_confim_pwd);
        mCb = (CheckBox) inflate.findViewById(R.id.cb);
        mTvAgreeement = (TextView) inflate.findViewById(R.id.tv_agreeement);
        mTvAgreeement.setOnClickListener(this);
        mRegister = (TextView) inflate.findViewById(R.id.register);
        mRegister.setOnClickListener(this);


        AdiUtils.showDeleteButton(mEtPwd, 2, null);
        AdiUtils.showDeleteButton(mEtConfimPwd, 2, null);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.tv_agreeement:
                break;
            case R.id.register:
                onSubmit();
                break;
            case R.id.back:
                pop();
                break;
        }
    }

    private void onSubmit() {
         String msg = mEtPwd.getText().toString();
         String msg2 = mEtConfimPwd.getText().toString();
        if (msg.equals(msg2)) {
                if (AdiUtils.isPassword(msg)) {
                        if (mCb.isChecked()){
                            initSetPwd(msg);
                        }else{
                            AdiUtils.showToast("请先阅读并勾选《一起饭隐私协议》");
                        }
                }else{
                }
        } else {
           showToast("两次密码输入不一致");
        }

    }

    private void initSetPwd(String msg) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("password", msg);
        hashMap.put("userId", DeeSpUtil.getInstance().getString("userId"));
        hashMap.put("ticket", DeeSpUtil.getInstance().getString("ticket"));
      /*  hashMap.put("app_id", AppContant.APPID);
        hashMap.put("nonce", "1");
        String sign = ParamsUtils.getSign(hashMap);
        try {

            hashMap.put("sign", SHA1Utils.strToSHA1(sign));

        } catch (Exception e) {
            e.printStackTrace();
        }*/

        DataService.getLoginService().initPassword(hashMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult baseResult) {
                        Log.e("Amout", baseResult.toString());
                        if (baseResult.getResult_code() == 0) {
                            start(SetUserProfirtFragment.getInstance());
                        } else{
                            AdiUtils.showToast(baseResult.getResult_msg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("Amout", e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
