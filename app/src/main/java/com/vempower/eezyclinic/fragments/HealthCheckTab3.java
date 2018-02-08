package com.vempower.eezyclinic.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vempower.eezyclinic.R;

public class HealthCheckTab3 extends  AbstractFragment {


    public static final String TITLE = "Weight/Height";
    private View fragmentView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.activity_checks_tab3, container, false);

        return fragmentView;
    }




    @Override
    View getFragemtView() {
        return fragmentView;
    }
}
