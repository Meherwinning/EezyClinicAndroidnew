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
import android.os.Messenger;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.vempower.eezyclinic.fragments.DemoFragment;
import com.vempower.eezyclinic.fragments.HealthChecksFragment;
import com.vempower.eezyclinic.fragments.HealthRecordsFragment;
import com.vempower.eezyclinic.fragments.HomeFragment;
import com.vempower.eezyclinic.fragments.MedicalRecordsFragment;
import com.vempower.eezyclinic.fragments.MyProfileFragment;
import com.vempower.eezyclinic.fragments.SettingsFragment;
import com.vempower.eezyclinic.interfaces.AbstractIBinder;
import com.vempower.eezyclinic.interfaces.HomeListener;
import com.vempower.eezyclinic.interfaces.IntentObjectListener;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.SharedPreferenceUtils;
import com.vempower.eezyclinic.utils.Utils;

import java.util.List;

/**
 * Created by Satish on 11/15/2017.
 */

public class HomeActivity extends AbstractMenuActivity {


    private static final String BOTTOM_IMAGE_ID_STR = "bottom_linear_image", BOTTOM_TEXTVIEW_ID_STR = "bottom_linear_textview";

//
    private int[] imageUnselectIds={
            R.drawable.footer_home,R.drawable.group_2,R.drawable.footer_health_checks,R.drawable.group_8,R.drawable.group_6};
    private int[] imageSelectIds={
            R.drawable.footer_home_active,R.drawable.group_6_blue,R.drawable.footer_health_checks_active,R.drawable.group_8_blue,R.drawable.group_7_blue};

    private AbstractFragment homeFragment,myProfileFragment,medicalRecordsFragment,settingsFragment1,newHomeFragment,healthChecksFragment;
   //private MedicalRecordsFragment ;
    private ImageView title_logo_iv;


    private Toolbar toolbar;

    private int PRESENT_TAB;

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
        hideKeyBord();
           if(selectedItem==-1)
           {
               return;
           }
           switch (selectedItem)
           {
               case  Constants.Home.HOME_ACTIVITY:
                   callDashboard();
                 //  callHomeActionBar();
                   break;
               case  Constants.Home.MY_PROFILE:
                   callMyProfile();
                 //  callProfileActionBar();
                   break;
               case  Constants.Home.MEDICAL_RECORDS:
                   callMedicalRecordds();
                  // callHomeActionBar();
                   break;
               case  Constants.Home.NEW_HOME_ACTIVITY:
                  // onNewHomeClick(null);
                   newHomeCall(true);
                  // callHomeActionBar();
                   break;
               case  Constants.Home.HEALTH_CHECKS:
                   callHealthChecks();
                   // callHomeActionBar();
                   break;
           }
        hideKeyBord();

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
       /* MyApplication.showTransparentDialog();
        bottomItemChange(PRESENT_TAB);
        MyApplication.hideTransaprentDialog();*/

        /*if(homeFragment!=null)
        {
            ((HomeFragment)homeFragment).refresh();
        }*/
    }

    private boolean isHomeOptionMenu;
    private boolean isSettingsOptionMenu;
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
    }

    private void  callSettingsActionBar() {
        isHomeOptionMenu=false;
        isSettingsOptionMenu=true;

        invalidateOptionsMenu();
    }

    private void  callProfileActionBar() {
        isHomeOptionMenu=true;
        isSettingsOptionMenu=false;

        invalidateOptionsMenu();
    }
    private void  callHomeActionBar() {
        isHomeOptionMenu=false;
        isSettingsOptionMenu=false;

        invalidateOptionsMenu();
    }

    public void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //setSupportActionBar(toolbar);
        // setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        title_logo_iv= findViewById(R.id.title_logo_iv);
        title_logo_iv.setVisibility(View.VISIBLE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            getSupportActionBar().setHomeAsUpIndicator(
                    getResources().getDrawable(R.drawable.menu));
        }
        super.setActionBar(true);

    }



    public void setHomeActionBar() {
       if(title_logo_iv!=null) {
           title_logo_iv.setVisibility(View.VISIBLE);
       }
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        getSupportActionBar().setTitle("");

    }

    private void settingsActionBar(String title) {
        //isHomeOptionMenu=true;
        //invalidateOptionsMenu();

        if(title_logo_iv!=null)
        {
            title_logo_iv.setVisibility(View.GONE);
        }


        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        //((Toolbar) findViewById(R.id.toolbar)).setTitle(deal.getEntityName());
        // titleName.setText(Utils.getStringFromResources(R.string.title_activity_appointments));
   /*     if(titleName!=null) {
            titleName.setText("Profile");
        }else
        {*/
        getSupportActionBar().setTitle(title);
        // }
        if(toolbar!=null) {
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //do something you want
                    finish();
                }
            });
        }
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_USE_LOGO);
        //mImageView = findViewById(R.id.image);
        //View  mToolbarView = findViewById(R.id.toolbar);
        //((Toolbar) findViewById(R.id.toolbar)).setTitle(deal.getEntityName());

        //// getSupportActionBar().setTitle("");

        // mToolbarView.setBackgroundColor(ScrollUtils.getColorWithAlpha(0, getResources().getColor(R.color.app_red)));

        //super.setActionBar(false);
    }

    private void profileActionBar() {
        //isHomeOptionMenu=true;
        //invalidateOptionsMenu();

        if(title_logo_iv!=null)
        {
            title_logo_iv.setVisibility(View.GONE);
        }


        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        //((Toolbar) findViewById(R.id.toolbar)).setTitle(deal.getEntityName());
        // titleName.setText(Utils.getStringFromResources(R.string.title_activity_appointments));
   /*     if(titleName!=null) {
            titleName.setText("Profile");
        }else
        {*/
            getSupportActionBar().setTitle("Profile");
       // }
        if(toolbar!=null) {
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //do something you want
                    finish();
                }
            });
        }
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_USE_LOGO);
        //mImageView = findViewById(R.id.image);
        //View  mToolbarView = findViewById(R.id.toolbar);
        //((Toolbar) findViewById(R.id.toolbar)).setTitle(deal.getEntityName());

        //// getSupportActionBar().setTitle("");

        // mToolbarView.setBackgroundColor(ScrollUtils.getColorWithAlpha(0, getResources().getColor(R.color.app_red)));

        //super.setActionBar(false);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        switch (PRESENT_TAB)
        {
            case  Constants.Home.NEW_HOME_ACTIVITY:
                setHomeActionBar();
                break;
            case  Constants.Home.HOME_ACTIVITY:
                setHomeActionBar();
                break;
            case  Constants.Home.MY_PROFILE:
                getMenuInflater().inflate(R.menu.profile_view_menu, menu);
                profileActionBar();

                break;

            case  Constants.Home.MEDICAL_RECORDS:
                getMenuInflater().inflate(R.menu.normal_menu, menu);
                settingsActionBar("Health Records");
                break;
            /*case  Constants.Home.SETTINGS:
                getMenuInflater().inflate(R.menu.normal_menu, menu);
                settingsActionBar("My Account");
                break;*/
            case  Constants.Home.HEALTH_CHECKS:
                getMenuInflater().inflate(R.menu.normal_menu, menu);
                settingsActionBar("Health Checks");
                break;


                default:
                    setHomeActionBar();

        }
      /*  if(isSettingsOptionMenu)
        {
            getMenuInflater().inflate(R.menu.normal_menu, menu);
            settingsActionBar();
        }
        else if(isHomeOptionMenu) {
            getMenuInflater().inflate(R.menu.profile_view_menu, menu);
            profileActionBar();
        }else
        {
            setHomeActionBar();
        }*/

       // MenuItem item = menu.findItem(R.id.action_filter);
       /* if(item!=null )
        {
            item.setVisible(isShowFilterIcon);
        }*/
        //searchView.setMenuItem(item);

        // initDisplayCart(menu,R.id.action_cart);

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_profile_edit:
               // Utils.showToastMsg("Profile Edit");
              /*  Intent  intent= getIntent();
                intent.setClass(this,EditProfileActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityForResult(intent,EditProfileActivity.IS_REFRESH_PROFILE);*/

               //Start
                if(myProfileFragment==null)
                {
                   break;
                }
                Intent intent = ((Activity)MyApplication.getCurrentActivityContext()).getIntent();
                intent.setClass(this,EditProfileActivity.class);
                           /*((Activity) MyApplication.getCurrentActivityContext()).getIntent();*/
                intent.putExtra(ListenerKey.ObjectKey.PATIENT_PROFILE_OBJECT_KEY,new Messenger(new AbstractIBinder(){
                    @Override
                    protected IntentObjectListener getMyObject() {
                        return new IntentObjectListener(){

                            @Override
                            public Object getObject() {
                                if(myProfileFragment==null)
                                {
                                    return null;
                                }
                                if(myProfileFragment instanceof  MyProfileFragment)
                                {
                                    MyProfileFragment  fragment= (MyProfileFragment) myProfileFragment;
                                    return fragment.getPatientProfileObject();
                                }
                                return null;
                            }
                        };
                    }
                }));
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent,EditProfileActivity.IS_REFRESH_PROFILE);


                //End

                break;
        }

        return super.onOptionsItemSelected(item);
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK)
        {
            if(requestCode== EditProfileActivity.IS_REFRESH_PROFILE)
            {

                refreshProfile();
            }
        }
    }



    private void refreshProfile()
    {
        //Utils.showToastMsg("Refresh profile");
        callMyProfile();
        if(myProfileFragment!=null && (myProfileFragment instanceof MyProfileFragment))
        {
            MyProfileFragment fragment= (MyProfileFragment) myProfileFragment;
            fragment.refreshFragment();
        }

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

    /*@Override
    protected void callSettings() {
        onSettingsClick(null);
    }
*/
    @Override
    protected void callHealthChecks() {
        onHealthChecksClick(null);
    }

    @Override
    protected void setFragment() {
        //super.setFragment();
    }


    public void onNewHomeClick(View view)
    {
        newHomeCall(false);

    }

    private void newHomeCall(boolean isShowFeatures) {
        PRESENT_TAB= Constants.Home.NEW_HOME_ACTIVITY;
        callSettingsActionBar();
        /*if(newHomeFragment==null)
        {*/
        newHomeFragment= new DemoFragment();
        // fragment.refreshData();
        // newHomeFragment=fragment;
        // }
        setFragment(newHomeFragment);
        if(isShowFeatures)
        {
            DemoFragment df= (DemoFragment) newHomeFragment;
            df.setToShowFeaturesView(true);

        }
        unSelectAllDistance(BOTTOM_TEXTVIEW_ID_STR,BOTTOM_IMAGE_ID_STR,Constants.Home.NEW_HOME_ACTIVITY);
    }

    public void onDashBoardClick(View view1)
    {
        PRESENT_TAB=Constants.Home.HOME_ACTIVITY;
        callHomeActionBar();
        if(homeFragment==null)
        {
            homeFragment= getHomeFragment();
        }else
        {
            ((HomeFragment)homeFragment).refresh();
        }


        setFragment(homeFragment);
        unSelectAllDistance(BOTTOM_TEXTVIEW_ID_STR,BOTTOM_IMAGE_ID_STR,Constants.Home.HOME_ACTIVITY);

    }


    public void onMyProfileClick(View view)
    {
        PRESENT_TAB=Constants.Home.MY_PROFILE;
        callProfileActionBar();
        if(myProfileFragment==null)
        {
            myProfileFragment= new MyProfileFragment();
        }
        setFragment(myProfileFragment);
        unSelectAllDistance(BOTTOM_TEXTVIEW_ID_STR,BOTTOM_IMAGE_ID_STR,Constants.Home.MY_PROFILE);

    }



    public void onHealthChecksClick(View view)
    {
        PRESENT_TAB=Constants.Home.HEALTH_CHECKS;
        callSettingsActionBar();
        if(healthChecksFragment==null)
        {
            healthChecksFragment= new HealthChecksFragment();
        }else if(healthChecksFragment instanceof HealthChecksFragment)
        {
            HealthChecksFragment f1 = (HealthChecksFragment) healthChecksFragment;

              if(f1.isAdded())
              {
                  f1.callHealthChecksMapper();
              }else
              {
                  f1.refreshView();
              }
        }

        setFragment(healthChecksFragment);
        unSelectAllDistance(BOTTOM_TEXTVIEW_ID_STR,BOTTOM_IMAGE_ID_STR,Constants.Home.HEALTH_CHECKS);
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        if(PRESENT_TAB==Constants.Home.MEDICAL_RECORDS) {
           if(medicalRecordsFragment!=null) {
               MedicalRecordsFragment recordsFragment= (MedicalRecordsFragment) medicalRecordsFragment;
               recordsFragment.refresh();
           }
        }
    }

    public void onMedicalRecordsClick(View view)
    {
        PRESENT_TAB=Constants.Home.MEDICAL_RECORDS;
        callHomeActionBar();
       /* if(medicalRecordsFragment==null)
        {*/
            medicalRecordsFragment= new MedicalRecordsFragment();
           // medicalRecordsFragment.setMyViewPager();
        //}
        setFragment(medicalRecordsFragment);
        unSelectAllDistance(BOTTOM_TEXTVIEW_ID_STR,BOTTOM_IMAGE_ID_STR,Constants.Home.MEDICAL_RECORDS);

    }
   /* public void onSettingsClick(View view)
    {
        PRESENT_TAB=Constants.Home.SETTINGS;
        callSettingsActionBar();
        if(settingsFragment==null)
        {
            settingsFragment= new SettingsFragment();
        }
        setFragment(settingsFragment);
        unSelectAllDistance(BOTTOM_TEXTVIEW_ID_STR,BOTTOM_IMAGE_ID_STR,Constants.Home.SETTINGS);
    }*/

    private void computeBottomViews(View view,int viewNum)
    {
        unSelectAllDistance(BOTTOM_TEXTVIEW_ID_STR,BOTTOM_IMAGE_ID_STR,viewNum);


    }

    private void unSelectAllDistance(String textviewStr,String imageViewStr,int selectedViewNum) {
        for (int i = 0; i <= 4; i++) {
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
                unselectImageView((ImageView) findViewById(resID),selectedViewNum==i?imageSelectIds[i]:imageUnselectIds[i]);
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
