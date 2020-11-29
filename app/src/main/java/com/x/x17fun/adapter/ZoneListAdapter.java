package com.x.x17fun.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.x.x17fun.R;
import com.x.x17fun.base.App;
import com.x.x17fun.custom.MultiImageView2;
import com.x.x17fun.entity.UserPushListEntity;
import com.x.x17fun.entity.ZoneEntity;
import com.x.x17fun.ui.activity.PhotoActivityActivity;
import com.x.x17fun.utils.ButtonUtils;
import com.x.x17fun.utils.DateFormatUtils;

import java.util.ArrayList;
import java.util.List;

public class ZoneListAdapter extends RecyclerView.Adapter {

    private Context mContext;
    public List<ZoneEntity.ZoneListBean> list = new ArrayList<>();
    private LayoutInflater mInflater;
    private ArrayList<String> images;

    public ZoneListAdapter(Context mContext) {

        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setList(List<ZoneEntity.ZoneListBean> list) {
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.dining_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int pos) {

        final ViewHolder viewHolder = (ViewHolder) holder;
        final ZoneEntity.ZoneListBean itemModle = list.get(pos);

        holder.itemView.setTag(pos);
        viewHolder.tvContent.setText(itemModle.getContent());


        String nickNameAndPortait = itemModle.getNickNameAndPortait();
        if (!TextUtils.isEmpty(nickNameAndPortait)){
            String[] split = nickNameAndPortait.split("[$][$]");
            String portait = split [1] ;
            String name = split [0] ;
            RequestOptions options = new RequestOptions().circleCrop();
            Glide.with(viewHolder.gridView.getContext())
                    .load(portait).apply(options).into(viewHolder.img_head);
            viewHolder.tv_name.setText(name);
        }

        viewHolder.tv_time.setText(itemModle.getCreatetime());
        viewHolder.zan_count.setText(itemModle.getAppreciateCount()+"");
        viewHolder.love_count.setText(itemModle.getCommentCount()+"");

       /* if (itemModle.getIsAppreciate().equals("1")){
            viewHolder.zan.setImageResource(R.mipmap.xinxinhong);
        }else{
            viewHolder.zan.setImageResource(R.mipmap.xinxin);
        }*/

        viewHolder.zan.setImageResource(R.mipmap.xinxin);
        viewHolder.love.setImageResource(R.mipmap.commend);

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
        viewHolder.btn_remark.setOnClickListener(v -> {
            if (onn!=null){
                if (!ButtonUtils.isFastDoubleClick(R.id.love,2000)){
                    onn.onRematkClick(pos);
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

    public void replaceData(List<ZoneEntity.ZoneListBean> zoneList) {
        if (list == zoneList || list.size() != zoneList.size()){
            return;
        }
        int position = 0;
        for (int i = 0; i < list.size(); i++) {
            ZoneEntity.ZoneListBean zoneListBean = list.get(i);
            if (zoneList.get(i).getCommentCount() != zoneListBean.getCommentCount()) {
                position = i;
            }
        }
        list.set(position,zoneList.get(position));
        notifyItemChanged(position);
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView zan;
        private final ImageView img_head;
        private final ImageView love;
        private final TextView zan_count;
        private final TextView love_count;
        private final TextView tv_name;
        private final TextView btn_remark;
        TextView tv_time;

        TextView tvContent;
        MultiImageView2 gridView;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
            zan_count = (TextView) itemView.findViewById(R.id.zan_count);
            love_count = (TextView) itemView.findViewById(R.id.love_count);
            gridView = (MultiImageView2) itemView.findViewById(R.id.gridview);
            zan = (ImageView) itemView.findViewById(R.id.zan);
            img_head = (ImageView) itemView.findViewById(R.id.img_head);
            love = (ImageView) itemView.findViewById(R.id.love);
            btn_remark = (TextView) itemView.findViewById(R.id.btn_remark);
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
             void onLoveClick(int position, boolean isSend);
             void onZanClick(int position, boolean isSend);
             void onRematkClick(int position);
         }
         public void onClick(onItemClickListener listener){
             onn = listener;
         }
}
