package com.vempower.eezyclinic.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.TabLayoutOnPageChangeListener;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.adapters.HealthRecordsViewPagerAdapter;
import com.vempower.eezyclinic.utils.Utils;

/**
 * Created by satish on 6/12/17.
 */

public class HealthRecordsFragment extends AbstractFragment {


    private View fragmentView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.activity_health_records, container, false);

        return fragmentView;
    }




    @Override
    View getFragemtView() {
        return fragmentView;
    }
}
