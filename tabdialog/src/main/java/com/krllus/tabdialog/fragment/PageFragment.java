package com.krllus.tabdialog.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.krllus.tabdialog.R;
import com.krllus.tabdialog.iface.IFragmentListener;
import com.krllus.tabdialog.iface.ISimpleDialogListener;

import java.util.Locale;
import java.util.Random;

public class PageFragment
        extends Fragment
        implements ISimpleDialogListener {

    public static PageFragment newInstance() {
        return new PageFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tdl_fragment_container, container, false);

        ViewGroup group = (ViewGroup) rootView;

        TextView txtView = new TextView(rootView.getContext());

        Random random = new Random();
        int rNum = random.nextInt(100) + 1;
        String txt = String.format(Locale.getDefault(), "Your luck number: %d", rNum);
        txtView.setText(txt);

        group.addView(txtView);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getActivity() instanceof IFragmentListener) {
            ((IFragmentListener) getActivity()).onFragmentViewCreated(this);
        } else {
            Fragment parentFragment;
            parentFragment = getParentFragment();

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
            parentFragment = getParentFragment();

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
            parentFragment = getParentFragment();

            if (parentFragment instanceof IFragmentListener) {
                ((IFragmentListener) parentFragment).onFragmentDetached(this);
            }
        }
    }

    @Override
    public void onNegativeButtonClicked(int requestCode, Dialog dialog) {
        Log.d("PageFragment", "Negative R Code: " + requestCode);
    }

    @Override
    public void onNeutralButtonClicked(int requestCode, Dialog dialog) {
        Log.d("PageFragment", "Neutral R Code: " + requestCode);
    }

    @Override
    public void onPositiveButtonClicked(int requestCode, Dialog dialog) {
        Log.d("PageFragment", "Positive R Code: " + requestCode);
    }
}
