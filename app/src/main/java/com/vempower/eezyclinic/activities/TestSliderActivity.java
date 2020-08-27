package com.vempower.eezyclinic.activities;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.vempower.eezyclinic.R;

public class TestSliderActivity extends AbstractActivity {


    FragmentPagerAdapter adapterViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_slider);
        ViewPager vpPager = (ViewPager) findViewById(R.id.vpPager);
        vpPager.setClipToPadding(false);
        vpPager.setPageMargin(12);

        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
    }



    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 9;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public float getPageWidth (int position) {
            return 0.93f;
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            return null;
            /*FirstFragment.newInstance(null,position, "Page # "+position)*/
            /*switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return FirstFragment.newInstance(position, "Page # 1");
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return FirstFragment.newInstance(1, "Page # 2");
                case 2: // Fragment # 1 - This will show SecondFragment
                    return FirstFragment.newInstance(2, "Page # 3");
                default:
                    return null;
            }*/
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

    }
}
