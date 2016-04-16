package com.example.dagaozi.rxjavaretrofitdemo;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.dagaozi.rxjavaretrofitdemo.Base.BaseActivity;
import com.example.dagaozi.rxjavaretrofitdemo.Base.recyclerView.DividerItemDecoration;
import com.example.dagaozi.rxjavaretrofitdemo.Base.recyclerView.RefreshCommonAdapter;
import com.example.dagaozi.rxjavaretrofitdemo.Base.recyclerView.RefreshForRecyclerView;
import com.example.dagaozi.rxjavaretrofitdemo.Base.recyclerView.ViewHolder;
import com.example.dagaozi.rxjavaretrofitdemo.Utils.DataTestManager;
import com.example.dagaozi.rxjavaretrofitdemo.dagger.components.AppComponent;
import com.example.dagaozi.rxjavaretrofitdemo.model.Student;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class RefreshRecyclerViewActivity extends BaseActivity {
    @Bind(R.id.refres_recycl)
    RecyclerView refresRecycl;
    @Bind(R.id.swipe_container)
    RefreshForRecyclerView swipeContainer;
    private List<Student> listStudents = new ArrayList<>();
    private List<String> mDatas = new ArrayList<>();
    private RefreshCommonAdapter<Student> studentsAdapter = null;


    @Override
    protected void setUpComponent(AppComponent appComponent) {

    }

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_refresh_recycler_view);
        refresRecycl.setLayoutManager(new LinearLayoutManager(this));
        refresRecycl.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        studentsAdapter = new RefreshCommonAdapter<Student>(RefreshRecyclerViewActivity.this, R.layout.recycler_item, listStudents) {
            @Override
            public void convert(ViewHolder holder, Student student) {
                TextView tv = holder.getView(R.id.tvName);
                tv.setText(student.getName());
            }
        };

        refresRecycl.setAdapter(studentsAdapter);
        swipeContainer.setChildView(refresRecycl);
        swipeContainer.setColorSchemeResources(R.color.google_blue,
                R.color.google_green,
                R.color.google_red,
                R.color.google_yellow);
        initDatas();

    }

    private void initDatas() {
        Observable.just(DataTestManager.getStudents()).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<List<Student>>() {
            @Override
            public void call(List<Student> students) {
                swipeContainer.setRefreshing(false);
                listStudents.addAll(students);
                studentsAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void initEvents() {
        //使用SwipeRefreshLayout的下拉刷新监听
        //use SwipeRefreshLayout OnRefreshListener
        swipeContainer.setOnRefreshListener(new RefreshForRecyclerView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initDatas();
            }
        });


        //使用自定义的RefreshLayout加载更多监听
        //use customed RefreshLayout OnLoadListener
        swipeContainer.setOnLoadListener(new RefreshForRecyclerView.OnLoadListener() {
            @Override
            public void onLoad() {
                initDatas();
            }
        });

    }


}
