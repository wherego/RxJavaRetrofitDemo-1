package com.example.dagaozi.rxjavaretrofitdemo.http;

import com.example.dagaozi.rxjavaretrofitdemo.model.HttpResult;
import com.example.dagaozi.rxjavaretrofitdemo.model.HttpResultModel;
import com.example.dagaozi.rxjavaretrofitdemo.model.Subject;
import com.example.dagaozi.rxjavaretrofitdemo.model.TaobaoModel;
import com.example.dagaozi.rxjavaretrofitdemo.model.Weather;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by dagaozi on 2016/3/25.
 */
public interface ApiStores {
    @GET("adat/sk/{cityId}.html")
    Observable<Weather> getWeathRxjava(@Path("cityId") String cityId);

    @GET("https://api.douban.com/v2/movie/top250")
    Observable<HttpResult<List<Subject>>> getTopMovie(@Query("start") int start, @Query("count") int count);

    @GET("http://ip.taobao.com/service/getIpInfo.php?ip=21.22.11.33")
    Observable<HttpResultModel<TaobaoModel>> getTaobaoData(@Query("ip") String ip);

  //<T extends Object> Observable<HttpResultModel<T>> getData();
  @GET("adat/sk/{cityId}.html")
  <T> Observable<T> getWeatherResult5(Class<T> c,@Path("cityId") String ip);


}
