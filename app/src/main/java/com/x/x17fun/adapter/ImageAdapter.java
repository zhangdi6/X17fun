package com.x.x17fun.adapter;

/* Created by AdScar
    on 2020/9/14.
      */

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.x.x17fun.R;
import com.x.x17fun.custom.RoundImageView4dp;
import com.x.x17fun.entity.BaseEntity;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter {

     private ArrayList<String> mList = new ArrayList<>();

     @NonNull
     @Override
     public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_img, viewGroup, false);
        return new ViewHolder(inflate);
     }

     @Override
     public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        ViewHolder holder = (ViewHolder) viewHolder;
        String baseEntity = mList.get(i);
        Glide.with(holder.itemView.getContext()).load(baseEntity).into(holder.img);

     }

     @Override
     public int getItemCount() {
        return Math.max(mList.size(), 0);
     }

    public void addData(ArrayList<String> objects) {
        mList.clear();
        mList.addAll(objects);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

              private final RoundImageView4dp img;


              public ViewHolder(View itemView) {
                  super(itemView);
                 img = itemView.findViewById(R.id.img);

              }
          }
}
