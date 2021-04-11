package com.example.love5;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.luck.picture.lib.config.PictureMimeType.ofImage;

public class MainCamera extends AppCompatActivity {
    //按钮
    private Button b1, b2, save;
    //图片框
    private ImageView i1, i2;
    //文本框
    private EditText text;


    //文件的本地真实路径
    private Uri imageuri;


    //    从新页面活动结束回来后，用来识别时从哪个活动回来的id，用于辨别不同的操作
    private static final int TAKE_PHOTO = 1;
    //读取sd卡权限
    private static final int CHOOSE_PHOTO = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_camera);

        //拍照和显示图片到指定地方
        i1 = findViewById(R.id.i1);
        b1 = findViewById(R.id.b1);

        //打开相册，并选中相应图片显示到指定地方
        i2 = findViewById(R.id.i2);
        b2 = findViewById(R.id.b2);
        //文本框
        text = findViewById(R.id.text);
//
//        //使用框架，经果测试，Android版本高于7.0都无法正常使用，拍照没有图片返回来，所以，只能拍照使用原生的办法了，图库就用图片选择器
//        b1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
////                PictureSelector.create(MainCamera.this)
////                        .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
////                        .theme(R.style.picture_default_style)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
////                        .maxSelectNum(1)// 最大图片选择数量 int
////                        .minSelectNum(1)// 最小选择数量 int
////                        .imageSpanCount(3)// 每行显示个数 int
//////                        .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
////                        .previewImage(true)// 是否可预览图片 true or false
////////                        .previewVideo(true)// 是否可预览视频 true or false
////////                        .enablePreviewAudio(true) // 是否可播放音频 true or false
//////                        .isCamera(false)// 是否显示拍照按钮 true or false
////////                        .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
////////                        .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
////////                        .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
//////                        .setOutputCameraPath("/storage/emulated/0/PictureSelector/CameraImage/")// 自定义拍照保存路径,可不填
//////                        .enableCrop(true)// 是否裁剪 true or false
//////////                        .compress(true)// 是否压缩 true or false
////////////                        .glideOverride()// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
//////                        .withAspectRatio(1, 1)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
////                        .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示 true or false
//////////                        .isGif(true)// 是否显示gif图片 true or false
//////////                        .compressSavePath("")//压缩图片保存地址
//////                        .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
////////                        .circleDimmedLayer(true)// 是否圆形裁剪 true or false
//////                        .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
//////                        .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
////                        .openClickSound(true)// 是否开启点击声音 true or false
////////                        .selectionMedia()// 是否传入已选图片 List<LocalMedia> list
////                        .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
//////////                        .cropCompressQuality(90)// 裁剪压缩质量 默认90 int
////////                        .minimumCompressSize(100)// 小于100kb的图片不压缩
////////                        .synOrAsy(true)//同步true或异步false 压缩 默认同步
//////////                        .cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
//////                        .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
//////                        .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
//////////                        .videoQuality(1)// 视频录制质量 0 or 1 int
////////                        .videoMaxSecond(15)// 显示多少秒以内的视频or音频也可适用 int
////////                        .videoMinSecond(10)// 显示多少秒以内的视频or音频也可适用 int
////////                        .recordVideoSecond(10)//视频秒数录制 默认60s int
//////                        .isDragFrame(true)// 是否可拖动裁剪框(固定)
////                        .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
////
//
//
//
////包括裁剪和压缩后的缓存，要在上传成功后调用，注意：需要系统sd卡权限
////                PictureFileUtils.deleteCacheDirFile(MainCamera.this);
//
////                单独启动拍照或视频 根据PictureMimeType自动识别
//                PictureSelector.create(MainCamera.this)
//                        .openCamera(PictureMimeType.ofImage())
//                        .forResult(PictureConfig.CHOOSE_REQUEST);
//
////                //拍照相册都开启
////                PictureSelector.create(MainCamera.this)
////                        .openGallery(ofImage())
////                        .forResult(PictureConfig.CHOOSE_REQUEST);
//
////                openall();
//
//
//            }
//        });
//
//        b2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!TextUtils.isEmpty(text.getText())) {
//                    //下载操作图片
//                    download();
//                } else {
//                    Toast.makeText(getApplicationContext(), "网址为空", Toast.LENGTH_SHORT).show();
//                }
//
//
//            }
//        });
//
//
//    }
//
////    //开启拍照与图库
////    private void openall() {
////        PictureSelector.create(MainCamera.this)
////                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
////                .theme(R.style.picture_default_style)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
////                .maxSelectNum(1)// 最大图片选择数量 int
////                .minSelectNum(1)// 最小选择数量 int
////                .imageSpanCount(3)// 每行显示个数 int
//////                .selectionMode()// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
//////                .previewImage(true)// 是否可预览图片 true or false
//////                .previewVideo()// 是否可预览视频 true or false
//////                .enablePreviewAudio() // 是否可播放音频 true or false
//////                .isCamera(true)// 是否显示拍照按钮 true or false
//////                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
//////                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
//////                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
//////                .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
//////                .enableCrop()// 是否裁剪 true or false
//////                .compress()// 是否压缩 true or false
//////                .glideOverride()// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
//////                .withAspectRatio()// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
//////                .hideBottomControls()// 是否显示uCrop工具栏，默认不显示 true or false
//////                .isGif()// 是否显示gif图片 true or false
//////                .compressSavePath(getPath())//压缩图片保存地址
//////                .freeStyleCropEnabled()// 裁剪框是否可拖拽 true or false
//////                .circleDimmedLayer()// 是否圆形裁剪 true or false
//////                .showCropFrame()// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
//////                .showCropGrid()// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
//////                .openClickSound()// 是否开启点击声音 true or false
//////                .selectionMedia()// 是否传入已选图片 List<LocalMedia> list
////                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
//////                .cropCompressQuality()// 裁剪压缩质量 默认90 int
//////                .minimumCompressSize(100)// 小于100kb的图片不压缩
//////                .synOrAsy(true)//同步true或异步false 压缩 默认同步
//////                .cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
//////                .rotateEnabled() // 裁剪是否可旋转图片 true or false
//////                .scaleEnabled()// 裁剪是否可放大缩小图片 true or false
//////                .videoQuality()// 视频录制质量 0 or 1 int
//////                .videoMaxSecond(15)// 显示多少秒以内的视频or音频也可适用 int
//////                .videoMinSecond(10)// 显示多少秒以内的视频or音频也可适用 int
//////                .recordVideoSecond()//视频秒数录制 默认60s int
//////                .isDragFrame(false)// 是否可拖动裁剪框(固定)
////                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
////
////
////
////
////    }
//
//    //用框架
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
//            switch (requestCode) {
//                case PictureConfig.CHOOSE_REQUEST:
//                    // 图片、视频、音频选择结果回调
//                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
//                    //String转uri的方法
////                    Uri path =  Uri.parse(selectList.get(0).getPath());
//                    Log.d("MainCamera.this", "=============================" + selectList.get(0).getPath() + "");
//
//                    //根据真实路径将图片放到指定的地方图片
//                    Bitmap bitmap = BitmapFactory.decodeFile(selectList.get(0).getPath());
//                    i1.setImageBitmap(bitmap);
//                    text.setText("正在上传。。。");
//                    //上传操作
//                    upload(selectList.get(0).getPath());
//                    // 例如 LocalMedia 里面返回三种path
//                    // 1.media.getPath(); 为原图path
//                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
//                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
//                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
////                    adapter.setList(selectList);
////                    adapter.notifyDataSetChanged();
//
//                //包括裁剪和压缩后的缓存，要在上传成功后调用，注意：需要系统sd卡权限
//                PictureFileUtils.deleteCacheDirFile(MainCamera.this);
//                    break;
//
//
//            }
//        }
//
//    }
//
//
//    public void upload(final String path) {
//        //开启线程来发起网络请求
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                File file = new File(path);
//
//
//                try {
//                    OkHttpClient client = new OkHttpClient();
////                    RequestBody rb = new FormBody.Builder()
////                            .add("name", str)
////                            .build();
//
//
////                    MediaType指的是要传递的数据的MIME类型，MediaType对象包含了三种信息：type  、subtype以及charset，
//// 一般将这些信息传入parse()方法中，这样就可以解析出MediaType对象，比如 "text/x-markdown; charset=utf-8" ，type值是text，
//// 表示是文本这一大类；/后面的x-markdown是subtype，表示是文本这一大类下的markdown这一小类； charset=utf-8 则表示采用UTF-8编码。
//// 如果不知道某种类型数据的MIME类型，可以参见链接Media Type和MIME 参考手册，较详细的列出了所有数据的MIME类型。
////                    json : application/json
////                    xml : application/xml
////                    png : image/png
////                    jpg : image/jpeg
////                    gif : imge/gif
//
//                    RequestBody image = RequestBody.create(MediaType.parse("image/*"), file);
//                    RequestBody requestBody = new MultipartBody.Builder()
//                            .setType(MultipartBody.FORM)
//                            .addFormDataPart("myfile", path, image)
//                            .build();
//
//
//                    Request request = new Request.Builder()
//                            .url("http://c2g1314.imwork.net:11673/cctv/gg/ajaxup")
////                            .url("http://c2g1314.imwork.net:11673/android")
//                            .post(requestBody)
//                            .build();
//                    Response response = client.newCall(request).execute();
//                    //返回的结果,string转int
////                    final int flag = Integer.parseInt(response.body().string().trim());
//
////                    //返回结果，转json类型
////                    final JSONObject jsonObject = new JSONObject(response.body().string().trim);
//                    final String t = response.body().string().trim();
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
////                            String t =jsonObject.optString("text");
//
//                            text.setText(t);
//
//
//                        }
//                    });
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }
//
//    public void download() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                OkHttpClient okHttpClient = new OkHttpClient();
//                Request request = new Request.Builder()
//                        .url(text.getText().toString())
//                        .build();
//
//                try {
//                    Response response = okHttpClient.newCall(request).execute();
//                    final byte[] bytes = response.body().bytes();
//
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//                            i2.setImageBitmap(bitmap);
//
//                        }
//                    });
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//
//
//    }
//
//
//}
//
//
////
//
//
// ======================================================================================================
        //原生代码，不用框架
        // //开启相机
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取摄像头权限值
                int checkCallPhonePermission = ContextCompat.checkSelfPermission(MainCamera.this, Manifest.permission.CAMERA);
//               // 判断权限值是否相等，不等则表示未授权
                if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                    //若没权限则显示提示框是否可以开启
                    //请求授权，<uses-permission android:name="android.permission.CAMERA" />授权格式由Manifest.和permission.CAMERA组成，注意规律
                    ActivityCompat.requestPermissions(MainCamera.this, new String[]{Manifest.permission.CAMERA}, TAKE_PHOTO);
                } else {
                    //曾获得权限或者版本低于6.0，则不需重新询问获取，可直接开启
                    //调用获取缓存文件真实路径的方法
                    getImageUri();
                    //调用开启摄像头方法
                    openCamera();
                }


            }
        });


        //原生代码，不用框架
        // //开启相册
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取读取手机sd卡的权限
                int checkCallPhonePermission = ContextCompat.checkSelfPermission(MainCamera.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                    //无权限
                    ActivityCompat.requestPermissions(MainCamera.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CHOOSE_PHOTO);
                } else {
                    //以获取权限
                    //直接开启相册
                    //调用开启相册的方法
                    openAlbum();

                }

            }
        });

        //保存照片到相册
        save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                //系统相册目录
//                String galleryPath= Environment.getExternalStorageDirectory()
//                        + File.separator + Environment.DIRECTORY_DCIM
//                        +File.separator+"Camera"+File.separator;
//                // 声明文件对象
//                File file = null;
//                // 声明输出流
//                FileOutputStream outStream = null;
//                Bitmap bitmap;
//                try {
//                    // 如果有目标文件，直接获得文件对象，否则创建一个以filename为名称的文件
//                    file = new File(getExternalCacheDir(), "output_image.jpg");
//
//                    // 获得文件相对路径
//                    String fileName = file.toString();
//                    // 获得输出流，如果文件中有内容，追加内容
//                    outStream = new FileOutputStream(fileName);
//                    if (null != outStream) {
//                         bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageuri));
//                        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outStream);
//
////通知相册更新
//                        MediaStore.Images.Media.insertImage(MainCamera.this.getContentResolver(),
//                                bitmap, fileName, null);
//                        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//                        Uri uri = Uri.fromFile(file);
//                        intent.setData(uri);
//                        MainCamera.this.sendBroadcast(intent);
//
//
//                    }
//
//                } catch (Exception e) {
//                    e.getStackTrace();
//                }finally {
//                    try {
//                        if (outStream != null) {
//                            outStream.close();
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                long t=System.currentTimeMillis();
//                String fileName = String.valueOf(t);
//
//
//                MediaStore.Images.Media.insertImage(MainCamera.this.getContentResolver(),
//
//                        imageuri, fileName, null);
//

                if(imageuri != null){
                    Bitmap bitmap = null;
                    try {
                        bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageuri));

                        saveToSystemGallery(MainCamera.this,bitmap);
                        imageuri = null;

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }else {
                    Toast.makeText(getApplicationContext(),"请先拍照",Toast.LENGTH_SHORT).show();
                }


            }
        });











    }



    public void saveToSystemGallery(Context context, Bitmap bmp) {
        //系统相册目录
        String galleryPath = Environment.getExternalStorageDirectory()
                + File.separator + Environment.DIRECTORY_DCIM
                + File.separator + "Camera" + File.separator;
        // 首先保存图片

        //设置存储位置文件夹名称
//        File appDir = new File(Environment.getExternalStorageDirectory(), "love5");
        File appDir = new File(galleryPath);
        if (!appDir.exists()) {

            appDir.mkdir();

        }

        //用时间戳命名
        String fileName = System.currentTimeMillis() + ".jpg";

        File file = new File(appDir, fileName);

        try {

            FileOutputStream fos = new FileOutputStream(file);

            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);

            fos.flush();

            fos.close();

        } catch (FileNotFoundException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }


        if (Build.VERSION.SDK_INT >= 23) {
            // 其次把文件插入到系统图库

            try {

                MediaStore.Images.Media.insertImage(context.getContentResolver(),

                        file.getAbsolutePath(), fileName, null);

            } catch (FileNotFoundException e) {
                Toast.makeText(MainCamera.this, "保存失败", Toast.LENGTH_SHORT).show();
                e.printStackTrace();

            }

        }

        // 最后通知图库更新

        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse(file.getAbsolutePath())));
        //相当于
//        MediaStore.Images.Media.insertImage(context.getContentResolver(),bmp,fileName,null);
//        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//        Uri uri = Uri.fromFile(file);
//        intent.setData(uri);
//        context.sendBroadcast(intent);


        Toast.makeText(MainCamera.this, "保存成功", Toast.LENGTH_SHORT).show();

    }









    //获取摄像头拍照后图片的存储路径，即获取缓存文件真实路径的方法
    private void getImageUri() {
        //创建file对象，用于存储拍照后的图片，getExternalCacheDir()可以获得应用关联缓存目录,在里面查找一个output_image.jpg的文件
        File outputImage = new File(getExternalCacheDir(), "output_image.jpg");

        try {
            //判断这个文件是否存在，存在则删除然后新建一个空的，等待新的照片临时缓存
            if (outputImage.exists()) {
                //删除
                outputImage.delete();
            }
            //新建
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //都要提前在Androidmainfest.xml里注册权限<uses-permission android:name="android.permission.CAMERA"
        //获取文件的本地真实路径imageuri
        //判断手机Android版本是否大于23，即Android6.0,6.0版本后读取的真实路径方法不一样
        if (Build.VERSION.SDK_INT >= 23) {
            //高于6.0，需要使用一个内容提供其获取本地真是路径，可以提高安全性
            imageuri = FileProvider.getUriForFile(MainCamera.this, "com.example.cameraalbumtest.fileprovider", outputImage);
        } else {
            //低于6.0，可以直接获取图片的本地真实路径
            imageuri = Uri.fromFile(outputImage);

        }

    }

    //   // 开启摄像头方法
    private void openCamera() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageuri);
        startActivityForResult(intent, TAKE_PHOTO);
    }

    //    //调用开启相册的方法,仅获取图片
    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);//开启相册

//    intent.setType(“image/*”);//选择图片
//intent.setType(“audio/*”); //选择音频
//intent.setType(“video/*”); //选择视频 （mp4 3gp 是android支持的视频格式）
//intent.setType(“video/;image/”);//同时选择视频和图片

    }


    //    //权限询问反馈，判断多个权限是否通过
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //requestCode为1则表示有授权返回内容，是个自然数，一般调用这个方法时都是1
        switch (requestCode) {
            case 1:
                //一般都是单个授权，所有只获取数组permissions【0】
                switch (permissions[0]) {
                    case Manifest.permission.CAMERA://摄像头授权
                        //重新确认是否授权成功
                        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                            //成功，获取图片缓存真实路径
                            getImageUri();
                            //启动摄像头
                            openCamera();
                        } else {
                            Toast.makeText(this, "没有开启摄像头的权限", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case Manifest.permission.WRITE_EXTERNAL_STORAGE://访问sd卡的权限
                        // //重新确认是否授权成功
                        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                            //读取sd卡开启相册
                            openAlbum();
                        } else {
                            Toast.makeText(this, "没有访问sd卡的权限", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    default:
                }
                break;
            default:
        }
    }


    //从新页面活动结束回来后，进行操作，requestCode就是识别从哪个活动回来的id,resultCode判断是否成功运行的参数
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            //开启相机
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        //把拍摄的照片显示出来
                        //由真实路径转化成Bitmap格式
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageuri));
                        i1.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
//            //开启相册
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
//                   //真实的图片路径，判断手机系统版本号
                    if (Build.VERSION.SDK_INT > 19) {
                        //android4.4以上
                        handleImgeOnKitKat(data);
                    } else {
                        //android4.4以下且包含4.4
                        handleImageBeforeKitKat(data);
                    }

                }
            default:
                break;
        }
    }

    /**
     * 4.4及以下系统处理图片的方法
     */
    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        //获取图片的真实路径
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    /**
     * 4.4以上系统处理图片的方法
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void handleImgeOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            //如果是document类型的uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                //解析出数字格式的id
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                //获取图片的真实路径
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                //获取图片的真实路径
                imagePath = getImagePath(contentUri, null);
            } else if ("content".equalsIgnoreCase(uri.getScheme())) {
                //如果是content类型的uri，则使用普通方式处理
                //获取图片的真实路径
                imagePath = getImagePath(uri, null);
            } else if ("file".equalsIgnoreCase(uri.getScheme())) {
                //如果是file类型的uri，直接获取图片路径即可
                imagePath = uri.getPath();
            }
            //根据图片路径显示图片
            displayImage(imagePath);
        }
    }

    /**
     * 根据图片路径显示图片的方法
     */
    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            i2.setImageBitmap(bitmap);
        } else {
            Toast.makeText(MainCamera.this, "获取失败", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 通过uri和selection来获取真实的图片路径
     */
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
}
//====================================================================================================================================

//
//
//
//
//
//



