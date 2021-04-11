package com.example.love5;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
    //定位管理器
    public static LocationManager lm;

    //如果使用监听则需要加入MissingPermission
    @SuppressLint({"SetJavaScriptEnabled", "MissingPermission"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
//        mContext = getApplicationContext();
        //   隐藏系统自带的顶部标题栏，导入新的标题栏，详细看书本110页
        ActionBar act = getSupportActionBar();
        act.hide();


//
//        //获取摄像头权限值
//        int checkCallPhonePermission = ContextCompat.checkSelfPermission(Main4Activity.this, Manifest.permission.CAMERA);
////               // 判断权限值是否相等，不等则表示未授权
//        if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
//
//
//


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


        //定位  定位管理器  获得定位管理器后可以监听位置的变化
        lm = (LocationManager) getSystemService(LOCATION_SERVICE);

//
//        //定位方式【wifi ，3G/4G  ，gps】
//        String provider = "gps";
//        // 定位的时间差【单位毫秒】
//        long minTime = 10;
//        //定位的距离差【单位米】
//        float MinDistance = 10;
//        //定位监听回调结果
//        //回调监听
//        LocationListener listener = new LocationListener() {
//            @Override
//            public void onLocationChanged(Location location) {
//                float accuracy = location.getAccuracy();//精确度，m为单位
//                double altitude = location.getAltitude();//海拔高度
//                double longitude = location.getLongitude();//获取经度
//                double latitude = location.getLatitude();//获取纬度
//                float speed = location.getSpeed();//获取速度
//                System.out.println(location.toString());
//                System.out.println(longitude + "," + latitude);
//
//            }
//
//            @Override
//            public void onStatusChanged(String provider, int status, Bundle extras) {
//
//            }
//
//            @Override
//            public void onProviderEnabled(String provider) {
//
//            }
//
//            @Override
//            public void onProviderDisabled(String provider) {
//
//            }
//
//        };
//        LocationManager lm = Main4Activity.lm;
//        lm.requestLocationUpdates(provider, minTime, MinDistance, listener);

    }

}
