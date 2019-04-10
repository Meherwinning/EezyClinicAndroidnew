package com.vempower.eezyclinic.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.adapters.HealthRecordsViewPagerAdapter;
import com.vempower.eezyclinic.utils.Utils;

/**
 * Created by satish on 6/12/17.
 */

public class MedicalRecordsFragment extends AbstractFragment {

    private ViewPager mViewPager;
    private HealthRecordsViewPagerAdapter mViewPagerAdapter;
    private TabLayout mTabLayout;
    private View fragmentView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.medical_records_layout, container, false);

        myInit();

        return fragmentView;
    }

    private void myInit() {

        mViewPager = (ViewPager) getFragemtView().findViewById(R.id.pager1);
        mTabLayout = (TabLayout) getFragemtView().findViewById(R.id.tab1);
        mViewPager.setOffscreenPageLimit(3);



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
        mViewPagerAdapter = new HealthRecordsViewPagerAdapter(getFragmentManager());
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

    public void refresh() {
        //setMyViewPager();
    }
}