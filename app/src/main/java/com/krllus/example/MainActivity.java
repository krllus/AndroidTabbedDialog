package com.krllus.example;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.krllus.tabdialog.fragment.PageFragment;
import com.krllus.tabdialog.fragment.TabDialogFragment;
import com.krllus.tabdialog.iface.IFragmentListener;
import com.krllus.tabdialog.iface.ISimpleDialogCancelListener;
import com.krllus.tabdialog.iface.ISimpleDialogListener;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class MainActivity
        extends AppCompatActivity
        implements ISimpleDialogListener, ISimpleDialogCancelListener, IFragmentListener {

    private static final int REQUEST_TABBED_DIALOG = 42;

    private static final String TAG = MainActivity.class.getSimpleName();

    private final Set<Fragment> mMyScheduleFragments = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.testbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TabDialogFragment.createBuilder(MainActivity.this, getSupportFragmentManager())
                        .setTitle("Title")
                        .setSubTitle("Subtitle")
                        .setTabButtonText(new CharSequence[]{"Tab 01", "Tab 02", "Tab 03"})
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
                    dialog.dismiss();
                break;
        }
    }

    @Override
    public void onNeutralButtonClicked(int requestCode, Dialog dialog) {
        switch (requestCode) {
            case REQUEST_TABBED_DIALOG:
                Toast.makeText(MainActivity.this, "Activity Neutral Clicked", Toast.LENGTH_SHORT).show();
                if (dialog != null && dialog.isShowing())
                    dialog.dismiss();
                break;
        }
    }

    @Override
    public void onPositiveButtonClicked(int requestCode, Dialog dialog) {
        switch (requestCode) {
            case REQUEST_TABBED_DIALOG:
                Toast.makeText(MainActivity.this, "Activity Positive Clicked", Toast.LENGTH_SHORT).show();
                if (dialog != null && dialog.isShowing())
                    dialog.dismiss();
                break;
        }
    }


    @Override
    public void onFragmentViewCreated(PageFragment fragment) {
        int selectedTabPosition = fragment.getArguments().getInt(PageFragment.TAB_POSITION, 0);
        View container = fragment.getContentContainer();
        Log.i(TAG, "Position: " + selectedTabPosition);

        switch (selectedTabPosition) {
            case 0:
                selectedFirstTab(container);
                break;

            case 1:
                selectedSecondTab(container);
                break;

            case 2:
                selectedThirdTab(container);
                break;

            default:
                break;
        }
    }

    private void selectedFirstTab(View rootContainer) {
        // add view in container for first tab
        View detailRootView = getLayoutInflater().inflate(R.layout.tab_one_layout, (ViewGroup) rootContainer);

        TextView textView = detailRootView.findViewById(R.id.text_view);
        textView.setText("coleta");
    }

    private void selectedSecondTab(View rootContainer) {
        // add view in container for second tab
        View detailRootView = getLayoutInflater().inflate(R.layout.tab_two_layout, (ViewGroup) rootContainer);

        TextView txtTitle = detailRootView.findViewById(R.id.txt_title);
        TextView txtMessage = detailRootView.findViewById(R.id.txt_message);

        String message = String.format(Locale.getDefault(), "Message: %s", "mensagem escondida");

        txtTitle.setText(R.string.app_name);
        txtMessage.setText(message);

    }

    private void selectedThirdTab(View rootContainer) {
        // add view in container for second tab
        View detailRootView = getLayoutInflater().inflate(R.layout.tab_tree_layout, (ViewGroup) rootContainer);

        TextView txtName = detailRootView.findViewById(R.id.txt_name_content);
        TextView txtSurname = detailRootView.findViewById(R.id.txt_surname_content);

        String name = "João Carlos";
        String surname = "Marques";

        txtName.setText(name);
        txtSurname.setText(surname);
    }

    @Override
    public void onFragmentAttached(PageFragment fragment) {
        mMyScheduleFragments.add(fragment);
    }

    @Override
    public void onFragmentDetached(PageFragment fragment) {
        mMyScheduleFragments.remove(fragment);
    }
}
