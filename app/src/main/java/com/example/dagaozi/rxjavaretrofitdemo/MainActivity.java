package com.example.dagaozi.rxjavaretrofitdemo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dagaozi.rxjavaretrofitdemo.Base.BaseActivity;
import com.example.dagaozi.rxjavaretrofitdemo.Base.BaseSubscriber;
import com.example.dagaozi.rxjavaretrofitdemo.Base.IBaseSubscriber;
import com.example.dagaozi.rxjavaretrofitdemo.Utils.RxBus;
import com.example.dagaozi.rxjavaretrofitdemo.model.Subject;
import com.example.dagaozi.rxjavaretrofitdemo.model.TaobaoModel;
import com.example.dagaozi.rxjavaretrofitdemo.model.Weather;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;

public class MainActivity extends BaseActivity implements IBaseSubscriber {

    @Bind(R.id.tvTest)
    TextView tvTest;
    @Bind(R.id.btnOper)
    Button btnOper;
    private Subscriber<Weather> subscriber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      /*  Observer<String> observer=new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.d("Tag",s);
            }
        };
        Observable observable =Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("hhahahh");
                subscriber.onNext("lalalal");
                subscriber.onCompleted();
            }
        });
        observable.subscribe(observer);*/
        //getWeather();
        //  getMoves( 0, 10);
        //getTaobaoData("21.22.11.33");
        // RxBusText();
        getTaobaodata2("21.22.11.33");

    }
    @OnClick(R.id.btnOper)
    public void btnClink(){
        Intent i=new Intent(MainActivity.this,OperatorActivity.class);
        startActivity(i);
    }
    private void getMoves(int start, int size) {
        Subscriber subscriber1 = new Subscriber<List<Subject>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                tvTest.setText(e.getMessage());
            }

            @Override
            public void onNext(List<Subject> subjectsBeans) {
                int size = subjectsBeans.size();
                tvTest.setText(subjectsBeans.get(0).getSubtype().toString());
                Toast.makeText(MainActivity.this, subjectsBeans.get(0).getOriginal_title().toString(), Toast.LENGTH_SHORT).show();

            }
        };
        Subscription sn = apiMethods.getMoves(subscriber1, 0, 10);
        addSubscription(sn);
    }

    private void getWeather() {
        subscriber = new Subscriber<Weather>() {
            @Override
            public void onCompleted() {
                Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                tvTest.setText(e.getMessage());
            }

            @Override
            public void onNext(Weather weather) {
                tvTest.setText(weather.getWeatherinfo().toString());
            }
        };
        Subscription sn = apiMethods.getWeather(subscriber, "101010100");
        addSubscription(sn);
        // apiMethods.getWeather4(Weather.class, subscriber,"101010100");
    }

    private void getTaobaoData(String ip) {
        Subscriber subscriber2 = new Subscriber<TaobaoModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(TaobaoModel taobaoModel) {
                tvTest.setText(taobaoModel.getCountry());
            }
        };
        Subscription sn = apiMethods.getTaoboData(subscriber2, ip);
        addSubscription(sn);

    }

    //使用BaseSubscriber访问网络
    private void getTaobaodata2(String ip) {
        Subscription sn = apiMethods.getTaoboData(new BaseSubscriber<TaobaoModel>(this, 1), ip);
        addSubscription(sn);
    }

    private void RxBusText() {
        Subscription sn = RxBus.getDefault().toOserverble(TaobaoModel.class)
                .subscribe(new Action1<TaobaoModel>() {
                    @Override
                    public void call(TaobaoModel taobaoModel) {
                        tvTest.setText(taobaoModel.getCountry());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        tvTest.setText("chucuola");
                    }
                });
        RxBus.getDefault().post(new TaobaoModel("RXBus测试"));
    }



    @Override
    public void onNext(Object o, int flag) {
        switch (flag) {
            case 1:
                tvTest.setText(((TaobaoModel) o).getCountry());
                break;
            default:
                tvTest.setText("没有对应flag");
        }

    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {

    }
}
