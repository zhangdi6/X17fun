package com.x.x17fun.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.x.x17fun.R;
import com.x.x17fun.entity.BaseEntity;

import java.util.ArrayList;


// Created by Ardy on 2020/1/6.
public class AccoutTypeAdapter extends RecyclerView.Adapter {

    public ArrayList<BaseEntity>mList = new ArrayList<>();
    private Context mContext ;
    private onItemCheckedListener ono;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_account_type, viewGroup,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        BaseEntity entity = mList.get(i);
        ViewHolder view= (ViewHolder) viewHolder;
        if (entity.isCheck()){
            view.check.setImageResource(entity.getResource());
        }else{
            view.check.setImageResource(entity.getMipmap());
        }
        view.tv_name.setText(entity.getTitle());
        view.tv_no.setText(entity.getSubtitle());
        view.itemView.setOnClickListener(v -> {
            if (ono != null ){
                ono.onItemCheck(i);
            }
        });
    }

    public int getCheckedPosition(){
        for (int i = 0; i < mList.size(); i++) {
            BaseEntity rechargeMoneyEntity = mList.get(i);
            if (rechargeMoneyEntity.isCheck()){
                return i;
            }
        }
        return -1;
    }
    public void changeState(int position){
        for (int i = 0; i < mList.size() ; i++) {
            BaseEntity entity = mList.get(i);
            if (i==position){
                entity.setCheck(true);
            }else{
                entity.setCheck(false);
            }
        }
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void addData(ArrayList<BaseEntity> objects) {
        mList.clear();
        mList.addAll(objects);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView tv_no;
        private final TextView tv_name;
        private final ImageView check;
        private final ConstraintLayout root;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            root = itemView.findViewById(R.id.root);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_no = itemView.findViewById(R.id.tv_no);
            check = itemView.findViewById(R.id.check);
        }
    }
    public interface onItemCheckedListener{
        void onItemCheck(int position);
    }
    public void onItemCheck(onItemCheckedListener listener){
        ono = listener ;
    }
}
