package com.example.love5;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


//sharedpreference存储

//总结：
//
//
public class Main6Activity extends AppCompatActivity {
    Button b1, b2, b3;
    EditText e1, e2;
    TextView t1, t2;
    CheckBox c1, c2;
    private String sex = null;
    //男为true ，女为false
    private boolean flag;
    //文件名
    private String na = "fhjgfs78453";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);

        //存储信息按钮
        b1 = findViewById(R.id.b1);
        //加载数据按钮
        b2 = findViewById(R.id.b2);
        //姓名
        e1 = findViewById(R.id.e1);
        //年龄
        e2 = findViewById(R.id.e2);
        //性别
        c1 = findViewById(R.id.c1);
        c2 = findViewById(R.id.c2);
        //状态提示
        t1 = findViewById(R.id.t1);
        //展示内容
        t2 = findViewById(R.id.t2);

        //使得复选框只能选一个
        //男
        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                在xml中可以用ischeck:true来设置默认为选中状态。
//
//                在activity中可以用setChecked(true);来设置为选中状态。
//
//                isChecked();语句为判断是否选中，返回一个boolean值。
                //这是取消选择功能属性，固定不变
//                c1.setClickable(false);
                //将其设备选中或者不选择状态
                c2.setChecked(false);
                sex = c1.getText().toString();
                flag = true;
            }
        });
        //女
        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c1.setChecked(false);
                sex = c2.getText().toString();
                flag = false;
            }
        });


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (e1.getText().toString().isEmpty() ||
                        e2.getText().toString().isEmpty() ||
                        sex.isEmpty()
                ) {
                    //空
                    Toast.makeText(getApplicationContext(), "输入不能为空", Toast.LENGTH_SHORT).show();
                    t1.setText("");
                } else {
                    //不空
                    if (isNumeric(e2.getText().toString())) {
                        //纯数字
                        t1.setText("存储中");
                        //存储，返回存储结果
                        String r = save(e1.getText().toString(), Integer.parseInt(e2.getText().toString()), sex, flag);
                        t1.setText(r);
                    } else {
                        //不是纯数字
                        Toast.makeText(getApplicationContext(), "年龄请写纯数字", Toast.LENGTH_SHORT).show();
                        t1.setText("");
                    }
                }

            }
        });

        //加载按钮
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences s = getSharedPreferences(na, MODE_PRIVATE);
                String name = s.getString("姓名", "");
                //false和true在这里似乎没区别
                boolean flag = s.getBoolean("flag", false);
                String sex = s.getString("性别", "");
                int age = s.getInt("年龄", 0);
                t2.setText("姓名：" + name + "\n年龄：" + age + "\n性别：" + sex);
                if (flag){
                    Toast.makeText(getApplicationContext(), "true", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(), "false", Toast.LENGTH_SHORT).show();
                }


            }
        });


//        //清空数据
        b3 = findViewById(R.id.b3);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Main6Activity", "234555555555555555555555555");
//                SharedPreferences.Editor editor =pref.edit();
                SharedPreferences.Editor editor = getSharedPreferences(na, MODE_PRIVATE).edit();
                editor.clear();
                editor.apply();
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

    //存储
    public String save(String name, int age, String sex, boolean flag) {
        SharedPreferences.Editor editor = getSharedPreferences(na, MODE_PRIVATE).edit();
        Log.d("Main6Activity", "看到我了吗"+name);
        editor.putString("姓名", name);
        editor.putInt("年龄", age);
        editor.putString("性别", sex);
        editor.putBoolean("flag", flag);
        editor.apply();
        return "成功";
    }


}
