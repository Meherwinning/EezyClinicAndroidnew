package com.vempower.eezyclinic.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vempower.eezyclinic.R;

/**
 * Created by satish on 6/12/17.
 */

public class MyProfileFragment extends AbstractFragment {

    private View fragmentView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.my_profile_layout, container, false);


        return fragmentView;
    }

    @Override
    View getFragemtView() {
        return fragmentView;
    }
}
