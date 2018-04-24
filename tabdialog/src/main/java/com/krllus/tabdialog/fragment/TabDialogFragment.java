package com.krllus.tabdialog.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.DimenRes;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.View;

import com.krllus.tabdialog.R;
import com.krllus.tabdialog.core.BaseDialogBuilder;
import com.krllus.tabdialog.core.BaseDialogFragment;
import com.krllus.tabdialog.core.BaseViewPagerAdapter;
import com.krllus.tabdialog.iface.INegativeButtonDialogListener;
import com.krllus.tabdialog.iface.INeutralButtonDialogListener;
import com.krllus.tabdialog.iface.IPositiveButtonDialogListener;
import com.krllus.tabdialog.iface.IViewPagerAdapterInterface;

import java.util.List;

public class TabDialogFragment extends BaseDialogFragment {

    protected final static String ARG_TITLE = "title";
    protected final static String ARG_SUB_TITLE = "sub_title";
    protected final static String ARG_CONTENT_HEIGHT_MAX_SIZE = "content_height_max_size";
    protected final static String ARG_POSITIVE_BUTTON = "positive_button";
    protected final static String ARG_NEGATIVE_BUTTON = "negative_button";
    protected final static String ARG_NEUTRAL_BUTTON = "neutral_button";
    protected final static String ARG_SHOW_BOTTOM_DIVIDER = "use_bottom_divider";


    public static TabDialogBuilder createBuilder(Context context, FragmentManager fragmentManager) {
        return new TabDialogBuilder(context, fragmentManager, TabDialogFragment.class, null);
    }

    public static TabDialogBuilder createBuilder(Context context, FragmentManager fragmentManager, IViewPagerAdapterInterface listener) {
        return new TabDialogBuilder(context, fragmentManager, TabDialogFragment.class, listener);
    }

    @Override
    protected BaseDialogFragment.Builder build(BaseDialogFragment.Builder builder) {
        final CharSequence title = getTitle();
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }

        final CharSequence subTitle = getSubTitle();
        if (!TextUtils.isEmpty(subTitle)) {
            builder.setSubTitle(subTitle);
        }

        final boolean showBottomDivider = getShowBottomDivider();
        if (showBottomDivider)
            builder.showBottomDivider();

        final CharSequence positiveButtonText = getPositiveButtonText();
        if (!TextUtils.isEmpty(positiveButtonText)) {
            builder.setPositiveButton(positiveButtonText, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (IPositiveButtonDialogListener listener : getPositiveButtonDialogListeners()) {
                        listener.onPositiveButtonClicked(mRequestCode, getDialog());
                    }
                }
            });
        }

        final CharSequence negativeButtonText = getNegativeButtonText();
        if (!TextUtils.isEmpty(negativeButtonText)) {
            builder.setNegativeButton(negativeButtonText, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (INegativeButtonDialogListener listener : getNegativeButtonDialogListeners()) {
                        listener.onNegativeButtonClicked(mRequestCode, getDialog());
                    }
                }
            });
        }

        final CharSequence neutralButtonText = getNeutralButtonText();
        if (!TextUtils.isEmpty(neutralButtonText)) {
            builder.setNeutralButton(neutralButtonText, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (INeutralButtonDialogListener listener : getNeutralButtonDialogListeners()) {
                        listener.onNeutralButtonClicked(mRequestCode, getDialog());
                    }
                }
            });
        }

        int resId = getContentHeightMaxSize();
        int contentHeightMaxSize;
        if (resId == 0)
            contentHeightMaxSize = getResources().getDimensionPixelSize(R.dimen.dialog_content_heigth_max_size);
        else
            contentHeightMaxSize = getResources().getDimensionPixelSize(getContentHeightMaxSize());
        builder.setContentHeightMaxSize(contentHeightMaxSize);

        if (getViewPagerListener() != null && getViewPagerListener().getCount() > 0) {
            buildTab(builder);
        }

        return builder;
    }

    private void buildTab(final Builder builder) {
        builder.setTabItems(prepareAdapter());
    }

    private BaseViewPagerAdapter prepareAdapter() {
        return new BaseViewPagerAdapter(getChildFragmentManager(), getViewPagerListener());
    }

    protected CharSequence getTitle() {
        return getArguments().getCharSequence(ARG_TITLE);
    }

    protected CharSequence getSubTitle() {
        return getArguments().getCharSequence(ARG_SUB_TITLE);
    }

    protected Boolean getShowBottomDivider() {
        return getArguments().getBoolean(ARG_SHOW_BOTTOM_DIVIDER, false);
    }

    protected CharSequence getPositiveButtonText() {
        return getArguments().getCharSequence(ARG_POSITIVE_BUTTON);
    }

    protected CharSequence getNegativeButtonText() {
        return getArguments().getCharSequence(ARG_NEGATIVE_BUTTON);
    }

    protected CharSequence getNeutralButtonText() {
        return getArguments().getCharSequence(ARG_NEUTRAL_BUTTON);
    }

    protected int getContentHeightMaxSize() {
        return getArguments().getInt(ARG_CONTENT_HEIGHT_MAX_SIZE);
    }

    /**
     * Get positive button dialog listeners.
     * There might be more than one listener.
     *
     * @return Dialog listeners
     * @since 2.1.0
     */
    protected List<IPositiveButtonDialogListener> getPositiveButtonDialogListeners() {
        return getDialogListeners(IPositiveButtonDialogListener.class);
    }

    /**
     * Get negative button dialog listeners.
     * There might be more than one listener.
     *
     * @return Dialog listeners
     * @since 2.1.0
     */
    protected List<INegativeButtonDialogListener> getNegativeButtonDialogListeners() {
        return getDialogListeners(INegativeButtonDialogListener.class);
    }

    /**
     * Get neutral button dialog listeners.
     * There might be more than one listener.
     *
     * @return Dialog listeners
     * @since 2.1.0
     */
    protected List<INeutralButtonDialogListener> getNeutralButtonDialogListeners() {
        return getDialogListeners(INeutralButtonDialogListener.class);
    }


    public static class TabDialogBuilder extends BaseDialogBuilder {
        private CharSequence mTitle;
        private CharSequence mSubTitle;
        private boolean mShowBottomDivider = false;
        private CharSequence mPositiveButtonText;
        private CharSequence mNegativeButtonText;
        private CharSequence mNeutralButtonText;
        private int mContentHeightMaxSize;

        TabDialogBuilder(Context context, FragmentManager fragmentManager, Class<? extends BaseDialogFragment> clazz, IViewPagerAdapterInterface listener) {
            super(context, fragmentManager, clazz, listener);
        }

        @Override
        protected TabDialogBuilder self() {
            return this;
        }

        public TabDialogBuilder setTitle(int titleResourceId) {
            mTitle = mContext.getString(titleResourceId);
            return this;
        }

        public TabDialogBuilder setTitle(CharSequence title) {
            mTitle = title;
            return this;
        }

        public TabDialogBuilder setSubTitle(int subTitleResourceId) {
            mSubTitle = mContext.getString(subTitleResourceId);
            return this;
        }

        public TabDialogBuilder setSubTitle(CharSequence subTitle) {
            mSubTitle = subTitle;
            return this;
        }

        public TabDialogBuilder setContentHeightMaxSize(@DimenRes int contentHeightMaxSize) {
            mContentHeightMaxSize = contentHeightMaxSize;
            return this;
        }

        public TabDialogBuilder showBottomDivider() {
            mShowBottomDivider = true;
            return this;
        }

        public TabDialogBuilder setPositiveButtonText(int textResourceId) {
            mPositiveButtonText = mContext.getString(textResourceId);
            return this;
        }

        public TabDialogBuilder setPositiveButtonText(CharSequence text) {
            mPositiveButtonText = text;
            return this;
        }

        public TabDialogBuilder setNegativeButtonText(int textResourceId) {
            mNegativeButtonText = mContext.getString(textResourceId);
            return this;
        }

        public TabDialogBuilder setNegativeButtonText(CharSequence text) {
            mNegativeButtonText = text;
            return this;
        }

        public TabDialogBuilder setNeutralButtonText(int textResourceId) {
            mNeutralButtonText = mContext.getString(textResourceId);
            return this;
        }

        public TabDialogBuilder setNeutralButtonText(CharSequence text) {
            mNeutralButtonText = text;
            return this;
        }

        @Override
        protected Bundle prepareArguments() {
            Bundle args = new Bundle();
            args.putCharSequence(TabDialogFragment.ARG_TITLE, mTitle);
            args.putCharSequence(TabDialogFragment.ARG_SUB_TITLE, mSubTitle);
            args.putBoolean(TabDialogFragment.ARG_SHOW_BOTTOM_DIVIDER, mShowBottomDivider);
            args.putCharSequence(TabDialogFragment.ARG_POSITIVE_BUTTON, mPositiveButtonText);
            args.putCharSequence(TabDialogFragment.ARG_NEGATIVE_BUTTON, mNegativeButtonText);
            args.putCharSequence(TabDialogFragment.ARG_NEUTRAL_BUTTON, mNeutralButtonText);
            args.putInt(TabDialogFragment.ARG_CONTENT_HEIGHT_MAX_SIZE, mContentHeightMaxSize);

            return args;
        }
    }
}
