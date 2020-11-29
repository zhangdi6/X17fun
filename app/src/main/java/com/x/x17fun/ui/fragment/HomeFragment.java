package com.x.x17fun.ui.fragment;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;
import com.fashare.stack_layout.StackLayout;
import com.fashare.stack_layout.transformer.AlphaTransformer;
import com.fashare.stack_layout.transformer.AngleTransformer;
import com.weigan.loopview.LoopView;
import com.weigan.loopview.OnItemSelectedListener;
import com.x.x17fun.R;
import com.x.x17fun.adapter.HomeListAdapter;
import com.x.x17fun.adapter.HomeListAdapter2;
import com.x.x17fun.adapter.MsgRlvAdapter;
import com.x.x17fun.base.App;
import com.x.x17fun.base.BaseDialog;
import com.x.x17fun.base.BaseFragment;
import com.x.x17fun.base.EventMessage;
import com.x.x17fun.custom.MyStackPageTransformer;
import com.x.x17fun.custom.statubar.StatusBarCompat;
import com.x.x17fun.custom.vague.VagueDialog;
import com.x.x17fun.data.DataService;
import com.x.x17fun.entity.BaseResult;
import com.x.x17fun.entity.TodayEntity;
import com.x.x17fun.entity.TomorrowEntity;
import com.x.x17fun.entity.UnReadListEntity;
import com.x.x17fun.ui.activity.ChatActivity;
import com.x.x17fun.ui.activity.MainActivity;
import com.x.x17fun.ui.activity.MsgActivity;
import com.x.x17fun.ui.activity.ProductDetailActivity;
import com.x.x17fun.ui.activity.ReceiveProductActivity;
import com.x.x17fun.ui.activity.SalerProfileActivity;
import com.x.x17fun.ui.activity.UserVerifyActivity;
import com.x.x17fun.utils.AdiUtils;
import com.x.x17fun.utils.DeeSpUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment {


    private TextView today;
    private TextView tomorrow;
    private RelativeLayout ball;
    private RelativeLayout ball2;
    private RelativeLayout ball_all;
    private ConstraintLayout cardLayout;
    private StackLayout cardList;
    private HomeListAdapter mAdapter;
    private int a = 0 ;
    private ImageView newAdd;
    private HomeListAdapter2 mAdapter2;
    private MyLocationListener myListener = new MyLocationListener();
    private int b = 0;
    private TextView tb_title;
    private String mHeadimg;
    /*private ConstraintLayout load_fail;*/
    private TextView tv_search;
    private AnimationSet set1;
    private AnimationSet set2;
    private TextView repeart;
    private int loadingpage = 1;
    private LatLng latLng = new LatLng(0, 0);
    private ImageView btn_chat;
    private TextView tag2;
    private BaseDialog basDialog;


    public static HomeFragment newInstance(){
        return new HomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_home2, container, false);
        /*EventBus.getDefault().register(this);*/

        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        option.setCoorType("bd09ll");
        option.setScanSpan(1000);
        option.setOpenGps(true);
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setLocationNotify(true);
        option.setIgnoreKillProcess(true);
        option.setIsNeedLocationPoiList(true);
        option.SetIgnoreCacheException(false);
        option.setWifiCacheTimeOut(5*60*1000);
        option.setEnableSimulateGps(false);
        option.setNeedNewVersionRgc(true);
        App.mLocationClient.setLocOption(option);
        App.mLocationClient.registerLocationListener(myListener);
        App.mLocationClient.start();

        initView(inflate);
        initLogic();
        initData();
        /*initData();*/
        /*load(1);*/

        return inflate;
    }

    private void initUnRead() {
        HashMap<String, Object> hashMap = new HashMap<>();
        String userId = DeeSpUtil.getInstance().getString("userId");
        hashMap.put("userId",userId);
        hashMap.put("ticket",DeeSpUtil.getInstance().getString("ticket"));
        DataService.getHomeService().unreadMessages(hashMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult<UnReadListEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<UnReadListEntity> baseResult) {
                        Log.e("result",baseResult.toString());
                        if (baseResult.getResult_code() == 0) {
                            if (baseResult.getData()!=null && baseResult.getData().getUnReadRecordsBean()!=null){
                                if (baseResult.getData().getUnReadRecordsBean().size()>0){
                                    int a = 0;
                                    for (int i = 0; i < baseResult.getData().getUnReadRecordsBean().size(); i++) {
                                        UnReadListEntity.UnReadRecordBean unReadRecordBean = baseResult.getData().getUnReadRecordsBean().get(i);
                                        a = a +unReadRecordBean.getUnreadCounts();
                                    }
                                    if (a>99){
                                        tag2.setText("99+");
                                    }else{
                                        tag2.setText(a+"");
                                    }


                                    tag2.setVisibility(View.VISIBLE);
                                }else{
                                    tag2.setVisibility(View.GONE);
                                }
                            }
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        AdiUtils.showToast(e.getMessage());

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location){
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取地址相关的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
             latLng = new LatLng(location.getLatitude(), location.getLongitude());
            if (location.getPoiList()!=null && location.getPoiList().size()>0){
                tb_title.setText(location.getPoiList().get(0).getName());
            }else{
                tb_title.setText("定位中···");
                App.mLocationClient.requestLocation();
            }

        }
    }
    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        StatusBarCompat.setStatusBarColor(getActivity(), Color.parseColor("#333333"));
        StatusBarCompat.cancelLightStatusBar(getActivity());
        initUnRead();
        String isRefresh = DeeSpUtil.getInstance().getString("isRefresh");
        if (isRefresh !=null && isRefresh.equals("1")){
            if (today.isEnabled()){
                initDataTomorrow();
            }else{
                initData();
            }
            DeeSpUtil.getInstance().putString("isRefresh","0");
        }

    }

    private void initLogic() {
        startAnim1();
        startAnim2();
        today.setEnabled(false);
        today.setBackgroundResource(R.drawable.conner_yellow);

        today.setOnClickListener(v -> {
            today.setEnabled(false);
            tomorrow.setEnabled(true);
            today.setBackgroundResource(R.drawable.conner_yellow);
            tomorrow.setBackgroundResource(R.drawable.conner_white);
            load(1);
        });

        tomorrow.setOnClickListener(v -> {
            tomorrow.setEnabled(false);
            today.setEnabled(true);
            tomorrow.setBackgroundResource(R.drawable.conner_yellow);
            today.setBackgroundResource(R.drawable.conner_white);
            load(2);
        });

        repeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (today.isEnabled()){
                    load(2);
                }else{
                    load(1);
                }

            }
        });


    }

    private void initView(View inflate) {

        tb_title = inflate.findViewById(R.id.tb_title);
         btn_chat = inflate.findViewById(R.id.btn_chat);
        ImageView btn_menu = inflate.findViewById(R.id.btn_menu);
         tag2 = inflate.findViewById(R.id.tag2);
         /*load_fail = inflate.findViewById(R.id.load_fail);*/
        repeart = inflate.findViewById(R.id.repeart);

        tb_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ReceiveProductActivity.class);
                intent.putExtra("city",tb_title.getText().toString());
                startActivityForResult(intent,200);
            }
        });
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new EventMessage(1,""));
            }
        });

        btn_chat.setOnClickListener(v -> {
          startToActivity(getContext(), MsgActivity.class);
        });
        tb_title.setText("定位中···");

        cardLayout = inflate.findViewById(R.id.card_layout);
        cardList = inflate.findViewById(R.id.card_list);
        today = inflate.findViewById(R.id.today);
        tomorrow = inflate.findViewById(R.id.tomorrow);
        tv_search = inflate.findViewById(R.id.tv_search);
        ball = inflate.findViewById(R.id.ball);
        ball2 = inflate.findViewById(R.id.ball2);
        ball_all = inflate.findViewById(R.id.ball_all);
        newAdd = inflate.findViewById(R.id.new_add);

        newAdd.setOnClickListener(v -> initPushDialoig());

        cardList.setVisibility(View.GONE);
        /*load_fail.setVisibility(View.GONE);*/
    }
  /*  @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiver(EventMessage event){
        if (event.getType() == 2){
            tb_title.setText((String)event.getData());
        }
    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        /*EventBus.getDefault().unregister(this);*/
    }

    private void initPushDialoig(){


        new VagueDialog(getActivity(), R.layout.dialog_push_type)
                .setOnDismiss(dialog -> dialog.dismiss())
                .setOnItemClickListener((adapterView, view1, i, l) -> {
                            switch (i) {
                                case 0:
                                    if (App.userMsg()!=null &&App.userMsg().getUserInfo()!=null
                                            && App.userMsg().getUserInfo().getUserStatus().equals("1")){
                                        start(HopeFragment.newInstance());
                                    }else{
                                        showToast("您还未进行实名认证");
                                        startToActivity(getContext(), UserVerifyActivity.class);
                                    }
                                    break;
                                case 1:
                                    if (App.userMsg()!=null &&App.userMsg().getUserInfo()!=null
                                    && App.userMsg().getUserInfo().getUserStatus().equals("1")){
                                        start(PushMealFragment.newInstance());
                                    }else{
                                        showToast("您还未进行实名认证");
                                        startToActivity(getContext(), UserVerifyActivity.class);
                                    }
                                    break;
                                case 2:
                                    showToast("功能开发中，敬请期待");
                                    break;
                                default:
                                    break;
                            }
                        }).show();

    }
    private void initData() {


        HashMap<String, Object> hashMap = new HashMap<>();
        String userId = DeeSpUtil.getInstance().getString("userId");
        if (userId!=null && !userId.equals("")){
            hashMap.put("userId",userId);
            hashMap.put("ticket",DeeSpUtil.getInstance().getString("ticket"));
        }
        Observer<BaseResult<TodayEntity>> observer = new Observer<BaseResult<TodayEntity>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(BaseResult<TodayEntity> baseResult) {


                if (baseResult.getResult_code() == 0) {
                    Log.e("home", baseResult.toString());
                    if (baseResult.getData() != null && baseResult.getData().getTodayPbulishedList() != null
                            && baseResult.getData().getTodayPbulishedList().size() > 0) {
                        /*load_fail.setVisibility(View.GONE);*/
                        if (!today.isEnabled()) {
                            cardList.setVisibility(View.VISIBLE);
                            ball_all.setVisibility(View.GONE);
                            ball.clearAnimation();
                            ball2.clearAnimation();
                            mAdapter = new HomeListAdapter(latLng);
                            cardList.setAdapter(mAdapter);
                            cardList.addPageTransformer(new AlphaTransformer());
                            mAdapter.addData(baseResult.getData().getTodayPbulishedList());

                            cardList.setOnSwipeListener(new StackLayout.OnSwipeListener() {
                                @Override
                                public void onSwiped(View swipedView,
                                                     int swipedItemPos,
                                                     boolean isSwipeLeft,
                                                     int itemLeft) {
                                    // 少于5条, 加载更多
                                    if (itemLeft == 0) {
                                        /*load(1);*/
                                        cardList.setVisibility(View.GONE);
                                        /*load_fail.setVisibility(View.GONE);*/
                                        repeart.setVisibility(View.GONE);
                                        ball_all.setVisibility(View.VISIBLE);
                                        startAnim1();
                                        startAnim2();
                                        ball.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                cardList.setVisibility(View.GONE);
                                                ball_all.setVisibility(View.VISIBLE);
                                                tv_search.setText("暂无餐品，请稍后再试");
                                                ball.clearAnimation();
                                                ball2.clearAnimation();
                                                repeart.setVisibility(View.VISIBLE);

                                            }
                                        }, 2000);
                                    }
                                }
                            });
                            mAdapter.onClick(new HomeListAdapter.onItemClickListener() {
                                @Override
                                public void onClick(int position) {
                                    Intent intent = new Intent(getContext(), SalerProfileActivity.class);
                                    intent.putExtra("publisherId", mAdapter.mList.get(position).getUserId());
                                    startActivity(intent);
                                }

                                @Override
                                public void onItemClick(int position) {
                                    Intent intent = new Intent(getContext(), ProductDetailActivity.class);
                                    intent.putExtra("bean", mAdapter.mList.get(position));
                                    intent.putExtra("lat", latLng.latitude);
                                    intent.putExtra("long", latLng.longitude);
                                    intent.putExtra("type", 1);
                                    startActivity(intent);
                                }

                                @Override
                                public void onPayClick(int position) {
                                    if (App.userMsg() != null && App.userMsg().getUserInfo() != null
                                            && App.userMsg().getUserInfo().getUserStatus().equals("1")) {
                                        TodayEntity.TodayPbulishedListBean listBean = mAdapter.mList.get(position);
                                        String displayImg = listBean.getDisplayUrl();
                                        if (displayImg.contains(",")) {
                                            String[] img = displayImg.split(",");

                                            mHeadimg = img[0];
                                        } else {
                                            mHeadimg = displayImg;
                                        }
                                        start(OrderPayFragment.getInstance(0,
                                                listBean.getPortait(), listBean.getNickName(), listBean.getProductName(),
                                                mHeadimg, listBean.getSalePrice(), listBean.getCount(), listBean.getMyPublishedId()));
                                    } else {
                                        showToast("您还未进行实名认证");
                                        startToActivity(getContext(), UserVerifyActivity.class);
                                    }


                                }

                                @Override
                                public void onLoveClick(int position, boolean isSend) {
                                    TodayEntity.TodayPbulishedListBean bean =
                                            baseResult.getData().getTodayPbulishedList().get(position);
                                    String isAppreciate = bean.getIsAppreciate();
                                    if (bean.getIsAppreciate().equals("1")) {
                                        bean.setIsAppreciate("0");
                                        bean.setAppreciateCount(bean.getAppreciateCount() - 1);
                                        mAdapter.notifyDataSetChanged();
                                        if (isSend && !isAppreciate.equals(bean.getIsAppreciate())) {
                                            clickZan(bean.getMyPublishedId(), 1, position, bean.getFoodId());
                                        }
                                    } else {
                                        bean.setIsAppreciate("1");
                                        bean.setAppreciateCount(bean.getAppreciateCount() + 1);
                                        mAdapter.notifyDataSetChanged();
                                        if (isSend && !isAppreciate.equals(bean.getIsAppreciate())) {
                                            clickZan(bean.getMyPublishedId(), 1, position, bean.getFoodId());
                                        }
                                    }

                                }

                                @Override
                                public void onFocusClick(int position) {
                                    onFocus(position, 1);
                                }
                            });
                        }


                    } else {
                        /*load_fail.setVisibility(View.VISIBLE);*/
                        ball.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (!today.isEnabled()) {
                                    cardList.setVisibility(View.GONE);
                                    ball_all.setVisibility(View.VISIBLE);
                                    tv_search.setText("暂无餐品，请稍后再试");
                                    ball.clearAnimation();
                                    ball2.clearAnimation();
                                    repeart.setVisibility(View.VISIBLE);
                                }
                            }
                        }, 800);

                    }
                } else {
                    showToast(baseResult.getResult_msg());
                    /*load_fail.setVisibility(View.VISIBLE);*/
                    ball.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (!today.isEnabled()) {
                                cardList.setVisibility(View.GONE);
                                ball_all.setVisibility(View.VISIBLE);
                                tv_search.setText("暂无餐品，请稍后再试");
                                ball.clearAnimation();
                                ball2.clearAnimation();
                                repeart.setVisibility(View.VISIBLE);
                            }

                        }
                    }, 800);
                }
            }

            @Override
            public void onError(Throwable e) {
                /*load_fail.setVisibility(View.VISIBLE);*/
                showToast(e.getMessage());
                ball.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!today.isEnabled()) {
                            cardList.setVisibility(View.GONE);
                            ball_all.setVisibility(View.VISIBLE);
                            tv_search.setText("暂无餐品，请稍后再试");
                            ball.clearAnimation();
                            ball2.clearAnimation();
                            repeart.setVisibility(View.VISIBLE);
                        }

                    }
                }, 800);
            }

            @Override
            public void onComplete() {

            }
        };
        DataService.getHomeService().getTodayPublishedList(hashMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);


    }

    private void initRlv1(){

    }
    private void initRlv2(){
        mAdapter2 = new HomeListAdapter2(latLng);
        cardList.setAdapter(mAdapter2);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("activity","back");
        if (requestCode == 200) {
            if (resultCode == 700){
                Log.e("activity","succcuess");
                tb_title.setText(data.getStringExtra("address"));
                App.mLocationClient.unRegisterLocationListener(myListener);
            }else if (resultCode == 800){
                tb_title.setText(data.getStringExtra("address"));
                App.mLocationClient.unRegisterLocationListener(myListener);
            }
        }
    }

    private void initDataTomorrow() {


        HashMap<String, Object> hashMap = new HashMap<>();
        String userId = DeeSpUtil.getInstance().getString("userId");
        if (userId!=null && !userId.equals("")){
            hashMap.put("userId",userId);
            hashMap.put("ticket",DeeSpUtil.getInstance().getString("ticket"));
        }
        DataService.getHomeService().getTomorrowPublishedList(hashMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult<TomorrowEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<TomorrowEntity> baseResult) {


                        if (baseResult.getResult_code() == 0) {

                            Log.e("home",baseResult.toString());
                            if (baseResult.getData()!=null && baseResult.getData().getTomorrowPublishedList()!=null
                            && baseResult.getData().getTomorrowPublishedList().size()>0){
                                /*load_fail.setVisibility(View.GONE);*/

                                if (!tomorrow.isEnabled()){
                                    cardList.setVisibility(View.VISIBLE);
                                    ball_all.setVisibility(View.GONE);
                                    mAdapter2 = new HomeListAdapter2(latLng);
                                    cardList.setAdapter(mAdapter2);
                                    mAdapter2.addData(baseResult.getData().getTomorrowPublishedList());
                                    cardList.setOnSwipeListener(new StackLayout.OnSwipeListener() {
                                        @Override
                                        public void onSwiped(View swipedView, int swipedItemPos, boolean isSwipeLeft, int itemLeft) {
                                            // 少于5条, 加载更多
                                            if(itemLeft == 0 ){
                                                /*load(2);*/
                                                cardList.setVisibility(View.GONE);
                                                /*load_fail.setVisibility(View.GONE);*/
                                                repeart.setVisibility(View.GONE);
                                                ball_all.setVisibility(View.VISIBLE);
                                                startAnim1();
                                                startAnim2();
                                                ball.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        cardList.setVisibility(View.GONE);
                                                        ball_all.setVisibility(View.VISIBLE);
                                                        tv_search.setText("暂无餐品，请稍后再试");
                                                        ball.clearAnimation();
                                                        ball2.clearAnimation();
                                                        repeart.setVisibility(View.VISIBLE);

                                                    }
                                                },2000);
                                            }
                                        }
                                    });
                                    mAdapter2.onClick(new HomeListAdapter2.onItemClickListener() {
                                        @Override
                                        public void onClick(int position) {
                                            Intent intent = new Intent(getContext(), SalerProfileActivity.class);
                                            intent.putExtra("publisherId",mAdapter2.mList.get(position).getUserId());
                                            startActivity(intent);
                                        }

                                        @Override
                                        public void onItemClick(int position) {
                                            Intent intent = new Intent(getContext(), ProductDetailActivity.class);
                                            intent.putExtra("bean",mAdapter2.mList.get(position));
                                            intent.putExtra("type",2);
                                            intent.putExtra("lat",latLng.latitude);
                                            intent.putExtra("long",latLng.longitude);
                                            startActivity(intent);
                                        }

                                        @Override
                                        public void onPayClick(int position) {
                                            if (App.userMsg()!=null &&App.userMsg().getUserInfo()!=null
                                                    && App.userMsg().getUserInfo().getUserStatus().equals("1")){
                                                TomorrowEntity.TomorrowPublishedListBean listBean = mAdapter2.mList.get(position);
                                                String displayImg = listBean.getDisplayUrl();
                                                if (displayImg.contains(",")){
                                                    String[] img = displayImg.split(",");
                                                    mHeadimg = img[0];
                                                }else{
                                                    mHeadimg = displayImg;
                                                }
                                                start(OrderPayFragment.getInstance(0,
                                                        listBean.getPortait(),listBean.getNickName(),listBean.getProductName(),
                                                        mHeadimg,listBean.getSalePrice(),listBean.getCount(),listBean.getMyPublishedId()));
                                            }else{
                                                showToast("您还未进行实名认证");
                                                startToActivity(getContext(), UserVerifyActivity.class);
                                            }


                                        }

                                        @Override
                                        public void onLoveClick(int position, boolean isSend) {
                                            TomorrowEntity.TomorrowPublishedListBean bean = baseResult.getData().getTomorrowPublishedList().get(position);
                                            String isAppreciate = bean.getIsAppreciate();
                                            if (bean.getIsAppreciate().equals("1")){
                                                bean.setIsAppreciate("0");
                                                bean.setAppreciateCount(bean.getAppreciateCount()-1);
                                                mAdapter2.notifyDataSetChanged();
                                                if (isSend && !isAppreciate.equals(bean.getIsAppreciate())){
                                                    clickZan(bean.getMyPublishedId(),2,position,bean.getFoodId());
                                                }
                                            }else{
                                                bean.setIsAppreciate("1");
                                                bean.setAppreciateCount(bean.getAppreciateCount()+1);
                                                mAdapter2.notifyDataSetChanged();
                                                if (isSend && !isAppreciate.equals(bean.getIsAppreciate())){
                                                    clickZan(bean.getMyPublishedId(),2,position,bean.getFoodId());
                                                }
                                            }
                                        }


                                        @Override
                                        public void onFocusClick(int position) {
                                            onFocus(position,2);
                                        }
                                    });
                                }

                            }else{
                                /*load_fail.setVisibility(View.VISIBLE);*/
                                ball.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (!tomorrow.isEnabled()){
                                            cardList.setVisibility(View.GONE);
                                            ball_all.setVisibility(View.VISIBLE);
                                            tv_search.setText("暂无餐品，请稍后再试");
                                            ball.clearAnimation();
                                            ball2.clearAnimation();
                                            repeart.setVisibility(View.VISIBLE);
                                        }
                                    }
                                },800);

                            }

                        } else {
                            /*load_fail.setVisibility(View.VISIBLE);*/
                            ball.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (!tomorrow.isEnabled()){
                                        cardList.setVisibility(View.GONE);
                                        ball_all.setVisibility(View.VISIBLE);
                                        tv_search.setText("暂无餐品，请稍后再试");
                                        ball.clearAnimation();
                                        ball2.clearAnimation();
                                        repeart.setVisibility(View.VISIBLE);
                                    }

                                }
                            },800);

                            showToast(baseResult.getResult_msg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        /*load_fail.setVisibility(View.VISIBLE);*/
                        ball.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (!tomorrow.isEnabled()){
                                    cardList.setVisibility(View.GONE);
                                    ball_all.setVisibility(View.VISIBLE);
                                    tv_search.setText("暂无餐品，请稍后再试");
                                    ball.clearAnimation();
                                    ball2.clearAnimation();
                                    repeart.setVisibility(View.VISIBLE);
                                }

                            }
                        },800);

                        showToast(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void onFocus(int position , int type) {

        HashMap<String, Object> hashMap = new HashMap<>();
        if (type==1) {
            hashMap.put("focusUserId",mAdapter.mList.get(position).getUserId());
        }else{
            hashMap.put("focusUserId",mAdapter2.mList.get(position).getUserId());
        }
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

                            if (type==1){
                                TodayEntity.TodayPbulishedListBean pbulishedListBean = mAdapter.mList.get(position);
                                pbulishedListBean.setIsFocus("1");
                                mAdapter.mList.set(position,pbulishedListBean);
                                mAdapter.notifyDataSetChanged();
                            }else{
                                TomorrowEntity.TomorrowPublishedListBean pbulishedListBean = mAdapter2.mList.get(position);
                                pbulishedListBean.setIsFocus("1");
                                mAdapter2.mList.set(position,pbulishedListBean);
                                mAdapter2.notifyDataSetChanged();
                            }
                            EventBus.getDefault().post(new EventMessage(2,null));
                        } else {
                            /*showToast(baseResult.getResult_msg());*/
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


    private void clickZan(String Id , int type ,int position,String foodId) {

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("publishedId",Id);
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
                            if (type==1){

                            }else{

                            }
                            EventBus.getDefault().post(new EventMessage(2,null));
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

    private void load(int type) {

        if (type==1){
            if (mAdapter!=null){
                mAdapter.mList.clear();
            }
            cardList.setVisibility(View.GONE);
            /*load_fail.setVisibility(View.GONE);*/
            repeart.setVisibility(View.GONE);
            ball_all.setVisibility(View.VISIBLE);
            startAnim1();
            startAnim2();
            initData();
           /* ball.postDelayed(new Runnable() {
                @Override
                public void run() {

                    ball_all.setVisibility(View.GONE);
                    ball.clearAnimation();
                    ball2.clearAnimation();
                    a=0;

                }
            },2000);*/
        }else{
            if (mAdapter2!=null){
                mAdapter2.mList.clear();
            }
            cardList.setVisibility(View.GONE);
            /*load_fail.setVisibility(View.GONE);*/
            repeart.setVisibility(View.GONE);
            ball_all.setVisibility(View.VISIBLE);
            startAnim1();
            startAnim2();
            initDataTomorrow();
          /*  ball.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ball_all.setVisibility(View.GONE);
                    ball.clearAnimation();
                    ball2.clearAnimation();
                    b=0;

                }
            },2000);*/
        }

    }

    private void startAnim2() {
        if (set1!=null){
            ball2.startAnimation(set1);
        }else{
            set1 = new AnimationSet(true);
            //缩放动画，以中心从1.4倍放大到1.8倍
            ScaleAnimation scaleAnimation = new ScaleAnimation(1.4f, 1.8f, 1.4f, 1.8f,
                    ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                    ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
            //渐变动画
            AlphaAnimation alphaAnimation = new AlphaAnimation(0.5f, 0.1f);
            scaleAnimation.setDuration(800);
            scaleAnimation.setRepeatCount(Animation.INFINITE);
            alphaAnimation.setRepeatCount(Animation.INFINITE);
            set1.setDuration(800);
            set1.addAnimation(scaleAnimation);
            set1.addAnimation(alphaAnimation);
            set1.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    tv_search.setVisibility(View.VISIBLE);
                    tv_search.setText("搜索中...");
                }

                @Override
                public void onAnimationEnd(Animation animation) {

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            ball2.startAnimation(set1);
        }


    }

    private void startAnim1() {
        if (set2!=null){
            ball.startAnimation(set2);
        }else {
            set2 = new AnimationSet(true);
            //缩放动画，以中心从原始放大到1.4倍
            ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 1.4f, 1.0f, 1.4f,
                    ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                    ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
            //渐变动画
            AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.5f);
            scaleAnimation.setDuration(800);
            scaleAnimation.setRepeatCount(Animation.INFINITE);
            alphaAnimation.setRepeatCount(Animation.INFINITE);
            set2.setDuration(800);
            set2.addAnimation(scaleAnimation);
            set2.addAnimation(alphaAnimation);
            set2.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    tv_search.setVisibility(View.VISIBLE);
                    tv_search.setText("搜索中...");
                }

                @Override
                public void onAnimationEnd(Animation animation) {

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            ball.startAnimation(set2);
        }

    }
}
