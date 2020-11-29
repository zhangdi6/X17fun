package com.x.x17fun.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.x.x17fun.R;
import com.x.x17fun.entity.BillEntity;
import com.x.x17fun.entity.RefrectEntity;

import java.util.ArrayList;
import java.util.List;

// Created by Ardy on 2020/3/28.
public class BillAdapter2 extends RecyclerView.Adapter {
    private ArrayList<RefrectEntity.WithdrawListBean> mList = new ArrayList<>();
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_user_order2, viewGroup,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        ViewHolder holder = (ViewHolder) viewHolder;
        RefrectEntity.WithdrawListBean bean = mList.get(i);

        if (bean.getWithdrawTo().equals("1")){
            holder.type.setText("提现到微信");
        }else if (bean.getWithdrawTo().equals("2")){
            holder.type.setText("提现到支付宝");
        }else{
            holder.type.setText("提现到银行卡");
        }

        holder.shop_name.setText(bean.getAccountName());
        holder.time_tag.setText(bean.getWithdrawCode());
        holder.price.setText(bean.getWithdrawAmount()+"元");
        if (bean.getWithdrawStatus().equals("1")){
            holder.status.setText("待处理");
        }else if (bean.getWithdrawStatus().equals("2")){
            holder.status.setText("处理中");
        } else{
            holder.status.setText("提现到账");
        }


    }

    @Override
    public int getItemCount() {
        return mList.size()>0?mList.size():0;
    }

    public void addData(List<RefrectEntity.WithdrawListBean> withdrawList) {
        mList.clear();
        mList.addAll(withdrawList);
        notifyDataSetChanged();
    }

    public void addNewData(List<RefrectEntity.WithdrawListBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView time_tag;
        private final TextView shop_name;
        private final TextView price;
        private final TextView type;
        private final TextView status;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            shop_name = itemView.findViewById(R.id.shop_name);
            time_tag = itemView.findViewById(R.id.time);
            price = itemView.findViewById(R.id.price);
            type = itemView.findViewById(R.id.type);
            status = itemView.findViewById(R.id.status);
        }
    }
}
