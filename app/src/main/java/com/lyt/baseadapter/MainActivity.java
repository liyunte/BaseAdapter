package com.lyt.baseadapter;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DemoAdapter demoAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rv_main_demo = findViewById(R.id.rv_main_demo);
        rv_main_demo.setLayoutManager(new LinearLayoutManager(this));
        View header = LayoutInflater.from(this).inflate(R.layout.app_header_demo,null);
        demoAdapter = new DemoAdapter();
        demoAdapter.addHeaderView(header);
        rv_main_demo.setAdapter(demoAdapter);
        rv_main_demo.setItemAnimator(new DefaultItemAnimator());
        findViewById(R.id.tv_main_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<DemoBean> list = new ArrayList<>();
                for (int i=0;i<10;i++){
                    DemoBean demoBean = new DemoBean(i,"李四"+i,i*4);
                    list.add(demoBean);
                }
                demoAdapter.setNewData(list);
            }
        });
        findViewById(R.id.tv_main_replace).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<DemoBean> list = new ArrayList<>();
                for (int i=0;i<10;i++){
                    DemoBean demoBean = new DemoBean(i,"王五"+i,i*4);
                    list.add(demoBean);
                }
                demoAdapter.addData(list);
            }
        });

        List<DemoBean> list = new ArrayList<>();
        for (int i=0;i<10;i++){
            DemoBean demoBean = new DemoBean(i,"张三"+i,i*4);
            list.add(demoBean);
        }
        demoAdapter.setNewData(list);
    }
}
