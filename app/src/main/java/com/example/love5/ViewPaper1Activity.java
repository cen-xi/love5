package com.example.love5;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import util.FragAdapter;
import util.Fragment1;
import util.Fragment2;
import util.Fragment3;
import util.Fragment4;
import util.MyOnPagerChangeListener;
import util.PagerAdaper;

/////////////////////////////////////////////////////////////
/*
总结：
1.多个子布局pager1,2,3,4分别由对应的fragment来绑定与该子布局里控件互动
2.多个fragment由fragAdaper适配器在活动页面进行绑定来绑定
3.ViewPager绑定适配器
4.实列监听类，监听了绑定接口
5.ViewPager绑定监听类



 */

/////////////////////////////////////////////////////////




public class ViewPaper1Activity extends AppCompatActivity implements MyOnPagerChangeListener.MyClickListener {
    private ViewPager viewpager;
//    private View view1, view2, view3;
//    private ArrayList<View> viewArrayList;
//    private List<String> titleList;

    private TextView t1, t2, t3, t4;
    //记录当前页面
    private int beforepage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_paper1);

//        viewPager = findViewById(R.id.viewpager);
//
//        LayoutInflater inflater = getLayoutInflater();
//        view1 = inflater.inflate(R.layout.paper1, null);
//        view2 = inflater.inflate(R.layout.pager2, null);
//        view3 = inflater.inflate(R.layout.pager3, null);
//
//        viewArrayList = new ArrayList<>();
//        viewArrayList.add(view1);
//        viewArrayList.add(view2);
//        viewArrayList.add(view3);
//
//
//        titleList = new ArrayList<String>();// 每个页面的Title数据
//        titleList.add("page1");
//        titleList.add("page2");
//        titleList.add("page3");


        t1 = findViewById(R.id.t1);
        t2 = findViewById(R.id.t2);
        t3 = findViewById(R.id.t3);
        t4 = findViewById(R.id.t4);




        //view已经在fragment里被绑定了，所以这里只要绑定相应的fragment就好了
        //构造适配器

        List<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(new Fragment1());
        fragments.add(new Fragment2());
        fragments.add(new Fragment3());
        fragments.add(new Fragment4());






         viewpager = (ViewPager) findViewById(R.id.viewpager);
        //设定适配器
        FragAdapter adapter = new FragAdapter(getSupportFragmentManager(), fragments);
        viewpager.setAdapter(adapter);
        MyOnPagerChangeListener m = new MyOnPagerChangeListener();
        m.setMyClickListener( ViewPaper1Activity.this);
        viewpager.setOnPageChangeListener(m);




        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewpager.setCurrentItem(0);
//                t2.setTextColor(Color.parseColor("#75050505"));
//                t3.setTextColor(Color.parseColor("#75050505"));
//                t4.setTextColor(Color.parseColor("#75050505"));
//                t1.setTextColor(Color.parseColor("#19A4F0"));
            }
        });
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewpager.setCurrentItem(1);
//                t1.setTextColor(Color.parseColor("#75050505"));
//                t3.setTextColor(Color.parseColor("#75050505"));
//                t4.setTextColor(Color.parseColor("#75050505"));
//                t2.setTextColor(Color.parseColor("#19A4F0"));
            }
        });
        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewpager.setCurrentItem(2);
//                t1.setTextColor(Color.parseColor("#75050505"));
//                t2.setTextColor(Color.parseColor("#75050505"));
//                t4.setTextColor(Color.parseColor("#75050505"));
//                t3.setTextColor(Color.parseColor("#19A4F0"));
            }
        });
        t4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewpager.setCurrentItem(3);
//                t1.setTextColor(Color.parseColor("#75050505"));
//                t2.setTextColor(Color.parseColor("#75050505"));
//                t3.setTextColor(Color.parseColor("#75050505"));
//                t4.setTextColor(Color.parseColor("#19A4F0"));
            }
        });
    }

    //接口返回的操作，即换页后的自动操作
    public void turnColor(int afterpage){
        switch (beforepage){
            case 0:
                t1.setTextColor(Color.parseColor("#75050505"));
                break;
            case 1:
                t2.setTextColor(Color.parseColor("#75050505"));

                break;
            case 2:
                t3.setTextColor(Color.parseColor("#75050505"));

                break;
            case 3:
                t4.setTextColor(Color.parseColor("#75050505"));

                break;
            default:
                break;
        }
        switch (afterpage){
            case 0:
                t1.setTextColor(Color.parseColor("#19A4F0"));
                break;
            case 1:
                t2.setTextColor(Color.parseColor("#19A4F0"));
                break;
            case 2:
                t3.setTextColor(Color.parseColor("#19A4F0"));
                break;
            case 3:
                t4.setTextColor(Color.parseColor("#19A4F0"));
                break;
            default:
                break;
        }
        beforepage = afterpage;

    }


}












/*
//        //如果适配器与活动分开写，适配器页类记得写一个接收参数的方法，分不分都可以，看个人爱好，最好还是不要分开，不然还要写监听接口，有点烦，但是整洁一点
//        //可是我个人觉得简洁不了多少，还是不要分开了好
//        PagerAdapter pagerAdapter =new PagerAdaper(viewArrayList);
//        viewPager.setAdapter(pagerAdapter);
*/
//        //适配器内置方法
//        PagerAdapter pagerAdapter = new PagerAdapter() {
//            @Override
//            public boolean isViewFromObject(@NonNull View arg0, @NonNull Object arg1) {
//                // TODO Auto-generated method stub
//                return arg0 == arg1;
//
//            }
//
//            //getCount()，返回滑动的View的个数。
//            @Override
//            public int getCount() {
//                // TODO Auto-generated method stub
//                return viewArrayList.size();
//            }
//
//            //destroyItem,从容器中删除指定position的View
//            @Override
//            public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
//                // TODO Auto-generated method stub
//                container.removeView(viewArrayList.get(position));
//
//            }
//
//            //instantiateItem（）方法中，我先讲指定position位置的View添加到容器中，末了，将本View返回。
//            //@NotNull：注解用在指明一个参数，字段或者方法的返回值不可以为null，是标准化的；
//            //@NonNull ：变量到一个被@NonNull修饰（标记）的参数的方法中时，开发工具IDE会警告程序可能会有崩溃的风险。这个是一个静态的分析方法，运行时不报任何警告；
//            @NonNull
//            @Override
//            public Object instantiateItem(ViewGroup container, int position) {
//                // TODO Auto-generated method stub
//                container.addView(viewArrayList.get(position));
//                return viewArrayList.get(position);
//            }
//
//
//            //给每个page标题
//            @Override
//            public CharSequence getPageTitle(int position) {
//                // TODO Auto-generated method stub
//                return titleList.get(position);
//
//            }
//
//        };
//        //添加适配器
//        viewPager.setAdapter(pagerAdapter);
//
//
//    }


//}


//
//
