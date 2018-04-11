package com.krllus.tabdialog.iface;

import android.app.Dialog;

/**
 * Implement this interface in Activity or Fragment to react to negative dialog buttons.
 *
 * @author Tomáš Kypta
 * @since 2.1.0
 */
public interface INegativeButtonDialogListener {

    void onNegativeButtonClicked(int requestCode, Dialog dialog);
}
