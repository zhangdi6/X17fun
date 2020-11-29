package com.x.x17fun.ui.fragment;

import android.os.Bundle;
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
import com.x.x17fun.adapter.BillAdapter;
import com.x.x17fun.adapter.BillAdapter2;
import com.x.x17fun.base.BaseFragment;
import com.x.x17fun.data.DataService;
import com.x.x17fun.entity.BaseResult;
import com.x.x17fun.entity.BillEntity;
import com.x.x17fun.entity.RefrectEntity;
import com.x.x17fun.utils.AdiUtils;
import com.x.x17fun.utils.ParamsUtils;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class RefrectHistotyFragment extends BaseFragment {


    private ImageView mBack;
    private RecyclerView mBillList;
    private SmartRefreshLayout mSmart;
    private BillAdapter2 adapter;



    public static RefrectHistotyFragment newInstance() {
        return new RefrectHistotyFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_refrect_histoty, container, false);
        initView(view);
        mBack.setOnClickListener(v -> pop());
        return view;
    }

    private void initView(View view) {

        mBack = (ImageView) view.findViewById(R.id.back);
        mBillList = (RecyclerView) view.findViewById(R.id.bill_list);
        mSmart = (SmartRefreshLayout) view.findViewById(R.id.smart);


        mSmart.setOnRefreshListener(refreshLayout -> {
            /*page = 1;*/
            initData();
        });

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();

        initData();
    }

    //
    private void initData() {

        HashMap paramsMap = ParamsUtils.getParamsMap();
        DataService.getHomeService().getMoneyList(paramsMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult<RefrectEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<RefrectEntity> o) {

                        Log.e("bill", o.toString());
                        if (o.getResult_code() == 0 && o.getData() != null
                                && o.getData().getWithdrawList() != null
                                && o.getData().getWithdrawList().size() > 0) {
                            adapter = new BillAdapter2();
                            mBillList.setLayoutManager(new LinearLayoutManager(getContext()));
                            mBillList.setAdapter(adapter);
                            mSmart.setVisibility(View.VISIBLE);
                            adapter.addData(o.getData().getWithdrawList());

                        } else if (o.getResult_code() == -3) {
                            AdiUtils.loginOut();
                        } else {
                            mSmart.setVisibility(View.GONE);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        mSmart.setVisibility(View.GONE);

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
