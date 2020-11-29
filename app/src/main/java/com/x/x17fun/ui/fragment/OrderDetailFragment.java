package com.x.x17fun.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.x.x17fun.R;
import com.x.x17fun.base.BaseFragment;
import com.x.x17fun.custom.RoundImageView4dp;
import com.x.x17fun.entity.BuyInOrderEntity;

import java.io.Serializable;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderDetailFragment extends BaseFragment {

    private View view;
    private ImageView mBack;
    /**
     * 马化腾
     */
    private TextView mUserName;
    /**
     * 北京  北京市  朝阳区  望京SOHO塔2C座506室
     */
    private TextView mUserAddress;
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
    /**
     * 不要放香菜萨芬是哈大V还能聊卡大V领肯定是女郎看见你SVN 是大V给你了上课就朵女郎接口啥的哪里看
     */
    private TextView mOther;
    /**
     * 132148648148174
     */
    private TextView mOrderNum;
    /**
     * 2020-05-12 12:00
     */
    private TextView mOrderTime;
    private BuyInOrderEntity.PurchaseOrdersBean bean;


    public static OrderDetailFragment getInstance(BuyInOrderEntity.PurchaseOrdersBean bean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("bean",bean);
        OrderDetailFragment fragment = new OrderDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_order_detail, container, false);
        Bundle bundle = getArguments();
        bean = (BuyInOrderEntity.PurchaseOrdersBean)bundle.getSerializable("bean");
        initView(inflate);
        return inflate;
    }

    private void initView(View inflate) {

        mBack = (ImageView) inflate.findViewById(R.id.back);
        mBack.setOnClickListener(v -> pop());
        mUserName = (TextView) inflate.findViewById(R.id.user_name);
        mUserAddress = (TextView) inflate.findViewById(R.id.user_address);
        mSalerImg = (ImageView) inflate.findViewById(R.id.saler_img);
        loadCircleImg(bean.getPortait(),mSalerImg);
        mPrice = (TextView) inflate.findViewById(R.id.price);
        mTotalSaleCount = (TextView) inflate.findViewById(R.id.total_sale_count);
        mMealName = (TextView) inflate.findViewById(R.id.meal_name);
        mSalerName = (TextView) inflate.findViewById(R.id.saler_name);
        mImg = (RoundImageView4dp) inflate.findViewById(R.id.img);
        if (bean.getDisplayUrl()!=null){
            if (bean.getDisplayUrl().contains(",")){
                String[] split = bean.getDisplayUrl().split(",");
                Glide.with(getActivity()).load(split[0]).into(mImg);
            }else{
                Glide.with(getContext()).load(bean.getDisplayUrl()).into(mImg);
            }
        }

        mOther = (TextView) inflate.findViewById(R.id.other);
        mOrderNum = (TextView) inflate.findViewById(R.id.order_num);
        mOrderTime = (TextView) inflate.findViewById(R.id.order_time);

        mUserName.setText(bean.getReceiverName()+"  "+bean.getReceiverPhone());
        mUserAddress.setText(bean.getReceiverAddress());
        mSalerName.setText(bean.getNickName());
        mMealName.setText(bean.getProductName());
        mTotalSaleCount.setText("共"+bean.getPurchaseCount()+"份");
        mPrice.setText("¥ "+bean.getTotalSaleCost());
        mOther.setText(bean.getOrderNote()==null|| bean.getOrderNote().equals("")?"无":bean.getOrderNote());
        mOrderNum.setText(bean.getOrderId());
        mOrderTime.setText(bean.getCreatetime());
    }
}
