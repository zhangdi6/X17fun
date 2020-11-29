package com.x.x17fun.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.x.x17fun.R;
import com.x.x17fun.adapter.PayTypeAdapter;
import com.x.x17fun.adapter.RechargeMoneyAdapter;
import com.x.x17fun.alipay.PayResult;
import com.x.x17fun.base.App;
import com.x.x17fun.base.AppContant;
import com.x.x17fun.base.BaseFragment;
import com.x.x17fun.data.DataService;
import com.x.x17fun.entity.AliPayEntity;
import com.x.x17fun.entity.BaseEntity;
import com.x.x17fun.entity.BaseResult;
import com.x.x17fun.entity.WeChatPayEntity;
import com.x.x17fun.utils.AdiUtils;
import com.x.x17fun.utils.ParamsUtils;
import com.x.x17fun.utils.SHA1Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChargeFragment extends BaseFragment implements View.OnClickListener {

    private View view;
    private ImageView mBack;
    private SmartRefreshLayout mSmart;
    private RecyclerView mRlvPrice;
    private RecyclerView mRlvType;
    /**
     * 去支付
     */
    private TextView mPay;
    private PayTypeAdapter adapter;
    private RechargeMoneyAdapter adapter2;
    private IWXAPI wxapi;
    private String sn_ali;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            if (msg.what == 0) {
                PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                /**
                 * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                 */
                String resultStatus = payResult.getResultStatus();
                // 判断resultStatus 为9000则代表支付成功
                Log.e("card",payResult.getResult());
                if (TextUtils.equals(resultStatus, "9000")) {
                    showToast("支付成功");
                }else {
                    // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                    AdiUtils.showToast(payResult.getMemo());
                }
            }


        }
    };
    public static ChargeFragment getInstance() {
        return new ChargeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View inflate = inflater.inflate(R.layout.fragment_charge, container, false);
        initView(inflate);
        initData();
        return inflate;
    }

    private void initData() {

        ArrayList<BaseEntity> objects2 = new ArrayList<>();
        objects2.add(new BaseEntity("100",true));
        objects2.add(new BaseEntity("50",false));
        objects2.add(new BaseEntity("30",false));
        objects2.add(new BaseEntity("10",false));

        mRlvPrice.setHasFixedSize(true);
        mRlvPrice.setNestedScrollingEnabled(false);
        mRlvPrice.setLayoutManager(new GridLayoutManager(getContext(),2));
        adapter2 = new RechargeMoneyAdapter();
        mRlvPrice.setAdapter(adapter2);
        adapter2.addData(objects2);
        adapter2.onItemCheck(position -> adapter2.changeState(position));

        ArrayList<BaseEntity> objects = new ArrayList<>();
        objects.add(new BaseEntity("支付宝支付",R.mipmap.sex_yes,R.mipmap.sex_no,
                true,R.mipmap.alipay));
        objects.add(new BaseEntity("微信支付",R.mipmap.sex_yes,R.mipmap.sex_no,
                false,R.mipmap.wechat));


        mRlvType.setHasFixedSize(true);
        mRlvType.setNestedScrollingEnabled(false);
        mRlvType.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PayTypeAdapter();
        mRlvType.setAdapter(adapter);
        adapter.addData(objects);
        adapter.onItemCheck(position -> adapter.changeState(position));



    }

    private void initView(View inflate) {
        mBack = (ImageView) inflate.findViewById(R.id.back);
        mBack.setOnClickListener(this);
        mSmart = (SmartRefreshLayout) inflate.findViewById(R.id.smart);
        mBack.setOnClickListener(this);
        mRlvPrice = (RecyclerView) inflate.findViewById(R.id.rlv_price);
        mRlvType = (RecyclerView) inflate.findViewById(R.id.rlv_type);
        mPay = (TextView) inflate.findViewById(R.id.pay);
        mPay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.back:
                pop();
                break;
            case R.id.pay:
                int checkedPosition = adapter2.getCheckedPosition();
                String title = adapter2.mList.get(checkedPosition).getTitle();
                float parseFloat = Float.parseFloat(title);
                //支付宝
                if (adapter.getCheckedPosition()==0){
                    aliPay(parseFloat);
                }else{
                    wechatPay(parseFloat);
                }
                break;
        }
    }

    private void wechatPay(float parseFloat) {
        /**
         * 进行支付
         * @param money 单位为元
         */
            HashMap hashMap = ParamsUtils.getParamsMap("chargeAmount", parseFloat);

            DataService.getHomeService().wechatPay(hashMap)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseResult<WeChatPayEntity>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BaseResult<WeChatPayEntity> baseResult) {
                            Log.e("card", baseResult.toString());
                            if (baseResult.getResult_code() == 0) {
                                WeChatPayEntity data = baseResult.getData();


                                if (!App.mWxApi.isWXAppInstalled()){
                                    AdiUtils.showToast("请您先安装微信，然后才能使用微信支付");
                                }else if (baseResult.getResult_code()== -3){
                                    AdiUtils.loginOut();
                                }else{
                                    wxapi = WXAPIFactory.createWXAPI(getContext(), null);
                                    wxapi.registerApp(AppContant.WXAPPID);
                                    PayReq req = new PayReq();
                                    //req.appId = "wxf8b4f85f3a794e77";  // 测试用appId
                                    req.appId			= data.getAppid();
                                    req.partnerId		= data.getMch_id();
                                    req.prepayId		=  data.getPrepay_id();
                                    req.nonceStr		= data.getNonce_str();
                                    req.timeStamp		= data.getTimestamp();
                                    //默认
                                    req.packageValue	= "Sign=WXPay";
                                    req.sign			= data.getSign();
                                    wxapi.sendReq(req);

                                }
                            } else {
                                AdiUtils.showToast("支付失败，"+(baseResult.getResult_msg()==null?"请您重试":baseResult.getResult_msg()));
                            }
                        }

                        @Override
                        public void onError(Throwable e)   {
                            Log.e("card", e.getMessage());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });


    }

    private void aliPay(float parseFloat) {
        HashMap hashMap = ParamsUtils.getParamsMap("chargeAmount", parseFloat);

        DataService.getHomeService().aliPay(hashMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult<AliPayEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<AliPayEntity> baseResult) {

                        Log.e("card", baseResult.toString());
                        if (baseResult.getResult_code() == 0) {
                            sn_ali = baseResult.getData().getPrePayMessage();
                            Runnable payRunnable = () -> {
                                PayTask alipay = new PayTask(getActivity());
                                Map<String, String> result = alipay.payV2(sn_ali, true);
                                Message msg = new Message();
                                msg.what = 0;
                                msg.obj = result;
                                mHandler.sendMessage(msg);
                            };
                            Thread payThread = new Thread(payRunnable);
                            payThread.start();
                        } else if (baseResult.getResult_code()== -3){
                            AdiUtils.loginOut();
                        }else {

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("card", e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
