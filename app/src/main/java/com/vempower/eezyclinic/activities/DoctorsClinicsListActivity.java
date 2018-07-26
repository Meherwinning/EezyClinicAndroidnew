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
import android.os.Handler;
import android.os.Messenger;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.gc.materialdesign.views.ButtonFloat;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.callbacks.AbstractAppHandler;
import com.vempower.eezyclinic.callbacks.FilterRefreshListListener;
import com.vempower.eezyclinic.callbacks.HomeBottomItemClickListener;
import com.vempower.eezyclinic.callbacks.ListenerKey;
import com.vempower.eezyclinic.core.SearchRequest;
import com.vempower.eezyclinic.fragments.AbstractFragment;
import com.vempower.eezyclinic.fragments.ClinicsMapFragment;
import com.vempower.eezyclinic.fragments.DoctorsMapFragment;
import com.vempower.eezyclinic.fragments.MapFragment;
import com.vempower.eezyclinic.fragments.SearchClinicListFragment;
import com.vempower.eezyclinic.fragments.SearchDoctorsListFragment;
import com.vempower.eezyclinic.interfaces.SearchResultListener;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.SharedPreferenceUtils;
import com.vempower.eezyclinic.utils.Utils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Satish on 11/15/2017.
 */

public class DoctorsClinicsListActivity extends AbstractMenuActivity {


    private static final String BOTTOM_IMAGE_ID_STR = "bottom_linear_image", BOTTOM_TEXTVIEW_ID_STR = "bottom_linear_textview";

    //  private boolean isDoctorsListShow;
    // private boolean isShowFilterIcon=true;;
    // private boolean isClinicListShow=false;
    // private boolean isShowMapViewType;

    private String titleNameStr;


    private SearchDoctorsListFragment doctorsListFragment;
    private SearchClinicListFragment clinicListFragment;
    private DoctorsMapFragment doctorsMapFragment;
    private ClinicsMapFragment clinicsMapFragment;
    private ButtonFloat fab;
    private TextView titleName, search_query_tv;
    private boolean isCurrentShowMap;
    boolean isFromNewHomeSpeshality;
    private String newTileName = "";

    private SearchResultListener searchResultListener;

    private interface ViewState {
        int DOCTORS_LIST_VIEW = 1;
        int DOCTORS_MAP_VIEW = 2;
        int CLINIC_LIST_VIEW = 3;
        int CLINIC_MAP_VIEW = 4;
    }

    int currentStateView;

    protected void setMyContectntView() {
        setContentView(R.layout.activity_doctors_list_menu_layout);
        fab = findViewById(R.id.fab_all);
        titleNameStr = "Search Result";

        isFromNewHomeSpeshality = getIntent().getBooleanExtra(Constants.Pref.IS_FROM_NEW_HOME_SPESHALITY, false);

        if (isFromNewHomeSpeshality) {
            newTileName = getIntent().getStringExtra(Constants.Pref.DIPLAY_SPESHALITY_NAME);
        }


        //showView(ViewState.DOCTORS_LIST_VIEW,false,false);
        //onDoctorsListClick();
        //onClinicListClick();
        myInit();
        getIntent().putExtra(ListenerKey.FILTER_REFRESH_LIST_LISTENER_KEY, new Messenger(getFilterRefreshListListener()));

        openInitialSeachScreen();

        //getIntent().putExtra(ListenerKey.HOME_BOTTOM_ITEMS_SELECT_LISTENER_KEY, new Messenger(getBottomSelectItemKeyListener()));
    }

    private void openInitialSeachScreen() {
        SearchRequest requestParms = MyApplication.getInstance().getSearchRequestParms();
        if (requestParms == null) {
            requestParms = new SearchRequest(Constants.RESULT_PAGE_ITEMS_LIMIT1);
        }
        showView(requestParms.getSearchtype().equalsIgnoreCase(SearchRequest.DOCTOR_TYPE) ? ViewState.DOCTORS_LIST_VIEW : ViewState.CLINIC_LIST_VIEW, false, false);

    }


    private void myInit() {
        search_query_tv = findViewById(R.id.search_query_tv);

        searchResultListener = new SearchResultListener() {
            @Override
            public void result(int matchFound) {
                SearchRequest searchRequest = MyApplication.getInstance().getSearchRequestParms();
                search_query_tv.setText(matchFound + " matches found");

                if (searchRequest != null) {
                    StringBuilder queryStr = new StringBuilder();

                    if (!TextUtils.isEmpty(searchRequest.getCity())) {
                        queryStr.append("City : " + searchRequest.getCityName());
                    }

                    if (!TextUtils.isEmpty(searchRequest.getCountry())) {
                        if(queryStr.length()!=0) {
                            queryStr.append(", ");
                        }
                        queryStr.append("Country : " + searchRequest.getCountryName());
                    }


                    if (!TextUtils.isEmpty(searchRequest.getLocality())) {
                        if(queryStr.length()!=0) {
                            queryStr.append(", ");
                        }
                        queryStr.append("Locality : " + searchRequest.getLocality());
                    }


                    if (searchRequest.getGendersearch().size()!=0) {
                        if(queryStr.length()!=0) {
                            queryStr.append(", ");
                        }
                        String gender="";
                        for ( String gen:searchRequest.getGendersearch())
                        {
                            if(gender.length()!=0) {
                                gender=gender+", ";
                            }
                            gender=gender+gen;
                        }
                        queryStr.append("Gender : " + gender);
                    }

                    if(queryStr.length()!=0) {
                        search_query_tv.setText(matchFound + " matches found\n"+queryStr);

                    }




                    /*", specality='" + specality + '\'' +
                            ", country='" + country + '\'' +
                            ", city='" + city + '\'' +
                            ", insurenceList=" + insurenceList +
                            ", nationalityList=" + nationalityList +
                            ", gendersearch=" + gendersearch +
                            ", locality='" + locality + '\'' +
                            ", launguage=" + launguage +
                            ", searchName='" + searchName + '\'' +
                            ", onlinebooking=" + onlinebooking +
                            '}';*/
                }


            }
        };


        {

            fab.setDrawableIcon(getResources().getDrawable(R.drawable.location_icon1));

            handleFAB();
            fab.setOnMyClickListener(new ButtonFloat.MyClickListener() {
                @Override
                public void onClick(View view) {
                    buttonClick();
                }
            });

           /* fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Intent intent = new Intent(MyApplication.getCurrentActivityContext(), MapDoctorsActivity.class);
                    //startActivity(intent);
                    buttonClick();
                }
            });*/

        }
    }

    private void handleFAB() {
        /*if(!isClinicListShow)
        {
            fab.setVisibility(View.VISIBLE);
        }else
        {
            fab.setVisibility(View.GONE);
        }*/
    }

    private void buttonClick() {
        //handleFAB();

        showView(currentStateView, false, true);
        invalidateOptionsMenu();

    }


    @Override
    protected AbstractFragment getFragment() {

        return getHomeFragment();
    }

    public void onLogoutButtonClick(View view) {
        logout();

    }

    private void logout() {
        MyApplication.getInstance().setLoggedUserDetailsToSharedPref(null);
        SharedPreferenceUtils.setStringValueToSharedPrefarence(Constants.Pref.USER_VALIDATION_KEY, "");
        Intent intent = new Intent(MyApplication.getCurrentActivityContext(), SigninActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

   /* public AbstractFragment getSearchDoctorsListFragment() {
       // HomeFragment fragment=new HomeFragment();
        if(doctorsListFragment==null) {
            doctorsListFragment = new SearchDoctorsListFragment();

        }
        return doctorsListFragment;

    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filter_menu, menu);

        MenuItem item = menu.findItem(R.id.action_filter);
       /* if(item!=null )
        {
            item.setVisible(isShowFilterIcon);
        }*/
        //searchView.setMenuItem(item);

        // initDisplayCart(menu,R.id.action_cart);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_filter:
                Intent intent = getIntent();
                intent.setClass(this, FilterActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onSupportNavigateUp() {
        //finish();
       /* if(!isShowFilterIcon && !isClinicListShow)
        {
            buttonClick();
            return false;
        }*/
        finishScreen();
        return true;
    }

    @Override
    public void onBackPressed() {
        //finish();
        // super.onBackPressed();
        /*if(!isShowFilterIcon && !isClinicListShow)
        {
            buttonClick();
            return;
        }*/
        finishScreen();
    }

    @Override
    protected void setFragment() {
        //super.setFragment();
    }

    private void showView(int viewState, boolean isRefreshMap, boolean isOppisitViewShow) {
        invalidateOptionsMenu();
        switch (viewState) {
            case ViewState.DOCTORS_LIST_VIEW:
                if (isOppisitViewShow) {
                    onDoctorMapClick(isRefreshMap);
                } else {
                    onDoctorsListClick();
                }

                break;
            case ViewState.DOCTORS_MAP_VIEW:
                if (isOppisitViewShow) {
                    onDoctorsListClick();

                } else {
                    onDoctorMapClick(isRefreshMap);
                }
                break;
            case ViewState.CLINIC_LIST_VIEW:
                if (isOppisitViewShow) {
                    onClinicsMapClick(isRefreshMap);

                } else {
                    onClinicListClick();
                }

                break;
            case ViewState.CLINIC_MAP_VIEW:
                if (isOppisitViewShow) {
                    onClinicListClick();

                } else {
                    onClinicsMapClick(isRefreshMap);
                }
                break;
        }
    }

    public void onDoctorsListClick() {
        currentStateView = ViewState.DOCTORS_LIST_VIEW;
        isCurrentShowMap = false;
        myInit();
        titleNameStr = (Utils.getStringFromResources(R.string.search_result_doctors_lbl));
        if (titleName != null) {
            if (isFromNewHomeSpeshality) {
                titleName.setText(newTileName);
            } else {
                titleName.setText(titleNameStr);
            }

        }
        //if(titleName!=null) {
        // titleName.setText("Doctors-List");

        // }
        if (doctorsListFragment == null) {
            doctorsListFragment = new SearchDoctorsListFragment();
            doctorsListFragment.setOnSearchResultListener(searchResultListener);

            doctorsListFragment.isViewOnlyList(false);
        } else {
            doctorsListFragment.isViewOnlyList(true);
        }

        setFragment(doctorsListFragment);
        fab.setDrawableIcon(getResources().getDrawable(R.drawable.location_icon1));


    }

    public void onClinicListClick() {
        currentStateView = ViewState.CLINIC_LIST_VIEW;
        isCurrentShowMap = false;
        myInit();
        titleNameStr = Utils.getStringFromResources(R.string.search_result_clinic_lbl);
        if (titleName != null) {
            if (isFromNewHomeSpeshality) {
                titleName.setText(newTileName);
            } else {
                titleName.setText(titleNameStr);
            }
        }

        //if(titleName!=null) {
        //titleName.setText("Clinics-List");


        //}
        if (clinicListFragment == null) {
            clinicListFragment = new SearchClinicListFragment();
            clinicListFragment.setOnSearchResultListener(searchResultListener);

            clinicListFragment.isViewOnlyList(false);
        } else {
            clinicListFragment.isViewOnlyList(true);
        }

        setFragment(clinicListFragment);
        fab.setDrawableIcon(getResources().getDrawable(R.drawable.location_icon1));

    }


    public void onDoctorMapClick(boolean isRefresh) {

        currentStateView = ViewState.DOCTORS_MAP_VIEW;
        isCurrentShowMap = true;
        handleFAB();
        // titleName.setText("Doctors-Map");
        titleNameStr = Utils.getStringFromResources(R.string.search_result_doctors_lbl);
        if (titleName != null) {
            if (isFromNewHomeSpeshality) {
                titleName.setText(newTileName);
            } else {
                titleName.setText(titleNameStr);
            }
        }

        /*if(doctorsMapFragment ==null)
        {
            doctorsMapFragment = new MapFragment();

        }*/
        if (doctorsMapFragment == null) {
            doctorsMapFragment = new DoctorsMapFragment();


        }

        if (isRefresh) {
            doctorsMapFragment.isViewOnlyList(false);
        } else {
            doctorsMapFragment.setDoctorsList(doctorsListFragment.getDoctorsList());
            doctorsMapFragment.isViewOnlyList(true);

        }


        /*if(doctorsMapFragment !=null)
        {

        }*/


        setFragment(doctorsMapFragment);
        fab.setDrawableIcon(getResources().getDrawable(R.drawable.list_view_icon1));

    }

    public void onClinicsMapClick(boolean isRefresh) {
        currentStateView = ViewState.CLINIC_MAP_VIEW;
        isCurrentShowMap = true;
        handleFAB();
        //if(titleName!=null) {
        // titleName.setText("Clinics-Map");
        titleNameStr = (Utils.getStringFromResources(R.string.search_result_clinic_lbl));
        if (titleName != null) {
            if (isFromNewHomeSpeshality) {
                titleName.setText(newTileName);
            } else {
                titleName.setText(titleNameStr);
            }
        }


        //}
        /*if(doctorsMapFragment ==null)
        {
            doctorsMapFragment = new MapFragment();

        }*/
        if (clinicsMapFragment == null) {
            clinicsMapFragment = new ClinicsMapFragment();

        }

        if (isRefresh) {
            clinicsMapFragment.isViewOnlyList(false);
        } else {
            clinicsMapFragment.setClinicsList(clinicListFragment.getClinicList());
            clinicsMapFragment.isViewOnlyList(true);

        }

        setFragment(clinicsMapFragment);
        fab.setDrawableIcon(getResources().getDrawable(R.drawable.list_view_icon1));

    }


    @NonNull
    private AbstractAppHandler getFilterRefreshListListener() {
        return new AbstractAppHandler() {
            @Override
            public void getObject(Object obj) {
                if (obj != null && (obj instanceof FilterRefreshListListener)) {
                    FilterRefreshListListener listener = (FilterRefreshListListener) obj;

                    refreshTheList(listener.refreshList());

                }
            }
        };
    }

    private void refreshTheList(String searchRequestStr) {

        if (TextUtils.isEmpty(searchRequestStr)) {
            return;
        }
        if (searchRequestStr.equalsIgnoreCase(SearchRequest.DOCTOR_TYPE)) {

            doctorsListFragment = null;
            doctorsMapFragment = null;
            if (isCurrentShowMap) {
                onDoctorMapClick(true);

            } else {
                onDoctorsListClick();
            }
            //if()

        } else if (searchRequestStr.equalsIgnoreCase(SearchRequest.CLINIC_TYPE)) {
            clinicListFragment = null;
            clinicsMapFragment = null;
            if (isCurrentShowMap) {
                onClinicsMapClick(true);

            } else {
                onClinicListClick();
            }
        }


    }

    private final int DELAY_TIME = 4000;//In millis
    private boolean isBackArrowPressed = false;

    private void autoStopBackPressCallback() {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                isBackArrowPressed = false;
            }
        }, DELAY_TIME);
    }

    protected void finishScreen() {
       /* if (!isBackArrowPressed) {
            showToastMessage(getResources().getString(R.string.press_again_to_exit_from_search));
            isBackArrowPressed = true;
            autoStopBackPressCallback();
            return;
        }*/
        finish();
    }

    public void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (titleName == null) {
            titleName = toolbar.findViewById(R.id.title_logo_tv);
        }
        //((Toolbar) findViewById(R.id.toolbar)).setTitle(deal.getEntityName());
        if (titleName != null) {
            if (isFromNewHomeSpeshality) {
                titleName.setText(newTileName);
            } else {
                titleName.setText(titleNameStr);
            }
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //do something you want
               /* if(!isShowFilterIcon && !isClinicListShow)
                {
                    buttonClick();
                    return;
                }*/
                finishScreen();
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

}
