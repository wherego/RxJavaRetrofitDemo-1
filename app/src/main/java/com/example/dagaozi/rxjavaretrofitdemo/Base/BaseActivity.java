package com.example.dagaozi.rxjavaretrofitdemo.Base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.dagaozi.rxjavaretrofitdemo.dagger.components.AppComponent;
import com.example.dagaozi.rxjavaretrofitdemo.dagger.components.DaggerActivityComponent;
import com.example.dagaozi.rxjavaretrofitdemo.dagger.modules.ActivityModule;
import com.example.dagaozi.rxjavaretrofitdemo.http.ApiMethods;

import javax.inject.Inject;

import butterknife.ButterKnife;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by dagaozi on 216/3/30.
 */
public abstract class BaseActivity extends AppCompatActivity {
   // public static final ApiMethods apiMethods = ApiMethods.getInstance();
    private CompositeSubscription mCompositeSubscription;
    @Inject
    public ApiMethods apiMethods;

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

       protected void setUpActivityComponet(){
           DaggerActivityComponent.builder().appComponent(getAppComponent()).activityModule(new ActivityModule(this)).build().inject(this);

    }
    protected AppComponent getAppComponent() {

        return ((App) getApplication()).getAppcomponet();
    }
    protected  abstract void setUpComponent(AppComponent appComponent);
    protected abstract void initViews();
    protected  abstract void initEvents();

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(BaseActivity.this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpComponent(getAppComponent());
        setUpActivityComponet();
        initViews();
        initEvents();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(BaseActivity.this);
        if(mCompositeSubscription!=null)
            mCompositeSubscription.unsubscribe();
    }
}
