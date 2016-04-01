package com.example.dagaozi.rxjavaretrofitdemo;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.dagaozi.rxjavaretrofitdemo.Base.BaseActivity;
import com.example.dagaozi.rxjavaretrofitdemo.Base.BaseSubscriber;
import com.example.dagaozi.rxjavaretrofitdemo.Base.IBaseSubscriber;

import java.util.Objects;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;

public class OperatorActivity extends BaseActivity implements IBaseSubscriber {

    private static final int rang = 1;
    private static final int defer = 2;
    private static final int just = 3;
    private  StringBuilder result=new StringBuilder() ;
    @Bind(R.id.tvResult)
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator);
        ButterKnife.bind(this);
        //rangeTest();
        deferTest();
    }

    private void rangeTest() {
        //发射5个不大于等于的数据
        Observable.range(10, 5).subscribe(new BaseSubscriber<Integer>(this, rang));
    }
    private void deferTest(){
         Object i=10;
    /*  Observable deferObservalbe=  Observable.defer(new Func0<Observable<Object>>() {
            @Override
            public Observable<Object> call() {
                return Observable.just(i);
            }
        });*/
        Observable justObservable=Observable.just(i);
       i=12;
       justObservable.subscribe(new BaseSubscriber<Objects>(this,just));
      //  deferObservalbe.subscribe(new BaseSubscriber<Objects>(this,defer));
    }

    @Override
    public void onNext(Object o, int flag) {
        switch (flag) {
            case rang:
                result.append((Integer)o+" ");
               tvResult.setText(result.toString());
                Log.d("Rang", ((Integer) o) + "");
                break;
            case defer:
                tvResult.setText((Integer)o+"");
                break;
            case just:
                tvResult.setText((Integer)o+"");
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
