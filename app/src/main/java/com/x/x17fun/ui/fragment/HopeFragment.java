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
import com.x.x17fun.adapter.MoreChooseAdapter;
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
import com.x.x17fun.entity.BaseEntity;
import com.x.x17fun.entity.BaseResult;
import com.x.x17fun.ui.activity.LoginActivity;
import com.x.x17fun.ui.activity.ReceiveProductActivity;
import com.x.x17fun.utils.AdiUtils;
import com.x.x17fun.utils.CalenderUtilss;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * A simple {@link Fragment} subclass.
 */
public class HopeFragment extends BaseFragment {


    private TextView ok;

    private EditText meal_name;
    private RecyclerView rlv_meal;
    private ImageView back;
    private GridImageAdapter adapter;

    //这个集合用来放媒体文件，包括图片，视频，音频都可以
    private List<LocalMedia> selectList = new ArrayList<>();
    private int chooseMode = PictureMimeType.ofImage();
    private int themeId;


    private ArrayList<BaseEntity> objects = new ArrayList<>();
    private BaseDialog pushDialog;

    //配送地点
    private String real_address;
    //下架时间
    private String real_gone_time;
    //配送时间
    private String real_push_time;
    private UserReceiveAddressAdapter receiveAddressAdapter;
    private RecyclerView address;
    private boolean ispush = false;
    private String real_area;
    private String aLong;
    private String aLa;
    private BaseDialog loadingDialog;
    private TextView title;
    private ImageView remote;
    private ImageView mBtnChoose;
    private ImageView mBtnMore;
    private RecyclerView rlv;


    public static HopeFragment newInstance(){
        return new HopeFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_hope, container, false);
        EventBus.getDefault().register(this);
        //themeId必须设置
        themeId = R.style.picture_default_style;
        objects.add(new BaseEntity(R.mipmap.friend,"发动态",0));
        objects.add(new BaseEntity(R.mipmap.send_food,"发商品",0));
        objects.add(new BaseEntity(R.mipmap.heart_black,"发心愿",0));
         ispush = false;
        initView(inflate);
        initRv();

        return inflate;


    }


    private void initPushDialog() {

        if (pushDialog==null){
        View inflate = LayoutInflater.from(_mActivity).inflate(R.layout.dialog_more, null);
        rlv = inflate.findViewById(R.id.rlv);
        ImageView close = inflate.findViewById(R.id.close);
        rlv.setLayoutManager(new LinearLayoutManager(getContext()));
        rlv.setNestedScrollingEnabled(false);
        rlv.setHasFixedSize(true);
        MoreChooseAdapter moreChooseAdapter = new MoreChooseAdapter();
        rlv.setAdapter(moreChooseAdapter);
        moreChooseAdapter.addData(objects);

        close.setOnClickListener(v -> pushDialog.dismiss());
        moreChooseAdapter.onClick(position -> {
            switch (position){
                case 0:
                    pushDialog.dismiss();
                    break;
                case 1:
                    start(PushMealFragment.newInstance());
                    pushDialog.dismiss();
                    break;
                case 2:
                    showToast("功能开发中，敬请期待");
                    pushDialog.dismiss();
                    break;
                default:
                    break;
            }
        });
        pushDialog = new BaseDialog(_mActivity, inflate, Gravity.BOTTOM);
        }
        pushDialog.show();

    }

    //发布动态
    private void pushMeal() {

        ispush = true ;

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("content",meal_name.getText().toString());

        showLoadingPage();
        App.execute(()->{
            final BaseResult fr = App.webService().publishActivity("extraImg",getFileList(),hashMap);
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
                meal_name.postDelayed(new Runnable() {
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



    private void initView(View inflate) {

         ok = inflate.findViewById(R.id.ok);
         meal_name = inflate.findViewById(R.id.meal_name);
         rlv_meal = inflate.findViewById(R.id.rlv_meal);
         back = inflate.findViewById(R.id.back);
         mBtnChoose = inflate.findViewById(R.id.btn_choose_photo);
         mBtnMore = inflate.findViewById(R.id.btn_choose_more);

         back.setOnClickListener(v -> pop());
         ok.setOnClickListener(v -> {
             if (ispush){
                 showToast("您有动态正在发布哦");
             }else{
                 if ("".equals(meal_name.getText().toString())){
                     showToast("请输入信息");
                 }else{
                     pushMeal();
                 }
             }
         });
         mBtnMore.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 initPushDialog();
             }
         });
         mBtnChoose.setOnClickListener(v -> {
             if (EasyPermissions.hasPermissions(getContext().getApplicationContext(),
                     Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                 boolean mode = true;
                 if (mode) {
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

             }else {
                 EasyPermissions.requestPermissions(getActivity(),"应用需要访问手机照片和拍照权限",101,
                         Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE);
             }
         });

    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();

    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        StatusBarCompat.setStatusBarColor(getActivity(), Color.WHITE);
        StatusBarCompat.changeToLightStatusBar(getActivity());

    }


    private GridImageAdapter.onAddPicClickListener onAddPicClickListener =
            new GridImageAdapter.onAddPicClickListener() {
                @Override
                public void onAddPicClick() {

                    if (EasyPermissions.hasPermissions(getContext().getApplicationContext(),
                            Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                        boolean mode = true;
                        if (mode) {
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
            /*new AppSettingsDialog.Builder(this).build().show();*/
        }
    }

    //初始化照片选择器
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
                default:
                    break;
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
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
