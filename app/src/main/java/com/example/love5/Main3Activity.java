package com.example.love5;


import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

//记得继承点击监听接口啊啊啊啊啊啊啊！！！！！
public class Main3Activity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Button b2 = findViewById(R.id.b2);
        b2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Main3Activity.this, Main4Activity.class);
                //如果不需要传输下一页传输数据回来，则用下面这句指令
                startActivity(in);
            }
        });



        //对按钮b监听
        Button b = (Button) findViewById(R.id.b);
        b.setOnClickListener(this);



    }


    public void onClick(View v) {
        if (v.getId() == R.id.b) {
            final EditText id = findViewById(R.id.n1);
            final EditText psw = findViewById(R.id.n2);
            final String sid = id.getText().toString();
            final String spsw = psw.getText().toString();
            if (TextUtils.isEmpty(sid) || TextUtils.isEmpty(spsw) || sid.equals("") || spsw.equals("")) {
                Toast.makeText(getApplicationContext(), "输入不能为空！！", Toast.LENGTH_SHORT).show();
            } else {
                final TextView text = findViewById(R.id.n3);
                text.setText("正在上传》》》");
                sendRequestwithHttpURLConnection(sid,spsw);
            }
        }
    }

    private void sendRequestwithHttpURLConnection(final String iid, final String psw) {
        //开启线程来发起网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {



////依赖包则这样写get，减少很多代码，不会出现乱码
//                                try {
//                    OkHttpClient client =new OkHttpClient();
//                    Request request = new Request.Builder()
//                            .url("http://c2g1314.imwork.net:46084/android?username=岑远明&password=46545")
//                            .build();
//                    Response response = client.newCall(request).execute();
//                    String responsedata = response.body().string();
//                    showResponse(responsedata);

        //依赖写法post,不会出现乱码，建议用这个
             try{
                 OkHttpClient client =new OkHttpClient();
                 RequestBody rb = new FormBody.Builder()
                         .add("id",iid)
                         .add("psw",psw)
                         .build();
                    Request request = new Request.Builder()
                            .url("http://c2g1314.imwork.net:46084/android")
                            .post(rb)
                            .build();
                 Response response = client.newCall(request).execute();
                    String responsedata = response.body().string();
                    showResponse(responsedata);





            //这是原始写法post,出现中文乱码,最好是不用
//                HttpURLConnection conn = null;
//                BufferedReader reader = null;
//                try {
//                    URL url = new URL("http://c2g1314.imwork.net:46084/android");
//                    conn = (HttpURLConnection) url.openConnection();
//                    conn.setRequestMethod("POST");
//                    conn.setConnectTimeout(8000);
//                    conn.setReadTimeout(8000);
//                    DataOutputStream out = new DataOutputStream(conn.getOutputStream());
//                    out.writeBytes("username=岑远明&password=46545");
//                    InputStream in = conn.getInputStream();
//                    //解读获取的输入流
//                    reader = new BufferedReader(new InputStreamReader(in));
//                    StringBuffer response = new StringBuffer();
//                    String line;
//                    while ((line = reader.readLine()) != null) {
//                        response.append(line);
//                    }
//                    showResponse(response.toString());


                } catch (MalformedURLException e) {
                    e.printStackTrace();

                } catch (ProtocolException e) {
                    e.printStackTrace();

                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void showResponse(final String re) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //  这里进行ui操作
                final TextView text = findViewById(R.id.n3);
                text.setText(re);
            }
        });
    }


}

