package com.krllus.tabdialog.iface;

import android.support.v4.app.Fragment;

import java.io.Serializable;

/**
 * Created by João Carlos on 4/12/18.
 * Biox Pecuária Moderna
 * desenvolvedorberrante@bioxbr.com
 */
public interface IViewPagerAdapterInterface extends Serializable {
    CharSequence getTitle(int position);

    Fragment getItem(int position);

    int getCount();
}
