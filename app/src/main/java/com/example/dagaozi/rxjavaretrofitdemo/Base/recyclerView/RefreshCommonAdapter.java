package com.example.dagaozi.rxjavaretrofitdemo.Base.recyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dagaozi.rxjavaretrofitdemo.R;

import java.util.List;

/**
 * Created by dagaozi .（dagaozi@163.com）
 * 创建时间：2016/4/15 9:29
 * 类描述：
 */
public abstract class RefreshCommonAdapter<T> extends CommonAdapter<T> {

    private final int TYPE_ITEM=0;
    private final int TYPE_FOOTER=1;

    public RefreshCommonAdapter(Context context, int layoutId, List<T> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public int getItemCount() {
        return mDatas.size()==0? 0:mDatas.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
       if(position+1==getItemCount()){
           return TYPE_FOOTER;
       }else{
           return TYPE_ITEM;
       }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType==TYPE_ITEM){
            return super.onCreateViewHolder(parent, viewType);
        }
        else if(viewType==TYPE_FOOTER){
            View view= LayoutInflater.from(mContext).inflate(R.layout.listview_footer,parent,false);
        }
        return null;
    }
    static class FootViewHolder extends RecyclerView.ViewHolder {
        public FootViewHolder(View itemView) {
            super(itemView);
        }
    }
}
