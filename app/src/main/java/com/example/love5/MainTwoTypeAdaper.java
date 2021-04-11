package com.example.love5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import util.ChatAdaper;
import util.ChatTwoType;
import util.Myada;

public class MainTwoTypeAdaper extends AppCompatActivity  implements ChatAdaper.OnRecyclerItemClickListener{
    private RecyclerView recyclerView;
    private ChatAdaper adapter;
    private List<ChatTwoType> list;
    Button b1,b2;
    TextView t1;
    //识别是接收还是发送
    private int user = 2;
    private int rp = R.drawable.xiaren;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_two_type_adaper);
        //实列父view
        recyclerView = findViewById(R.id.r1);

        //实列化list
        list = new ArrayList<>();
        for (int i = 0 ; i < 20;i++){

            //        加入初始数据，可以不加
            list.add(new ChatTwoType(1, R.drawable.coffee, "cen", "吃饭没？\n\n\n5533\n44534"));
            list.add(new ChatTwoType(2, R.drawable.xiaren, "666", "吃了，你呢"));

        }

        //垂直布局，默认底下添加
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        //实列适配并且赋值,具体参数需要对应适配器方法里的参数顺序
        adapter = new ChatAdaper(list);
        //这是适配器的接口监听，如果没有，则会报错接口空指针异常
        adapter.setOnRecyclerItemClickListener(this);

        recyclerView.setAdapter(adapter);

        t1 =findViewById(R.id.t1);
        b1 = findViewById(R.id.b1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(t1.getText())){

                    ChatTwoType c = new ChatTwoType(user,rp,"666",t1.getText().toString());
                    list.add(c);
                    adapter.notifyItemInserted(list.size()-1);
                    recyclerView.scrollToPosition(list.size()-1);
                    t1.setText("");
                }
            }
        });

        b2 = findViewById(R.id.b2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                换成接收
                if(user == 2){
                    //                换成接收
                    rp = R.drawable.coffee;
                    user = 1;

                }else if(user == 1){
                    //换成发送
                    rp = R.drawable.xiaren;
                    user = 2;
                }

            }
        });



    }

    private ImageView imageView,imageView2;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        imageView = findViewById(R.id.item1i);
        int width = imageView.getWidth();
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        imageView.measure(w, h);
        ViewGroup.LayoutParams params = imageView.getLayoutParams();
        int height = width;
        params.height = height;
        imageView.setLayoutParams(params);

        imageView2 = findViewById(R.id.item2i);
        imageView2.setLayoutParams(params);
    }


    //使用接口里的方法
   public void a55(int u){
        if(u == 1){
            Toast.makeText(getApplicationContext(),"这是对方头像",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getApplicationContext(),"这是我的头像",Toast.LENGTH_SHORT).show();
        }


    }



}


























//        // 设置item及item中控件的点击事件
//        adapter.setOnItemClickListener(MyItemClickListener);
//
//
//    }
//
//    //监听控件
//    private ChatAdaper.OnRecyclerItemClickListener MyItemClickListener = new ChatAdaper.OnRecyclerItemClickListener() {
//
//
//        @Override
//
//        public void onItemClick(View v, int position) {
//
//            switch (v.getId()) {
//                case R.id.item1i:
//                    Toast.makeText(getApplicationContext(), "你点击了对方头像" +position , Toast.LENGTH_SHORT).show();
//                case R.id.item1t:
//                    Toast.makeText(getApplicationContext(), "你点击了对方文本" +position , Toast.LENGTH_SHORT).show();
//                case R.id.item2i:
//                    Toast.makeText(getApplicationContext(), "你点击了我的头像" +position , Toast.LENGTH_SHORT).show();
//                case R.id.item2t:
//                    Toast.makeText(getApplicationContext(), "你点击了我的文本" +position , Toast.LENGTH_SHORT).show();
//
//
//            }
//        }
//    };



