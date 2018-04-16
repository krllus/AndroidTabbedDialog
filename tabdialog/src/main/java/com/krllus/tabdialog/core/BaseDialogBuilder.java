package com.krllus.tabdialog.core;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.StyleRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.krllus.tabdialog.iface.IViewPagerAdapterInterface;

/**
 * Internal base builder that holds common values for all dialog fragment builders.
 * <p/>
 * Created by b_ashish on 21-Mar-16.
 */
public abstract class BaseDialogBuilder<T extends BaseDialogBuilder<T>> {

    public final static int DEFAULT_REQUEST_CODE = -42;
    public final static String DEFAULT_TAG = "simple_dialog";

    public final static String ARG_REQUEST_CODE = "request_code";
    public final static String ARG_CANCELABLE_ON_TOUCH_OUTSIDE = "cancelable_oto";
    public static String ARG_USE_CUSTOM_THEME = "usecustomtheme";
    public static String ARG_USE_DARK_THEME = "usedarktheme";
    public static String ARG_USE_LIGHT_THEME = "uselighttheme";

    protected final Context mContext;
    protected final FragmentManager mFragmentManager;
    protected final Class<? extends BaseDialogFragment> mClass;

    private String mTag = DEFAULT_TAG;
    private int mRequestCode = DEFAULT_REQUEST_CODE;
    private Fragment mTargetFragment;
    private boolean mCancelable = true;
    private boolean mCancelableOnTouchOutside = true;
    private int mUseCustomTheme = 0;
    private boolean mUseDarkTheme = false;
    private boolean mUseLightTheme = false;

    private IViewPagerAdapterInterface mViewPagerListener;

    public BaseDialogBuilder(Context context,
                             FragmentManager fragmentManager,
                             Class<? extends BaseDialogFragment> clazz, IViewPagerAdapterInterface listener) {
        mFragmentManager = fragmentManager;
        mContext = context.getApplicationContext();
        mClass = clazz;
        mViewPagerListener = listener;
    }

    protected abstract T self();

    protected abstract Bundle prepareArguments();

    public T setCancelable(boolean cancelable) {
        mCancelable = cancelable;
        return self();
    }

    public T setCancelableOnTouchOutside(boolean cancelable) {
        mCancelableOnTouchOutside = cancelable;
        if (cancelable) {
            mCancelable = cancelable;
        }
        return self();
    }

    public T setTargetFragment(Fragment fragment, int requestCode) {
        mTargetFragment = fragment;
        mRequestCode = requestCode;
        return self();
    }

    public T setRequestCode(int requestCode) {
        mRequestCode = requestCode;
        return self();
    }

    public T setTag(String tag) {
        mTag = tag;
        return self();
    }

    public T useCustomTheme(@StyleRes int theme) {
        mUseCustomTheme = theme;
        return self();
    }

    /*
    public T useDarkTheme() {
        mUseDarkTheme = true;
        return self();
    }

    public T useLightTheme() {
        mUseLightTheme = true;
        return self();
    }*/

    private BaseDialogFragment create() {
        final Bundle args = prepareArguments();

        final BaseDialogFragment fragment = (BaseDialogFragment) Fragment.instantiate(mContext, mClass.getName(), args);

        args.putBoolean(ARG_CANCELABLE_ON_TOUCH_OUTSIDE, mCancelableOnTouchOutside);
        args.putBoolean(ARG_USE_DARK_THEME, mUseDarkTheme);
        args.putBoolean(ARG_USE_LIGHT_THEME, mUseLightTheme);
        args.putInt(ARG_USE_CUSTOM_THEME, mUseCustomTheme);

        if (mTargetFragment != null) {
            fragment.setTargetFragment(mTargetFragment, mRequestCode);
        } else {
            args.putInt(ARG_REQUEST_CODE, mRequestCode);
        }
        fragment.setCancelable(mCancelable);
        fragment.setViewPagerListener(mViewPagerListener);
        return fragment;
    }

    public DialogFragment show() {
        BaseDialogFragment fragment = create();
        fragment.show(mFragmentManager, mTag);
        return fragment;
    }

    /**
     * Like show() but allows the commit to be executed after an activity's state is saved. This
     * is dangerous because the commit can be lost if the activity needs to later be restored from
     * its state, so this should only be used for cases where it is okay for the UI state to change
     * unexpectedly on the user.
     */
    public DialogFragment showAllowingStateLoss() {
        BaseDialogFragment fragment = create();
        fragment.showAllowingStateLoss(mFragmentManager, mTag);
        return fragment;
    }
}
