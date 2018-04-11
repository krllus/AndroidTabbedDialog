package com.krllus.example;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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

public class MainActivity extends AppCompatActivity implements ISimpleDialogListener, ISimpleDialogCancelListener, IFragmentListener {

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
    public void onNegativeButtonClicked(int requestCode) {
        if (requestCode == REQUEST_TABBED_DIALOG) {
            Toast.makeText(MainActivity.this, "Negative button clicked", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNeutralButtonClicked(int requestCode) {
        if (requestCode == REQUEST_TABBED_DIALOG) {
            Toast.makeText(MainActivity.this, "Neutral button clicked", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPositiveButtonClicked(int requestCode) {
        if (requestCode == REQUEST_TABBED_DIALOG) {
            Toast.makeText(MainActivity.this, "Positive button clicked", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFragmentViewCreated(Fragment fragment) {
        int selectedTabPosition = fragment.getArguments().getInt(PageFragment.ARG_DAY_INDEX, 0);
        View rootContainer = fragment.getView().findViewById(R.id.root_container);
        Log.i(TAG, "Position: " + selectedTabPosition);

        switch (selectedTabPosition) {
            case 0:
                selectedFirstTab(rootContainer);
                break;

            case 1:
                selectedSecondTab(rootContainer);
                break;

            case 2:
                selectedThirdTab(rootContainer);
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
    public void onFragmentAttached(Fragment fragment) {
        mMyScheduleFragments.add(fragment);
    }

    @Override
    public void onFragmentDetached(Fragment fragment) {
        mMyScheduleFragments.remove(fragment);
    }
}
