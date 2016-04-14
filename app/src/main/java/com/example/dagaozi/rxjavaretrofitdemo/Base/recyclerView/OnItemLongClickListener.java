package com.example.dagaozi.rxjavaretrofitdemo.Base.recyclerView;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by dagaozi .（dagaozi@163.com）
 * 创建时间：2016/4/14 8:36
 * 类描述：长按Item接口
 */
public interface OnItemLongClickListener<T> {
    boolean onItemLongClick(ViewGroup parent, View view, T t, int position);
}
