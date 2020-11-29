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
import com.x.x17fun.entity.UserPushListEntity;
import com.x.x17fun.ui.activity.PhotoActivityActivity;
import com.x.x17fun.utils.DeeSpUtil;

import java.util.ArrayList;


/* Created by AdScar
    on 2020/7/23.
      */

public class MyTalkListAdapter extends RecyclerView.Adapter {


    private ArrayList<RemarkListEntity.CommentListBean> mList = new ArrayList<>();
    private ArrayList<String> images;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_talk_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        ViewHolder holder = (ViewHolder) viewHolder;
        RemarkListEntity.CommentListBean baseEntity = mList.get(i);

        if (baseEntity.getUserId()==null){
            holder.talk_tag.setVisibility(View.GONE);
            holder.talk_name.setText("游客");
            holder.tv_time.setText(baseEntity.getCreatetime());
            holder.tvContent.setText(baseEntity.getRemarkContent());
            RequestOptions requestOptions = new RequestOptions().circleCrop()
                    .placeholder(R.mipmap.logoyiqifan).error(R.mipmap.logoyiqifan);
            Glide.with(holder.gridView.getContext()).load(R.mipmap.logoyiqifan)
                    .apply(requestOptions).into( holder.talk_head);
            String url = baseEntity.getExtraImg();
            Log.e("question",i+"---"+baseEntity.getExtraImg());
            ArrayList<String> objects = new ArrayList<>();
            if (url!= null && !url.equals("")){
                if (url.contains(",")){
                    String[] split = url.split(",");
                    for (int j = 0; j < split.length; j++) {
                        objects.add(split[j]);
                    }
                }else{
                    objects.add(url);
                }
            }

            if (objects.size() > 0) {
                holder.gridView.setVisibility(View.VISIBLE);
                holder.gridView.setList(objects);

                holder.gridView.setOnItemClickListener(new MultiImageView.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(holder.itemView.getContext(), PhotoActivityActivity.class);
                        intent.putExtra("img",mList.get(i).getExtraImg());
                        intent.putExtra("po",position);
                        holder.itemView.getContext().startActivity(intent);
                    }
                });
            } else {
                holder.gridView.setVisibility(View.GONE);
            }
        }else {

            holder.talk_name.setText(baseEntity.getUserNickName());
            holder.tv_time.setText(baseEntity.getCreatetime());
            holder.tvContent.setText(baseEntity.getRemarkContent());
            RequestOptions requestOptions = new RequestOptions().circleCrop()
                    .placeholder(R.mipmap.logoyiqifan).error(R.mipmap.logoyiqifan);
            Glide.with(holder.gridView.getContext()).load(baseEntity.getUserPortait())
                    .apply(requestOptions).into( holder.talk_head);
            String url = baseEntity.getExtraImg();
            Log.e("question",i+"---"+baseEntity.getExtraImg());
            ArrayList<String> objects = new ArrayList<>();
            if (url!= null && !url.equals("")){
                if (url.contains(",")){
                    String[] split = url.split(",");
                    for (int j = 0; j < split.length; j++) {
                        objects.add(split[j]);
                    }
                }else{
                    objects.add(url);
                }
            }

            if (objects.size() > 0) {
                holder.gridView.setVisibility(View.VISIBLE);
                holder.gridView.setList(objects);

                holder.gridView.setOnItemClickListener(new MultiImageView.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        /*addPic(baseEntity);*/
                   /* Intent intent = new Intent(mContext, GalleryActivity.class);
                    intent.putStringArrayListExtra("imagePath", images);
                    intent.putExtra("position", position);
                    mContext.startActivity(intent);*/
                    }
                });
            } else {
                holder.gridView.setVisibility(View.GONE);
            }

            if (baseEntity.getPublisherId().equals(baseEntity.getUserId())){
                    holder.talk_tag.setText("卖家");
                    holder.talk_tag.setVisibility(View.VISIBLE);
                    holder.talk_tag.setBackgroundResource(R.drawable.slae_type);
                }else{

                    //游客
                    if (baseEntity.getStarsCount()==null || "".equals(baseEntity.getStarsCount())){
                        holder.talk_tag.setVisibility(View.GONE);

                    }else{  //买家
                        //匿名
                        if (baseEntity.getIsHidden()!=null && "1".equals(baseEntity.getIsHidden())) {
                            holder.talk_name.setText("匿名用户");
                            RequestOptions requestOptions2 = new RequestOptions().circleCrop()
                                    .placeholder(R.mipmap.logoyiqifan).error(R.mipmap.logoyiqifan);
                            Glide.with(holder.gridView.getContext()).load(R.mipmap.logoyiqifan)
                                    .apply(requestOptions2).into( holder.talk_head);
                           //不匿名
                        }else{
                            holder.talk_tag.setText("买家");
                            holder.talk_tag.setVisibility(View.VISIBLE);
                            holder.talk_tag.setBackgroundResource(R.drawable.buy_type);

                        }


                    }

                }
        }


        holder.talk_head.setOnClickListener(v -> {
            if (onn!=null){
                onn.onHeadClick(i);
            }
        });
        holder.zan.setOnClickListener(v -> {
            if (onn!=null){
                onn.onZanClick(i,false);
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
        if (url!=null && !url.equals("")){
            if (url.contains(",")){
                String[] split = url.split(",");
                for (int i = 0; i < split.length; i++) {
                    objects.add(split[i]);
                }
            }else{
                objects.add(url);
            }
        }
        images.addAll(objects);
    }

    @Override
    public int getItemCount() {
        return mList.size()>0 ? mList.size() : 0;
    }

    public void addData(ArrayList<RemarkListEntity.CommentListBean> objects) {
        mList.clear();
        mList.addAll(objects);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView zan;
        private final ImageView talk_head;
        private final TextView zan_count;
        private final TextView talk_name;
        private final TextView talk_tag;
        TextView tv_time;
        TextView tvContent;
        MultiImageView gridView;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_time = (TextView) itemView.findViewById(R.id.talk_time);
            talk_head = (ImageView) itemView.findViewById(R.id.talk_head);
            talk_name = (TextView) itemView.findViewById(R.id.talk_name);
            talk_tag = (TextView) itemView.findViewById(R.id.talk_tag);
            tvContent = (TextView) itemView.findViewById(R.id.talk_content);
            zan_count = (TextView) itemView.findViewById(R.id.zan_count);
            gridView = (MultiImageView) itemView.findViewById(R.id.gridview);
            zan = (ImageView) itemView.findViewById(R.id.zan);

        }
    }

    private onItemClickListener onn;

    public interface onItemClickListener{
        void onHeadClick(int position);
        void onZanClick(int position,boolean isSend);
    }
    public void onClick(onItemClickListener listener){
        onn = listener;
    }
}
