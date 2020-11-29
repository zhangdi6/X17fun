package com.x.x17fun.ui.fragment;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.x.x17fun.R;
import com.x.x17fun.adapter.CardListAdapter;
import com.x.x17fun.base.BaseFragment;
import com.x.x17fun.data.DataService;
import com.x.x17fun.entity.BaseResult;
import com.x.x17fun.entity.CardListEnity;
import com.x.x17fun.utils.DeeSpUtil;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class BankCardFragment extends BaseFragment implements View.OnClickListener {

    private View view;
    private ImageView mBack;
    private ImageView mAdd;
    private RecyclerView mRlv;
    private SmartRefreshLayout mSmart;
    private ConstraintLayout loadfile;
    private CardListAdapter listAdapter;

    public static BankCardFragment getInstance(){
        return new BankCardFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_bank_card, container, false);
        initView(inflate);
        initData();
        return inflate;
    }

    @Override
    public void onSupportVisible() {
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
                                loadfile.setVisibility(View.GONE);
                                mSmart.setVisibility(View.VISIBLE);
                                if (listAdapter==null){
                                    listAdapter = new CardListAdapter();
                                    mRlv.setAdapter(listAdapter);
                                    listAdapter.addData(baseResult.getData().getMyPayCards());
                                }else{
                                    listAdapter.addData(baseResult.getData().getMyPayCards());
                                }
                            }else{
                                loadfile.setVisibility(View.VISIBLE);
                                mSmart.setVisibility(View.GONE);
                            }

                        }else{
                            loadfile.setVisibility(View.VISIBLE);
                            mSmart.setVisibility(View.GONE);
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
        loadfile = (ConstraintLayout) inflate.findViewById(R.id.load_fail);
        mBack.setOnClickListener(this);
        mAdd = (ImageView) inflate.findViewById(R.id.add);
        mAdd.setOnClickListener(this);
        mRlv = (RecyclerView) inflate.findViewById(R.id.rlv);
        mSmart = (SmartRefreshLayout) inflate.findViewById(R.id.smart);

        mRlv.setLayoutManager(new LinearLayoutManager(getContext()));

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.back:
                pop();
                break;
            case R.id.add:
                start(AddAccountFragment.getInstance());
                break;
        }
    }
}
