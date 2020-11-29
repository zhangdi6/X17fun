package com.x.x17fun.utils;

import android.text.format.DateUtils;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class DateFormatUtils {

    public static final String FORMAT_1 = "MM月dd日 E";
    public static final String FORMAT_2 = "yyyyMMdd";
    public static final String FORMAT_3 = "yyyyMMddHHmmss" ;
    public static final String FORMAT_4 = "yyyy-MM-dd";
    public static final String FORMAT_5 = "yyyy年MM月dd日 HH:mm";
    public static final String FORMAT_6 = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_9 = "yyyy-MM-dd HH:mm";
    public static final String FORMAT_7 = "yyyyMMddHHMM";
    public static final String FORMAT_8 = "yyyy";




    public static String getFormattedDateString(String value, String format){

        Date date = getDateFromString(value);
        if(date != null){

           return new SimpleDateFormat(format, Locale.CHINA).format(date);
        }

        return null;
    }

   public static String getCurrentDateString(){

       long l = System.currentTimeMillis();
        return longToDate(l, FORMAT_6);
    }


    public static Date getDateFromString(String value){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT_5, Locale.CHINA);

        try {
           return simpleDateFormat.parse(value);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;

    }
    public static Date getDateFromString2(String value){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT_6, Locale.CHINA);

        try {
           return simpleDateFormat.parse(value);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;

    }


    public static String longToDate(long lo ,String Format) {

        Date date = new Date(lo);

        SimpleDateFormat sd = new SimpleDateFormat(Format);

        Log.e("time",sd.format(date));
        return sd.format(date);
    }

    public static String format(Date date) {
        DateFormat format = new SimpleDateFormat("yy-MM-dd hh:mm:ss");
        return format.format(date);
    }


    public static String longToYear(long lo) {

        Date date = new Date(lo);


        SimpleDateFormat sd = new SimpleDateFormat(FORMAT_8);

        return sd.format(date);
    }

}
