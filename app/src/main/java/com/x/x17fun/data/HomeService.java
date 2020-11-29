package com.x.x17fun.data;



import com.x.x17fun.entity.AliPayEntity;
import com.x.x17fun.entity.BaseResult;
import com.x.x17fun.entity.BillEntity;
import com.x.x17fun.entity.CardListEnity;
import com.x.x17fun.entity.ChatEntity;
import com.x.x17fun.entity.MyAddessEntity;
import com.x.x17fun.entity.MyPushedEntity;
import com.x.x17fun.entity.OrderDetailEntity;
import com.x.x17fun.entity.RePEntity;
import com.x.x17fun.entity.RefrectEntity;
import com.x.x17fun.entity.RemarkListEntity;
import com.x.x17fun.entity.RemarkZoneEntity;
import com.x.x17fun.entity.TodayEntity;
import com.x.x17fun.entity.TomorrowEntity;
import com.x.x17fun.entity.UnReadListEntity;
import com.x.x17fun.entity.UserHomePageEntity;
import com.x.x17fun.entity.UserPushListEntity;
import com.x.x17fun.entity.UserSyncEntity;
import com.x.x17fun.entity.WeChatPayEntity;
import com.x.x17fun.entity.ZoneEntity;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface HomeService {

    /*
    *
    * 首页相关
    *
    */


    //某人的主页信息
    // publisherId
    @POST("api/somebodyDetail")
    @FormUrlEncoded
    Observable<BaseResult<UserHomePageEntity>> getUserHomePageMsg(@FieldMap Map<String, Object> map);

    //某人的发布记录
    // publisherId
    @POST("api/somebodyPublishedList")
    @FormUrlEncoded
    Observable<BaseResult<UserPushListEntity>>getSomebodyPushList(@FieldMap Map<String, Object> map);


  /* 获取今日发布列表
    */
    @POST("api/todayPublishedList")
    @FormUrlEncoded
    Observable<BaseResult<TodayEntity>>getTodayPublishedList(@FieldMap Map<String, Object> map);

    //获取明日发布列表
    @POST("api/tomorrowPublishedList")
    @FormUrlEncoded
    Observable<BaseResult<TomorrowEntity>>getTomorrowPublishedList(@FieldMap Map<String, Object> map);

    //同步

    @POST("api/syncUser")
    @FormUrlEncoded
    Observable<BaseResult<UserSyncEntity>>sysnUser(@FieldMap Map<String, Object> map);

    //我的发布记录
    @POST("api/myPublished")
    @FormUrlEncoded
    Observable<BaseResult<MyPushedEntity>>getMyPushList(@FieldMap Map<String, Object> map);

   /*
    发布
    productName
    productTag
    price
    count
    deadLine
    deliveryTime
    deliveryAddress
    deliveryAera
    aeraLongtitude
    aeraLatitude
    */

    @POST("api/published")
    @FormUrlEncoded
    Observable<BaseResult>doPushMeal(@FieldMap Map<String, Object> map);

    /*
    重新发布
    foodId
    price
    count
    deadLine
    deliveryTime
    deliveryAddress
    deliveryAera
    aeraLongtitude
    aeraLatitude
    */

    @POST("api/published")
    @FormUrlEncoded
    Observable<BaseResult>rePushMeal(@FieldMap Map<String, Object> map);


    /*修改个人信息
    nickName
    gender
    birthday
    portait
    bornAddress
    colleage 学校
    company 公司
    profession 职业
    signature
    */
    @POST("api/editMyself")
    @FormUrlEncoded
    Observable<BaseResult>editUserProfit(@FieldMap Map<String, Object> map);

    //点赞
    // publisherId foodId
    @POST("api/appreciatePublished")
    @FormUrlEncoded
    Observable<BaseResult>appreciatePublished(@FieldMap Map<String, Object> map);

    //关注
    // focusUserId
    @POST("api/focus")
    @FormUrlEncoded
    Observable<BaseResult>focus(@FieldMap Map<String, Object> map);

    //取消关注
    // focusUserId
    @POST("api/cancelFocus")
    @FormUrlEncoded
    Observable<BaseResult>cancleFocus(@FieldMap Map<String, Object> map);

    //新增地址
    /*
    * receiveTag
    * receiverName
    * receiverGender
    * receiverPhone
    * receiverAddress
    * receiverAera
    * aeraLongtitude
    * aeraLatitude
    */
    @POST("api/addReceiveMessage")
    @FormUrlEncoded
    Observable<BaseResult>addreceivermeg(@FieldMap Map<String, Object> map);

    //我的地址
    @POST("api/myReceive")
    @FormUrlEncoded
    Observable<BaseResult<MyAddessEntity>>myReceive(@FieldMap Map<String, Object> map);


    /*
    下单
    payPassword
    publishedId
    purchaseCount
    orderNote
    receiverPhone
    receiverGender
    receiverName
    receiverAddress
    */
    @POST("api/placeOrder")
    @FormUrlEncoded
    Observable<BaseResult<OrderDetailEntity>>pay(@FieldMap Map<String, Object> map);

    /*
    添加账户
    outerAccountType
    accountName
    accountNo
    bank

    */
    @POST("api/payCard/add")
    @FormUrlEncoded
    Observable<BaseResult>addAccout(@FieldMap Map<String, Object> map);

    /*
    查看账户
    */
    @POST("api/payCards")
    @FormUrlEncoded
    Observable<BaseResult<CardListEnity>>getPayCardList(@FieldMap Map<String, Object> map);

    /*
    删除卖掉的订单
    orderId
    */
    @POST("api/order/deleteSale")
    @FormUrlEncoded
    Observable<BaseResult>deleteSaled(@FieldMap Map<String, Object> map);

    /*
    删除买了的订单
    orderId
    */
    @POST("api/order/deleteBuy")
    @FormUrlEncoded
    Observable<BaseResult>deleteBuy(@FieldMap Map<String, Object> map);

    //用户账单
    @POST("api/accountChangeRecord")
    @FormUrlEncoded
    Observable<BaseResult<BillEntity>>chargeRecord(@FieldMap Map<String,Object>map);

  //微信支付
    @POST("api/charge/weichatpay")
    @FormUrlEncoded
    Observable<BaseResult<WeChatPayEntity>>wechatPay(@FieldMap Map<String,Object>map);

    //阿里支付
    // userId
    // ticket
    @POST("api/charge/alipay")
    @FormUrlEncoded
    Observable<BaseResult<AliPayEntity>>aliPay(@FieldMap Map<String,Object>map);

    //提现
    // userId
    // ticket
    @POST("api/withdraw")
    @FormUrlEncoded
    Observable<BaseResult>getMoney(@FieldMap Map<String,Object>map);

    //提现记录
    // userId
    // ticket
    @POST("api/withdrawList")
    @FormUrlEncoded
    Observable<BaseResult<RefrectEntity>>getMoneyList(@FieldMap Map<String,Object>map);

    //评论
    // publisherId
    @POST("api/remarkPublished")
    @FormUrlEncoded
    Observable<BaseResult>remarkPublished(@FieldMap Map<String, Object> map);

    //评论列表
    // publisherId foodId
    @POST("api/remarkList")
    @FormUrlEncoded
    Observable<BaseResult<RemarkListEntity>>remarkList(@FieldMap Map<String, Object> map);

    //获取和某人的聊天记录
    // toUserId
    // lasttime
    // refreshPatten
    @POST("api/imChatRecords")
    @FormUrlEncoded
    Observable<BaseResult<ChatEntity>>imChatRcords(@FieldMap Map<String, Object> map);

    //获取关注动态列表
    // lasttime
    // refreshPatten
    @POST("api/forcusZoneList")
    @FormUrlEncoded
    Observable<BaseResult<ZoneEntity>>focusZoneList(@FieldMap Map<String, Object> map);

    //获取推荐动态列表
    // lasttime
    // refreshPatten
    @POST("api/recommendZoneList")
    @FormUrlEncoded
    Observable<BaseResult<ZoneEntity>>recommendZoneList(@FieldMap Map<String, Object> map);

    //发消息
    //toUserId
    //dataType
    //content
    @POST("api/sendMessage")
    @FormUrlEncoded
    Observable<BaseResult>sendMsg(@FieldMap Map<String, Object> map);

    //未读消息列表
    @POST("api/unreadMessages")
    @FormUrlEncoded
    Observable<BaseResult<UnReadListEntity>>unreadMessages(@FieldMap Map<String, Object> map);

    //查询商品信息
    @POST("api/foodDetail")
    @FormUrlEncoded
    Observable<BaseResult<RePEntity>>foodDetail(@FieldMap Map<String, Object> map);

    /**
     * 动态点赞
     * param zoneUserId
     * param zoneId
     */
    @POST("api/appreciateZone")
    @FormUrlEncoded
    Observable<BaseResult>appreciateZone(@FieldMap Map<String, Object> map);

    /**
     * 动态评论
     * param userId
     * param zoneId
     * param zoneUserId
     * param remarkContent
     */
    @POST("api/remarkZone")
    @FormUrlEncoded
    Observable<BaseResult>remarkZone(@FieldMap Map<String, Object> map);

    /**
     * 动态评论列表
     * param zoneId
     */
    @POST("api/zone/remarkList")
    @FormUrlEncoded
    Observable<BaseResult<RemarkZoneEntity>>remarkZoneList(@FieldMap Map<String, Object> map);


}
