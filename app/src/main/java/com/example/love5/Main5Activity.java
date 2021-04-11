package com.example.love5;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


//文件存储

//总结：
// 文件存储，缺点是直接文本或文件存储，不好区分内容，但是我觉得适合存图片
public class Main5Activity extends AppCompatActivity {
    //存储按钮
    Button b1, b2;
    //存储文本
    EditText e1;
    //状态提示
    TextView t1,t2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        //状态提示
        t1 = findViewById(R.id.t1);
        //加载内容view
        t2 = findViewById(R.id.t2);
        //文件存储
        b1 = findViewById(R.id.b1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                e1 = findViewById(R.id.e1);
                if (!TextUtils.isEmpty(e1.getText())) {
                    //状态提示
                    t1.setText("存储中");
                    //存储，返回结果
                    String r = save(e1.getText().toString(), "date1234");
                    //状态提示
                    t1.setText(r);
                } else {
                    Toast.makeText(getApplicationContext(), "输入不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //加载文件
        b2 = findViewById(R.id.b2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //状态提示
                t1.setText("加载中");
                String data = load("date1234");
                if(!TextUtils.isEmpty(data)){
                    //状态提示
                    t1.setText("成功");
                    //显示内容
                    t2.setText(data);
                }else {
                    t1.setText("失败或为空");
                }

            }
        });



    }

    public String save(String data, String filename) {
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = openFileOutput(filename, Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            return "失败！io异常";
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return "失败！writer无法关闭";
                }
            }
        }
        return "成功";
    }

    public String load(String filename) {
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try {
            in = openFileInput(filename);
            reader = new BufferedReader(new InputStreamReader(in));
            //剩余总行数
            String line = "";
            while ((line= reader.readLine())!=null){
                content.append(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return content.toString();
    }


}


