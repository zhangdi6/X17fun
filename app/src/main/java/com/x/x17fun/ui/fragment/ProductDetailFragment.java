package com.x.x17fun.ui.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.x.x17fun.R;
import com.x.x17fun.adapter.MyTalkListAdapter;
import com.x.x17fun.base.App;
import com.x.x17fun.base.BaseDialog;
import com.x.x17fun.base.BaseFragment;
import com.x.x17fun.base.EventMessage;
import com.x.x17fun.data.DataService;
import com.x.x17fun.entity.BaseEntity;
import com.x.x17fun.entity.BaseResult;
import com.x.x17fun.entity.RemarkListEntity;
import com.x.x17fun.entity.TodayEntity;
import com.x.x17fun.entity.TomorrowEntity;
import com.x.x17fun.ui.activity.UpdatePayPwdActivity;
import com.x.x17fun.ui.activity.UserVerifyActivity;
import com.x.x17fun.utils.ButtonUtils;
import com.x.x17fun.utils.DeeSpUtil;
import com.xw.banner.Banner;
import com.xw.banner.BannerConfig;
import com.xw.banner.loader.ImageLoader;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDetailFragment extends BaseFragment implements View.OnClickListener {

    private View view;
    private ImageView mBack;
    private ImageView mAdd;
    /**
     * 1
     */
    private TextView mTotalIndex;
    /**
     * 5
     */
    private TextView mAllIndex;
    private Banner mVpBanner;
    /**
     * 三秦套餐：凉皮+肉夹馍+冰峰
     */
    private TextView mFoodName;
    private ImageView mUserHead;
    private ImageView mUserSex;
    private ImageView mFoller;
    /**
     * AdScar
     */
    private TextView mUserName;
    /**
     * 山西 阳泉人  中国矿业大学
     */
    private TextView mUserAddress;
    /**
     * 明天 中午 12:30
     */
    private TextView mFoodTime;
    /**
     * 距您300m
     */
    private TextView mUserLocation;
    /**
     * 望京SOHO大厦
     */
    private TextView mUserLocationDesc;
    private SmartRefreshLayout mSmart;
    /**
     * 立即购买
     */
    private TextView mBtnBuy;
    /**
     * 1314
     */
    private TextView mLoveCount;
    private ImageView mLove;
    /**
     * 521
     */
    private TextView mZanCount;
    private ImageView mZan;
    private List<String> mList = new ArrayList<>();
    private ViewPager.OnPageChangeListener onPageChangeListener;
    private  TodayEntity.TodayPbulishedListBean listBean;
    private  TomorrowEntity.TomorrowPublishedListBean listBean2;

    private TextView mPrice;
    private TextView mTv_tag;
    private String mHeadimg = "";
    private int zanCount;
    private boolean isCheck;
    private double lat;
    private double along;
    private TextView user_talk;
    private RecyclerView rlv_talk;
    private MyTalkListAdapter talkListAdapter;
    private LinearLayout btn_pinglun;
    private BaseDialog baseDialog;
    private String s = "";
    private EditText et_pinglun;
    private String publishedId = "";
    private String publisherId = "";
    private ImageView send;
    private int a = 2;
    private String foodId = "";

    public static ProductDetailFragment getInstance(TodayEntity.TodayPbulishedListBean id , int type,
                                                    double lat , double along) {
        ProductDetailFragment detailFragment = new ProductDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("id",id);
        bundle.putInt("type",type);
        bundle.putDouble("lat",lat);
        bundle.putDouble("along",along);
        Log.e("sss2",id.toString());
        detailFragment.setArguments(bundle);
        return detailFragment;
    }
    public static ProductDetailFragment getInstance2(TomorrowEntity.TomorrowPublishedListBean id , int type ,
                                                     double lat , double along) {
        ProductDetailFragment detailFragment = new ProductDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("id",id);
        bundle.putInt("type",type);
        bundle.putDouble("lat",lat);
        bundle.putDouble("along",along);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_product_detail, container, false);
        Bundle bundle = getArguments();
        initView(inflate);
        if (bundle!=null){

             lat = bundle.getDouble("lat");
             along = bundle.getDouble("along");
            if (bundle.getInt("type")==1){
                TodayEntity.TodayPbulishedListBean base = (TodayEntity.TodayPbulishedListBean)bundle.getSerializable("id");
                Log.e("sss",base.toString());

                initData(base);
            }else {
                TomorrowEntity.TomorrowPublishedListBean base = (TomorrowEntity.TomorrowPublishedListBean)bundle.getSerializable("id");

                initData(base);
            }

        }



        return inflate;
    }

    private void initTalk() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("publishedId",publishedId);
        hashMap.put("foodId",foodId);
        Log.e("list",publishedId);
        DataService.getHomeService().remarkList(hashMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult<RemarkListEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<RemarkListEntity> baseResult) {
                        if (baseResult.getResult_code() == 0) {

                            if (baseResult.getData()!=null && baseResult.getData().getCommentList()!=null
                            && baseResult.getData().getCommentList().size()>0){
                                ArrayList<RemarkListEntity.CommentListBean> commentList = (ArrayList<RemarkListEntity.CommentListBean>) baseResult.getData().getCommentList();
                                user_talk.setText(commentList.size()+"条评论");
                                rlv_talk.setHasFixedSize(true);
                                rlv_talk.setNestedScrollingEnabled(false);
                                rlv_talk.setLayoutManager(new LinearLayoutManager(getContext()));
                                talkListAdapter = new MyTalkListAdapter();
                                rlv_talk.setAdapter(talkListAdapter);
                                talkListAdapter.addData(commentList);
                            }else{

                                user_talk.setVisibility(View.GONE);
                            }
                        } else {
                            showToast(baseResult.getResult_msg());
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


    private void initBanner(String displayImg) {

        if (!displayImg.equals("")){
            if (displayImg.contains(",")){
                mList.clear();
                String[] img = displayImg.split(",");
                mHeadimg = img[0];
                for (int i = 0; i < img.length; i++) {
                    mList.add(img[i]);
                }
            }else{
                mList.clear();
                mList.add(displayImg);
                mHeadimg = displayImg;
            }

            //初始化头部轮播图
            mVpBanner.setImages(mList).setImageLoader(new ImageLoader() {
                @Override
                public void displayImage(Context context, Object path, ImageView imageView) {
                    String path1 = (String) path;
                    RequestOptions requestOptions = RequestOptions.centerCropTransform();
                    if (context!=null && path1!=null && !path1.equals("")){
                        Glide.with(context).load(path1).apply(requestOptions).into(imageView);
                    }
                }
            }).setDelayTime(3000)
                    .isAutoPlay(false)
                    .setBannerStyle(BannerConfig.CUSTOM_INDICATOR)
                    .start().setOnPageChangeListener(onPageChangeListener);
        }
        mTotalIndex.setText(1+"");
        mAllIndex.setText("/"+mList.size()+"");
    }

    private void initData(TodayEntity.TodayPbulishedListBean baseEntity) {
        this.listBean = baseEntity;
        mPrice.setText(baseEntity.getSalePrice() + "");
        publishedId = baseEntity.getMyPublishedId();
        publisherId = baseEntity.getUserId();
        foodId = baseEntity.getFoodId();
        mUserName.setText(baseEntity.getNickName() + "");
            if (baseEntity.getBornAddress()==null || baseEntity.getBornAddress().equals("")){

            }else {
                if (baseEntity.getColleage()!=null && !baseEntity.getColleage().equals("")){
                    mUserAddress.setText(baseEntity.getBornAddress()+" "+baseEntity.getColleage());
                }else{
                    mUserAddress.setText(baseEntity.getBornAddress());
                }
            }
            mTv_tag.setText(baseEntity.getProductTag());
            mUserLocationDesc.setText(baseEntity.getDeliveryAera()==null? "":baseEntity.getDeliveryAera());

        if (lat!=0){
            LatLng latLng = new LatLng(Double.parseDouble(baseEntity.getAeraLatitude()), Double.parseDouble(baseEntity.getAeraLongtitude()));
            float distance = (float) DistanceUtil.getDistance(latLng, new LatLng(lat,along));
            float cc = distance/100; //得到10.51==
            int d = Math.round(cc);//四舍五入是11
            float e=d/(float)10;//把10 也强转为float型的，再让10除以它==
            mUserLocation.setText("距您"+e+"km");
        }else{
            mUserLocation.setText("距离暂未知");
        }
            TodayEntity.TodayPbulishedListBean.DeliveryTimeBean deliveryTime = baseEntity.getDeliveryTime();
            TodayEntity.TodayPbulishedListBean.CreatetimeBean createtime = baseEntity.getCreatetime();
            String day = deliveryTime.getDate()==createtime.getDate()?"今天 ":"明天 ";
            if (deliveryTime.getHours()>= 6 && deliveryTime.getHours()< 11){
                mFoodTime.setText(day+ "上午 "+deliveryTime.getHours()+":"+deliveryTime.getMinutes());
            }else if (deliveryTime.getHours() < 6){
                mFoodTime.setText(day + "凌晨 "+deliveryTime.getHours()+":"+deliveryTime.getMinutes());
            }else if (deliveryTime.getHours()>14 && deliveryTime.getHours()< 18 ){
                mFoodTime.setText(day + "下午 "+deliveryTime.getHours()+":"+deliveryTime.getMinutes());
            }else if (deliveryTime.getHours()>=11 && deliveryTime.getHours()<=14){
                mFoodTime.setText(day + "中午 "+deliveryTime.getHours()+":"+deliveryTime.getMinutes());
            }else{
                mFoodTime.setText(day + "晚上 "+deliveryTime.getHours()+":"+deliveryTime.getMinutes());
            }
            initBanner(baseEntity.getDisplayUrl());
            mFoodName.setText(baseEntity.getProductName());
            mUserSex.setImageResource("1".equals(baseEntity.getGender())?R.mipmap.nan : R.mipmap.women);
             zanCount = baseEntity.getAppreciateCount();
            mZanCount.setText(zanCount+"");
            if (baseEntity.getIsAppreciate().equals("1")){
                isCheck = true;
                mZan.setImageResource(R.mipmap.xinxinhong);
            }else{
                isCheck = false;
                mZan.setImageResource(R.mipmap.xinxin);
            }
            mZan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isCheck){
                        isCheck = false;
                        mZan.setImageResource(R.mipmap.xinxin);
                        zanCount--;
                    }else{
                        isCheck = true;
                        zanCount++;
                        mZan.setImageResource(R.mipmap.xinxinhong);
                    }
                    mZanCount.setText(zanCount+"");
                    if (!ButtonUtils.isFastDoubleClick(R.id.zan,2000)){
                        zan(baseEntity.getMyPublishedId());
                    }
                }
            });
            if (baseEntity.getIsFocus().equals("1")){
                mFoller.setVisibility(View.GONE);
            }else{
                mFoller.setVisibility(View.VISIBLE);
                mFoller.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickCancle(baseEntity.getUserId());
                    }});
            }
            RequestOptions requestOptions = new RequestOptions().circleCrop();
            Glide.with(getContext()).load(baseEntity.getPortait()).apply(requestOptions).into(mUserHead);
        initTalk();
    }

    private void zan(String myPublishedId) {

            HashMap<String, Object> hashMap = new HashMap<>();

            hashMap.put("publishedId",myPublishedId);
            hashMap.put("foodId",foodId);
            hashMap.put("userId", DeeSpUtil.getInstance().getString("userId"));
            hashMap.put("ticket",DeeSpUtil.getInstance().getString("ticket"));
            DataService.getHomeService().appreciatePublished(hashMap)
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
                                a++;
                                if ( a % 2 == 1){
                                    DeeSpUtil.getInstance().putString("isRefresh","1");
                                }else {
                                    DeeSpUtil.getInstance().putString("isRefresh","0");
                                }
                            } else {
                                showToast(baseResult.getResult_msg());
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


    private void clickCancle(String userId) {
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("focusUserId",userId);
        hashMap.put("userId", DeeSpUtil.getInstance().getString("userId"));
        hashMap.put("ticket",DeeSpUtil.getInstance().getString("ticket"));
        DataService.getHomeService().focus(hashMap)
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
                            mFoller.setVisibility(View.GONE);
                        } else {

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

    private void initData(TomorrowEntity.TomorrowPublishedListBean baseEntity) {
        this.listBean2 = baseEntity;
        mPrice.setText(baseEntity.getSalePrice() + "");
        mUserName.setText(baseEntity.getNickName() + "");
        if (baseEntity.getBornAddress()==null || "".equals(baseEntity.getBornAddress())){

        }else {
            if (baseEntity.getBornAddress()==null || "".equals(baseEntity.getBornAddress())){

            }else {
                if (baseEntity.getColleage()!=null && !"".equals(baseEntity.getColleage())){
                    mUserAddress.setText(baseEntity.getBornAddress()+" "+baseEntity.getColleage());
                }else{
                    mUserAddress.setText(baseEntity.getBornAddress());
                }
            }
        }
         publishedId = baseEntity.getMyPublishedId();
         publisherId = baseEntity.getUserId();
        foodId = baseEntity.getFoodId();
        mTv_tag.setText(baseEntity.getProductTag());
        mUserLocationDesc.setText(baseEntity.getDeliveryAera()==null? "":baseEntity.getDeliveryAera());
        if (lat!=0){
            LatLng latLng = new LatLng(Double.parseDouble(baseEntity.getAeraLatitude()), Double.parseDouble(baseEntity.getAeraLongtitude()));
            float distance = (float) DistanceUtil.getDistance(latLng, new LatLng(lat,along));
            float cc = distance/100; //得到10.51==
            int d = Math.round(cc);//四舍五入是11
            float e=d/(float)10;//把10 也强转为float型的，再让10除以它==
            mUserLocation.setText("距您"+e+"km");
        }else{
            mUserLocation.setText("距离暂未知");
        }
        TomorrowEntity.TomorrowPublishedListBean.DeliveryTimeBean deliveryTime = baseEntity.getDeliveryTime();
        TomorrowEntity.TomorrowPublishedListBean.CreatetimeBean createtime = baseEntity.getCreatetime();
        String day = deliveryTime.getDate()==createtime.getDate()?"今天 ":"明天 ";
        if (deliveryTime.getHours()>= 6 && deliveryTime.getHours()< 11){
            mFoodTime.setText(day+ "上午 "+deliveryTime.getHours()+":"+deliveryTime.getMinutes());
        }else if (deliveryTime.getHours() < 6){
            mFoodTime.setText(day + "凌晨 "+deliveryTime.getHours()+":"+deliveryTime.getMinutes());
        }else if (deliveryTime.getHours()>14 && deliveryTime.getHours()< 18 ){
            mFoodTime.setText(day + "下午 "+deliveryTime.getHours()+":"+deliveryTime.getMinutes());
        }else if (deliveryTime.getHours()>=11 && deliveryTime.getHours()<=14){
            mFoodTime.setText(day + "中午 "+deliveryTime.getHours()+":"+deliveryTime.getMinutes());
        }else{
            mFoodTime.setText(day + "晚上 "+deliveryTime.getHours()+":"+deliveryTime.getMinutes());
        }
        initBanner(baseEntity.getDisplayUrl());
        mFoodName.setText(baseEntity.getProductName());
        mUserSex.setImageResource("1".equals(baseEntity.getGender())?R.mipmap.nan : R.mipmap.women);
        zanCount = baseEntity.getAppreciateCount();
        mZanCount.setText(zanCount+"");

        if (baseEntity.getIsAppreciate().equals("1")){
            isCheck = true;
            mZan.setImageResource(R.mipmap.xinxinhong);
        }else{
            isCheck = false;
            mZan.setImageResource(R.mipmap.xinxin);
        }
        mZan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCheck){
                    isCheck = false;
                    zanCount--;
                    mZan.setImageResource(R.mipmap.xinxin);
                }else{
                    isCheck = true;
                    zanCount++;
                    mZan.setImageResource(R.mipmap.xinxinhong);
                }
                mZanCount.setText(zanCount+"");
                if (!ButtonUtils.isFastDoubleClick(R.id.zan,2000)){
                    zan(baseEntity.getMyPublishedId());
                }
            }
        });
        if (baseEntity.getIsFocus().equals("1")){
            mFoller.setVisibility(View.GONE);
        }else{
            mFoller.setVisibility(View.VISIBLE);
            mFoller.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickCancle(baseEntity.getUserId());
                }
            });
        }
        RequestOptions requestOptions = new RequestOptions().circleCrop();
        Glide.with(getContext()).load(baseEntity.getPortait()).apply(requestOptions).into(mUserHead);
        initTalk();
    }



    private void initView(View inflate) {
        mBack = (ImageView) inflate.findViewById(R.id.back);
        mBack.setOnClickListener(this);
        mAdd = (ImageView) inflate.findViewById(R.id.share);
        mAdd.setOnClickListener(this);
        mTotalIndex = (TextView) inflate.findViewById(R.id.total_index);
        mAllIndex = (TextView) inflate.findViewById(R.id.all_index);
        mVpBanner = (Banner) inflate.findViewById(R.id.vp_banner);
        mFoodName = (TextView) inflate.findViewById(R.id.food_name);
        mUserHead = (ImageView) inflate.findViewById(R.id.user_head);
        mUserSex = (ImageView) inflate.findViewById(R.id.user_sex);
        mTv_tag = (TextView) inflate.findViewById(R.id.tag);
        mFoller = (ImageView) inflate.findViewById(R.id.foller);
        mFoller.setOnClickListener(this);
        mUserName = (TextView) inflate.findViewById(R.id.user_name);
        mUserAddress = (TextView) inflate.findViewById(R.id.user_address);
        mFoodTime = (TextView) inflate.findViewById(R.id.food_time);
        mUserLocation = (TextView) inflate.findViewById(R.id.user_location);
        mUserLocationDesc = (TextView) inflate.findViewById(R.id.user_location_desc);
        mSmart = (SmartRefreshLayout) inflate.findViewById(R.id.smart);
        mBtnBuy = (TextView) inflate.findViewById(R.id.btn_buy);
        mPrice = (TextView) inflate.findViewById(R.id.price);
        mBtnBuy.setOnClickListener(this);
        mLoveCount = (TextView) inflate.findViewById(R.id.love_count);
        mLove = (ImageView) inflate.findViewById(R.id.love);
        mLove.setOnClickListener(this);
        mZanCount = (TextView) inflate.findViewById(R.id.zan_count);
        user_talk = (TextView) inflate.findViewById(R.id.user_talk);
        rlv_talk = (RecyclerView) inflate.findViewById(R.id.rlv_talk);
        mZanCount = (TextView) inflate.findViewById(R.id.zan_count);
        mZan = (ImageView) inflate.findViewById(R.id.zan);
        btn_pinglun = (LinearLayout) inflate.findViewById(R.id.btn_pinglun);
        mZan.setOnClickListener(this);

        btn_pinglun.setOnClickListener(this);
        onPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

                mTotalIndex.setText(i+1+"");
                mAllIndex.setText("/"+mList.size()+"");
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.back:
                getActivity().finish();
                break;
            case R.id.share:
                break;
            case R.id.foller:
                break;
            case R.id.btn_buy:
                if (App.userMsg()!=null &&App.userMsg().getUserInfo()!=null
                        && App.userMsg().getUserInfo().getUserStatus().equals("1")){
                    if (listBean!=null){
                        start(OrderPayFragment.getInstance(1,
                                listBean.getPortait(),listBean.getNickName(),listBean.getProductName(),
                                mHeadimg,listBean.getSalePrice(),listBean.getCount(),listBean.getMyPublishedId()));
                    }else{
                        start(OrderPayFragment.getInstance(1,
                                listBean2.getPortait(),listBean2.getNickName(),listBean2.getProductName(),
                                mHeadimg,listBean2.getSalePrice(),listBean2.getCount(),listBean2.getMyPublishedId()));
                    }
                }else{
                    showToast("您还未进行实名认证");
                    startToActivity(getContext(), UserVerifyActivity.class);
                }


                break;
            case R.id.love:
                break;
            case R.id.zan:
                break;
            case R.id.btn_pinglun:
                initPingLunDialog();
                break;
        }
    }

    private void initPingLunDialog() {

        View inflate = getLayoutInflater().inflate(R.layout.dialog_pinglun, null);
         send = inflate.findViewById(R.id.send);
        RelativeLayout null_layout = inflate.findViewById(R.id.null_layout);
        ConstraintLayout layout = inflate.findViewById(R.id.layout);
          et_pinglun = inflate.findViewById(R.id.et_pinglun);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseDialog.dismiss();
            }
        });

        et_pinglun.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (et_pinglun.getText().toString().length()>0){
                    send.setBackgroundResource(R.mipmap.feijihong);
                    send.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pinglun();
                        }
                    });
                }else {
                    send.setBackgroundResource(R.mipmap.feijihui);
                    send.setOnClickListener(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
               /* if (et_pinglun.getText().toString().length()>0){
                    send.setBackgroundResource(R.mipmap.feijihong);
                    send.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pinglun();
                        }
                    });
                }else {
                    send.setBackgroundResource(R.mipmap.feijihui);
                    send.setOnClickListener(null);
                }*/
            }
        });
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        null_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseDialog.dismiss();
            }
        });
        mZan.postDelayed(new Runnable() {
            @Override
            public void run() {
                baseDialog.showKeyboard(et_pinglun);
            }
        },300);
            baseDialog = new BaseDialog(getActivity(), inflate, Gravity.BOTTOM);
            baseDialog.setOnDismissListener(dialog -> s = et_pinglun.getText().toString());
            baseDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    et_pinglun.setText(s);
                    et_pinglun.setSelection(s.length());
                    baseDialog.showKeyboard(et_pinglun);
                }
            });
        baseDialog.show();

    }

    private void pinglun() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("publishedId",publishedId);
        Log.e("list","ed----"+publishedId);
        hashMap.put("publisherId",publisherId);
        hashMap.put("foodId",foodId);
        Log.e("list","er----"+publisherId);

        hashMap.put("remarkContent",et_pinglun.getText().toString());

            hashMap.put("userId",DeeSpUtil.getInstance().getString("userId"));
            hashMap.put("ticket",DeeSpUtil.getInstance().getString("ticket"));

        DataService.getHomeService().remarkPublished(hashMap)
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
                            showToast("已发布");
                            s = "";
                            et_pinglun.setText("");
                            baseDialog.dismiss();
                            initTalk();
                        } else {
                            showToast(baseResult.getResult_msg());
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
