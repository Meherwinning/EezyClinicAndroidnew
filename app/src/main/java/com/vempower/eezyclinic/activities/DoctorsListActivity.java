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
import android.os.Messenger;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gc.materialdesign.views.ButtonFloat;
import com.vempower.eezyclinic.APICore.Appointment;
import com.vempower.eezyclinic.APICore.Followup;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.callbacks.AbstractAppHandler;
import com.vempower.eezyclinic.callbacks.HomeBottomItemClickListener;
import com.vempower.eezyclinic.callbacks.ListenerKey;
import com.vempower.eezyclinic.fragments.AbstractFragment;
import com.vempower.eezyclinic.fragments.HomeFragment;
import com.vempower.eezyclinic.fragments.MapFragment;
import com.vempower.eezyclinic.fragments.MedicalRecordsFragment;
import com.vempower.eezyclinic.fragments.MyProfileFragment;
import com.vempower.eezyclinic.fragments.SearchDoctorsListFragment;
import com.vempower.eezyclinic.fragments.SettingsFragment;
import com.vempower.eezyclinic.interfaces.HomeListener;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.SharedPreferenceUtils;
import com.vempower.eezyclinic.utils.Utils;

import java.util.List;

/**
 * Created by Satish on 11/15/2017.
 */

public class DoctorsListActivity extends AbstractMenuActivity {


    private static final String BOTTOM_IMAGE_ID_STR = "bottom_linear_image", BOTTOM_TEXTVIEW_ID_STR = "bottom_linear_textview";

    private boolean isDoctorsListShow;

    private int[] imageUnselectIds={R.drawable.group_2,R.drawable.group_6,R.drawable.group_8,R.drawable.group_10};
    private int[] imageSelectIds={R.drawable.group_6_blue,R.drawable.group_7_blue,R.drawable.group_8_blue,R.drawable.group_9_blue};

    private SearchDoctorsListFragment doctorsList;
    private MapFragment doctorsMap;

    protected void setMyContectntView()
    {
        setContentView(R.layout.activity_doctors_list_menu_layout);
        onDoctorsListClick();
        myInit();
        //getIntent().putExtra(ListenerKey.HOME_BOTTOM_ITEMS_SELECT_LISTENER_KEY, new Messenger(getBottomSelectItemKeyListener()));
    }

    private void myInit() {
        {
            ButtonFloat fab = findViewById(R.id.fab_all);

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Intent intent = new Intent(MyApplication.getCurrentActivityContext(), MapDoctorsActivity.class);
                    //startActivity(intent);
                    if(isDoctorsListShow)
                    {
                        onDoctorMapClick();

                    }else
                    {
                        onDoctorsListClick();
                    }
                }
            });
        }
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

   /* public AbstractFragment getSearchDoctorsListFragment() {
       // HomeFragment fragment=new HomeFragment();
        if(doctorsList==null) {
            doctorsList = new SearchDoctorsListFragment();

        }
        return doctorsList;

    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filter_menu, menu);

       // MenuItem item = menu.findItem(R.id.action_search);
        //searchView.setMenuItem(item);

       // initDisplayCart(menu,R.id.action_cart);

        return true;
    }


    public void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView titleName = toolbar.findViewById(R.id.title_logo_tv);
        //((Toolbar) findViewById(R.id.toolbar)).setTitle(deal.getEntityName());
        titleName.setText(Utils.getStringFromResources(R.string.title_activity_find_docotor));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //do something you want
                finish();
            }
        });
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_USE_LOGO);
        //mImageView = findViewById(R.id.image);
        //View  mToolbarView = findViewById(R.id.toolbar);
        //((Toolbar) findViewById(R.id.toolbar)).setTitle(deal.getEntityName());
        getSupportActionBar().setTitle("");
        // mToolbarView.setBackgroundColor(ScrollUtils.getColorWithAlpha(0, getResources().getColor(R.color.app_red)));

        //super.setActionBar(false);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }





    @Override
    protected void setFragment() {
        //super.setFragment();
    }

    public void onDoctorsListClick()
    {
        if(doctorsList==null) {
            doctorsList = new SearchDoctorsListFragment();

        }else
        {
            doctorsList.refreshList();
        }

        setFragment(doctorsList);
        isDoctorsListShow=true;

    }


    public void onDoctorMapClick()
    {
        if(doctorsMap==null)
        {
            doctorsMap= new MapFragment();
        }else
        {
            doctorsMap.refreshMap();
        }
        setFragment(doctorsMap);
        isDoctorsListShow=false;

    }




}
