package util.forJSInterface.service;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.webkit.WebView;

import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.love5.Main4Activity;
import com.example.love5.MainCamera;

import java.text.ParseException;
import java.util.Date;

import util.forJSInterface.InterfaceForJS;
import util.forJSInterface.JsData;

import static android.content.Context.LOCATION_SERVICE;
import static android.support.v4.content.ContextCompat.getSystemService;

public class JsServiceImpl implements JsService {


    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


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
        //开始时间
        final Date yd = new Date();

//        int sc = Integer.parseInt(d.getData());


        //刷新时间有变化时，则刷新配置文件信息
//        if (Main4Activity.scanSpanNum != sc){
//            if (Main4Activity.mLocationClient.isStarted()){
//                //关闭刷新
//                Main4Activity.mLocationClient.stop();
//            }
//            Main4Activity.scanSpanNum = sc;
//            Main4Activity.mLocationClient.unRegisterLocationListener(Main4Activity.myListener);
//            LocationClientOption locOption = Main4Activity.mLocationClient.getLocOption();
//            locOption.setScanSpan(sc);
//            Main4Activity.mLocationClient.setLocOption(locOption);
//            Main4Activity.mLocationClient.registerLocationListener(Main4Activity.myListener);
//        }
        //开启线程
        new Thread() {
            @Override
            public void run() {
                super.run();
                BDLocation location = null;
                //线程循环,200毫秒休眠一次,最多等待10秒
                for (int i = 0; i < 50; i++) {
                    if (!Main4Activity.mLocationClient.isStarted()) {
                        //如果未开启,则开启
                        Main4Activity.mLocationClient.start();
                    }
                    if (Main4Activity.locationData != null
                            && Main4Activity.locationData.getTime() != null
                    ) {
                        String m = Main4Activity.locationData.getTime();
                        try {
                            format.setLenient(false); // 禁止宽松格式
                            Date mc = format.parse(Main4Activity.locationData.getTime());
                            if (!mc.before(yd)) {
                                location = Main4Activity.locationData;
                                break;
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                    //必须开线程才行
                    try {
                        Thread.sleep(200);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (Main4Activity.mLocationClient.isStarted()) {
                    //如果开启,则关闭
                    Main4Activity.mLocationClient.stop();
                }
//                String params = "";
//                if (location != null) {
//                    params = location.getTime() + "==" + location.getLatitude() + "," + location.getLongitude() + "==" +
//                            location.getCountry() + location.getCity() + location.getDistrict() + location.getStreet() + location.getAddrStr();
//                }
                //封装返回结果
                jsCallbackMethod(d.getCallbackMethodForApp(), setLocationResult(location));
            }
        }.start();


//        if ()

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
//                if (d.getCallbackMethodForApp() != null) {
//                    jsCallbackMethod(d.getCallbackMethodForApp(), longitude + "," + latitude);
//                }
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
//        if (location == null) {
//            return "";
//        }
//        //结果拼接
//        return location.getTime() + "==" + location.getLatitude() + "," + location.getLongitude() + "==" +
//                location.getCountry() + location.getCity() + location.getDistrict() + location.getStreet() + location.getAddrStr();
        return "";
    }

    //封装返回结果
    private String setLocationResult(BDLocation location) {
        if (location == null) {
            return "";
        }
        //                    //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
////                    //以下只列举部分获取经纬度相关（常用）的结果信息
////                    //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

        StringBuffer sb = new StringBuffer(256);
        sb.append("time : ");
        /**
         * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
         * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
         */
        sb.append(location.getTime());
        sb.append("\nlocType : ");// 定位类型
        sb.append(location.getLocType());
        sb.append("\nCoorType : ");// 经纬度坐标类型
        sb.append(location.getCoorType());
        sb.append("\nlocType description : ");// *****对应的定位类型说明*****
        sb.append(location.getLocTypeDescription());
        sb.append("\nlatitude : ");// 纬度
        sb.append(location.getLatitude());
        sb.append("\nlontitude : ");// 经度
        sb.append(location.getLongitude());
        sb.append("\nradius : ");// 半径，定位精度，默认值为0.0f
        sb.append(location.getRadius());
        sb.append("\nCountryCode : ");// 国家码
        sb.append(location.getCountryCode());
        sb.append("\nCountry : ");// 国家名称
        sb.append(location.getCountry());
        sb.append("\ncitycode : ");// 城市编码
        sb.append(location.getCityCode());
        sb.append("\ncity : ");// 城市
        sb.append(location.getCity());
        sb.append("\nDistrict : ");// 区
        sb.append(location.getDistrict());
        sb.append("\nStreet : ");// 街道
        sb.append(location.getStreet());
        sb.append("\naddr : ");// 地址信息
        sb.append(location.getAddrStr());
        sb.append("\nUserIndoorState: ");// *****返回用户室内外判断结果*****
        sb.append(location.getUserIndoorState());
        sb.append("\nDirection(not all devices have value): ");
        sb.append(location.getDirection());// 方向
        sb.append("\nlocationdescribe: ");
        sb.append(location.getLocationDescribe());// 位置语义化信息
        sb.append("\nPoi: ");// POI信息
        return sb.toString();
    }


}
