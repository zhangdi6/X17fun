package com.x.x17fun.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.Poi;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.baidu.mapapi.utils.DistanceUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.x.x17fun.R;
import com.x.x17fun.adapter.UserAboutAddressAdapter;
import com.x.x17fun.adapter.UserNearAddressAdapter;
import com.x.x17fun.adapter.UserReceiveAddressAdapter;
import com.x.x17fun.base.App;
import com.x.x17fun.base.BaseActivity;
import com.x.x17fun.custom.statubar.StatusBarCompat;
import com.x.x17fun.data.DataService;
import com.x.x17fun.entity.BaseEntity;
import com.x.x17fun.entity.BaseResult;
import com.x.x17fun.entity.MyAddessEntity;
import com.x.x17fun.utils.AdiUtils;
import com.x.x17fun.utils.DeeSpUtil;
import com.x.x17fun.utils.ResUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ReceiveProductActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBack;
    /**
     * 新增地址
     */
    private TextView mAdd;
    /**
     * 北京
     */
    private TextView mBtnAddress;
    /**
     * 望京SOHO
     */
    private TextView mSureAddress;
    /**
     * 重新定位
     */
    private TextView mRetryAddress;
    /**
     * 展开全部地址
     */
    private TextView mOpenTv;
    private LinearLayout mOpenRlvLayout;
    private RecyclerView mRlvMyReciver;
    private RelativeLayout mReceiveLayout;
    /**
     * 暂无收货地址
     */
    private TextView mHadNoAddress;
    private RecyclerView mRlvNearAddress;
    private RecyclerView mRlvAboutSearch;
    private RelativeLayout mSearchLayout;
    /**
     * 请输入收货地址
     */
    private EditText mEtAddress;
    private SmartRefreshLayout mSmart;
    private UserReceiveAddressAdapter receiveAddressAdapter;
    private UserNearAddressAdapter nearAdapter;
    private UserAboutAddressAdapter aboutAdapter;
    private MyLocationListener myListener = new MyLocationListener();
    private MyLocationListener2 myListener2 = new MyLocationListener2();
    private LatLng latLng;

    private SuggestionSearch mSuggestionSearch;
    private boolean isRetry = false;
    private boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        StatusBarCompat.changeToLightStatusBar(this);
        App.mLocationClient.registerLocationListener(myListener);
        setContentView(R.layout.activity_receive_product);
        initView();
        initData();
        initLogic();
    }


    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location){
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取地址相关的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

            List<Poi> poiList = location.getPoiList();
            if (!isRetry){
                mSureAddress.setText(poiList.get(0).getName());

            }
            mRetryAddress.setText("重新定位");
            if (location.getCity().contains("市")){
                mBtnAddress.setText(location.getCity().replace("市",""));
            }else{
                mBtnAddress.setText(location.getCity());
            }

            latLng = new LatLng(location.getLatitude(), location.getLongitude());
            ArrayList<BaseEntity> objects2 = new ArrayList<>();
            for (int i = 0; i < poiList.size(); i++) {
                objects2.add(new BaseEntity(poiList.get(i).getName()));
            }
            if (nearAdapter!=null){
                nearAdapter.addData(objects2);
            }else {
                nearAdapter = new UserNearAddressAdapter();
                mRlvNearAddress.setLayoutManager(new LinearLayoutManager(ReceiveProductActivity.this));
                mRlvNearAddress.setHasFixedSize(true);
                mRlvNearAddress.setNestedScrollingEnabled(false);
                mRlvNearAddress.setAdapter(nearAdapter);
                nearAdapter.onClick(new UserNearAddressAdapter.onItemClickListener() {
                    @Override
                    public void onClick(int position) {
                        Intent intent = new Intent();
                        intent.putExtra("address",nearAdapter.mList.get(position).getTitle());
                        mSureAddress .setText(nearAdapter.mList.get(position).getTitle());
                        intent.putExtra("address2",location.getStreet());
                        intent.putExtra("la",location.getLatitude()+"");
                        intent.putExtra("long",location.getLongitude()+""+"");
                        setResult(700,intent);
                        finish();
                    }
                });
            }

            mRetryAddress.setOnClickListener(v -> App.mLocationClient.requestLocation());
        }
    }
    public class MyLocationListener2 extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location){
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取地址相关的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

            List<Poi> poiList = location.getPoiList();
            if (!isRetry){
                mSureAddress.setText(poiList.get(0).getName());
            }
            /*mRetryAddress.setText("重新定位");*/
            latLng = new LatLng(location.getLatitude(), location.getLongitude());
            ArrayList<BaseEntity> objects2 = new ArrayList<>();
            for (int i = 0; i < poiList.size(); i++) {
                objects2.add(new BaseEntity(poiList.get(i).getName()));
            }
            if (nearAdapter!=null){
                nearAdapter.addData(objects2);
            }else {
                nearAdapter = new UserNearAddressAdapter();
                mRlvNearAddress.setLayoutManager(new LinearLayoutManager(ReceiveProductActivity.this));
                mRlvNearAddress.setHasFixedSize(true);
                mRlvNearAddress.setNestedScrollingEnabled(false);
                mRlvNearAddress.setAdapter(nearAdapter);
                nearAdapter.onClick(new UserNearAddressAdapter.onItemClickListener() {
                    @Override
                    public void onClick(int position) {
                        Intent intent = new Intent();
                        intent.putExtra("address",nearAdapter.mList.get(position).getTitle());
                        mSureAddress.setText(nearAdapter.mList.get(position).getTitle());
                        intent.putExtra("address2",location.getStreet());
                        intent.putExtra("la",location.getLatitude()+"");
                        intent.putExtra("long",location.getLongitude()+""+"");
                        setResult(700,intent);
                        finish();
                    }
                });
            }

            mRetryAddress.setOnClickListener(v -> App.mLocationClient.requestLocation());
        }
    }

    @Override
    protected void initView() {

        mBack = (ImageView) findViewById(R.id.back);
        mBack.setOnClickListener(this);
        mAdd = (TextView) findViewById(R.id.add);
        mAdd.setOnClickListener(this);
        mBtnAddress = (TextView) findViewById(R.id.btn_address);
        mBtnAddress.setOnClickListener(this);
        mSureAddress = (TextView) findViewById(R.id.sure_address);
        mRetryAddress = (TextView) findViewById(R.id.retry_address);
        mOpenTv = (TextView) findViewById(R.id.open_tv);
        mOpenRlvLayout = (LinearLayout) findViewById(R.id.open_rlv_layout);
        mOpenRlvLayout.setOnClickListener(this);
        mRlvMyReciver = (RecyclerView) findViewById(R.id.rlv_my_reciver);
        mReceiveLayout = (RelativeLayout) findViewById(R.id.receive_layout);
        mHadNoAddress = (TextView) findViewById(R.id.had_no_address);
        mRlvNearAddress = (RecyclerView) findViewById(R.id.rlv_near_address);
        mRlvAboutSearch = (RecyclerView) findViewById(R.id.rlv_about_search);
        mSearchLayout = (RelativeLayout) findViewById(R.id.search_layout);
        mEtAddress = (EditText) findViewById(R.id.et_address);
        mSmart = (SmartRefreshLayout) findViewById(R.id.smart);

        if (getIntent()!=null && getIntent().getStringExtra("city")!=null){
            mRetryAddress.setText("重新定位");
            mSureAddress.setText(getIntent().getStringExtra("city"));
            isRetry = true ;
            App.mLocationClient.requestLocation();
        }else{
            isRetry = false ;
            App.mLocationClient.requestLocation();
        }

        mRetryAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRetryAddress.setText("定位中...");
               mRetryAddress.postDelayed(new Runnable() {
                   @Override
                   public void run() {
                       isRetry = false;
                       App.mLocationClient.stop();
                       App.mLocationClient.restart();
                   }
               },1000);
            }
        });
        //创建检索实例
        mSuggestionSearch = SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(searchListener);
    }

    @Override
    protected void initData() {
        HashMap<String, Object> hashMap = new HashMap<>();
        String userId = DeeSpUtil.getInstance().getString("userId");
        if (userId!=null && !userId.equals("")){
            hashMap.put("userId",userId);
            hashMap.put("ticket",DeeSpUtil.getInstance().getString("ticket"));
        }
        DataService.getHomeService().myReceive(hashMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult<MyAddessEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<MyAddessEntity> baseResult) {
                        Log.e("result",baseResult.toString());
                        if (baseResult.getResult_code() == 0) {
                            if (baseResult.getData()!=null && baseResult.getData().getReceiveList()!=null
                            && baseResult.getData().getReceiveList().size()>0){
                                mHadNoAddress.setVisibility(View.GONE);
                                mRlvMyReciver.setVisibility(View.VISIBLE);

                                if (baseResult.getData().getReceiveList().size()>5){
                                    mOpenRlvLayout.setVisibility(View.VISIBLE);

                                    ArrayList<MyAddessEntity.ReceiveListBean> objects = new ArrayList<>();
                                    for (int i = 0; i < 5; i++) {
                                        List<MyAddessEntity.ReceiveListBean> receiveList = baseResult.getData().getReceiveList();
                                        objects.add(receiveList.get(i));
                                    }
                                    receiveAddressAdapter = new UserReceiveAddressAdapter();
                                    mRlvMyReciver.setLayoutManager(new LinearLayoutManager(ReceiveProductActivity.this));

                                    mRlvMyReciver.setHasFixedSize(true);
                                    mRlvMyReciver.setNestedScrollingEnabled(false);
                                    mRlvMyReciver.setAdapter(receiveAddressAdapter);
                                    receiveAddressAdapter.addData(objects);
                                    mOpenRlvLayout.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if(isOpen){
                                                receiveAddressAdapter.addData(objects);
                                                mOpenTv.setText("展开全部地址");
                                                mOpenTv.setCompoundDrawablesWithIntrinsicBounds(null,null,
                                                        ResUtils.getDrawable(R.mipmap.down),null);

                                                isOpen = false ;
                                            }else{
                                                receiveAddressAdapter.addData(baseResult.getData().getReceiveList());
                                                mOpenTv.setText("收起全部地址");
                                                mOpenTv.setCompoundDrawablesWithIntrinsicBounds(null,null,
                                                        ResUtils.getDrawable(R.mipmap.up),null);
                                                isOpen = true ;
                                            }
                                        }
                                    });
                                }else{
                                    mOpenRlvLayout.setVisibility(View.GONE);
                                    receiveAddressAdapter = new UserReceiveAddressAdapter();
                                    mRlvMyReciver.setLayoutManager(new LinearLayoutManager(ReceiveProductActivity.this));

                                    mRlvMyReciver.setHasFixedSize(true);
                                    mRlvMyReciver.setNestedScrollingEnabled(false);
                                    mRlvMyReciver.setAdapter(receiveAddressAdapter);
                                    receiveAddressAdapter.addData(baseResult.getData().getReceiveList());
                                }

                                receiveAddressAdapter.onClick(new UserReceiveAddressAdapter.onItemClickListener() {
                                    @Override
                                    public void onClick(int position) {
                                        Intent intent = new Intent();
                                        mSureAddress.setText(receiveAddressAdapter.mList.get(position).getReceiverAera());
                                        intent.putExtra("address",receiveAddressAdapter.mList.get(position).getReceiverAera());
                                        intent.putExtra("address2",receiveAddressAdapter.mList.get(position).getReceiverAddress());
                                        intent.putExtra("la",receiveAddressAdapter.mList.get(position).getAeraLatitude());
                                        intent.putExtra("long",receiveAddressAdapter.mList.get(position).getAeraLongtitude());
                                        setResult(700,intent);
                                        finish();
                                    }
                                });

                            }else{
                                mHadNoAddress.setVisibility(View.VISIBLE);
                                mRlvMyReciver.setVisibility(View.GONE);
                            }
                        }else{
                            mHadNoAddress.setVisibility(View.VISIBLE);
                            mRlvMyReciver.setVisibility(View.GONE);
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
   /*     ArrayList<BaseEntity> objects = new ArrayList<>();
        objects.add(new BaseEntity(0,"望京SOHO塔二C座506室","欧阳下单","先生","17630360083",""));
        objects.add(new BaseEntity(1,"望京SOHO塔二C座506室","欧阳下单","先生","17630360083","家"));
        objects.add(new BaseEntity(2,"望京SOHO塔二C座506室","欧阳下单","先生","17630360083","公司"));
        objects.add(new BaseEntity(3,"望京SOHO塔二C座506室","欧阳下单","先生","17630360083","学校"));
        objects.add(new BaseEntity(0,"望京SOHO塔二C座506室","欧阳下单","先生","17630360083",""));*/



    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();

       /* App.mLocationClient.registerLocationListener(myListener2);
        App.mLocationClient.requestLocation();*/
    }

    @Override
    protected void onDestroy() {
        App.mLocationClient.unRegisterLocationListener(myListener);
        mSuggestionSearch.destroy();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode==200 && resultCode == 400){
            mBtnAddress.setText(data.getStringExtra("address"));
            App.mLocationClient.unRegisterLocationListener(myListener);
            App.mLocationClient.registerLocationListener(myListener2);
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void initLogic() {

        mEtAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mAdd.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mEtAddress.getText().toString().trim().length()==0){
                            mSearchLayout.setVisibility(View.GONE);
                        }else{
                            mSearchLayout.setVisibility(View.VISIBLE);
                            String s1 = mEtAddress.getText().toString();
                            mSuggestionSearch.requestSuggestion(new SuggestionSearchOption()
                                    .city(mBtnAddress.getText().toString())
                                    .citylimit(true)
                                    .keyword(s.toString()));
                        }

                    }
                },500);
            }
        });
    }

    OnGetSuggestionResultListener searchListener = new OnGetSuggestionResultListener() {
        @Override
        public void onGetSuggestionResult(SuggestionResult suggestionResult) {
            if (suggestionResult.getAllSuggestions()!=null && suggestionResult.getAllSuggestions().size()>0){
                ArrayList<BaseEntity> objects = new ArrayList<>();
                List<SuggestionResult.SuggestionInfo> infos = suggestionResult.getAllSuggestions();
                if (aboutAdapter!=null){
                    for (int i = 0; i < infos.size(); i++) {
                        SuggestionResult.SuggestionInfo suggestionInfo = infos.get(i);
                        float distance = (float) DistanceUtil.getDistance(suggestionInfo.getPt(), latLng);
                        float cc = distance/100; //得到10.51==
                        int d = Math.round(cc);//四舍五入是11
                        float e=d/(float)10;//把10 也强转为float型的，再让10除以它==
                        objects.add(new BaseEntity(suggestionInfo.getKey(),suggestionInfo.getCity()+suggestionInfo.getDistrict()
                                ,e+"km",suggestionInfo.getPt()));
                    }
                    aboutAdapter.addData(objects);
                }else{
                    for (int i = 0; i < infos.size(); i++) {
                        SuggestionResult.SuggestionInfo suggestionInfo = infos.get(i);
                        float distance = (float) DistanceUtil.getDistance(suggestionInfo.getPt(), latLng);
                        float cc = distance/100; //得到10.51==
                        int d = Math.round(cc);//四舍五入是11
                        float e=d/(float)10;//把10 也强转为float型的，再让10除以它==
                        objects.add(new BaseEntity(suggestionInfo.getKey(),suggestionInfo.getCity()+suggestionInfo.getDistrict()
                                ,e+"km",suggestionInfo.getPt()));
                    }
                    aboutAdapter = new UserAboutAddressAdapter();
                    mRlvAboutSearch.setLayoutManager(new LinearLayoutManager(ReceiveProductActivity.this));

                    mRlvAboutSearch.setHasFixedSize(true);
                    mRlvAboutSearch.setNestedScrollingEnabled(false);
                    mRlvAboutSearch.setAdapter(aboutAdapter);
                    aboutAdapter.addData(objects);
                    aboutAdapter.onClick(new UserAboutAddressAdapter.onItemClickListener() {
                        @Override
                        public void onClick(int position) {
                            Intent intent = new Intent();
                            mSureAddress.setText(aboutAdapter.mList.get(position).getTitle());
                            intent.putExtra("address",aboutAdapter.mList.get(position).getTitle());
                            intent.putExtra("address2",aboutAdapter.mList.get(position).getTitle());
                            LatLng pt = aboutAdapter.mList.get(position).getPt();
                            intent.putExtra("la",pt.latitude+"");
                            intent.putExtra("long",pt.longitude+"");
                            setResult(700,intent);
                            finish();
                        }
                    });
                }

            }else{
                showToast("暂时没有发现有关位置信息哦");
            }
        }
    };
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.back:
                onBackPressedSupport();
                break;
            case R.id.add:
                startToActivity(this,AddAddressActivity.class);
                break;
            case R.id.btn_address:
                Intent intent = new Intent(this, SelectCityActivity.class);
                startActivityForResult(intent,200);
                break;
            case R.id.open_rlv_layout:
                break;
        }
    }

    @Override
    public void onBackPressedSupport() {
        Intent intent1 = new Intent();
        intent1.putExtra("address",mSureAddress.getText().toString());
        setResult(800,intent1);
        finish();
    }
}
