package com.krllus.example;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.krllus.tabdialog.fragment.PageFragment;
import com.krllus.tabdialog.fragment.TabDialogFragment;
import com.krllus.tabdialog.iface.IFragmentListener;
import com.krllus.tabdialog.iface.ISimpleDialogListener;


/**
 * Created by João Carlos on 4/11/18.
 * joaocarlusferrera at gmail
 */
public class MainFragment
        extends Fragment
        implements IFragmentListener, ISimpleDialogListener {

    private static final int REQUEST_CODE_FRAGMENT = 1;
    private static final String TAG = MainFragment.class.getSimpleName();

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
                .setTitle("title")
                .setSubTitle("subtitle")
                .setTabButtonText(new CharSequence[]{"tab1", "tab2"})
                .setPositiveButtonText("OK")
                .setNegativeButtonText("Cancel")
                .setFragmentListener(MainFragment.this)
                .setTargetFragment(this, REQUEST_CODE_FRAGMENT)
                .setRequestCode(REQUEST_CODE_FRAGMENT)
                .show();
    }

    @Override
    public void onFragmentViewCreated(PageFragment fragment) {
        int selectedTabPosition = fragment.getPagePosition();
        View container = fragment.getContentContainer();
        Log.i(TAG, "Position: " + selectedTabPosition);

        switch (selectedTabPosition) {
            case 0:
                selectedFirstTab(container);
                break;
        }

    }

    private void selectedFirstTab(View container) {
        // add view in container for first tab
        View detailRootView = getLayoutInflater().inflate(R.layout.tab_one_layout, (ViewGroup) container);

        TextView textView = detailRootView.findViewById(R.id.text_view);
        textView.setText("coleta");
    }

    @Override
    public void onFragmentAttached(PageFragment fragment) {

    }

    @Override
    public void onFragmentDetached(PageFragment fragment) {

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
