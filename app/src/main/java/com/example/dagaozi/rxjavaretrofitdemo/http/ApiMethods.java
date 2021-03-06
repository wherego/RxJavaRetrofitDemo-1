package com.example.dagaozi.rxjavaretrofitdemo.http;

import com.example.dagaozi.rxjavaretrofitdemo.dagger.components.ApiServiceComponent;
import com.example.dagaozi.rxjavaretrofitdemo.dagger.components.DaggerApiServiceComponent;
import com.example.dagaozi.rxjavaretrofitdemo.dagger.modules.ApiServiceModule;
import com.example.dagaozi.rxjavaretrofitdemo.model.Subject;
import com.example.dagaozi.rxjavaretrofitdemo.model.TaobaoModel;
import com.example.dagaozi.rxjavaretrofitdemo.model.Weather;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by dagaozi on 2016/3/30.
 */
public class ApiMethods extends ApiFactory {
    public ApiMethods(){
        ApiServiceComponent component= DaggerApiServiceComponent.builder().apiServiceModule(new ApiServiceModule()).build();
        component.inject(this);
    }
    @Inject
    ApiStores apiStores;
  /*  private static class SingletonHolder {
        private static final ApiMethods INSTANCE = new ApiMethods();
    }
    public static ApiMethods getInstance()
    {
        return  SingletonHolder.INSTANCE;
    }*/
    /**
     * 用于获取天气情况
     *
     * @param subscriber 由调用者传过来的观察者对象
     */
    public Subscription getWeather(Subscriber<Weather> subscriber, String cityId) {
        Observable observable=apiStores.getWeathRxjava(cityId);
        return  toSubscribe(observable, subscriber);
    }

    /**
     * 用于获取电影列表
     *
     * @param subscriber 由调用者传过来的观察者对象
     */
    public Subscription getMoves(Subscriber<List<Subject>> subscriber, int start ,int count) {
        Observable observable=apiStores.getTopMovie(start, count);
        return  toSubscribe(observable.map(new HttpResultGetData<List<Subject>>()),subscriber);
    }
//Dagger风格
    public Subscription getTaoboData(Subscriber<TaobaoModel> subscriber,String ip){
        Observable observable=apiStores.getTaobaoData(ip);
      return toSubscribe(observable.map(new HttpResultFunc<TaobaoModel>()),subscriber);
    }

}
