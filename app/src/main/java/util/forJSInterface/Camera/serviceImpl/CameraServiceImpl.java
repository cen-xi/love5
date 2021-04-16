package util.forJSInterface.Camera.serviceImpl;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.format.Time;
import android.util.Log;
import android.widget.Toast;

import com.example.love5.BuildConfig;
import com.example.love5.Main4Activity;
import com.example.love5.MainCamera;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.ui.ImageGridActivity;

import java.io.File;
import java.util.Date;
import java.util.Random;

import util.forJSInterface.Camera.CameraService;
import util.forJSInterface.JsData;

public class CameraServiceImpl implements CameraService {


    //文件的本地相对路径
    public static Uri tempFileUri;
    //文件的本地完成存储路径 【即加上了savefont + saveMid】
    public static String tempFilePath = "";
    //相对路径 【即加上了  saveMid】
    public static String filePath = "";

    //    public static String file = "file://";
    public final static String androidfile = "http://androidfile";

    //临时存储 js数据 对象 ， 因为是1对1 的关系，不怕数据取错
    public static JsData jsData;
    //标志是否正常开启摄像头
    public static boolean isOpenCamera = false;

    //设置一个临时路径，保存所拍的文件
//    public static final String savefont = "/Android/data/expressApp/";
    //记得加 /storage/emulated/0/  部分

//    String galleryPath = Environment.getExternalStorageDirectory()
//            + File.separator + Environment.DIRECTORY_DCIM
//            + File.separator + "Camera" + File.separator;

    public static final String savefont = Environment.getExternalStorageDirectory()
            + File.separator + Environment.DIRECTORY_DCIM
            + File.separator + "Camera";
    public static final String saveMid = "/";

    //    public static final String savefont = "/storage/emulated/0";
//    public static final String saveMid = "/Android/data/com.example.love5/";
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    //获取摄像头数据
    @Override
    public String getCamera(JsData d) {

        //关闭摄像头时才可以开启
        if (!isOpenCamera) {
            //【1是照相机，2是录像机】
            if (d.getData().equals("1")) {
                //相机
                if (checkLocationPower(Main4Activity.TAKE_CAMERA)) {
                    jsData = d;
                    //开启相机
                    openCamera();
//                    //开启图片选择器
//                    Intent intent = new Intent(Main4Activity.context, ImageGridActivity.class);
//                    ((Activity) Main4Activity.context).startActivityForResult(intent, ImagePicker.RESULT_CODE_ITEMS);


                }
            } else {
                //录像机
                if (checkLocationPower(Main4Activity.TAKE_VIDEO_CAMERA)) {
                    jsData = d;
                    //开启录像机
                    openVideoCamera();
                }
            }
        }
        return "";
    }


    //检查相机和存储相关的权限
    private boolean checkLocationPower(int toke) {

        //动态权限请求
        int CAMERA = ContextCompat.checkSelfPermission(Main4Activity.context, Manifest.permission.CAMERA);
        int WRITE_EXTERNAL_STORAGE = ContextCompat.checkSelfPermission(Main4Activity.context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int READ_EXTERNAL_STORAGE = ContextCompat.checkSelfPermission(Main4Activity.context, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (CAMERA != PackageManager.PERMISSION_GRANTED ||
                WRITE_EXTERNAL_STORAGE != PackageManager.PERMISSION_GRANTED ||
                READ_EXTERNAL_STORAGE != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions((Activity) Main4Activity.context,
                    new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                    }, toke);
            return false;
        }

        return true;
    }


    //开启相机
    public static void openCamera() {
        //获取临时存储的文件路径
        getTempuri(Main4Activity.TAKE_CAMERA);
        //标识为关闭摄像头
        CameraServiceImpl.isOpenCamera = true;
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//        Intent intent = new Intent("MediaStore.ACTION_IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, tempFileUri);
        ((Activity) Main4Activity.context).startActivityForResult(intent, Main4Activity.TAKE_CAMERA);
    }

    //开启录像机
    public static void openVideoCamera() {
        //获取临时存储的文件路径
        getTempuri(Main4Activity.TAKE_VIDEO_CAMERA);
        //标识为关闭摄像头
        CameraServiceImpl.isOpenCamera = true;
        Intent intent = new Intent("android.media.action.VIDEO_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, tempFileUri);
        ((Activity) Main4Activity.context).startActivityForResult(intent, Main4Activity.TAKE_VIDEO_CAMERA);
    }

    //获取临时存储的文件路径
    private static void getTempuri(int type) {
        Date t = new Date();
        //转日期字符串
        String sCurDate = format.format(t);

//        filePath = saveMid + "CameraFile/" + sCurDate;
//        File CameraFolder = new File(savefont + saveMid + "CameraFile/", sCurDate);
        File CameraFolder = new File(savefont);
        if (!CameraFolder.exists()) {
            //创建文件夹【多级】
            CameraFolder.mkdirs();
        }
        if (CameraFolder.exists() && CameraFolder.isDirectory()) {
            Log.d("yzf", "创建文件夹成功");
        } else {
            Log.d("yzf", "文件夹已经存在");
        }

        //随机数
        int i = (new Random().nextInt(9));

        if (type == Main4Activity.TAKE_CAMERA) {
            //图片，png格式
            tempFilePath = CameraFolder + "/img_" + t.getTime() + i + ".png";
            filePath = saveMid + "CameraFile/" + sCurDate + "/img_" + t.getTime() + i + ".png";
        } else {
            //视频，MP4格式
            tempFilePath = CameraFolder + "/video_" + t.getTime() + i + ".mp4";
            filePath = saveMid + "CameraFile/" + sCurDate + "/img_" + t.getTime() + i + ".mp4";
        }

        Log.d("文件路径", tempFilePath);
        //创建File并获取它的URI值
        if (Build.VERSION.SDK_INT >= 24) {
            //高于6.0，需要使用一个内容提供其获取本地真是路径，可以提高安全性
            //BuildConfig.APPLICATION_ID 就是包名，即  com.example.love5
            tempFileUri = FileProvider.getUriForFile(Main4Activity.context, BuildConfig.APPLICATION_ID + ".fileprovider", new File(tempFilePath));
        } else {
            //低于等于6.0，可以直接获取图片的本地真实路径
            tempFileUri = Uri.fromFile(new File(tempFilePath));


        }
    }


    //获取相册数据
    @Override
    public String getAlbum(JsData d) {
        //【1是图片，2是是视频】
        if (d.getData().equals("1")) {
            //图片
            if (checkLocationPower(Main4Activity.TAKE_ALBUM_IMG)) {
                jsData = d;
                openAlbum(Main4Activity.TAKE_ALBUM_IMG);
            }
        }else  if (d.getData().equals("2")) {
            //视频
            if (checkLocationPower(Main4Activity.TAKE_ALBUM_VIDEO)) {
                jsData = d;
                openAlbum(Main4Activity.TAKE_ALBUM_VIDEO);
            }
        }

        return null;
    }

    //开启相册
    public static void openAlbum(int type) {
        String typestr = "";
        if (type == Main4Activity.TAKE_ALBUM_IMG) {
            typestr = "image/*";
        } else if (type == Main4Activity.TAKE_ALBUM_VIDEO) {
            typestr = "video/*";
        } else {
            Toast.makeText(Main4Activity.context, "仅支持图片和视频", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            //低于4.4
            intent = new Intent(Intent.ACTION_GET_CONTENT);
        } else {
            intent = new Intent(Intent.ACTION_PICK);
        }
        intent.setType(typestr);
        ((Activity) Main4Activity.context).startActivityForResult(intent, type);//开启相册
    }


}
