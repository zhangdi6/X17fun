package com.x.x17fun.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.x.x17fun.R;
import com.x.x17fun.base.App;
import com.x.x17fun.base.BaseActivity;
import com.x.x17fun.custom.MyRecycleViewAdapter;
import com.x.x17fun.custom.statubar.StatusBarCompat;
import com.x.x17fun.data.DataService;
import com.x.x17fun.entity.BaseEntity;
import com.x.x17fun.entity.BaseResult;
import com.x.x17fun.entity.UserHomePageEntity;
import com.x.x17fun.entity.UserPushListEntity;
import com.x.x17fun.entity.UserSyncEntity;
import com.x.x17fun.utils.ButtonUtils;
import com.x.x17fun.utils.CalenderUtilss;
import com.x.x17fun.utils.DateFormatUtils;
import com.x.x17fun.utils.DeeSpUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SalerProfileActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBack;
    private ImageView mShare;
    private ImageView mUserHeadImg;
    private ImageView mUserSexImg;
    /**
     * 68
     */
    private TextView mLove;
    /**
     * 132
     */
    private TextView mFollower;
    /**
     * 521
     */
    private TextView mCool;
    /**
     * 关注
     */
    private TextView mBtnFollow;
    /**
     * AdScar
     */
    private TextView mUserName;
    /**
     * 27岁
     */
    private TextView mUserBirth;
    private TextView mUserStatu;
    /**
     * 北京  朝阳
     */
    private TextView mUserAddress;
    private boolean ischeck = false;
    /**
     * 酒吧、民宿老板娘、旅居世界的自由职业者
     */
    private TextView mUserTalk;
    private RecyclerView mRlv;
    private MyRecycleViewAdapter viewAdapter;
    private String publisherId;
    private int fans;
    private int zan;
    int a = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        StatusBarCompat.changeToLightStatusBar(this);
         publisherId = getIntent().getStringExtra("publisherId");
        setContentView(R.layout.activity_saler_profile);
        initView();
        initData();
        initLogic();
    }

    @Override
    protected void initView() {

        mBack = (ImageView) findViewById(R.id.back);
        mBack.setOnClickListener(this);
        mShare = (ImageView) findViewById(R.id.share);
        mShare.setOnClickListener(this);
        mUserHeadImg = (ImageView) findViewById(R.id.user_head_img);

        mUserSexImg = (ImageView) findViewById(R.id.user_sex_img);
        mLove = (TextView) findViewById(R.id.love);
        mFollower = (TextView) findViewById(R.id.follower);
        mCool = (TextView) findViewById(R.id.cool);
        mBtnFollow = (TextView) findViewById(R.id.btn_follow);
        mBtnFollow.setOnClickListener(this);
        mUserName = (TextView) findViewById(R.id.user_name);
        mUserBirth = (TextView) findViewById(R.id.user_birth);
        mUserStatu = (TextView) findViewById(R.id.user_statu);
        mUserAddress = (TextView) findViewById(R.id.user_address);
        mUserTalk = (TextView) findViewById(R.id.user_talk);
        mRlv = (RecyclerView) findViewById(R.id.rlv);
        DeeSpUtil.getInstance().putString("isRefresh","0");

        if (App.userMsg()!=null && App.userMsg().getUserInfo()!=null &&
        App.userMsg().getUserInfo().getUserStatus()!=null &&
        App.userMsg().getUserInfo().getUserStatus().equals("1")){
            mUserStatu.setVisibility(View.VISIBLE);
        }else{
            mUserStatu.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initData() {

        HashMap<String, Object> hashMap = new HashMap<>();
        String userId = DeeSpUtil.getInstance().getString("userId");
        if (userId!=null && !userId.equals("")){
            hashMap.put("userId",userId);
            hashMap.put("ticket",DeeSpUtil.getInstance().getString("ticket"));
        }
        hashMap.put("publisherId",publisherId);
        DataService.getHomeService().getUserHomePageMsg(hashMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult<UserHomePageEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<UserHomePageEntity> baseResult) {
                        if (baseResult.getResult_code() == 0) {
                            if (baseResult.getData()!=null && baseResult.getData().getUserInfo()!=null){
                                UserHomePageEntity.UserInfoBean info = baseResult.getData().getUserInfo();

                                zan = info.getAppreciateAmount();
                                fans = info.getFansCount();
                                mLove.setText(info.getFocusCount()+"");
                                if (info.getIsFocus().equals("1")){
                                    if (fans == 0){
                                        fans++;
                                    }
                                }else{
                                    if (fans==-1){
                                        fans++;
                                    }
                                }
                                mFollower.setText(fans+"");
                                mCool.setText(info.getAppreciateAmount()+"");
                                if (info.getBornAddress()==null || info.getBornAddress().equals("")){
                                    mUserAddress.setText("出生地未知");
                                }else{
                                    mUserAddress.setText(info.getBornAddress());
                                }
                                if (info.getIsFocus().equals("1")){
                                    ischeck = true;
                                    mBtnFollow.setText("已关注");
                                    mBtnFollow.setBackgroundResource(R.drawable.little_red_bg);
                                }else{
                                    ischeck = false;
                                    mBtnFollow.setBackgroundResource(R.drawable.red_bg);
                                    mBtnFollow.setText("关注");
                                }
                                mBtnFollow.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        changeState(ischeck);
                                        if (!ButtonUtils.isFastDoubleClick(R.id.btn_follow,2000)){
                                            if (ischeck){
                                                clickGuanZhu(info.getUserId());
                                            }else{

                                                clickCancle(info.getUserId());
                                            }

                                        }
                                    }
                                });
                                mUserSexImg.setImageResource(info.getGender().equals("1")?R.mipmap.nan:R.mipmap.women);
                                mUserName.setText(info.getNickName());
                                if (info.getBirthday()==null){
                                    mUserBirth.setText("年龄未知");
                                }else{
                                    int year = CalenderUtilss.getYear();
                                    long time = info.getBirthday().getTime();
                                    String s = DateFormatUtils.longToYear(time);
                                    int i = Integer.parseInt(s);
                                    mUserBirth.setText(year-i+"岁");
                                }

                                if (info.getSignature()==null || info.getSignature().equals("")){
                                    mUserTalk.setText("ta还未填写个性签名");
                                }else{
                                    mUserTalk.setText(info.getSignature());
                                }
                                //头像
                                RequestOptions error = new RequestOptions().circleCrop().placeholder(R.mipmap.yiqifan)
                                        .error(R.mipmap.yiqifan);
                                if (!SalerProfileActivity.this.isDestroyed()){
                                    Glide.with(SalerProfileActivity.this).load(info.getPortait())
                                            .apply(error).into(mUserHeadImg);
                                }

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
        DataService.getHomeService().cancleFocus(hashMap)
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

    private void changeState(boolean check) {
        if (check){
            ischeck = false;
            fans--;
            mBtnFollow.setBackgroundResource(R.drawable.red_bg);
            mBtnFollow.setText("关注");
        }else{
            ischeck = true;
            fans++;
            mBtnFollow.setBackgroundResource(R.drawable.little_red_bg);
            mBtnFollow.setText("已关注");
        }
        mFollower.setText(fans+"");
    }

    private void clickGuanZhu(String Id) {

        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("focusUserId",Id);
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

    @Override
    protected void initLogic() {

        HashMap<String, Object> hashMap = new HashMap<>();
        String userId = DeeSpUtil.getInstance().getString("userId");
        if (userId!=null && !userId.equals("")){
            hashMap.put("userId",userId);
            hashMap.put("ticket",DeeSpUtil.getInstance().getString("ticket"));
        }
        hashMap.put("publisherId",publisherId);
        DataService.getHomeService().getSomebodyPushList(hashMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())

                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult<UserPushListEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<UserPushListEntity> baseResult) {
                        if (baseResult.getResult_code() == 0) {
                            Log.e("push",baseResult.toString());
                            if (baseResult.getData()!=null && baseResult.getData().getUserPublishedList()!=null
                            && baseResult.getData().getUserPublishedList().size()>0){

                                mRlv.setLayoutManager(new LinearLayoutManager(SalerProfileActivity.this));
                                mRlv.setHasFixedSize(true);
                                mRlv.setNestedScrollingEnabled(false);
                                viewAdapter = new MyRecycleViewAdapter(SalerProfileActivity.this);
                                viewAdapter.setList(baseResult.getData().getUserPublishedList());
                                mRlv.setAdapter(viewAdapter);
                                viewAdapter.onClick(new MyRecycleViewAdapter.onItemClickListener() {
                                    @Override
                                    public void onClick(int position) {

                                    }

                                    @Override
                                    public void onLoveClick(int position, boolean isSend) {
                                        UserPushListEntity.UserPublishedListBean bean = baseResult.getData().getUserPublishedList().get(position);
                                        if (bean.isLove()==false){
                                            bean.setLove(true);
                                            bean.setStarCount(bean.getStarCount()+1);
                                            viewAdapter.notifyItemChanged(position,R.id.love);
                                            viewAdapter.notifyItemChanged(position,R.id.love_count);
                                        }else{
                                            bean.setLove(false);
                                            bean.setStarCount(bean.getStarCount()-1);
                                            viewAdapter.notifyItemChanged(position,R.id.love);
                                            viewAdapter.notifyItemChanged(position,R.id.love_count);
                                        }
                                    }

                                    @Override
                                    public void onZanClick(int position, boolean isSend) {
                                        UserPushListEntity.UserPublishedListBean bean = baseResult.getData().getUserPublishedList().get(position);
                                        String isAppreciate = bean.getIsAppreciate();

                                        DeeSpUtil.getInstance().putString("isRefresh","1");

                                        if (bean.getIsAppreciate().equals("1")){
                                            bean.setIsAppreciate("0");
                                            bean.setAppreciateCount(bean.getAppreciateCount()-1);
                                            zan--;
                                            mCool.setText(zan+"");
                                            viewAdapter.notifyItemChanged(position,R.id.zan);
                                            viewAdapter.notifyItemChanged(position,R.id.zan_count);
                                            if (isSend && !isAppreciate.equals(bean.getIsAppreciate())){
                                                clickZan(bean.getMyPublishedId(),bean.getFoodId());
                                            }
                                        }else{
                                            zan++;
                                            mCool.setText(zan+"");
                                            bean.setIsAppreciate("1");
                                            bean.setAppreciateCount(bean.getAppreciateCount()+1);
                                            viewAdapter.notifyItemChanged(position,R.id.zan);
                                            viewAdapter.notifyItemChanged(position,R.id.zan_count);
                                            if (isSend && !isAppreciate.equals(bean.getIsAppreciate())){
                                                clickZan(bean.getMyPublishedId(),bean.getFoodId());
                                            }
                                        }
                                    }
                                });
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void clickZan(String Id ,String foodId) {
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.back:
                finish();
                break;
            case R.id.share:
                break;
            case R.id.btn_follow:

                break;
        }
    }
}
