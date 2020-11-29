package com.x.x17fun.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.ArrayRes;
import android.support.annotation.BoolRes;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntegerRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.x.x17fun.base.App;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 获取资源文件的工具类
 *
 * @author Ardy
 * @date 2018/7/9-下午4:56
 */
public class ResUtils {

    public static Resources getResources() {
        return App.defaultApp().getResources();
    }

    public static Drawable getDrawable(@DrawableRes int id) {
        return ContextCompat.getDrawable(App.defaultApp(), id);
    }

    public static String getString(@StringRes int id) {
        return getResources().getString(id);
    }

    @Deprecated
    public static int getColor(@ColorRes int id) {
        return ContextCompat.getColor(App.defaultApp(), id);
    }

    public static int getColor(Context context, @ColorRes int id) {
        return context.getResources().getColor(id);
    }

    public static int getColor(View view, @ColorRes int id) {
        return view.getContext().getResources().getColor(id);
    }

    public static float getDimens(@DimenRes int id) {
        return getResources().getDimension(id);
    }

    public static String[] getStringArray(@ArrayRes int id) {
        return getResources().getStringArray(id);
    }

    public static boolean getBoolean(@BoolRes int id) {
        return getResources().getBoolean(id);
    }

    public static int getInteger(@IntegerRes int id) {
        return getResources().getInteger(id);
    }

    public static String getAssets(String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = App.defaultApp().getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
