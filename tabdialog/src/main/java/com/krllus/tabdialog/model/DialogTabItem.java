package com.krllus.tabdialog.model;

import android.support.v4.app.Fragment;

import java.io.Serializable;

/**
 * Created by João Carlos on 4/12/18.
 * joaocarlusferrera at gmail
 */
public class DialogTabItem implements Serializable {
    private final Fragment fragment;
    private final String title;

    public DialogTabItem(Fragment fragment, String title) {
        this.fragment = fragment;
        this.title = title;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public String getTitle() {
        return title;
    }
}
