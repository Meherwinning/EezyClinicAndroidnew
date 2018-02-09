package com.vempower.eezyclinic.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vempower.eezyclinic.APICore.HealthChecksData;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.adapters.HealthChecksViewPagerAdapter;
import com.vempower.eezyclinic.adapters.HealthRecordsViewPagerAdapter;
import com.vempower.eezyclinic.views.NonSwipeableViewPager;

/**
 * Created by satish on 6/12/17.
 */

public class HealthChecksFragment extends AbstractFragment {

    private NonSwipeableViewPager mViewPager;
    private HealthChecksViewPagerAdapter mViewPagerAdapter;
    private TabLayout mTabLayout;
    private View fragmentView;
    private HealthChecksData healthChecksData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.health_checks_layout, container, false);

        myInit();

        return fragmentView;
    }

    private void myInit() {
        mViewPager = (NonSwipeableViewPager) getFragemtView().findViewById(R.id.pager1);
        mTabLayout = (TabLayout) getFragemtView().findViewById(R.id.tab1);
        mViewPager.setOffscreenPageLimit(4);
       // mViewPager.setScol



    }

    @Override
    View getFragemtView() {
        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setMyViewPager();

    }
    public void setMyViewPager()
    {
        setViewPager();
    }

    private void setViewPager() {
        mViewPagerAdapter = new HealthChecksViewPagerAdapter(healthChecksData,getFragmentManager());
        mViewPager.setAdapter(mViewPagerAdapter);

        mTabLayout.setupWithViewPager(mViewPager);
      /*  mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Utils.showToastMsg(" onPageScrolled position "+position);
            }

            @Override
            public void onPageSelected(int position) {
                Utils.showToastMsg(" onPageSelected position "+position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Utils.showToastMsg(" onPageScrollStateChanged  "+state);
            }
        });*/


        // ((TabLayout) findViewById(R.id.tabHost)).setupWithViewPager(mViewPager);
    }

    public void setHealthChecksData(HealthChecksData healthChecksData) {
        this.healthChecksData = healthChecksData;
    }
}