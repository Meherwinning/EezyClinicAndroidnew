package com.vempower.eezyclinic.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vempower.eezyclinic.APICore.PatientData;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.fragments.AbstractFragment;
import com.vempower.eezyclinic.fragments.HomeFragment;
import com.vempower.eezyclinic.interfaces.MenuScreenListener;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.SharedPreferenceUtils;
import com.vempower.eezyclinic.utils.Utils;


/**
 * Created by Satishk on 8/21/2017.
 */

public abstract class AbstractMenuActivity extends AbstractBackPressActivity implements View.OnClickListener {


    private DrawerLayout drawerLayout;
    //private TextView titleTextview;
    private LinearLayout linearLayout;
    private ActionBarDrawerToggle drawerToggle;

    private  TextView nameTv;

    private AbstractFragment currentFragment,prevoiusFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMyContectntView();
        init();
    }

    protected void setMyContectntView()
    {
        setContentView(R.layout.activity_menu_layout);
    }




    private void init() {
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
            nameTv = findViewById(R.id.entity_name_tv);
        //}
        //titleRightImageview=findViewById(R.id.title_right_iv);


        setSideMenuLayout();
        setFragment();
        setSideMenuCutomerTitle();
        setActionBar();
    }


    @Override
    protected void onResume() {
        super.onResume();
        hideKeyBord(nameTv);
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

    protected abstract  void setActionBar();



    private void setSidemenuItemsListener() {

        findViewById(R.id.logout_tv).setOnClickListener(this);


       // findViewById(R.id.home_linear_new).setOnClickListener(this);


        //findViewById(R.id.profile_linear).setOnClickListener(this);
       // findViewById(R.id.edit_profile_linear).setOnClickListener(this);
        //findViewById(R.id.change_pwd_linear).setOnClickListener(this);
        //findViewById(R.id.order_history_linear).setOnClickListener(this);
       // findViewById(R.id.logout_tv1).setOnClickListener(this);
        /*findViewById(R.id.my_orders_linear).setOnClickListener(this);
        findViewById(R.id.feed_back_linear).setOnClickListener(this);
        findViewById(R.id.login_logout_linear).setOnClickListener(this);
        findViewById(R.id.logout_tv1).setOnClickListener(this);
*/

        //For logout items
       /* findViewById(R.id.categories_linear).setOnClickListener(this);
        findViewById(R.id.refer_a_vendor_linear).setOnClickListener(this);
        findViewById(R.id.subscribe_with_us_linear).setOnClickListener(this);
        findViewById(R.id.customer_support_linear).setOnClickListener(this);
        findViewById(R.id.contact_us_linear).setOnClickListener(this);*/
       // findViewById(R.id.login_or_signup_linear).setOnClickListener(this);

      /*  findViewById(R.id.view_profile_linear_new).setOnClickListener(this);
        findViewById(R.id.signout_linear_new).setOnClickListener(this);
        findViewById(R.id.refer_a_vendor_linear).setOnClickListener(this);
        findViewById(R.id.side_menu_cart_linear).setOnClickListener(this);

        findViewById(R.id.support_contact_us_linear).setOnClickListener(this);

        findViewById(R.id.scribe_with_us_linear).setOnClickListener(this);*/

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
                isShowMenu?(Toolbar) findViewById(R.id.toolbar):null,
                /*toolbar,*/  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                //super.onDrawerClosed(view);
                //linearLayout.removeAllViews();
                if(isShowMenu)
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
                if(isShowMenu) {
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
            if(user == null)
            {
                logout();
                return;
            }

            if (!TextUtils.isEmpty(user.getPatientName())) {
                nameTv.setText(user.getPatientName()+"\n"+user.getPatientUniqueId());
            }
            else if (!TextUtils.isEmpty(user.getPatientUniqueId())) {
                nameTv.setText(user.getPatientUniqueId());
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





    @Override
    public void onClick(@NonNull View item) {
        Intent intent=null;
        closeMenu();
        switch (item.getId())
        {

            case R.id.logout_tv:
                checkConformation();

                break;
//            case R.id.home_linear_new:
//                if(MyApplication.getCurrentActivityContext() instanceof HomeActivity)
//                {
//
//                    return;
//                }
//                //setFragment(getHomeFragment());
//                 intent= new Intent(this,HomeActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//                break;
//
//            case R.id.view_profile_linear_new:
//
//                if(expandableLayout_account!=null)
//                {
//                    expandableLayout_account.toggle();
//                }
//
//                 intent= new Intent(this,ViewProfileActivity.class);
//               // intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//               // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//                break;
//
//            case R.id.edit_profile_linear:
//                if(expandableLayout_account!=null)
//                {
//                    expandableLayout_account.toggle();
//                }
//                //setFragment(getEditProfileFragment());
//                intent= new Intent(this,EditProfileActivity.class);
//                startActivity(intent);
//                break;
//
//            case R.id.change_pwd_linear:
//                if(expandableLayout_account!=null)
//                {
//                    expandableLayout_account.toggle();
//                }
//                //setFragment(getChangePasswordFragment());
//                intent= new Intent(this,ChangePasswordActivity.class);
//                startActivity(intent);
//                break;
//
//           case R.id.order_history_linear:
//               if(expandableLayout_account!=null)
//               {
//                   expandableLayout_account.toggle();
//               }
//               //setFragment(getChangePasswordFragment());
//               intent= new Intent(this,OrderHistoryActivity.class);
//               startActivity(intent);
//               break;
//
//
//
//            case R.id.side_menu_cart_linear:
//                callCartActivity();
//                break;
//
//            case R.id.scribe_with_us_linear:
//                callScribeWithUsActivity();
//                break;
//
//            case R.id.support_contact_us_linear:
//                callTawkChatWebView();
//                break;


/*
            case R.id.categories_linear:
                showToastMessage("coming soon.");
                break;
            case R.id.refer_a_vendor_linear:
                showToastMessage("coming soon.");
                break;
            case R.id.subscribe_with_us_linear:
                showToastMessage("coming soon.");
                break;
            case R.id.customer_support_linear:
                showToastMessage("coming soon.");
                break;
            case R.id.contact_us_linear:
                showToastMessage("coming soon.");
                break;

            case R.id.login_logout_linear:*/
           /* case R.id.refer_a_vendor_linear:
                if(expandableLayout_account!=null)
                {
                    expandableLayout_account.toggle();
                }
                //setFragment(getChangePasswordFragment());
                intent= new Intent(this,ReferAVendorActivity.class);
                startActivity(intent);
                break;

           // case R.id.logout_tv1:
            case R.id.signout_linear_new:
                checkConformation();
                break;*/

                default:
                    showToastMessage("Coming soon");
                    break;

        }



        closeMenu();
    }

    private MenuScreenListener getMenuScreenListener()
    {
        return  new MenuScreenListener() {
            @Override
            public void onFinishScreen() {
                if(currentFragment==null)
                {
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



                if((!(currentFragment instanceof HomeFragment)))
                {
                    setFragment(getHomeFragment());
                    return;
                }


            }

            @Override
            public void onTitleMenuClick(Object obj) {
                if(currentFragment==null)
                {
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
        if (fragment == null) {
            return;
        }
        prevoiusFragment=currentFragment;
        fragment.setOnMenuScreenListener(getMenuScreenListener());

        currentFragment=fragment;

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //sharedPreferences.edit().putInt("FRAGMENT", 0).apply();
        //abstractFragment = getFragment();//new FragmentAllSongs();
       /* if(abstractFragment instanceof ContactDetailsFragment)
        {
             contactDetailsFragment= (ContactDetailsFragment) abstractFragment;
        }*/
        fragmentTransaction.replace(R.id.fragment_layout, fragment, "FRAGMENT");
        fragmentTransaction.commit();
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
        currentFragment=null;
        prevoiusFragment=null;
        refreshSideMenuItems(false);
        //setFragment(getHomeFragment());
        Intent intent= new Intent(this,SigninActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        //startActivity(new Intent(this,HomeActivity.class));
       // finish();

        //startActivity(new Intent(this,HomeActivity.class));
        //finish();
    }



    public void onTitleRightButtonClick(View view)
    {
        if(currentFragment!=null)
        {
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
        HomeFragment fragment=new HomeFragment();

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

        switch(item.getItemId())
        {
        }

        return super.onOptionsItemSelected(item);
    }

}
