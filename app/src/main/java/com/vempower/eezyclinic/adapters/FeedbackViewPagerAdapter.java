package com.vempower.eezyclinic.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.vempower.eezyclinic.fragments.CasesheetsFragment;
import com.vempower.eezyclinic.fragments.PendingFeedbackFragment;
import com.vempower.eezyclinic.fragments.PrescriptionsFragment;
import com.vempower.eezyclinic.fragments.ReportsFragment;
import com.vempower.eezyclinic.fragments.SubmitedFeedbackFragment;

public class FeedbackViewPagerAdapter extends FragmentStatePagerAdapter {

    private static int TAB_COUNT = 2;
   // private PrescriptionsFragment prescriptionsFragment;

    public FeedbackViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return PendingFeedbackFragment.newInstance();
            case 1:
                return SubmitedFeedbackFragment.newInstance();
           /* case 2:
                return CasesheetsFragment.newInstance();*/
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
                return PendingFeedbackFragment.TITLE;

            case 1:
                return SubmitedFeedbackFragment.TITLE;

           /* case 2:
                return CasesheetsFragment.TITLE;*/
        }
        return super.getPageTitle(position);
    }


}