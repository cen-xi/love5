package util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;


/*
//FragmentStatePagerAdapter和FragmentPagerAdapter相比，
//        它更适用于大量页面的展示，
//        当整个fragment不再被访问，
//        则会被销毁（由于预加载，默认最多保存3个fragment），
//        只保存其状态，这相对于FragmentPagerAdapter占有了更少的内存

 */

public class FragAdapter extends FragmentStatePagerAdapter {



    private List<Fragment> mFragments;



    public FragAdapter(FragmentManager fm, List<Fragment> fragments) {

        super(fm);

        // TODO Auto-generated constructor stub

        mFragments=fragments;

    }



    @Override

    public Fragment getItem(int arg0) {

        // TODO Auto-generated method stub

        return mFragments.get(arg0);

    }



    @Override

    public int getCount() {

        // TODO Auto-generated method stub

        return mFragments.size();

    }



}