package com.x.x17fun.ui.fragment;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.weigan.loopview.LoopView;
import com.weigan.loopview.OnItemSelectedListener;
import com.x.x17fun.R;
import com.x.x17fun.adapter.ImageAdapter;
import com.x.x17fun.adapter.PushMealChanelAdapter;
import com.x.x17fun.adapter.UserReceiveAddressAdapter;
import com.x.x17fun.base.App;
import com.x.x17fun.base.BaseDialog;
import com.x.x17fun.base.BaseFragment;
import com.x.x17fun.base.EventMessage;
import com.x.x17fun.custom.FullyGridLayoutManager;
import com.x.x17fun.custom.GridImageAdapter;
import com.x.x17fun.custom.citypicker.AddressPickerView;
import com.x.x17fun.custom.statubar.StatusBarCompat;
import com.x.x17fun.data.DataService;
import com.x.x17fun.entity.BaseEntity;
import com.x.x17fun.entity.BaseResult;
import com.x.x17fun.entity.MyPushedEntity;
import com.x.x17fun.entity.RePEntity;
import com.x.x17fun.ui.activity.LoginActivity;
import com.x.x17fun.ui.activity.ReceiveProductActivity;
import com.x.x17fun.utils.AdiUtils;
import com.x.x17fun.utils.CalenderUtilss;
import com.x.x17fun.utils.DeeSpUtil;
import com.x.x17fun.utils.ResUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * A simple {@link Fragment} subclass.
 */
public class RePushMealFragment extends BaseFragment {


    private TextView ok;
    private EditText meal_desc;
    private EditText meal_name;
    private RecyclerView rlv_meal;
    private RecyclerView rlv_bottom;
    private ImageView back;
    private GridImageAdapter adapter;

    //这个集合用来放媒体文件，包括图片，视频，音频都可以
    private List<LocalMedia> selectList = new ArrayList<>();
    private int chooseMode = PictureMimeType.ofImage();
    private int themeId;
    private PushMealChanelAdapter chanelAdapter;
    private BaseDialog countDialog;
    private String countStr = "";
    private BaseDialog pushDialog;
    private String yuanStr = "";
    private String jiaoStr = "";
    private BaseDialog priceDialog;
    private String dayStr = "";
    private String timeStr = "";
    private String millonStr = "";
    private BaseDialog timeDialog;
    private BaseDialog timeDialog2;
    private Dialog addressDialog;
    //配送地点
    private String real_address;
    //下架时间
    private String real_gone_time;
    //配送时间
    private String real_push_time;
    private UserReceiveAddressAdapter receiveAddressAdapter;
    private RecyclerView address;
    private boolean ispush = false;
    private String real_area = "";
    private String aLong = "";
    private String aLa = "";
    private BaseDialog loadingDialog;
    private TextView title;
    private ImageView remote;
    /*private MyPushedEntity.UserPublishedListBean bean ;*/
    private String foodId = "";


    public static RePushMealFragment newInstance(String foodId){
        RePushMealFragment rePushMealFragment = new RePushMealFragment();
        Bundle bundle = new Bundle();
        bundle.putString("foodId",foodId);
        rePushMealFragment.setArguments(bundle);
        return rePushMealFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_push_meal, container, false);
        /*EventBus.getDefault().register(this);*/
        //themeId必须设置
        themeId = R.style.picture_default_style;
        Bundle arguments = getArguments();
        foodId = arguments.getString("foodId");
        ispush = false;
        initView(inflate);
        initRv();

        return inflate;


    }

    private void initRv() {
        HashMap<String, Object> hashMap = new HashMap<>();
        String userId = DeeSpUtil.getInstance().getString("userId");
        hashMap.put("userId",userId);
        hashMap.put("ticket",DeeSpUtil.getInstance().getString("ticket"));
        hashMap.put("foodId",foodId);
        DataService.getHomeService().foodDetail(hashMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult<RePEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<RePEntity> baseResult) {
                        Log.e("result",baseResult.toString());
                        if (baseResult.getResult_code() == 0 && baseResult.getData()!=null && baseResult.getData().getFoodDetail()!=null) {
                            RePEntity.FoodDetailBean bean = baseResult.getData().getFoodDetail();
                            meal_name.setText(bean.getFoodName());
                            meal_desc.setText(bean.getFoodTag());
                            meal_name.setTextColor(ResUtils.getColor(getActivity(),R.color.tv666));
                            meal_desc.setTextColor(ResUtils.getColor(getActivity(),R.color.tv666));
                            meal_name.setFocusable(false);
                            meal_desc.setFocusable(false);
                            String displayUrl = bean.getDisplayUrl();
                            ArrayList<String> objects = new ArrayList<>();
                            Log.e("name1",displayUrl);
                            if (displayUrl.contains(",")){
                                String[] split = displayUrl.split(",");
                                for (int i = 0; i < split.length; i++) {
                                    objects.add(split[i]);
                                    Log.e("name2",split[i]);
                                }
                            }else{
                                objects.add(displayUrl);
                                Log.e("name3",displayUrl);
                            }
                            ImageAdapter imageAdapter = new ImageAdapter();
                            rlv_meal.setAdapter(imageAdapter);
                            imageAdapter.addData(objects);
                            rlv_meal.setHasFixedSize(true);
                            rlv_meal.setNestedScrollingEnabled(false);
                            initRv2();
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

    private void initRv2() {


        ArrayList<BaseEntity> objects = new ArrayList<>();


        objects.add(new BaseEntity(R.mipmap.jine,"价格","请设置价格"));
        objects.add(new BaseEntity(R.mipmap.shulaing,"数量","请设置数量"));
        objects.add(new BaseEntity(R.mipmap.shijian,"下架时间","请设置时间"));
        objects.add(new BaseEntity(R.mipmap.shijian,"配送时间","请设置时间"));
        objects.add(new BaseEntity(R.mipmap.weizhi,"配送地点","请设置地点"));
        chanelAdapter = new PushMealChanelAdapter();
         rlv_bottom.setLayoutManager(new LinearLayoutManager(getContext()));
         rlv_bottom.setAdapter(chanelAdapter);
         rlv_bottom.setHasFixedSize(true);
         rlv_bottom.setNestedScrollingEnabled(false);
         chanelAdapter.addData(objects);


         chanelAdapter.onClick(new PushMealChanelAdapter.onItemClickListener() {
             @Override
             public void onClick(int position) {
                 switch (position){
                     case 0:
                         initPriceDialog();
                         break;
                     case 1:
                         initCountDialog();
                         break;
                     case 2:
                         initTimeDialog();
                         break;
                     case 3:
                         initTimeDialog2();
                         break;
                     case 4:
                         /*initAddressDialog();*/
                         Intent intent = new Intent(getContext(), ReceiveProductActivity.class);
                         startActivityForResult(intent,100);
                         break;
                 }
             }
         });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100){
            if (resultCode==700){
                real_area = data.getStringExtra("address");
                real_address = data.getStringExtra("address2");
                aLa = data.getStringExtra("la");
                aLong = data.getStringExtra("long");
                chanelAdapter.changePosition(4,real_area);
            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (address!=null){

        }
    }


    private void initPushDialog() {
        if ("".equals(meal_name.getText().toString().trim())){
            showToast("请输入商品名称");
            return;
        }
        if ("".equals(meal_desc.getText().toString().trim())){
            showToast("请输入商品描述");
            return;
        }
        if ("请设置价格".equals(chanelAdapter.mList.get(0).getSubtitle())){
            showToast("请设置价格");
            return;
        }
        if ("请设置数量".equals(chanelAdapter.mList.get(1).getSubtitle())){
            showToast("请设置数量");
            return;
        }
        if ("请设置时间".equals(chanelAdapter.mList.get(2).getSubtitle())){
            showToast("请设置下架时间");
            return;
        }
        if ("请设置价格".equals(chanelAdapter.mList.get(3).getSubtitle())){
            showToast("请设置配送价格");
            return;
        }
        if ("请设置地点".equals(chanelAdapter.mList.get(4).getSubtitle())){
            showToast("请设置配送地点");
            return;
        }
       /* if (selectList==null || selectList.size()==0){
            showToast("请上传商品图片");
            return;
        }*/
        View inflate = LayoutInflater.from(_mActivity).inflate(R.layout.dialog_push, null);
        ImageView wechat = inflate.findViewById(R.id.wechat);
        ImageView fanquan = inflate.findViewById(R.id.fanquan);
        TextView cancle = inflate.findViewById(R.id.cancle);
        ImageView lianhe = inflate.findViewById(R.id.lianhe);
        if (pushDialog==null){
            pushDialog = new BaseDialog(_mActivity, inflate, Gravity.BOTTOM);
        }
        //发布到微信
        wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pushDialog.dismiss();
            }
        });
        //发布到饭圈
        fanquan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pushMeal();
            }
        });
        //联合发布
        lianhe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        pushDialog.show();

    }

    //发布到饭圈
    private void pushMeal() {

        ispush = true ;

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("deliveryAddress",real_address==null|| real_address.equals("")?real_area:real_address);
        hashMap.put("deliveryAera",real_area);
        hashMap.put("foodId",foodId);
        hashMap.put("deadLine",real_push_time);
        hashMap.put("deliveryTime",real_gone_time);
            if (jiaoStr.equals("")||jiaoStr.equals("0")) {
                hashMap.put("price",yuanStr+".00");
            }else{
                hashMap.put("price",yuanStr+"."+jiaoStr);
            }

        hashMap.put("count",countStr);
        hashMap.put("productName",meal_name.getText().toString().trim());
        hashMap.put("productTag",meal_desc.getText().toString().trim());
        hashMap.put("aeraLongtitude",aLong);
        hashMap.put("aeraLatitude",aLa);
        pushDialog.dismiss();
        showLoadingPage();
        App.execute(()->{
            final BaseResult fr = App.webService().rePublishProduct(hashMap);
            getActivity().runOnUiThread(()->{

                if (fr.getResult_code()==0){

                    if (title!=null){
                        title.setText("发布成功");
                    }
                    ispush = false ;
                    if (isVisible()){
                        pop();
                    }
                }else if (fr.getResult_code()== -3){
                    ispush = false ;
                    AdiUtils.showToast("您的登录信息已经失效，请重新登录！");
                    Intent intent = new Intent(App.defaultApp(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    App.defaultApp().startActivity(intent);
                }else {
                    ispush = false ;
                    showToast(fr.getResult_msg());
                }
                meal_desc.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hideLoadingPage();
                    }
                },300);

            });
        });

    }

    private void hideLoadingPage() {
        if (remote!=null){
            remote.clearAnimation();
        }
        loadingDialog.dismiss();
    }

    private void showLoadingPage() {
        View inflate = LayoutInflater.from(_mActivity).inflate(R.layout.dialog_loading, null);
         title = inflate.findViewById(R.id.title);
         remote = inflate.findViewById(R.id.remote);

        Animation a = AnimationUtils.loadAnimation(getActivity() , R.anim.remote);
        LinearInterpolator lin = new LinearInterpolator();
        a.setInterpolator(lin);
        if (a != null){
            remote.startAnimation(a);
        }

        if (loadingDialog==null){
            loadingDialog = new BaseDialog(_mActivity, inflate, Gravity.CENTER);
        }
        loadingDialog.show();
    }

    private void initTimeDialog() {
        View inflate = LayoutInflater.from(_mActivity).inflate(R.layout.dialog_time, null);
        TextView cancle = inflate.findViewById(R.id.cancle);
        TextView sure = inflate.findViewById(R.id.sure);
        LoopView  loop = inflate.findViewById(R.id.loop);
        LoopView  loop2 = inflate.findViewById(R.id.loop2);
        LoopView  loop3 = inflate.findViewById(R.id.loop3);
        loop.setNotLoop();
        ArrayList<String> list = new ArrayList<>();
        ArrayList<String> list2 = new ArrayList<>();
        ArrayList<String> list3 = new ArrayList<>();
        sure.setOnClickListener(v -> {
                int selectedItem = loop.getSelectedItem();
                dayStr = list.get(selectedItem);

                int selectedItem2 = loop2.getSelectedItem();
                timeStr = list2.get(selectedItem2);
                int selectedItem3 = loop3.getSelectedItem();
                millonStr = list3.get(selectedItem3);
                chanelAdapter.changePosition(2,dayStr+"天"+timeStr+"时"+millonStr+"分");
                timeDialog .dismiss();

                if (selectedItem==0){
                    real_push_time = CalenderUtilss.getYear() + ((CalenderUtilss.getMonth()>9?
                            CalenderUtilss.getMonth():"0"+ CalenderUtilss.getMonth())+ ""
                            + (CalenderUtilss.getCurrentMonthDay()>9?CalenderUtilss.getCurrentMonthDay():
                            "0"+CalenderUtilss.getCurrentMonthDay()) + timeStr  + millonStr);
                }else{
                    Date date = new Date();
                    Calendar instance = new GregorianCalendar();
                    instance.setTime(date);
                    instance.add(Calendar.DATE,1);
                    real_push_time =
                            instance.get(Calendar.YEAR)+ "" + ((instance.get(Calendar.MONTH)+1)
                                    >9?(instance.get(Calendar.MONTH)+1):"0"+(instance.get(Calendar.MONTH)+1))+""
                                    + (instance.get(Calendar.DAY_OF_MONTH)>9?instance.get(Calendar.DAY_OF_MONTH):
                                    "0"+instance.get(Calendar.DAY_OF_MONTH)) + timeStr  + millonStr;
                }
        });

        list.add("今");
        list.add("明");
        for (int i = 0; i <=23; i++) {
            if (i<10){
                list2.add("0"+i);
            }else{
                list2.add(i+"");
            }

        }

        for (int i = 0; i <=59; i++) {
            if (i<10){
                list3.add("0"+i);
            }else{
                list3.add(i+"");
            }

        }

        loop.setItems(list);
        loop2.setItems(list2);
        loop3.setItems(list3);

        loop2.setInitPosition(12);
        loop3.setInitPosition(30);
        loop.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                dayStr = list.get(index);
            }
        });
        loop2.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                timeStr = list2.get(index);
            }
        });
        loop3.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                millonStr = list3.get(index);
            }
        });

        cancle.setOnClickListener(v -> timeDialog.dismiss());


        if (timeDialog==null){
            timeDialog = new BaseDialog(_mActivity, inflate, Gravity.BOTTOM);
        }
        timeDialog.show();
    }

    private void initTimeDialog2() {
        View inflate = LayoutInflater.from(_mActivity).inflate(R.layout.dialog_time, null);
        TextView cancle = inflate.findViewById(R.id.cancle);
        TextView sure = inflate.findViewById(R.id.sure);
        LoopView  loop = inflate.findViewById(R.id.loop);
        loop.setNotLoop();
        LoopView  loop2 = inflate.findViewById(R.id.loop2);
        LoopView  loop3 = inflate.findViewById(R.id.loop3);
        ArrayList<String> list = new ArrayList<>();
        ArrayList<String> list2 = new ArrayList<>();
        ArrayList<String> list3 = new ArrayList<>();
        sure.setOnClickListener(v -> {
                int selectedItem = loop.getSelectedItem();
                dayStr = list.get(selectedItem);
                int selectedItem2 = loop2.getSelectedItem();
                timeStr = list2.get(selectedItem2);
                int selectedItem3 = loop3.getSelectedItem();
                millonStr = list3.get(selectedItem3);
                chanelAdapter.changePosition(3,dayStr+"天"+timeStr+"时"+millonStr+"分");

            if (selectedItem == 0 ){
                real_gone_time = CalenderUtilss.getYear() + ((CalenderUtilss.getMonth()>9?
                        CalenderUtilss.getMonth():"0"+ CalenderUtilss.getMonth())+ ""
                        + (CalenderUtilss.getCurrentMonthDay()>9?CalenderUtilss.getCurrentMonthDay():
                        "0"+CalenderUtilss.getCurrentMonthDay()) + timeStr  + millonStr);
            }else{
                Date date = new Date();
                Calendar instance = new GregorianCalendar();
                instance.setTime(date);
                instance.add(Calendar.DATE,1);
                real_gone_time =
               instance.get(Calendar.YEAR)+ "" + ((instance.get(Calendar.MONTH)+1)
                        >9?(instance.get(Calendar.MONTH)+1):"0"+(instance.get(Calendar.MONTH)+1))+""
                        + (instance.get(Calendar.DAY_OF_MONTH)>9?instance.get(Calendar.DAY_OF_MONTH):
                        "0"+instance.get(Calendar.DAY_OF_MONTH)) + timeStr  + millonStr;


            }
                timeDialog2 .dismiss();
        });

        list.add("今");
        list.add("明");
        for (int i = 0; i <=23; i++) {
            if (i<10){
                list2.add("0"+i);
            }else {
                list2.add(i + "");
            }
        }
        for (int i = 0; i <=59; i++) {
            if (i<10){
                list3.add("0"+i);
            }else {
                list3.add(i + "");
            }
        }

        loop.setItems(list);
        loop2.setItems(list2);
        loop3.setItems(list3);
        loop2.setInitPosition(12);
        loop3.setInitPosition(30);
        loop.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                dayStr = list.get(index);
            }
        });
        loop2.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                timeStr = list2.get(index);
            }
        });
        loop3.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                millonStr = list3.get(index);
            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeDialog2.dismiss();
            }
        });


        if (timeDialog2==null){
            timeDialog2 = new BaseDialog(_mActivity, inflate, Gravity.BOTTOM);
        }
        timeDialog2.show();
    }

    private void initCountDialog() {


        View inflate = LayoutInflater.from(_mActivity).inflate(R.layout.dialog_count, null);
        TextView cancle = inflate.findViewById(R.id.cancle);
        TextView sure = inflate.findViewById(R.id.sure);
        LoopView  loop = inflate.findViewById(R.id.loop);

        ArrayList<String> list = new ArrayList<>();
        for (int i = 1; i <=4; i++) {
          list.add(i+"");
        }

        loop.setItems(list);
        loop.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                countStr = list.get(index);
            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDialog.dismiss();
            }
        });

        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedItem = loop.getSelectedItem();
                countStr = list.get(selectedItem);
                chanelAdapter.changePosition(1,countStr);
                countDialog.dismiss();
            }
        });
        if (countDialog==null){
            countDialog = new BaseDialog(_mActivity, inflate, Gravity.BOTTOM);
        }
        countDialog.show();
    }

    private void initPriceDialog() {
        View inflate = LayoutInflater.from(_mActivity).inflate(R.layout.dialog_price, null);
        TextView cancle = inflate.findViewById(R.id.cancle);
        TextView sure = inflate.findViewById(R.id.sure);
        LoopView  loop_yuan = inflate.findViewById(R.id.loop1);
        LoopView  loop_jiao = inflate.findViewById(R.id.loop2);

        ArrayList<String> list = new ArrayList<>();
        for (int i = 1; i <=100; i++) {
            list.add(i+"");
        }
        ArrayList<String> list2 = new ArrayList<>();
        for (int i = 0; i <= 9; i++) {
            list2.add(i+"");
        }

        loop_yuan.setItems(list);
        loop_jiao.setItems(list2);

        loop_yuan.setInitPosition(9);
        loop_jiao.setInitPosition(0);
        loop_yuan.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                yuanStr = list.get(index);
            }
        });
        loop_jiao.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                jiaoStr = list2.get(index);
            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priceDialog.dismiss();
            }
        });

        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    int selectedItem = loop_yuan.getSelectedItem();
                    yuanStr = list.get(selectedItem);
                    int selectedItem1 = loop_jiao.getSelectedItem();
                    jiaoStr = list2.get(selectedItem1);
                chanelAdapter.changePosition(0,yuanStr +"."+jiaoStr+"元");

                priceDialog.dismiss();
            }
        });
        if (priceDialog==null){
            priceDialog = new BaseDialog(_mActivity, inflate, Gravity.BOTTOM);
        }
        priceDialog.show();
    }

    private void initView(View inflate) {

         ok = inflate.findViewById(R.id.ok);
         meal_desc = inflate.findViewById(R.id.meal_desc);
         meal_name = inflate.findViewById(R.id.meal_name);
         rlv_meal = inflate.findViewById(R.id.rlv_meal);
         rlv_bottom = inflate.findViewById(R.id.rlv_bottom);
         back = inflate.findViewById(R.id.back);
        title = inflate.findViewById(R.id.title);


        rlv_meal.setLayoutManager(new GridLayoutManager(getContext(),3));

         back.setOnClickListener(v -> pop());
         ok.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if (ispush){
                     showToast("您有商品正在发布哦");
                 }else{
                    initPushDialog();
                 }
             }
         });
         title.setText("编辑商品");
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        AddressPickerView.mYwpAddressBean = null;
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        StatusBarCompat.setStatusBarColor(getActivity(), Color.WHITE);
        StatusBarCompat.changeToLightStatusBar(getActivity());

    }


   /* private GridImageAdapter.onAddPicClickListener onAddPicClickListener =
            new GridImageAdapter.onAddPicClickListener() {
                @Override
                public void onAddPicClick() {

                    if (EasyPermissions.hasPermissions(getContext().getApplicationContext(),
                            Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                        boolean mode = true;
                        if (mode) {
                            // 进入相册 以下是例子：不需要的api可以不写
                            // 进入相册 以下是例子：用不到的api可以不写
                            PictureSelector.create(getActivity())
                                    .openGallery(chooseMode)//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                                    .theme(themeId)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                                    .maxSelectNum(9)// 最大图片选择数量 int
                                    .minSelectNum(1)// 最小选择数量 int
                                    .imageSpanCount(4)// 每行显示个数 int
                                    .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                                    .previewImage(true)// 是否可预览图片 true or false
                                    .previewVideo(false)// 是否可预览视频 true or false
                                    .enablePreviewAudio(false) // 是否可播放音频 true or false
                                    .isCamera(true)// 是否显示拍照按钮 true or false
                                    .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                                    .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                                    .sizeMultiplier(1.0f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                                    .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                                    .enableCrop(false)// 是否裁剪 true or false
                                    .compress(true)// 是否压缩 true or false
                                    *//* .glideOverride(76,76)*//*// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                                    .withAspectRatio(1, 1)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                                    .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示 true or false
                                    .isGif(false)// 是否显示gif图片 true or false
                                    //.compressSavePath(getPath())//压缩图片保存地址
                                    .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                                    .circleDimmedLayer(false)// 是否圆形裁剪 true or false
                                    .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                                    .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                                    .openClickSound(false)// 是否开启点击声音 true or false
                                    .selectionMedia(selectList)// 是否传入已选图片 List<LocalMedia> list
                                    .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                                    .cropCompressQuality(90)// 裁剪压缩质量 默认90 int
                                    .minimumCompressSize(100)// 小于100kb的图片不压缩
                                    .synOrAsy(true)//同步true或异步false 压缩 默认同步
                                    .cropWH(1, 1)// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
                                    .rotateEnabled(false) // 裁剪是否可旋转图片 true or false
                                    .scaleEnabled(false)// 裁剪是否可放大缩小图片 true or false
                                    .videoQuality(0)// 视频录制质量 0 edor 1 int
                                    .videoMaxSecond(15)// 显示多少秒以内的视频or音频也可适用 int
                                    .videoMinSecond(10)// 显示多少秒以内的视频or音频也可适用 int
                                    .recordVideoSecond(60)//视频秒数录制 默认60s int
                                    .isDragFrame(false)// 是否可拖动裁剪框(固定)
                                    .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
                        }

                    }else {
                        EasyPermissions.requestPermissions(getActivity(),"应用需要访问手机照片和拍照权限",101,
                                Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    }

                }
            };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventMessage message) {
        if (message.type == EventMessage.EVENT_FEEDBACK) {
            Intent data = (Intent) message.data;
            // 图片选择，把选好的图片存入集合，展示在缩略图列表
            selectList = PictureSelector.obtainMultipleResult(data);
            adapter.setList(selectList);
            //刷新适配器
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            *//*new AppSettingsDialog.Builder(this).build().show();*//*
        }
    }*/

  /*  //初始化照片选择器
    private void initRv() {
        //存放缩略图的列表
        FullyGridLayoutManager manager =
                new FullyGridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
        rlv_meal.setLayoutManager(manager);
        adapter = new GridImageAdapter(getActivity(), onAddPicClickListener, 0);
        //设置集合数据
        adapter.setList(selectList);
        //设置最大选择数量
        //最大选择文件数，此时我们需求最大传四张图片
        int maxSelectNum = 9;
        adapter.setSelectMax(maxSelectNum);
        rlv_meal.setAdapter(adapter);

        rlv_meal.setHasFixedSize(true);
        rlv_meal.setNestedScrollingEnabled(false);
        adapter.setOnItemClickListener((position, v) -> {
            //缩略图选择列表子条目点击事件
            LocalMedia media = selectList.get(position);
            String pictureType = media.getPictureType();
            int mediaType = PictureMimeType.pictureToVideo(pictureType);
            switch (mediaType) {
                case 1:
                    // 预览图片
                    PictureSelector.create(getActivity())
                            .externalPicturePreview(position, selectList);
                    break;
                case 2:
                    // 预览视频
                    PictureSelector.create(getActivity()).externalPictureVideo(media.getPath());
                    break;
                case 3:
                    // 预览音频
                    PictureSelector.create(getActivity()).externalPictureAudio(media.getPath());
                    break;
            }
        });
    }
*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        /*EventBus.getDefault().unregister(this);*/
    }



    private File getFileFormPosition(int position){
        LocalMedia media = selectList.get(position);
        String path = "";

        if (media.isCut() && !media.isCompressed()) {
            // 裁剪过
            path = media.getCutPath();
        } else if (media.isCompressed() || (media.isCut() && media.isCompressed())) {
            // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
            path = media.getCompressPath();
        } else {
            // 原图
            path = media.getPath();
        }
        return new File(path);
    }

    private List<File> getFileList(){
        ArrayList<File> objects = new ArrayList<>();
        for (int i = 0; i < selectList.size(); i++) {
            LocalMedia media = selectList.get(i);
            String path = "";
            if (media.isCut() && !media.isCompressed()) {
                // 裁剪过
                path = media.getCutPath();
            } else if (media.isCompressed() || (media.isCut() && media.isCompressed())) {
                // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                path = media.getCompressPath();
            } else {
                // 原图
                path = media.getPath();
            }
            objects.add(new File(path));
        }
        return objects;
    }
}
