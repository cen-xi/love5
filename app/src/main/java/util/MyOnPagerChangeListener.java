package util;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.TextView;

import com.example.love5.ViewPaper1Activity;

public class MyOnPagerChangeListener implements ViewPager.OnPageChangeListener {

/////////////////////////////////////////////////////////////////
    //自定义接口
    private MyClickListener listener;
    //映射方法，使得这里调用活动里的方法
    public interface MyClickListener{
        void turnColor(int afterpage);
    }
    //活动绑定的接口方法，参数为活动上下文
    public void setMyClickListener(MyClickListener listener) {
        this.listener = listener;
    }

  //////////////////////////////////////////////////////////////


    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub
        Log.d("ViewPaper1Activity","====11111========="+arg0);


    }



    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

        // TODO Auto-generated method stub
        Log.d("ViewPaper1Activity","====22222========="+arg0+"==="+arg1+"===="+arg2);




    }


    //刚滑倒的新页面下标
    @Override
    public void onPageSelected(int position) {

        Log.d("ViewPaper1Activity","======33333======"+position);

        listener.turnColor(position);


    }

}
