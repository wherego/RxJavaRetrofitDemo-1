package com.example.dagaozi.rxjavaretrofitdemo;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.dagaozi.rxjavaretrofitdemo.Base.BaseActivity;
import com.example.dagaozi.rxjavaretrofitdemo.Base.recyclerView.CommonAdapter;
import com.example.dagaozi.rxjavaretrofitdemo.Base.recyclerView.DividerItemDecoration;
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

public class RecycleViewActivity extends BaseActivity {

    @Bind(R.id.recyclerTest)
    RecyclerView recyclerTest;
    private List<Student> listStudents= new ArrayList<>();
    private List<String> mDatas = new ArrayList<>();

    @Override
    protected void setUpComponent(AppComponent appComponent) {

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view);
        listStudents= DataTestManager.getStudents();
        recyclerTest.setLayoutManager(new LinearLayoutManager(this));
        recyclerTest.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
       // initDatas();
        Observable.just(DataTestManager.getStudents()).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<List<Student>>() {
            @Override
            public void call(List<Student> students) {
                recyclerTest.setAdapter(new CommonAdapter<Student>(RecycleViewActivity.this, R.layout.recycler_item, students) {
                    @Override
                    public void convert(ViewHolder holder, Student student) {
                        TextView tv = holder.getView(R.id.tvName);
                        tv.setText(student.getName());
                    }
                });
            }
        });
       /* recyclerTest.setAdapter(new CommonAdapter<String>(RecycleViewActivity.this, R.layout.recycler_item, mDatas)
        {
            @Override
            public void convert(ViewHolder holder, String s)
            {
                holder.setText(R.id.tvName, s);
            }
        });*/

    }
    private void initDatas()
    {
        for (int i = 'A'; i < 'z'; i++)
        {
            mDatas.add((char) i + "");
        }
    }
}
