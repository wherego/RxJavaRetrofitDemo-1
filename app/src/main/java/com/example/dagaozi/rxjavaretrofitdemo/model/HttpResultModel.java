package com.example.dagaozi.rxjavaretrofitdemo.model;

/**
 * Created by dagaozi on 2016/3/29.
 */
public class HttpResultModel<T> {
    private int code;
    private  T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
