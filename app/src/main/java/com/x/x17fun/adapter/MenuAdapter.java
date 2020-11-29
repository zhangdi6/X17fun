package com.x.x17fun.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.x.x17fun.R;
import com.x.x17fun.entity.BaseEntity;
import com.x.x17fun.entity.BillEntity;

import java.util.ArrayList;
import java.util.List;

// Created by Ardy on 2020/3/28.
public class MenuAdapter extends RecyclerView.Adapter {
    private ArrayList<BaseEntity> mList = new ArrayList<>();
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_menu, viewGroup,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        ViewHolder holder = (ViewHolder) viewHolder;
        BaseEntity bean = mList.get(i);
        holder.iv.setBackgroundResource(bean.getMipmap());
        holder.tv.setText(bean.getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onn!=null){
                    onn.onClick(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size()>0?mList.size():0;
    }

    public void addData(List<BaseEntity> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {


        private final ImageView iv;
        private final TextView tv;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv = itemView.findViewById(R.id.tv);
            iv = itemView.findViewById(R.id.iv);
        }
    }


         private onItemClickListener onn;

         public interface onItemClickListener{
             void onClick(int position);
         }
         public void onClick(onItemClickListener listener){
             onn = listener;
         }
}
