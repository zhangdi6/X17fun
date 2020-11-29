package com.x.x17fun.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.x.x17fun.R;
import com.x.x17fun.custom.MultiImageView;
import com.x.x17fun.entity.BaseEntity;
import com.x.x17fun.entity.RemarkListEntity;
import com.x.x17fun.entity.RemarkZoneEntity;
import com.x.x17fun.ui.activity.PhotoActivityActivity;

import java.util.ArrayList;


/* Created by AdScar
    on 2020/7/23.
      */

public class MyZoneTalkListAdapter extends RecyclerView.Adapter {


    private ArrayList<RemarkZoneEntity.CommentListBean> mList = new ArrayList<>();
    private ArrayList<String> images;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_zone_talk_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        ViewHolder holder = (ViewHolder) viewHolder;
        RemarkZoneEntity.CommentListBean baseEntity = mList.get(i);

        holder.talk_name.setText(baseEntity.getUserNickName());
        holder.tv_time.setText(baseEntity.getCreatetime());
        holder.tvContent.setText(baseEntity.getRemarkContent());
        RequestOptions requestOptions = new RequestOptions().circleCrop()
                .placeholder(R.mipmap.logoyiqifan).error(R.mipmap.logoyiqifan);
        Glide.with(holder.talk_head.getContext()).load(baseEntity.getUserPortait())
                .apply(requestOptions).into(holder.talk_head);

        holder.talk_head.setOnClickListener(v -> {
            if (onn != null) {
                onn.onHeadClick(i);
            }
        });
        holder.zan.setOnClickListener(v -> {
            if (onn != null) {
                onn.onZanClick(i, false);
            }
        });

    }

    private void addPic(final BaseEntity item) {
        if (images == null) {
            images = new ArrayList<>();
        } else {
            images.clear();
        }

        String url = item.getStr2();
        ArrayList<String> objects = new ArrayList<>();
        if (url != null && !url.equals("")) {
            if (url.contains(",")) {
                String[] split = url.split(",");
                for (int i = 0; i < split.length; i++) {
                    objects.add(split[i]);
                }
            } else {
                objects.add(url);
            }
        }
        images.addAll(objects);
    }

    @Override
    public int getItemCount() {
        return Math.max(mList.size(), 0);
    }

    public void addData(ArrayList<RemarkZoneEntity.CommentListBean> objects) {
        mList.clear();
        mList.addAll(objects);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView zan;
        private final ImageView talk_head;
        private final TextView zan_count;
        private final TextView talk_name;
        TextView tv_time;
        TextView tvContent;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_time = (TextView) itemView.findViewById(R.id.talk_time);
            talk_head = (ImageView) itemView.findViewById(R.id.talk_head);
            talk_name = (TextView) itemView.findViewById(R.id.talk_name);
            tvContent = (TextView) itemView.findViewById(R.id.talk_content);
            zan_count = (TextView) itemView.findViewById(R.id.zan_count);
            zan = (ImageView) itemView.findViewById(R.id.zan);

        }
    }

    private onItemClickListener onn;

    public interface onItemClickListener {
        void onHeadClick(int position);

        void onZanClick(int position, boolean isSend);
    }

    public void onClick(onItemClickListener listener) {
        onn = listener;
    }
}
