package com.example.dagaozi.rxjavaretrofitdemo.Base;

/**
 * Created by dagaozi on 2016/3/31.
 */
public interface IBaseSubscriber<T> {
   // void preStart();
    void onNext(T t,int flag);
    void onCompleted();
    void onError(Throwable e);
}
