package com.krllus.tabdialog.core;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.krllus.tabdialog.R;
import com.krllus.tabdialog.custom.WrapContentHeightViewPager;
import com.krllus.tabdialog.iface.ISimpleDialogCancelListener;
import com.krllus.tabdialog.iface.IViewPagerAdapterInterface;
import com.krllus.tabdialog.util.TypefaceHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class BaseDialogFragment extends DialogFragment {

    protected int mRequestCode;
    private IViewPagerAdapterInterface listener;

    public IViewPagerAdapterInterface getViewPagerListener() {
        return listener;
    }

    public void setViewPagerListener(IViewPagerAdapterInterface listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());

        Bundle args = getArguments();
        if (args != null) {
            dialog.setCanceledOnTouchOutside(
                    args.getBoolean(BaseDialogBuilder.ARG_CANCELABLE_ON_TOUCH_OUTSIDE));
        }
        /*
         * disable the actual title of a dialog cause custom dialog title is rendering through custom layout
         */
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Builder builder = new Builder(getActivity(), inflater, container);
        return build(builder).create();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final Fragment targetFragment = getTargetFragment();
        if (targetFragment != null) {
            mRequestCode = getTargetRequestCode();
        } else {
            Bundle args = getArguments();
            if (args != null) {
                mRequestCode = args.getInt(BaseDialogBuilder.ARG_REQUEST_CODE, 0);
            }
        }
    }

    @Override
    public void onDestroyView() {
        // bug in the compatibility library
        if (getDialog() != null && getRetainInstance()) {
            getDialog().setDismissMessage(null);
        }
        super.onDestroyView();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        for (ISimpleDialogCancelListener listener : getCancelListeners()) {
            listener.onCancelled(mRequestCode);
        }
    }

    /**
     * Get dialog cancel listeners.
     * There might be more than one cancel listener.
     *
     * @return Dialog cancel listeners
     * @since 2.1.0
     */
    protected List<ISimpleDialogCancelListener> getCancelListeners() {
        return getDialogListeners(ISimpleDialogCancelListener.class);
    }

    /**
     * Utility method for acquiring all listeners of some type for current instance of DialogFragment
     *
     * @param listenerInterface Interface of the desired listeners
     * @return Unmodifiable list of listeners
     * @since 2.1.0
     */
    @SuppressWarnings("unchecked")
    protected <T> List<T> getDialogListeners(Class<T> listenerInterface) {
        final Fragment targetFragment = getTargetFragment();
        List<T> listeners = new ArrayList<>();
        for (Fragment f : getChildFragmentManager().getFragments()) {
            if (listenerInterface.isAssignableFrom(f.getClass()))
                listeners.add((T) f);
        }

        if (targetFragment != null && listenerInterface.isAssignableFrom(targetFragment.getClass())) {
            listeners.add((T) targetFragment);
        }
        if (getActivity() != null && listenerInterface.isAssignableFrom(getActivity().getClass())) {
            listeners.add((T) getActivity());
        }
        return Collections.unmodifiableList(listeners);
    }

    public void showAllowingStateLoss(FragmentManager manager, String tag) {
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(this, tag);
        ft.commitAllowingStateLoss();
    }

    private boolean isScrollable(ViewGroup listView) {
        int totalHeight = 0;
        for (int i = 0; i < listView.getChildCount(); i++) {
            totalHeight += listView.getChildAt(i).getMeasuredHeight();
        }
        return listView.getMeasuredHeight() < totalHeight;
    }

    /**
     * Key method for using { com.avast.android.dialogs.core.BaseDialogFragment}.
     * Customized dialogs need to be set up via provided builder.
     *
     * @param initialBuilder Provided builder for setting up customized dialog
     * @return Updated builder
     */
    protected abstract Builder build(Builder initialBuilder);

    /**
     * Custom dialog builder
     */
    protected static class Builder {

        private final Context mContext;

        private final ViewGroup mContainer;
        private final LayoutInflater mInflater;

        private CharSequence mTitle = null;
        private CharSequence mSubTitle = null;

        private CharSequence mPositiveButtonText;
        private CharSequence mNegativeButtonText;
        private CharSequence mNeutralButtonText;

        private View.OnClickListener mNegativeButtonListener;
        private View.OnClickListener mPositiveButtonListener;
        private View.OnClickListener mNeutralButtonListener;

        private View mCustomView;
        private ListAdapter mListAdapter;

        private WrapContentHeightViewPager viewPager;
        private BaseViewPagerAdapter viewPagerAdapter;

        private int mListCheckedItemIdx;
        private int mChoiceMode;
        private int[] mListCheckedItemMultipleIds;

        private AdapterView.OnItemClickListener mOnItemClickListener;

        private int contentHeight;

        Builder(Context context, LayoutInflater inflater, ViewGroup container) {
            this.mContext = context;
            this.mContainer = container;
            this.mInflater = inflater;
        }

        public LayoutInflater getLayoutInflater() {
            return mInflater;
        }

        public Builder setContentPaneHeight(int height) {
            this.contentHeight = height;
            return this;
        }

        public Builder setTitle(int titleId) {
            this.mTitle = mContext.getText(titleId);
            return this;
        }

        public Builder setTitle(CharSequence title) {
            this.mTitle = title;
            return this;
        }

        public Builder setSubTitle(int subTitleId) {
            this.mSubTitle = mContext.getText(subTitleId);
            return this;
        }

        public Builder setSubTitle(CharSequence subTitle) {
            this.mSubTitle = subTitle;
            return this;
        }

        public Builder setPositiveButton(int textId, final View.OnClickListener listener) {
            mPositiveButtonText = mContext.getText(textId);
            mPositiveButtonListener = listener;
            return this;
        }

        public Builder setPositiveButton(CharSequence text, final View.OnClickListener listener) {
            mPositiveButtonText = text;
            mPositiveButtonListener = listener;
            return this;
        }

        public Builder setNegativeButton(int textId, final View.OnClickListener listener) {
            mNegativeButtonText = mContext.getText(textId);
            mNegativeButtonListener = listener;
            return this;
        }

        public Builder setNegativeButton(CharSequence text, final View.OnClickListener listener) {
            mNegativeButtonText = text;
            mNegativeButtonListener = listener;
            return this;
        }

        public Builder setNeutralButton(int textId, final View.OnClickListener listener) {
            mNeutralButtonText = mContext.getText(textId);
            mNeutralButtonListener = listener;
            return this;
        }

        public Builder setNeutralButton(CharSequence text, final View.OnClickListener listener) {
            mNeutralButtonText = text;
            mNeutralButtonListener = listener;
            return this;
        }

        public Builder setItems(ListAdapter listAdapter, int[] checkedItemIds, int choiceMode, final AdapterView.OnItemClickListener listener) {
            mListAdapter = listAdapter;
            mListCheckedItemMultipleIds = checkedItemIds;
            mOnItemClickListener = listener;
            mChoiceMode = choiceMode;
            mListCheckedItemIdx = -1;
            return this;
        }

        public Builder setTabItems(BaseViewPagerAdapter adapter) {
            viewPagerAdapter = adapter;
            return this;
        }

        /**
         * Set list
         *
         * @param checkedItemIdx Item check by default, -1 if no item should be checked
         */
        public Builder setItems(ListAdapter listAdapter, int checkedItemIdx,
                                final AdapterView.OnItemClickListener listener) {
            mListAdapter = listAdapter;
            mOnItemClickListener = listener;
            mListCheckedItemIdx = checkedItemIdx;
            mChoiceMode = AbsListView.CHOICE_MODE_NONE;
            return this;
        }

        public Builder setView(View view) {
            mCustomView = view;
            return this;
        }

        View create() {

            final LinearLayout content = (LinearLayout) mInflater.inflate(R.layout.tdl_dialog, mContainer, false);
            TextView vTitle = content.findViewById(R.id.tdl_title_text);
            TextView vSubTitle = content.findViewById(R.id.tdl_subtitle_text);

            viewPager = content.findViewById(R.id.view_pager);
            //setContentHeight();

            final TabLayout vTabLayout = content.findViewById(R.id.tab_layout);

            Button vPositiveButton = content.findViewById(R.id.tdl_button_positive);
            Button vNegativeButton = content.findViewById(R.id.tdl_button_negative);
            Button vNeutralButton = content.findViewById(R.id.tdl_button_neutral);

            Typeface regularFont = TypefaceHelper.get(mContext, "Roboto-Regular");
            Typeface mediumFont = TypefaceHelper.get(mContext, "Roboto-Medium");

            set(vTitle, mTitle, mediumFont);
            set(vSubTitle, mSubTitle, regularFont);

            if (viewPagerAdapter != null) {
                viewPager.setAdapter(viewPagerAdapter);
                vTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        viewPager.setCurrentItem(tab.getPosition(), true);
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });
                viewPager.setPageMargin(mContext.getResources().getDimensionPixelSize(R.dimen.my_tab_view_page_margin));
                viewPager.setPageMarginDrawable(R.drawable.page_margin);
                vTabLayout.setupWithViewPager(viewPager);
            }

            set(vPositiveButton, mPositiveButtonText, mediumFont, mPositiveButtonListener);
            set(vNegativeButton, mNegativeButtonText, mediumFont, mNegativeButtonListener);
            set(vNeutralButton, mNeutralButtonText, mediumFont, mNeutralButtonListener);

            return content;
        }

        /*
         * defining the height of a content layout
         */
        private void setContentHeight() {
            if (contentHeight <= 0) {
                viewPager.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) mContext.getResources().getDimension(R.dimen.dialog_main_pane_height)));
            } else {
                viewPager.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, contentHeight));
            }
        }

        private boolean shouldStackButtons() {
            return shouldStackButton(mPositiveButtonText) || shouldStackButton(mNegativeButtonText)
                    || shouldStackButton(mNeutralButtonText);
        }

        private boolean shouldStackButton(CharSequence text) {
            final int MAX_BUTTON_CHARS = 12; // based on observation, could be done better with measuring widths
            return text != null && text.length() > MAX_BUTTON_CHARS;
        }

        private void set(Button button, CharSequence text, Typeface font, View.OnClickListener listener) {
            set(button, text, font);
            if (listener != null) {
                button.setOnClickListener(listener);
            }
        }

        private void set(TextView textView, CharSequence text) {
            if (text != null) {
                textView.setText(text);
            } else {
                textView.setVisibility(View.GONE);
            }
        }

        private void set(TextView textView, CharSequence text, Typeface font) {
            if (text != null) {
                textView.setText(text);
                textView.setTypeface(font);
            } else {
                textView.setVisibility(View.GONE);
            }
        }
    }
}
