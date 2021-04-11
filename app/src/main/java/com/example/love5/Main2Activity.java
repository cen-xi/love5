package com.example.love5;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.spec.AlgorithmParameterSpec;


public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //获取标签文字
//        final TextView text =findViewById(R.id.textview);
//        String str = text.getText().toString();
        //    final TextView text =findViewById(R.id.textview);
//                String str = text.getText().toString();

        //   隐藏系统自带的顶部标题栏，导入新的标题栏，详细看书本110页
        ActionBar act = getSupportActionBar();
        act.hide();

//        //获取上一页的数据
//        Intent in = getIntent();
//        String s = in.getStringExtra("data");
//        Toast.makeText(Main2Activity.this, s, Toast.LENGTH_LONG).show();


        //提示按键
        final Button b = findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                final TextView text =findViewById(R.id.textview);
//                String str = text.getText().toString();
                final EditText e = findViewById(R.id.et1);
//                TextUtils.isEmpty(）用来判断内容是否为空
                if (TextUtils.isEmpty(e.getText().toString())) {
//            工作台打印日志，相当于print        Log.d("Main2Activity","我在哪里，要到哪去？");

                    //可以用getApplicationContext()，这是通用，也可以用Main2Activity。this，有局限性，只能在onCreate()里使用
                    Toast.makeText(getApplicationContext(), "输入不能为空！！", Toast.LENGTH_SHORT).show();
                } else {
                    //ASCII加密
                    String str = e.getText().toString();
                    StringBuilder s = new StringBuilder();
                    char[] c = str.toCharArray();
                    for (int i = 0; i < str.length(); i++) {
                        int a = c[i];
                        a = a + 7;
                        s.append((char) a)  ;
                    }

                    //ascll解密一
//                    String d = "";
//                    char[] c2 = s.toString().toCharArray();
//                    for (int i = 0; i < s.length(); i++) {
//                        int b = c2[i];
//                        b = b - 7;
//                        d = d + (char) b;
//                    }
                    //ascll解密二
                    StringBuilder d = new StringBuilder();
                    char[] c2 = s.toString().toCharArray();
                    for (int i = 0; i < s.length(); i++) {
                        int b = c2[i];
                        b = b - 7;
                        d.append((char) b);
                    }
                    Toast.makeText(getApplicationContext(),s+"=加密解密对比="+d, Toast.LENGTH_SHORT).show();
                }
            }
        });

        //back键，即返回键，将要销毁当前页面
        Button b2 = findViewById(R.id.back);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent();
                in.putExtra("data2", "我是新页面传输回来的数据");
                setResult(RESULT_OK, in);
                finish();
            }
        });


        //登录。
        Button b3 = findViewById(R.id.login);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Main2Activity.this, Main3Activity.class);
                //如果不需要传输下一页传输数据回来，则用下面这句指令
                startActivity(in);
            }
        });


        //文件存储
        Button but1 = findViewById(R.id.but1);
        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Main2Activity.this, Main5Activity.class);
                startActivity(in);
            }
        });

        //SharedPreferences存储
        Button but2 = findViewById(R.id.but2);
        but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Main2Activity.this, Main6Activity.class);
                startActivity(in);
            }
        });

        //sqllite数据库存储
        Button but3 = findViewById(R.id.but3);
        but3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Main2Activity.this, Main7Activity.class);
                startActivity(in);
            }
        });

    }


    //按手机金刚键的返回键传输数据会旧页面
//    @Override
//    public void onBackPressed() {
//        Intent in = new Intent();
//        in.putExtra("data2", "我是新页面传输回来的数据");
//        setResult(RESULT_OK, in);
//        finish();
//    }

//    //销毁活动会自动运行这条方法
//    @Override
//    protected void onDestroy(){
//        super.onDestroy();
//        //下面写操作内容
//    }


}
