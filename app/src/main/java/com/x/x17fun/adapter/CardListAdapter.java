package com.x.x17fun.adapter;

/* Created by AdScar
    on 2020/6/24.
      */

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.x.x17fun.R;
import com.x.x17fun.entity.BaseEntity;
import com.x.x17fun.entity.CardListEnity;

import java.util.ArrayList;
import java.util.List;

public class CardListAdapter extends RecyclerView.Adapter {

       private ArrayList<CardListEnity.MyPayCardsBean> mList = new ArrayList<>();

       @NonNull
       @Override
       public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
          View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_card, viewGroup, false);
          return new ViewHolder(inflate);
       }

       @Override
       public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

          ViewHolder holder = (ViewHolder) viewHolder;
           CardListEnity.MyPayCardsBean baseEntity = mList.get(i);

           /*holder.name.setText(baseEntity.getAccountName());*/
              holder.phone.setText(baseEntity.getAccountNo());
          if (mList.get(i).getOuterAccountType().equals("2")){
              holder.layout.setBackgroundResource(R.drawable.text_bg_blue_10);
              holder.name.setText("支付宝");
          }else if (mList.get(i).getOuterAccountType().equals("1")){
              holder.layout.setBackgroundResource(R.drawable.text_bg_green_10);
              holder.name.setText("微信");
          }else{
              holder.layout.setBackgroundResource(R.drawable.text_bg_yellow_15);
              holder.name.setText("银行卡");
          }

       }

       @Override
       public int getItemCount() {
          return Math.max(mList.size(), 0);
       }

    public void addData(List<CardListEnity.MyPayCardsBean> myPayCards) {

           mList.clear();
           mList.addAll(myPayCards);
           notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

                private final ConstraintLayout layout;
                private final TextView name;
                private final TextView phone;

                public ViewHolder(View itemView) {
                    super(itemView);
                    layout = itemView.findViewById(R.id.layout);
                    name = itemView.findViewById(R.id.name);
                    phone = itemView.findViewById(R.id.phone);
                }
            }
}
