package com.example.dagaozi.rxjavaretrofitdemo.Base;

import android.app.Application;

import com.example.dagaozi.rxjavaretrofitdemo.dagger.components.AppComponent;

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
       setupGraph();
    }

    private void setupGraph() {
       /* component=DaggerAppComponent*//**/
    }
}
