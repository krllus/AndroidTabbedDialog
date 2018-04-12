package com.krllus.tabdialog.model;

import android.support.v4.app.Fragment;

import com.krllus.tabdialog.iface.ISimpleDialogListener;

import java.io.Serializable;

/**
 * Created by João Carlos on 4/12/18.
 * joaocarlusferrera at gmail
 */
public class DialogTabItem implements Serializable {
    private final Fragment fragmentWeakReference;
    private final String title;

    public DialogTabItem(Fragment fragment, String title) {
        if (!(fragment instanceof ISimpleDialogListener)) {
            throw new RequiredInterfaceImplementationException();
        }
        //this.fragmentWeakReference = new WeakReference<>(fragment);
        this.fragmentWeakReference = fragment;
        this.title = title;
    }

    public Fragment getFragment() {
        return fragmentWeakReference;
    }

    public String getTitle() {
        return title;
    }

    private class RequiredInterfaceImplementationException extends RuntimeException {
        RequiredInterfaceImplementationException() {
            super("Is required that the fragment implement ISimpleDialogListener");
        }
    }

}
