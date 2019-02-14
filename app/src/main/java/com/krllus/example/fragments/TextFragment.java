package com.krllus.example.fragments;

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

import com.krllus.example.R;
import com.krllus.tabdialog.iface.INegativeButtonDialogListener;
import com.krllus.tabdialog.iface.IPositiveButtonDialogListener;


public class TextFragment
        extends Fragment
        implements IPositiveButtonDialogListener, INegativeButtonDialogListener {

    private static final String ARG_PARAM1 = "param1";

    private TextView txtSimpleText;
    private String mParam1;

    public TextFragment() {
        // Required empty public constructor
    }

    public static TextFragment newInstance(String param1) {
        TextFragment fragment = new TextFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_text, container, false);

        txtSimpleText = rootView.findViewById(R.id.txt_simple_text);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateUI();
    }

    @Override
    public void onNegativeButtonClicked(int requestCode, Dialog dialog) {
        Log.d(TextFragment.class.getSimpleName(), "Negative Button Clicked request code: " + requestCode);
    }

    @Override
    public void onPositiveButtonClicked(int requestCode, Dialog dialog) {
        Log.d(TextFragment.class.getSimpleName(), "Positive Button Clicked request code: " + requestCode);
    }

    private void updateUI() {
        txtSimpleText.setText(mParam1);
    }


}
