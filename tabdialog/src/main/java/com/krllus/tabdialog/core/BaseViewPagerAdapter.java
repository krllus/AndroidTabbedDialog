package com.krllus.tabdialog.core;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.krllus.tabdialog.iface.ViewPagerAdapterInterface;


public class BaseViewPagerAdapter
        extends FragmentPagerAdapter {

    private ViewPagerAdapterInterface listener;

    public BaseViewPagerAdapter(FragmentManager fm, ViewPagerAdapterInterface listener) {
        super(fm);
        this.listener = listener;
    }

    @Override
    public Fragment getItem(int position) {
        if (listener != null)
            return listener.getItem(position);
        else
            return null;
    }

    @Override
    public int getCount() {
        if (listener != null)
            return listener.getCount();
        else
            return 0;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (listener != null)
            return listener.getTitle(position);
        else
            return "";
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // hack. we don't want to destroy our fragments and re-initiate them after
    }
}
