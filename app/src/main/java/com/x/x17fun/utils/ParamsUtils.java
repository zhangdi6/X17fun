package com.x.x17fun.utils;

import com.x.x17fun.base.App;
import com.x.x17fun.base.AppContant;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

// Created by Ardy on 2020/1/6.
public class ParamsUtils {

    public static String getSign(HashMap<String,Object>map){

        TreeSet<String> objects1 = new TreeSet<>();
        for(Map.Entry<String, Object> entry: map.entrySet()) {
            objects1.add(entry.getKey()+"="+entry.getValue());
        }
        String s = objects1.toString();
        String s1 = s.replaceAll(", ","&");
        String substring = s1.substring(1, s1.length() - 1);
        //将所有参数排序后按 "aa=bbb&cc=nnn&dd=ppp&key=edencity_2"的格式拼接成sign参数
        String s2 = substring + "&key="+ AppContant.APPKEY;
        return s2;
    }

    //无参数的
    //此方法用来免去公共参数的添加和麻烦的加密转换
    public static HashMap getParamsMap(){
        String userId = DeeSpUtil.getInstance().getString("userId");
        String ticket = DeeSpUtil.getInstance().getString("ticket");
        HashMap<Object, Object> hashMap = new HashMap<>();
        if (userId!=null && ticket!=null && !userId.equals("") && !ticket.equals("")){
            hashMap.put("userId", userId);
            hashMap.put("ticket", ticket);
        }
       /* hashMap.put("app_id", AppContant.APPID);
        hashMap.put("nonce", "1");*/
      return hashMap;
    }
    //一个参数的
    public static HashMap getParamsMap(String paramKey , Object paramValue){
        String userId = DeeSpUtil.getInstance().getString("userId");
        String ticket = DeeSpUtil.getInstance().getString("ticket");
        HashMap<Object, Object> hashMap = new HashMap<>();
        if (userId!=null && ticket!=null && !userId.equals("") && !ticket.equals("")){
            hashMap.put("userId", userId);
            hashMap.put("ticket", ticket);
        }
       /* hashMap.put("app_id", AppContant.APPID);
        hashMap.put("nonce", "1");*/
        hashMap.put(paramKey, paramValue);
      return hashMap;
    }
    //两个参数的
    public static HashMap getParamsMap(String paramKey1 , Object paramValue1 , String paramKey2 , Object paramValue2 ){
        DeeSpUtil instance = App.getSp();
        String userId = instance.getString("userId");
        String ticket = instance.getString("ticket");
        HashMap<Object, Object> hashMap = new HashMap<>();
        if (userId!=null && ticket!=null && !userId.equals("") && !ticket.equals("")){
            hashMap.put("userId", userId);
            hashMap.put("ticket", ticket);
        }
        hashMap.put("app_id", AppContant.APPID);
        hashMap.put("nonce", "1");
        hashMap.put(paramKey1, paramValue1);
        hashMap.put(paramKey2, paramValue2);
      return hashMap;
    }
    //三个参数的
    public static HashMap getParamsMap(String paramKey1 , Object paramValue1 ,
                                        String paramKey2 , Object paramValue2,String paramKey3 , Object paramValue3 ){
        DeeSpUtil instance = App.getSp();
        String userId = instance.getString("userId");
        String ticket = instance.getString("ticket");
        HashMap<Object, Object> hashMap = new HashMap<>();
        if (userId!=null && ticket!=null && !userId.equals("") && !ticket.equals("")){
            hashMap.put("userId", userId);
            hashMap.put("ticket", ticket);
        }
        hashMap.put("app_id", AppContant.APPID);
        hashMap.put("nonce", "1");
        hashMap.put(paramKey1, paramValue1);
        hashMap.put(paramKey2, paramValue2);
        hashMap.put(paramKey3, paramValue3);
      return hashMap;
    }

    //无需userId 和 ticket的
    public static HashMap getParamsMapWithNoId(String paramKey1, Object paramValue1) {

        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("app_id", AppContant.APPID);
        if (paramKey1!=null && paramValue1!=null){
            hashMap.put(paramKey1, paramValue1);
        }
        hashMap.put("nonce", "1");
        return hashMap;
    }
}
