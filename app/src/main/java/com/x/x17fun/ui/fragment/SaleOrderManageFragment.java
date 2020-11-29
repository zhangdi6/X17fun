package com.x.x17fun.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.x.x17fun.R;
import com.x.x17fun.adapter.BuyInProManAdapter;
import com.x.x17fun.adapter.SaleOrderProManAdapter;
import com.x.x17fun.base.BaseDialog;
import com.x.x17fun.base.BaseFragment;
import com.x.x17fun.data.DataService;
import com.x.x17fun.entity.BaseEntity;
import com.x.x17fun.entity.BaseResult;
import com.x.x17fun.entity.BuyInOrderEntity;
import com.x.x17fun.entity.SalerOutEntity;
import com.x.x17fun.ui.activity.ChatActivity;
import com.x.x17fun.ui.activity.MainActivity;
import com.x.x17fun.utils.AdiUtils;
import com.x.x17fun.utils.DateFormatUtils;
import com.x.x17fun.utils.ParamsUtils;

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
public class SaleOrderManageFragment extends BaseFragment {

    private View view;
    private ImageView mBack;
    private RecyclerView mRlv;
    private SmartRefreshLayout mSmart;
    private SaleOrderProManAdapter saleProManAdapter;
    private BaseDialog baseDialog;
    private ConstraintLayout load_fiail;

    public static SaleOrderManageFragment getInstance() {
        return new SaleOrderManageFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_sale_order_manage, container, false);
        initView(inflate);
        initData();
        return inflate;
    }

    private void initView(View inflate) {

        mBack = (ImageView) inflate.findViewById(R.id.back);
        mBack.setOnClickListener(v -> getActivity().finish());
        mRlv = (RecyclerView) inflate.findViewById(R.id.rlv);
        mSmart = (SmartRefreshLayout) inflate.findViewById(R.id.smart);
        load_fiail = (ConstraintLayout) inflate.findViewById(R.id.load_fail);
        mSmart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                initData();
            }
        });
    }

    private void initData() {
        HashMap paramsMap = ParamsUtils.getParamsMap();

        DataService.getLoginService().getSalerOutList(paramsMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult<SalerOutEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<SalerOutEntity> o) {

                        Log.e("result",o.toString());
                        if (o.getResult_code()==0){

                            if (o.getData()!=null && o.getData().getSellOrders()
                                    !=null && o.getData().getSellOrders().size()>0){
                                List<SalerOutEntity.SellOrdersBean> sellOrders = o.getData().getSellOrders();
                                mSmart.setVisibility(View.VISIBLE);
                                load_fiail.setVisibility(View.GONE);
                                if (saleProManAdapter!=null){
                                    saleProManAdapter.addData(sellOrders);
                                }else{
                                    mRlv.setLayoutManager(new LinearLayoutManager(getActivity()));
                                    saleProManAdapter = new SaleOrderProManAdapter();
                                    saleProManAdapter.addData(sellOrders);
                                    mRlv.setAdapter(saleProManAdapter);
                                    saleProManAdapter.onClick(new SaleOrderProManAdapter.onItemClickListener() {
                                        @Override
                                        public void onClick(int position ,SalerOutEntity.SellOrdersBean orderDetail) {
                                            BuyInOrderEntity.PurchaseOrdersBean bean = new BuyInOrderEntity.PurchaseOrdersBean();
                                            bean.setDisplayUrl(orderDetail.getDisplayUrl());
                                            bean.setPortait(orderDetail.getPortait());
                                            bean.setCreatetime(orderDetail.getCreatetime());
                                            bean.setOrderNote(orderDetail.getOrderNote());
                                            bean.setNickName(orderDetail.getNickName());
                                            bean.setProductName(orderDetail.getProductName());
                                            bean.setTotalCost(orderDetail.getTotalSaleCost());
                                            bean.setTotalSaleCost(orderDetail.getTotalCost());
                                            bean.setReceiverName(orderDetail.getReceiverName());
                                            bean.setReceiverAddress(orderDetail.getReceiverAddress());
                                            bean.setReceiverPhone(orderDetail.getReceiverPhone());
                                            bean.setReceiverGender(orderDetail.getReceiverGender());
                                            bean.setOrderId(orderDetail.getOrderId());
                                            bean.setOrderStatus(orderDetail.getOrderStatus());
                                            bean.setPurchaseCount(orderDetail.getPurchaseCount());
                                            start(OrderDetailFragment.getInstance(bean));
                                        }

                                        @Override
                                        public void onBtnClick(int position) {
                                            onReceiver(position);

                                        }

                                        @Override
                                        public void onDeleteClick(SalerOutEntity.SellOrdersBean position) {
                                            onDelete(position);

                                        }


                                        @Override
                                        public void onSendClick(int position) {
                                            onSend(position);
                                        }

                                        @Override
                                        public void onMoreClick(int position) {

                                        }

                                        @Override
                                        public void onOkCancleClick(int position) {

                                        }

                                        @Override
                                        public void onCallClick(int position) {
                                            Log.e("provider",saleProManAdapter.mList.get(position).toString());
                                            Intent intent = new Intent(getActivity(), ChatActivity.class);
                                            intent.putExtra("name",saleProManAdapter.mList.get(position).getNickName());
                                            intent.putExtra("providerId",saleProManAdapter.mList.get(position).getUserId());
                                            startActivity(intent);
/*
                                            View inflate = LayoutInflater.from(getContext()).inflate(R.layout.dialog_kefu, null);
                                            TextView phone = inflate.findViewById(R.id.phone);
                                            TextView title = inflate.findViewById(R.id.title);
                                            phone.setText(saleProManAdapter.mList.get(position).getReceiverPhone());
                                            title.setText("买家电话");
                                            baseDialog = new BaseDialog(getContext(), inflate, Gravity.CENTER);
                                            baseDialog.show();*/

                                        }
                                    });
                                }

                            }else{
                                mSmart.setVisibility(View.GONE);
                                load_fiail.setVisibility(View.VISIBLE);
                            }
                        }
                        else {
                            mSmart.setVisibility(View.GONE);
                            load_fiail.setVisibility(View.VISIBLE);
                            AdiUtils.showToast(o.getResult_msg());
                        }

                        mSmart.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (mSmart.isRefreshing()){
                                    mSmart.finishRefresh();
                                }
                            }
                        },1000);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mSmart.setVisibility(View.GONE);
                        load_fiail.setVisibility(View.VISIBLE);
                        AdiUtils.showToast(e.getMessage());
                                if (mSmart.isRefreshing()){
                                    mSmart.finishRefresh();
                                }
                    }

                    @Override
                    public void onComplete() {
                        if (mSmart.isRefreshing()){
                            mSmart.finishRefresh();
                        }

                    }
                });


    }

    private void onDelete(SalerOutEntity.SellOrdersBean position) {

        HashMap paramsMap = ParamsUtils.getParamsMap();

        paramsMap.put("orderId",position.getOrderId());
        DataService.getHomeService().deleteSaled(paramsMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult o) {

                        Log.e("result",o.toString());
                        if (o.getResult_code()==0){
                            showToast("订单已删除");
                            saleProManAdapter.mList.remove(position);
                            saleProManAdapter.notifyDataSetChanged();
                        }else{
                            showToast(o.getResult_msg());
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

    private void onReceiver(int position) {
        HashMap paramsMap = ParamsUtils.getParamsMap();

        paramsMap.put("orderId",saleProManAdapter.mList.get(position).getOrderId());
        DataService.getLoginService().receiverOrder(paramsMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult o) {

                        Log.e("result",o.toString());
                        if (o.getResult_code()==0){
                            showToast("已确认接单");
                            mSmart.autoRefresh();
                        }else{
                            showToast(o.getResult_msg());
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
    private void onSend(int position) {
        HashMap paramsMap = ParamsUtils.getParamsMap();

        paramsMap.put("orderId",saleProManAdapter.mList.get(position).getOrderId());
        DataService.getLoginService().pendingBuyerConfirm(paramsMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult o) {

                        Log.e("result",o.toString());
                        if (o.getResult_code()==0){
                            showToast("已通知发货");
                            mSmart.autoRefresh();
                        }else{
                            showToast(o.getResult_msg());
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
