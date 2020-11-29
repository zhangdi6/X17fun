package com.x.x17fun.adapter;

/* Created by AdScar
    on 2020/5/15.
      */

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.x.x17fun.R;
import com.x.x17fun.entity.BaseEntity;
import com.x.x17fun.entity.MyPushedEntity;

import java.util.ArrayList;
import java.util.List;

public class SaleProManAdapter extends RecyclerView.Adapter {

       public ArrayList<MyPushedEntity.UserPublishedListBean> mList = new ArrayList<>();

       @NonNull
       @Override
       public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
          View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_sale_pro_manage, viewGroup, false);
          return new ViewHolder(inflate);
       }

       @Override
       public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

          ViewHolder holder = (ViewHolder) viewHolder;
           MyPushedEntity.UserPublishedListBean baseEntity = mList.get(i);
           RequestOptions error = new RequestOptions().placeholder(R.mipmap.yiqifan).error(R.mipmap.yiqifan);
           if (baseEntity.getDisplayUrl().contains(",")){
               String[] split = baseEntity.getDisplayUrl().split(",");
               Glide.with(holder.itemView.getContext()).load(split[0]).apply(error).into(holder.img);
           }else{
               Glide.with(holder.itemView.getContext()).load(baseEntity.getDisplayUrl()).apply(error).into(holder.img);
           }


          holder.all_sale_count.setText("累计销售"+baseEntity.getSaledCount()+"件");
          holder.total_sale_count.setText("剩余"+baseEntity.getCount()+"件");
          holder.price.setText("¥ "+baseEntity.getPrice());
          holder.meal_name.setText(baseEntity.getProductName());
          if (baseEntity.getPublishedStatus().equals("2")){
              holder.sale_statu.setText("销售中");
              holder.btn_delete.setText("提前下架");
              holder.btn_delete.setVisibility(View.GONE);
          }else if (baseEntity.getPublishedStatus().equals("1")){
              holder.sale_statu.setText("草稿箱");
              holder.btn_delete.setText("重新发布");

          }else{
              holder.sale_statu.setText("已下架");
              holder.btn_delete.setText("重新发布");

          }

          holder.itemView.setOnClickListener(v -> {
              if (onn!=null){
                  onn.onClick(i);
              }
          });
          holder.btn_delete.setOnClickListener(v -> {
              if (onn!=null){
                  onn.onBtnClick(i);
              }
          });
          holder.btn_more.setOnClickListener(v -> {
              if (onn!=null){
                  onn.onMoreClick(i);
              }
          });
       }

       @Override
       public int getItemCount() {
          return Math.max(mList.size(), 0);
       }

    public void addData(List<MyPushedEntity.UserPublishedListBean> objects) {
        mList.clear();
        mList.addAll(objects);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

                private final ImageView img;
                private final TextView sale_statu;
                private final TextView meal_name;
                private final TextView all_sale_count;
                private final TextView total_sale_count;
                private final TextView price;
                private final TextView btn_delete;
                private final TextView btn_more;

                public ViewHolder(View itemView) {
                    super(itemView);
                    img = itemView.findViewById(R.id.img);
                    sale_statu = itemView.findViewById(R.id.sale_statu);
                    meal_name = itemView.findViewById(R.id.meal_name);
                    all_sale_count = itemView.findViewById(R.id.all_sale_count);
                    total_sale_count = itemView.findViewById(R.id.total_sale_count);
                    price = itemView.findViewById(R.id.all_price);
                    btn_delete = itemView.findViewById(R.id.btn_delete);
                    btn_more = itemView.findViewById(R.id.btn_more);
                }
            }


                 private onItemClickListener onn;

                 public interface onItemClickListener{
                     void onClick(int position);
                     void onBtnClick(int position);
                     void onMoreClick(int position);
                 }
                 public void onClick(onItemClickListener listener){
                     onn = listener;
                 }
}
