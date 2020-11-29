package com.x.x17fun.base;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.x.x17fun.data.DataService;
import com.x.x17fun.entity.BaseResult;
import com.x.x17fun.entity.UserSyncEntity;
import com.x.x17fun.utils.AdiUtils;
import com.x.x17fun.utils.DeeSpUtil;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.yokeyword.fragmentation.SupportFragment;


public class BaseFragment extends SupportFragment {

    /**
     * 返回上一步
     */
    public void backToParent(){
        FragmentManager fm=getFragmentManager();
        if (fm!=null && !fm.isStateSaved()){
            pop();
        }
    }

    public void showToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
    /**
     * 接收视图中控件的点击事件。子类应根据视图的id进行判断和处理
     * @param view 被点击的视图
     * @return true:if has handled  false: not handle
     */
    public void onViewItemClicked(View view){

    }

    /**
     * 处理异步请求，如果需要处理的话，覆盖该函数
     * @param msg
     */
    public void handleMessage(Message msg){

    }
    public void loadImg(Object path , ImageView imageView){
        Glide.with(getContext()).load(path).into(imageView);
    }

    public void loadCircleImg(Object path , ImageView imageView){
        RequestOptions requestOptions = new RequestOptions().circleCrop();
        Glide.with(getContext()).load(path).apply(requestOptions).into(imageView);
    }


    public void startToActivity(Context context, Class<?> aClass) {
        Intent intent = new Intent(context, aClass);
        startActivity(intent);
    }

    public void initVerify(String type, String phone , IBaseCallBack<String> callBack) {

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("phone", phone);
        hashMap.put("type", type);
       /* hashMap.put("app_id", AppContant.APPID);
        hashMap.put("nonce", "1");
        String sign = ParamsUtils.getSign(hashMap);
        try {
            hashMap.put("sign", SHA1Utils.strToSHA1(sign));

        } catch (Exception e) {
            e.printStackTrace();
        }*/

        DataService.getLoginService().get(hashMap)
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
                            callBack.onSuccess("验证码发送成功");
                        } else if (baseResult.getResult_code()==-7){
                            callBack.onSuccess(baseResult.getResult_msg());
                        }else {
                            callBack.onFail(baseResult.getResult_msg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onFail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void syncUser(IBaseCallBack<UserSyncEntity> callBack) {

        //更新用户信息
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("userId", DeeSpUtil.getInstance().getString("userId"));
        hashMap.put("ticket", DeeSpUtil.getInstance().getString("ticket"));
     /*   String sign = ParamsUtils.getSign(hashMap);
        try {
            hashMap.put("sign", SHA1Utils.strToSHA1(sign));
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        DataService.getHomeService().sysnUser(hashMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult<UserSyncEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<UserSyncEntity> baseResult) {

                        if (baseResult != null && baseResult.getResult_code() == 0) {
                            Log.d("bbbbb", baseResult.toString());
                            callBack.onSuccess(baseResult.getData());
                        } else if (baseResult.getResult_code()== -3){
                            AdiUtils.loginOut();
                        }else {
                            callBack.onFail(baseResult.getResult_msg()==null?"请求失败":baseResult.getResult_msg());
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("bbbbb", e.getMessage());
                        callBack.onFail(e.getMessage()==null?"请求失败":e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
