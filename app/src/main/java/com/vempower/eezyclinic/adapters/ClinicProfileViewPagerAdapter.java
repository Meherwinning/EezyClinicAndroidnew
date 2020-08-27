package com.vempower.eezyclinic.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.vempower.eezyclinic.APICore.ClinicProfileData;
import com.vempower.eezyclinic.fragments.ClinicProfileTabFragment;
import com.vempower.eezyclinic.fragments.ViewDoctorsTabFragment;

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