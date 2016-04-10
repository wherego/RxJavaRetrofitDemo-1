package com.example.dagaozi.rxjavaretrofitdemo.dagger.components;

import com.example.dagaozi.rxjavaretrofitdemo.dagger.modules.ActivityModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by dagaozi .（dagaozi@163.com）
 * 创建时间：2016/4/10 21:05
 * 类描述：
 */
@Singleton
@Component(dependencies = AppComponent.class, modules ={ActivityModule.class}
)
public interface ActivityComponent {
}
