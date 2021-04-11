package com.example.love5;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import util.forJSInterface.InterfaceForJS;

public class Main4Activity extends AppCompatActivity {

//    private static Context mContext;
//
//    public static Context getContext(){
//        return mContext;
//    }

    public static WebView w;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
//        mContext = getApplicationContext();
        //   隐藏系统自带的顶部标题栏，导入新的标题栏，详细看书本110页
        ActionBar act = getSupportActionBar();
        act.hide();


        w = (WebView) findViewById(R.id.web);
        //启用js
        w.getSettings().setJavaScriptEnabled(true);
        //启用dom
        w.getSettings().setDomStorageEnabled(true);
        //允许弹窗
        w.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        //加载前端的网址
//        w.loadUrl("http://192.168.0.103:55/html/app/login.html");
//        w.loadUrl("https://www.baidu.com");
        w.loadUrl("http://192.168.0.103:55/html/app/taskRoom/taskList.html");
        //使用chrome 浏览器客户端
        w.setWebChromeClient(new WebChromeClient());
        w.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //禁用非http 和 https 的超链接
                if (url.contains("://") && !url.contains("http://") && !url.contains("https://")) {
                    //不存在时,禁用跳转操作
                    return true;
                }
                w.loadUrl(url);
                return true;
            }

        });
        //绑定暴露给js的接口
        w.addJavascriptInterface(new InterfaceForJS(), "InterfaceForJS");


    }

}
