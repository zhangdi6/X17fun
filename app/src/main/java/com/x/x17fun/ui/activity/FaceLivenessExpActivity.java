package com.x.x17fun.ui.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;


import com.baidu.idl.face.platform.FaceStatusEnum;
import com.baidu.idl.face.platform.ui.FaceLivenessActivity;
import com.baidu.idl.face.platform.utils.Base64Utils;
import com.baidu.idl.face.platform.utils.BitmapUtils;
import com.x.x17fun.base.App;
import com.x.x17fun.custom.DefaultDialog;
import com.x.x17fun.custom.statubar.StatusBarCompat;
import com.x.x17fun.entity.BaseResult;
import com.x.x17fun.utils.AdiUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import pub.devrel.easypermissions.EasyPermissions;

public class FaceLivenessExpActivity extends FaceLivenessActivity implements EasyPermissions.PermissionCallbacks {

    private DefaultDialog mDefaultDialog;
    String parentPath = "";    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        StatusBarCompat.changeToLightStatusBar(this);
         if (EasyPermissions.hasPermissions(getApplicationContext(),
                    Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE)){

         }else {
                EasyPermissions.requestPermissions(this,"应用需要相机实名认证",101,
                        Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE);
         }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            /*new AppSettingsDialog.Builder(this).build().show();*/
        }
    }
    @Override
    public void onLivenessCompletion(FaceStatusEnum status, String message, HashMap<String, String> base64ImageMap) {
        super.onLivenessCompletion(status, message, base64ImageMap);
        if (status == FaceStatusEnum.OK && mIsCompletion) {

            int a = 0 ;
            Set<Map.Entry<String, String>> sets = base64ImageMap.entrySet();
            Bitmap bmp = null;
            for (Map.Entry<String, String> entry : sets) {
                bmp = base64ToBitmap(entry.getValue());
                Log.e("name","进入for循环");
                if (a == 0) {
                    Log.e("name","进入a判断");

                  /*  if (Environment.getExternalStorageState().equals(Environment.DIRECTORY_PICTURES)) {
                        parentPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();
                    } else {
                        parentPath = Environment.getDownloadCacheDirectory().getAbsolutePath();
                    }*/
                    File file = saveBitmap(bmp, Environment.getExternalStorageDirectory().getAbsolutePath(), "/faceimg.png");

                    Log.e("name","进入faceimg判断");

                    HashMap<Object, Object> hashMap = new HashMap<>();
                    hashMap.put("name",getIntent().getStringExtra("name"));
                    hashMap.put("idCardNo",getIntent().getStringExtra("card"));
                    App.execute(()->{
                        Log.e("name","进入上传");
                        final BaseResult fr=App.webService().updateUserApproval("faceImg",new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/faceimg.png"),hashMap);
                        runOnUiThread(()->{
                            if (fr.getResult_code()==0){
                                App.userMsg().getUserInfo().setUserStatus("1");
                                showMessageDialog("实名认证", "人脸认证成功");

                            }else if (fr.getResult_code() == -3){
                                AdiUtils.loginOut();
                            }else {
                                AdiUtils.showToast(fr.getResult_msg());
                            }
                        });
                    });

                    a++;
                }
            }

        } else if (status == FaceStatusEnum.Error_DetectTimeout ||
                status == FaceStatusEnum.Error_LivenessTimeout ||
                status == FaceStatusEnum.Error_Timeout) {
            showMessageDialog("实名认证", "人脸采集超时");
        }
    }

    private File saveBitmap(Bitmap bitmap,String path, String filename)
    {
        File file = new File(path + filename);
        Log.e("name",file.getAbsolutePath());
        Log.e("name",file.getName());
        file.setReadable(true);
        file.setWritable(true);
        if(!file.exists()){
            try {
                file.createNewFile();
                Log.e("name","创建");
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("name",e.getMessage()+"1");
            }
        }else{
            file.delete();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileOutputStream out;
        try{
            out = new FileOutputStream(file);
            if(bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)) {
                Log.e("name","成功了");
                out.flush();
                out.close();
                return file;
            }else{
                Log.e("name","十倍了");
            }
        }
        catch (FileNotFoundException e)
        {
            Log.e("name",e.getMessage());
            e.printStackTrace();
        }
        catch (IOException e)
        {
            Log.e("name",e.getMessage()+"2");
            e.printStackTrace();
        }
        return file;
    }

    private static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64Utils.decode(base64Data, Base64Utils.NO_WRAP);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
    private void showMessageDialog(String title, String message) {
        if (mDefaultDialog == null) {
            DefaultDialog.Builder builder = new DefaultDialog.Builder(this);
            builder.setTitle(title).
                    setMessage(message).
                    setNegativeButton("确认",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mDefaultDialog.dismiss();
                                    Intent intent = new Intent();
                                    setResult(300,intent);
                                    finish();
                                }
                            });
            mDefaultDialog = builder.create();
            mDefaultDialog.setCancelable(true);
        }
        mDefaultDialog.dismiss();
        mDefaultDialog.show();
    }

    @Override
    public void finish() {
        super.finish();
    }

}
