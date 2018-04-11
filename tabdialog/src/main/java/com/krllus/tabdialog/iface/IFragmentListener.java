package com.krllus.tabdialog.iface;

import com.krllus.tabdialog.fragment.PageFragment;

import java.io.Serializable;

/**
 * Created by b_ashish on 23-Mar-16.
 */
public interface IFragmentListener extends Serializable {
    void onFragmentViewCreated(PageFragment fragment);

    void onFragmentAttached(PageFragment fragment);

    void onFragmentDetached(PageFragment fragment);
}
