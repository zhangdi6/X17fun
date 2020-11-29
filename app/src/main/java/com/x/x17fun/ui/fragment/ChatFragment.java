package com.x.x17fun.ui.fragment;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.x.x17fun.R;
import com.x.x17fun.adapter.ChatListAdapter;
import com.x.x17fun.adapter.UserReceiveAddressAdapter;
import com.x.x17fun.base.App;
import com.x.x17fun.base.BaseFragment;
import com.x.x17fun.base.EventMessage;
import com.x.x17fun.custom.FullyGridLayoutManager;
import com.x.x17fun.custom.GridImageAdapter;
import com.x.x17fun.data.DataService;
import com.x.x17fun.entity.BaseResult;
import com.x.x17fun.entity.ChatEntity;
import com.x.x17fun.entity.MyAddessEntity;
import com.x.x17fun.ui.activity.LoginActivity;
import com.x.x17fun.ui.activity.PhotoActivityActivity;
import com.x.x17fun.utils.AdiUtils;
import com.x.x17fun.utils.CalenderUtilss;
import com.x.x17fun.utils.DateFormatUtils;
import com.x.x17fun.utils.DeeSpUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
public class ChatFragment extends BaseFragment implements View.OnClickListener {

    private String providerId = "";
    private String name = "";
    private View view;
    private ImageView mBack;
    /**
     *
     */
    private TextView mTitle;
    private RecyclerView mChatRlv;
    private SmartRefreshLayout mSmart;
    /**
     * 发消息...
     */
    private EditText mEtContent;
    private ImageView mPhoto;
    private ImageView mSend;
    private GridImageAdapter adapter;

    //这个集合用来放媒体文件，包括图片，视频，音频都可以
    private List<LocalMedia> selectList = new ArrayList<>();
    private int chooseMode = PictureMimeType.ofImage();
    private int themeId;
    private ChatListAdapter listAdapter;

    private List<ChatEntity.ChatRecordsBean> chatRecords ;
    private boolean isSend = true;
    private boolean isFirst = true;

    public ChatFragment() {
        // Required empty public constructor
    }

    public static ChatFragment newInstance(String providerid, String name) {
        ChatFragment chatFragment = new ChatFragment();
        Bundle bundle = new Bundle();
        bundle.putString("providerId", providerid);
        bundle.putString("name", name);
        chatFragment.setArguments(bundle);
        return chatFragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_chat, container, false);
        providerId = getArguments().getString("providerId");
        name = getArguments().getString("name");
        EventBus.getDefault().register(this);
        themeId = R.style.picture_default_style;

        initView(inflate);

        return inflate;
    }


    @Override
    public void onSupportVisible() {

        isSend = true;
        initData(DateFormatUtils.longToDate(System.currentTimeMillis(),DateFormatUtils.FORMAT_6),"1");
        super.onSupportVisible();
    }

    @Override
    public void onSupportInvisible() {
        isSend = false ;
        super.onSupportInvisible();
    }

    private void initData(String time , String dateType) {
        if (isSend) {
            HashMap<String, Object> hashMap = new HashMap<>();
            String userId = DeeSpUtil.getInstance().getString("userId");
            hashMap.put("userId", userId);
            hashMap.put("ticket", DeeSpUtil.getInstance().getString("ticket"));
            hashMap.put("toUserId", providerId);


            hashMap.put("lasttime", time);
            hashMap.put("refreshPatten", dateType);
            DataService.getHomeService().imChatRcords(hashMap)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseResult<ChatEntity>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BaseResult<ChatEntity> baseResult) {
                            Log.e("result", baseResult.toString());
                            if (baseResult.getResult_code() == 0) {

                                if (baseResult.getData() != null && baseResult.getData().getChatRecords() != null &&
                                        baseResult.getData().getChatRecords().size() > 0) {
                                    if (chatRecords != null) {
                                        String content = chatRecords.get(0).getCreatetime();
                                        String createtime = baseResult.getData().getChatRecords().get(0).getCreatetime();
                                        if (!content.equals(createtime)) {
                                            chatRecords = baseResult.getData().getChatRecords();
                                            ArrayList<ChatEntity.ChatRecordsBean> objects = new ArrayList<>();
                                            for (int i = 0; i < baseResult.getData().getChatRecords().size(); i++) {

                                                if (i > 0) {
                                                    ChatEntity.ChatRecordsBean recordsBean = baseResult.getData().getChatRecords().get(i - 1);
                                                    ChatEntity.ChatRecordsBean recordsBean2 = baseResult.getData().getChatRecords().get(i);
                                                    if (isBigger(recordsBean.getCreatetime(), recordsBean2.getCreatetime())) {
                                                        baseResult.getData().getChatRecords().get(i).setHasTime(true);
                                                    } else {
                                                        baseResult.getData().getChatRecords().get(i).setHasTime(false);
                                                    }
                                                } else {
                                                    baseResult.getData().getChatRecords().get(i).setHasTime(true);
                                                }

                                            }
                                                Collections.reverse(baseResult.getData().getChatRecords());
                                                objects.addAll(baseResult.getData().getChatRecords());
                                                listAdapter.addData(objects);
                                                if (isFirst){
                                                    mChatRlv.smoothScrollToPosition(listAdapter.mList.size()-1);
                                                }


                                        }
                                    } else {
                                        chatRecords = baseResult.getData().getChatRecords();
                                        ArrayList<ChatEntity.ChatRecordsBean> objects = new ArrayList<>();
                                        for (int i = 0; i < baseResult.getData().getChatRecords().size(); i++) {

                                            if (i > 0) {
                                                ChatEntity.ChatRecordsBean recordsBean = baseResult.getData().getChatRecords().get(i - 1);
                                                ChatEntity.ChatRecordsBean recordsBean2 = baseResult.getData().getChatRecords().get(i);
                                                if (isBigger(recordsBean.getCreatetime(), recordsBean2.getCreatetime())) {
                                                    baseResult.getData().getChatRecords().get(i).setHasTime(true);
                                                } else {
                                                    baseResult.getData().getChatRecords().get(i).setHasTime(false);
                                                }
                                            } else {
                                                baseResult.getData().getChatRecords().get(i).setHasTime(true);
                                            }

                                        }

                                            Collections.reverse(baseResult.getData().getChatRecords());
                                            objects.addAll(baseResult.getData().getChatRecords());
                                            listAdapter.addData(objects);
                                         if (isFirst){
                                            mChatRlv.smoothScrollToPosition(listAdapter.mList.size()-1);
                                        }


                                    }
                                    mChatRlv.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {

                                             isFirst = false;
                                            initData(DateFormatUtils.longToDate(System.currentTimeMillis(), DateFormatUtils.FORMAT_6),
                                                    "1");
                                        }
                                    }, 10000);

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
    }

    private boolean isBigger(String str1, String str2){
        long time = DateFormatUtils.getDateFromString2(str1).getTime();
        long time2 = DateFormatUtils.getDateFromString2(str2).getTime();

        if (time2 - time > 1000 * 60 * 5 ){
            return true;
        }
        return false;
    }
    private boolean isBigger(String str1, long currenttime){
        long time = DateFormatUtils.getDateFromString2(str1).getTime();

        if (currenttime - time > 1000 * 60 * 5 ){
            return true;
        }
        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventMessage message) {
        if (message.type == EventMessage.EVENT_FEEDBACK) {
            Intent data = (Intent) message.data;
            // 图片选择，把选好的图片存入集合，展示在缩略图列表
            selectList = PictureSelector.obtainMultipleResult(data);


            File fileFormPosition = getFileFormPosition(0);

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("toUserId",providerId);
            hashMap.put("dataType","2");
            hashMap.put("content","");

            App.execute(()->{
                final BaseResult fr = App.webService().sendImg("extraImg",fileFormPosition,hashMap);
                getActivity().runOnUiThread(()->{

                    if (fr.getResult_code()==0){
                        mEtContent.setText("");
                        mChatRlv.smoothScrollToPosition(listAdapter.mList.size()-1);
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

            if (selectList!=null && selectList.size()>0){
                ChatEntity.ChatRecordsBean recordsBean = new ChatEntity.ChatRecordsBean();
                recordsBean.setContent("");
                recordsBean.setCreatetime(DateFormatUtils.getCurrentDateString());
                recordsBean.setDataType("2");
                if (listAdapter!=null && listAdapter.mList.size()>0){
                    String createtime = listAdapter.mList.get(listAdapter.mList.size() - 1).getCreatetime();
                    long time = new Date().getTime();
                    boolean bigger = isBigger(createtime, time);
                    if (bigger){
                        recordsBean.setHasTime(true);
                    }else{
                        recordsBean.setHasTime(false);
                    }
                }else{
                    recordsBean.setHasTime(false);
                }

                recordsBean.setContent(getFileFormPosition(0).getAbsolutePath());
                recordsBean.setFromUserId(DeeSpUtil.getInstance().getString("userId"));
                listAdapter.addNewData(recordsBean);
                selectList.clear();
            }
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

    }
    private void initView(View inflate) {

        mBack = (ImageView) inflate.findViewById(R.id.back);
        mBack.setOnClickListener(this);
        mTitle = (TextView) inflate.findViewById(R.id.title);
        mChatRlv = (RecyclerView) inflate.findViewById(R.id.chat_rlv);
        mSmart = (SmartRefreshLayout) inflate.findViewById(R.id.smart);

        mTitle.setText(name);
        mEtContent = (EditText) inflate.findViewById(R.id.et_content);
        mPhoto = (ImageView) inflate.findViewById(R.id.photo);
        mPhoto.setOnClickListener(this);
        mSend = (ImageView) inflate.findViewById(R.id.send);
        mSend.setOnClickListener(this);
        mChatRlv.setLayoutManager(new LinearLayoutManager(getContext()));
        listAdapter = new ChatListAdapter();
        mChatRlv.setAdapter(listAdapter);

        listAdapter.onClick(new ChatListAdapter.onItemClickListener() {
            @Override
            public void onClickLeft(int position) {
                Intent intent = new Intent(getContext(), PhotoActivityActivity.class);
                intent.putExtra("img",listAdapter.mList.get(position).getContent());
                startActivity(intent);
            }

            @Override
            public void onClickRight(int position) {
                Intent intent = new Intent(getContext(), PhotoActivityActivity.class);
                intent.putExtra("img",listAdapter.mList.get(position).getContent());
                startActivity(intent);
            }
        });
        mEtContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (mEtContent.getText().length()>0){
                    mSend.setVisibility(View.VISIBLE);
                    mSend.setBackgroundResource(R.mipmap.feijihong);
                }else{
                    mSend.setBackgroundResource(R.mipmap.feijihui);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
      /*  mSmart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                if (listAdapter.mList!=null && listAdapter.mList.size()>0){
                    initData(listAdapter.mList.get(0).getCreatetime(),"1");
                }
            }
        });*/
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
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.back:
                getActivity().finish();
                break;
            case R.id.photo:
               initPhoto();
                break;
            case R.id.send:
                if (getMsg()!=null && !"".equals(getMsg())){
                    ChatEntity.ChatRecordsBean recordsBean = new ChatEntity.ChatRecordsBean();
                    recordsBean.setContent(getMsg());
                    recordsBean.setCreatetime(DateFormatUtils.getCurrentDateString());
                    recordsBean.setDataType("1");
                    if (listAdapter!=null && listAdapter.mList.size()>0){
                        String createtime = listAdapter.mList.get(listAdapter.mList.size() - 1).getCreatetime();
                        long time = new Date().getTime();
                        boolean bigger = isBigger(createtime, time);
                        if (bigger){
                            recordsBean.setHasTime(true);
                        }else{
                            recordsBean.setHasTime(false);
                        }
                    }else{
                        recordsBean.setHasTime(false);
                    }

                    recordsBean.setFromUserId(DeeSpUtil.getInstance().getString("userId"));
                    listAdapter.addNewData(recordsBean);
                    sendMsg();
                }
                break;
        }
    }

    private void sendMsg() {
        HashMap<String, Object> hashMap = new HashMap<>();
        String userId = DeeSpUtil.getInstance().getString("userId");
        hashMap.put("userId",userId);
        hashMap.put("ticket",DeeSpUtil.getInstance().getString("ticket"));
        hashMap.put("toUserId",providerId);
        hashMap.put("content",getMsg());
        hashMap.put("dataType","1");
        DataService.getHomeService().sendMsg(hashMap)
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
                            mEtContent.setText("");
                            mChatRlv.smoothScrollToPosition(listAdapter.mList.size()-1);
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

    private String getMsg(){
        return mEtContent.getText().toString().trim();
    }

    private void initPhoto() {
        if (EasyPermissions.hasPermissions(getContext().getApplicationContext(),
                Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            boolean mode = true;
            if (mode) {
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

        }else {
            EasyPermissions.requestPermissions(getActivity(),"应用需要访问手机照片和拍照权限",101,
                    Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }


}
