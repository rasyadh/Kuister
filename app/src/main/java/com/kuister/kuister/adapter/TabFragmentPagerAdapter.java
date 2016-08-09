package com.kuister.kuister.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.kuister.kuister.fragment.AturWaktuKuisFragment;
import com.kuister.kuister.fragment.KuisSekarangFragment;

/**
 * Created by Rasyadh A Aziz on 13/07/2016.
 */
public class TabFragmentPagerAdapter extends FragmentPagerAdapter {

    String[] title = new String[]{
            "Kuis Sekarang", "Atur Waktu Kuis"
    };

    public TabFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new KuisSekarangFragment();
                break;
            case 1:
                fragment = new AturWaktuKuisFragment();
                break;
            default:
                fragment = null;
                break;
        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }

    @Override
    public int getCount() {
        return title.length;
    }
}

