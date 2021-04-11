package com.example.love5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;


//普通view
public class Main9Activity extends AppCompatActivity {
//    private String[] data = {"1111", "22", "32342"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main9);
        //定义40个数据用使用
        String[] data= new String[40];
        for(int i = 0 ; i < 40; i++ ){
            data[i] = (i+1) +"";
        }
        //android.R.layout.simple_list_item_1指嵌入view的子布局
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_list_item_1, data);
        ListView l1 = findViewById(R.id.l1);
        l1.setAdapter(adapter);

    }
}
