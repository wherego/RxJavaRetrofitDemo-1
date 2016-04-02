package com.example.dagaozi.rxjavaretrofitdemo;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.dagaozi.rxjavaretrofitdemo.Base.BaseActivity;
import com.example.dagaozi.rxjavaretrofitdemo.Base.BaseSubscriber;
import com.example.dagaozi.rxjavaretrofitdemo.Base.IBaseSubscriber;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;
import rx.functions.Func0;

public class OperatorActivity extends BaseActivity implements IBaseSubscriber {

    private static final int rang = 1;
    private static final int defer = 2;
    private static final int just = 3;
    @Bind(R.id.btnTest)
    Button btnTest;
    private StringBuilder result = new StringBuilder();
    @Bind(R.id.tvResult)
    TextView tvResult;
    Observable justObservable;
    Observable deferObservalbe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator);
        deferAndJust();

    }

    private void deferAndJust() {
        final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String time = df.format(new Date());
         justObservable = Observable.just(df.format(new Date()));

         deferObservalbe = Observable.defer(new Func0<Observable<String>>() {
             @Override
             public Observable<String> call() {
                 return Observable.just(df.format(new Date()));
             }
         });
    }

    @OnClick(R.id.btnTest)
    public void btnClink(){
        rangeTest();
       // deferTest();
    }
    private void rangeTest() {
        //发射5个不大于等于的数据
        Observable.range(10, 5).subscribe(new BaseSubscriber<Integer>(this, rang));
    }

    //defer是在订阅那一刻才赋值;just在创建Observable的时候就赋值。
    private void deferTest() {
        //创建justObservable时已经赋值
        //justObservable.subscribe(new BaseSubscriber<String>(this, just));
        //这一刻才赋值
        deferObservalbe.subscribe(new BaseSubscriber<Objects>(this,defer));
    }

    @Override
    public void onNext(Object o, int flag) {
        switch (flag) {
            case rang:
                result.append((Integer) o + " ");
                tvResult.setText(result.toString());
                Log.d("Rang", ((Integer) o) + "");
                break;
            case just:
                tvResult.setText((String) o);
            case defer:
                tvResult.setText((String) o);
                break;

            default:
                Log.d("default", "no fand flag");
        }
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {

    }
}
