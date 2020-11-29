package com.x.x17fun.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.idl.face.platform.FaceConfig;
import com.baidu.idl.face.platform.FaceEnvironment;
import com.baidu.idl.face.platform.FaceSDKManager;
import com.x.x17fun.R;
import com.x.x17fun.base.App;
import com.x.x17fun.base.AppContant;
import com.x.x17fun.base.BaseFragment;
import com.x.x17fun.ui.activity.FaceDetectExpActivity;
import com.x.x17fun.ui.activity.FaceLivenessExpActivity;
import com.x.x17fun.utils.IDCardUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserVerifyFragment extends BaseFragment implements View.OnClickListener {

    private View view;
    private ImageView mBack;
    /**
     * 请输入姓名
     */
    private EditText mEtName;
    /**
     * 请输入身份证号
     */
    private EditText mEtIdcard;
    /**
     * 下一步
     */
    private TextView mNext;

    public static UserVerifyFragment getInstance() {
        return new UserVerifyFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_user_verify, container, false);
        FaceSDKManager.getInstance().initialize(getContext(), AppContant.licenseID, AppContant.licenseFileName);
        initView(inflate);
        return inflate;
    }

    private void initView(View inflate) {

        mBack = (ImageView) inflate.findViewById(R.id.back);
        mBack.setOnClickListener(this);
        mEtName = (EditText) inflate.findViewById(R.id.et_name);
        mEtIdcard = (EditText) inflate.findViewById(R.id.et_idcard);
        mNext = (TextView) inflate.findViewById(R.id.next);
        mNext.setOnClickListener(this);

        setFaceConfig();
    }
    private void setFaceConfig() {
        FaceConfig config = FaceSDKManager.getInstance().getFaceConfig();
        // SDK初始化已经设置完默认参数（推荐参数），您也根据实际需求进行数值调整
        config.setLivenessTypeList(App.livenessList);
        config.setLivenessRandom(App.isLivenessRandom);
        config.setBlurnessValue(FaceEnvironment.VALUE_BLURNESS);
        config.setBrightnessValue(FaceEnvironment.VALUE_BRIGHTNESS);
        config.setCropFaceValue(FaceEnvironment.VALUE_CROP_FACE_SIZE);
        config.setHeadPitchValue(FaceEnvironment.VALUE_HEAD_PITCH);
        config.setHeadRollValue(FaceEnvironment.VALUE_HEAD_ROLL);
        config.setHeadYawValue(FaceEnvironment.VALUE_HEAD_YAW);
        config.setMinFaceSize(FaceEnvironment.VALUE_MIN_FACE_SIZE);
        config.setNotFaceValue(FaceEnvironment.VALUE_NOT_FACE_THRESHOLD);
        config.setOcclusionValue(FaceEnvironment.VALUE_OCCLUSION);
        config.setCheckFaceQuality(true);
        config.setFaceDecodeNumberOfThreads(2);

        FaceSDKManager.getInstance().setFaceConfig(config);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.back:
                pop();
                break;
            case R.id.next:
                onSubmit();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==200&&resultCode==300){
            getActivity().finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void onSubmit() {
        final String userName = mEtName.getText().toString().trim();
        final String idNo = mEtIdcard.getText().toString().trim();

        if (userName.length() < 2 || userName.length() > 5) {
            showToast("请输入正确的姓名");
            return;
        }
        if (idNo.length() != 18 || !IDCardUtil.isValid(idNo)) {
            showToast("请输入正确的身份证号");
            return;
        }
        Intent intent = new Intent(getContext(), FaceLivenessExpActivity.class);
        intent.putExtra("name",userName);
        intent.putExtra("card",idNo);
        startActivityForResult(intent,200);

    }
}
