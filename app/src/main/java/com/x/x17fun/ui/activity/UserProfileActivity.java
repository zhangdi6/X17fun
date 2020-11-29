package com.x.x17fun.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.weigan.loopview.LoopView;
import com.x.x17fun.R;
import com.x.x17fun.base.App;
import com.x.x17fun.base.BaseActivity;
import com.x.x17fun.base.BaseDialog;
import com.x.x17fun.base.EventMessage;
import com.x.x17fun.base.IBaseCallBack;
import com.x.x17fun.custom.citypicker.AddressPickerView;
import com.x.x17fun.custom.statubar.StatusBarCompat;
import com.x.x17fun.data.DataService;
import com.x.x17fun.entity.BaseResult;
import com.x.x17fun.entity.UserSyncEntity;
import com.x.x17fun.utils.AdiUtils;
import com.x.x17fun.utils.DateFormatUtils;
import com.yalantis.ucrop.UCrop;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class UserProfileActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBack;
    private ImageView mUserHeader;
    /**
     * 还未填写个性签名，介绍一下自己吧
     */
    private TextView mSign;
    /**
     * AdScar
     */
    private TextView mUserName;
    /**
     * 832523
     */
    private TextView mUserId;
    /**
     * 女
     */
    private TextView mUserSex;
    /**
     * 1991-07-13
     */
    private TextView mUserBirthday;
    /**
     * 山西-阳泉
     */
    private TextView mUserLocation;
    /**
     * 请输入大学
     */
    private TextView mUserSchool;
    /**
     * 请输入公司
     */
    private TextView mUserCompany;
    /**
     * 请输入职业
     */
    private TextView mUserWork;
    /**
     * 待认证
     */
    private TextView mUserVerify;
    private BaseDialog timeDialog2;
    private String yearStr;
    private String monthStr;
    private String dayStr;
    private String mBirth = "";
    private Dialog addressDialog;
    private String real_address = "";
    //这个集合用来放媒体文件，包括图片，视频，音频都可以
    private List<LocalMedia> selectList = new ArrayList<>();
    private int chooseMode = PictureMimeType.ofImage();
    private int themeId;
    private int mPosition;
    private TextView mSave;
    private String sexStr = "";
    private BaseDialog sexDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        StatusBarCompat.changeToLightStatusBar(this);
        setContentView(R.layout.activity_user_profile);
        EventBus.getDefault().register(this);
        initView();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void initView() {
        themeId = R.style.picture_default_style;
        mBack = (ImageView) findViewById(R.id.back);
        mBack.setOnClickListener(this);
        mUserHeader = (ImageView) findViewById(R.id.user_header);
        mUserHeader.setOnClickListener(this);


        mSign = (TextView) findViewById(R.id.sign);
        mSign.setOnClickListener(this);
        mUserName = (TextView) findViewById(R.id.user_name);
        mUserName.setOnClickListener(this);
        mSave = (TextView) findViewById(R.id.save);
        mSave.setOnClickListener(this);
        mUserId = (TextView) findViewById(R.id.user_id);
        mUserId.setOnClickListener(this);
        mUserSex = (TextView) findViewById(R.id.user_sex);
        mUserSex.setOnClickListener(this);
        mUserBirthday = (TextView) findViewById(R.id.user_birthday);
        mUserBirthday.setOnClickListener(this);
        mUserLocation = (TextView) findViewById(R.id.user_location);
        mUserLocation.setOnClickListener(this);
        mUserSchool = (TextView) findViewById(R.id.user_school);
        mUserSchool.setOnClickListener(this);
        mUserCompany = (TextView) findViewById(R.id.user_company);
        mUserCompany.setOnClickListener(this);
        mUserWork = (TextView) findViewById(R.id.user_work);
        mUserWork.setOnClickListener(this);
        mUserVerify = (TextView) findViewById(R.id.user_verify);
        mUserVerify.setOnClickListener(this);

        if (App.userMsg()!=null && App.userMsg().getUserInfo()!=null
                && App.userMsg().getUserInfo().getUserStatus()!=null && App.userMsg().getUserInfo().getUserStatus().equals("1")){
            mUserVerify.setText("已认证");
        }else{
            mUserVerify.setText("待认证");
        }
    }

    @Override
    protected void initData() {
        syncUser(new IBaseCallBack<UserSyncEntity>() {
            @Override
            public void onSuccess(UserSyncEntity data) {
                App.defaultApp().saveUserMsg(data);
                UserSyncEntity.UserInfoBean userInfo = data.getUserInfo();
                //头像
                RequestOptions error = new RequestOptions().circleCrop().placeholder(R.mipmap.yiqifan)
                        .error(R.mipmap.yiqifan);
                Glide.with(UserProfileActivity.this).load(userInfo.getPortait())
                        .apply(error).into(mUserHeader);
                //昵称
                if (userInfo.getNickName()==null || userInfo.getNickName().equals("")){
                    mUserName.setText("请输入昵称");
                    mUserName.setTextColor(Color.parseColor("#999999"));
                }else {
                    mUserName.setText(userInfo.getNickName());
                    mUserName.setTextColor(Color.parseColor("#333333"));
                }
                mUserId.setText(userInfo.getFunCode());
                mUserSex.setText(userInfo.getGender().equals("1")?"男":"女");

                //生日
                if (userInfo.getBirthday()!=null){
                    long time = userInfo.getBirthday().getTime();
                    String s = DateFormatUtils.longToDate(time,DateFormatUtils.FORMAT_4);
                    mUserBirthday.setText(s);
                    mUserBirthday.setTextColor(Color.parseColor("#333333"));
                }else{
                    mUserBirthday.setText("请选择生日");
                    mUserBirthday.setTextColor(Color.parseColor("#999999"));
                }

                //出生地
                if (userInfo.getBornAddress()==null || userInfo.getBornAddress().equals("")){
                    mUserLocation.setText("请选择出生地");
                    mUserLocation.setTextColor(Color.parseColor("#999999"));
                }else{
                    mUserLocation.setText(userInfo.getBornAddress());
                    mUserLocation.setTextColor(Color.parseColor("#333333"));
                }

                //大学
                if (userInfo.getColleage()==null || userInfo.getColleage().equals("")){
                    mUserSchool.setText("请输入大学");
                    mUserSchool.setTextColor(Color.parseColor("#999999"));
                }else{
                    mUserSchool.setText(userInfo.getColleage());
                    mUserSchool.setTextColor(Color.parseColor("#333333"));
                }

                //公司
                if (userInfo.getCompany()==null || userInfo.getCompany().equals("")){
                    mUserCompany.setText("请输入公司");
                    mUserCompany.setTextColor(Color.parseColor("#999999"));
                }else{
                    mUserCompany.setText(userInfo.getCompany());
                    mUserCompany.setTextColor(Color.parseColor("#333333"));
                }

                //职业
                if (userInfo.getProfession()==null || userInfo.getProfession().equals("")){
                    mUserWork.setText("请输入职业");
                    mUserWork.setTextColor(Color.parseColor("#999999"));
                }else{
                    mUserWork.setText(userInfo.getProfession());
                    mUserWork.setTextColor(Color.parseColor("#333333"));
                }

                //签名
                if (userInfo.getSignature()==null || userInfo.getSignature().equals("")){
                    mSign.setText("还未填写个性签名，介绍一下自己吧");
                    mSign.setTextColor(Color.parseColor("#999999"));
                }else{
                    mSign.setText(userInfo.getSignature());
                    mSign.setTextColor(Color.parseColor("#333333"));
                }

            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

    @Override
    protected void initLogic() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.back:
                finish();
                break;
            case R.id.user_header:
                if (EasyPermissions.hasPermissions(getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                )){
                    if (selectList.size()>0){
                        selectList = new ArrayList<>();
                    }
                    initCamera();
                }else {
                    EasyPermissions.requestPermissions(this,"",123,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                    );
                }
                break;
            case R.id.sign:
                Intent intent = new Intent(this, UpdateInfoActivity.class);
                intent.putExtra("type","sign");
                startActivityForResult(intent,404);
                break;
            case R.id.user_name:
                Intent intent1 = new Intent(this, UpdateInfoActivity.class);
                intent1.putExtra("type","name");
                startActivityForResult(intent1,404);
                break;
            case R.id.user_id:
                break;
            case R.id.user_sex:
                initSexialog();
                break;

            case R.id.save:
                /*update();*/
                break;
            case R.id.user_birthday:
                initBirthdayDialog();
                break;
            case R.id.user_location:
                initAddressDialog();
                break;
            case R.id.user_school:
                Intent intent2 = new Intent(this, UpdateInfoActivity.class);
                intent2.putExtra("type","school");
                startActivityForResult(intent2,404);
                break;
            case R.id.user_company:
                Intent intent3 = new Intent(this, UpdateInfoActivity.class);
                intent3.putExtra("type","company");
                startActivityForResult(intent3,404);
                break;
            case R.id.user_work:
                Intent intent4 = new Intent(this, UpdateInfoActivity.class);
                intent4.putExtra("type","work");
                startActivityForResult(intent4,404);
                break;
            case R.id.user_verify:
                if (App.userMsg()!=null && App.userMsg().getUserInfo()!=null
                && App.userMsg().getUserInfo().getUserStatus()!=null && App.userMsg().getUserInfo().getUserStatus().equals("1")){

                }else{
                    Intent intent5 = new Intent(this, UserVerifyActivity.class);
                    startActivity(intent5);
                }
                break;
        }
    }

    private void initSexialog() {
        View inflate = LayoutInflater.from(UserProfileActivity.this).inflate(R.layout.dialog_sex, null);
        TextView cancle = inflate.findViewById(R.id.cancle);
        TextView sure = inflate.findViewById(R.id.sure);
        LoopView loop = inflate.findViewById(R.id.loop);

        ArrayList<String> list = new ArrayList<>();

        sure.setOnClickListener(v -> {
            int selectedItem = loop.getSelectedItem();
            sexStr = list.get(selectedItem);
            mUserSex.setText(sexStr);
            mUserSex.setTextColor(Color.parseColor("#333333"));
            sexDialog .dismiss();
            HashMap<String, Object> objectHashMap = new HashMap<>();
            objectHashMap.put("gender",selectedItem == 0 ? "1":"2");
            App.execute(()->{
                final BaseResult fr = App.webService().updateUserMsg(null,null,objectHashMap);
                runOnUiThread(()->{
                    Log.d("tsg",fr.toString());

                    if (fr.getResult_code()==0){
                    }else if (fr.getResult_code()== -3){
                        AdiUtils.loginOut();
                    }else {
                        showToast(fr.getResult_msg());
                    }
                });
            });
        });

            list.add("男");
            list.add("女");

          loop.setItems(list);
          loop.setCurrentPosition(1);
          loop.setNotLoop();
        loop.setListener(index -> yearStr = list.get(index));

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sexDialog.dismiss();
            }
        });


        if (sexDialog==null){
            sexDialog = new BaseDialog(UserProfileActivity.this, inflate, Gravity.BOTTOM);
        }
        sexDialog.show();
    }

    private void update() {
        HashMap<String, Object> hashMap = new HashMap<>();
        if (!"请输入昵称".equals(mUserName.getText().toString().trim())) {
            hashMap.put("nickName",mUserName.getText().toString().trim());
        }

        if ( !mBirth.equals("")) {
            hashMap.put("birthday",mBirth);
        }
        if ( !sexStr.equals("")) {
            if (sexStr.equals("男")){
                hashMap.put("gender","1");
            }else{
                hashMap.put("gender","2");
            }
        }

        if (!real_address.equals("")){
            hashMap.put("bornAddress",real_address);
        }
        if (!"还未填写个性签名，介绍一下自己吧".equals(mSign.getText().toString().trim())) {
            hashMap.put("signature",mSign.getText().toString().trim());
        }

        if (!"请输入公司".equals(mUserCompany.getText().toString().trim())) {
            hashMap.put("company",mUserCompany.getText().toString().trim());
        }
        if (!"请输入职业".equals(mUserWork.getText().toString().trim())) {
            hashMap.put("profession",mUserWork.getText().toString().trim());
        }
        if (!"请输入大学".equals(mUserSchool.getText().toString().trim())) {
            hashMap.put("colleage",mUserSchool.getText().toString().trim());
        }
        App.execute(()->{
            final BaseResult fr = App.webService().updateUserMsg("portait",getFileFormPosition(0),hashMap);
            runOnUiThread(()->{
                Log.d("tsg",fr.toString());

                if (fr.getResult_code()==0){

                    showToast("修改成功");
                    finish();

                }else if (fr.getResult_code()== -3){
                    AdiUtils.showToast("您的登录信息已经失效，请重新登录！");
                    Intent intent = new Intent(App.defaultApp(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    App.defaultApp().startActivity(intent);
                }else {
                    showToast(fr.getResult_msg());
                }
            });
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    private void initBirthdayDialog() {
        View inflate = LayoutInflater.from(UserProfileActivity.this).inflate(R.layout.dialog_birth, null);
        TextView cancle = inflate.findViewById(R.id.cancle);
        TextView sure = inflate.findViewById(R.id.sure);
        LoopView loop = inflate.findViewById(R.id.loop);
        LoopView  loop2 = inflate.findViewById(R.id.loop2);
        LoopView  loop3 = inflate.findViewById(R.id.loop3);
        ArrayList<String> list = new ArrayList<>();
        ArrayList<String> list2 = new ArrayList<>();
        ArrayList<String> list3 = new ArrayList<>();
        sure.setOnClickListener(v -> {
            int selectedItem = loop.getSelectedItem();
            yearStr = list.get(selectedItem);
            int selectedItem2 = loop2.getSelectedItem();
            monthStr = list2.get(selectedItem2);
            int selectedItem3 = loop3.getSelectedItem();
            dayStr = list3.get(selectedItem3);
            mUserBirthday.setText(yearStr+"-"+monthStr+"-"+dayStr);
            mBirth = yearStr+monthStr+dayStr;
            mUserBirthday.setTextColor(Color.parseColor("#333333"));
            timeDialog2 .dismiss();
            HashMap<String, Object> objectHashMap = new HashMap<>();
            objectHashMap.put("birthday",mBirth);
            App.execute(()->{
                final BaseResult fr = App.webService().updateUserMsg(null,null,objectHashMap);
                runOnUiThread(()->{
                    Log.d("tsg",fr.toString());

                    if (fr.getResult_code()==0){
                    }else if (fr.getResult_code()== -3){
                        AdiUtils.loginOut();
                    }else {
                        showToast(fr.getResult_msg());
                    }
                });
            });
        });

        for (int i = 1950; i <= 2020; i++) {
            list.add(i+"");
        }

        for (int i = 1; i <=12; i++) {
            if (i < 10){
                list2.add("0"+i);
            }else{
                list2.add(i+"");
            }
        }
        for (int i = 1; i <=31; i++) {
            if (i < 10){
                list3.add("0"+i);
            }else{
                list3.add(i+"");
            }
        }

        loop.setItems(list);
        loop2.setItems(list2);
        loop3.setItems(list3);

        if (App.userMsg()!=null && App.userMsg().getUserInfo()!=null && App.userMsg().getUserInfo().getBirthday()!=null){
            long time = App.userMsg().getUserInfo().getBirthday().getTime();
            String s = DateFormatUtils.longToDate(time,DateFormatUtils.FORMAT_4);
            String[] split = s.split("-");
            loop2.setInitPosition(list2.indexOf(split[1]));
            loop3.setInitPosition(list3.indexOf(split[2]));
            loop.setInitPosition(list.indexOf(split[0]));
        }


        loop.setListener(index -> yearStr = list.get(index));
        loop2.setListener(index -> monthStr = list2.get(index));
        loop3.setListener(index -> dayStr = list3.get(index));

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeDialog2.dismiss();
            }
        });


        if (timeDialog2==null){
            timeDialog2 = new BaseDialog(UserProfileActivity.this, inflate, Gravity.BOTTOM);
        }
        timeDialog2.show();
    }
    private void initAddressDialog() {

        View inflate = LayoutInflater.from(UserProfileActivity.this).inflate(R.layout.dialog_address2, null);
        /*TextView cancle = inflate.findViewById(R.id.cancle);
        TextView sure = inflate.findViewById(R.id.sure);*/
        AddressPickerView addressPicker = inflate.findViewById(R.id.address);

        addressPicker.setOnAddressPickerSure(new AddressPickerView.OnAddressPickerSureListener() {

            @Override
            public void onSureClick(String address, String provinceCode, String cityCode, String districtCode) {

                address = address.replace("市辖区","");
                address = address.replace("县","");
                address = address.replace("省直辖县级行政单位","");


                real_address = address;
                mUserLocation.setText(address);
                mUserLocation.setTextColor(Color.parseColor("#333333"));
                addressDialog.dismiss();
                HashMap<String, Object> objectHashMap = new HashMap<>();
                objectHashMap.put("bornAddress",real_address);
                App.execute(()->{
                    final BaseResult fr = App.webService().updateUserMsg(null,null,objectHashMap);
                    runOnUiThread(()->{
                        Log.d("tsg",fr.toString());

                        if (fr.getResult_code()==0){
                        }else if (fr.getResult_code()== -3){
                            AdiUtils.loginOut();
                        }else {
                            showToast(fr.getResult_msg());
                        }
                    });
                });
            }
        });
       /* cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            17611283721
                addressDialog.dismiss();
            }
        });

        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addressDialog.dismiss();
            }
        });*/
        if (addressDialog==null){
            addressDialog = new BaseDialog(UserProfileActivity.this, inflate, Gravity.BOTTOM);
        }
        addressDialog.show();
    }

    private void initCamera() {
        // 进入相册 以下是例子：不需要的api可以不写
        // 进入相册 以下是例子：用不到的api可以不写
        PictureSelector.create(UserProfileActivity.this)
                .openGallery(chooseMode)//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(themeId)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                .maxSelectNum(1)// 最大图片选择数量 int
                .minSelectNum(1)// 最小选择数量 int
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
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
                /* .glideOverride(76,76)*/// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
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
                .forResult(PictureConfig.CAMERA);//结果回调onActivityResult code
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode== PictureConfig.CAMERA){
            switch (resultCode) {
                case Activity.RESULT_OK:
                    if (data!=null){
                        EventBus.getDefault().post(new EventMessage(EventMessage.EVENT_PHOTO,data));
                    }
                    break;
            }

        }else if (requestCode==404){

            String name = data.getStringExtra("name");
            switch (resultCode){
                //签名
                case 1:
                    mSign.setText(name);
                    mSign.setTextColor(Color.parseColor("#333333"));
                    break;
                    //昵称
                case 2:
                    mUserName.setText(name);
                    mUserName.setTextColor(Color.parseColor("#333333"));
                    break;
                    //大学
                case 3:
                    mUserSchool.setText(name);
                    mUserSchool.setTextColor(Color.parseColor("#333333"));
                    break;
                    //公司
                case 4:
                    mUserCompany.setText(name);
                    mUserCompany.setTextColor(Color.parseColor("#333333"));
                    break;
                    //职业
                case 5:
                    mUserWork.setText(name);
                    mUserWork.setTextColor(Color.parseColor("#333333"));
                    break;
                case 6:
                    mUserVerify.setText(name);
                    mUserVerify.setTextColor(Color.parseColor("#333333"));
                    break;
            }
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventMessage message) {
        Log.d("aaa","接收");
        if (message.type == EventMessage.EVENT_PHOTO) {
            Intent data = (Intent) message.data;
            // 图片选择，把选好的图片存入集合，展示在缩略图列表
            selectList = PictureSelector.obtainMultipleResult(data);
            Log.d ("aaa",selectList.size()+"");
            if (selectList.size()>0){
                File fileFormPosition = getFileFormPosition(0);
                Log.d("aaa",fileFormPosition.getPath());
                RequestOptions requestOptions = new RequestOptions().circleCrop();
                Glide.with(UserProfileActivity.this).load(fileFormPosition).apply(requestOptions).into(mUserHeader);
                HashMap<String, Object> objectHashMap = new HashMap<>();

                App.execute(()->{
                    final BaseResult fr = App.webService().updateUserMsg("portait",getFileFormPosition(0),objectHashMap);
                    runOnUiThread(()->{
                        Log.d("tsg",fr.toString());

                        if (fr.getResult_code()==0){

                        }else if (fr.getResult_code()== -3){
                            AdiUtils.showToast("您的登录信息已经失效，请重新登录！");
                            Intent intent = new Intent(App.defaultApp(), LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            App.defaultApp().startActivity(intent);
                        }else {
                            showToast(fr.getResult_msg());
                        }
                    });
                });
            }

        }
    }
    private File getFileFormPosition(int position){
        if (selectList.size()==0){
            return null;
        }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
