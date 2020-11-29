package com.x.x17fun.adapter;

/* Created by AdScar
    on 2020/5/13.
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
import com.x.x17fun.entity.BaseEntity;

import java.util.ArrayList;

public class PushMealChanelAdapter extends RecyclerView.Adapter {

       public ArrayList<BaseEntity> mList = new ArrayList<>();

       @NonNull
       @Override
       public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
          View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_push_meal, viewGroup, false);
          return new ViewHolder(inflate);
       }

       @Override
       public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

          ViewHolder holder = (ViewHolder) viewHolder;
          BaseEntity baseEntity = mList.get(i);
          Glide.with(holder.itemView.getContext()).load(baseEntity.getMipmap()).into(holder.img);
          holder.tag.setText(baseEntity.getSubtitle());
          holder.title.setText(baseEntity.getTitle());

          holder.tag.setOnClickListener(new View.OnClickListener() {
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

    public void addData(ArrayList<BaseEntity> objects) {
           mList.clear();
           mList.addAll(objects);
           notifyDataSetChanged();
    }

    public void changePosition(int i, String s) {
        BaseEntity baseEntity = mList.get(i);
        mList.set(i,new BaseEntity(baseEntity.getMipmap(),baseEntity.getTitle(),s));
        notifyItemChanged(i);
    }

    class ViewHolder extends RecyclerView.ViewHolder{

                private final ImageView img;
                private final TextView title;
                private final TextView tag;

                public ViewHolder(View itemView) {
                    super(itemView);
                   img = itemView.findViewById(R.id.icon);
                   title = itemView.findViewById(R.id.title);
                   tag = itemView.findViewById(R.id.desc);
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
