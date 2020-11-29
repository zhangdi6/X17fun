package com.x.x17fun.data;



import com.x.x17fun.entity.BaseResult;
import com.x.x17fun.entity.BuyInOrderEntity;
import com.x.x17fun.entity.SalerOutEntity;
import com.x.x17fun.entity.UserLoginEntity;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginService {

    /*
    *
    * 登录注册相关
    *
    */


    //获取验证码 phone
    // type (login,regist,forget,modify)
    @POST("api/verificationCode/get")
    @FormUrlEncoded
    Observable<BaseResult> get(@FieldMap Map<String, Object> map);

    //注册或登录
    // phone
    // verificationCode
    @POST("api/registerOrLogin")
    @FormUrlEncoded
    Observable<BaseResult<UserLoginEntity>>register(@FieldMap Map<String, Object> map);


  /*  初始化密码
    password*/
    @POST("api/initPassword")
    @FormUrlEncoded
    Observable<BaseResult>initPassword(@FieldMap Map<String, Object> map);

    //忘记、修改密码
    // phone
    // verificationCode
    // password
    @POST("api/modifyPassword")
    @FormUrlEncoded
    Observable<BaseResult>resetLoginPwd(@FieldMap Map<String, Object> map);

    //忘记、修改支付密码
    // phone
    // verificationCode
    // payPassword

    @POST("api/modifyPayPassword")
    @FormUrlEncoded
    Observable<BaseResult>resetPayPwd(@FieldMap Map<String, Object> map);

    //手机号密码登录
    // phone
    // password
    @POST("api/loginByPassword")
    @FormUrlEncoded
    Observable<BaseResult<UserLoginEntity>>phonePwdLogin(@FieldMap Map<String, Object> map);

    //我买入的订单
    // orderStadus
    @POST("api/order/myPurchase")
    @FormUrlEncoded
    Observable<BaseResult<BuyInOrderEntity>>getBuyInList(@FieldMap Map<String, Object> map);

    //我卖出的订单
    // orderStadus
    @POST("api/order/mySell")
    @FormUrlEncoded
    Observable<BaseResult<SalerOutEntity>>getSalerOutList(@FieldMap Map<String, Object> map);

    //接受订单
    // orderId
    @POST("api/order/accept")
    @FormUrlEncoded
    Observable<BaseResult>receiverOrder(@FieldMap Map<String, Object> map);

    //取消订单
    // orderId
    @POST("api/order/buyerCancel")
    @FormUrlEncoded
    Observable<BaseResult>cancleOrder(@FieldMap Map<String, Object> map);

    //确认收货
    // orderId
    @POST("api/order/confirmReceive")
    @FormUrlEncoded
    Observable<BaseResult>confirmReceive(@FieldMap Map<String, Object> map);

    //确认发货
    // orderId
    @POST("api/order/pendingBuyerConfirm")
    @FormUrlEncoded
    Observable<BaseResult>pendingBuyerConfirm(@FieldMap Map<String, Object> map);


}
