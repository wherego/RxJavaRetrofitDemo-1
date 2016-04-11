package com.example.dagaozi.rxjavaretrofitdemo.http;

import com.example.dagaozi.rxjavaretrofitdemo.model.HttpResult;
import com.example.dagaozi.rxjavaretrofitdemo.model.HttpResultModel;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by dagaozi on 2016/3/30.
 */
public class ApiFactory {
/*   private static ApiStores apiStores = null;
    public static ApiStores getApiStores() {
        if (apiStores == null) {
            apiStores = HttpMethods.getInstance().getApiStores();
        }
        return apiStores;
    }*/
    //线程管理、订阅
    protected Subscription toSubscribe(Observable o, Subscriber s)
    {
     return   o.subscribeOn(Schedulers.io())
            // .unsubscribeOn(Schedulers.io())
             .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }
    /**
     * 判断ResultCode，获取Data数据
     * @param <T>
     */
    protected class HttpResultGetData<T> implements Func1<HttpResult<T>,T>
    {
        @Override
        public T call(HttpResult<T> httpResult) {
            return httpResult.getSubjects();
        }
    }
    /**
     * 判断ResultCode，获取Data数据
     * @param <T>
     */
    protected class HttpResultFunc<T> implements Func1<HttpResultModel<T>,T>
    {
        @Override
        public T call(HttpResultModel<T> httpResult) {
            if(httpResult.getCode()!=0){
                throw new ApiException(httpResult.getCode());
            }
            return httpResult.getData();
        }
    }
}
