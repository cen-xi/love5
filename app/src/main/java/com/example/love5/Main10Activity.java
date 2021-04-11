package com.example.love5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import util.Photo;
import util.PhotoAdaper;

public class Main10Activity extends AppCompatActivity {
    private List<Photo> photolist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main10);
        //初始化水果数据
        initPhoto();
//三个参数分别是该监视后台名，自定义的单一组件页面文件路径返回的一串数字，数据list集合)
        PhotoAdaper adaper = new PhotoAdaper(Main10Activity.this, R.layout.photo_view, photolist);
        //l1为与本页面后台对应的前端页面ListView组件
        ListView listView = findViewById(R.id.l1);
        listView.setAdapter(adaper);
        //点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //识别是哪个小组件被触发
                Photo photo =photolist.get(position);
                Toast.makeText(getApplicationContext(),photo.getName(),Toast.LENGTH_SHORT).show();
            }
        });


    }


    //这是单一的横或者竖布局使用
    //这里收录每个小组件的信息
    private void initPhoto() {
        for (int i = 0; i < 20; i++) {
            //R.+路径 返回来的是一串数字
            Photo xiaren = new Photo("虾仁", R.drawable.xiaren);
            photolist.add(xiaren);
            Photo coffee = new Photo("咖啡", R.drawable.coffee);
            photolist.add(coffee);
        }
    }


}
