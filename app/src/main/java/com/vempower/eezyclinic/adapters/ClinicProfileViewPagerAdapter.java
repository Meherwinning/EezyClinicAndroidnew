package com.vempower.eezyclinic.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.vempower.eezyclinic.APICore.ClinicProfileData;
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
private ClinicProfileData  clinicProfileData;
    public ClinicProfileViewPagerAdapter(ClinicProfileData clinicProfileData,FragmentManager fm) {
        super(fm);
        this.clinicProfileData=clinicProfileData;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position)
        {
            case 0:
                return  ClinicProfileTabFragment.getInstance(clinicProfileData);
            default:
                return  ViewDoctorsTabFragment.getInstance(clinicProfileData.getDoctorsList());

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