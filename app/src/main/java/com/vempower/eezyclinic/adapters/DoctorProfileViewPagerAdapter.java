package com.vempower.eezyclinic.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.MotionEvent;

import com.vempower.eezyclinic.APICore.DoctorProfileData;
import com.vempower.eezyclinic.fragments.DoctorProfileTabFragment;
import com.vempower.eezyclinic.fragments.DoctorReviewsTabFragment;
import com.vempower.eezyclinic.fragments.ListViewFragment;

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