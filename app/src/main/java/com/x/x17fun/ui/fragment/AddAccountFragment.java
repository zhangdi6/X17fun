package com.x.x17fun.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.x.x17fun.R;
import com.x.x17fun.base.BaseFragment;
import com.x.x17fun.data.DataService;
import com.x.x17fun.entity.BaseResult;
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
public class AddAccountFragment extends BaseFragment implements View.OnClickListener {

    private View view;
    private ImageView mBack;
    private RadioGroup mAccountRadio;
    /**
     * 请填写开户行
     */
    private EditText mEtMenpai;
    private RelativeLayout mLayout;
    /**
     * 请填写账户名称
     */
    private EditText mEtName;
    /**
     * 请填写账户号码
     */
    private EditText mEtPhone;
    /**
     * 保存
     */
    private TextView mSave;

    public static AddAccountFragment getInstance() {
        return new AddAccountFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_add_account, container, false);
        initView(inflate);
        return inflate;
    }

    private void initView(View inflate) {


        mBack = (ImageView) inflate.findViewById(R.id.back);
        mBack.setOnClickListener(this);
        mAccountRadio = (RadioGroup) inflate.findViewById(R.id.account_radio);
        mEtMenpai = (EditText) inflate.findViewById(R.id.et_menpai);
        mLayout = (RelativeLayout) inflate.findViewById(R.id.layout);
        mEtName = (EditText) inflate.findViewById(R.id.et_name);
        mEtPhone = (EditText) inflate.findViewById(R.id.et_phone);
        mSave = (TextView) inflate.findViewById(R.id.save);
        mSave.setOnClickListener(this);
        mAccountRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.btn3){
                    mLayout.setVisibility(View.VISIBLE);
                }else{
                    mLayout.setVisibility(View.GONE);
                }
            }
        });
        mAccountRadio.check(R.id.btn1);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.back:
                pop();
                break;
            case R.id.save:
                switch (mAccountRadio.getCheckedRadioButtonId()){
                    case R.id.btn1:
                        if (AdiUtils.isNoNull(mEtName)){
                            if (AdiUtils.isNoNull(mEtPhone)){
                                addWechat();
                            }
                        }
                        break;
                    case R.id.btn2:
                        if (AdiUtils.isNoNull(mEtName)){
                            if (AdiUtils.isNoNull(mEtPhone)){
                                addAli();
                            }
                        }
                        break;
                    case R.id.btn3:
                        if (AdiUtils.isNoNull(mEtName)){
                            if (AdiUtils.isNoNull(mEtPhone)){
                                if (AdiUtils.isNoNull(mEtMenpai)){
                                    addBank();
                                }
                            }
                        }

                        break;
                }
                break;
        }
    }

    private void addBank() {
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("userId", DeeSpUtil.getInstance().getString("userId"));
        hashMap.put("ticket",DeeSpUtil.getInstance().getString("ticket"));
        hashMap.put("outerAccountType","3");
        hashMap.put("accountName",mEtName.getText().toString().trim());
        hashMap.put("accountNo",mEtPhone.getText().toString().trim());
        hashMap.put("bank",mEtMenpai.getText().toString().trim());

        DataService.getHomeService().addAccout(hashMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult baseResult) {
                        if (baseResult.getResult_code() == 0) {
                            Log.e("zan",baseResult.toString());
                            showToast("添加成功");
                            pop();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        showToast(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void addAli() {
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("userId", DeeSpUtil.getInstance().getString("userId"));
        hashMap.put("ticket",DeeSpUtil.getInstance().getString("ticket"));
        hashMap.put("outerAccountType","2");
        hashMap.put("accountName",mEtName.getText().toString().trim());
        hashMap.put("accountNo",mEtPhone.getText().toString().trim());
        hashMap.put("bank","");

        DataService.getHomeService().addAccout(hashMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult baseResult) {
                        if (baseResult.getResult_code() == 0) {
                            Log.e("zan",baseResult.toString());
                            showToast("添加成功");
                            pop();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        showToast(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void addWechat() {

        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("userId", DeeSpUtil.getInstance().getString("userId"));
        hashMap.put("ticket",DeeSpUtil.getInstance().getString("ticket"));
        hashMap.put("outerAccountType","1");
        hashMap.put("accountName",mEtName.getText().toString().trim());
        hashMap.put("accountNo",mEtPhone.getText().toString().trim());
        hashMap.put("bank","");

        DataService.getHomeService().addAccout(hashMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult baseResult) {
                        if (baseResult.getResult_code() == 0) {
                            Log.e("zan",baseResult.toString());
                            showToast("添加成功");
                            pop();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        showToast(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
