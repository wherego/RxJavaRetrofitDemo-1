package com.example.dagaozi.rxjavaretrofitdemo.dagger.modules;

import android.content.Context;
import android.content.res.Resources;

import com.example.dagaozi.rxjavaretrofitdemo.Base.App;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dagaozi .（dagaozi@163.com）
 * 创建时间：2016/4/8 10:49
 * 类描述：
 */
@Module
public class AppModule {
    private  App app;
    public AppModule (App app){
        this.app=app;
    }
    @Provides
    @Singleton
    public Context produceAppContext(){
        return app;
    }
    @Provides
    @Singleton
    public Resources provideResources(){
        return app.getResources();
    }
}
