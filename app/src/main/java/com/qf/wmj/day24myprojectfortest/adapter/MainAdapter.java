package com.qf.wmj.day24myprojectfortest.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by JB on 2016/10/12.
 */
public class MainAdapter extends FragmentPagerAdapter{
    private ArrayList<Fragment> list;
    public MainAdapter(FragmentManager fm,ArrayList<Fragment>list) {
        super(fm);
        this.list=list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
