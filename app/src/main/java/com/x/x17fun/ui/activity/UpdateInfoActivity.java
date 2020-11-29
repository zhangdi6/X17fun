package com.x.x17fun.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.x.x17fun.R;
import com.x.x17fun.base.App;
import com.x.x17fun.base.BaseActivity;
import com.x.x17fun.base.IBaseCallBack;
import com.x.x17fun.custom.statubar.StatusBarCompat;
import com.x.x17fun.entity.BaseResult;
import com.x.x17fun.utils.AdiUtils;

import java.lang.ref.WeakReference;
import java.util.HashMap;

public class UpdateInfoActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBack;
    /**
     *
     */
    private TextView mTitle;
    /**
     * 完成
     */
    private TextView mOk;
    private EditText mEt;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        StatusBarCompat.changeToLightStatusBar(this);
        setContentView(R.layout.activity_update_info);
        type = getIntent().getStringExtra("type");
        initView();
        initLogic();
    }

    @Override
    protected void initView() {

        mBack = (ImageView) findViewById(R.id.back);
        mBack.setOnClickListener(this);
        mTitle = (TextView) findViewById(R.id.title);
        mOk = (TextView) findViewById(R.id.ok);
        mOk.setOnClickListener(this);
        mEt = (EditText) findViewById(R.id.et);

        mEt.requestFocus();

    }

    @Override
    protected void initData() {

    }


    @Override
    protected void initLogic() {

        switch (type){
            case "name":
                mEt.setHint("请输入昵称");
                mTitle.setText("修改昵称");
                break;
            case "sign":
                mEt.setHint("请输入个性签名");
                mTitle.setText("修改介绍");
                break;
            case "school":
                mEt.setHint("请输入大学名称");
                mTitle.setText("修改大学");
                break;
            case "work":
                mEt.setHint("请输入职业");
                mTitle.setText("修改职业");
                break;
            case "company":
                mEt.setHint("请输入公司名称");
                mTitle.setText("修改公司");
                break;
            case "other":
                mEt.setHint("请输入备注信息");
                mTitle.setText("添加备注");
                break;
        }

    }

    @Override
    public void onBackPressedSupport() {
        Intent intent = new Intent();
        intent.putExtra("name","");
        setResult(6,intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.back:
                onBackPressedSupport();
                break;
            case R.id.ok:
                if (mEt.getText().toString().trim().length()>0){

                    Intent intent = new Intent();
                    intent.putExtra("name",mEt.getText().toString());
                    HashMap<String, Object> objectHashMap = new HashMap<>();
                    switch (type){
                        case "name":
                            objectHashMap.put("nickName",mEt.getText().toString());
                            App.execute(()->{
                                final BaseResult fr = App.webService().updateUserMsg(null,null,objectHashMap);
                                runOnUiThread(()->{
                                    Log.d("tsg",fr.toString());

                                    if (fr.getResult_code()==0){
                                        setResult(2,intent);
                                        showToast("修改成功");
                                        finish();
                                    }else if (fr.getResult_code()== -3){
                                        AdiUtils.loginOut();
                                    }else {
                                        showToast(fr.getResult_msg());
                                    }
                                });
                            });

                            break;
                        case "sign":
                            objectHashMap.put("signature",mEt.getText().toString());
                            App.execute(()->{
                                final BaseResult fr = App.webService().updateUserMsg(null,null,objectHashMap);
                                runOnUiThread(()->{
                                    Log.d("tsg",fr.toString());

                                    if (fr.getResult_code()==0){
                                        setResult(1,intent);
                                        showToast("修改成功");
                                        finish();
                                    }else if (fr.getResult_code()== -3){
                                        AdiUtils.loginOut();
                                    }else {
                                        showToast(fr.getResult_msg());
                                    }
                                });
                            });
                            break;
                        case "school":
                            objectHashMap.put("colleage",mEt.getText().toString());
                            App.execute(()->{
                                final BaseResult fr = App.webService().updateUserMsg(null,null,objectHashMap);
                                runOnUiThread(()->{
                                    Log.d("tsg",fr.toString());

                                    if (fr.getResult_code()==0){
                                        setResult(3,intent);
                                        showToast("修改成功");
                                        finish();
                                    }else if (fr.getResult_code()== -3){
                                        AdiUtils.loginOut();
                                    }else {
                                        showToast(fr.getResult_msg());
                                    }
                                });
                            });
                            break;
                        case "work":
                            objectHashMap.put("profession",mEt.getText().toString());
                            App.execute(()->{
                                final BaseResult fr = App.webService().updateUserMsg(null,null,objectHashMap);
                                runOnUiThread(()->{
                                    Log.d("tsg",fr.toString());

                                    if (fr.getResult_code()==0){
                                        setResult(5,intent);
                                        showToast("修改成功");
                                        finish();
                                    }else if (fr.getResult_code()== -3){
                                        AdiUtils.loginOut();
                                    }else {
                                        showToast(fr.getResult_msg());
                                    }
                                });
                            });
                            break;
                        case "company":
                            objectHashMap.put("company",mEt.getText().toString());
                            App.execute(()->{
                                final BaseResult fr = App.webService().updateUserMsg(null,null,objectHashMap);
                                runOnUiThread(()->{
                                    Log.d("tsg",fr.toString());

                                    if (fr.getResult_code()==0){
                                        setResult(4,intent);
                                        showToast("修改成功");
                                        finish();
                                    }else if (fr.getResult_code()== -3){
                                        AdiUtils.loginOut();
                                    }else {
                                        showToast(fr.getResult_msg());
                                    }
                                });
                            });
                            break;
                        case "other":
                            setResult(500,intent);
                            break;
                    }
                }else{
                    showToast("请输入修改内容");
                }
                break;
        }
    }
}
