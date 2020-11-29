package com.x.x17fun.adapter;

/* Created by AdScar
    on 2020/5/8.
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
import com.x.x17fun.entity.UnReadListEntity;

import java.util.ArrayList;
import java.util.List;

public class MsgRlvAdapter extends RecyclerView.Adapter {


       private ArrayList<UnReadListEntity.UnReadRecordBean> mList = new ArrayList<>();

       @NonNull
       @Override
       public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
          View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_msg_rlv, viewGroup, false);
          return new ViewHolder(inflate);
       }

       @Override
       public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

          ViewHolder holder = (ViewHolder) viewHolder;
           UnReadListEntity.UnReadRecordBean baseEntity = mList.get(i);
           RequestOptions requestOptions = new RequestOptions().circleCrop();
           Glide.with(holder.itemView.getContext()).load(baseEntity.getPortait()).apply(requestOptions).into(holder.img_header);
          holder.time.setText(baseEntity.getLasttime());
          holder.desc.setText(baseEntity.getContent());
          holder.name.setText(baseEntity.getNickName());
          if (baseEntity.getUnreadCounts()==0){
              holder.tag.setVisibility(View.GONE);
          }else{
              holder.tag.setVisibility(View.VISIBLE);
              if (baseEntity.getUnreadCounts()>99){
                  holder.tag.setText("99+");
              }else{
                  holder.tag.setText(baseEntity.getUnreadCounts()+"");
              }

          }

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
          return Math.max(mList.size(), 0);
       }

    public void addData(List<UnReadListEntity.UnReadRecordBean> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{


                private final ImageView img_header;
                private final TextView name;
                private final TextView time;
                private final TextView desc;
                private final TextView tag;

                public ViewHolder(View itemView) {
                    super(itemView);
                    img_header = itemView.findViewById(R.id.img_header);
                    name = itemView.findViewById(R.id.name);
                    time = itemView.findViewById(R.id.time);
                    desc = itemView.findViewById(R.id.desc);
                    tag = itemView.findViewById(R.id.tag);
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
