package com.krllus.tabdialog.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.krllus.tabdialog.R;
import com.krllus.tabdialog.iface.IFragmentListener;

public class PageFragment extends Fragment {

    public static final String TAB_POSITION = "tab_position";
    public static final String REQUEST_CODE = "request_code";

    private IFragmentListener listener;
    private View contentContainer;

    public static PageFragment newInstance(int position, int requestCode) {
        PageFragment frag = new PageFragment();
        Bundle args = new Bundle();
        args.putInt(PageFragment.TAB_POSITION, position);
        args.putInt(PageFragment.REQUEST_CODE, requestCode);
        frag.setArguments(args);
        return frag;
    }

    public void setListener(IFragmentListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tdl_fragment_container, container, false);
        contentContainer = rootView.findViewById(R.id.root_container);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (listener != null) {
            listener.onFragmentViewCreated(this);
            return;
        }

        if (getActivity() instanceof IFragmentListener) {
            ((IFragmentListener) getActivity()).onFragmentViewCreated(this);
        } else {
            Fragment parentFragment;
            parentFragment = getParentFragment();

            if (parentFragment instanceof IFragmentListener) {
                ((IFragmentListener) parentFragment).onFragmentViewCreated(this);
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (listener != null) {
            listener.onFragmentAttached(this);
            return;
        }

        if (getActivity() instanceof IFragmentListener) {
            ((IFragmentListener) getActivity()).onFragmentAttached(this);
        } else {
            Fragment parentFragment;
            parentFragment = getParentFragment();

            if (parentFragment instanceof IFragmentListener) {
                ((IFragmentListener) parentFragment).onFragmentAttached(this);
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (listener != null) {
            listener.onFragmentDetached(this);
            return;
        }

        if (getActivity() instanceof IFragmentListener) {
            ((IFragmentListener) getActivity()).onFragmentDetached(this);
        } else {
            Fragment parentFragment;
            parentFragment = getParentFragment();

            if (parentFragment instanceof IFragmentListener) {
                ((IFragmentListener) parentFragment).onFragmentDetached(this);
            }
        }
    }

    public int getPagePosition() {
        if (getArguments() == null) return -1;
        return getArguments().getInt(PageFragment.TAB_POSITION, -1);
    }

    public int getRequestCode() {
        if (getArguments() == null) return -1;
        return getArguments().getInt(PageFragment.REQUEST_CODE, -1);
    }

    public View getContentContainer() {
        return contentContainer;
    }
}
