package com.x.x17fun.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.x.x17fun.adapter.AccoutTypeAdapter;
import com.x.x17fun.adapter.CardListAdapter;
import com.x.x17fun.base.App;
import com.x.x17fun.base.BaseDialog;
import com.x.x17fun.base.BaseFragment;
import com.x.x17fun.base.IBaseCallBack;
import com.x.x17fun.data.DataService;
import com.x.x17fun.entity.BaseEntity;
import com.x.x17fun.entity.BaseResult;
import com.x.x17fun.entity.BuyInOrderEntity;
import com.x.x17fun.entity.CardListEnity;
import com.x.x17fun.entity.OrderDetailEntity;
import com.x.x17fun.entity.UserSyncEntity;
import com.x.x17fun.ui.activity.UpdatePayPwdActivity;
import com.x.x17fun.utils.AdiUtils;
import com.x.x17fun.utils.DateFormatUtils;
import com.x.x17fun.utils.DeeSpUtil;
import com.x.x17fun.utils.ParamsUtils;
import com.x.x17fun.utils.StringUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReflectFragment extends BaseFragment implements View.OnClickListener {

    private View view;
    private ImageView mBack;
    private SmartRefreshLayout mSmart;
    /**
     * 微信账户：zdzjbnb
     */
    private TextView mBtnAccount;
    private EditText mEtPrice;
    /**
     * 当前可结算金额为1313元
     */
    private TextView mCanbeCountPrice;
    /**
     * 提现
     */
    private TextView mBtnCount;
    private BaseDialog baseDialog;
    private BaseDialog baseDialog2;
    private EditText curEditText;

    private EditText et_verify_code_1;
    private EditText et_verify_code_2;
    private EditText et_verify_code_3;
    private EditText et_verify_code_4;
    private EditText et_verify_code_5;
    private EditText et_verify_code_6;
    private TextView mTag;
    private float totalCoinCount;
    private BaseDialog baseDialog3;
    private String bank = "";
    private String mNo = "";
    private String mName = "";
    private float coinAmount = 0;
    private TextView history;
    private String mType = "";

    public static ReflectFragment getInstance() {
        return new ReflectFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_reflect, container, false);
        initView(inflate);
        return inflate;
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        syncUser(new IBaseCallBack<UserSyncEntity>() {
            @Override
            public void onSuccess(UserSyncEntity data) {
                 coinAmount = data.getUserInfo().getCoinAmount();
                 coinAmount = coinAmount - data.getUserInfo().getFrozenAmount() - data.getUserInfo().getFrozenIncomeAmount();
                mCanbeCountPrice.setText("当前可结算金额为"+coinAmount+"元");
            }

            @Override
            public void onFail(String msg) {

            }
        });
        initData();
    }

    private void initData() {

        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("userId", DeeSpUtil.getInstance().getString("userId"));
        hashMap.put("ticket",DeeSpUtil.getInstance().getString("ticket"));


        DataService.getHomeService().getPayCardList(hashMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult<CardListEnity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<CardListEnity> baseResult) {
                        if (baseResult.getResult_code() == 0) {
                            Log.e("zan",baseResult.toString());
                            if (baseResult.getData()!=null && baseResult.getData().getMyPayCards()!=null
                                    && baseResult.getData().getMyPayCards().size()>0){
                                CardListEnity.MyPayCardsBean cardsBean = baseResult.getData().getMyPayCards().get(0);
                                if (cardsBean.getOuterAccountType().equals("1")){
                                    mBtnAccount.setText("微信账户："+cardsBean.getAccountNo());
                                }else if (cardsBean.getOuterAccountType().equals("2")){
                                    mBtnAccount.setText("支付宝账户："+cardsBean.getAccountNo());
                                }else{
                                    mBtnAccount.setText("银行卡账户："+cardsBean.getAccountNo());
                                    bank = cardsBean.getBank();
                                }
                                mNo = cardsBean.getAccountNo();
                                mName = cardsBean.getAccountName();
                                mType = cardsBean.getOuterAccountType();
                                List<CardListEnity.MyPayCardsBean> payCards = baseResult.getData().getMyPayCards();
                                ArrayList<BaseEntity> objects = new ArrayList<>();
                                for (int i = 0; i < payCards.size(); i++) {
                                    CardListEnity.MyPayCardsBean bean = payCards.get(i);
                                    String type = "";
                                    if (bean.getOuterAccountType().equals("1")){
                                       type = "微信";
                                    }else if (bean.getOuterAccountType().equals("2")){
                                        type = "支付宝";
                                    }else{
                                        type = "银行卡";
                                    }
                                    if (i==0){
                                        objects.add(new BaseEntity(type,bean.getAccountNo(),
                                                R.mipmap.sex_yes,R.mipmap.sex_no,true));
                                    }else{
                                        objects.add(new BaseEntity(type,bean.getAccountNo(),
                                                R.mipmap.sex_yes,R.mipmap.sex_no,false));
                                    }

                                }

                                View inflate = getLayoutInflater().inflate(R.layout.dialog_account, null);
                                RecyclerView rlv = inflate.findViewById(R.id.rlv);
                                rlv.setLayoutManager(new LinearLayoutManager(getContext()));
                                AccoutTypeAdapter typeAdapter = new AccoutTypeAdapter();
                                typeAdapter.addData(objects);
                                rlv.setAdapter(typeAdapter);
                                baseDialog = new BaseDialog(getActivity(), inflate, Gravity.BOTTOM);
                                typeAdapter.onItemCheck(new AccoutTypeAdapter.onItemCheckedListener() {
                                    @Override
                                    public void onItemCheck(int position) {
                                        typeAdapter.changeState(position);
                                        CardListEnity.MyPayCardsBean myPayCards = baseResult.getData().getMyPayCards().get(position);
                                        if (myPayCards.getOuterAccountType().equals("1")){
                                            mBtnAccount.setText("微信账户："+myPayCards.getAccountNo());
                                        }else if (myPayCards.getOuterAccountType().equals("2")){
                                            mBtnAccount.setText("支付宝账户："+myPayCards.getAccountNo());
                                        }else{
                                            mBtnAccount.setText("银行卡账户："+myPayCards.getAccountNo());
                                            bank = myPayCards.getBank();
                                        }
                                        mNo = myPayCards.getAccountNo();
                                        mName = myPayCards.getAccountName();
                                        mType = myPayCards.getOuterAccountType();
                                        baseDialog.dismiss();
                                    }
                                });
                                mBtnAccount.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        baseDialog.show();

                                    }
                                });

                            }else{
                                mBtnAccount.setText("暂无账户，请添加");
                                mBtnAccount.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        start(AddAccountFragment.getInstance());
                                    }
                                });
                            }

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

    private void initView(View inflate) {
        mBack = (ImageView) inflate.findViewById(R.id.back);
        history = (TextView) inflate.findViewById(R.id.history);
        mBack.setOnClickListener(v -> pop());
        /*mSmart = (SmartRefreshLayout) inflate.findViewById(R.id.smart);*/
        mBack.setOnClickListener(this);
        mBtnAccount = (TextView) inflate.findViewById(R.id.btn_account);
        mEtPrice = (EditText) inflate.findViewById(R.id.et_price2);
        mCanbeCountPrice = (TextView) inflate.findViewById(R.id.canbe_count_price);
        mBtnCount = (TextView) inflate.findViewById(R.id.btn_count);
        mBtnCount.setOnClickListener(this);
        history.setOnClickListener(this);
        AdiUtils.setEditTextTwoPoint(mEtPrice);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.back:
                pop();
                break;
            case R.id.history:
                start(RefrectHistotyFragment.newInstance());
                break;
            case R.id.btn_count:

                BigDecimal bigDecimal = StringUtil.parseDecimal(mEtPrice.getText().toString().trim(), BigDecimal.ZERO);
                if (bigDecimal.compareTo(BigDecimal.ZERO)<=0){
                    showToast("请输入有效的支付金额");
                    break;
                }
                String s = mEtPrice.getText().toString();
                float v1 = Float.parseFloat(s);
                if (v1 >= 0.1) {
                     totalCoinCount = v1;
                }else{
                    showToast("单次提现金额不得小于0.1元!");
                    break;
                }
                if (coinAmount > Float.parseFloat(mEtPrice.getText().toString())){
                }else{
                    showToast("您的可提现金额不足");
                    break;
                }
                if (App.userMsg().getUserInfo().getPayPassword().equals("true")){
                   showKeyBord();
                }else{
                    View inflate = getLayoutInflater().inflate(R.layout.dialog_no_paypwd, null);
                    RelativeLayout close = inflate.findViewById(R.id.ll);

                    TextView sure = inflate.findViewById(R.id.sure);

                    TextView cancle = inflate.findViewById(R.id.cancle);
                    close.setOnClickListener(a -> baseDialog3.dismiss());
                    cancle.setOnClickListener(a -> baseDialog3.dismiss());

                    sure.setOnClickListener(a -> {
                        Intent intent = new Intent(getActivity(), UpdatePayPwdActivity.class);
                        startActivity(intent);
                        baseDialog3.dismiss();
                    });
                    baseDialog3 = new BaseDialog(getActivity(), inflate, Gravity.CENTER);
                    baseDialog3.show();
                }

                break;
        }
    }

    private void showKeyBord() {

        View inflate = getLayoutInflater().inflate(R.layout.dialog_pay_pwd, null);
        ImageView close = inflate.findViewById(R.id.close);
        RelativeLayout null_layout = inflate.findViewById(R.id.null_layout);
        et_verify_code_1 = (EditText)inflate.findViewById(R.id.et_verify_code_1);
        et_verify_code_2 = inflate.findViewById(R.id.et_verify_code_2);
        et_verify_code_3 = inflate.findViewById(R.id.et_verify_code_3);
        et_verify_code_4 = inflate.findViewById(R.id.et_verify_code_4);
        RelativeLayout layout = inflate.findViewById(R.id.layout);
        et_verify_code_5 = inflate.findViewById(R.id.et_verify_code_5);
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

        baseDialog2 = new BaseDialog(getActivity(), inflate, Gravity.CENTER);
        baseDialog2.show();
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseDialog2.dismiss();
            }
        });
        mTag.postDelayed(new Runnable() {
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



        HashMap hashMap = ParamsUtils.getParamsMap();
        hashMap.put("withdrawAmount",totalPrice);
        hashMap.put("withdrawTo",mType);
        hashMap.put("accountNo",mNo);
        hashMap.put("accountName",mName);
        if (bank !=null && !"".equals(bank)){
            hashMap.put("bank",bank);
        }
        hashMap.put("payPassword",payPassword);

        DataService.getHomeService().getMoney(hashMap)
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
                            mTag.setVisibility(View.GONE);
                            showToast("提现成功");
                            baseDialog2.dismiss();
                            pop();
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

                        }else {
                            baseDialog2.dismiss();
                            showToast(baseResult.getResult_msg());
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
}
