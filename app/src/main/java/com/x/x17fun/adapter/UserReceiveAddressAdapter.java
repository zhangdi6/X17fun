package com.x.x17fun.adapter;

/* Created by AdScar
    on 2020/5/22.
      */

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.x.x17fun.R;
import com.x.x17fun.base.App;
import com.x.x17fun.entity.BaseEntity;
import com.x.x17fun.entity.MyAddessEntity;

import java.util.ArrayList;
import java.util.List;

public class UserReceiveAddressAdapter extends RecyclerView.Adapter {

    public ArrayList<MyAddessEntity.ReceiveListBean> mList = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_user_receive_address, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        ViewHolder holder = (ViewHolder) viewHolder;
        MyAddessEntity.ReceiveListBean baseEntity = mList.get(i);

        holder.tag.setText(baseEntity.getReceiveTag());
        String receiveAera = baseEntity.getReceiverAera();
        if (receiveAera == null){
            holder.title.setText( baseEntity.getReceiverAddress());
        }else{
            holder.title.setText(baseEntity.getReceiverAera() + baseEntity.getReceiverAddress());
        }

        String gender = baseEntity.getReceiverGender();
        if (gender.equals("1")){
            holder.sex.setText("先生");
        }else{
            holder.sex.setText("女士");
        }
        holder.phone.setText(baseEntity.getReceiverPhone());
        holder.name.setText(baseEntity.getReceiverName());

           holder.itemView.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      if (onn!=null){
                          onn.onClick(i);
                      }
                  }
              });

           if (baseEntity.getReceiveTag()==null){
               holder.tag.setVisibility(View.GONE);
           }else{
               switch (baseEntity.getReceiveTag()){
                   case "":
                       holder.tag.setVisibility(View.GONE);
                       break;
                   case "家":
                       holder.tag.setVisibility(View.VISIBLE);
                       holder.tag.setTextColor(Color.parseColor("#F7B708"));
                       holder.tag.setBackground(holder.tag.getContext().getResources().getDrawable(R.drawable.jia));
                       break;
                   case "公司":
                       holder.tag.setVisibility(View.VISIBLE);
                       holder.tag.setTextColor(Color.parseColor("#3B75D7"));
                       holder.tag.setBackground(holder.tag.getContext().getResources().getDrawable(R.drawable.gongsi));
                       break;
                   case "学校":
                       holder.tag.setVisibility(View.VISIBLE);
                       holder.tag.setTextColor(Color.parseColor("#72B34D"));
                       holder.tag.setBackground(holder.tag.getContext().getResources().getDrawable(R.drawable.xuexiao));
                       break;

               }
           }


    }

    @Override
    public int getItemCount() {
        return Math.max(mList.size(), 0);
    }

    public void addData(List<MyAddessEntity.ReceiveListBean> objects) {
        mList.clear();
        mList.addAll(objects);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        private final TextView title;
        private final TextView tag;
        private final TextView name;
        private final TextView sex;
        private final TextView phone;

        public ViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            tag = itemView.findViewById(R.id.tag);
            name = itemView.findViewById(R.id.name);
            sex = itemView.findViewById(R.id.sex);
            phone = itemView.findViewById(R.id.phone);
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
