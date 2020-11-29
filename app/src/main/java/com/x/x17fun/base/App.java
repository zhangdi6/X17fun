package com.x.x17fun.base;

import android.app.Application;
import android.content.Context;
import android.os.Vibrator;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.baidu.idl.face.platform.FaceSDKManager;
import com.baidu.idl.face.platform.LivenessTypeEnum;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.liys.doubleclicklibrary.helper.ViewDoubleHelper;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.x.x17fun.data.HttpsClient;
import com.x.x17fun.data.WebService;
import com.x.x17fun.entity.UserSyncEntity;
import com.x.x17fun.utils.DeeSpUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


// Created by Ardy on 2020/5/7.
public class App extends MultiDexApplication {

    private ExecutorService mExecService;
    private static App defaultApp;
    private DeeSpUtil spUtil = DeeSpUtil.getInstance();
    private WebService mWebService;
    private HttpsClient mHttpsClient;
    private UserSyncEntity userMsg;
    public static LocationClient mLocationClient = null;
    private Vibrator systemService;
    private HashMap<String, Object> cache;
    public static List<LivenessTypeEnum> livenessList = new ArrayList<LivenessTypeEnum>();
    public static boolean isLivenessRandom = false;
    public static IWXAPI mWxApi;
    @Override
    public void onCreate() {
        super.onCreate();
        //全局防止多次点击
        ViewDoubleHelper.init(this, 500); //自定义间隔时间(单位：毫秒)
        defaultApp = this;
        DeeSpUtil.init(this, "x17fun", Context.MODE_PRIVATE);
        registerToWx();
        mHttpsClient = new HttpsClient();
        mHttpsClient.initHttpsClient();
        mWebService = new WebService();
        mWebService.setHttpsClient(mHttpsClient);

        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        cache = new HashMap<>();
         systemService = (Vibrator)getApplicationContext().getSystemService(VIBRATOR_SERVICE);
        SDKInitializer.initialize(getApplicationContext());

    }


    public static App defaultApp() {
        return defaultApp;
    }

    private void registerToWx() {
        mWxApi = WXAPIFactory.createWXAPI(this, AppContant.WXAPPID, false);
        mWxApi.registerApp(AppContant.WXAPPID);
    }
    public static DeeSpUtil getSp() {
        return DeeSpUtil.getInstance();
    }

    public static boolean execute(Runnable task) {
        try {
            if (defaultApp.mExecService == null) {
                defaultApp.mExecService = Executors.newCachedThreadPool();
            }
            defaultApp.mExecService.execute(task);
            return true;
        } catch (Exception e) {
            Log.e("edencity", e.getMessage(), e);
            return false;
        }

    }
    public static WebService webService() {
        if (defaultApp.mWebService == null)
            defaultApp.mWebService = new WebService();
        return defaultApp.mWebService;
    }

    public static UserSyncEntity userMsg() {
        if (defaultApp.userMsg!=null){
            return defaultApp.userMsg;
        }
        return new UserSyncEntity();
    }


    public static void setCache(String key, Object value) {
        defaultApp.cache.put(key, value);
    }

    public static void removeCache(String key) {
        defaultApp.cache.remove(key);
    }

    public static Object getCache(String key) {
        return defaultApp.cache.get(key);
    }

    public void saveUserMsg(UserSyncEntity user) {

        if (user == null) {
            getSp().remove("userId");
            getSp().remove("ticket");
        } else {
            UserSyncEntity userMsgEntity = new UserSyncEntity(user.getUserInfo(), user.getUnDealOrderCount());
            this.userMsg = userMsgEntity;
        }
    }
}
