package com.example.dagaozi.rxjavaretrofitdemo.http;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dagaozi on 2016/3/25.
 */
public class
        HttpMethods {
    public static final String BASE_URL = "http://www.weather.com.cn/";

    private static final int DEFAULT_TIMEOUT = 5;

    private Retrofit retrofit;
    private ApiStores apiStores;

    private HttpMethods() {
        //OKhttp的日志系统
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //手动创建一个OkHttpClient并设置超时时间，和日志
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor);
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        apiStores = retrofit.create(ApiStores.class);
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    //获取单例
    public static HttpMethods getInstance() {
        return SingletonHolder.INSTANCE;
    }



    public ApiStores getApiStores() {
        return apiStores;
    }

}


