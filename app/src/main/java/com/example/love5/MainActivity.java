package com.example.love5;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    protected void onCreate(Bundle canshu) {
        super.onCreate(canshu);
        setContentView(R.layout.activity_main);

        //   隐藏系统自带的顶部标题栏，导入新的标题栏，详细看书本110页
        ActionBar act = getSupportActionBar();
        act.hide();


        Button bnext = findViewById(R.id.bnext);
        bnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //       跳页
                Intent in = new Intent(MainActivity.this, Main2Activity.class);
                //如果不需要传输下一页传输数据回来，则用下面这句指令
//                startActivity(in);
                //如果需要传输下一页传输数据回来，则用下面这句指令
                startActivityForResult(in, 1);

////                如果传输一些数据，这如下：
//                Intent in = new Intent(MainActivity.this, Main2Activity.class);
//                in.putExtra("data","我是上一个页面传输过来的数据");
//                startActivity(in);

            }
        });

        //跳转到listview功能的页面
        Button b2 = findViewById(R.id.b2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, Main8Activity.class);
                //不传输数据回来
                startActivity(in);
            }
        });


    }


    //获取新页面返回的数据方法以及数据的处理
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    String str = data.getStringExtra("data2");
//                    Log.d("MainActivity",str);
                    //
                    Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
//                    Toast.makeText(Main2Activity, str, Toast.LENGTH_SHORT).show();

                    //实时改变页面的标签值
                    TextView t = findViewById(R.id.fanhuizhi);
                    t.setText(str);

                    //图片则用，具体需要使用的时候自己更改
                    ImageView im = findViewById(R.id.photo2);
                    im.setImageDrawable(getResources().getDrawable(R.drawable.img_1));
                    String path = Environment.getExternalStorageDirectory() + File.separator + "img_1.png";
                    Bitmap b = BitmapFactory.decodeFile(path);
                    im.setImageBitmap(b);
                    im.setImageResource(R.drawable.img_1);

                }
                break;
            default:
        }
    }


}


//
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.navigation, menu);
//        return true;
//
//    }




