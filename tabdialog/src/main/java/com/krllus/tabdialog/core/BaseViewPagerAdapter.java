package com.krllus.tabdialog.core;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.krllus.tabdialog.iface.IViewPagerAdapterInterface;

/**
 * Created by João Carlos on 4/12/18.
 * joaocarlusferrera at gmail
 */
public class BaseViewPagerAdapter
        extends FragmentPagerAdapter {

    private IViewPagerAdapterInterface listener;

    public BaseViewPagerAdapter(FragmentManager fm, IViewPagerAdapterInterface listener) {
        super(fm);
        this.listener = listener;
    }

    @Override
    public Fragment getItem(int position) {
        return listener != null ? listener.getItem(position) : null;
    }

    @Override
    public int getCount() {
        return listener != null ? listener.getCount() : 0;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return listener != null ? listener.getTitle(position) : null;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        // hack. we don't want to destroy our fragments and re-initiate them after
    }
}
