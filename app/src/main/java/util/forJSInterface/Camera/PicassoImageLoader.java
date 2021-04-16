//package util.forJSInterface.Camera;
//
//import android.app.Activity;
//import android.net.Uri;
//import android.os.Bundle;
//import android.widget.ImageView;
//
//import com.example.love5.R;
//import com.lzy.imagepicker.ImagePicker;
//import com.lzy.imagepicker.loader.ImageLoader;
//import com.lzy.imagepicker.view.CropImageView;
//import com.squareup.picasso.MemoryPolicy;
//import com.squareup.picasso.Picasso;
//
//import java.io.File;
//
//public class PicassoImageLoader implements ImageLoader {
//    @Override
//    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
//        Picasso.with(activity)
//                .load(Uri.fromFile(new File(path)))
////                .placeholder(R.mipmap.default_image)
////                .error(R.mipmap.default_image)
////                .resize(width, height)
////                .centerInside()
////                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)//
//                .into(imageView);
//    }
//
//    @Override
//    public void displayImagePreview(Activity activity, String path, ImageView imageView, int width, int height) {
//
//    }
//
//    @Override
//    public void clearMemoryCache() {
//        //这里是清除缓存的方法,根据需要自己实现
//    }
//
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_image_picker);
////
////
////    }
//
//}
