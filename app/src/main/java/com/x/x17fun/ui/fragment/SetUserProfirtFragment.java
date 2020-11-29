package com.x.x17fun.ui.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.weigan.loopview.LoopView;
import com.weigan.loopview.OnItemSelectedListener;
import com.x.x17fun.R;
import com.x.x17fun.base.App;
import com.x.x17fun.base.BaseDialog;
import com.x.x17fun.base.BaseFragment;
import com.x.x17fun.base.EventMessage;
import com.x.x17fun.custom.GridImageAdapter;
import com.x.x17fun.custom.citypicker.AddressPickerView;
import com.x.x17fun.data.DataService;
import com.x.x17fun.entity.BaseResult;
import com.x.x17fun.entity.UserLoginEntity;
import com.x.x17fun.ui.activity.LoginActivity;
import com.x.x17fun.ui.activity.MainActivity;
import com.x.x17fun.utils.AdiUtils;
import com.zhihu.matisse.engine.impl.GlideEngine;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
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
public class SetUserProfirtFragment extends BaseFragment implements View.OnClickListener {

    private View view;
    private ImageView mBack;
    /**
     * 请输入昵称
     */
    private EditText mEtName;
    private RadioGroup mRadioSex;
    /**
     * 请设置出生日期
     */
    private TextView mBirthday;
    /**
     * 请设置出生地
     */
    private TextView mAddress;
    private ImageView mCamera;
    /**
     * 完成
     */
    private TextView mBtnOk;
    //这个集合用来放媒体文件，包括图片，视频，音频都可以
    private List<LocalMedia> selectList = new ArrayList<>();
    private int chooseMode = PictureMimeType.ofImage();
    private int themeId;
    private int mPosition;
    private String yearStr;
    private String monthStr;
    private String dayStr;
    private BaseDialog timeDialog2;
    private String mBirth = "";
    private Dialog addressDialog;
    private String real_address;

    public static SetUserProfirtFragment getInstance() {
        // Required empty public constructor
        return new SetUserProfirtFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_set_user_profirt, container, false);

        EventBus.getDefault().register(this);
        initView(inflate);
        return inflate;
    }

    private void initView(View inflate) {
        //themeId必须设置
        themeId = R.style.picture_default_style;
        mBack = (ImageView) inflate.findViewById(R.id.back);
        mBack.setOnClickListener(this);
        mEtName = (EditText) inflate.findViewById(R.id.et_name);
        mRadioSex = (RadioGroup) inflate.findViewById(R.id.radio_sex);
        mBirthday = (TextView) inflate.findViewById(R.id.birthday);
        mBirthday.setOnClickListener(this);
        mAddress = (TextView) inflate.findViewById(R.id.address);
        mAddress.setOnClickListener(this);
        mCamera =  inflate.findViewById(R.id.camera);
        mCamera.setOnClickListener(this);
        mBtnOk = (TextView) inflate.findViewById(R.id.btn_ok);
        mBtnOk.setOnClickListener(this);
        mRadioSex.check(R.id.btn1);

    }

   /* @Override
    public void onSupportVisible() {
        super.onSupportVisible();
    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.back:
                pop();
                break;
            case R.id.birthday:
                initBirthdayDialog();
                break;
            case R.id.address:
                initAddressDialog();
                break;
            case R.id.camera:
                if (EasyPermissions.hasPermissions(getActivity().getApplicationContext(),
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
            case R.id.btn_ok:
                onSubmit();
                break;
        }
    }

    private void onSubmit() {

        HashMap<String, Object> hashMap = new HashMap<>();

        if (mEtName.getText().toString().trim().length() == 0) {
            showToast("请填写您的昵称");
            return;
        }

        hashMap.put("nickName",mEtName.getText().toString().trim());
        int checkedRadioButtonId = mRadioSex.getCheckedRadioButtonId();
        if (checkedRadioButtonId==R.id.btn1){
            hashMap.put("gender","1");
        }else{
            hashMap.put("gender","2");
        }

        if (mBirthday.getText().toString().trim()!=null && !"".equals(mBirthday.getText().toString().trim())) {
            hashMap.put("birthday",mBirth);
        }else{
            showToast("请设置您的生日");
            return;
        }


        if (real_address!=null && !real_address.equals("")){
            hashMap.put("bornAddress",real_address);
        }else{
            showToast("请设置您的出生地");
            return;
        }

        if (selectList.size()>0){

        }else{
            showToast("请设置您的头像");
            return;
        }
        App.execute(()->{
            final BaseResult fr = App.webService().updateUserMsg("portait",getFileFormPosition(0),hashMap);
            getActivity().runOnUiThread(()->{
                Log.d("tsg",fr.toString());

                if (fr.getResult_code()==0){

                    Intent intent1 = new Intent(getActivity(), MainActivity.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent1);
                    getActivity().finish();

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


    private void initBirthdayDialog() {



        View inflate = LayoutInflater.from(_mActivity).inflate(R.layout.dialog_birth, null);
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
            mBirthday.setText(yearStr+" 年 "+monthStr+" 月 "+dayStr+" 日");
            mBirth = yearStr+monthStr+dayStr;
            mBirthday.setTextColor(Color.parseColor("#333333"));
            timeDialog2 .dismiss();
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

        loop2.setInitPosition(list2.indexOf(Calendar.getInstance().get(Calendar.MONTH)+1+""));
        loop3.setInitPosition(list3.indexOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)+""));
        loop.setInitPosition(list.indexOf(Calendar.getInstance().get(Calendar.YEAR)+""));

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
            timeDialog2 = new BaseDialog(_mActivity, inflate, Gravity.BOTTOM);
        }
        timeDialog2.show();
    }

    private void initAddressDialog() {

        View inflate = LayoutInflater.from(_mActivity).inflate(R.layout.dialog_address2, null);
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
                 mAddress.setText(address);
                 mAddress.setTextColor(Color.parseColor("#333333"));
                addressDialog.dismiss();
            }
        });
       /* cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            addressDialog = new BaseDialog(_mActivity, inflate, Gravity.BOTTOM);
        }
        addressDialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    private void initCamera() {
        // 进入相册 以下是例子：不需要的api可以不写
        // 进入相册 以下是例子：用不到的api可以不写
        PictureSelector.create(getActivity())
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
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventMessage message) {
        Log.d("aaa","接收");
        if (message.type == EventMessage.EVENT_FEEDBACK) {
            Intent data = (Intent) message.data;
            // 图片选择，把选好的图片存入集合，展示在缩略图列表
            selectList = PictureSelector.obtainMultipleResult(data);
            Log.d ("aaa",selectList.size()+"");
            if (selectList.size()>0){
                File fileFormPosition = getFileFormPosition(0);
                Log.d("aaa",fileFormPosition.getPath());
                RequestOptions requestOptions = new RequestOptions().circleCrop();
                Glide.with(getContext()).load(fileFormPosition.getAbsolutePath()).apply(requestOptions).into(mCamera);
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
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
