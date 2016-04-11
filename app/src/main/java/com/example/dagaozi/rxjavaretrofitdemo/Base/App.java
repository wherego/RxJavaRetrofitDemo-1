package com.example.dagaozi.rxjavaretrofitdemo.Base;

import android.app.Application;

import com.example.dagaozi.rxjavaretrofitdemo.dagger.components.AppComponent;
import com.example.dagaozi.rxjavaretrofitdemo.dagger.components.DaggerAppComponent;
import com.example.dagaozi.rxjavaretrofitdemo.dagger.modules.AppModule;

/**
 * Created by dagaozi .（dagaozi@163.com）
 * 创建时间：2016/4/8 10:43
 * 类描述：
 */
public class App extends Application{
    private AppComponent component;
    @Override
    public void onCreate() {
        super.onCreate();
      setUpComponent();
    }

    private void setUpComponent() {
        component= DaggerAppComponent.builder().appModule(new AppModule(this)).build();
        component.inject(this);
    }
    public AppComponent getAppcomponet(){
        return component;
    }
}
