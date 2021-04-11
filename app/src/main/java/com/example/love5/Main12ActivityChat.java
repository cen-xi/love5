package com.example.love5;

import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


public class Main12ActivityChat extends AppCompatActivity {
    EditText e1;
    Button b1;
    LinearLayout chatview ,bianjikuang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main12_chat);
        //发送按钮
        b1 = findViewById(R.id.b1);
        e1 = findViewById(R.id.e1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(e1.getText())) {
                    //不空
                    //发送信息
                    sentmessage(e1.getText().toString());

                    //显示接收到的信息
//                    seeMessage(e1.getText().toString());
                }
            }
        });
        //
        e1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScrollView scrollView = findViewById(R.id.s1);
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });

        //碰到编辑框，消息滑到底
        bianjikuang = findViewById(R.id.bianjikuang);
        bianjikuang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScrollView scrollView = findViewById(R.id.s1);
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });


    }

    //
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        if(hasFocus){
//            ScrollView scrollView = findViewById(R.id.s1);
//            scrollView.fullScroll(ScrollView.FOCUS_DOWN);
//        }
//    }


    //发送消息
    public void sentmessage(String message)  {
        //获取屏幕的默认分辨率
        Display display = getWindowManager().getDefaultDisplay();
//        heigth = display.getHeight();
        int  width = display.getWidth();

        //最外层聊天框
        chatview = findViewById(R.id.chatview);

//       // 第一层
        LinearLayout l1 = new LinearLayout(this);
        //线性水平布局android:orientation="horizontal"
        l1.setOrientation(LinearLayout.HORIZONTAL);
        //l1的具体子布局
//        // 相当于android:layout_width="wrap_content"//
//        //      android:layout_height="wrap_content"//
        chatview.addView(l1);
        LinearLayout.LayoutParams l1LayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        l1LayoutParams.setMargins(0,10,0,0);
        l1.setLayoutParams(l1LayoutParams);

        // 第2层第一个
        LinearLayout l2 = new LinearLayout(this);
        l1.addView(l2);
        LinearLayout.LayoutParams l2LayoutParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
        l2LayoutParams.weight = 6;
        l2.setLayoutParams(l2LayoutParams);


        // 第2层第2个
        LinearLayout l3 = new LinearLayout(this);
        l3.setOrientation(LinearLayout.HORIZONTAL);
        l3.setGravity(Gravity.RIGHT);
        l1.addView(l3);
        LinearLayout.LayoutParams l3LayoutParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
        l3LayoutParams.gravity = Gravity.RIGHT;
        l3LayoutParams.weight = 25;
        l3.setLayoutParams(l3LayoutParams);

        // 第2层第3个
        LinearLayout l4 = new LinearLayout(this);
        l1.addView(l4);
        LinearLayout.LayoutParams l4LayoutParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
        l4LayoutParams.weight = 1;
        l4.setLayoutParams(l4LayoutParams);


        //第3层第一个
        LinearLayout l31 = new LinearLayout(this);
        //引用背景图片,相当于 android:src="@drawable/xiaren"
        Resources resources = Main12ActivityChat.this.getResources();
        Drawable drawable = resources.getDrawable(R.drawable.chat3);
        l31.setBackground(drawable);
        l3.addView(l31);
        LinearLayout.LayoutParams l31LayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        l31LayoutParams.setMargins(width/7,0,0,0);
        l31.setLayoutParams(l31LayoutParams);


        TextView textView = new TextView(this);
        textView.setText(message);
        textView.setTextSize(22);
        l31.addView(textView);
        LinearLayout.LayoutParams textLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        textLayoutParams.gravity = Gravity.CENTER;
        textView.setLayoutParams(textLayoutParams);


        //第3层第2个
        ImageView imageView = new ImageView(this);
        //图片view有单独的方法，直接调用就可以
        imageView.setImageResource(R.drawable.xiaren);
        l3.addView(imageView);
        //参数将近dp的4倍
        LinearLayout.LayoutParams imagelayoutParams = new LinearLayout.LayoutParams(width/8, width/8);
        imagelayoutParams.gravity = Gravity.TOP;
        imagelayoutParams.setMargins(10,0,0,0);
        imageView.setLayoutParams(imagelayoutParams);

        //然后把编辑框e1清空
        e1.setText("");
        //自动滚动到低
        ScrollView scrollView = findViewById(R.id.s1);
        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
    }

    //显示接收到的消息
    public  void seeMessage(String message){
        //获取手机分辨率
        Display display = getWindowManager().getDefaultDisplay();
        int width = display.getWidth();

        //最外层聊天框
        chatview = findViewById(R.id.chatview);

        //第一层view
        LinearLayout l1 = new LinearLayout(this);
        l1.setOrientation(LinearLayout.HORIZONTAL);


        chatview.addView(l1);
        LinearLayout.LayoutParams l1layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        l1layoutParams.gravity = Gravity.LEFT;
        l1layoutParams.setMargins(width/224,10,width/32,0);
        l1.setLayoutParams(l1layoutParams);

       //图片
        ImageView imageView = new ImageView(this);
        //图片view有单独的方法，直接调用就可以
        imageView.setImageResource(R.drawable.coffee);
        l1.addView(imageView);
        //参数将近dp的4倍
        LinearLayout.LayoutParams imagelayoutParams = new LinearLayout.LayoutParams(width/8, width/8);
        imagelayoutParams.gravity = Gravity.TOP;
        imagelayoutParams.setMargins(0,0,10,0);
        imageView.setLayoutParams(imagelayoutParams);

        //第二层view
        LinearLayout l2 = new LinearLayout(this);
        //引用背景图片,相当于 android:src="@drawable/xiaren"
        Resources resources = Main12ActivityChat.this.getResources();
        Drawable drawable = resources.getDrawable(R.drawable.chat3);
        l2.setBackground(drawable);
        l1.addView(l2);
        LinearLayout.LayoutParams l2layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        l2.setLayoutParams(l2layoutParams);

        //textview
        TextView textView = new TextView(this);
        textView.setText(message);
        textView.setTextSize(22);
        l2.addView(textView);
        LinearLayout.LayoutParams textlayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        textlayoutParams.gravity = Gravity.TOP;
        textView.setLayoutParams(textlayoutParams);

        //自动滚动到低，看不到最后一个是因为查询的速度比生成控件的速度快，所以查询为没有这个控件，查询完毕才生成，所以达不到想要的效果
        ScrollView scrollView = findViewById(R.id.s1);
        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
    }

}
//
///*************************************************心得：****************************************
//判断id为zuiwai的LinearLayout是否显示在屏幕
//        Rect scrollBounds = new Rect();
//        scrollView.getHitRect(scrollBounds);
//        LinearLayout zuiwai = findViewById(R.id.zuiwai);
//        if (zuiwai.getLocalVisibleRect(scrollBounds)) {
//            //子控件至少有一个像素在可视范围内
//            Toast.makeText(getApplicationContext(),"有",Toast.LENGTH_SHORT).show();
//        } else {
//            //子控件完全不在可视范围内
//            // NONE of the childView is within the visible window
//            Toast.makeText(getApplicationContext(),"没有啊",Toast.LENGTH_SHORT).show();
//        }
//获取scrollView的内高度
//        int cc = scrollView.getChildAt(0).getHeight();
//smoothScrollTo方法是去到参数里的位置，两个参数分别是第四象限的x,y值坐标位置，最顶上的左上角为0,0坐标
// scrollView.smoothScrollTo(0,cc+250);
//************************************************************************************************

//    String fileName=".SHP";
//fileName.equals(".shp")为false;
//        fileName.equalsIgnoreCase(".shp")为TRUE;
//        equalsIgnoreCase与equals区别在于前者不区分大小写
//
//
///**************************************************













///*
//
///**
// * 使用代码进行登录界面的布局
// * @author wangke
// */
//public class CodeLayoutActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        //创建一个最外层的根布局
//        LinearLayout rootLayout = new LinearLayout(this);
//        rootLayout.setBackgroundColor(Color.CYAN);
//
//        //设置线性布局中子View的摆放为竖直方向
//        rootLayout.setOrientation(LinearLayout.VERTICAL);
//        //创建一个根部局的LayoutParams
//        LinearLayout.LayoutParams rootParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        //设置根部局的Margin值
//        rootParams.setMargins(DensityUtil.dip2px(this,5),DensityUtil.dip2px(this,5),DensityUtil.dip2px(this,5),DensityUtil.dip2px(this,5));
//        rootParams.gravity = Gravity.CENTER_HORIZONTAL;
//        rootLayout.setLayoutParams(rootParams);
//        //设置最外层根部局布局的背景颜色
//        rootLayout.setBackgroundColor(Color.CYAN);
//        //将当前创建的根部局设置给Activity
//        setContentView(rootLayout);
//
//
//        //创建一个显示用户头像的ImageView
//        ImageView ivIco = new ImageView(this);
//        ivIco.setImageResource(R.drawable.ico);
//        //将创建的用于显示头像的ImageView添加到最外层的线性布局中
//        rootLayout.addView(ivIco);
//        //当通过子View进行获取所在的布局的时候，rootLayout.addView(ivIco)需要写在前面，原因想起来也很清楚,当前的ImageView都还没有添加到父View如果直接通过getLayoutParams()当然会出先空指针异常(可以查看源码)
//        LinearLayout.LayoutParams ivParams = (LinearLayout.LayoutParams) ivIco.getLayoutParams();
//        //给用于显示头像的ImageView设置宽高
//        ivParams.width = DensityUtil.dip2px(this,100f);
//        ivParams.height = DensityUtil.dip2px(this,100f);
//        //设置居中的方式(子View在父View下的摆放方式为水平居中)
//        ivParams.gravity = Gravity.CENTER_HORIZONTAL;
//        ivParams.setMargins(0,DensityUtil.dip2px(this,100),0,0);
//        ivIco.setLayoutParams(ivParams);
//
//
//
//        //创建水平排列的线性布局,用于放置账户相关的View
//        LinearLayout userCountLayout = new LinearLayout(this);
//        userCountLayout.setOrientation(LinearLayout.HORIZONTAL);
//        //将当前的创建的线性布局添加到根View
//        rootLayout.addView(userCountLayout);
//
//
//        //显示用户名的TextView
//        TextView tvShowUser = new TextView(this);
//        tvShowUser.setText("用户名:");
//        userCountLayout.addView(tvShowUser);
//        LinearLayout.LayoutParams userCountlayoutParams = (LinearLayout.LayoutParams) tvShowUser.getLayoutParams();
//        userCountlayoutParams.setMargins(DensityUtil.dip2px(this,20),DensityUtil.dip2px(this,20),DensityUtil.dip2px(this,20),DensityUtil.dip2px(this,20));
//
//
//        //创建用于填写用户账户的EditText
//        EditText edtUserCount = new EditText(this);
//        userCountLayout.addView(edtUserCount);
//        edtUserCount.setHint("请输入用户名");
//        LinearLayout.LayoutParams edtUserCountlayoutParams = (LinearLayout.LayoutParams) edtUserCount.getLayoutParams();
//        edtUserCount.setWidth(0);
//        edtUserCountlayoutParams.weight = 1;
//        //父View的Gravity属性,确定子View的摆放规则
//        edtUserCountlayoutParams.gravity = Gravity.CENTER_VERTICAL;
//        //View.setGravity方法相当于  android:gravity="center",该属性表示对View中内容进行的限定
////        edtUserCount.setGravity(Gravity.CENTER);
//
//
//        //创建线性布局用于容纳密码相关的View
//        LinearLayout pwdLayout =  new LinearLayout(this);
//        pwdLayout.setGravity(LinearLayout.HORIZONTAL);
//        LinearLayout.LayoutParams pwdLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
//        pwdLayout.setLayoutParams(pwdLayoutParams);
//        rootLayout.addView(pwdLayout);
//
//        //TextView显示密码文本
//        TextView tvShowPwd = new TextView(this);
//        tvShowPwd.setText("密    码:");
//        pwdLayout.addView(tvShowPwd);
//        LinearLayout.LayoutParams tvShowPwdParams = (LinearLayout.LayoutParams) tvShowPwd.getLayoutParams();
//        tvShowPwdParams.setMargins(DensityUtil.dip2px(this,20),DensityUtil.dip2px(this,20),DensityUtil.dip2px(this,20),DensityUtil.dip2px(this,20));
//        tvShowPwdParams.gravity = Gravity.CENTER_VERTICAL;
//
//        //EdtText用于输入密码
//        EditText edtPwd = new EditText(this);
//        edtPwd.setHint("请输入密码");
//        pwdLayout.addView(edtPwd);
//        //使用new的方式来获取 LayoutParams,两个参数即为宽和高的布局，这样简单
//        LinearLayout.LayoutParams edtPwdParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        //设置权重
//        edtPwdParams.weight = 1;
//        edtPwdParams.width = 0;
//        //将布局参数设置给EdtText
//        edtPwd.setLayoutParams(edtPwdParams);
//
//        //用于进行登录的button
//        Button btnLogin = new Button(this);
//        btnLogin.setText("登陆");
//        rootLayout.addView(btnLogin);
//        LinearLayout.LayoutParams btnLoginParams = (LinearLayout.LayoutParams) btnLogin.getLayoutParams();
//        //设置Button的大小
//        btnLoginParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
//        btnLoginParams.height= LinearLayout.LayoutParams.WRAP_CONTENT;
//        btnLoginParams.gravity = Gravity.CENTER_HORIZONTAL;
//
//
//    }
//}







