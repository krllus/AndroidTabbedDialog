package com.androidifygeeks.library.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidifygeeks.library.R;
import com.androidifygeeks.library.iface.IFragmentListener;

/**
 * Created by b_ashish on 17-Mar-16.
 * <p/>
 * this class can be invoke from activity or either from fragment
 */
public class PageFragment extends Fragment {

    private String mContentDescription = null;
    private View mRoot = null;

    public static final String ARG_DAY_INDEX = "com.androidifygeeks.library.ARG_DAY_INDEX";
    public static final String PARENT_TAG = "com.androidifygeeks.library.PARENT_TAG";


    public static PageFragment newInstance(int position, String parentTag) {
        PageFragment frag = new PageFragment();
        Bundle args = new Bundle();
        args.putInt(PageFragment.ARG_DAY_INDEX, position);
        args.putString(PageFragment.PARENT_TAG, parentTag);
        frag.setArguments(args);
        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.tdl_fragment_container, container, false);
        if (mContentDescription != null) {
            mRoot.setContentDescription(mContentDescription);
        }
        return mRoot;
    }

    public void setContentDescription(String desc) {
        mContentDescription = desc;
        if (mRoot != null) {
            mRoot.setContentDescription(mContentDescription);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getActivity() instanceof IFragmentListener) {
            ((IFragmentListener) getActivity()).onFragmentViewCreated(this);
        } else {
            Fragment parentFragment;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                parentFragment = getParentFragment();
            } else {
                // Collect the tag from the arguments
                String tag = getArguments().getString(PARENT_TAG);
                // Use the tag to get the parentFragment from the activity, which is (conveniently) available in onAttach()
                parentFragment = getActivity().getSupportFragmentManager().findFragmentByTag(tag);
            }

            if (parentFragment instanceof IFragmentListener) {
                ((IFragmentListener) parentFragment).onFragmentViewCreated(this);
            }
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() instanceof IFragmentListener) {
            ((IFragmentListener) getActivity()).onFragmentAttached(this);
        } else {
            Fragment parentFragment;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                parentFragment = getParentFragment();
            } else {
                // Collect the tag from the arguments
                String tag = getArguments().getString(PARENT_TAG);
                // Use the tag to get the parentFragment from the activity, which is (conveniently) available in onAttach()
                parentFragment = getActivity().getSupportFragmentManager().findFragmentByTag(tag);
            }

            if (parentFragment instanceof IFragmentListener) {
                ((IFragmentListener) parentFragment).onFragmentAttached(this);
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (getActivity() instanceof IFragmentListener) {
            ((IFragmentListener) getActivity()).onFragmentDetached(this);
        } else {
            Fragment parentFragment;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                parentFragment = getParentFragment();
            } else {
                // Collect the tag from the arguments
                String tag = getArguments().getString(PARENT_TAG);
                // Use the tag to get the parentFragment from the activity, which is (conveniently) available in onAttach()
                parentFragment = getActivity().getSupportFragmentManager().findFragmentByTag(tag);
            }

            if (parentFragment instanceof IFragmentListener) {
                ((IFragmentListener) parentFragment).onFragmentDetached(this);
            }
        }
    }

}
