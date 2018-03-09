/*
 * Copyright (C) 2016 Ronald Martin <hello@itsronald.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Last modified 10/13/16 2:53 PM.
 */

package com.vempower.eezyclinic.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.fragments.PageFragment;

public class AppTourActivity extends AbstractFragmentActivity {

    private int mSelectedItem;
    private boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_java_example);
        flag=false;

        final ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        final  PagerAdapter adapter= new PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

       /* final ViewPager.LayoutParams layoutParams = new ViewPager.LayoutParams();
        layoutParams.width = ViewPager.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewPager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.BOTTOM;
*/
       // final ViewPagerIndicator indicator =  findViewById(R.id.view_pager_indicator);//new ViewPagerIndicator(this);
        //viewPager.addView(indicator);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public int state=-1;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mSelectedItem = position;

                if(state== ViewPager.SCROLL_STATE_DRAGGING && position==(adapter.getCount()-1))
                {
                    if(!flag)
                    onNextButtonClick();
                    return;
                }


            }

            @Override
            public void onPageSelected(int position) {
                //mSelectedItem = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

                this.state=state;
            }
        });

        viewPager.setPageTransformer(false, new ViewPager.PageTransformer()
        {
            @Override
            public void transformPage(View page, float position)
            {
                int pageWidth = viewPager.getMeasuredWidth() -
                        viewPager.getPaddingLeft() - viewPager.getPaddingRight();
                int pageHeight = viewPager.getHeight();
                int paddingLeft = viewPager.getPaddingLeft();
                float transformPos = (float) (page.getLeft() -
                        (viewPager.getScrollX() + paddingLeft)) / pageWidth;
                int max = pageHeight / 10;
                if (transformPos < -1)
                {
                    // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    page.setAlpha(0.5f);// to make left transparent
                    page.setScaleY(0.7f);
                }
                else if (transformPos <= 1)
                {
                    // [-1,1]
                    page.setScaleY(1f);
                }
                else
                {
                    // (1,+Infinity]
                    // This page is way off-screen to the right.
                    page.setAlpha(0.5f);// to make right transparent
                    page.setScaleY(0.7f);
                }
            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        flag=false;
    }

    private void onNextButtonClick() {
        flag=true;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent= new Intent(AppTourActivity.this,SigninActivity.class);
                startActivity(intent);
                finish();
            }
        },200);

    }

    private static class PagerAdapter extends FragmentPagerAdapter {

        private static final int NUM_PAGES = 3;

        private PagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        @Override
        public Fragment getItem(int position) {
            return PageFragment.newInstance( position);
        }
    }
}
