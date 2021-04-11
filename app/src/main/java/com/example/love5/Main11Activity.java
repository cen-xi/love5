package com.example.love5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


import util.Myadaper;
import util.Photo;
import util.PhotoAdaper2;
import util.Myada;


//多面板就看这个网址https://blog.csdn.net/qq_38225558/article/details/80627273

public class Main11Activity extends AppCompatActivity implements Myada.OnRecyclerItemClickListener , Myadaper.OnRecyclerItemClickListener{
    private PhotoAdaper2 adaper2;
    private Myada myada;
    private Myadaper m;

    private List<Photo> photolist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main11);

        //点击按钮才会添加，可以用
//        Button b = findViewById(R.id.b);
//
//        b.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//
//                //初始化水果数据
//                initPhoto();
//
//
//
//                myada = new Myada(Main11Activity.this, photolist);
//                myada.setOnRecyclerItemClickListener(Main11Activity.this);
//                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
//                recyclerView.setAdapter(myada);
//
//                DefaultItemAnimator animator = new DefaultItemAnimator();
//                animator.setRemoveDuration(1000);
//                recyclerView.setItemAnimator(animator);
//                //recyclerView.addItemDecoration(new MyDividerItemDecoration(this,MyDividerItemDecoration.VERTICAL_LIST));
//
//                //最后一个参数是反转布局一定是false,为true的时候为逆向显示，在聊天记录中可能会有使用
//                //这个东西在显示后才会加载，不会像ScollView一样一次性加载导致内存溢出
//                LinearLayoutManager layoutManager = new LinearLayoutManager(Main11Activity.this, LinearLayoutManager.VERTICAL, false);
//                recyclerView.setLayoutManager(layoutManager);
//
//
//
//
//            }
//        });




//        //初始化水果数据
//        initPhoto();

//        //实列主页面的RecyclerView布局控件
//        RecyclerView recyclerView =findViewById(R.id.recycler_view);
//
//        //单一listview布局监听
//        //默认垂直布局
//    //书本教的方法，不全面    LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this);
//        //这才是完整吗
//        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
////        若是横向滑动需要这一句，false表示从地下开始添加子布局，true则表示从顶上开始往上布局，比如用到的聊天记录之类功能就会用到
////        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//
//        //多条listview布局监听
////        //瀑布布局则加上如下一句,第一个参数是数字，表示有多少列
////        StaggeredGridLayoutManager linearLayoutManager = new StaggeredGridLayoutManager(5,StaggeredGridLayoutManager.VERTICAL);
//
//
////使用单一或者多条布局监听需要提前设定好，然后将布局监听模式加入主页面的RecyclerView布局控件的属性里
//        recyclerView.setLayoutManager(linearLayoutManager);
//        //实列子布局的适配器，并把装有泛型数据的list提供给适配器，适配器需要什么功能，就在对应的位置添加，不同的是匹配器代码不同，但是效果可能一样的
//        //这里的this是传进PhotoAdaper2的Context context，可有可没有，若没有
//       adaper2 =new PhotoAdaper2(this,photolist);
//        //适配器其实还可以设计很多属性，书本没讲
//
//
//        //将制作好的适配器加入主页面的RecyclerView布局控件里
//        recyclerView.setAdapter(adaper2);


//左边
        //初始化水果数据
        initPhoto();
        myada = new Myada(this, photolist);
        myada.setOnRecyclerItemClickListener(this);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setAdapter(myada);

//        //删除动画延时，若使用默认可不写
//        DefaultItemAnimator animator = new DefaultItemAnimator();
//        animator.setRemoveDuration(1000);
//        recyclerView.setItemAnimator(animator);

        //recyclerView.addItemDecoration(new MyDividerItemDecoration(this,MyDividerItemDecoration.VERTICAL_LIST));

        //最后一个参数是反转布局一定是false,为true的时候为逆向显示，在聊天记录中可能会有使用
        //这个东西在显示后才会加载，不会像ScollView一样一次性加载导致内存溢出
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        //点击添加，底下新的子页面
        Button b1 = findViewById(R.id.b1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Photo a = new Photo("我爱你",R.drawable.chat3);

                myada.insert(a);



            }
        });


        //点击添加，底下新的子页面
        Button b2 = findViewById(R.id.b2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Photo a = new Photo("我爱你",R.drawable.chat3);
                myada.inserttou(a);


            }
        });







        //右边
        List<Photo> lp = new ArrayList<>();
         m = new Myadaper(this,lp);
        m.setOnRecyclerItemClickListener(this);
        //
        RecyclerView re = findViewById(R.id.recycler_view2);
        re.setAdapter(m);
        //
        DefaultItemAnimator an = new DefaultItemAnimator();
        an.setRemoveDuration(1000);
        re.setItemAnimator(an);
        //
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);
        re.setLayoutManager(layoutManager2);


        //点击添加，底下新的子页面
//        Button b2 = findViewById(R.id.b2);
//        b2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Photo a = new Photo("我爱你",R.drawable.chat3);
//                 m.insert(a);
//
//
//            }
//        });











    }



    @Override
    public void OnRecyclerItemClick(RecyclerView parent, View view, int position, String data) {
//        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();

        TextView textView = findViewById(R.id.t1);
        textView.setText(position+"======"+data);


//        MYAdapter.remove(position);
    }


    public void OnRecyclerItemClick2( int position,Photo photo) {
//        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
        //传进来的position是子控件的顺序数字及list下标在这里只能是个数字，photo在这个是个新的Photo类型值，存储了被点击的子控件整体，如果内部有多个控件，则不识别具体是哪个被点击
        TextView textView = findViewById(R.id.t1);
        textView.setText(position+photo.getName()+"==="+photo.getImageid() );



//        MYAdapter.remove(position);
    }

    public  void Oinia(){

        Toast.makeText(getApplicationContext(),"woaini ",Toast.LENGTH_LONG).show();



    }














    //初始化水果数据的具体方法
    //这里收录每个小组件的信息
    private void initPhoto() {
//        for (int i = 0; i < 20; i++) {
            //R.+路径 返回来的是一串数字
            //实列图片类泛型
            Photo xiaren = new Photo("虾仁", R.drawable.xiaren);
            //将泛型数据加入list类里
            photolist.add(xiaren);
            Photo coffee = new Photo("咖啡", R.drawable.coffee);
            photolist.add(coffee);
//        }
    }


}
