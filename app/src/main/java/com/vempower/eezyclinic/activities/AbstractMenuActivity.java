package com.vempower.eezyclinic.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vempower.eezyclinic.APICore.PatientData;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.callbacks.FromActivityListener;
import com.vempower.eezyclinic.callbacks.HomeBottomItemClickListener;
import com.vempower.eezyclinic.callbacks.ListenerKey;
import com.vempower.eezyclinic.fragments.AbstractFragment;
import com.vempower.eezyclinic.fragments.HomeFragment;
import com.vempower.eezyclinic.interfaces.ApiErrorDialogInterface;
import com.vempower.eezyclinic.interfaces.MenuScreenListener;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.SharedPreferenceUtils;
import com.vempower.eezyclinic.utils.Utils;

import com.vempower.eezyclinic.activities.AbstractActivity;


/**
 * Created by Satishk on 8/21/2017.
 */

public abstract class AbstractMenuActivity extends AbstractBackPressActivity implements View.OnClickListener {


    private DrawerLayout drawerLayout;
    //private TextView titleTextview;
    private LinearLayout linearLayout;
    private ActionBarDrawerToggle drawerToggle;

    private TextView nameTv, emailTv;

    private AbstractFragment currentFragment, prevoiusFragment;
    // private  Intent  intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkNetwork();

        //intent= getIntent();
    }

    protected void checkNetwork() {
        if (!Utils.isNetworkAvailable(this)) {
            showMyDialog("Alert", "Network not available", new ApiErrorDialogInterface() {
                @Override
                public void onCloseClick() {
                    finish();
                }

                @Override
                public void retryClick() {
                    checkNetwork();
                }
            });
            return;
        }
        setMyContectntView();
        init();
    }

    protected void setMyContectntView() {
        setContentView(R.layout.activity_menu_layout);
    }


    protected void init() {
        drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        linearLayout = (LinearLayout) findViewById(R.id.left_drawer);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
            }
        });


        // if(nameTv==null) {
        nameTv = findViewById(R.id.patient_name_tv);
        emailTv = findViewById(R.id.patient_email_tv);
        //}
        //titleRightImageview=findViewById(R.id.title_right_iv);


        setSideMenuLayout();
        setFragment();
        setSideMenuCutomerTitle();
        setActionBar();


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideKeyBord(nameTv);
                hideKeyBord();
            }
        },200);
    }

    @Override
    protected void onResume() {
        super.onResume();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideKeyBord(nameTv);
                hideKeyBord();
            }
        },250);


        //checkNetwork();
    }


    private void setSideMenuLayout() {

        callCategoriesMapper();
        setSidemenuItemsListener();
    }



   /* private ObjectAnimator createRotateAnimator(final View target, final float from, final float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
        animator.setDuration(300);
        animator.setInterpolator(com.github.aakira.expandablelayout.Utils.createInterpolator(com.github.aakira.expandablelayout.Utils.LINEAR_INTERPOLATOR));
        return animator;
    }*/

    protected abstract void setActionBar();


    private void setSidemenuItemsListener() {


        findViewById(R.id.dashdoard_linear).setOnClickListener(this);
        findViewById(R.id.book_appointment_linear).setOnClickListener(this);
        findViewById(R.id.my_profile_linear).setOnClickListener(this);
        findViewById(R.id.notification_linear).setOnClickListener(this);
        findViewById(R.id.health_checks_linear).setOnClickListener(this);
        findViewById(R.id.medical_history_linear).setOnClickListener(this);
        findViewById(R.id.notes_linear).setOnClickListener(this);
        findViewById(R.id.family_members_linear).setOnClickListener(this);
        findViewById(R.id.appointment_history_linear).setOnClickListener(this);
        findViewById(R.id.health_records_linear).setOnClickListener(this);
        findViewById(R.id.my_account_settings_linear).setOnClickListener(this);
        findViewById(R.id.feedback_linear).setOnClickListener(this);
        findViewById(R.id.features_and_benefits_linear).setOnClickListener(this);
        findViewById(R.id.logout_tv).setOnClickListener(this);


    }

    @Override
    public void onClick(@NonNull final View item) {
        Intent intent = getIntent();
        Log.i("Menu1", "Clicked");
        closeMenu();
        MyApplication.hideTransaprentDialog();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                openScreen(item);
                MyApplication.hideTransaprentDialog();
            }
        }, 250);


        // closeMenu();
    }



    private void openScreen(@NonNull View item) {
        hideKeyBord();
        Intent intent = getIntent();
        switch (item.getId()) {
            case R.id.dashdoard_linear:
                callDashboard();

                break;
            case R.id.book_appointment_linear:
                // showToastMessage("Coming soon");
                // callSideMenuScreen(ScheduleAppointmentActivity.class);
                //Intent  intent= getIntent(); //new Intent(this,HomeActivity.class);
                intent.setClass(this, SearchActivity.class);
                intent.putExtra(Constants.Pref.TITLE_BAR_NAME_KEY, "New Appointment");
                intent.putExtra(Constants.Pref.IS_FROM_DASH_BOARD, false);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                sendHandlerMessage(intent, ListenerKey.FROM_ACTIVITY_LISTENER_KEY, new FromActivityListener() {
                    @Override
                    public boolean isFromSearchActivity() {
                        return false;
                    }
                });


                startActivity(intent);

                break;
            case R.id.my_profile_linear:
                callMyProfile();

                break;
            case R.id.notification_linear:
               // showToastMessage("Coming soon");
                callSideMenuScreen(NotificationsListActivity.class);

                break;
            case R.id.health_checks_linear:
                //TODO something
               // showToastMessage("Coming soon");
                callSideMenuScreen(HealthChecksActivity.class);
                break;
            case R.id.medical_history_linear:
               // showToastMessage("Coming soon");
                callSideMenuScreen(MedicalHistoryListActivity.class);
                break;
            case R.id.notes_linear:
                //showToastMessage("Coming soon");
                callSideMenuScreen(MyNotesListActivity.class);
                break;
            case R.id.family_members_linear:
                //TODO something
               // showToastMessage("Coming soon");
                callSideMenuScreen(FamilyMembersActivity.class);
                break;
            case R.id.appointment_history_linear:
                callSideMenuScreen(AppointmentHistoryListActivity.class);
                break;
            case R.id.health_records_linear:
                //showToastMessage("Coming soon");
                callMedicalRecordds();
                // intent= new Intent(this,.class);
                //Intent  intent= getIntent(); //new Intent(this,HomeActivity.class);

                /*intent.setClass(this,.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);*/
                break;
            case R.id.my_account_settings_linear:
                callSettings();

                break;
            case R.id.feedback_linear:
                //showToastMessage("Coming soon");
                callSideMenuScreen(FeedbackActivity.class);
                break;
            case R.id.features_and_benefits_linear:
                showToastMessage("Coming soon");
                //callSideMenuScreen(NewHomeActivity.class);
                break;

            case R.id.logout_tv:
                checkConformation();

                break;

            default:
                showToastMessage("Coming soon");
                break;

        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideKeyBord(nameTv);
                hideKeyBord();
            }
        },200);
    }

    protected void callMyProfile() {
        Intent intent = getIntent(); //new Intent(this,HomeActivity.class);
        intent.setClass(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        sendHandlerMessage(getIntent(), ListenerKey.HOME_BOTTOM_ITEMS_SELECT_LISTENER_KEY, getRecordingListTitleBarListener(Constants.Home.MY_PROFILE));


    }


    //
    protected void callSideMenuScreen(Class<? extends AbstractActivity> myClass) {
        Intent intent = getIntent(); //new Intent(this,HomeActivity.class);
        intent.setClass(this, myClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        // sendHandlerMessage(getIntent(), ListenerKey.HOME_BOTTOM_ITEMS_SELECT_LISTENER_KEY, getRecordingListTitleBarListener(Constants.Home.MY_PROFILE));


    }


    protected void callMedicalRecordds() {
        Intent intent = getIntent(); //= new Intent(this,HomeActivity.class);
        intent.setClass(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        sendHandlerMessage(getIntent(), ListenerKey.HOME_BOTTOM_ITEMS_SELECT_LISTENER_KEY, getRecordingListTitleBarListener(Constants.Home.MEDICAL_RECORDS));


    }

    protected void callSettings() {
        Intent intent = getIntent(); //= new Intent(this,HomeActivity.class);
        intent.setClass(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        sendHandlerMessage(getIntent(), ListenerKey.HOME_BOTTOM_ITEMS_SELECT_LISTENER_KEY, getRecordingListTitleBarListener(Constants.Home.SETTINGS));
    }



    public void onMenuClick(View view) {
        setSideMenuCutomerTitle();
        drawerLayout.openDrawer(Gravity.LEFT);
       /* if(drawer!=null) {
            drawer.openDrawer(GravityCompat.START);
        }*/
    }

    public void setActionBar(final boolean isShowMenu) {
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);


        /*setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
         getSupportActionBar().setHomeButtonEnabled(true);
        if (Build.VERSION.SDK_INT >= 18) {
            getSupportActionBar().setHomeAsUpIndicator(
                    getResources().getDrawable(R.drawable.menu_icon));
        }*/

        drawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                isShowMenu ? (Toolbar) findViewById(R.id.toolbar) : null,
                /*toolbar,*/  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                //super.onDrawerClosed(view);
                //linearLayout.removeAllViews();
                if (isShowMenu)
                    invalidateOptionsMenu();
                linearLayout.setVisibility(View.GONE);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                // if (slideOffset > 0.6 && linearLayout.getChildCount() == 0)
                linearLayout.setVisibility(View.VISIBLE);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                // super.onDrawerOpened(drawerView);
                setSideMenuCutomerTitle();
                if (isShowMenu) {
                    invalidateOptionsMenu();
                }
            }

        };

        drawerLayout.post(new Runnable() {
            @Override
            public void run() {
                drawerToggle.syncState();
            }
        });
        drawerLayout.addDrawerListener(drawerToggle);

    }


    private void setSideMenuCutomerTitle() {

        nameTv.setText(Utils.getStringFromResources(R.string.welcome_guest_lbl));
        refreshSideMenuItems(false);
        if (!TextUtils.isEmpty(SharedPreferenceUtils.getStringValueFromSharedPrefarence(Constants.Pref.USER_VALIDATION_KEY, ""))) {

            Object obj = MyApplication.getInstance().getLoggedUserDetailsFromSharedPref();


            if (obj == null || !(obj instanceof PatientData)) {
                SharedPreferenceUtils.setStringValueToSharedPrefarence(Constants.Pref.USER_VALIDATION_KEY, null);
                return;
            }

            PatientData user = (PatientData) obj;
            if (user == null) {
                logout();
                return;
            }
            //Picasso.with(activity).load(mayorShipImageLink).transform(new CircleTransform()).into(ImageView);
            //String imgUrl="https://media.istockphoto.com/photos/friendly-doctor-at-the-hospital-picture-id511583494?s=2048x2048";
            ImageView imageView = findViewById(R.id.profile_iv);
            if (imageView != null) {
                //int defaultImageId, ImageView imageView,String imageUrl
                MyApplication.getInstance().setBitmapToImageviewCircular(R.drawable.profile_icon, imageView, user.getPatientLogo());
            }

            if (!TextUtils.isEmpty(user.getPatientName())) {
                nameTv.setText(user.getPatientName());
            }
            if (!TextUtils.isEmpty(user.getPatentEmail())) {
                emailTv.setText(user.getPatentEmail());
            }
            refreshSideMenuItems(true);

        }

    }

    private void refreshSideMenuItems(boolean isLoggedin) {

        // my_account_view.setVisibility(isLoggedin? View.VISIBLE: View.GONE);
        // findViewById(R.id.logout_tv1).setVisibility(/*isLoggedin?View.VISIBLE:*/View.INVISIBLE);


        // findViewById(R.id.login_linear_new).setVisibility(isLoggedin? View.GONE: View.VISIBLE);

        //findViewById(R.id.login_or_signup_linear).setVisibility(/*isLoggedin?View.GONE:*/View.INVISIBLE);
        //For login items
      /* findViewById(R.id.profile_linear).setVisibility(isLoggedin?View.VISIBLE:View.GONE);
       findViewById(R.id.edit_profile_linear).setVisibility(isLoggedin?View.VISIBLE:View.GONE);
        findViewById(R.id.change_pwd_linear).setVisibility(isLoggedin?View.VISIBLE:View.GONE);
        findViewById(R.id.my_orders_linear).setVisibility(isLoggedin?View.VISIBLE:View.GONE);
        findViewById(R.id.feed_back_linear).setVisibility(isLoggedin?View.VISIBLE:View.GONE);
        findViewById(R.id.login_logout_linear).setVisibility(isLoggedin?View.VISIBLE:View.GONE);
        findViewById(R.id.logout_tv1).setVisibility(isLoggedin?View.VISIBLE:View.GONE);


        //For logout items
        findViewById(R.id.categories_linear).setVisibility(isLoggedin?View.GONE:View.VISIBLE);
        findViewById(R.id.refer_a_vendor_linear).setVisibility(isLoggedin?View.GONE:View.VISIBLE);
        findViewById(R.id.subscribe_with_us_linear).setVisibility(isLoggedin?View.GONE:View.VISIBLE);
        findViewById(R.id.customer_support_linear).setVisibility(isLoggedin?View.GONE:View.VISIBLE);
        findViewById(R.id.contact_us_linear).setVisibility(isLoggedin?View.GONE:View.VISIBLE);

*/
    }


    private MenuScreenListener getMenuScreenListener() {
        return new MenuScreenListener() {
            @Override
            public void onFinishScreen() {
                if (currentFragment == null) {
                    setFragment(getHomeFragment());
                    return;
                }
                /*if(currentFragment instanceof DealViewFragment)
                {
                    if(prevoiusFragment!=null)
                    {

                        setFragment(prevoiusFragment);
                        prevoiusFragment.refreshBasicValues();
                        return;
                    }
                }*/


                if ((!(currentFragment instanceof HomeFragment))) {
                    setFragment(getHomeFragment());
                    return;
                }


            }

            @Override
            public void onTitleMenuClick(Object obj) {
                if (currentFragment == null) {
                    return;
                }
               /* if(((currentFragment instanceof ProfileFragment)))
                {
                    setFragment(getEditProfileFragment());
                    return;
                }
                if(((currentFragment instanceof EditProfileFragment)))
                {
                    if(prevoiusFragment!=null)
                    {
                        setFragment(prevoiusFragment);
                    }else {
                        setFragment(getProfileFragment());
                    }
                    return;
                }*/
            }

            @Override
            public void getRightTileDrawableId(int id) {
                setTitleRightMenuImage(id);
            }
        };
    }

    protected void closeMenu() {
        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawerLayout != null) {
            drawerLayout.closeDrawer(GravityCompat.START);

        }
       /* new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (drawerLayout != null) {
                    drawerLayout.closeDrawer(GravityCompat.START);

                }
            }
        },200);*/


    }

    protected abstract AbstractFragment getFragment();

    //protected abstract String getScreenTitle();


    protected void setFragment() {
        if (getFragment() == null) {
            return;
        }
        setFragment(getFragment());
        //setTitleBarName(getScreenTitle());
    }

    protected void setFragment(AbstractFragment fragment) {
        if (fragment == null || isFinishing()) {
            return;
        }
        prevoiusFragment = currentFragment;
        fragment.setOnMenuScreenListener(getMenuScreenListener());

        currentFragment = fragment;

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //sharedPreferences.edit().putInt("FRAGMENT", 0).apply();
        //abstractFragment = getFragment();//new FragmentAllSongs();
       /* if(abstractFragment instanceof ContactDetailsFragment)
        {
             contactDetailsFragment= (ContactDetailsFragment) abstractFragment;
        }*/
        Log.d("fragment", fragment.getClass().getSimpleName());
        //if(fragmentManager.findFragmentByTag(fragment.getClass().getSimpleName())==null) {
        fragmentTransaction.replace(R.id.fragment_layout, fragment, fragment.getClass().getSimpleName()/*"FRAGMENT"*/);
        //}else {
        //fragmentTransaction.show(fragmentManager.findFragmentByTag(fragment.getClass().getSimpleName()));
        // fragmentTransaction.attach(fragmentManager.findFragmentByTag(fragment.getClass().getSimpleName()));
        //}
        //Log.d("hidden","Show");
        //} /*else {
        //fragmentTransaction.hide(fragment);
        //Log.d("Shown","Hide");
        // }
        fragmentTransaction.commitAllowingStateLoss();
        //fragmentTransaction.commit();

                /*if(!TextUtils.isEmpty(getScreenTitle())) {
                    toolbar.setTitle(getScreenTitle());
                }*/

    }


    private void setTitleBarName(String str) {
       /* if (titleTextview == null) {
            titleTextview = findViewById(R.id.title_tv);
        }
        titleTextview.setText(str);*/

    }

    protected void showLogoutAlertDialog(String title, String msg, final boolean isFinish, final Intent intent) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MyApplication.getCurrentActivityContext());

        // Setting Dialog Title
        alertDialog.setTitle(TextUtils.isEmpty(title) ? "" : title);

        // Setting Dialog Message
        alertDialog.setMessage(msg);

        // setting Dialog cancelable to false 9010864578
        alertDialog.setCancelable(false);

        // On pressing Settings button
        alertDialog.setPositiveButton(Utils.getStringFromResources(R.string.ok_label),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();


                        if (intent != null) {
                            startActivity(intent);
                        }
                        if (isFinish) {
                            finish();
                        }
                    }
                });


        alertDialog.show();

    }

    private void checkConformation() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MyApplication.getCurrentActivityContext());
        alertDialog.setTitle(Utils.getStringFromResources(R.string.logout_msg_title));
        alertDialog.setMessage(Utils.getStringFromResources(R.string.logout_msg));
        alertDialog.setCancelable(false);

        alertDialog.setPositiveButton(Utils.getStringFromResources(R.string.yes_label),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        logout();
                    }
                });

        alertDialog.setNegativeButton(Utils.getStringFromResources(R.string.cancel_lbl),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });


        alertDialog.show();
    }


    private void logout() {


        SharedPreferenceUtils.setStringValueToSharedPrefarence(Constants.Pref.USER_VALIDATION_KEY, "");

        MyApplication.getInstance().setLoggedUserDetailsToSharedPref(null);

        //Toast.makeText(getBaseContext(), "Logged out successfully!", Toast.LENGTH_LONG).show();
        showToastMessage(R.string.success_logout_msg);
        setSideMenuCutomerTitle();
        if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        currentFragment = null;
        prevoiusFragment = null;
        refreshSideMenuItems(false);
        //setFragment(getHomeFragment());
        Intent intent = new Intent(this, SigninActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        //startActivity(new Intent(this,HomeActivity.class));
        // finish();

        //startActivity(new Intent(this,HomeActivity.class));
        //finish();
    }


    public void onTitleRightButtonClick(View view) {
        if (currentFragment != null) {
            currentFragment.onTitleRightButtonClick();
        }
    }

    private void setTitleRightMenuImage(int imageId) {
        /*if(titleRightImageview!=null)
        {
            if(imageId==0)
            {
                titleRightImageview.setBackgroundResource(0);
                titleRightImageview.setBackgroundColor(0);
                return;
            }
            MyApplication.getInstance().setBitmapToImageview(titleRightImageview,imageId);
        }*/

    }


    public void onCategoriesRelativeClick(View view1) {

        //findViewById(R.id.categories_title_relative).setBackground(getResources().getDrawable(R.drawable.background_shape_button1));
        // findViewById(R.id.cat_iv).setBackground(getResources().getDrawable(R.drawable.categories_icon));
        //((TextView)findViewById(R.id.cat_tv)).setTextColor(getResources().getColor(R.color.black));


    }

    private void callCategoriesMapper() {

    }

    @Override
    public void setFinishOnTouchOutside(boolean finish) {
        super.setFinishOnTouchOutside(finish);
        //findViewById(R.id.categories_list_linear).setVisibility(View.GONE);
    }


    @Override
    public void onBackPressed() {
        //View categoriesView= findViewById(R.id.categories_list_linear);


       /* if(currentFragment instanceof DealViewFragment)
        {
            if(prevoiusFragment!=null)
            {

                setFragment(prevoiusFragment);
                prevoiusFragment.refreshBasicValues();
                return;
            }
        }*/

        super.onBackPressed();
    }


//    private AbstractFragment getProfileFragment() {
//        ProfileFragment  fragment=new ProfileFragment();
//        // int id=R.drawable.edit;
//
//        return fragment;
//    }
//
//    private AbstractFragment getCategoryAllDealsFragment(String bussinessTypeId, String name, String imagePath) {
//        CategoryAllDealsFragment fragment=new CategoryAllDealsFragment();
//        // int id=R.drawable.edit;
//        Bundle bundle= new Bundle();
//        bundle.putString(Constants.Pref.CATEGORY_TYPE_ID_KEY,bussinessTypeId);
//        bundle.putString(Constants.Pref.CATEGORY_NAME_KEY,name==null?"":name);
//        bundle.putString(Constants.Pref.CATEGORY_IMAGE_PATH_KEY,imagePath==null?"":imagePath);
//        fragment.setArguments(bundle);
//        fragment.setOnCategoryAllDealsListener(new CategoryAllDealsListener() {
//            @Override
//            public void onSingleDealClick(Deal deal) {
//
//                //showToastMessage("Now click on "+deal.getEntityName());
//
//                callDealViewPage(deal);
//
//            }
//
//            @Override
//            public void onFinishScreen() {
//
//            }
//        });
//
//        return fragment;
//    }

    public AbstractFragment getHomeFragment() {
        HomeFragment fragment = new HomeFragment();

        // int id=R.drawable.cart;
        //setTitleRightMenuImage(id);
        return fragment;
    }

   /* public AbstractFragment getDealViewFragment() {
        DealViewFragment fragment=new DealViewFragment();
        // int id=R.drawable.check;
        //setTitleRightMenuImage(id);
        return fragment;
    }
*/

   /* public AbstractFragment getEditProfileFragment() {
        EditProfileFragment fragment=new EditProfileFragment();
        // int id=R.drawable.check;
        //setTitleRightMenuImage(id);
        return fragment;
    }

    public AbstractFragment getChangePasswordFragment() {

        ChangePasswordFragment fragment=new ChangePasswordFragment();
        // int id=R.drawable.check;
        //setTitleRightMenuImage(id);
        return fragment;
    }
*/

    // AutocompleteCustomArrayAdapter arrayAdapter;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
        }

        return super.onOptionsItemSelected(item);
    }
   protected void hideKeyBord()
    {
        hideKeyBord(drawerLayout);
     }



}
