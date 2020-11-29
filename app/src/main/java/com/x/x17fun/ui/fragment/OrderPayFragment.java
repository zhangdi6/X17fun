package com.x.x17fun.ui.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.x.x17fun.R;
import com.x.x17fun.adapter.PayTypeAdapter;
import com.x.x17fun.adapter.UserReceiveAddressAdapter;
import com.x.x17fun.base.App;
import com.x.x17fun.base.BaseDialog;
import com.x.x17fun.base.BaseFragment;
import com.x.x17fun.custom.RoundImageView4dp;
import com.x.x17fun.custom.statubar.StatusBarCompat;
import com.x.x17fun.data.DataService;
import com.x.x17fun.entity.BaseEntity;
import com.x.x17fun.entity.BaseResult;
import com.x.x17fun.entity.BuyInOrderEntity;
import com.x.x17fun.entity.MyAddessEntity;
import com.x.x17fun.entity.OrderDetailEntity;
import com.x.x17fun.ui.activity.AddAddressActivity;
import com.x.x17fun.ui.activity.MainActivity;
import com.x.x17fun.ui.activity.UpdateInfoActivity;
import com.x.x17fun.ui.activity.UpdatePayPwdActivity;
import com.x.x17fun.utils.AdiUtils;
import com.x.x17fun.utils.DateFormatUtils;
import com.x.x17fun.utils.DeeSpUtil;
import com.x.x17fun.utils.ParamsUtils;
import com.x.x17fun.utils.SHA1Utils;
import com.x.x17fun.utils.StringUtil;

import org.simpleframework.xml.Order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderPayFragment extends BaseFragment implements View.OnClickListener {

    private View view;
    private ImageView mBack;
    /**
     * 请选择收货地址
     */
    private TextView mHadNoAddress;
    /**
     * 马化腾
     */
    private TextView mUserName;
    /**
     * 北京  北京市  朝阳区  望京SOHO塔2C座506室
     */
    private TextView mUserAddress;
    /**
     * 修改
     */
    private TextView mUpdate;
    private ImageView mSalerImg;
    /**
     * ¥12.50
     */
    private TextView mPrice;
    /**
     * 共1件
     */
    private TextView mTotalSaleCount;
    /**
     * TextView
     */
    private TextView mMealName;
    /**
     * GOGO的小食堂
     */
    private TextView mSalerName;
    private RoundImageView4dp mImg;
    private ImageView mBtnJian;
    private ImageView mBtnJia;
    /**
     * 1
     */
    private TextView mCount;
    /**
     * 和商家协商一致后留言，最多100个字
     */
    private EditText mEtOther;
    private SmartRefreshLayout mSmartRefreshLayout;
    /**
     * ¥ 12.50
     */
    private TextView mAllPrice;
    /**
     * 抢先支付 05:00
     */
    private TextView mBtnPay;
    private RecyclerView mRlv;
    private PayTypeAdapter adapter;

    private int a = 1;
    private ArrayList<BaseEntity> objects;
    private RecyclerView address;
    private BaseDialog addressDialog;
    private UserReceiveAddressAdapter receiveAddressAdapter;
    private Float shop_price;
    private int shop_count;
    private String receiverGender;
    private float coinAmount;
    private float totalCoinCount;
    private BaseDialog baseDialog;
    private EditText curEditText;

    private EditText et_verify_code_1;
    private EditText et_verify_code_2;
    private EditText et_verify_code_3;
    private EditText et_verify_code_4;
    private EditText et_verify_code_5;
    private EditText et_verify_code_6;
    private BaseDialog baseDialog2;
    private TextView mTag;
    private String phone;
    private String name;
    private String publishedId;
    private String shop_prodeuct_img;
    private String shop_head;
    private int hasno = 0;

    public static OrderPayFragment getInstance(int type ,String shop_head ,
                                               String shop_name , String shop_product,
                                               String shop_prodeuct_img,float shop_price,int shop_count,String id) {
        OrderPayFragment orderPayFragment = new OrderPayFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putInt("shop_count", shop_count);
        bundle.putString("shop_head", shop_head);
        bundle.putString("shop_name", shop_name);
        bundle.putString("id", id);
        bundle.putString("shop_product", shop_product);
        bundle.putString("shop_prodeuct_img", shop_prodeuct_img);
        bundle.putFloat("shop_price", shop_price);
        orderPayFragment.setArguments(bundle);
        return orderPayFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_order_pay, container, false);
        StatusBarCompat.setStatusBarColor(getActivity(),Color.WHITE);
        StatusBarCompat.changeToLightStatusBar(getActivity());
        initView(inflate);
        initPayType();
        loadAddress();
        DeeSpUtil.getInstance().putString("isRefresh","0");
        return inflate;
    }

    private void initView(View inflate) {

        mBack = (ImageView) inflate.findViewById(R.id.back);
        mBack.setOnClickListener(this);
        mHadNoAddress = (TextView) inflate.findViewById(R.id.had_no_address);
        mUserName = (TextView) inflate.findViewById(R.id.user_name);
        mUserAddress = (TextView) inflate.findViewById(R.id.user_address);
        mUpdate = (TextView) inflate.findViewById(R.id.update);
        mUpdate.setOnClickListener(this);
        mSalerImg = (ImageView) inflate.findViewById(R.id.saler_img);

        mPrice = (TextView) inflate.findViewById(R.id.price);
        mTotalSaleCount = (TextView) inflate.findViewById(R.id.total_sale_count);
        mMealName = (TextView) inflate.findViewById(R.id.meal_name);
        mSalerName = (TextView) inflate.findViewById(R.id.saler_name);
        mImg = (RoundImageView4dp) inflate.findViewById(R.id.img);

        mBtnJian = (ImageView) inflate.findViewById(R.id.btn_jian);
        mBtnJian.setOnClickListener(this);
        mBtnJia = (ImageView) inflate.findViewById(R.id.btn_jia);
        mBtnJia.setOnClickListener(this);
        mCount = (TextView) inflate.findViewById(R.id.count);
        mEtOther = (EditText) inflate.findViewById(R.id.et_other);
        mSmartRefreshLayout = (SmartRefreshLayout) inflate.findViewById(R.id.smartRefreshLayout);
        mAllPrice = (TextView) inflate.findViewById(R.id.all_price);
        mBtnPay = (TextView) inflate.findViewById(R.id.btn_pay);
        mRlv = (RecyclerView) inflate.findViewById(R.id.rlv);
        mBtnPay.setOnClickListener(this);

        int type = getArguments().getInt("type");
         shop_head = getArguments().getString("shop_head");
        String shop_name = getArguments().getString("shop_name");
        String shop_product = getArguments().getString("shop_product");
        String id = getArguments().getString("id");
         shop_prodeuct_img = getArguments().getString("shop_prodeuct_img");
        shop_price = getArguments().getFloat("shop_price");
        shop_count = getArguments().getInt("shop_count");
        loadCircleImg(shop_head,mSalerImg);
        loadImg(shop_prodeuct_img,mImg);
        mMealName.setText(shop_product);
        mSalerName.setText(shop_name);
        mPrice.setText("¥"+shop_price);
        mAllPrice.setText("¥"+shop_price);
        totalCoinCount = shop_price;
        mTotalSaleCount.setText("共"+shop_count+"件");

        publishedId = id;
        mEtOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UpdateInfoActivity.class);
                intent.putExtra("type","other");
                startActivityForResult(intent,200);
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200){
            if (resultCode == 500){
                mEtOther.setText(data.getStringExtra("name"));
            }
        }
    }

    @Override
    public void onSupportVisible() {

        super.onSupportVisible();


    }
    private void initPayType() {
        objects = new ArrayList<>();
        /*objects.add(new BaseEntity("支付宝",R.mipmap.xuanze,R.mipmap.weixuan,
                true,R.mipmap.alipay));
         objects.add(new BaseEntity("微信",R.mipmap.xuanze,R.mipmap.weixuan,
                false,R.mipmap.wechat));*/
        objects.add(new BaseEntity("钱包",R.mipmap.xuanze,R.mipmap.weixuan,
                false,R.mipmap.qianbao));

        mRlv.setHasFixedSize(true);
        mRlv.setNestedScrollingEnabled(false);
        mRlv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PayTypeAdapter();
        mRlv.setAdapter(adapter);
        adapter.addData(objects);
        adapter.onItemCheck(position -> adapter.changeState(position));
    }

    private void initAddressDialog() {
        View inflate = LayoutInflater.from(_mActivity).inflate(R.layout.dialog_address, null);
        TextView cancle = inflate.findViewById(R.id.cancle);
        TextView sure = inflate.findViewById(R.id.sure);
        address = inflate.findViewById(R.id.address);


        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addressDialog.dismiss();
            }
        });

        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startToActivity(getContext(), AddAddressActivity.class);
            }
        });
        loadAddress();
        if (addressDialog==null){
            addressDialog = new BaseDialog(_mActivity, inflate, Gravity.BOTTOM);
        }
        addressDialog.show();
    }

    private void loadAddress() {
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
                                mUpdate.setText("修改");
                                mUserAddress.setVisibility(View.VISIBLE);
                                mUserName.setVisibility(View.VISIBLE);

                                hasno = 1;
                               if (addressDialog==null){
                                   MyAddessEntity.ReceiveListBean receiveListBean = baseResult.getData().getReceiveList().get(0);
                                   mUserAddress.setText(receiveListBean.getReceiverAera()+receiveListBean.getReceiverAddress());
                                   phone = receiveListBean.getReceiverPhone();
                                   name = receiveListBean.getReceiverName();
                                   mUserName.setText(name+ "  "+
                                           phone);
                                    receiverGender = receiveListBean.getReceiverGender();
                               }else {
                                   if (receiveAddressAdapter!=null){
                                       receiveAddressAdapter.addData(baseResult.getData().getReceiveList());
                                   }else{

                                       receiveAddressAdapter = new UserReceiveAddressAdapter();
                                       address.setLayoutManager(new LinearLayoutManager(getActivity()));
                                       address.setAdapter(receiveAddressAdapter);
                                       receiveAddressAdapter.addData(baseResult.getData().getReceiveList());

                                       receiveAddressAdapter.onClick(new UserReceiveAddressAdapter.onItemClickListener() {
                                           @Override
                                           public void onClick(int position) {
                                               MyAddessEntity.ReceiveListBean receiveListBean = receiveAddressAdapter.mList.
                                                       get(position);
                                               mUserAddress.setText(receiveListBean.getReceiverAera()+receiveListBean.getReceiverAddress());
                                                phone = receiveListBean.getReceiverPhone();
                                                name = receiveListBean.getReceiverName();
                                               mUserName.setText(name + "   "+
                                                       phone);
                                               receiverGender = receiveListBean.getReceiverGender();
                                               addressDialog.dismiss();
                                           }
                                       });
                                   }
                               }




                            }else{
                                mHadNoAddress.setVisibility(View.VISIBLE);
                                mUpdate.setText("添加");
                                mUserAddress.setVisibility(View.GONE);
                                mUserName.setVisibility(View.GONE);
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

    @Override
    public void onResume() {
        super.onResume();
        if (address!=null){
            loadAddress();
        }
    }
    private void payPassword() {

        View inflate = getLayoutInflater().inflate(R.layout.dialog_pay_pwd, null);
        ImageView close = inflate.findViewById(R.id.close);
        RelativeLayout null_layout = inflate.findViewById(R.id.null_layout);
        et_verify_code_1 = (EditText)inflate.findViewById(R.id.et_verify_code_1);
        et_verify_code_2 = inflate.findViewById(R.id.et_verify_code_2);
        et_verify_code_3 = inflate.findViewById(R.id.et_verify_code_3);
        et_verify_code_4 = inflate.findViewById(R.id.et_verify_code_4);
        et_verify_code_5 = inflate.findViewById(R.id.et_verify_code_5);
        RelativeLayout layout = inflate.findViewById(R.id.layout);
        et_verify_code_6 = inflate.findViewById(R.id.et_verify_code_6);
        EditText et_verify_code_7 = inflate.findViewById(R.id.et_verify_code_7);

        initEditText();
        mTag = inflate.findViewById(R.id.tag_text);
        TextView mForget = inflate.findViewById(R.id.forget);
        mForget.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), UpdatePayPwdActivity.class);
            startActivity(intent);
            baseDialog2.dismiss();
        });
        TextView price = inflate.findViewById(R.id.price);
        price.setText(totalCoinCount+"");
        mTag.setVisibility(View.GONE);
        close.setOnClickListener(v -> baseDialog2.dismiss());

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseDialog2.dismiss();
            }
        });
        baseDialog2 = new BaseDialog(getActivity(), inflate, Gravity.CENTER);
        baseDialog2.show();
        et_verify_code_1.postDelayed(new Runnable() {
            @Override
            public void run() {
                baseDialog2.showKeyboard(et_verify_code_1);
            }
        },300);

    }

    private void initEditText() {
        //处理验证码输入
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.length() > 0 && curEditText != null) {
                    if (curEditText.getId() == R.id.et_verify_code_1) {
                        et_verify_code_2.requestFocus();
                    } else if (curEditText.getId() == R.id.et_verify_code_2) {
                        et_verify_code_3.requestFocus();
                    } else if (curEditText.getId() == R.id.et_verify_code_3) {
                        et_verify_code_4.requestFocus();
                    } else if (curEditText.getId() == R.id.et_verify_code_4) {
                        et_verify_code_5.requestFocus();
                    } else if (curEditText.getId() == R.id.et_verify_code_5) {
                        et_verify_code_6.requestFocus();
                    } else if (curEditText.getId() == R.id.et_verify_code_6) {
                        onVerifyCode(totalCoinCount+"");
                    }
                }
            }
        };

        View.OnKeyListener onKeyListener = (view, i, keyEvent) -> {
            if (view instanceof EditText) {
                EditText curEdit = (EditText) view;
                if (i == KeyEvent.KEYCODE_DEL) {
                    if (curEdit.getText().length() > 0) {
                        curEdit.setText(null);
                    } else {
                        if (curEdit.getId() == R.id.et_verify_code_2) {
                            et_verify_code_1.setText(null);
                            et_verify_code_1.requestFocus();
                        } else if (curEdit.getId() == R.id.et_verify_code_3) {
                            et_verify_code_2.setText(null);
                            et_verify_code_2.requestFocus();
                        } else if (curEdit.getId() == R.id.et_verify_code_4) {
                            et_verify_code_3.setText(null);
                            et_verify_code_3.requestFocus();
                        } else if (curEdit.getId() == R.id.et_verify_code_5) {
                            et_verify_code_4.setText(null);
                            et_verify_code_4.requestFocus();
                        } else if (curEdit.getId() == R.id.et_verify_code_6) {
                            et_verify_code_5.setText(null);
                            et_verify_code_5.requestFocus();
                        }
                    }
                } else if (i == KeyEvent.KEYCODE_ENTER) {
                    onVerifyCode(totalCoinCount+"");
                }
            }
            return false;
        };

        et_verify_code_1.setOnKeyListener(onKeyListener);
        et_verify_code_2.setOnKeyListener(onKeyListener);
        et_verify_code_3.setOnKeyListener(onKeyListener);
        et_verify_code_4.setOnKeyListener(onKeyListener);
        et_verify_code_5.setOnKeyListener(onKeyListener);
        et_verify_code_6.setOnKeyListener(onKeyListener);

        et_verify_code_1.addTextChangedListener(textWatcher);
        et_verify_code_2.addTextChangedListener(textWatcher);
        et_verify_code_3.addTextChangedListener(textWatcher);
        et_verify_code_4.addTextChangedListener(textWatcher);
        et_verify_code_5.addTextChangedListener(textWatcher);
        et_verify_code_6.addTextChangedListener(textWatcher);

        View.OnFocusChangeListener fouceListener = (view, b) -> {
            if (b && view instanceof EditText) {
                curEditText = (EditText) view;
            }
        };
        et_verify_code_5.setOnFocusChangeListener(fouceListener);
        et_verify_code_6.setOnFocusChangeListener(fouceListener);
        et_verify_code_3.setOnFocusChangeListener(fouceListener);
        et_verify_code_4.setOnFocusChangeListener(fouceListener);
        et_verify_code_1.setOnFocusChangeListener(fouceListener);
        et_verify_code_2.setOnFocusChangeListener(fouceListener);
    }

    private void onVerifyCode(String totalPrice) {
        final String code = et_verify_code_1.getText().toString()
                +et_verify_code_2.getText().toString()
                +et_verify_code_3.getText().toString()
                +et_verify_code_4.getText().toString()
                +et_verify_code_5.getText().toString()
                +et_verify_code_6.getText().toString();
        onPay(code,totalPrice);
    }


    private void onPay(String payPassword , String totalPrice){



        HashMap<String , Object> hashMap = new HashMap<>();
        hashMap.put("payPassword",payPassword);
        hashMap.put("publishedId",publishedId);
        hashMap.put("userId",DeeSpUtil.getInstance().getString("userId"));
        hashMap.put("ticket",DeeSpUtil.getInstance().getString("ticket"));
        hashMap.put("purchaseCount",a);
        hashMap.put("orderNote",mEtOther.getText().toString().trim());
        hashMap.put("receiverPhone",phone);
        hashMap.put("receiverGender",receiverGender);
        hashMap.put("receiverName",name);
        hashMap.put("receiverAddress",mUserAddress.getText().toString().trim());
        DataService.getHomeService().pay(hashMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult<OrderDetailEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<OrderDetailEntity> baseResult) {

                        Log.e("result",baseResult.toString());
                        if (baseResult.getResult_code() == 0) {
                            mTag.setVisibility(View.GONE);
                            OrderDetailEntity.OrderDetailBean orderDetail = baseResult.getData().getOrderDetail();
                            showToast("支付成功");
                            BuyInOrderEntity.PurchaseOrdersBean bean = new BuyInOrderEntity.PurchaseOrdersBean();
                            bean.setDisplayUrl(shop_prodeuct_img);
                            bean.setPortait(shop_head);
                            bean.setCreatetime(DateFormatUtils.longToDate(orderDetail.getCreatetime().getTime(),DateFormatUtils.FORMAT_9));
                            bean.setOrderNote(mEtOther.getText().toString());
                            bean.setNickName(mSalerName.getText().toString());
                            bean.setProductName(orderDetail.getProductName());
                            bean.setTotalCost(orderDetail.getTotalCost());
                            bean.setTotalSaleCost(orderDetail.getTotalSaleCost());
                            bean.setReceiverName(orderDetail.getReceiverName());
                            bean.setReceiverAddress(orderDetail.getReceiverAddress());
                            bean.setReceiverPhone(orderDetail.getReceiverPhone());
                            bean.setReceiverGender(orderDetail.getReceiverGender());
                            bean.setOrderId(orderDetail.getOrderId());
                            bean.setOrderStatus("0");
                            bean.setPurchaseCount(a);
                            start(OrderDetailFragment.getInstance(bean));
                            baseDialog2.dismiss();
                        }else if (baseResult.getResult_code()== -3){
                            AdiUtils.loginOut();
                        }else if (baseResult.getResult_code()==-1 && baseResult.getResult_msg().equals("密码不正确，请重新输入")){
                            mTag.setVisibility(View.VISIBLE);
                            et_verify_code_1.requestFocus();
                            et_verify_code_2.setText(null);
                            et_verify_code_1.setText(null);
                            et_verify_code_3.setText(null);
                            et_verify_code_4.setText(null);
                            et_verify_code_5.setText(null);
                            et_verify_code_6.setText(null);

                        }else if (baseResult.getResult_code()== -1 ){
                            showToast(baseResult.getResult_msg());
                            baseDialog2.dismiss();
                        }else {
                            baseDialog2.dismiss();

                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    private void initDialogNoMoney(String totalBanlance) {
        View inflate = getLayoutInflater().inflate(R.layout.dialog_no_money, null);
        ImageView close = inflate.findViewById(R.id.close);
        //充值
        TextView recharge = inflate.findViewById(R.id.go_to_recherge);
        //剩余伊甸币
        TextView total_money = inflate.findViewById(R.id.money_total);
        close.setOnClickListener(v -> baseDialog.dismiss());
        total_money.setText("剩余"+totalBanlance+"元");
        recharge.setOnClickListener(v -> {
            showToast("充值功能暂未开放，敬请期待");
            /*Intent intent = new Intent(getActivity(), CardRechageActivity.class);
            startActivity(intent);*/
            baseDialog.dismiss();
        });
        baseDialog = new BaseDialog(getActivity(), inflate, Gravity.CENTER);
        baseDialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.back:
                pop();
                break;
            case R.id.update:
                if (hasno == 1){
                    initAddressDialog();
                }else{
                    startToActivity(getContext(), AddAddressActivity.class);
                }

                break;
            case R.id.btn_jian:
                if (a < shop_count){
                    a++;
                    mCount.setText(a+"");
                    totalCoinCount = (float)a * shop_price;
                    mAllPrice.setText("¥"+ totalCoinCount);
                }
                break;
            case R.id.btn_jia:
                if (a>1){
                    a--;
                    mCount.setText(a+"");
                    totalCoinCount = (float)a * shop_price;
                    mAllPrice.setText("¥"+ totalCoinCount);
                }

                break;
            case R.id.btn_pay:
                int checkedPosition = adapter.getCheckedPosition();
                if (checkedPosition==0){
                    coinAmount = App.userMsg().getUserInfo().getCoinAmount();
                    //如果余额不足
                    if (coinAmount < totalCoinCount){
                        initDialogNoMoney(totalCoinCount+"");
                    }else{
                        //如果有支付密码
                        if ( App.userMsg().getUserInfo().getPayPassword().equals("true")){
                            //将以上参数排序，拼接keySecret
                            payPassword();
                            //没有支付密码
                        }else{
                            View inflate = getLayoutInflater().inflate(R.layout.dialog_no_paypwd, null);
                            RelativeLayout close = inflate.findViewById(R.id.ll);

                            TextView sure = inflate.findViewById(R.id.sure);

                            TextView cancle = inflate.findViewById(R.id.cancle);
                            close.setOnClickListener(a -> baseDialog.dismiss());
                            cancle.setOnClickListener(a -> baseDialog.dismiss());

                        sure.setOnClickListener(a -> {
                            Intent intent = new Intent(getActivity(), UpdatePayPwdActivity.class);
                            startActivity(intent);
                            baseDialog.dismiss();
                        });
                            baseDialog = new BaseDialog(getActivity(), inflate, Gravity.CENTER);
                            baseDialog.show();                }
                    }
                }else{
                    showToast("暂未开放，请选择钱包支付");
                }

                /*int checkedPosition = adapter.getCheckedPosition();
                showToast(objects.get(checkedPosition).getTitle());*/
        }
    }
}
