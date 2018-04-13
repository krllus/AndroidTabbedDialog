package com.krllus.tabdialog.fragment;

import android.app.Dialog;
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
import com.krllus.tabdialog.iface.ISimpleDialogListener;

import java.util.Locale;
import java.util.Random;

/**
 * Created by João Carlos on 4/12/18.
 * joaocarlusferrera at gmail
 */
public class PageFragment
        extends Fragment
        implements ISimpleDialogListener {

    public static PageFragment newInstance() {
        return new PageFragment();
    }

    private TextView txtView;
    private int randomNumber;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Random random = new Random();
        randomNumber = random.nextInt(100) + 1;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tdl_fragment_container, container, false);

        ViewGroup group = (ViewGroup) rootView;

        txtView = new TextView(rootView.getContext());
        group.addView(txtView);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateUI();
    }

    private void updateUI() {
        randomNumber = randomNumber * 5 / 3;
        String txt = String.format(Locale.getDefault(), "Your luck number: %d", randomNumber);
        txtView.setText(txt);
    }

    @Override
    public void onNegativeButtonClicked(int requestCode, Dialog dialog) {
        updateUI();
        Log.d("PageFragment", "Negative R Code: " + requestCode);
    }

    @Override
    public void onNeutralButtonClicked(int requestCode, Dialog dialog) {
        updateUI();
        Log.d("PageFragment", "Neutral R Code: " + requestCode);
    }

    @Override
    public void onPositiveButtonClicked(int requestCode, Dialog dialog) {
        updateUI();
        Log.d("PageFragment", "Positive R Code: " + requestCode);
    }
}
