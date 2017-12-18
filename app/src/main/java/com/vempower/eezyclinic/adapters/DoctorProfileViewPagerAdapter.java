package com.vempower.eezyclinic.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.MotionEvent;

import com.vempower.eezyclinic.fragments.DoctorProfileTabFragment;
import com.vempower.eezyclinic.fragments.DoctorReviewsTabFragment;
import com.vempower.eezyclinic.fragments.ListViewFragment;

/**
 * Created by satish on 18/12/17.
 */

public class DoctorProfileViewPagerAdapter extends FragmentPagerAdapter {

    public DoctorProfileViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position)
        {
            case 0:
                return new DoctorProfileTabFragment();
            case 1:
                return new DoctorReviewsTabFragment();
                default:
                    return ListViewFragment.newInstance(0);
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