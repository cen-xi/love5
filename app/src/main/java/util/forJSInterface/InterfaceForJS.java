package util.forJSInterface;

import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.alibaba.fastjson.JSON;
import com.example.love5.Main4Activity;

import util.forJSInterface.Camera.CameraService;
import util.forJSInterface.Camera.serviceImpl.CameraServiceImpl;
import util.forJSInterface.service.JsService;
import util.forJSInterface.service.JsServiceImpl;

/**
 * 用于被js调用的接口
 */
public class InterfaceForJS {

    private static JsService jsService = new JsServiceImpl();
    private static CameraService cameraService = new CameraServiceImpl();


    //app 调用 页面的 js 方法
    public static void jsCallbackMethod(final String method, final String params) {

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

//
//
//    //无返回结果
//    @JavascriptInterface
//    public void jsCallbackMethod1(String jsonstr) {
//        //解析接口参数为对象
//        System.out.println(jsonstr);
//        JsData d = JSON.parseObject(jsonstr, JsData.class);
//        System.out.println(d.getType());
//        System.out.println(d.getData());
//        System.out.println(d.getCallbackMethodForApp());
//
//    }
//
//    //有返回结果
//    @JavascriptInterface
//    public String jsCallbackMethod2(String jsonstr) {
//        //解析接口参数为对象
//        System.out.println(jsonstr);
//        JsData d = JSON.parseObject(jsonstr, JsData.class);
//        System.out.println(d.getType());
//        System.out.println(d.getData());
//        System.out.println(d.getCallbackMethodForApp());
//        String res = "";
//        switch (d.getType()) {
//            case "getGPSData":
////                获取gps经纬度数据
////                res = jsService.getGPSData(d);
//                break;
//            default:
//                break;
//
//        }
//
//        return res;
//    }

    //使用单独的回调js方法回调结果
    @JavascriptInterface
    public String jsCallbackMethod(String jsonstr) {
        //解析接口参数为对象
        System.out.println(jsonstr);
        JsData d = JSON.parseObject(jsonstr, JsData.class);
        System.out.println(d.getType());
        System.out.println(d.getData());
        System.out.println(d.getCallbackMethodForApp());
        String res = "";
        switch (d.getType()) {
            case "getGPSData":
                //获取gps经纬度数据
                res = jsService.getGPSData(d);
                break;
            case "getCameraData":
                //获取摄像头数据【拍照或者录像】
                res = cameraService.getCamera(d);
//                final String js = "javascript:" + method + "('" + params + "')";
//                final String js = "javascript:showfile('file:///storage/emulated/0/Android/data/com.example.love5/2021-04-13/CameraFile/img_16182994103701.png')";
//                //获取容器对象
//                final WebView w = Main4Activity.w;
//                //调用js方法
//                w.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        //执行
//                        w.loadUrl(js);
//                    }
//                });
                break;
            case "getAlbumData":
                //获取相册数据
                res = cameraService.getAlbum(d);
                break;
            default:
                break;

        }
        return res;
//        System.out.println(jsonstr);
//        JsData d = JSON.parseObject(jsonstr, JsData.class);
//        System.out.println(d.getType());
//        System.out.println(d.getData());
//        System.out.println(d.getCallbackMethodForApp());
//        d.setData("苹果" + d.getData());
//        //传给js方法的参数
//        final String params = JSON.toJSONString(d);
//        //执行的js脚本
//        final String js = "javascript:" + d.getCallbackMethodForApp() + "('" + params + "')";
//        //获取web容器对象
//        final WebView w = Main4Activity.w;
//        //调用js方法
//        w.post(new Runnable() {
//            @Override
//            public void run() {
//                w.loadUrl(js);
//            }
//        });

    }


}
