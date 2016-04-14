package com.example.dagaozi.rxjavaretrofitdemo.Base.recyclerView;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * Created by dagaozi .（dagaozi@163.com）
 * 创建时间：2016/4/14 21:36
 * 类描述：
 */
public class RefreshForRecyclerView extends SwipeRefreshLayout {
    private final int mTouchSlop;
    private RecyclerView mRecyclerView;
    private OnLoadListener mOnLoadListener;

    private float firstTouchY;
    private float lastTouchY;

    protected boolean isLoading = false;

    public RefreshForRecyclerView(Context context) {
        this(context, null);
    }

    public RefreshForRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    //set the child view of RefreshLayout,ListView

    public void setChildView(final RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    case SCREEN_STATE_ON:
                        break;
                    case SCREEN_STATE_OFF:
                        if (canLoadMore()) {
                            loadData();
                        }
                        break;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                firstTouchY = event.getRawY();
                break;

            case MotionEvent.ACTION_UP:
                lastTouchY = event.getRawY();
                break;
            default:
                break;
        }

        return super.dispatchTouchEvent(event);
    }

    private boolean canLoadMore() {
        return isBottom() && !isLoading && isPullingUp();
    }

    private boolean isBottom() {
        if (mRecyclerView.getAdapter().getItemCount()> 0) {
            if (mRecyclerView.getBottom() == mRecyclerView.getAdapter().getItemCount() - 1 &&
                    mRecyclerView.getChildAt(mRecyclerView.getChildCount() - 1).getBottom() <= mRecyclerView.getHeight()) {

                return true;
            }
        }

        return false;
    }

    private boolean isPullingUp() {
        return (firstTouchY - lastTouchY) >= mTouchSlop;
    }

    private void loadData() {
        if (mOnLoadListener != null) {
            setLoading(true);
        }
    }

    public void setLoading(boolean loading) {
        if (mRecyclerView == null) return;
        isLoading = loading;
        if (loading) {
            if (isRefreshing()) {
                setRefreshing(false);
            }
            mRecyclerView.setBottom(mRecyclerView.getAdapter().getItemCount() - 1);
            mOnLoadListener.onLoad();
        } else {
            firstTouchY = 0;
            lastTouchY = 0;
        }
    }

    public void setOnLoadListener(OnLoadListener loadListener) {
        mOnLoadListener = loadListener;
    }

    public interface OnLoadListener {
        public void onLoad();
    }
}
