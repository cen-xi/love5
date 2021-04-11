package util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.love5.R;

import java.util.List;


//自定义view适配器
public class PhotoAdaper extends ArrayAdapter<Photo> {
    private int resourceid;

    //三个参数分别是当前监视后台名，自定义的单一组件页面文件路径返回的一串数字，数据list集合，含有图片路径数字串和文字)
    public PhotoAdaper(Context context, int textviewResourceid, List<Photo> objects) {
        super(context, textviewResourceid, objects);
        resourceid = textviewResourceid;
    }


//    //这里是处理每个小组件的地方，集中处理，一起输出
//    public View getView(int position, View converView, ViewGroup parent) {
//        Photo photo = getItem(position);//获取当前的photo实列
//
//      // // //原始版
////     // // //最简单的原生放方式，效率比较低
////    // //   View view= LayoutInflater.from(getContext()).inflate(resourceid,parent,false);
//
//        //改进版
//        //converView为缓存，若缓存有内容，则不再重新获取，效率相对比原生的高效一点
//        View view;
//        if (converView == null) {
//            view = LayoutInflater.from(getContext()).inflate(resourceid, parent, false);
//        } else {
//            view = converView;
//        }
//
//
//        //自定义的单一组件页面的组件id
//        ImageView photoImage = view.findViewById(R.id.imagekkk);
//        TextView photoname = view.findViewById(R.id.photoname);
//        photoImage.setImageResource(photo.getImageid());
//        photoname.setText(photo.getName());
//        return view;
//    }


    //    //最终改进版
    public View getView(int position, View converView, ViewGroup parent) {
        Photo photo = getItem(position);//获取当前的photo实列
        View view;
        ViewHolder viewHolder;
        if (converView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceid, parent, false);
            viewHolder =new ViewHolder();
            viewHolder.photoImage =view.findViewById(R.id.imagekkk);
            viewHolder.photoname = view.findViewById(R.id.photoname);
            view.setTag(viewHolder);//将viewHolder存储在view中

        } else {
            view = converView;
            viewHolder=(ViewHolder)view.getTag();
        }
        viewHolder.photoImage.setImageResource(photo.getImageid());
        viewHolder.photoname.setText(photo.getName());
        return view;
    }

    class ViewHolder{
        ImageView photoImage;
        TextView photoname;
    }
}
