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
import com.vempower.eezyclinic.APICore.HealthChecksHeight;
import com.vempower.eezyclinic.APICore.HealthChecksHeightWeight;
import com.vempower.eezyclinic.APICore.HealthChecksWeight;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.adapters.HealthChecksViewPagerAdapter;
import com.vempower.eezyclinic.adapters.HealthRecordsViewPagerAdapter;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.views.NonSwipeableViewPager;

import java.util.ArrayList;
import java.util.List;

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

    public ArrayList<HealthChecksHeightWeight> getHeightAndWeightList(List<HealthChecksHeight> heightList, List<HealthChecksWeight> weightList) {

        ArrayList heightWeightList = new ArrayList<>();

        if (heightList == null || weightList == null) {
            return heightWeightList;
        }
        if (heightList.size() == 0 || weightList.size() == 0) {
            return heightWeightList;
        }

        for (HealthChecksHeight height : heightList) {

            if (height == null) {
                continue;
            }
            HealthChecksWeight newWeight = new HealthChecksWeight();
            newWeight.setCheckupgroupid(height.getCheckupgroupid());

            int index = weightList.indexOf(newWeight);

            if (index != -1) {
                HealthChecksWeight weight = weightList.get(index);
                HealthChecksHeightWeight heightWeight = new HealthChecksHeightWeight(height);
                heightWeight.setWeightCheckupValue(weight.getWeightCheckupValue());
                heightWeightList.add(heightWeight);

            }


        }
        return heightWeightList;


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

    public void setMyViewPager() {
        setViewPager();
    }

    private void setViewPager() {
        //MyApplication.showTransparentDialog();
        ArrayList<HealthChecksHeightWeight> heightAndWeightList = getHeightAndWeightList(healthChecksData.getHeight(), healthChecksData.getWeight());
       // MyApplication.hideTransaprentDialog();


        mViewPagerAdapter = new HealthChecksViewPagerAdapter(healthChecksData,heightAndWeightList, getFragmentManager());

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