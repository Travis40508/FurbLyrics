package com.tressler.travistressler.lyricsfurb.Util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by travistressler on 11/2/17.
 */

public class MainViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;

    public MainViewPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {

        }
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }
}
