package com.example.dagaozi.rxjavaretrofitdemo.dagger.components;

import com.example.dagaozi.rxjavaretrofitdemo.Base.BaseActivity;
import com.example.dagaozi.rxjavaretrofitdemo.MainActivity;
import com.example.dagaozi.rxjavaretrofitdemo.dagger.modules.ActivityModule;
import com.example.dagaozi.rxjavaretrofitdemo.dagger.scope.ActivityScope;

import dagger.Component;

/**
 * Created by dagaozi .（dagaozi@163.com）
 * 创建时间：2016/4/10 21:05
 * 类描述：
 */

@ActivityScope
@Component(dependencies = AppComponent.class, modules ={ActivityModule.class}
)
public interface ActivityComponent {
    void inject(MainActivity activity);
    void inject(BaseActivity activity);
}
