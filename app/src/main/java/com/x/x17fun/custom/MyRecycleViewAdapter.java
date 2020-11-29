package com.x.x17fun.custom;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.x.x17fun.R;
import com.x.x17fun.entity.BaseEntity;
import com.x.x17fun.entity.UserPushListEntity;
import com.x.x17fun.ui.activity.PhotoActivityActivity;
import com.x.x17fun.utils.ButtonUtils;
import com.x.x17fun.utils.DateFormatUtils;

import java.util.ArrayList;
import java.util.List;

public class MyRecycleViewAdapter extends RecyclerView.Adapter {
 
    private Context mContext;
    private List<UserPushListEntity.UserPublishedListBean> list = new ArrayList<>();
    private LayoutInflater mInflater;
    private ArrayList<String> images;
 
    public MyRecycleViewAdapter(Context mContext) {
 
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }
 
    public void setList(List<UserPushListEntity.UserPublishedListBean> list) {
        this.list = list;
    }
 
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_layout, parent, false);
        return new ViewHolder(view);
    }
 
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int pos) {
 
        final ViewHolder viewHolder = (ViewHolder) holder;
        final UserPushListEntity.UserPublishedListBean itemModle = list.get(pos);
 
        holder.itemView.setTag(pos);
        viewHolder.tvContent.setText(itemModle.getProductTag());
       /* Glide.with(mContext).load(itemModle.getHeadImg())
                .asBitmap()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new BitmapImageViewTarget(viewHolder.ivHead) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable rounde = RoundedBitmapDrawableFactory.create(mContext.getResources(), resource);
                        rounde.setCircular(true);
                        //要实现圆角，只需要加上这句
                        rounde.setCornerRadius(100L);
                        viewHolder.ivHead.setImageDrawable(rounde);
                    }
                });
 */


        long time = itemModle.getCreatetime().getTime();
        String s = DateFormatUtils.longToDate(time,DateFormatUtils.FORMAT_6);
        viewHolder.tv_time.setText(s);
        viewHolder.zan_count.setText(itemModle.getAppreciateCount()+"");
        viewHolder.love_count.setText(itemModle.getStarCount()+"");

        if (itemModle.getIsAppreciate().equals("1")){
            viewHolder.zan.setImageResource(R.mipmap.xinxinhong);
        }else{
            viewHolder.zan.setImageResource(R.mipmap.xinxin);
        }



        if (itemModle.isLove()==false){
            viewHolder.love.setImageResource(R.mipmap.xingxing);
        }else{
            viewHolder.love.setImageResource(R.mipmap.xingxinghuanh);
        }

        viewHolder.zan.setOnClickListener(v -> {
            if (onn!=null){
                if (!ButtonUtils.isFastDoubleClick(R.id.zan,2000)){
                    onn.onZanClick(pos,true);
                }else{
                    onn.onZanClick(pos,false);
                }

            }
        });
        viewHolder.love.setOnClickListener(v -> {
            if (onn!=null){
                if (!ButtonUtils.isFastDoubleClick(R.id.love,2000)){
                    onn.onLoveClick(pos,true);
                }else{
                    onn.onLoveClick(pos,false);
                }

            }
        });

        String url = itemModle.getDisplayUrl();
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
        if (objects.size() > 0) {
            viewHolder.gridView.setVisibility(View.VISIBLE);
            viewHolder.gridView.setList(objects);

            viewHolder.gridView.setOnItemClickListener(new MultiImageView2.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(mContext, PhotoActivityActivity.class);
                    intent.putExtra("img",list.get(pos).getDisplayUrl());
                    intent.putExtra("po",position);
                    mContext.startActivity(intent);
                }
            });
        } else {
            viewHolder.gridView.setVisibility(View.GONE);
        }
    }
 
    @Override
    public int getItemCount() {
        return Math.max(list.size(), 0);
    }

   /* public void addData(ArrayList<BaseEntity> objects) {
        if (list!=null){
            list.clear();
        }
        list.addAll(objects);
        notifyDataSetChanged();
    }*/

    class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView zan;
        private final ImageView love;
        private final TextView zan_count;
        private final TextView love_count;
        TextView tv_time;

        TextView tvContent;
        MultiImageView2 gridView;
 
        public ViewHolder(View itemView) {
            super(itemView);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
            zan_count = (TextView) itemView.findViewById(R.id.zan_count);
            love_count = (TextView) itemView.findViewById(R.id.love_count);
            gridView = (MultiImageView2) itemView.findViewById(R.id.gridview);
            zan = (ImageView) itemView.findViewById(R.id.zan);
            love = (ImageView) itemView.findViewById(R.id.love);
        }
    }
 
    private void addPic(final UserPushListEntity.UserPublishedListBean item) {
        if (images == null) {
            images = new ArrayList<>();
        } else {
            images.clear();
        }

        String url = item.getDisplayUrl();
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


         private onItemClickListener onn;

         public interface onItemClickListener{
             void onClick(int position);
             void onLoveClick(int position ,boolean isSend);
             void onZanClick(int position,boolean isSend);
         }
         public void onClick(onItemClickListener listener){
             onn = listener;
         }
}
