package com.vempower.eezyclinic.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.adapters.HealthRecordsViewPagerAdapter;

/**
 * Created by satish on 6/12/17.
 */

public class HealthRecordsFragment extends AbstractFragment {

    private ViewPager mViewPager;
    private HealthRecordsViewPagerAdapter mViewPagerAdapter;
    private TabLayout mTabLayout;


    private View fragmentView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.activity_health_records, container, false);

        myInit();

        return fragmentView;
    }

    private void myInit() {

        mViewPager = (ViewPager) getFragemtView().findViewById(R.id.pager);
        mTabLayout = (TabLayout) getFragemtView().findViewById(R.id.tab);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setViewPager();

    }

    private void setViewPager() {
        mViewPagerAdapter = new HealthRecordsViewPagerAdapter(getFragmentManager());
        mViewPager.setAdapter(mViewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        // ((TabLayout) findViewById(R.id.tabHost)).setupWithViewPager(mViewPager);
    }

    @Override
    View getFragemtView() {
        return fragmentView;
    }
}
