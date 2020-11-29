package com.x.x17fun.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.TextView;

import com.x.x17fun.R;
import com.x.x17fun.entity.BillEntity;

import java.util.ArrayList;
import java.util.List;

// Created by Ardy on 2020/3/28.
public class BillAdapter extends RecyclerView.Adapter {
    private ArrayList<BillEntity.AccountChangeRecordBean> mList = new ArrayList<>();
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_user_order, viewGroup,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        ViewHolder holder = (ViewHolder) viewHolder;
        BillEntity.AccountChangeRecordBean bean = mList.get(i);

        if ("0".equals(bean.getChangeType())){
            holder.price.setText("- "+bean.getChangeAmount());
            holder.price.setTextColor(Color.parseColor("#333333"));
        }else{
            holder.price.setText("+ "+bean.getChangeAmount());
            holder.price.setTextColor(Color.parseColor("#F24F32"));
        }

        if ("1".equals(bean.getChangeSource())){
            holder.shop_name.setText("充值");
        }else if ("2".equals(bean.getChangeSource())){
            holder.shop_name.setText("消费");
        }else if ("3".equals(bean.getChangeSource())){
            holder.shop_name.setText("收入");
        } else{
            holder.shop_name.setText("提现");
        }
        holder.time_tag.setText(bean.getCreatetime());

    }

    @Override
    public int getItemCount() {
        return mList.size()>0?mList.size():0;
    }

    public void addData(List<BillEntity.AccountChangeRecordBean> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addNewData(List<BillEntity.AccountChangeRecordBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView time_tag;
        private final TextView shop_name;
        private final TextView price;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            shop_name = itemView.findViewById(R.id.shop_name);
            time_tag = itemView.findViewById(R.id.time);
            price = itemView.findViewById(R.id.price);
        }
    }
}
