package com.example.dagaozi.rxjavaretrofitdemo.dagger.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by dagaozi .（dagaozi@163.com）
 * 创建时间：2016/4/11 9:33
 * 类描述：
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityScope {
}
