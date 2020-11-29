package com.x.x17fun.data;

import android.util.Log;


import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.x.x17fun.BuildConfig;
import com.x.x17fun.base.AppContant;
import com.x.x17fun.utils.AdiUtils;
import com.x.x17fun.utils.ParamsUtils;
import com.x.x17fun.utils.SHA1Utils;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class DataService {

    private static final long DEFAULT_TIMEOUT = 20000;
    private static volatile LoginService mLoginApiService;
    private static volatile HomeService mHomeApiServcer;

    private static  DataService dataService;

    public static DataService getInstance(){
        if (dataService==null){
            return new DataService();
        }
        return dataService;
    }

     public static LoginService getLoginService() {
           if (mLoginApiService == null) {
                synchronized (DataService.class) {
                       if (mLoginApiService == null) {
                              HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                              if (BuildConfig.DEBUG) {
                                  logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                              } else {
                                  logging.setLevel(HttpLoggingInterceptor.Level.NONE);
                              }

                              final OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                                      .addInterceptor(logging)
                                      .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                                      .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                                      .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

                              Retrofit retrofit = new Retrofit.Builder()
                                      .client(httpClient.build())
                                      .addConverterFactory(GsonConverterFactory.create())
                                      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                                      .baseUrl(AppContant.BASE_URL)
                                      .build();
                           mLoginApiService = retrofit.create(LoginService.class);
                       }
                }
           }
           return mLoginApiService;
     }

    public static HomeService getHomeService() {
        if (mHomeApiServcer == null) {
            synchronized (DataService.class) {
                if (mHomeApiServcer == null) {
                    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                    if (BuildConfig.DEBUG) {
                        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                    } else {
                        logging.setLevel(HttpLoggingInterceptor.Level.NONE);
                    }

                    final OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                            .addInterceptor(logging)
                            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                            .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                            .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

                    Retrofit retrofit = new Retrofit.Builder()
                            .client(httpClient.build())
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .baseUrl(AppContant.BASE_URL)
                            .build();
                    mHomeApiServcer = retrofit.create(HomeService.class);
                }
            }
        }
        return mHomeApiServcer;
    }
}
