package com.example.dagaozi.rxjavaretrofitdemo;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dagaozi.rxjavaretrofitdemo.Base.BaseActivity;
import com.example.dagaozi.rxjavaretrofitdemo.Utils.RxBus;
import com.example.dagaozi.rxjavaretrofitdemo.model.Subject;
import com.example.dagaozi.rxjavaretrofitdemo.model.TaobaoModel;
import com.example.dagaozi.rxjavaretrofitdemo.model.Weather;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;

public class MainActivity extends BaseActivity {

    @Bind(R.id.tvTest)
    TextView tvTest;
    private Subscriber<Weather> subscriber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
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
        // getTaobaoData("21.22.11.33");
        RxBusText();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
