package com.vempower.eezyclinic.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.vempower.eezyclinic.APICore.SearchResultDoctorListData;
import com.vempower.eezyclinic.fragments.ClinicProfileTabFragment;
import com.vempower.eezyclinic.fragments.DoctorProfileTabFragment;
import com.vempower.eezyclinic.fragments.DoctorReviewsTabFragment;
import com.vempower.eezyclinic.fragments.ListViewFragment;
import com.vempower.eezyclinic.fragments.ViewDoctorsTabFragment;

import java.util.ArrayList;

/**
 * Created by satish on 18/12/17.
 */

public class ClinicProfileViewPagerAdapter extends FragmentPagerAdapter {

    public ClinicProfileViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position)
        {
            case 0:
                return new ClinicProfileTabFragment();
            case 1:
                return  ViewDoctorsTabFragment.getInstance(new ArrayList<SearchResultDoctorListData>());
                default:
                    return ListViewFragment.newInstance(2);
        }

    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Clinic Profile";
            case 1:
                return "View Doctors";

        }

        return "";
    }


}