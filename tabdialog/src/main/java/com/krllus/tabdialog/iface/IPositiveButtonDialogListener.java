package com.krllus.tabdialog.iface;

/**
 * Implement this interface in Activity or Fragment to react to positive dialog buttons.
 *
 * @author Tomáš Kypta
 * @since 2.1.0
 */
public interface IPositiveButtonDialogListener {

    void onPositiveButtonClicked(int requestCode);
}
