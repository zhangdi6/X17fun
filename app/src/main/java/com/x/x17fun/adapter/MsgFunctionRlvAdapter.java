package com.x.x17fun.adapter;

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


public class MsgFunctionRlvAdapter extends RecyclerView.Adapter {

   private ArrayList<BaseEntity> mList = new ArrayList<>();
   private onItemClickListener onn;

   @NonNull
   @Override
   public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
      View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_msg_function, viewGroup, false);
      return new ViewHolder(inflate);
   }

   @Override
   public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

      ViewHolder holder = (ViewHolder) viewHolder;
      BaseEntity baseEntity = mList.get(i);
      Glide.with(holder.itemView.getContext()).load(baseEntity.getMipmap()).into(holder.img);
      if (baseEntity.getCount()==0){
         holder.tag.setVisibility(View.GONE);
      }else if (baseEntity.getCount()>99){
          holder.tag.setText("99+");
      }else{
          holder.tag.setText(baseEntity.getCount()+"");
      }

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

   public void addData(ArrayList<BaseEntity> list) {
      mList.clear();
      mList.addAll(list);
      notifyDataSetChanged();
   }

   class ViewHolder extends RecyclerView.ViewHolder{

            private final ImageView img;
            private final TextView title;
            private final TextView tag;

            public ViewHolder(View itemView) {
                super(itemView);
               img = itemView.findViewById(R.id.img);
               title = itemView.findViewById(R.id.title);
               tag = itemView.findViewById(R.id.tag);
            }
        }




   public interface onItemClickListener{
         void onClick(int position);
    }
    public void onClick(onItemClickListener listener){
         onn = listener;
    }
}
