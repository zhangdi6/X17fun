package com.x.x17fun.adapter;

/* Created by AdScar
    on 2020/5/8.
      */

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fashare.stack_layout.StackLayout;
import com.x.x17fun.R;
import com.x.x17fun.custom.RoundTopImg10dp;
import com.x.x17fun.entity.BaseEntity;
import com.x.x17fun.entity.TodayEntity;
import com.x.x17fun.utils.ButtonUtils;
import com.x.x17fun.utils.CalenderUtilss;
import com.x.x17fun.utils.DateFormatUtils;

import java.util.ArrayList;
import java.util.List;

public class HomeListAdapter extends StackLayout.Adapter {

    public ArrayList<TodayEntity.TodayPbulishedListBean> mList = new ArrayList<>();
    private LatLng lat;
    private ViewHolder holder1;

    public HomeListAdapter(LatLng latLng) {
        this.lat = latLng;
    }

    @NonNull
    @Override
    public StackLayout.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_home_list, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(StackLayout.ViewHolder holder, int position) {
         holder1 = (ViewHolder) holder;
        TodayEntity.TodayPbulishedListBean baseEntity = mList.get(position);

        holder1.food_price.setText(baseEntity.getSalePrice() + "");
        holder1.user_name.setText(baseEntity.getNickName() + "");
        if (baseEntity.getBornAddress()==null || baseEntity.getBornAddress().equals("")){
            holder1.user_address.setText("");
        }else {
            if (baseEntity.getColleage()==null || baseEntity.getColleage().equals("")){
                holder1.user_address.setText(baseEntity.getBornAddress());
            }else{
                holder1.user_address.setText(baseEntity.getBornAddress()+"  "+baseEntity.getColleage());
            }
        }
        holder1.user_location_desc.setText(baseEntity.getDeliveryAera());

        if (lat.latitude!=0){
            LatLng latLng = new LatLng(Double.parseDouble(baseEntity.getAeraLatitude()), Double.parseDouble(baseEntity.getAeraLongtitude()));
            float distance = (float) DistanceUtil.getDistance(latLng, lat);
            float cc = distance/100; //得到10.51==
            int d = Math.round(cc);//四舍五入是11
            float e=d/(float)10;//把10 也强转为float型的，再让10除以它==

            holder1.user_location.setText("距您"+e+"km");
        }else{
            holder1.user_location.setText("距离暂未知");
        }

        TodayEntity.TodayPbulishedListBean.DeliveryTimeBean deliveryTime = baseEntity.getDeliveryTime();
        TodayEntity.TodayPbulishedListBean.DeadLineBean deadLine = baseEntity.getDeadLine();
        TodayEntity.TodayPbulishedListBean.CreatetimeBean createtime = baseEntity.getCreatetime();
        int currentMonthDay = CalenderUtilss.getCurrentMonthDay();
        String day = currentMonthDay==deliveryTime.getDate()?"今天 ":"明天 ";
        if (deliveryTime.getHours()>= 6 && deliveryTime.getHours()< 11){
            holder1.food_time.setText(day+ "上午 "+deliveryTime.getHours()+":"+deliveryTime.getMinutes());
        }else if (deliveryTime.getHours() < 6){
            holder1.food_time.setText(day + "凌晨 "+deliveryTime.getHours()+":"+deliveryTime.getMinutes());
        }else if (deliveryTime.getHours()>14 && deliveryTime.getHours()< 18 ){
            holder1.food_time.setText(day + "下午 "+deliveryTime.getHours()+":"+deliveryTime.getMinutes());
        }else if (deliveryTime.getHours()>=11 && deliveryTime.getHours()<=14){
            holder1.food_time.setText(day + "中午 "+deliveryTime.getHours()+":"+deliveryTime.getMinutes());
        }else{
            holder1.food_time.setText(day + "晚上 "+deliveryTime.getHours()+":"+deliveryTime.getMinutes());
        }


        holder1.food_name.setText(baseEntity.getProductName());
        holder1.user_sex.setImageResource("1".equals(baseEntity.getGender())?R.mipmap.nan : R.mipmap.women);
        holder1.out_count.setText("已销" + baseEntity.getTotalOrderCount() + "份");

        holder1.tv_love.setText(baseEntity.getAppreciateCount()+"");
        if (baseEntity.getIsAppreciate().equals("1")){
            holder1.btn_love.setImageResource(R.mipmap.home_xinxin_hone);
            /*baseEntity.setLove(true);*/
        }else{
            holder1.btn_love.setImageResource(R.mipmap.home_xinxin);
            /*baseEntity.setLove(false);*/
        }
        if (baseEntity.getIsFocus().equals("1")){
            holder1.btn_foller.setVisibility(View.GONE);

        }else{
            holder1.btn_foller.setVisibility(View.VISIBLE);
            holder1.btn_foller.setImageResource(R.mipmap.gaunzhu);
        }
        if (baseEntity.getUserStatus()!=null && baseEntity.getUserStatus().equals("1")){
            holder1.user_statu.setVisibility(View.VISIBLE);
        }else{
            holder1.user_statu.setVisibility(View.GONE);
        }
        RequestOptions requestOptions = new RequestOptions().circleCrop();
        String displayUrl = baseEntity.getDisplayUrl();
        if (displayUrl.contains(",")){
            String[] split = displayUrl.split(",");
            Glide.with(holder1.itemView.getContext()).load(split[0]).into(holder1.food_img);
        }else{
            Glide.with(holder1.itemView.getContext()).load(baseEntity.getDisplayUrl()).into(holder1.food_img);
        }
        Glide.with(holder1.itemView.getContext()).load(baseEntity.getPortait()).apply(requestOptions).into(holder1.user_head);

        holder1.user_head.setOnClickListener(v -> {
            if (onn != null) {
                onn.onClick(position);
            }
        });
        holder1.card.setOnClickListener(v -> {
            if (onn != null) {
                onn.onItemClick(position);
            }
        });
        holder1.btn_buy.setOnClickListener(v -> {
            if (onn != null) {
                onn.onPayClick(position);
            }
        });
        holder1.btn_love.setOnClickListener(v -> {
           /* if (!baseEntity.isLove()){
                holder1.btn_love.setImageResource(R.mipmap.home_xinxin_hone);
            }else{
                holder1.btn_love.setImageResource(R.mipmap.home_xinxin);
            }*/
            if (onn != null) {
                if (!ButtonUtils.isFastDoubleClick(R.id.btn_love,2000)){
                    onn.onLoveClick(position,true);

                }else{
                    onn.onLoveClick(position,false);

                }

            }
        });
        holder1.btn_foller.setOnClickListener(v -> {
            if (onn != null) {
                onn.onFocusClick(position);
            }
        });


    }


    @Override
    public int getItemCount() {
        return Math.max(mList.size(), 0);
    }

    public void addData(List<TodayEntity.TodayPbulishedListBean> objects) {
        mList.clear();
        mList.addAll(objects);
        notifyDataSetChanged();
    }

    public void addNewData(List<TodayEntity.TodayPbulishedListBean> objects) {
        mList.addAll(objects);
        notifyDataSetChanged();

    }



    class ViewHolder extends StackLayout.ViewHolder {

        private final ConstraintLayout card;
        private final RoundTopImg10dp food_img;
        private final TextView user_name;
        private final ImageView user_head;
        private final ImageView user_sex;
        private final TextView user_address;
        private final TextView food_price;
        private final TextView food_time;
        private final TextView btn_buy;
        private final TextView user_location;
        private final TextView user_location_desc;
        private final ImageView btn_love;
        private final ImageView btn_foller;
        private final TextView tv_love;
        private final TextView out_count;
        private final TextView food_name;
        private final TextView user_statu;


        public ViewHolder(View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.card);
            food_img = itemView.findViewById(R.id.food_img);
            user_name = itemView.findViewById(R.id.user_name);
            user_head = itemView.findViewById(R.id.user_head);
            food_name = itemView.findViewById(R.id.food_name);
            out_count = itemView.findViewById(R.id.out_count);
            user_sex = itemView.findViewById(R.id.user_sex);
            user_address = itemView.findViewById(R.id.user_address);
            food_price = itemView.findViewById(R.id.food_price);
            food_time = itemView.findViewById(R.id.food_time);
            btn_buy = itemView.findViewById(R.id.btn_buy);
            btn_foller = itemView.findViewById(R.id.foller);
            user_statu = itemView.findViewById(R.id.user_statu);
            user_location = itemView.findViewById(R.id.user_location);
            user_location_desc = itemView.findViewById(R.id.user_location_desc);
            btn_love = itemView.findViewById(R.id.btn_love);
            tv_love = itemView.findViewById(R.id.tv_love);

        }
    }


    private onItemClickListener onn;


    public interface onItemClickListener {
        void onClick(int position);
        void onItemClick(int position);
        void onPayClick(int position);
        void onLoveClick(int position , boolean isSend);
        void onFocusClick(int position);
    }

    public void onClick(onItemClickListener listener) {
        onn = listener;
    }
}



