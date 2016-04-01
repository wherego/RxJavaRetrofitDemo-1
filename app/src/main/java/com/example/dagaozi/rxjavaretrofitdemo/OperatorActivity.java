package com.example.dagaozi.rxjavaretrofitdemo;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.dagaozi.rxjavaretrofitdemo.Base.BaseActivity;
import com.example.dagaozi.rxjavaretrofitdemo.Base.BaseSubscriber;
import com.example.dagaozi.rxjavaretrofitdemo.Base.IBaseSubscriber;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;

public class OperatorActivity extends BaseActivity implements IBaseSubscriber {

    private static final int rang = 1;
    private  StringBuilder result=new StringBuilder() ;
    @Bind(R.id.tvResult)
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator);
        ButterKnife.bind(this);
        rangeTest();
    }

    private void rangeTest() {
        //发射5个不小于10的数据
        Observable.range(10, 5).subscribe(new BaseSubscriber<Integer>(this, rang));
    }

    @Override
    public void onNext(Object o, int flag) {
        switch (flag) {
            case rang:
                result.append((Integer)o+" ");
               tvResult.setText(result.toString());
                Log.d("Rang", ((Integer) o) + "");
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
