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
                return false;
            }

        });
        //绑定暴露与js的接口
        w.addJavascriptInterface(new InterfaceForJS(), "InterfaceForJS");


//        //webView必须要有下面4行才可以访问网页
//        final WebView w = (WebView) findViewById(R.id.web);
//        w.getSettings().setJavaScriptEnabled(true);
//        w.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//
//                try {
//                    if (url.lastIndexOf("://") > -1) {
//                        //存在
//                        String[] strArr = url.split("://");
//                        if (strArr[0].length() > 0 && !strArr[0].equalsIgnoreCase("http")
//                                && !strArr[0].equalsIgnoreCase("https")) {
//                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                            startActivity(intent);
//                            return true;
//                        }
//                    }
//                } catch (Exception e) {
//                    return false;
//                }
//                w.loadUrl(url);
//                return true;
//            }
//        });
////        w.loadUrl("https://www.baidu.com");
//        w.loadUrl("http://192.168.153.1:55/html/app/login.html");


//        //webView必须要有下面4行才可以访问网页
//        WebView w=(WebView) findViewById(R.id.web);
//        w.getSettings().setJavaScriptEnabled(true);
//
//
//        w.setWebViewClient(new WebViewClient());
//        w.loadUrl("http://192.168.153.1:55/html/app/login.html");


    }

}
