package com.example.dagaozi.rxjavaretrofitdemo.Base;

import rx.Subscriber;

/**
 * Created by dagaozi on 2016/3/31.
 */
public class BaseSubscriber<T> extends Subscriber<T> {
    private  IBaseSubscriber iBaseSubscriber;
    private int flag;

    public BaseSubscriber(IBaseSubscriber iBaseSubscriber,int flag) {
        this.iBaseSubscriber = iBaseSubscriber;
        this.flag=flag;
    }

  /*  @Override
    public void onStart() {
        iBaseSubscriber.preStart();
    }*/
    @Override
    public void onNext(T t) {
     iBaseSubscriber.onNext(t,flag);
    }

    @Override
    public void onCompleted() {
      iBaseSubscriber.onCompleted();
    }

    @Override
    public void onError(Throwable e) {
 iBaseSubscriber.onError(e);
    }


}
