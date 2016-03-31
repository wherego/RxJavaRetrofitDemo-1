package com.example.dagaozi.rxjavaretrofitdemo.Base;

/**
 * Created by dagaozi on 2016/3/31.
 */
public interface IBaseSubscriber {
    void onStart();
    void onNext();
    void onComplate();
    void onError();
}
