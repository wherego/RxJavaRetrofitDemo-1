package com.example.dagaozi.rxjavaretrofitdemo.Base;

import android.support.v7.app.AppCompatActivity;

import com.example.dagaozi.rxjavaretrofitdemo.http.ApiMethods;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by dagaozi on 216/3/30.
 */
public class BaseActivity extends AppCompatActivity {
    public static final ApiMethods apiMethods = ApiMethods.getInstance();
    private CompositeSubscription mCompositeSubscription;


    public CompositeSubscription getCompositeSubscription() {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        return this.mCompositeSubscription;
    }
    public void addSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(s);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mCompositeSubscription!=null)
            mCompositeSubscription.unsubscribe();
    }
}
