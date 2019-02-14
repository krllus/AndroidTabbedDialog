package com.krllus.example.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.krllus.example.R;
import com.krllus.tabdialog.fragment.PageFragment;
import com.krllus.tabdialog.fragment.TabDialogFragment;
import com.krllus.tabdialog.iface.ISimpleDialogListener;
import com.krllus.tabdialog.iface.IViewPagerAdapterInterface;
import com.krllus.tabdialog.model.DialogTabItem;

import java.util.ArrayList;


/**
 * Created by João Carlos on 4/11/18.
 * joaocarlusferrera at gmail
 */
public class MainFragment
        extends Fragment
        implements ISimpleDialogListener {

    private static final int REQUEST_CODE_FRAGMENT = 50;

    private ArrayList<DialogTabItem> items;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            default:
                break;
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        Button btn = rootView.findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchDialog();
            }


        });
        return rootView;
    }

    private void launchDialog() {
        TabDialogFragment.createBuilder(getContext(), getFragmentManager(),
                new IViewPagerAdapterInterface() {
                    @Override
                    public CharSequence getTitle(int position) {
                        return retrieveItems().get(position).getTitle();
                    }

                    @Override
                    public Fragment getItem(int position) {
                        return retrieveItems().get(position).getFragment();
                    }

                    @Override
                    public int getCount() {
                        return retrieveItems().size();
                    }
                })
                .setTitle("Fragment Title")
                .setSubTitle("Using fragments is really cool")
                .showBottomDivider()
                .setPositiveButtonText("OK")
                .setNegativeButtonText("Cancel")
                .setTargetFragment(this, REQUEST_CODE_FRAGMENT)
                .setRequestCode(REQUEST_CODE_FRAGMENT)
                .show();
    }

    private ArrayList<DialogTabItem> retrieveItems() {
        if (items == null)
            items = new ArrayList<>();

        if (items.isEmpty()) {
            PageFragment fragmentUno = PageFragment.newInstance();
            PageFragment fragmentTwo = PageFragment.newInstance();
            TextFragment fragmentCuatro = TextFragment.newInstance("Shh, we don't talk about tres");

            items.add(new DialogTabItem(fragmentUno, "Uno"));
            items.add(new DialogTabItem(fragmentTwo, "Dos"));
            items.add(new DialogTabItem(fragmentCuatro, "Cuatro"));
        }

        return items;
    }

    @Override
    public void onNegativeButtonClicked(int requestCode, Dialog dialog) {
        switch (requestCode) {
            case REQUEST_CODE_FRAGMENT:
                Toast.makeText(getContext(), "Fragment Negative Clicked", Toast.LENGTH_SHORT).show();
                if (dialog != null && dialog.isShowing())
                    //dialog.dismiss();
                break;
        }
    }

    @Override
    public void onNeutralButtonClicked(int requestCode, Dialog dialog) {
        switch (requestCode) {
            case REQUEST_CODE_FRAGMENT:
                Toast.makeText(getContext(), "Fragment Neutral Clicked", Toast.LENGTH_SHORT).show();
                if (dialog != null && dialog.isShowing())
                    //dialog.dismiss();
                break;
        }
    }

    @Override
    public void onPositiveButtonClicked(int requestCode, Dialog dialog) {
        switch (requestCode) {
            case REQUEST_CODE_FRAGMENT:
                Toast.makeText(getContext(), "Fragment Positive Clicked", Toast.LENGTH_SHORT).show();
                if (dialog != null && dialog.isShowing())
                    //dialog.dismiss();
                break;
        }
    }
}
