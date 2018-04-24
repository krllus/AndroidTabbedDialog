package com.krllus.example.activitys;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.krllus.example.R;
import com.krllus.example.fragments.MainFragment;
import com.krllus.example.fragments.NewsFragment;
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
                        .setTitle(R.string.dialog_title)
                        .setContentHeightMaxSize(R.dimen.content_max_height)
                        .setSubTitle(R.string.dialog_subtitle)
                        .showBottomDivider()
                        .setPositiveButtonText(R.string.btn_ok)
                        .setNegativeButtonText(R.string.btn_cancel)
                        .useCustomTheme(R.style.Custom_AlertDialogTheme)
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
            items.add(new DialogTabItem(NewsFragment.newInstance(), "News"));
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
