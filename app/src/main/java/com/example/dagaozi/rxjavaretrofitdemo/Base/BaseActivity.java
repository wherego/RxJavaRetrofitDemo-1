package com.example.dagaozi.rxjavaretrofitdemo.Base;

import android.support.v7.app.AppCompatActivity;

import com.example.dagaozi.rxjavaretrofitdemo.http.ApiMethods;

import butterknife.ButterKnife;
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
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(BaseActivity.this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(BaseActivity.this);
        if(mCompositeSubscription!=null)
            mCompositeSubscription.unsubscribe();
    }
}
