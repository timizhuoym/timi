package com.qf.damobobo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by TimiZhuo on 2016/12/5.
 */
public class MyViewPagerAdapter extends FragmentStatePagerAdapter {
    List<Fragment>data;
    public MyViewPagerAdapter(FragmentManager fm,List<Fragment>data) {
        super(fm);
        this.data=data;
    }

    @Override
    public Fragment getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getCount() {
        return data.size();
    }
}
