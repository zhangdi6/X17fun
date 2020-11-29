package com.x.x17fun.adapter;

import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


/* Created by AdScar
    on 2020/8/14.
      */

public class PhotoAdapter extends PagerAdapter {

    private ArrayList<ImageView> imageViews;
    private String[] split;

    public PhotoAdapter(ArrayList<ImageView> imageViews, String[] split) {

        this.imageViews = imageViews;
        this.split = split;
    }

    @Override
    public int getCount() {
        return imageViews.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = imageViews.get(position);
        Glide.with(container.getContext()).load(split[position]).into(imageView);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(imageViews.get(position));
    }
}
