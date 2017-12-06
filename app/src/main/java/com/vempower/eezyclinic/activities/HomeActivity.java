/*
 * Copyright (C) 2017 Ronald Martin <hello@itsronald.com>
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
 * Last modified 11/15/17 12:56 PM.
 */

package com.vempower.eezyclinic.activities;

import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.fragments.AbstractFragment;
import com.vempower.eezyclinic.fragments.HomeFragment;
import com.vempower.eezyclinic.fragments.MedicalRecordsFragment;
import com.vempower.eezyclinic.fragments.MyProfileFragment;
import com.vempower.eezyclinic.fragments.SettingsFragment;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.SharedPreferenceUtils;

/**
 * Created by Satish on 11/15/2017.
 */

public class HomeActivity extends AbstractMenuActivity {

    private static final String BOTTOM_IMAGE_ID_STR = "bottom_linear_image", BOTTOM_TEXTVIEW_ID_STR = "bottom_linear_textview";


    private int[] imageUnselectIds={R.drawable.group_2,R.drawable.group_6,R.drawable.group_8,R.drawable.group_10};
    private int[] imageSelectIds={R.drawable.group_6_blue,R.drawable.group_7_blue,R.drawable.group_8_blue,R.drawable.group_9_blue};

    private AbstractFragment homeFragment,myProfileFragment,medicalRecordsFragment,settingsFragment;

    protected void setMyContectntView()
    {
        setContentView(R.layout.activity_home_menu_layout);
        onDashBoardClick(null);
    }




    @Override
    protected AbstractFragment getFragment() {
        if(homeFragment==null)
        {
            homeFragment= new HomeFragment();
        }
        return homeFragment;
    }

    public void onLogoutButtonClick(View view)
    {
        logout();

    }

    private void logout() {
        MyApplication.getInstance().setLoggedUserDetailsToSharedPref(null);
        SharedPreferenceUtils.setStringValueToSharedPrefarence(Constants.Pref.USER_VALIDATION_KEY,"");
        Intent intent= new Intent(MyApplication.getCurrentActivityContext(),SigninActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    public AbstractFragment getHomeFragment() {
        HomeFragment fragment=new HomeFragment();
      /*  fragment.setOnHomeScreenListener(new HomeScreenListener(){
            @Override
            public void onViewAllButtonClick(CategoryDeals categoryDeals) {

                Intent intent= new Intent(HomeActivity.this, CategoryAllDealsActivity.class);

                String name=categoryDeals.getBtypeName();
                String imagePath=categoryDeals.getBtypeImageWhite();
                intent.putExtra(Constants.Pref.CATEGORY_TYPE_ID_KEY,categoryDeals.getBtypeId());
                intent.putExtra(Constants.Pref.CATEGORY_NAME_KEY,name==null?"":name);
                intent.putExtra(Constants.Pref.CATEGORY_IMAGE_PATH_KEY,imagePath==null?"":imagePath);

                startActivity(intent);

            }

            @Override
            public void onSingleDealClick(Deal deal) {

                callDealViewPage(deal);

            }
        });*/
        // int id=R.drawable.cart;
        //setTitleRightMenuImage(id);
        return fragment;
    }

    public void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //setSupportActionBar(toolbar);
        // setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            getSupportActionBar().setHomeAsUpIndicator(
                    getResources().getDrawable(R.drawable.menu));
        }
        super.setActionBar(true);

    }

    @Override
    protected void setFragment() {
        //super.setFragment();
    }

    public void onDashBoardClick(View view)
    {
        if(homeFragment==null)
        {
            homeFragment= new HomeFragment();
        }
       setFragment(homeFragment);
        unSelectAllDistance(BOTTOM_TEXTVIEW_ID_STR,BOTTOM_IMAGE_ID_STR,1);

    }
    public void onMyProfileClick(View view)
    {
        if(myProfileFragment==null)
        {
            myProfileFragment= new MyProfileFragment();
        }
        setFragment(myProfileFragment);
        unSelectAllDistance(BOTTOM_TEXTVIEW_ID_STR,BOTTOM_IMAGE_ID_STR,2);

    }
    public void onMedicalRecordsClick(View view)
    {
        if(medicalRecordsFragment==null)
        {
            medicalRecordsFragment= new MedicalRecordsFragment();
        }
        setFragment(medicalRecordsFragment);
        unSelectAllDistance(BOTTOM_TEXTVIEW_ID_STR,BOTTOM_IMAGE_ID_STR,3);

    }
    public void onSettingsClick(View view)
    {
        if(settingsFragment==null)
        {
            settingsFragment= new SettingsFragment();
        }
        setFragment(settingsFragment);
        unSelectAllDistance(BOTTOM_TEXTVIEW_ID_STR,BOTTOM_IMAGE_ID_STR,4);
    }

    private void computeBottomViews(View view,int viewNum)
    {
        unSelectAllDistance(BOTTOM_TEXTVIEW_ID_STR,BOTTOM_IMAGE_ID_STR,viewNum);


    }

    private void unSelectAllDistance(String textviewStr,String imageViewStr,int selectedViewNum) {
        for (int i = 1; i <= 4; i++) {
            //Textview
            {
                String str = textviewStr + "" + i;
                int resID = getResources().getIdentifier(str,
                        "id", MyApplication.getCurrentActivityContext().getPackageName());
                unselectTextView((TextView) findViewById(resID), selectedViewNum==i);
            }

            {
                String str = imageViewStr + "" + i;
                int resID = getResources().getIdentifier(str,
                        "id", MyApplication.getCurrentActivityContext().getPackageName());
                unselectImageView((ImageView) findViewById(resID),selectedViewNum==i?imageSelectIds[i-1]:imageUnselectIds[i-1]);
            }
        }
        // selectedDistance = -1;
        //View addButton = findViewById(resID);
    }
    private void unselectImageView(ImageView  imageView,int id) {
        if (imageView == null || id<0) {
            return;
        }
        imageView.setImageResource(0);
        //imageView.setImageResource();
        imageView.setImageResource(id);
       // textView.setTextColor(getResources().getColor(R.color.bottom_text_color));
    }


    private void unselectTextView(TextView textView,boolean isSelected) {
        if (textView == null) {
            return;
        }
        //textView.setBackground(MyApplication.getCurrentActivityContext().getResources().getDrawable(R.drawable.circle_shape_light_gray));
       if(isSelected)
       {
           textView.setTextColor(getResources().getColor(R.color.colorPrimary));
       }else {
           textView.setTextColor(getResources().getColor(R.color.bottom_text_color));
       }
    }

}
