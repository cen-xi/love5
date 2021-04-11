package util;

import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.example.love5.ViewPaper1Activity;

import java.util.ArrayList;

public class PagerAdaper extends PagerAdapter {

    private ArrayList<View> viewArrayList;


    //接收参数
    public PagerAdaper(ArrayList<View> list) {
       viewArrayList = list;
    }


    @Override

    public boolean isViewFromObject(View arg0, Object arg1) {

        // TODO Auto-generated method stub

        return arg0 == arg1;

    }



    //getCount()，返回滑动的View的个数。
    @Override

    public int getCount() {

        // TODO Auto-generated method stub

        return viewArrayList.size();

    }



    //destroyItem,从容器中删除指定position的View
    @Override

    public void destroyItem(ViewGroup container, int position,

                            Object object) {

        // TODO Auto-generated method stub

        container.removeView(viewArrayList.get(position));

    }



    //instantiateItem（）方法中，我先讲指定position位置的View添加到容器中，末了，将本View返回。
    @Override

    public Object instantiateItem(ViewGroup container, int position) {

        // TODO Auto-generated method stub

        container.addView(viewArrayList.get(position));





        return viewArrayList.get(position);

    }

}
