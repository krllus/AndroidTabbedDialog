package com.krllus.example;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.krllus.tabdialog.fragment.PageFragment;
import com.krllus.tabdialog.fragment.TabDialogFragment;
import com.krllus.tabdialog.iface.ISimpleDialogCancelListener;
import com.krllus.tabdialog.iface.ISimpleDialogListener;
import com.krllus.tabdialog.iface.IViewPagerAdapterInterface;
import com.krllus.tabdialog.model.DialogTabItem;

import java.util.ArrayList;

public class MainActivity
        extends AppCompatActivity
        implements ISimpleDialogListener, ISimpleDialogCancelListener {

    private static final int REQUEST_TABBED_DIALOG = 49;

    private ArrayList<DialogTabItem> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.testbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TabDialogFragment.createBuilder(MainActivity.this, getSupportFragmentManager(),
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
                        .setTitle("Title")
                        .setSubTitle(R.string.subtitle)
                        .setPositiveButtonText("OK")
                        .setNegativeButtonText("Cancel")
                        .setRequestCode(REQUEST_TABBED_DIALOG)
                        .show();
            }
        });


        MainFragment fragment = MainFragment.newInstance();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    private ArrayList<DialogTabItem> retrieveItems() {
        if (items == null)
            items = new ArrayList<>();

        if (items.isEmpty()) {
            items.add(new DialogTabItem(PageFragment.newInstance(), "Tab 01"));
            //items.add(new DialogTabItem(PageFragment.newInstance(), "Tab 02"));
            items.add(new DialogTabItem(PageFragment.newInstance(), "Tab 03"));
        }

        return items;
    }


    @Override
    public void onCancelled(int requestCode) {
        switch (requestCode) {
            case REQUEST_TABBED_DIALOG:
                Toast.makeText(MainActivity.this, "Dialog cancelled", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onNegativeButtonClicked(int requestCode, Dialog dialog) {
        switch (requestCode) {
            case REQUEST_TABBED_DIALOG:
                Toast.makeText(MainActivity.this, "Activity Negative Clicked", Toast.LENGTH_SHORT).show();
                if (dialog != null && dialog.isShowing())
                    //dialog.dismiss();
                    break;
        }
    }

    @Override
    public void onNeutralButtonClicked(int requestCode, Dialog dialog) {
        switch (requestCode) {
            case REQUEST_TABBED_DIALOG:
                Toast.makeText(MainActivity.this, "Activity Neutral Clicked", Toast.LENGTH_SHORT).show();
                if (dialog != null && dialog.isShowing())
                    //dialog.dismiss();
                    break;
        }
    }

    @Override
    public void onPositiveButtonClicked(int requestCode, Dialog dialog) {
        switch (requestCode) {
            case REQUEST_TABBED_DIALOG:
                Toast.makeText(MainActivity.this, "Activity Positive Clicked", Toast.LENGTH_SHORT).show();
                if (dialog != null && dialog.isShowing())
                    //dialog.dismiss();
                    break;
        }
    }
}
