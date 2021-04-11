package com.example.love5;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainSendMessage extends AppCompatActivity {
    private Button b1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_send_message);

        b1 = findViewById(R.id.b1);
        b1.setOnClickListener(new View.OnClickListener() {
            //8.0以下不需要使用通知渠道,8.0以上包含8.0必须要,不然会提示没有权限

            @Override
            public void onClick(View v) {

/**
 *  判断手机Android版本是否大于26，即Android8.0
 *
 */
//也可以写成android.os.Build.VERSION.SDK_INT
                if (Build.VERSION.SDK_INT >= 26) {
                    //高于8.0，需要使用通知渠道
                    version6up();

                } else {
                    //低于8.0，可以直接通知
                    version6down();

                }
            }
        });


    }


    //高于8.0，需要使用通知渠道
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void version6up() {
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //创建通知渠道实例
        //并为它设置属性
        //通知渠道的ID,随便写
        String id = "channel_01";
        //用户可以看到的通知渠道的名字，R.string.app_name就是strings.xml文件的参数，自定义一个就好了
        CharSequence name = getString(R.string.app_name);
        //用户可看到的通知描述
        String description = getString(R.string.app_name);
        //构建NotificationChannel实例
        NotificationChannel notificationChannel =
                new NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH);
        //
        //
        //配置通知渠道的属性
        notificationChannel.setDescription(description);
        //设置通知出现时的闪光灯
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.RED);
        //设置通知出现时的震动
        notificationChannel.enableVibration(true);
        notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 100});
        //
        //
        //
        //
        //在notificationManager中创建通知渠道
        manager.createNotificationChannel(notificationChannel);
        //
        //
        //
        //加入点击功能更，然后跳到指定页面，Main12ActivityChat.class是你要取得地方，按返回键会默认回到这里
        Intent intent = new Intent(MainSendMessage.this, Main12ActivityChat.class);
        PendingIntent pi = PendingIntent.getActivity(MainSendMessage.this, 0, intent, 0);
        //
        //
        //
        Notification notification = new NotificationCompat.Builder(MainSendMessage.this, id)
                .setContentTitle("标题")
                .setContentText("内容888这是不低于8.0版本")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.tubiao)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.xiaren))
                //跳到指定页面才加这行
                .setContentIntent(pi)
                //点击后通知了自动消失
                .setAutoCancel(true)
                .build();
        manager.notify(1, notification);


    }


    //低于8.0，可以直接通知
    private void version6down() {

        /**
         * 老师教的方法
         */
        //低于8.0，可以直接通知
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //加入点击功能更，然后跳到指定页面，Main12ActivityChat.class是你要取得地方，按返回键会默认回到这里
        Intent intent = new Intent(MainSendMessage.this, Main12ActivityChat.class);
        PendingIntent pi = PendingIntent.getActivity(MainSendMessage.this, 0, intent, 0);

        Notification.Builder builder = new Notification.Builder(MainSendMessage.this);
        builder.setContentTitle("标题")
                .setContentText("内容666这是低于8.0版本")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.tubiao)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.coffee))
                //跳到指定页面才加这行
                .setContentIntent(pi)
                //点击后通知了自动消失
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);   //设置默认的三色灯与振动器
        manager.notify(111, builder.build());
//

        /**
         * 我的方法，感觉还是我自己的方法好理解一点
         */
//        //低于8.0，可以直接通知
//        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        //加入点击功能更，然后跳到指定页面，Main12ActivityChat.class是你要取得地方，按返回键会默认回到这里
//        Intent intent = new Intent(MainSendMessage.this, Main12ActivityChat.class);
//        PendingIntent pi = PendingIntent.getActivity(MainSendMessage.this, 0, intent, 0);


//        Notification notification = new NotificationCompat.Builder(MainSendMessage.this)
//                .setContentTitle("标题")
//                .setContentText("内容666这是低于8.0版本")
//                .setWhen(System.currentTimeMillis())
//                .setSmallIcon(R.drawable.tubiao)
//                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.coffee))
//                //跳到指定页面才加这行
//                .setContentIntent(pi)
//                //点击后通知了自动消失
//                .setAutoCancel(true)
//                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE)    //设置默认的三色灯与振动器
//                .build();
//        manager.notify(1, notification);
    }


}


//    /**
//     * Notification:8.0系统和8.0系统之下的逻辑
//     */
//    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//    public void postNotification(Context context) {
//        int channelId = 0x22222;
//        Notification.Builder builder;
//        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        //NotificationManagerCompat managerCompat= (NotificationManagerCompat) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {//8.0系统之上
//            NotificationChannel channel = new NotificationChannel(String.valueOf(channelId), "chanel_name", NotificationManager.IMPORTANCE_HIGH);
//            manager.createNotificationChannel(channel);
//            builder = new Notification.Builder(context, String.valueOf(channelId));
//            //或者
//            //builder = new Notification.Builder(context);
//            //builder.setChannelId(String.valueOf(channelId)); //创建通知时指定channelId
//        } else {
//            builder = new Notification.Builder(context);
//        }
//        //需要跳转指定的页面
//        Intent intent = new Intent(context, MainActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        builder.setContentIntent(pendingIntent);
//
//        builder.setTicker("new message")
//                .setSmallIcon(R.drawable.ic_launcher_background)
//                .setContentTitle("标题")
//                .setContentText("内容")
//                .setContentIntent(pendingIntent);
//        manager.notify(channelId, builder.build());
//    }
