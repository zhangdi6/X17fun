package com.x.x17fun.data;

/* Created by AdScar
    on 2020/6/1.
      */

import android.util.Log;

import com.x.x17fun.base.App;
import com.x.x17fun.base.AppContant;
import com.x.x17fun.entity.BaseResult;
import com.x.x17fun.utils.AdiUtils;
import com.x.x17fun.utils.DeeSpUtil;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import okhttp3.OkHttpClient;

public class WebService {

    public static final int ERROR_NEED_AUTH = 1;
    public static final int ERROR_NETWORK = 2;
    public static final int ERROR_OTHER = 3;
    public static final int ERROR_NOT_REGISTER = 4; //未注册
    private HttpsClient httpsClient;


    public void setHttpsClient(HttpsClient httpsClient) {
        this.httpsClient = httpsClient;
    }

    //更新用户信息
    public BaseResult updateUserMsg(String fileKey1, File file1, HashMap hashMap) {

        try {
            hashMap.put("ticket", DeeSpUtil.getInstance().getString("ticket"));
            hashMap.put("userId", DeeSpUtil.getInstance().getString("userId"));
          /*  hashMap.put("app_id", AppContant.APPID);
            hashMap.put("nonce", "1");*/

            if (file1 != null && file1.exists()) {
                String result = httpsClient.updateUserMsg(AppContant.BASE_URL + "/api/editMyself", hashMap,
                        fileKey1, file1);
                if (result == null) {
                    return new BaseResult<>(ERROR_OTHER, "系统服务无法访问，请重试");
                }
                Log.e("http", result);
                JSONObject jsonObject = new JSONObject(result);
                return new BaseResult<>(jsonObject.getInt("result_code"), jsonObject.getString("result_msg"));

            } else {
                String result = httpsClient.updateUserMsg(AppContant.BASE_URL + "/api/editMyself", hashMap);
                if (result == null) {
                    return new BaseResult<>(ERROR_OTHER, "系统服务无法访问，请重试");
                }
                Log.e("http", result);
                JSONObject jsonObject = new JSONObject(result);
                return new BaseResult<>(jsonObject.getInt("result_code"), jsonObject.getString("result_msg"));
            }

        } catch (Exception e) {
            Log.e("edencity", e.getMessage(), e);
            return new BaseResult<>(ERROR_OTHER, "系统处理出现错误，请重试");
        }

    }
    //实名认证
    public BaseResult updateUserApproval(String fileKey1, File file1, HashMap hashMap) {

        try {
            hashMap.put("ticket", DeeSpUtil.getInstance().getString("ticket"));
            hashMap.put("userId", DeeSpUtil.getInstance().getString("userId"));
          /*  hashMap.put("app_id", AppContant.APPID);
            hashMap.put("nonce", "1");*/

            if (file1 != null && file1.exists()) {
                String result = httpsClient.updateUserMsg(AppContant.BASE_URL + "/api/authentication", hashMap,
                        fileKey1, file1);
                if (result == null) {
                    Log.e("name","1");
                    return new BaseResult<>(ERROR_OTHER, "系统服务无法访问，请重试");
                }
                Log.e("http", result);
                JSONObject jsonObject = new JSONObject(result);
                return new BaseResult<>(jsonObject.getInt("result_code"), jsonObject.getString("result_msg"));

            } else {
                String result = httpsClient.updateUserMsg(AppContant.BASE_URL + "/api/authentication", hashMap);
                if (result == null) {
                    Log.e("name","2");
                    return new BaseResult<>(ERROR_OTHER, "系统服务无法访问，请重试");
                }
                Log.e("http", result);
                JSONObject jsonObject = new JSONObject(result);
                return new BaseResult<>(jsonObject.getInt("result_code"), jsonObject.getString("result_msg"));
            }

        } catch (Exception e) {
            Log.e("edencity", e.getMessage(), e);
            return new BaseResult<>(ERROR_OTHER, "系统处理出现错误，请重试");
        }

    }
    //发布动态
    public BaseResult publishActivity(String fileKey1, List<File> file1, HashMap hashMap) {

        try {
            hashMap.put("ticket", DeeSpUtil.getInstance().getString("ticket"));
            hashMap.put("userId", DeeSpUtil.getInstance().getString("userId"));
          /*  hashMap.put("app_id", AppContant.APPID);
            hashMap.put("nonce", "1");*/

            Log.e("hashnmap",hashMap.toString());
                String result = httpsClient.publishProduct(AppContant.BASE_URL + "/api/zonePublished", hashMap, "multipart/octet-stream"
                        , fileKey1, file1);
                if (result == null) {
                    return new BaseResult<>(ERROR_OTHER, "系统服务无法访问，请重试");
                }
                Log.e("http", result);
                JSONObject jsonObject = new JSONObject(result);
                return new BaseResult<>(jsonObject.getInt("result_code"), jsonObject.getString("result_msg"));

        } catch (Exception e) {
            Log.e("edencity", e.getMessage(), e);
            return new BaseResult<>(ERROR_OTHER, "系统处理出现错误，请重试");
        }
    }

    //发布商品
    public BaseResult publishProduct(String fileKey1, List<File> file1, HashMap hashMap) {

        try {
            hashMap.put("ticket", DeeSpUtil.getInstance().getString("ticket"));
            hashMap.put("userId", DeeSpUtil.getInstance().getString("userId"));
          /*  hashMap.put("app_id", AppContant.APPID);
            hashMap.put("nonce", "1");*/

          Log.e("hashnmap",hashMap.toString());
            if (file1 != null && file1.size() > 0) {
                String result = httpsClient.publishProduct(AppContant.BASE_URL + "/api/published", hashMap, "multipart/octet-stream"
                        , fileKey1, file1);
                if (result == null) {
                    return new BaseResult<>(ERROR_OTHER, "系统服务无法访问，请重试");
                }
                Log.e("http", result);
                JSONObject jsonObject = new JSONObject(result);
                return new BaseResult<>(jsonObject.getInt("result_code"), jsonObject.getString("result_msg"));

            } else {
                AdiUtils.showToast("你未上传商品图片");
                return new BaseResult<>(ERROR_OTHER, "你未上传商品图片");
            }

        } catch (Exception e) {
            Log.e("edencity", e.getMessage(), e);
            return new BaseResult<>(ERROR_OTHER, "系统处理出现错误，请重试");
        }
    }

    //重新发布商品
    public BaseResult rePublishProduct(HashMap hashMap) {

        try {
            hashMap.put("ticket", DeeSpUtil.getInstance().getString("ticket"));
            hashMap.put("userId", DeeSpUtil.getInstance().getString("userId"));
          /*  hashMap.put("app_id", AppContant.APPID);
            hashMap.put("nonce", "1");*/

          Log.e("hashnmap",hashMap.toString());

                String result = httpsClient.publishProduct(AppContant.BASE_URL + "/api/rePublished", hashMap, "multipart/octet-stream"
                        , null, null);
                if (result == null) {
                    return new BaseResult<>(ERROR_OTHER, "系统服务无法访问，请重试");
                }
                Log.e("http", result);
                JSONObject jsonObject = new JSONObject(result);
                return new BaseResult<>(jsonObject.getInt("result_code"), jsonObject.getString("result_msg"));


        } catch (Exception e) {
            Log.e("edencity", e.getMessage(), e);
            return new BaseResult<>(ERROR_OTHER, "系统处理出现错误，请重试");
        }
    }
    
    //发送图片
    public BaseResult sendImg(String fileKey1, File file1, HashMap hashMap) {

        try {
            hashMap.put("ticket", DeeSpUtil.getInstance().getString("ticket"));
            hashMap.put("userId", DeeSpUtil.getInstance().getString("userId"));
          /*  hashMap.put("app_id", AppContant.APPID);
            hashMap.put("nonce", "1");*/

          Log.e("hashnmap",hashMap.toString());
            if (file1 != null) {
                String result = httpsClient.sendImg(AppContant.BASE_URL + "/api/sendMessage", hashMap, "multipart/octet-stream"
                        , fileKey1, file1);
                if (result == null) {
                    return new BaseResult<>(ERROR_OTHER, "系统服务无法访问，请重试");
                }
                Log.e("http", result);
                JSONObject jsonObject = new JSONObject(result);
                return new BaseResult<>(jsonObject.getInt("result_code"), jsonObject.getString("result_msg"));

            } else {
                AdiUtils.showToast("你未选择图片");
                return new BaseResult<>(ERROR_OTHER, "你未选择图片");
            }

        } catch (Exception e) {
            Log.e("edencity", e.getMessage(), e);
            return new BaseResult<>(ERROR_OTHER, "系统处理出现错误，请重试");
        }
    }


    //发布评价
    public BaseResult publishRemark(String fileKey1, List<File> file1, HashMap hashMap) {

        try {


          Log.e("hashnmap",hashMap.toString());
            if (file1 != null && file1.size() > 0) {
                String result = httpsClient.publishProduct(AppContant.BASE_URL + "/api/remarkPublished", hashMap, "multipart/octet-stream"
                        , fileKey1, file1);
                if (result == null) {
                    return new BaseResult<>(ERROR_OTHER, "系统服务无法访问，请重试");
                }
                Log.e("http", result);
                JSONObject jsonObject = new JSONObject(result);
                return new BaseResult<>(jsonObject.getInt("result_code"), jsonObject.getString("result_msg"));

            } else {
                return new BaseResult<>(ERROR_OTHER, "你未上传商品图片");
            }

        } catch (Exception e) {
            Log.e("edencity", e.getMessage(), e);
            return new BaseResult<>(ERROR_OTHER, "系统处理出现错误，请重试");
        }
    }

}
