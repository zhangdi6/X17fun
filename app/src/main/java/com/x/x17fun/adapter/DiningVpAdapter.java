package com.x.x17fun.adapter;

/* Created by AdScar
    on 2020/10/11.
      */

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.x.x17fun.ui.fragment.DiningFragment;

import java.util.ArrayList;

public class DiningVpAdapter extends FragmentPagerAdapter {

    private final ArrayList<DiningFragment> object;

    public DiningVpAdapter(FragmentManager fm , ArrayList<DiningFragment> object) {
        super(fm);
        this.object = object;
    }

    @Override
    public Fragment getItem(int i) {
        return object.get(i);
    }

    @Override
    public int getCount() {
        return object.size();
    }
}
