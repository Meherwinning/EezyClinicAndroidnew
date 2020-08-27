package com.vempower.eezyclinic.adapters;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.vempower.eezyclinic.fragments.CasesheetsFragment;
import com.vempower.eezyclinic.fragments.PrescriptionsFragment;
import com.vempower.eezyclinic.fragments.ReportsFragment;

public class HealthRecordsViewPagerAdapter extends FragmentStatePagerAdapter {

    private static int TAB_COUNT = 3;
   // private PrescriptionsFragment prescriptionsFragment;

    public HealthRecordsViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return PrescriptionsFragment.newInstance();
            case 1:
                return ReportsFragment.newInstance();
            case 2:
                return CasesheetsFragment.newInstance();
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
                return PrescriptionsFragment.TITLE;

            case 1:
                return ReportsFragment.TITLE;

            case 2:
                return CasesheetsFragment.TITLE;
        }
        return super.getPageTitle(position);
    }


}
