package com.example.dagaozi.rxjavaretrofitdemo.model;

/**
 * Created by dagaozi .（dagaozi@163.com）
 * 创建时间：2016/4/3 12:51
 * 类描述：
 */
public class Person {
    protected String name;
    protected  int gender;
    public Person(String name) {
        this.name = name;
    }
    public Person(String name,int gender) {
        this.name = name;
        this.gender=gender;
    }
}
