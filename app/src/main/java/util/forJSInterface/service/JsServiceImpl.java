package util.forJSInterface.service;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.webkit.WebView;

import com.example.love5.Main4Activity;
import com.example.love5.MainCamera;

import util.forJSInterface.InterfaceForJS;
import util.forJSInterface.JsData;

import static android.content.Context.LOCATION_SERVICE;
import static android.support.v4.content.ContextCompat.getSystemService;

public class JsServiceImpl implements JsService {


    //app 调用 页面的 js 方法
    private void jsCallbackMethod(final String method, final String params) {

        //拼接js
        final String js = "javascript:" + method + "('" + params + "')";
        //获取容器对象
        final WebView w = Main4Activity.w;
        //调用js方法
        w.post(new Runnable() {
            @Override
            public void run() {
                //执行
                w.loadUrl(js);
            }
        });

    }

    /**
     * 获取gps经纬数据
     */
    //如果使用监听则需要加入MissingPermission
    @SuppressLint("MissingPermission")
    @Override
    public String getGPSData(final JsData d) {


        //定位方式【wifi ，3G/4G  ，gps】
        String provider = "gps";
        // 定位的时间差【单位毫秒】
        long minTime = 10;
        //定位的距离差【单位米】
        float MinDistance = 10;
        //定位监听回调结果
        //回调监听
        LocationListener listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                float accuracy = location.getAccuracy();//精确度，m为单位
                double altitude = location.getAltitude();//海拔高度
                double longitude = location.getLongitude();//获取经度
                double latitude = location.getLatitude();//获取纬度
                float speed = location.getSpeed();//获取速度
                System.out.println(location.toString());
                System.out.println(longitude + "," + latitude);
                if (d.getCallbackMethodForApp() != null) {
                    jsCallbackMethod(d.getCallbackMethodForApp(), longitude + "," + latitude);
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }

        };
        LocationManager lm = Main4Activity.lm;
        lm.requestLocationUpdates(provider, minTime, MinDistance, listener);
        return "";
    }


}
