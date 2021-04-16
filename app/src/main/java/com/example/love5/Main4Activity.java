package com.example.love5;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.ArrayMap;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.view.CropImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import lombok.SneakyThrows;
//import util.forJSInterface.Camera.PicassoImageLoader;
import util.forJSInterface.Camera.serviceImpl.CameraServiceImpl;
import util.forJSInterface.InterfaceForJS;

public class Main4Activity extends AppCompatActivity {

    public static Context context;

    //快捷键输入 logt 然后回车
    private static final String TAG = "Main4Activity";


//    //百度sdk客户端
//    public LocationClient mLocationClient = null;
//    private MyLocationListener myListener = new MyLocationListener();

    //百度定位数据
    public static BDLocation locationData = null;
    //最后一次百度定位时间-废弃 ,  BDLocation 里面有时间 ,且不是每次都会成功的
//    public static Date curLocationTime = null;
    //百度sdk客户端
    public static LocationClient mLocationClient = null;
    //开启间隔 n毫秒 刷新gps数据,如果是0则仅刷新一次就关闭,默认是0 【如果不是0必须大于1000才生效】
    public static int scanSpanNum = 1000;
    //监听
    public static BDAbstractLocationListener myListener = null;
    //定位权限认证标识
    public static final int TAKE_LOCATION = 1;
    //相机权限认证标识
    public static final int TAKE_CAMERA = 2;
    //录像机权限认证标识
    public static final int TAKE_VIDEO_CAMERA = 3;
    //相册图片权限认证标识
    public static final int TAKE_ALBUM_IMG = 4;
    //相册视频权限认证标识
    public static final int TAKE_ALBUM_VIDEO = 5;

    //浏览器组件对象
    public static WebView w;
    //定位管理器
    public static LocationManager lm;


    //如果使用监听则需要加入MissingPermission  【 , "MissingPermission"】
    @SuppressLint({"SetJavaScriptEnabled"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
//        mContext = getApplicationContext();
        //   隐藏系统自带的顶部标题栏，导入新的标题栏，详细看书本110页
        ActionBar act = getSupportActionBar();
        act.hide();

        context = Main4Activity.this;
        w = (WebView) findViewById(R.id.web);
        //获取定位管理器
        lm = (LocationManager) getSystemService(LOCATION_SERVICE);
//
//        //获取摄像头权限值
//        int checkCallPhonePermission = ContextCompat.checkSelfPermission(Main4Activity.this, Manifest.permission.CAMERA);
////               // 判断权限值是否相等，不等则表示未授权
//        if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
//
//
//


        //权限校验
        if (checkPower()) {
            //配置webview
            setWebViewInfo();
        }


//        try {


//            //百度sdk客户端，//声明LocationClient类
//            mLocationClient = new LocationClient(getApplicationContext());
//
//            //配置定位信息
//            LocationClientOption option = new LocationClientOption();
//            //可选，设置定位模式，默认高精度
//            //LocationMode.Hight_Accuracy：高精度；
//            //LocationMode. Battery_Saving：低功耗；
//            //LocationMode. Device_Sensors：仅使用设备；
//            option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
//            //可选，设置返回经纬度坐标类型，默认GCJ02
//            //GCJ02：国测局坐标；
//            //BD09ll：百度经纬度坐标；
//            //BD09：百度墨卡托坐标；
//            //海外地区定位，无需设置坐标类型，统一返回WGS84类型坐标
//            option.setCoorType("bd09ll");
//
//            //可选，设置发起定位请求的间隔，int类型，单位ms
//            //如果设置为0，则代表单次定位，即仅定位一次，默认为0
//            //如果设置非0，需设置1000ms以上才有效
//            option.setScanSpan(scanSpanNum);
//            //可选，设置是否使用gps，默认false
//            //使用高精度和仅用设备两种定位模式的，参数必须设置为true
//            option.setOpenGps(true);
//            //可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false
//            //可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false
////            option.setLocationNotify(true);
//
//            //可选，定位SDK内部是一个service，并放到了独立进程。
//            //设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)
//            option.setIgnoreKillProcess(false);
//
//            //可选，设置是否收集Crash信息，默认收集，即参数为false
//            option.SetIgnoreCacheException(false);
//            //可选，V7.2版本新增能力
//            //如果设置了该接口，首次启动定位时，会先判断当前Wi-Fi是否超出有效期，
//            // 若超出有效期，会先重新扫描Wi-Fi，然后定位
//            option.setWifiCacheTimeOut(5 * 60 * 1000);
//            //可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false
//            option.setEnableSimulateGps(false);
//            //可选，设置是否需要最新版本的地址信息。默认需要，即参数为true
//            option.setNeedNewVersionRgc(true);
//            //是否需要地址信息,默认不需要
//            option.setIsNeedAddress(true);
//
//
//            //mLocationClient为第二步初始化过的LocationClient对象
//            //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
//            //更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明
//            mLocationClient.setLocOption(option);
//            //
//            //配置监听
//            myListener = new BDAbstractLocationListener() {
//
//                @Override
//                public void onReceiveLocation(BDLocation location) {
////                    //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
//////                    //以下只列举部分获取经纬度相关（常用）的结果信息
//////                    //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
//////
//                    double latitude = location.getLatitude();    //获取纬度信息
//////                    double longitude = location.getLongitude();    //获取经度信息
//////                    float radius = location.getRadius();    //获取定位精度，默认值为0.0f
//////
//////                    String coorType = location.getCoorType();
//////                    //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准
//////
//////                    int errorCode = location.getLocType();
//////                    //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
//////                    Log.d(TAG,"经纬===爱是感动户籍卡个时间大噶山豆根==="+longitude+","+latitude);
//
//                    String t = location.getTime();
//                    Log.d(TAG, "纬度=" + location.getLatitude() + "");
//                    if (t == null || location.getLatitude() == 4.9E-324) {
////                        //失败
////                         if (Main4Activity.scanSpanNum==0){
////                             //再执行一次
////                             mLocationClient.start();
////                         }
//                        return;
//                    }
//                    Main4Activity.locationData = location;

//                    //刷新时间有变化时，则刷新配置文件信息
//                    if (mLocationClient.getLocOption().getScanSpan() != scanSpanNum){
//                        mLocationClient.getLocOption().setScanSpan(scanSpanNum);
//                        if (scanSpanNum==0){
//                            //关闭刷新
//                            mLocationClient.stop();
//                        }else{
//
//                        }
//
//                    }
//                    final String js = "javascript:" + method + "('" + params + "')";
//                    final String js = "javascript:mtext('" + location.getTime()+"=="+location.getLatitude()+","+location.getLongitude() + "=="+
//                            location.getCountry() + location.getCity() + location.getDistrict()+ location.getStreet()+ location.getAddrStr()+"')";
//                    //获取容器对象
//                    final WebView w = Main4Activity.w;
//                    //调用js方法
//                    w.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            //执行
//                            w.loadUrl(js);
//                        }
//                    });
//                }
//            };
//            //百度sdk里注册监听函数
//            mLocationClient.registerLocationListener(myListener);
        //获取一次gps数据
//            mLocationClient.start();


//            Log.i("Main4Activity","准备定位");
//            //定位方式【wifi ，3G/4G  ，gps】
//            String provider = "gps";
//            // 定位的时间差【单位毫秒】
//            long minTime = 10;
//            //定位的距离差【单位米】
//            float MinDistance = 10;
//            //定位监听回调结果
//            //回调监听
//            LocationListener listener = new LocationListener() {
//                @Override
//                public void onLocationChanged(Location location) {
//                    Log.i("Main4Activity", "GPS数据变化了");
//                    float accuracy = location.getAccuracy();//精确度，m为单位
//                    double altitude = location.getAltitude();//海拔高度
//                    double longitude = location.getLongitude();//获取经度
//                    double latitude = location.getLatitude();//获取纬度
//                    float speed = location.getSpeed();//获取速度
//                    System.out.println(location.toString());
//                    System.out.println(longitude + "," + latitude);
//
//                }
//
//                //GPS状态变化时触发
//                @Override
//                public void onStatusChanged(String provider, int status, Bundle extras) {
//                    Log.i("Main4Activity", "GPS状态变化时触发");
//                }
//
//                //GPS开启时触发
//                @Override
//                public void onProviderEnabled(String provider) {
//                    Log.i("Main4Activity", "GPS开启时触发");
//                }
//
//                //GPS禁用时触发
//                @Override
//                public void onProviderDisabled(String provider) {
//                    Log.i("Main4Activity", "GPS禁用时触发");
//                }
//
//            };
//            LocationManager lm = Main4Activity.lm;
//            lm.requestLocationUpdates(provider, minTime, MinDistance, listener);
//        } catch (Exception e) {
//            Log.i("Main4Activity", e.getMessage());
//            e.printStackTrace();
//            System.out.println(e.getMessage());
//        }


//        //图片选择器配置
//        ImagePicker imagePicker = ImagePicker.getInstance();
//        imagePicker.setImageLoader(new PicassoImageLoader());   //设置图片加载器
//        imagePicker.setShowCamera(true);  //显示拍照按钮
//        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
//        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
//        imagePicker.setSelectLimit(9);    //选中数量限制
//        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
//        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
//        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
//        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
//        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
    }

    //封装webview 配置
    @SuppressLint("SetJavaScriptEnabled")
    private void setWebViewInfo() {
        //启用js
        w.getSettings().setJavaScriptEnabled(true);
        //启用dom
        w.getSettings().setDomStorageEnabled(true);
        //允许弹窗
        w.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        w.getSettings().setDefaultTextEncodingName("utf-8");
        //加载前端的网址
//        w.loadUrl("http://192.168.0.103:55/html/app/login.html");
//        w.loadUrl("https://www.baidu.com");
//        w.loadUrl("http://192.168.0.103:55/html/app/taskRoom/taskList.html");
        w.loadUrl("http://172.168.1.116:55/html/app/taskRoom/taskList.html");
        //使用chrome 浏览器客户端
        w.setWebChromeClient(new WebChromeClient());


        w.setWebViewClient(new WebViewClient() {


            @SneakyThrows
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

            //允许的文件类型
            private final String[] suffixsImg = {"png", "mp4", "jpg", "jpeg"};
            private final String[] suffixsvideo = {"mp4"};

            //处理虚拟请求
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {

                //获取请求路径
                String url = request.getUrl().toString();
                //单独处理不同路径的
                if (url.contains(CameraServiceImpl.androidfile)) {
                    //获取本地文件
                    Log.d(TAG, "虚拟请求" + url);
                    //获取后缀名
                    String[] strarr = url.split("\\.");
                    if (strarr.length > 1) {
                        String suffix = strarr[strarr.length - 1];
                        //是否是允许的类型
                        boolean isok = false;
                        String type = "";
                        for (String s : suffixsImg) {
                            if (s.equals(suffix.toLowerCase())) {
                                isok = true;
                                type = "image/";
                                break;
                            }
                        }
                        if (!isok) {
                            for (String s : suffixsvideo) {
                                if (s.equals(suffix.toLowerCase())) {
                                    isok = true;
                                    type = "video/";
                                    break;
                                }
                            }
                        }
                        if (isok) {
                            //本地真实路径
                            String murl = url.replace(CameraServiceImpl.androidfile, "");
                            try {
                                FileInputStream inputStream = new FileInputStream(new File(murl.trim()));
                                WebResourceResponse response = new WebResourceResponse(type + suffix, "UTF-8", inputStream);
                                //设置响应头
                                Map<String, String> map = new HashMap<>();
                                //允许跨域
                                map.put("Access-Control-Allow-Origin", "*");
                                response.setResponseHeaders(map);
                                //返回请求结果
                                return response;
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                return super.shouldInterceptRequest(view, request);
            }

        });
        //绑定暴露给js的接口
        w.addJavascriptInterface(new InterfaceForJS(), "InterfaceForJS");
    }

    //封装权限验证
    private boolean checkPower() {
        // 判断GPS是否正常启动
        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "请开启GPS导航...", Toast.LENGTH_SHORT).show();
            // 返回开启GPS导航设置界面
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(intent, 0);
            return false;
        }


        //动态权限请求
        int ACCESS_COARSE_LOCATION = ContextCompat.checkSelfPermission(Main4Activity.this, Manifest.permission.ACCESS_COARSE_LOCATION);
        int ACCESS_FINE_LOCATION = ContextCompat.checkSelfPermission(Main4Activity.this, Manifest.permission.ACCESS_FINE_LOCATION);
        int ACCESS_WIFI_STATE = ContextCompat.checkSelfPermission(Main4Activity.this, Manifest.permission.ACCESS_WIFI_STATE);
        int ACCESS_NETWORK_STATE = ContextCompat.checkSelfPermission(Main4Activity.this, Manifest.permission.ACCESS_NETWORK_STATE);
        int CHANGE_WIFI_STATE = ContextCompat.checkSelfPermission(Main4Activity.this, Manifest.permission.CHANGE_WIFI_STATE);
        if (ACCESS_COARSE_LOCATION != PackageManager.PERMISSION_GRANTED ||
                ACCESS_FINE_LOCATION != PackageManager.PERMISSION_GRANTED ||
                ACCESS_WIFI_STATE != PackageManager.PERMISSION_GRANTED ||
                ACCESS_NETWORK_STATE != PackageManager.PERMISSION_GRANTED ||
                CHANGE_WIFI_STATE != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(Main4Activity.this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_WIFI_STATE,
                            Manifest.permission.ACCESS_NETWORK_STATE,
                            Manifest.permission.CHANGE_WIFI_STATE,}, TAKE_LOCATION);
            return false;
        }
        setBaiduGPSLocationInfo();
        return true;
    }

//    private void openGPSSettings() {
//        LocationManager alm = (LocationManager) this
//                .getSystemService(Context.LOCATION_SERVICE);
//        if (alm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
//            Toast.makeText(this, "GPS模块正常", Toast.LENGTH_SHORT).show();
//            return;
//        } else {
//            Toast.makeText(this, "请开启GPS！", Toast.LENGTH_SHORT).show();
////            Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
////            startActivityForResult(intent, 0); // 此为设置完成后返回到获取界面
//        }
//
//    }

    //封装定位配置
    private void setBaiduGPSLocationInfo() {

        //百度sdk客户端，//声明LocationClient类
        mLocationClient = new LocationClient(getApplicationContext());

        //配置定位信息
        LocationClientOption option = new LocationClientOption();
        //可选，设置定位模式，默认高精度
        //LocationMode.Hight_Accuracy：高精度；
        //LocationMode. Battery_Saving：低功耗；
        //LocationMode. Device_Sensors：仅使用设备；
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，设置返回经纬度坐标类型，默认GCJ02
        //GCJ02：国测局坐标；
        //BD09ll：百度经纬度坐标；
        //BD09：百度墨卡托坐标；
        //海外地区定位，无需设置坐标类型，统一返回WGS84类型坐标
        option.setCoorType("bd09ll");

        //可选，设置发起定位请求的间隔，int类型，单位ms
        //如果设置为0，则代表单次定位，即仅定位一次，默认为0
        //如果设置非0，需设置1000ms以上才有效
        option.setScanSpan(scanSpanNum);
        //可选，设置是否使用gps，默认false
        //使用高精度和仅用设备两种定位模式的，参数必须设置为true
        option.setOpenGps(true);
        //可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false
        //可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false
//            option.setLocationNotify(true);

        //可选，定位SDK内部是一个service，并放到了独立进程。
        //设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)
        option.setIgnoreKillProcess(false);

        //可选，设置是否收集Crash信息，默认收集，即参数为false
        option.SetIgnoreCacheException(false);
        //可选，V7.2版本新增能力
        //如果设置了该接口，首次启动定位时，会先判断当前Wi-Fi是否超出有效期，
        // 若超出有效期，会先重新扫描Wi-Fi，然后定位
        option.setWifiCacheTimeOut(5 * 60 * 1000);
        //可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false
        option.setEnableSimulateGps(false);
        //可选，设置是否需要最新版本的地址信息。默认需要，即参数为true
        option.setNeedNewVersionRgc(true);
        //是否需要地址信息,默认不需要
        option.setIsNeedAddress(true);
        //可选，设置是否需要地址描述
        option.setIsNeedLocationDescribe(true);
//可选，设置是否需要设备方向结果
        option.setNeedDeviceDirect(false);
//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setLocationNotify(false);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIsNeedLocationPoiList(true);
        //可选，默认false，设置定位时是否需要海拔信息，默认不需要，除基础定位版本都可用
        option.setIsNeedAltitude(false);


        //mLocationClient为第二步初始化过的LocationClient对象
        //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        //更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明
        mLocationClient.setLocOption(option);
        //
        //配置监听
        myListener = new BDAbstractLocationListener() {

            @Override
            public void onReceiveLocation(BDLocation location) {
//                    //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
////                    //以下只列举部分获取经纬度相关（常用）的结果信息
////                    //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
////
                double latitude = location.getLatitude();    //获取纬度信息
////                    double longitude = location.getLongitude();    //获取经度信息
////                    float radius = location.getRadius();    //获取定位精度，默认值为0.0f
////
////                    String coorType = location.getCoorType();
////                    //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准
////
////                    int errorCode = location.getLocType();
////                    //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
////                    Log.d(TAG,"经纬===爱是感动户籍卡个时间大噶山豆根==="+longitude+","+latitude);

                String t = location.getTime();
                Log.d(TAG, "纬度=" + location.getLatitude() + "");
                if (t == null || location.getLatitude() == 4.9E-324) {
//                        //失败
//                         if (Main4Activity.scanSpanNum==0){
//                             //再执行一次
//                             mLocationClient.start();
//                         }
                    return;
                }
                Main4Activity.locationData = location;
            }
        };
        //客户端注册监听函数
        mLocationClient.registerLocationListener(myListener);
//                获取一次gps数据
//            mLocationClient.start();

    }

    //权限询问反馈，判断多个权限是否通过
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //requestCode为权限认证标识
        switch (requestCode) {
            case TAKE_LOCATION:
                for (int r : grantResults) {
                    if (r != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "没有定位权限", Toast.LENGTH_SHORT).show();
                        //退出webview
                        Intent in = new Intent(Main4Activity.this, Main3Activity.class);
                        //如果不需要传输下一页传输数据回来，则用下面这句指令
                        startActivity(in);
                        return;
                    }
                }
                //执行定位操作
                setBaiduGPSLocationInfo();
                //配置webview
                setWebViewInfo();
                break;

            case TAKE_CAMERA:
                for (int r : grantResults) {
                    if (r != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "没有摄像头权限", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                //开启相机
                CameraServiceImpl.openCamera();
                break;
            case TAKE_VIDEO_CAMERA:
                for (int r : grantResults) {
                    if (r != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "没有摄像头权限", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                //开启录像机
                CameraServiceImpl.openVideoCamera();
                break;
            case TAKE_ALBUM_IMG:
            case TAKE_ALBUM_VIDEO:
                //相册
                for (int r : grantResults) {
                    if (r != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "没有相册权限", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                //开启相册
                CameraServiceImpl.openAlbum(requestCode);
                break;

            default:
        }
    }

//    //使用图片选择器
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);图片选择器回调=99999999999999
//        Log.d(TAG,"图片选择器回调=99999999999999");
//        switch (requestCode) {
//            case ImagePicker.RESULT_CODE_ITEMS:
//                //图片
//                if (data != null) {
//                    String s = "不空";
//                } else {
//                    String s = "空";
//                }
//                ArrayList<ImageItem> d = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
//                //获取定义图片的路径
//                String path = d.get(0).path;
//                //回调js
//                if (CameraServiceImpl.jsData.getCallbackMethodForApp() != null) {
//                    InterfaceForJS.jsCallbackMethod(CameraServiceImpl.jsData.getCallbackMethodForApp(), CameraServiceImpl.androidfile + path);
//
//                }
//                break;
//            default:
//                break;
//        }
//
//    }
//


    //从新页面活动结束回来后，进行操作，requestCode就是识别从哪个活动回来的id,resultCode判断是否成功运行的参数
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult999999999999999999999999999: " + requestCode);
//        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_CAMERA:
                //相机
                if (resultCode == RESULT_OK) {
//                    Log.d(TAG, "onActivityResult相机: " + data);
//                    String uristr = data.getStringExtra(MediaStore.EXTRA_OUTPUT);
//                    Log.d(TAG, "onActivityResult相机: " + uristr);
                    try {
//                        //把拍摄的照片显示出来
//                        //由真实路径转化成Bitmap格式
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(CameraServiceImpl.tempFileUri));
//                        Log.d(TAG, "tempFileUri============: " + CameraServiceImpl.tempFileUri);
////                        Log.d(TAG, "tempFilePath============: " + CameraServiceImpl.file + CameraServiceImpl.tempFilePath);
////                        Log.d(TAG, "bitmap============: " + bitmap);
////                        //保存到本地相册
//                        saveToSystemGallery(Main4Activity.this, bitmap, Bitmap.CompressFormat.PNG);
//                        //方法2： 保存到本地相册
                        saveToSystemGallery(this, new File(CameraServiceImpl.filePath), CameraServiceImpl.tempFileUri);
//                        i1.setImageBitmap(bitmap);
//
//
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }


//                    file:///storage/emulated/0/Android/data/com.example.love5/2021-04-13/CameraFile/img_16182951053344.png
                    //保存到本地相册


                    //回调js
                    if (CameraServiceImpl.jsData.getCallbackMethodForApp() != null) {
//                        try {
//                        String s = CameraServiceImpl.androidfile + CameraServiceImpl.tempFilePath;
//                            URL url = new URL(CameraServiceImpl.tempFilePath);
//                            String k =CameraServiceImpl.androidfile + String.valueOf(url);
                        InterfaceForJS.jsCallbackMethod(CameraServiceImpl.jsData.getCallbackMethodForApp(), CameraServiceImpl.androidfile + CameraServiceImpl.tempFilePath);
//                        } catch (MalformedURLException e) {
//                            e.printStackTrace();
//                        }

                    }
                }
                //标识为关闭摄像头
                CameraServiceImpl.isOpenCamera = false;
                break;
            case TAKE_VIDEO_CAMERA:
                //录像机
                if (resultCode == RESULT_OK) {
//                    Log.d(TAG, "onActivityResult录像机: " + data);
//                    String uristr = data.getStringExtra(MediaStore.EXTRA_OUTPUT);
//                    Log.d(TAG, "onActivityResult录像机: " + uristr);
                    try {
                        //把拍摄的照片显示出来
                        //由真实路径转化成Bitmap格式
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(CameraServiceImpl.tempFileUri));
                        Log.d(TAG, "tempFileUri============: " + CameraServiceImpl.tempFileUri);
                        Log.d(TAG, "tempFilePath============: " + CameraServiceImpl.androidfile + CameraServiceImpl.tempFilePath);
                        Log.d(TAG, "bitmap============: " + bitmap);
                        //保存到本地相册
//                        saveToSystemGallery(Main4Activity.this, bitmap,Bitmap.CompressFormat.);
                        saveToSystemGallery2(this, new File(CameraServiceImpl.tempFilePath));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    //回调js
                    if (CameraServiceImpl.jsData.getCallbackMethodForApp() != null) {
//                        try {
//                            URL url = new URL(CameraServiceImpl.androidfile + CameraServiceImpl.tempFilePath);
                            InterfaceForJS.jsCallbackMethod(CameraServiceImpl.jsData.getCallbackMethodForApp(), CameraServiceImpl.androidfile + CameraServiceImpl.tempFilePath);
//                        } catch (MalformedURLException e) {
//                            e.printStackTrace();
//                        }

                    }
                }
                //标识为关闭摄像头
                CameraServiceImpl.isOpenCamera = false;
                break;
////            //开启相册
//            case CHOOSE_PHOTO:
//                if (resultCode == RESULT_OK) {
////                   //真实的图片路径，判断手机系统版本号
//                    if (Build.VERSION.SDK_INT > 19) {
//                        //android4.4以上
//                        handleImgeOnKitKat(data);
//                    } else {
//                        //android4.4以下且包含4.4
//                        handleImageBeforeKitKat(data);
//                    }
//
//                }
            case TAKE_ALBUM_IMG:
            case TAKE_ALBUM_VIDEO:
                //相册
                if (resultCode == RESULT_OK) {
                    String path;
                    //获取文化路径
                    if (Build.VERSION.SDK_INT > 19) {
                        //android4.4以上
                        path = getFilePath4D4UP(data, requestCode);
                    } else {
                        //android4.4以下且包含4.4
                        path = getFilePath4D4(data, requestCode);
                    }
                    //回调js
                    if (CameraServiceImpl.jsData.getCallbackMethodForApp() != null) {
                        InterfaceForJS.jsCallbackMethod(CameraServiceImpl.jsData.getCallbackMethodForApp(), CameraServiceImpl.androidfile +path);
                    }

                }
                break;
            default:
                break;
        }
    }

    /**
     * 4.4以上系统-获取文件真实路径的方法
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private String getFilePath4D4UP(Intent data, int type) {
        String path = "";
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            //如果是document类型的uri，则通过document id处理,一般是4.4以上进行了封装
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                //解析出数字格式的id
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                //获取图片的真实路径
                path = handleFile(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection, type);
//            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
//                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
//                //获取图片的真实路径
//                path = handleFile(contentUri, null);
            } else if ("content".equalsIgnoreCase(uri.getScheme())) {
                //如果是content类型的uri，则使用普通方式处理
                //获取图片的真实路径
                path = handleFile(uri, null, type);
            } else if ("file".equalsIgnoreCase(uri.getScheme())) {
                //如果是file类型的uri，直接获取图片路径即可
                path = uri.getPath();
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            //就一个路径没有封装
            path = handleFile(uri, null, type);
        }else{
            //如果是file类型的uri，直接获取图片路径即可
            path = uri.getPath();
        }
        return path;

    }

    /**
     * 4.4以下且包含4.4-获取文件真实路径的方法
     */
    private String getFilePath4D4(Intent data, int type) {
        String path = "";
        Uri uri = data.getData();
        //获取图片的真实路径
        path = handleFile(uri, null, type);
        return path;
    }


    /**
     * 通过uri和selection来获取真实的图片路径
     */
    private String handleFile(Uri uri, String selection, int type) {
        String media = "";
        if (type == TAKE_ALBUM_IMG) {
            media = MediaStore.Images.Media.DATA;
        } else if (type == TAKE_ALBUM_VIDEO) {
            media = MediaStore.Video.Media.DATA;
        }
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(media));
            }
            cursor.close();
        }
        return path;
    }

    //存储图片到相册【这里做兼容操作】
    public void saveToSystemGallery(Context context, File file, Uri localUri) {

        //根据不同的系统版本或者手机类型来做不同的操作

        ContentResolver localContentResolver = context.getContentResolver();
        ContentValues localContentValues = getImageContentValues(context, file, System.currentTimeMillis());
        Uri localUri2 = localContentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, localContentValues);
//            Intent localIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
//            localIntent.setData(localUri2);
//            sendBroadcast(localIntent);
//        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//        Uri uri = Uri.fromFile(file);
//        intent.setData(uri);
//        context.sendBroadcast(intent);


//        //插入系统图库
//        //系统相册目录
//        String galleryPath = Environment.getExternalStorageDirectory()
//                + File.separator + Environment.DIRECTORY_DCIM
//                + File.separator + "Camera" + File.separator;
//        File appDir = new File(galleryPath);
//        if (!appDir.exists()) {
//
//            appDir.mkdir();
//
//        }
//
//        //用时间戳命名
//        String fileName = System.currentTimeMillis() + ".jpg";
//
//        File fileos = new File(appDir, fileName);
//        ContentResolver localContentResolver = context.getContentResolver();
//        ContentValues localContentValues = getImageContentValues(context, fileos, System.currentTimeMillis());
//        Uri localUri2 = localContentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, localContentValues);
//

//
//        try {
//
//            FileOutputStream fos = new FileOutputStream(fileos);
//            Bitmap  bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(localUri));
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
//
//            fos.flush();
//
//            fos.close();
//
//        } catch (FileNotFoundException e) {
//
//            e.printStackTrace();
//
//        } catch (IOException e) {
//
//            e.printStackTrace();
//
//        }


//        if (Build.VERSION.SDK_INT >= 23) {
//            // 其次把文件插入到系统图库
//
//            try {
//
//                MediaStore.Images.Media.insertImage(context.getContentResolver(), fileos.getAbsolutePath(), fileName, null);
//
//            } catch (FileNotFoundException e) {
//                Toast.makeText(context, "保存失败", Toast.LENGTH_SHORT).show();
//                e.printStackTrace();
//
//            }
//
//        }

        // 最后通知图库更新

//        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse(fileos.getAbsolutePath())));
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse(file.getAbsolutePath())));


//
////        sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://"+ Environment.getExternalStorageDirectory())));
//        sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, localUri));
        //方法2
//        Uri u = null;
//        if (Build.VERSION.SDK_INT >= 24) {
//            //高于6.0，需要使用一个内容提供其获取本地真是路径，可以提高安全性
//            //BuildConfig.APPLICATION_ID 就是包名，即  com.example.love5
//            u = FileProvider.getUriForFile(Main4Activity.context, BuildConfig.APPLICATION_ID + ".fileprovider", file);
//        } else {
//            //低于等于6.0，可以直接获取图片的本地真实路径
//            u = Uri.fromFile(file);
//
//
//        }
//        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, u));

        //方法3：
//        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));


        //方法2：

//        updateFileFromDatabase(getContext(),filePic);

    }

    //封装图片文件
    public static ContentValues getImageContentValues(Context paramContext, File paramFile,
                                                      long paramLong) {
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("title", paramFile.getName());
        localContentValues.put("_display_name", paramFile.getName());
        localContentValues.put("mime_type", "image/png");
        localContentValues.put("datetaken", paramLong);
        localContentValues.put("date_modified", paramLong);
        localContentValues.put("date_added", paramLong);
        localContentValues.put("orientation", 0);
        localContentValues.put("_data", paramFile.getAbsolutePath());
        localContentValues.put("_size", paramFile.length());
        return localContentValues;
    }


    //sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse(String.valueOf(file))));
//                    //获取ContentResolve对象，来操作插入视频
//                    ContentResolver localContentResolver = getContentResolver();
//                    //ContentValues：用于储存一些基本类型的键值对
//                    ContentValues localContentValues = getVideoContentValues(MainActivity.this, file, System.currentTimeMillis());
//                    //insert语句负责插入一条新的纪录，如果插入成功则会返回这条记录的id，如果插入失败会返回-1。
//                    Uri localUri = localContentResolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, localContentValues);


    //存储视频到相册【这里做兼容操作】
    public void saveToSystemGallery2(Context context, File file) {

        //根据不同的系统版本或者手机类型来做不同的操作

        ContentResolver localContentResolver = context.getContentResolver();
        ContentValues localContentValues = getVideoContentValues(context, file, System.currentTimeMillis());
        Uri localUri = localContentResolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, localContentValues);
        Log.d(TAG, "存储视频到相册6666666: " + localUri);
//        Intent localIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
//
//        localIntent.setData(localUri);
//        sendBroadcast(localIntent);
    }

    //封装视频文件
    public static ContentValues getVideoContentValues(Context paramContext, File paramFile,
                                                      long paramLong) {
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("title", paramFile.getName());
        localContentValues.put("_display_name", paramFile.getName());
//        localContentValues.put("mime_type", "video/3gp");
        localContentValues.put("mime_type", "video/mp4");
        localContentValues.put("datetaken", paramLong);
        localContentValues.put("date_modified", paramLong);
        localContentValues.put("date_added", paramLong);
        localContentValues.put("_data", paramFile.getAbsolutePath());
        localContentValues.put("_size", paramFile.length());
        return localContentValues;
    }


//    //保存到本地相册
//    public void saveToSystemGallery(Context context, Bitmap bmp, Bitmap.CompressFormat suffixType) {
//        //系统相册目录
//        String galleryPath = Environment.getExternalStorageDirectory()
//                + File.separator + Environment.DIRECTORY_DCIM
//                + File.separator + "Camera" + File.separator;
//        File appDir = new File(galleryPath);
//        if (!appDir.exists()) {
//
//            appDir.mkdir();
//
//        }
//
//        //解析文件名称，包含后缀
//        String[] strArr = CameraServiceImpl.tempFilePath.split("\\/");
//        String fileName = strArr[strArr.length - 1];
//        File file = new File(appDir, fileName);
//
//        try {
//
//            FileOutputStream fos = new FileOutputStream(file);
//
//            bmp.compress(suffixType, 100, fos);
//
//            fos.flush();
//
//            fos.close();
//
//        } catch (FileNotFoundException e) {
//
//            e.printStackTrace();
//
//        } catch (IOException e) {
//
//            e.printStackTrace();
//
//        }
//
//        if (Build.VERSION.SDK_INT >= 24) {
//            //把文件插入到系统图库
//            try {
//
//                MediaStore.Images.Media.insertImage(context.getContentResolver(),
//                        file.getAbsolutePath(), fileName, null);
//            } catch (FileNotFoundException e) {
//                Toast.makeText(context, "保存到相册失败", Toast.LENGTH_SHORT).show();
//                e.printStackTrace();
//
//            }
//        }
//
//        // 最后通知图库更新
//        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse(file.getAbsolutePath())));
//        Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT).show();
//    }

}
