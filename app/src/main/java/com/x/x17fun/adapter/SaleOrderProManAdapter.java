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
import com.x.x17fun.entity.SalerOutEntity;

import java.util.ArrayList;
import java.util.List;

public class SaleOrderProManAdapter extends RecyclerView.Adapter {

       public ArrayList<SalerOutEntity.SellOrdersBean> mList = new ArrayList<>();

       @NonNull
       @Override
       public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
          View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_sale_order_manage, viewGroup, false);
          return new ViewHolder(inflate);
       }

       @Override
       public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

          ViewHolder holder = (ViewHolder) viewHolder;
           SalerOutEntity.SellOrdersBean baseEntity = mList.get(i);
           if (baseEntity.getDisplayUrl()!=null){
               if (baseEntity.getDisplayUrl().contains(",")){
                   String[] split = baseEntity.getDisplayUrl().split(",");
                   Glide.with(holder.itemView.getContext()).load(split[0]).into(holder.img);
               }else{
                   Glide.with(holder.itemView.getContext()).load(baseEntity.getDisplayUrl()).into(holder.img);
               }
           }
           RequestOptions requestOptions = new RequestOptions().circleCrop();
           Glide.with(holder.itemView.getContext()).load(baseEntity.getPortait()).apply(requestOptions).into(holder.saler_img);

           holder.all_sale_count.setText("共"+baseEntity.getPurchaseCount()+"份");
           holder.price.setText("¥ "+baseEntity.getTotalCost());
           holder.meal_name.setText(baseEntity.getProductName());
           holder.saler_name.setText(baseEntity.getNickName());

           switch (baseEntity.getOrderStatus()){
               case "0":
                   holder.sale_statu.setText("等待卖家接单");
                   holder.btn_delete.setText("确认接单");
                   break;
               case "1":
                   holder.sale_statu.setText("卖家拒单");
                   holder.btn_delete.setText("删除订单");
                   break;
               case "2":
                   holder.sale_statu.setText("卖家已接单");
                   holder.btn_delete.setText("通知发货");
                   break;
               case "3":
                   holder.sale_statu.setText("买家取消");
                   holder.btn_delete.setText("删除订单");
                   break;
               case "4":
                   holder.sale_statu.setText("订单已完成");
                   holder.btn_delete.setText("删除记录");
                   break;
               case "5":
                   holder.sale_statu.setText("订单已删除");
                   holder.btn_delete.setText("删除记录");
                   break;
               case "6":
                   holder.sale_statu.setText("买家申请取消");
                   holder.btn_delete.setText("同意申请");
                   break;
               case "7":
                   holder.sale_statu.setText("卖家同意取消");
                   holder.btn_delete.setText("删除记录");
                   break;
               case "8":
                   holder.sale_statu.setText("卖家拒绝取消");
                   holder.btn_delete.setText("删除订单");
                   break;
               case "9":
                   holder.sale_statu.setText("等待收货");
                   holder.btn_delete.setText("删除订单");
                   break;
               case "10":
                   holder.sale_statu.setText("超时订单关闭");
                   holder.btn_delete.setText("删除订单");
                   break;
           }
           holder.itemView.setOnClickListener(v -> {
               if (onn!=null){
                   onn.onClick(i,mList.get(i));
               }
           });
           holder.btn_delete.setOnClickListener(v -> {
               if (onn!=null){
                   switch (baseEntity.getOrderStatus()){
                       //确认收货
                       case "0":
                           onn.onBtnClick(i);
                           break;
                           //同意买家取消
                       case "6":
                           onn.onOkCancleClick(i);
                           break;
                           //发货
                       case "2":
                           onn.onSendClick(i);
                           break;
                       case "1":
                       case "3":

                       case "4":
                       case "5":
                       case "7":
                       case "8":
                       case "9":
                       case "10":
                           onn.onDeleteClick(mList.get(i));
                           break;
                   }


               }
           });
           holder.btn_more.setOnClickListener(v -> {
               if (onn!=null){
                   onn.onMoreClick(i);
               }
           });
           holder.chat_saler.setOnClickListener(v -> {
               if (onn!=null){
                   onn.onCallClick(i);
               }
           });
           holder.call_tv.setOnClickListener(v -> {
               if (onn!=null){
                   onn.onCallClick(i);
               }
           });
       }

       @Override
       public int getItemCount() {
          return Math.max(mList.size(), 0);
       }

    public void addData(List<SalerOutEntity.SellOrdersBean> objects) {
        mList.clear();
        mList.addAll(objects);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

                private final ImageView img;
                private final TextView sale_statu;
                private final TextView meal_name;
                private final TextView all_sale_count;

                private final TextView price;
                private final TextView btn_delete;
                private final TextView btn_more;
                private final TextView call_tv;
        private final ImageView chat_saler;
        private final TextView saler_name;
        private final ImageView saler_img;

        public ViewHolder(View itemView) {
                    super(itemView);
                    img = itemView.findViewById(R.id.img);
                    sale_statu = itemView.findViewById(R.id.sale_statu);
                    meal_name = itemView.findViewById(R.id.meal_name);
                    all_sale_count = itemView.findViewById(R.id.total_sale_count);
                    price = itemView.findViewById(R.id.all_price);
                    btn_delete = itemView.findViewById(R.id.btn_delete);
                    btn_more = itemView.findViewById(R.id.btn_more);
            call_tv = itemView.findViewById(R.id.call_tv);
                    saler_img = (ImageView)itemView.findViewById(R.id.saler_img);
                    saler_name = (TextView)itemView.findViewById(R.id.saler_name);
                    chat_saler = (ImageView)itemView.findViewById(R.id.chat_saler);
                }
            }

    private onItemClickListener onn;

    public interface onItemClickListener{
        void onClick(int position ,SalerOutEntity.SellOrdersBean bean);
        void onBtnClick(int position);
        void onDeleteClick(SalerOutEntity.SellOrdersBean position);
        void onSendClick(int position);
        void onMoreClick(int position);
        void onOkCancleClick(int position);
        void onCallClick(int position);
    }
    public void onClick(onItemClickListener listener){
        onn = listener;
    }
}
