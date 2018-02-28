package com.vempower.eezyclinic.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.vempower.eezyclinic.APICore.HealthChecksData;
import com.vempower.eezyclinic.APICore.HealthChecksHeight;
import com.vempower.eezyclinic.APICore.HealthChecksHeightWeight;
import com.vempower.eezyclinic.APICore.HealthChecksWeight;
import com.vempower.eezyclinic.fragments.CasesheetsFragment;
import com.vempower.eezyclinic.fragments.HealthCheckTab1;
import com.vempower.eezyclinic.fragments.HealthCheckTab2;
import com.vempower.eezyclinic.fragments.HealthCheckTab3;
import com.vempower.eezyclinic.fragments.HealthCheckTab4;
import com.vempower.eezyclinic.fragments.PrescriptionsFragment;
import com.vempower.eezyclinic.fragments.ReportsFragment;
import com.vempower.eezyclinic.interfaces.HealthChecksRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class HealthChecksViewPagerAdapter extends FragmentStatePagerAdapter {

    private static int TAB_COUNT = 4;
    private final HealthChecksData healthChecksData;
   // private PrescriptionsFragment prescriptionsFragment;
   private ArrayList<HealthChecksHeightWeight> heightAndWeightList;
   //private HealthChecksRefreshListener refreshListener;

    public HealthChecksViewPagerAdapter(HealthChecksData healthChecksData,ArrayList<HealthChecksHeightWeight> heightAndWeightList,FragmentManager fm/*,HealthChecksRefreshListener refreshListener*/) {
        super(fm);
        this.healthChecksData=healthChecksData;
        this.heightAndWeightList=heightAndWeightList;
       // this.refreshListener=refreshListener;

    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                HealthCheckTab1  checkTab1= new HealthCheckTab1();
                checkTab1.setSugarLevelsList(healthChecksData.getSugar());
                return checkTab1;
            case 1:
                HealthCheckTab2 tab2= new HealthCheckTab2();
                tab2.setBPList( healthChecksData.getBp());
                return tab2;
            case 2:
                HealthCheckTab3  tab3= new HealthCheckTab3();
                tab3.setHeightAndWeightList(heightAndWeightList);
                return tab3;
            case 3:
                HealthCheckTab4 tab4= new HealthCheckTab4();
                tab4.setChorlList(healthChecksData.getCholesterol());
                return tab4;
        }
        return null;
    }



    @Override
    public int getCount() {
        return TAB_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return HealthCheckTab1.TITLE;

            case 1:
                return HealthCheckTab2.TITLE;

            case 2:
                return HealthCheckTab3.TITLE;
            case 3:
                return HealthCheckTab4.TITLE;
        }
        return super.getPageTitle(position);
    }


}
