package com.x.x17fun.adapter;

/* Created by AdScar
    on 2020/8/10.
      */

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.x.x17fun.R;
import com.x.x17fun.base.App;
import com.x.x17fun.entity.BaseEntity;
import com.x.x17fun.entity.ChatEntity;
import com.x.x17fun.utils.DeeSpUtil;

import java.util.ArrayList;
import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter {

      public ArrayList<ChatEntity.ChatRecordsBean> mList = new ArrayList<>();

      @NonNull
      @Override
      public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
         View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chat_list, viewGroup, false);
         return new ViewHolder(inflate);
      }

      @Override
      public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

         ViewHolder holder = (ViewHolder) viewHolder;
          ChatEntity.ChatRecordsBean baseEntity = mList.get(i);


          if (baseEntity.getFromUserId()!=null && baseEntity.getFromUserId().equals(DeeSpUtil.getInstance().getString("userId"))){

              holder.toUserLayout.setVisibility(View.GONE);
              holder.user_layout.setVisibility(View.VISIBLE);
              holder.time2.setText(baseEntity.getCreatetime());
              holder.time2.setVisibility(baseEntity.isHasTime() ? View.VISIBLE:View.GONE);
              //文本
              if ("1".equals(baseEntity.getDataType())){
                  holder.chat_img_user.setVisibility(View.GONE);
                  holder.user_talk.setVisibility(View.VISIBLE);
                  holder.user_talk.setText(baseEntity.getContent());
                  //图片
              }else{
                  holder.chat_img_user.setVisibility(View.VISIBLE);
                  holder.user_talk.setVisibility(View.GONE);
                  Glide.with(holder.itemView.getContext()).load(baseEntity.getContent()).into(holder.chat_img_user);

                  holder.chat_img_user.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          if (onn!=null){
                              onn.onClickRight(i);
                          }
                      }
                  });
              }

                  RequestOptions requestOptions = new RequestOptions().circleCrop().error(R.mipmap.logoyiqifan);
                  Glide.with(holder.itemView.getContext()).load(App.userMsg().getUserInfo()
                          .getPortait()==null ?"":App.userMsg().getUserInfo().getPortait()).apply(requestOptions).into(holder.head_img2);

          }else{

              holder.toUserLayout.setVisibility(View.VISIBLE);
              holder.user_layout.setVisibility(View.GONE);
              holder.time.setText(baseEntity.getCreatetime());
              holder.time.setVisibility(baseEntity.isHasTime() ? View.VISIBLE:View.GONE);
              String portait = baseEntity.getFromUserNickAndPortait();
              if (portait!=null && portait.contains("$$")){
                  String[] split = portait.split("[$][$]");
                  Log.e("zhuanye2",split[1]);
                  RequestOptions requestOptions = new RequestOptions().circleCrop().error(R.mipmap.logoyiqifan);
                  Glide.with(holder.itemView.getContext()).load(split[1]).apply(requestOptions).into(holder.head_img);
              }else{
                  RequestOptions requestOptions = new RequestOptions().circleCrop();
                  Glide.with(holder.itemView.getContext()).load(R.mipmap.logoyiqifan).apply(requestOptions).into(holder.head_img);
              }

              //文本
              if ("1".equals(baseEntity.getDataType())){
                  holder.chat_img_touser.setVisibility(View.GONE);
                  holder.touser_talk.setVisibility(View.VISIBLE);
                  holder.touser_talk.setText(baseEntity.getContent());
                  //图片
              }else{
                  Glide.with(holder.itemView.getContext()).load(baseEntity.getContent()).into(holder.chat_img_touser);
                  holder.chat_img_touser.setVisibility(View.VISIBLE);
                  holder.touser_talk.setVisibility(View.GONE);
                  holder.chat_img_touser.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          if (onn!=null){
                              onn.onClickLeft(i);
                          }
                      }
                  });

              }

          }

      }

      @Override
      public int getItemCount() {
         return Math.max(mList.size(), 0);
      }

    public void addData(List<ChatEntity.ChatRecordsBean> chatRecords) {
        mList.clear();
        mList.addAll(chatRecords);
        notifyDataSetChanged();

    } public void addNewDatas(List<ChatEntity.ChatRecordsBean> chatRecords) {

        chatRecords.addAll(mList);
        mList.clear();
        mList.addAll(chatRecords);
        notifyDataSetChanged();

    }
    public void addNewData(ChatEntity.ChatRecordsBean chatRecords) {
        mList.add(chatRecords);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

               private final ImageView head_img;
               private final ImageView head_img2;
               private final ImageView chat_img_touser;
               private final ImageView chat_img_user;
               private final TextView time;
               private final TextView time2;
               private final TextView touser_talk;
               private final TextView user_talk;
           private final RelativeLayout toUserLayout;
           private final RelativeLayout user_layout;

           public ViewHolder(View itemView) {
                   super(itemView);
                   toUserLayout = itemView.findViewById(R.id.toUserLayout);
                   user_layout = itemView.findViewById(R.id.user_layout);
                   time = itemView.findViewById(R.id.time);
                   time2 = itemView.findViewById(R.id.time2);
                   head_img = itemView.findViewById(R.id.head_img);
                   head_img2 = itemView.findViewById(R.id.head_img2);
                   chat_img_touser = itemView.findViewById(R.id.chat_img_touser);
                   chat_img_user = itemView.findViewById(R.id.chat_img_user);
                   touser_talk = itemView.findViewById(R.id.touser_talk);
                   user_talk = itemView.findViewById(R.id.user_talk);
               }
           }


                private onItemClickListener onn;

                public interface onItemClickListener{
                    void onClickLeft(int position);
                    void onClickRight(int position);
                }
                public void onClick(onItemClickListener listener){
                    onn = listener;
                }
}
