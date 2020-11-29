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

import com.bumptech.glide.Glide;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.x.x17fun.R;
import com.x.x17fun.adapter.BuyInProManAdapter;
import com.x.x17fun.adapter.SaleProManAdapter;
import com.x.x17fun.base.App;
import com.x.x17fun.base.BaseDialog;
import com.x.x17fun.base.BaseFragment;
import com.x.x17fun.data.DataService;
import com.x.x17fun.entity.BaseEntity;
import com.x.x17fun.entity.BaseResult;
import com.x.x17fun.entity.BuyInOrderEntity;
import com.x.x17fun.entity.SalerOutEntity;
import com.x.x17fun.ui.activity.ChatActivity;
import com.x.x17fun.utils.AdiUtils;
import com.x.x17fun.utils.ParamsUtils;
import com.x.x17fun.utils.SHA1Utils;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class BuyInProductFragment extends BaseFragment {

    private View view;
    private ImageView mBack;
    private RecyclerView mRlv;
    private SmartRefreshLayout mSmart;
    private BuyInProManAdapter saleProManAdapter;
    private BaseDialog baseDialog;
    private ConstraintLayout load_fail;

    public static BuyInProductFragment getInstance() {
        return new BuyInProductFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_buy_in_product, container, false);
        initView(inflate);
        initData();
        return inflate;
    }

    private void initData() {
        HashMap paramsMap = ParamsUtils.getParamsMap();


        DataService.getLoginService().getBuyInList(paramsMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult<BuyInOrderEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<BuyInOrderEntity> o) {

                        Log.e("result",o.toString());
                        if (o.getResult_code()==0){
                            if (o.getData()!=null && o.getData().getPurchaseOrders()!=null
                                    && o.getData().getPurchaseOrders().size()>0){

                                mSmart.setVisibility(View.VISIBLE);
                            load_fail.setVisibility(View.GONE);
                               if (saleProManAdapter!=null){
                                   saleProManAdapter.addData(o.getData().getPurchaseOrders());
                               }else{
                                   mRlv.setLayoutManager(new LinearLayoutManager(getActivity()));
                                   saleProManAdapter = new BuyInProManAdapter();
                                   saleProManAdapter.addData(o.getData().getPurchaseOrders());
                                   mRlv.setAdapter(saleProManAdapter);
                                   saleProManAdapter.onClick(new BuyInProManAdapter.onItemClickListener() {
                                       @Override
                                       public void onClick(int position ,BuyInOrderEntity.PurchaseOrdersBean bean) {
                                           start(OrderDetailFragment.getInstance(bean));
                                       }

                                       @Override
                                       public void onBtnClick(int position) {
                                           onReceiver(position);
                                       }

                                       @Override
                                       public void onMoreClick(int position) {

                                       }

                                       @Override
                                       public void onCancleClick(int position) {
                                           onCancle(position);
                                       }

                                       @Override
                                       public void onRemarkClick(int position) {
                                           String ss  = "";
                                           BuyInOrderEntity.PurchaseOrdersBean purchaseOrdersBean = saleProManAdapter.mList.get(position);
                                           String displayUrl = purchaseOrdersBean.getDisplayUrl();
                                           if (displayUrl!=null){
                                               if (displayUrl.contains(",")){
                                                   String[] split = displayUrl.split(",");
                                                   ss = split[0];
                                               }else{
                                                   ss = displayUrl;
                                               }
                                           }
                                           start(RemarkFragment.getInstance(purchaseOrdersBean.getProductName(),
                                                   ss,purchaseOrdersBean.getPublishedId(),purchaseOrdersBean.getProviderId(),purchaseOrdersBean.getFoodId()));
                                       }

                                       @Override
                                       public void onCallClick(int position) {
                                           Log.e("provider",saleProManAdapter.mList.get(position).toString());
                                           Intent intent = new Intent(getActivity(), ChatActivity.class);
                                           intent.putExtra("name",saleProManAdapter.mList.get(position).getNickName());
                                           intent.putExtra("providerId",saleProManAdapter.mList.get(position).getProviderId());
                                           startActivity(intent);
                                           /*View inflate = LayoutInflater.from(getContext()).inflate(R.layout.dialog_kefu, null);
                                           TextView phone = inflate.findViewById(R.id.phone);
                                           TextView title = inflate.findViewById(R.id.title);
                                           phone.setText(saleProManAdapter.mList.get(position).getPhone());
                                           title.setText("卖家电话");
                                           baseDialog = new BaseDialog(getContext(), inflate, Gravity.CENTER);
                                           baseDialog.show();*/
                                       }

                                       @Override
                                       public void onDeleteClick(BuyInOrderEntity.PurchaseOrdersBean position) {
                                           onDelete(position);

                                       }


                                   });
                               }

                            }else{
                                mSmart.setVisibility(View.GONE);
                                load_fail.setVisibility(View.VISIBLE);
                            }
                        }
                        else {
                            mSmart.setVisibility(View.GONE);
                            load_fail.setVisibility(View.VISIBLE);
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
                        AdiUtils.showToast(e.getMessage());
                                if (mSmart.isRefreshing()){
                                    mSmart.finishRefresh();
                                }
                        mSmart.setVisibility(View.GONE);
                        load_fail.setVisibility(View.VISIBLE);
                            }

                    @Override
                    public void onComplete() {

                                if (mSmart.isRefreshing()){
                                    mSmart.finishRefresh();
                                }
                            }
                });



    }

    private void onDelete(BuyInOrderEntity.PurchaseOrdersBean position) {
        HashMap paramsMap = ParamsUtils.getParamsMap();

        paramsMap.put("orderId",position.getOrderId());
        DataService.getHomeService().deleteBuy(paramsMap)
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
        DataService.getLoginService().confirmReceive(paramsMap)
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
                            showToast("已确认收货");
                            mSmart.autoRefresh();
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

    private void onCancle(int position) {
        HashMap paramsMap = ParamsUtils.getParamsMap();

        paramsMap.put("orderId",saleProManAdapter.mList.get(position).getOrderId());
        DataService.getLoginService().cancleOrder(paramsMap)
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
                            showToast("已通知商家请求取消");
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
    private void initView(View inflate) {

        mBack = (ImageView) inflate.findViewById(R.id.back);
        load_fail = (ConstraintLayout) inflate.findViewById(R.id.load_fail);

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        mRlv = (RecyclerView) inflate.findViewById(R.id.rlv);
        mSmart = (SmartRefreshLayout) inflate.findViewById(R.id.smart);
        mSmart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                initData();
            }
        });
    }
}
