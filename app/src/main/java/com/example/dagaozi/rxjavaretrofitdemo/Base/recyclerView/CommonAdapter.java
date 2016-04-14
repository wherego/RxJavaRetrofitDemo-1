package com.example.dagaozi.rxjavaretrofitdemo.Base.recyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

;

/**
 * Created by dagaozi .（dagaozi@163.com）
 * 创建时间：2016/4/12 22:43
 * 类描述：通用Adapter
 */
public abstract class CommonAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener=onItemClickListener;
    }
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener){
        this.mOnItemLongClickListener=onItemLongClickListener;
    }
    public CommonAdapter(Context context,int layoutId,List<T> datas){
        mContext=context;
        mInflater=LayoutInflater.from(context);
        mLayoutId=layoutId;
        mDatas=datas;
    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        ViewHolder viewHolder=ViewHolder.get(mContext,null,parent,mLayoutId,-1);
        setListener(parent, viewHolder, viewType);
        return viewHolder;


    }
    protected int getPosition(RecyclerView.ViewHolder viewHolder){
                return viewHolder.getAdapterPosition();
           }
    protected boolean isEnabled(int viewType){
                 return true;
    }

    protected void setListener(final ViewGroup parent, final ViewHolder viewHolder, int viewType) {
        if (!isEnabled(viewType)) return;
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = getPosition(viewHolder);
                    mOnItemClickListener.onItemClick(parent, v, mDatas.get(position), position);
                                   }
                           }
                   });

            viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener()
                        {
                       @Override
                        public boolean onLongClick(View v)
                       {
                                 if (mOnItemClickListener != null)
                                   {
                                        int position = getPosition(viewHolder);
                                        return mOnItemLongClickListener.onItemLongClick(parent, v, mDatas.get(position), position);
                                   }
                                return false;
                            }
                   });
            }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
     holder.updatePosition(position);
        convert(holder, mDatas.get(position));
    }
public abstract void convert(ViewHolder holder,T t);
    @Override
    public int getItemCount() {
        return mDatas.size();
    }
}
