package com.example.dagaozi.rxjavaretrofitdemo.dagger.components;

import com.example.dagaozi.rxjavaretrofitdemo.dagger.modules.ApiServiceModule;
import com.example.dagaozi.rxjavaretrofitdemo.http.ApiMethods;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by dagaozi .（dagaozi@163.com）
 * 创建时间：2016/4/11 16:26
 * 类描述：
 */
@Singleton
@Component(modules = {ApiServiceModule.class})
public interface ApiServiceComponent {
    void inject(ApiMethods apiMethods);
}
