package util;

//public class R11111111111 {
//}

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

//import com.example.paipaixiu.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class R11111111111 {
    //查询用户名是否可用
    public int selectUserName(final String name) {
        //0,不可使用；1，可以使用
        //开启线程来发起网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody rb = new FormBody.Builder()
                            .add("id", "")
                            .add("psw", "")
                            .build();
                    Request request = new Request.Builder()
                            .url("http://c2g1314.imwork.net:46084/android")
                            .post(rb)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responsedata = response.body().string();
                    //你要做的事情
//                    showResponse(responsedata);


                } catch (MalformedURLException e) {
                    e.printStackTrace();

                } catch (ProtocolException e) {
                    e.printStackTrace();

                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
        }).start();


        return 1;
    }




}
