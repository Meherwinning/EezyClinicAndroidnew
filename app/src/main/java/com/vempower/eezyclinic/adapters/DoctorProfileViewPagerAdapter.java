package com.vempower.eezyclinic.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.vempower.eezyclinic.APICore.DoctorProfileData;
import com.vempower.eezyclinic.fragments.DoctorProfileTabFragment;
import com.vempower.eezyclinic.fragments.DoctorReviewsTabFragment;

/**
 * Created by satish on 18/12/17.
 */

public class DoctorProfileViewPagerAdapter extends FragmentPagerAdapter {

    private final DoctorProfileData profileData;
    public DoctorProfileViewPagerAdapter(final DoctorProfileData profileData,FragmentManager fm) {
        super(fm);
        this.profileData=profileData;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position)
        {
            case 0:

                return  DoctorProfileTabFragment.getInstance(profileData);
            default:
                return  DoctorReviewsTabFragment.getInstance(profileData);

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
                return "Profile";
            case 1:
                return "Reviews";

        }

        return "";
    }


}