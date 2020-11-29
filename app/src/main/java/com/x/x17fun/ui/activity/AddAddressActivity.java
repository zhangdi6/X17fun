package com.x.x17fun.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiChildrenInfo;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.baidu.mapapi.utils.DistanceUtil;
import com.fashare.stack_layout.StackLayout;
import com.fashare.stack_layout.transformer.AngleTransformer;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.x.x17fun.R;
import com.x.x17fun.adapter.HomeListAdapter;
import com.x.x17fun.adapter.UserAboutAddressAdapter;
import com.x.x17fun.adapter.UserNearAddressAdapter;
import com.x.x17fun.adapter.UserNearAddressAdapter2;
import com.x.x17fun.base.App;
import com.x.x17fun.base.BaseActivity;
import com.x.x17fun.custom.statubar.StatusBarCompat;
import com.x.x17fun.data.DataService;
import com.x.x17fun.entity.BaseEntity;
import com.x.x17fun.entity.BaseResult;
import com.x.x17fun.entity.TodayEntity;
import com.x.x17fun.ui.fragment.OrderPayFragment;
import com.x.x17fun.utils.AdiUtils;
import com.x.x17fun.utils.DeeSpUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AddAddressActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBack;
    private MapView mMap;
    private BaiduMap map;
    /**
     * 奥特莱斯
     */
    private TextView tv_checked_address;
    /**
     * 北京市朝阳区望京路8号
     */
    private TextView tv_address_desc;
    /**
     * 修改地址 >
     */
    private TextView btn_update_address;
    private ConstraintLayout address_layout;
    /**
     * 选择收货地址 >
     */
    private TextView btn_choose;
    private RelativeLayout choose_layout;
    /**
     * 详细地址，如1层101室
     */
    private EditText et_menpai;
    private RadioGroup tag_radio;
    /**
     * 请填写收货人姓名
     */
    private EditText et_name;
    /**
     * 请填写手机号码
     */
    private EditText et_phone;
    /**
     * 保存地址
     */
    private TextView btn_save;
    private LinearLayout setting_layout;
    /**
     * 请输入收货地址
     */
    private EditText et_address;
    /**
     * 北京
     */
    private TextView btn_address;
    private RecyclerView rlv_near;
    private RelativeLayout seearch_layout;
    private RadioGroup sex_radio;
    private SmartRefreshLayout mSmart;
    private UserNearAddressAdapter2 nearAdapter;
    private MyLocationListener myListener = new MyLocationListener();
    private MyLocationListener2 myListener2 = new MyLocationListener2();
    private GeoCoder geoCoder;
    private float x_temp01;
    private float y_temp01;
    private float x_temp02;
    private float y_temp02;
    private SuggestionSearch mSuggestionSearch;
    private TextView cancle;
    private RecyclerView rlv_about;
    private UserAboutAddressAdapter addressAdapter;
    //自己的位置
    private LatLng latLng;

    //选的位置
    private double aLong = 0;
    private double aLat = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        StatusBarCompat.changeToLightStatusBar(this);

        setContentView(R.layout.activity_add_address);
        initView();
        initData();

    }


    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location){

            //mapView 销毁后不在处理新接收的位置
            if (location == null || map == null){
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(location.getDirection()).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            map.setMyLocationData(locData);
             latLng = new LatLng(location.getLatitude(), location.getLongitude());
             if (aLong == 0 && aLat ==0){
                 aLong = location.getLongitude();
                 aLat = location.getLatitude();
             }
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取地址相关的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

            List<Poi> poiList = location.getPoiList();
            btn_address.setText(location.getCity());
            ArrayList<BaseEntity> objects2 = new ArrayList<>();
            for (int i = 0; i < poiList.size(); i++) {
                objects2.add(new BaseEntity(poiList.get(i).getName(),poiList.get(i).getAddr(),""));
            }
            if (nearAdapter!=null){
                nearAdapter.addData(objects2);
            }else {
                nearAdapter = new UserNearAddressAdapter2();
                rlv_near.setLayoutManager(new LinearLayoutManager(AddAddressActivity.this));
                rlv_near.setHasFixedSize(true);
                rlv_near.setNestedScrollingEnabled(false);
                rlv_near.setAdapter(nearAdapter);
                nearAdapter.onClick(position -> {
                    seearch_layout.setVisibility(View.GONE);
                    setting_layout.setVisibility(View.VISIBLE);
                    choose_layout.setVisibility(View.GONE);
                    address_layout.setVisibility(View.VISIBLE);
                    tv_checked_address.setText(poiList.get(position).getName());
                    tv_address_desc.setText(poiList.get(position).getAddr());
                });
            }
        }
    }
    public class MyLocationListener2 extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location){

            //mapView 销毁后不在处理新接收的位置
            if (location == null || map == null){
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(location.getDirection()).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            map.setMyLocationData(locData);
            latLng = new LatLng(location.getLatitude(), location.getLongitude());
            if (aLong == 0 && aLat ==0){
                aLong = location.getLongitude();
                aLat = location.getLatitude();
            }
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取地址相关的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

            List<Poi> poiList = location.getPoiList();

            ArrayList<BaseEntity> objects2 = new ArrayList<>();
            for (int i = 0; i < poiList.size(); i++) {
                objects2.add(new BaseEntity(poiList.get(i).getName(),poiList.get(i).getAddr(),""));
            }
            if (nearAdapter!=null){
                nearAdapter.addData(objects2);
            }else {
                nearAdapter = new UserNearAddressAdapter2();
                rlv_near.setLayoutManager(new LinearLayoutManager(AddAddressActivity.this));
                rlv_near.setHasFixedSize(true);
                rlv_near.setNestedScrollingEnabled(false);
                rlv_near.setAdapter(nearAdapter);
                nearAdapter.onClick(position -> {
                    seearch_layout.setVisibility(View.GONE);
                    setting_layout.setVisibility(View.VISIBLE);
                    choose_layout.setVisibility(View.GONE);
                    address_layout.setVisibility(View.VISIBLE);
                    tv_checked_address.setText(poiList.get(position).getName());
                    tv_address_desc.setText(poiList.get(position).getAddr());
                });
            }
        }
    }
    @Override
    protected void initView() {

        mBack = (ImageView) findViewById(R.id.back);
        mBack.setOnClickListener(this);
        mMap = (MapView) findViewById(R.id.map);
        map = mMap.getMap();
        //显示卫星图层
        map.setMapType(BaiduMap.MAP_TYPE_NORMAL);

        map.setMyLocationEnabled(true);
        MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING,
                true, null, Color.parseColor("#50D8EEFB"), 0);
        map.setMyLocationConfiguration(config);

        tv_checked_address = (TextView) findViewById(R.id.tv_checked_address);
        tv_address_desc = (TextView) findViewById(R.id.tv_address_desc);
        btn_update_address = (TextView) findViewById(R.id.btn_update_address);
        btn_update_address.setOnClickListener(this);
        address_layout = (ConstraintLayout) findViewById(R.id.address_layout);
        btn_choose = (TextView) findViewById(R.id.btn_choose);
        btn_choose.setOnClickListener(this);
        choose_layout = (RelativeLayout) findViewById(R.id.choose_layout);
        et_menpai = (EditText) findViewById(R.id.et_menpai);
        tag_radio = (RadioGroup) findViewById(R.id.tag_radio);
        mSmart = (SmartRefreshLayout) findViewById(R.id.smart);
        et_name = (EditText) findViewById(R.id.et_name);
        et_phone = (EditText) findViewById(R.id.et_phone);
        btn_save = (TextView) findViewById(R.id.btn_save);
        sex_radio = (RadioGroup) findViewById(R.id.sex_radio);
        btn_save.setOnClickListener(this);
        setting_layout = (LinearLayout) findViewById(R.id.setting_layout);
        et_address = (EditText) findViewById(R.id.et_address);
        btn_address = (TextView) findViewById(R.id.btn_address);
        cancle = (TextView) findViewById(R.id.cancle);
        rlv_about = (RecyclerView) findViewById(R.id.rlv_about);
        btn_address.setOnClickListener(this);
        rlv_near = (RecyclerView) findViewById(R.id.rlv_near);
        seearch_layout = (RelativeLayout) findViewById(R.id.seearch_layout);
        map.setMapStatus(MapStatusUpdateFactory.zoomTo(16));

        sex_radio.check(R.id.btn1);

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initData() {

        //通过LocationClientOption设置LocationClient相关参数
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
          //设置locationClientOption
        App.mLocationClient.setLocOption(option);
        App.mLocationClient.registerLocationListener(myListener);
        App.mLocationClient.requestLocation();


        map.setOnMapStatusChangeListener(statusChangeListener);
        map.setOnMapClickListener(clicklistener);
        map.setOnMarkerClickListener(listener);

        //创建检索实例
        mSuggestionSearch = SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(searchListener);
        //实例化一个地理编码查询对象
        geoCoder = GeoCoder.newInstance();
        //设置地理编码查询监听
        geoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
            }
            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
                //获取点击的坐标地址
                tv_address_desc.setText(reverseGeoCodeResult.getAddress());
                aLong = reverseGeoCodeResult.getLocation().longitude;
                aLat = reverseGeoCodeResult.getLocation().latitude;
            }
        });
        rlv_near.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x_temp01 = event.getX();
                        y_temp01 = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        x_temp02 = event.getX();
                        y_temp02 = event.getY();
                        direction();
                        break;
                }
                return false;
            }
        });

        et_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_address.getText().toString().trim().length()==0){
                    rlv_near.setVisibility(View.GONE);
                    rlv_about.setVisibility(View.GONE);
                    if (mSmart.getVisibility()==View.GONE){
                        mSmart.setVisibility(View.VISIBLE);
                        mSmart.setBackgroundColor(Color.parseColor("#80000000"));
                    }else{
                        rlv_about.setVisibility(View.GONE);
                    }
                }
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_address.setText("");
                mSmart.setVisibility(View.GONE);
                cancle.setVisibility(View.GONE);
            }
        });
        et_address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length()==0){
                    rlv_about.setVisibility(View.GONE);
                    mSmart.setBackgroundColor(Color.parseColor("#80000000"));

                }else{
                    cancle.setVisibility(View.VISIBLE);
                    mSmart.setBackgroundColor(Color.WHITE);
                    mSmart.setVisibility(View.VISIBLE);
                    mSmart.setBackgroundColor(Color.WHITE);
                    rlv_about.setVisibility(View.VISIBLE);
                    rlv_about.setVisibility(View.VISIBLE);

                    mSuggestionSearch.requestSuggestion(new SuggestionSearchOption()
                            .city(btn_address.getText().toString())
                            .citylimit(true)
                            .keyword(s.toString()));
                }
            }
        });
    }

    //附近列表滑动事件
    private void direction() {
        float h = x_temp02 - x_temp01;
        float l = y_temp02 - y_temp01;

        if (Math.abs(h) > Math.abs(l)) {
            // 向左
            if (h > 0){
            } else {
           // 移动了x_temp02-x_temp01

            }
        } else {
            if (l > 0)// 向下
                {
                 rlv_near.setVisibility(View.GONE);
            } else {

            }
        }
    }

    BaiduMap.OnMapStatusChangeListener statusChangeListener = new BaiduMap.OnMapStatusChangeListener() {
        /**
         * 手势操作地图，设置地图状态等操作导致地图状态开始改变。
         *
         * @param status 地图状态改变开始时的地图状态
         */
        @Override
        public void onMapStatusChangeStart(MapStatus status) {
            if (map.getLocationConfiguration().locationMode == MyLocationConfiguration.LocationMode.FOLLOWING){
                MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL,
                        true, null, Color.parseColor("#50D8EEFB"), 0);
                map.setMyLocationConfiguration(config);
            }

        }
        /**
         * 手势操作地图，设置地图状态等操作导致地图状态开始改变。
         *
         * @param status 地图状态改变开始时的地图状态
         *
         * @param reason 地图状态改变的原因
         */
        @Override
        public void onMapStatusChangeStart(MapStatus status, int reason) {
            if (map.getLocationConfiguration().locationMode == MyLocationConfiguration.LocationMode.FOLLOWING){
                MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL,
                        true, null, Color.parseColor("#50D8EEFB"), 0);
                map.setMyLocationConfiguration(config);
            }
        }
        /**
         * 地图状态变化中
         *
         * @param status 当前地图状态
         */
        @Override
        public void onMapStatusChange(MapStatus status) {

        }
        /**
         * 地图状态改变结束
         *
         * @param status 地图状态改变结束后的地图状态
         */
        @Override
        public void onMapStatusChangeFinish(MapStatus status) {

        }
        };
    BaiduMap.OnMapClickListener clicklistener = new BaiduMap.OnMapClickListener() {
        /**
         * 地图单击事件回调函数
         *
         * @param point 点击的地理坐标
         */
        @Override
        public void onMapClick(LatLng point) {

        }

        /**
         * 地图内 Poi 单击事件回调函数
         *
         * @param mapPoi 点击的 poi 信息
         */
        @Override
        public void onMapPoiClick(MapPoi mapPoi) {
            seearch_layout.setVisibility(View.GONE);
            setting_layout.setVisibility(View.VISIBLE);
            choose_layout.setVisibility(View.GONE);
            rlv_near.setVisibility(View.VISIBLE);
            address_layout.setVisibility(View.VISIBLE);
            tv_checked_address.setText(mapPoi.getName());
            LatLng latLng = mapPoi.getPosition();
            aLong = mapPoi.getPosition().longitude;
            aLat = mapPoi.getPosition().latitude;
            //设置反地理编码位置坐标
            ReverseGeoCodeOption op = new ReverseGeoCodeOption();
            op.location(latLng);
            //发起反地理编码请求
            geoCoder.reverseGeoCode(op);


        }
    };
    BaiduMap.OnMarkerClickListener listener = new BaiduMap.OnMarkerClickListener() {
        /**
         * 地图 Marker 覆盖物点击事件监听函数
         * @param marker 被点击的 marker
         */
        @Override
        public boolean onMarkerClick(Marker marker) {

            return false;
        }

    };

    OnGetSuggestionResultListener searchListener = new OnGetSuggestionResultListener() {
        @Override
        public void onGetSuggestionResult(SuggestionResult suggestionResult) {
            //处理sug检索结果
            if (et_address.getText().toString().length()==0){
                mSmart.setBackgroundColor(Color.parseColor("#80000000"));
                rlv_about.setVisibility(View.GONE);
            }else{
                mSmart.setVisibility(View.VISIBLE);
                mSmart.setBackgroundColor(Color.WHITE);
                rlv_about.setVisibility(View.VISIBLE);
            }
            mSmart.setVisibility(View.VISIBLE);
            rlv_near.setVisibility(View.GONE);

            if (suggestionResult.getAllSuggestions()!=null && suggestionResult.getAllSuggestions().size()>0){
                if (addressAdapter!=null){
                    ArrayList<BaseEntity> objects = new ArrayList<>();
                    List<SuggestionResult.SuggestionInfo> infos = suggestionResult.getAllSuggestions();
                    for (int i = 0; i < infos.size(); i++) {
                        SuggestionResult.SuggestionInfo suggestionInfo = infos.get(i);
                        float distance = (float) DistanceUtil.getDistance(suggestionInfo.getPt(), latLng);
                        float cc = distance/100; //得到10.51==
                        int d = Math.round(cc);//四舍五入是11
                        float e=d/(float)10;//把10 也强转为float型的，再让10除以它==
                        objects.add(new BaseEntity(suggestionInfo.getKey(),suggestionInfo.getCity()+suggestionInfo.getDistrict()
                                ,e+"km",suggestionInfo.getPt()));
                    }
                    addressAdapter.addData(objects);
                }else{
                    rlv_about.setLayoutManager(new LinearLayoutManager(AddAddressActivity.this));
                    addressAdapter = new UserAboutAddressAdapter();
                    ArrayList<BaseEntity> objects = new ArrayList<>();
                    List<SuggestionResult.SuggestionInfo> infos = suggestionResult.getAllSuggestions();
                    for (int i = 0; i < infos.size(); i++) {
                        SuggestionResult.SuggestionInfo suggestionInfo = infos.get(i);
                        float distance = (float) DistanceUtil.getDistance(suggestionInfo.getPt(), latLng);
                        float cc = distance/100; //得到10.51==
                        int d = Math.round(cc);//四舍五入是11
                        float e=d/(float)10;//把10 也强转为float型的，再让10除以它==
                        objects.add(new BaseEntity(suggestionInfo.getKey(),suggestionInfo.getCity()+suggestionInfo.getDistrict()
                                ,e+"km",suggestionInfo.getPt()));
                    }
                    rlv_about.setAdapter(addressAdapter);
                    addressAdapter.addData(objects);
                    addressAdapter.onClick(new UserAboutAddressAdapter.onItemClickListener() {
                        @Override
                        public void onClick(int position) {
                            et_address.setText("");
                            rlv_about.setVisibility(View.GONE);
                            mSmart.setVisibility(View.GONE);
                            seearch_layout.setVisibility(View.GONE);
                            setting_layout.setVisibility(View.VISIBLE);
                            address_layout.setVisibility(View.VISIBLE);
                            aLong = addressAdapter.mList.get(position).getPt().longitude;
                            aLat = addressAdapter.mList.get(position).getPt().latitude;
                            //设置反地理编码位置坐标
                            ReverseGeoCodeOption op = new ReverseGeoCodeOption();
                            op.location(addressAdapter.mList.get(position).getPt());
                            //发起反地理编码请求
                            geoCoder.reverseGeoCode(op);
                            tv_checked_address.setText(addressAdapter.mList.get(position).getTitle());
                        }
                    });
                }
            }else{
                showToast("暂时没有发现有关位置信息哦");
            }
        }
    };

    @Override
    protected void initLogic() {

        if (choose_layout.getVisibility() == View.VISIBLE){
            showToast("请先选择收货地址");
            return;
        }
        if (et_menpai.getText().toString().trim().equals("")){
            showToast("请填写详细地址");
            return;
        }
        if (et_name.getText().toString().trim().equals("")){
            showToast("请填写联系人姓名");
            return;
        }
        if (et_phone.getText().toString().trim().equals("")){
            showToast("请填写联系人手机号");
            return;
        }
        if (tv_checked_address.getText().toString().trim().equals("")){
            showToast("请选择配送区域");
            return;
        }

        HashMap<String, Object> hashMap = new HashMap<>();
        String userId = DeeSpUtil.getInstance().getString("userId");
        if (userId!=null && !userId.equals("")){
            hashMap.put("userId",userId);
            hashMap.put("ticket",DeeSpUtil.getInstance().getString("ticket"));
        }
        int checkedRadioButtonId = tag_radio.getCheckedRadioButtonId();
        switch (checkedRadioButtonId){
            case R.id.btn_jia:
                hashMap.put("receiveTag","家");
                break;
            case R.id.btn_company:
                hashMap.put("receiveTag","公司");
                break;
            case R.id.btn_school:
                hashMap.put("receiveTag","学校");
                break;
        }
        int radioButtonId = sex_radio.getCheckedRadioButtonId();
        switch (radioButtonId){
            case R.id.btn1:
                hashMap.put("receiverGender","1");
                break;
            case R.id.btn2:
                hashMap.put("receiverGender","2");
                break;
        }

        hashMap.put("receiverName",et_name.getText().toString().trim());
        hashMap.put("receiverAera",tv_checked_address.getText().toString().trim());
        hashMap.put("receiverAddress", et_menpai.getText().toString().trim());
        hashMap.put("receiverPhone",et_phone.getText().toString().trim());
        hashMap.put("aeraLongtitude",aLong);
        hashMap.put("aeraLatitude",aLat);

        DataService.getHomeService().addreceivermeg(hashMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult baseResult) {
                        Log.e("result",baseResult.toString());
                        if (baseResult.getResult_code() == 0) {
                            showToast("保存成功");
                            finish();
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.back:
                onBackPressedSupport();
                break;
            case R.id.btn_update_address:
                seearch_layout.setVisibility(View.VISIBLE);
                rlv_near.setVisibility(View.VISIBLE);
                setting_layout.setVisibility(View.GONE);
                break;
            case R.id.btn_choose:
                seearch_layout.setVisibility(View.VISIBLE);
                setting_layout.setVisibility(View.GONE);
                choose_layout.setVisibility(View.GONE);
                rlv_near.setVisibility(View.VISIBLE);
                address_layout.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_save:
                initLogic();
                break;
            case R.id.btn_address:
                Intent intent = new Intent(this, SelectCityActivity.class);
                startActivityForResult(intent,200);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200){
            if (resultCode == 400){
                btn_address.setText(data.getStringExtra("address"));
                App.mLocationClient.unRegisterLocationListener(myListener);
                App.mLocationClient.registerLocationListener(myListener2);
            }
        }
    }

    @Override
    protected void onResume() {
        mMap.onResume();
        super.onResume();
    }

    @Override
    public void onBackPressedSupport() {
        if (seearch_layout.getVisibility() == View.VISIBLE){
            seearch_layout.setVisibility(View.GONE);
            choose_layout.setVisibility(View.VISIBLE);
            setting_layout.setVisibility(View.VISIBLE);
            address_layout.setVisibility(View.GONE);
        }else{
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        mMap.onDestroy();
        map.setMyLocationEnabled(false);
        App.mLocationClient.setLocOption(null);
        geoCoder.destroy();
        mSuggestionSearch.destroy();
        App.mLocationClient.unRegisterLocationListener(myListener);
        super.onDestroy();

    }

    @Override
    protected void onPause() {

        mMap.onPause();
        super.onPause();
    }
}
