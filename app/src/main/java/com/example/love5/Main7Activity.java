package com.example.love5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.sql.ResultSet;
import java.util.Collections;
import java.util.List;

import util.People;

import static com.example.love5.Main6Activity.isNumeric;

public class Main7Activity extends AppCompatActivity {
    Button b, b1, b2, b3, b4;
    EditText e1, e2;
    TextView t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);
        //采集的信息
        e1 = findViewById(R.id.e1);
        e2 = findViewById(R.id.e2);
        //显示结果
        t1 = findViewById(R.id.t1);


        //数据库存储
        //创建数据库，使用litepal建立数据库，但是在没有save（）方法，也不知道哪里出现了问题，等问老师
        b = findViewById(R.id.b);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LitePal.getDatabase();
            }
        });

//        //原生建立数据库方法
//        b = findViewById(R.id.b);
//        b.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

//
        //添加，
        // 每次添加都必须把所有元素添加
//        //无自定义主键，数据可重复写法
//        b1 = findViewById(R.id.b1);
//        b1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (TextUtils.isEmpty(e1.getText()) || TextUtils.isEmpty(e2.getText())
//                ) {
//                    //空
//                    Toast.makeText(getApplicationContext(), "输入不能为空", Toast.LENGTH_SHORT).show();
//                } else {
//                    if (isNumeric(e2.getText().toString())) {
//                        //纯数字
//                        People p = new People();
//                        p.setUid(1);
//                        p.setName(e1.getText().toString());
//                        p.setAge( Long.parseLong(e2.getText().toString()));
//                        p.save();
//                        Toast.makeText(getApplicationContext(), "存储成功", Toast.LENGTH_SHORT).show();
//                        e1.setText("");
//                        e2.setText("");
//                    } else {
//                        //非纯数字
//                        Toast.makeText(getApplicationContext(), "年龄请输入纯数字", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//        });

        //因为主键时默认的，所以我要自建一个主键逻辑,但是元素（列名）不能是id，会默认成ID，
        //自建主键.添加操作，主键重复不得添加
        b1 = findViewById(R.id.b1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(e1.getText()) || TextUtils.isEmpty(e2.getText())
                ) {
                    //空
                    Toast.makeText(getApplicationContext(), "输入不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    if (isNumeric(e2.getText().toString())) {
                        //纯数字
                        //仅获取所有主键
                        List<People> peoples = DataSupport.select("uid").find(People.class);
                        //获取最大主键
                        Long id = (long) 1;
                        if (!peoples.isEmpty()) {
                            //表里有数据
                            for (People people : peoples) {
                                if (id < people.getUid()) {
                                    id = people.getUid();
                                }
                            }
                            //最大主键
                            id = id + (long) 1;
                        }
                        People p = new People();
                        p.setUid(id);
                        p.setName(e1.getText().toString());
                        p.setAge(Long.parseLong(e2.getText().toString()));
                        p.save();
                        Toast.makeText(getApplicationContext(), "存储成功", Toast.LENGTH_SHORT).show();
                        e1.setText("");
                        e2.setText("");
                    } else {
                        //非纯数字
                        Toast.makeText(getApplicationContext(), "年龄请输入纯数字", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });


        //查询
        b2 = findViewById(R.id.b2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //查询所有人
                List<People> peoples = DataSupport.findAll(People.class);
                String s = "";
                if (peoples.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "数据库为空！", Toast.LENGTH_SHORT).show();
                    t1.setText("");
                } else {
                    for (People people : peoples) {

                        s = s + people.getUid() + "\n" + people.getName() + "\n" + people.getAge() + "\n-------------------\n";

                    }
//                //查询某一个人信息,只要是需要条件，都需要用List来循环取值，即便提前知道只有一个，这里list没有length方法，这要注意
//                List<People> peoples = DataSupport.where("uid = ?","5").find(People.class);
//                String s = "";
//                for (People people : peoples) {
//                    s = s + people.getUid()+ "\n"  + people.getName() + "\n" + people.getAge() + "\n-------------------\n";
//
//                }
                    t1.setText(s);
                }


            }
        });


        //更改
        b3 = findViewById(R.id.b3);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //更改某个或者某些数据
                People people = new People();
                //更改自定义主键uid为5的age值为100，以后处理id数字最好用字符串来存储，这样才不会有数字位数的局限性
                people.setAge((long) 100);
                people.updateAll("uid = ?", "5");
            }
        });


        //删除
        b4 = findViewById(R.id.b4);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DataSupport.deleteAll(People.class,"条件 =/>/<等 ?","条件值");
                //删除表中的所有数据
                DataSupport.deleteAll(People.class);
            }
        });


    }

    //判断是否是纯数据
    public static boolean isNumeric(String str) {
        for (int i = 0; i < str.length(); i++) {

            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

}
