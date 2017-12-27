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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.vempower.eezyclinic.APICore.Followup;
import com.vempower.eezyclinic.APICore.Appointment;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.callbacks.AbstractAppHandler;
import com.vempower.eezyclinic.callbacks.FromActivityListener;
import com.vempower.eezyclinic.callbacks.HomeBottomItemClickListener;
import com.vempower.eezyclinic.callbacks.ListenerKey;
import com.vempower.eezyclinic.fragments.AbstractFragment;
import com.vempower.eezyclinic.fragments.HomeFragment;
import com.vempower.eezyclinic.fragments.MedicalRecordsFragment;
import com.vempower.eezyclinic.fragments.MyProfileFragment;
import com.vempower.eezyclinic.fragments.SettingsFragment;
import com.vempower.eezyclinic.interfaces.AbstractIBinder;
import com.vempower.eezyclinic.interfaces.HomeListener;
import com.vempower.eezyclinic.interfaces.IntentObjectListener;
import com.vempower.eezyclinic.utils.Constants;

import java.util.List;

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

        getIntent().putExtra(ListenerKey.HOME_BOTTOM_ITEMS_SELECT_LISTENER_KEY, new Messenger(getBottomSelectItemKeyListener()));
        callDashboard();

    }



    @NonNull
    private AbstractAppHandler getBottomSelectItemKeyListener() {
        return new AbstractAppHandler() {
            @Override
            public void getObject(Object obj) {
                if (obj != null && (obj instanceof HomeBottomItemClickListener) ) {
                    HomeBottomItemClickListener listener= (HomeBottomItemClickListener) obj;
                    //listener.getItemClicked();
                    bottomItemChange(listener.getItemClicked());

                }
            }
        };
    }


    private void bottomItemChange( int selectedItem) {

           if(selectedItem==-1)
           {
               return;
           }
           switch (selectedItem)
           {
               case  Constants.Home.HOME_ACTIVITY:
                   callDashboard();
                   break;
               case  Constants.Home.MY_PROFILE:
                   callMyProfile();
                   break;
               case  Constants.Home.MEDICAL_RECORDS:
                   callMedicalRecordds();
                   break;
               case  Constants.Home.SETTINGS:
                   callSettings();
                   break;
           }
    }

    @Override
    protected AbstractFragment getFragment() {

        return null;
    }

    public void onLogoutButtonClick(View view)
    {
        //logout();

    }

    @Override
    protected void onResume() {
        super.onResume();

        /*if(homeFragment!=null)
        {
            ((HomeFragment)homeFragment).refresh();
        }*/
    }

    public AbstractFragment getHomeFragment() {
       // HomeFragment fragment=new HomeFragment();
        if(homeFragment==null)
        {
            homeFragment= new HomeFragment();
            homeFragment.setOnMyListener(new HomeListener() {

                @Override
                public void onUpcomingAppointmentClick(final List<Appointment> list) {
                   // Utils.showToastMsg("Now click appointments");

                  //  Intent  intent=new Intent(MyApplication.getCurrentActivityContext(),UpComingAppointmentListActivity.class);
                       //startActivity(intent);

                    //

                    Intent intent = getIntent();
                    intent.setClass(HomeActivity.this,UpComingAppointmentListActivity.class);
                    //intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra(ListenerKey.ObjectKey.UPCOMING_APPOINTMENT_LIST_DATA_KEY,new Messenger(new AbstractIBinder(){
                        @Override
                        protected IntentObjectListener getMyObject() {
                            return new IntentObjectListener(){

                                @Override
                                public Object getObject() {
                                    return list;
                                }
                            };
                        }
                    }));

                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |  Intent.FLAG_ACTIVITY_SINGLE_TOP);

                    startActivity(intent);
                }

                @Override
                public void onUpcomingFollowupsClick(List<Followup> followups) {
                    Intent intent = getIntent();
                    intent.setClass(HomeActivity.this,UpcomingFollowupsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                }

                @Override
                public void onSearchDocatorsClick() {
                   // Utils.showToastMsg("Now click search doctors");

                    //Utils.showToastMsg("Coming soon.");

                    Intent intent = getIntent();
                    intent.setClass(HomeActivity.this,SearchActivity.class);
                    intent.putExtra(Constants.Pref.IS_FROM_DASH_BOARD,true);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |  Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    sendHandlerMessage(intent, ListenerKey.FROM_ACTIVITY_LISTENER_KEY, new FromActivityListener() {
                        @Override
                        public boolean isFromSearchActivity() {
                            return true;
                        }
                    });
                }
            });

        }
        return homeFragment;
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
        //return fragment;
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
    protected void callDashboard() {
        onDashBoardClick(null);

    }

    @Override
    protected void callMyProfile() {

        onMyProfileClick(null);
    }

    @Override
    protected void callMedicalRecordds() {
        onMedicalRecordsClick(null);
    }

    @Override
    protected void callSettings() {
        onSettingsClick(null);
    }

    @Override
    protected void setFragment() {
        //super.setFragment();
    }

    public void onDashBoardClick(View view1)
    {
        if(homeFragment==null)
        {
            homeFragment= getHomeFragment();
        }

        setFragment(homeFragment);
        unSelectAllDistance(BOTTOM_TEXTVIEW_ID_STR,BOTTOM_IMAGE_ID_STR,Constants.Home.HOME_ACTIVITY);

    }


    public void onMyProfileClick(View view)
    {
        if(myProfileFragment==null)
        {
            myProfileFragment= new MyProfileFragment();
        }
        setFragment(myProfileFragment);
        unSelectAllDistance(BOTTOM_TEXTVIEW_ID_STR,BOTTOM_IMAGE_ID_STR,Constants.Home.MY_PROFILE);

    }
    public void onMedicalRecordsClick(View view)
    {
        if(medicalRecordsFragment==null)
        {
            medicalRecordsFragment= new MedicalRecordsFragment();
        }
        setFragment(medicalRecordsFragment);
        unSelectAllDistance(BOTTOM_TEXTVIEW_ID_STR,BOTTOM_IMAGE_ID_STR,Constants.Home.MEDICAL_RECORDS);

    }
    public void onSettingsClick(View view)
    {
        if(settingsFragment==null)
        {
            settingsFragment= new SettingsFragment();
        }
        setFragment(settingsFragment);
        unSelectAllDistance(BOTTOM_TEXTVIEW_ID_STR,BOTTOM_IMAGE_ID_STR,Constants.Home.SETTINGS);
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
