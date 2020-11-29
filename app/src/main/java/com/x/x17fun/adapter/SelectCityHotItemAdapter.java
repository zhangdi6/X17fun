package com.x.x17fun.adapter;

/* Created by AdScar
    on 2020/5/25.
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

public class SelectCityHotItemAdapter extends RecyclerView.Adapter {

      public ArrayList<BaseEntity> mList = new ArrayList<>();

      @NonNull
      @Override
      public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
         View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_hot_city, viewGroup, false);
         return new ViewHolder(inflate);
      }

      @Override
      public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

         ViewHolder holder = (ViewHolder) viewHolder;
         BaseEntity baseEntity = mList.get(i);
         holder.title.setText(baseEntity.getTitle());
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

    public void addData(ArrayList<BaseEntity> objects) {
        mList.clear();
        mList.addAll(objects);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{


               private final TextView title;


               public ViewHolder(View itemView) {
                   super(itemView);
                  title = itemView.findViewById(R.id.tv);

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
