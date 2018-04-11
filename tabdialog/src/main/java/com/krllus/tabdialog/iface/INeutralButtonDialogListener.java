package com.krllus.tabdialog.iface;

import android.app.Dialog;

/**
 * Implement this interface in Activity or Fragment to react to neutral dialog buttons.
 *
 * @author Tomáš Kypta
 * @since 2.1.0
 */
public interface INeutralButtonDialogListener {

    void onNeutralButtonClicked(int requestCode, Dialog dialog);
}
