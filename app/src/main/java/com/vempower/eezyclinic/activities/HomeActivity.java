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

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.vempower.eezyclinic.APICore.PatientData;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.fragments.AbstractFragment;
import com.vempower.eezyclinic.fragments.HomeFragment;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.SharedPreferenceUtils;

/**
 * Created by Satish on 11/15/2017.
 */

public class HomeActivity extends AbstractMenuActivity {


    /*@Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
        init();
    }
*/


    public void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //setSupportActionBar(toolbar);
        // setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        if (Build.VERSION.SDK_INT >= 18) {
            getSupportActionBar().setHomeAsUpIndicator(
                    getResources().getDrawable(R.drawable.menu));
        }
        super.setActionBar(true);

    }

    @Override
    protected AbstractFragment getFragment() {
        return getHomeFragment();
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
}
