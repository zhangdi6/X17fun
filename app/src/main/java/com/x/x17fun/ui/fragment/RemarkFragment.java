package com.x.x17fun.ui.fragment;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
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
import com.x.x17fun.R;
import com.x.x17fun.base.App;
import com.x.x17fun.base.BaseDialog;
import com.x.x17fun.base.BaseFragment;
import com.x.x17fun.base.EventMessage;
import com.x.x17fun.custom.FullyGridLayoutManager;
import com.x.x17fun.custom.GridImageAdapter;
import com.x.x17fun.custom.RatingBar;
import com.x.x17fun.entity.BaseResult;
import com.x.x17fun.ui.activity.LoginActivity;
import com.x.x17fun.utils.AdiUtils;
import com.x.x17fun.utils.DeeSpUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * A simple {@link Fragment} subclass.
 */
public class RemarkFragment extends BaseFragment implements View.OnClickListener {

    private View view;
    private ImageView mBack;
    /**
     * 发布
     */
    private TextView mOk;
    private ImageView mProductImg;
    /**
     * 红烧茄子
     */
    private TextView mProductName;
    private RatingBar mRatingbar;
    /**
     * 很满意，值得一试
     */
    private TextView mRemarkLevel;
    private String productName = "";
    private String img = "";
    /**
     * 亲，分享一下您的感受吧，可以帮助更多小伙伴哦~
     */
    private EditText mEditContent;
    private RecyclerView mRlv;
    private ImageView mImgCheck;
    private GridImageAdapter adapter;

    //这个集合用来放媒体文件，包括图片，视频，音频都可以
    private List<LocalMedia> selectList = new ArrayList<>();
    private int chooseMode = PictureMimeType.ofImage();
    private int themeId;
    private boolean isCheck = false;
    private  int rating = 5;
    private String mPublishedId = "";
    private String mPublisherId = "";
    private TextView title;
    private ImageView remote;
    private boolean ispush = false;
    private BaseDialog loadingDialog;
    private String foodId = "";


    public static RemarkFragment getInstance(String productName, String img ,String publishedId ,String publisherId,String foodid) {
        Bundle bundle = new Bundle();
        bundle.putString("productName", productName);
        bundle.putString("img", img);
        bundle.putString("publishedId", publishedId);

        bundle.putString("publisherId", publisherId);
        bundle.putString("foodId", foodid);
        RemarkFragment remarkFragment = new RemarkFragment();
        remarkFragment.setArguments(bundle);
        return remarkFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_remark, container, false);

        EventBus.getDefault().register(this);
        Bundle arguments = getArguments();

        if (arguments != null) {
            productName = arguments.getString("productName");
            img = arguments.getString("img");
            mPublishedId = arguments.getString("publishedId");
            mPublisherId = arguments.getString("publisherId");
            foodId = arguments.getString("foodId");
        }
        themeId = R.style.picture_default_style;

        initView(inflate);
        initRv();
        return inflate;
    }

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
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
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

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener =
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

    //初始化照片选择器
    private void initRv() {
        //存放缩略图的列表
        FullyGridLayoutManager manager =
                new FullyGridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
        mRlv.setLayoutManager(manager);
        adapter = new GridImageAdapter(getActivity(), onAddPicClickListener, 0);
        //设置集合数据
        adapter.setList(selectList);
        //设置最大选择数量
        //最大选择文件数，此时我们需求最大传四张图片
        int maxSelectNum = 9;
        adapter.setSelectMax(maxSelectNum);
        mRlv.setAdapter(adapter);

        mRlv.setHasFixedSize(true);
        mRlv.setNestedScrollingEnabled(false);
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

    private void initView(View inflate) {

        mBack = (ImageView) inflate.findViewById(R.id.back);
        mBack.setOnClickListener(this);
        mOk = (TextView) inflate.findViewById(R.id.ok);
        mOk.setOnClickListener(this);
        mProductImg = (ImageView) inflate.findViewById(R.id.product_img);
        mProductName = (TextView) inflate.findViewById(R.id.product_name);
        mRatingbar = inflate.findViewById(R.id.ratingbar);
        mRemarkLevel = (TextView) inflate.findViewById(R.id.remark_level);


        loadImg(img, mProductImg);
        mProductName.setText(productName);
        mRatingbar.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {


            @Override
            public void onRatingChange(float ratingCount) {
                rating = (int)ratingCount;

                switch ((int) ratingCount) {
                    case 1:
                        mRemarkLevel.setText("有点失望，待改善");
                        break;
                    case 2:
                        mRemarkLevel.setText("一般般");
                        break;
                    case 3:
                        mRemarkLevel.setText("还不错");
                        break;
                    case 4:
                        mRemarkLevel.setText("很满意，值得一试");
                        break;
                    case 5:
                        mRemarkLevel.setText("无可挑剔，强烈推荐");
                        break;

                }
            }
        });
        mEditContent = (EditText) inflate.findViewById(R.id.edit_content);
        mRlv = (RecyclerView) inflate.findViewById(R.id.rlv);
        mImgCheck = (ImageView) inflate.findViewById(R.id.img_check);
        mImgCheck.setOnClickListener(this);


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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.back:
                pop();
                break;
            case R.id.ok:
                if ("".equals(mEditContent.getText().toString().trim())){
                    showToast("请输入评价内容");
                    break;
                }
                pushRemark();
                break;
            case R.id.img_check:
                if (isCheck){
                    mImgCheck.setImageResource(R.mipmap.weixuan);
                    isCheck = false;
                }else{
                    mImgCheck.setImageResource(R.mipmap.xuanze);
                    isCheck = true;
                }
                break;
        }
    }

    private void pushRemark() {
        ispush = true ;
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("remarkContent",mEditContent.getText().toString().trim());
        hashMap.put("starsCount",rating+"");
        hashMap.put("publisherId",mPublisherId);
        hashMap.put("publishedId",mPublishedId);
        hashMap.put("foodId",foodId);
        if (isCheck){
            hashMap.put("isHidden","1");
        }
        hashMap.put("ticket", DeeSpUtil.getInstance().getString("ticket"));
        hashMap.put("userId", DeeSpUtil.getInstance().getString("userId"));
        showLoadingPage();
        App.execute(()->{
            final BaseResult fr = App.webService().publishRemark("extraImg",getFileList(),hashMap);
            getActivity().runOnUiThread(()->{

                if (fr.getResult_code()==0){

                    showToast("评价已发布");
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
                mRatingbar.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hideLoadingPage();
                    }
                },300);

            });
        });

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
    private void hideLoadingPage() {
        if (remote!=null){
            remote.clearAnimation();
        }
        loadingDialog.dismiss();
    }
}
