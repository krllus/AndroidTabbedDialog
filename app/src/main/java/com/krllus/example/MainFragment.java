package com.krllus.example;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.krllus.tabdialog.fragment.PageFragment;
import com.krllus.tabdialog.fragment.TabDialogFragment;
import com.krllus.tabdialog.iface.ISimpleDialogListener;
import com.krllus.tabdialog.iface.ViewPagerAdapterInterface;
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
        TabDialogFragment.createBuilder(getContext(), getFragmentManager())
                .setTitle("Fragment Title")
                .setSubTitle("Using fragments is really cool")
                .setListener(new ViewPagerAdapterInterface() {
                    @Override
                    public CharSequence getTitle(int position) {
                        return null;
                    }

                    @Override
                    public Fragment getItem(int position) {
                        return null;
                    }

                    @Override
                    public int getCount() {
                        return 0;
                    }
                })
                .setPositiveButtonText("OK")
                .setNegativeButtonText("Cancel")
                .setTargetFragment(this, REQUEST_CODE_FRAGMENT)
                .setRequestCode(REQUEST_CODE_FRAGMENT)
                .show();
    }

    private ArrayList<DialogTabItem> buildTabItens() {
        ArrayList<DialogTabItem> itens = new ArrayList<>();
        itens.add(new DialogTabItem(PageFragment.newInstance(), "Uno"));
        itens.add(new DialogTabItem(PageFragment.newInstance(), "Dos"));
        itens.add(new DialogTabItem(PageFragment.newInstance(), "Cuatro"));
        return itens;
    }


    private void selectedFirstTab(View container) {
        // add view in container for first tab
        View detailRootView = getLayoutInflater().inflate(R.layout.tab_one_layout, (ViewGroup) container);

        TextView txtFirst = detailRootView.findViewById(R.id.textView);
        TextView txtSecond = detailRootView.findViewById(R.id.textView2);
        TextView txtThird = detailRootView.findViewById(R.id.textView3);

        String strOne = "First Sentence";
        String strTwo = "Seconde Line";
        String strThird = "Last Sentence";

        txtFirst.setText(strOne);
        txtSecond.setText(strTwo);
        txtThird.setText(strThird);
    }

    private void selectedSecondTab(View container) {
        // add view in container for first tab
        View detailRootView = getLayoutInflater().inflate(R.layout.tab_simple_text, (ViewGroup) container);
        TextView textView = detailRootView.findViewById(R.id.textView);

        textView.setText(R.string.lorem);
    }

    private void selectedThirdTab(View container) {
        // add view in container for first tab
        View detailRootView = getLayoutInflater().inflate(R.layout.tab_simple_text, (ViewGroup) container);
        TextView textView = detailRootView.findViewById(R.id.textView);
        String third = "shh!! we don't speak about 3!";
        textView.setText(third);
    }

    @Override
    public void onNegativeButtonClicked(int requestCode, Dialog dialog) {
        switch (requestCode) {
            case REQUEST_CODE_FRAGMENT:
                Toast.makeText(getContext(), "Fragment Negative Clicked", Toast.LENGTH_SHORT).show();
                if (dialog != null && dialog.isShowing())
                    dialog.dismiss();
                break;
        }
    }

    @Override
    public void onNeutralButtonClicked(int requestCode, Dialog dialog) {
        switch (requestCode) {
            case REQUEST_CODE_FRAGMENT:
                Toast.makeText(getContext(), "Fragment Neutral Clicked", Toast.LENGTH_SHORT).show();
                if (dialog != null && dialog.isShowing())
                    dialog.dismiss();
                break;
        }
    }

    @Override
    public void onPositiveButtonClicked(int requestCode, Dialog dialog) {
        switch (requestCode) {
            case REQUEST_CODE_FRAGMENT:
                Toast.makeText(getContext(), "Fragment Positive Clicked", Toast.LENGTH_SHORT).show();
                if (dialog != null && dialog.isShowing())
                    dialog.dismiss();
                break;
        }
    }
}
