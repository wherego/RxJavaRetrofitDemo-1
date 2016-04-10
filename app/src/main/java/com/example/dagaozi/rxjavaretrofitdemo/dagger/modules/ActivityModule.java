package com.example.dagaozi.rxjavaretrofitdemo.dagger.modules;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dagaozi .（dagaozi@163.com）
 * 创建时间：2016/4/8 10:29
 * 类描述：
 */
@Module
public class ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity mActivity) {
        this.mActivity = mActivity;
    }
    @Provides
    Activity provideActivity(){
        return mActivity;
    }
}
