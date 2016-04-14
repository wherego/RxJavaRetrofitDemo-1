package com.example.dagaozi.rxjavaretrofitdemo;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dagaozi.rxjavaretrofitdemo.Base.BaseActivity;
import com.example.dagaozi.rxjavaretrofitdemo.Base.recyclerView.CommonAdapter;
import com.example.dagaozi.rxjavaretrofitdemo.Base.recyclerView.DividerItemDecoration;
import com.example.dagaozi.rxjavaretrofitdemo.Base.recyclerView.OnItemClickListener;
import com.example.dagaozi.rxjavaretrofitdemo.Base.recyclerView.OnItemLongClickListener;
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
    private CommonAdapter<Student> studentsAdapter=null;

    @Override
    protected void setUpComponent(AppComponent appComponent) {

    }

    @Override
    protected void initViews() {

        setContentView(R.layout.activity_recycle_view);
        recyclerTest.setLayoutManager(new LinearLayoutManager(this));
        recyclerTest.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        studentsAdapter=new CommonAdapter<Student>(RecycleViewActivity.this, R.layout.recycler_item, listStudents) {
            @Override
            public void convert(ViewHolder holder, Student student) {
                TextView tv = holder.getView(R.id.tvName);
                tv.setText(student.getName());
            }
        };

        recyclerTest.setAdapter(studentsAdapter);
         initDatas();

    }
        @Override
    protected void initEvents() {
            studentsAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                    Toast.makeText(RecycleViewActivity.this, position + "click", Toast.LENGTH_SHORT).show();
                }
            });
            studentsAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                    Toast.makeText(RecycleViewActivity.this, position + "longClick", Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
    }

    private void initDatas()
    {

            Observable.just(DataTestManager.getStudents()).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<List<Student>>() {
            @Override
            public void call(List<Student> students) {

                listStudents.addAll(students);
                studentsAdapter.notifyDataSetChanged();
            }
        });
    }
}
