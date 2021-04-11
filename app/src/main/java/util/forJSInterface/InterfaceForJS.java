package util.forJSInterface;

import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.alibaba.fastjson.JSON;
import com.example.love5.Main4Activity;

/**
 * 用于被js调用的接口
 */
public class InterfaceForJS {

    @JavascriptInterface
    public void jsCallbackMethod(String jsonstr) {
        //解析接口参数为对象
        System.out.println(jsonstr);
        JsData d = JSON.parseObject(jsonstr, JsData.class);
        System.out.println(d.getType());
        System.out.println(d.getData());
        System.out.println(d.getCallbackMethodForApp());

    }

    @JavascriptInterface
    public String jsCallbackMethod2(String jsonstr) {
        //解析接口参数为对象
        System.out.println(jsonstr);
        JsData d = JSON.parseObject(jsonstr, JsData.class);
        System.out.println(d.getType());
        System.out.println(d.getData());
        System.out.println(d.getCallbackMethodForApp());
        return "苹果" + d.getData();
    }

    @JavascriptInterface
    public void jsCallbackMethod3(String jsonstr) {
        //解析接口参数为对象
        System.out.println(jsonstr);
        JsData d = JSON.parseObject(jsonstr, JsData.class);
        System.out.println(d.getType());
        System.out.println(d.getData());
        System.out.println(d.getCallbackMethodForApp());
        d.setData("苹果" + d.getData());
        //传给js方法的参数
        final String params = JSON.toJSONString(d);
        //执行的js脚本
        final String js = "javascript:" + d.getCallbackMethodForApp() + "('" + params + "')";
        //获取web容器对象
        final WebView w = Main4Activity.w;
        //调用js方法
        w.post(new Runnable() {
            @Override
            public void run() {
                w.loadUrl(js);
            }
        });

    }


}
