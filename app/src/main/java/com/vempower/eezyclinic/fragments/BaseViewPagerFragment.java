package com.vempower.eezyclinic.fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import com.vempower.eezyclinic.tools.ScrollableFragmentListener;
import com.vempower.eezyclinic.tools.ScrollableListener;


public abstract class BaseViewPagerFragment extends AbstractFragment implements ScrollableListener {

    protected ScrollableFragmentListener mListener;
    protected static final String BUNDLE_FRAGMENT_INDEX = "BaseFragment.BUNDLE_FRAGMENT_INDEX";
    protected int mFragmentIndex;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mFragmentIndex = bundle.getInt(BUNDLE_FRAGMENT_INDEX, 0);
        }

        if (mListener != null) {
            mListener.onFragmentAttached(this, mFragmentIndex);
        }
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            mListener = (ScrollableFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(
                    activity.toString() + " must implement ScrollableFragmentListener");
        }
    }


   /* @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (ScrollableFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(
                    activity.toString() + " must implement ScrollableFragmentListener");
        }
    }*/

    @Override
    public void onDetach() {
        if (mListener != null) {
            mListener.onFragmentDetached(this, mFragmentIndex);
        }

        super.onDetach();
        mListener = null;
    }
}
