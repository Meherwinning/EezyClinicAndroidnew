package com.vempower.eezyclinic.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Messenger;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gc.materialdesign.views.ButtonFloat;
import com.vempower.eezyclinic.APICore.DoctorProfileData;
import com.vempower.eezyclinic.APICore.PatientData;
import com.vempower.eezyclinic.APICore.SearchResultDoctorListData;
import com.vempower.eezyclinic.APIResponce.DoctorProfileAPI;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.adapters.DoctorProfileViewPagerAdapter;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.callbacks.ListenerKey;
import com.vempower.eezyclinic.fragments.AbstractFragment;
import com.vempower.eezyclinic.fragments.ListViewFragment;
import com.vempower.eezyclinic.fragments.ScrollViewFragment;
import com.vempower.eezyclinic.interfaces.AbstractIBinder;
import com.vempower.eezyclinic.interfaces.ApiErrorDialogInterface;
import com.vempower.eezyclinic.interfaces.IntentObjectListener;
import com.vempower.eezyclinic.mappers.DoctorProfileMapper;
import com.vempower.eezyclinic.tools.ScrollableFragmentListener;
import com.vempower.eezyclinic.tools.ScrollableListener;
import com.vempower.eezyclinic.tools.ViewPagerHeaderHelper;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.Utils;
 ;
import com.vempower.eezyclinic.widget.TouchCallbackLayout;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class DoctorProfileActivity extends AbstractMenuActivity
        implements TouchCallbackLayout.TouchEventListener, ScrollableFragmentListener,
        ViewPagerHeaderHelper.OnViewPagerTouchListener {

    private static final long DEFAULT_DURATION = 300L;
    private static final float DEFAULT_DAMPING = 1.5f;

    private SparseArrayCompat<ScrollableListener> mScrollableListenerArrays =
            new SparseArrayCompat<>();
    private ViewPager mViewPager;
    private View mHeaderLayoutView;
    private ViewPagerHeaderHelper mViewPagerHeaderHelper;

    private int mTouchSlop;
    //private int mTabHeight;
    private int mHeaderHeight;

    private Interpolator mInterpolator = new DecelerateInterpolator();
    private LinearLayout myView;
    private  DoctorProfileData profileData;

    private  SearchResultDoctorListData data;

    @Override
    protected void setMyContectntView() {
        super.setMyContectntView();
        setContentView(R.layout.activity_menu_profile_layout);
        // myInit();


        Object obj = getObjectFromIntent(getIntent(), ListenerKey.ObjectKey.SEARCH_RESULT_DOCTOR_LIST_DATA_KEY);

        if (obj != null && obj instanceof SearchResultDoctorListData) {
            data = (SearchResultDoctorListData) obj;

            // showToastMessage("Data :" + data);
        } else {
            showMyAlertDialog("Sorry", "Doctor’s full profile is not updated", "Close", true);
            return;
        }

        if (data == null) {
            showMyAlertDialog("Sorry", "Doctor’s full profile is not updated", "Close", true);
            return;

        }

          myView = findViewById(R.id.header_view_linear);
       // myInit();
        callDoctorProfileMapper(data);



    }


    private void callDoctorProfileMapper(final SearchResultDoctorListData data) {
        //String doctorid, String clinicid,String branchid
       // Utils.showToastMessage("callDoctorProfileMapper");
        DoctorProfileMapper mapper = new DoctorProfileMapper(data.getDocId(), data.getClinicId(), data.getBranchId());
        mapper.setOnDoctorProfileListenerr(new DoctorProfileMapper.DoctorProfileListener() {
            @Override
            public void getDoctorProfileAPI(DoctorProfileAPI doctorProfileAPI, String errorMessage) {
                if (!isValidResponse(doctorProfileAPI, errorMessage) || doctorProfileAPI.getData() == null) {
               /*     showMyDialog("Alert", "Doctor’s full profile is not updated", new ApiErrorDialogInterface() {
                        @Override
                        public void onCloseClick() {
                            finish();
                        }

                        @Override
                        public void retryClick() {
                            callDoctorProfileMapper(data);
                        }
                    });*/
                    showMyAlertDialog("Sorry", "Doctor’s full profile is not updated", "Close", true);
                    return;
                }
                if (doctorProfileAPI.getData() == null) {
                    /*showMyDialog("Alert", "Unable to get Doctor profile,Please try again", new ApiErrorDialogInterface() {
                        @Override
                        public void onCloseClick() {
                            finish();
                        }

                        @Override
                        public void retryClick() {
                            callDoctorProfileMapper(data);
                        }
                    });
                    return;*/

                    showMyAlertDialog("Sorry", "Doctor’s full profile is not updated", "Close", true);
                    return;
                }
                profileData=doctorProfileAPI.getData();
                myInit(profileData);

            }
        });
    }

  /*  @Override
    protected void onResume() {
        super.onResume();

        MyApplication.showTransparentDialog();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MyApplication.hideTransaprentDialog();
                if(profileData!=null)
                    computeHeaderHeight();
            }
        },500);

    }*/

    public void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView titleName = toolbar.findViewById(R.id.title_logo_tv);
        //((Toolbar) findViewById(R.id.toolbar)).setTitle(deal.getEntityName());
        // titleName.setText(Utils.getStringFromResources(R.string.title_activity_appointments));
        titleName.setText("Profile");
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
    protected AbstractFragment getFragment() {
        return null;
    }





    private void myInit(final DoctorProfileData profileData) {
        ButtonFloat fab = findViewById(R.id.fab_all);

        fab.setOnMyClickListener(new ButtonFloat.MyClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=  new Intent(MyApplication.getCurrentActivityContext(),SingleDoctorMapActivity.class);
                           /*((Activity) MyApplication.getCurrentActivityContext()).getIntent();*/
                intent.putExtra(ListenerKey.ObjectKey.SEARCH_RESULT_DOCTOR_LIST_DATA_KEY,new Messenger(new AbstractIBinder(){
                    @Override
                    protected IntentObjectListener getMyObject() {
                        return new IntentObjectListener(){

                            @Override
                            public Object getObject() {
                                if(TextUtils.isEmpty(data.getGoogleMapLatitude()))
                                {
                                    data.setGoogleMapLatitude(profileData.getLatitude());
                                }
                                if(TextUtils.isEmpty(data.getGoogleMapLongitude()))
                                {
                                    data.setGoogleMapLongitude(profileData.getLongitude());
                                }
                                return data;
                            }
                        };
                    }
                }));
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                MyApplication.getCurrentActivityContext().startActivity(intent);
            }
        });


        findViewById(R.id.appointment_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=  new Intent(MyApplication.getCurrentActivityContext(),ScheduleAppointmentActivity.class);
                           /*((Activity) MyApplication.getCurrentActivityContext()).getIntent();*/
                intent.putExtra(ListenerKey.ObjectKey.SEARCH_RESULT_DOCTOR_LIST_DATA_KEY,new Messenger(new AbstractIBinder(){
                    @Override
                    protected IntentObjectListener getMyObject() {
                        return new IntentObjectListener(){

                            @Override
                            public Object getObject() {
                                return data;
                            }
                        };
                    }
                }));
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);

            }
        });


        //Start
       final AppCompatButton book_appointment_tv= findViewById(R.id.appointment_bt);

        if((!TextUtils.isEmpty(data.getInstantBooking()))  && data.getInstantBooking().equalsIgnoreCase("1")) {
            book_appointment_tv.setText("Book Appointment");
            book_appointment_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Utils.showToastMsg("Coming soon");


                    MyApplication.showTransparentDialog();
                    PatientData patientData = MyApplication.getInstance().getLoggedUserDetailsFromSharedPref();
                    MyApplication.hideTransaprentDialog();

                    if(patientData==null)
                    {

                        showMyDialog("Alert", Utils.getStringFromResources(R.string.non_logged_user_book_appointment_alert_msz), "Ok", "Cancel", new ApiErrorDialogInterface() {
                            @Override
                            public void onCloseClick() {



                            }

                            @Override
                            public void retryClick() {

                                MyApplication.getInstance().setSearchResultDoctorListData(data);

                                Intent intent = new Intent(MyApplication.getCurrentActivityContext(), SigninActivity.class);
                                /*((Activity) MyApplication.getCurrentActivityContext()).getIntent();*/
                                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                MyApplication.getCurrentActivityContext().startActivity(intent);
                                ((Activity) MyApplication.getCurrentActivityContext()).finish();

                            }
                        });

                        return;
                    }




                    Intent intent = new Intent(MyApplication.getCurrentActivityContext(), ScheduleAppointmentActivity.class);
                           /*((Activity) MyApplication.getCurrentActivityContext()).getIntent();*/
                    intent.putExtra(ListenerKey.ObjectKey.SEARCH_RESULT_DOCTOR_LIST_DATA_KEY, new Messenger(new AbstractIBinder() {
                        @Override
                        protected IntentObjectListener getMyObject() {
                            return new IntentObjectListener() {

                                @Override
                                public Object getObject() {
                                    return data;
                                }
                            };
                        }
                    }));
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    MyApplication.getCurrentActivityContext().startActivity(intent);
                }
            });
        }else
        {
            book_appointment_tv.setText("View Contact Number");
            book_appointment_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(TextUtils.isEmpty(data.getPrimaryMobileNo()))
                    {
                        book_appointment_tv.setText("Not Available");
                        book_appointment_tv.setOnClickListener(null);
                    }else
                    {
                        book_appointment_tv.setText(data.getPrimaryMobileNo());
                        book_appointment_tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Utils.openPhoneDialScreen(data.getPrimaryMobileNo());
                            }
                        });

                    }

                }
            });

        }

        //End

        ImageView imageView = findViewById(R.id.profile_iv1);
        if (imageView != null) {
            //MyApplication.getInstance().setBitmapToImageviewCircular(R.drawable.profile_icon, imageView, profileData.getDoctorLogo());

            //START
            if (!TextUtils.isEmpty(profileData.getDoctorLogo())) {
                MyApplication.getInstance().setBitmapToImageviewCircular(R.drawable.profile_icon, imageView, profileData.getDoctorLogo());
            } else {
                if (TextUtils.isEmpty(data.getGender())) {
                    MyApplication.getInstance().setBitmapToImageviewCircular(R.drawable.profile_icon, imageView, Constants.DefaultImage.UNISEX_URL);

                } else {
                    switch (data.getGender().trim().toLowerCase()) {
                        case "male":
                            MyApplication.getInstance().setBitmapToImageviewCircular(R.drawable.profile_icon, imageView, Constants.DefaultImage.MALE_URL);
                            break;
                        case "female":
                            MyApplication.getInstance().setBitmapToImageviewCircular(R.drawable.profile_icon, imageView, Constants.DefaultImage.FEMALE_URL);

                            break;
                        default:
                            MyApplication.getInstance().setBitmapToImageviewCircular(R.drawable.profile_icon, imageView, Constants.DefaultImage.UNISEX_URL);

                            break;
                    }
                }

            }
        }
        //END

        ((TextView)findViewById(R.id.doctor_name_tv)).setText(profileData.getDoctorfullname());
        ((TextView)findViewById(R.id.doctor_designation_tv)).setText(profileData.getDoctorsdegrees());

        MaterialRatingBar ratingBar=findViewById(R.id.doctor_rating_bar);
        ratingBar.setRating(profileData.getDoctoroverallrating());
       // ratingBar.setEnabled(false);
        ratingBar.setClickable(false);

        (( TextView)findViewById(R.id.recommendations_count_tv)).setText(profileData.getTotalrecommend());



        //  ((MaterialRatingBar)findViewById(R.id.doctor_designation_tv)).setText(profileData.getDoctorsdegrees());


        mTouchSlop = ViewConfiguration.get(this).getScaledTouchSlop();
        // mTabHeight = getResources().getDimensionPixelSize(R.dimen._50dp);
        mHeaderHeight = getResources().getDimensionPixelSize(R.dimen._185dp);
        //Utils.showToastMessage("mHeaderHeight "+mHeaderHeight);

        mViewPagerHeaderHelper = new ViewPagerHeaderHelper(this, this);

        TouchCallbackLayout touchCallbackLayout = (TouchCallbackLayout) findViewById(R.id.layout);
        touchCallbackLayout.setTouchEventListener(this);

        mHeaderLayoutView = findViewById(R.id.header);
       // final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabHost);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(new DoctorProfileViewPagerAdapter(profileData,getSupportFragmentManager()));

        //slidingTabLayout.setViewPager(mViewPager);
        ((TabLayout) findViewById(R.id.tabHost)).setupWithViewPager(mViewPager);

        mViewPager.setTranslationY(mHeaderHeight);
    }

    @Override
    public boolean onLayoutInterceptTouchEvent(MotionEvent event) {

        return mViewPagerHeaderHelper.onLayoutInterceptTouchEvent(event,
                /*mTabHeight +*/ mHeaderHeight);
    }

    @Override
    public boolean onLayoutTouchEvent(MotionEvent event) {
        return mViewPagerHeaderHelper.onLayoutTouchEvent(event);
    }

    @Override
    public boolean isViewBeingDragged(MotionEvent event) {
        /*if(mScrollableListenerArrays==null || mViewPager==null || mScrollableListenerArrays.valueAt(mViewPager.getCurrentItem())==null)
        {
            return true;
        }*/
        return mScrollableListenerArrays.valueAt(mViewPager.getCurrentItem())
                .isViewBeingDragged(event);
    }

    @Override
    public void onMoveStarted(float y) {

    }

    @Override
    public void onMove(float y, float yDx) {
        float headerTranslationY = mHeaderLayoutView.getTranslationY() + yDx;
        if (headerTranslationY >= 0) { // pull end
            headerExpand(0L);
        } else if (headerTranslationY <= -mHeaderHeight) { // push end
            headerFold(0L);
        } else {
            ViewCompat.animate(mHeaderLayoutView)
                    .translationY(headerTranslationY)
                    .setDuration(0)
                    .start();
            ViewCompat.animate(mViewPager)
                    .translationY(headerTranslationY + mHeaderHeight)
                    .setDuration(0)
                    .start();
        }
    }

    @Override
    public void onMoveEnded(boolean isFling, float flingVelocityY) {

        float headerY = mHeaderLayoutView.getTranslationY(); // 0到负数
        if (headerY == 0 || headerY == -mHeaderHeight) {
            return;
        }

        if (mViewPagerHeaderHelper.getInitialMotionY() - mViewPagerHeaderHelper.getLastMotionY()
                < -mTouchSlop) {  // pull > mTouchSlop = expand
            headerExpand(headerMoveDuration(true, headerY, isFling, flingVelocityY));
        } else if (mViewPagerHeaderHelper.getInitialMotionY()
                - mViewPagerHeaderHelper.getLastMotionY()
                > mTouchSlop) { // push > mTouchSlop = fold
            headerFold(headerMoveDuration(false, headerY, isFling, flingVelocityY));
        } else {
            if (headerY > -mHeaderHeight / 2f) {  // headerY > header/2 = expand
                headerExpand(headerMoveDuration(true, headerY, isFling, flingVelocityY));
            } else { // headerY < header/2= fold
                headerFold(headerMoveDuration(false, headerY, isFling, flingVelocityY));
            }
        }
    }

    private long headerMoveDuration(boolean isExpand, float currentHeaderY, boolean isFling,
                                    float velocityY) {

        long defaultDuration = DEFAULT_DURATION;

        if (isFling) {

            float distance = isExpand ? Math.abs(mHeaderHeight) - Math.abs(currentHeaderY)
                    : Math.abs(currentHeaderY);
            velocityY = Math.abs(velocityY) / 1000;

            defaultDuration = (long) (distance / velocityY * DEFAULT_DAMPING);

            defaultDuration =
                    defaultDuration > DEFAULT_DURATION ? DEFAULT_DURATION : defaultDuration;
        }

        return defaultDuration;
    }

    private void headerFold(long duration) {
        ViewCompat.animate(mHeaderLayoutView)
                .translationY(-mHeaderHeight)
                .setDuration(duration)
                .setInterpolator(mInterpolator)
                .start();

        ViewCompat.animate(mViewPager).translationY(0).
                setDuration(duration).setInterpolator(mInterpolator).start();

        mViewPagerHeaderHelper.setHeaderExpand(false);
    }

    private void headerExpand(long duration) {
        ViewCompat.animate(mHeaderLayoutView)
                .translationY(0)
                .setDuration(duration)
                .setInterpolator(mInterpolator)
                .start();

        ViewCompat.animate(mViewPager)
                .translationY(mHeaderHeight)
                .setDuration(duration)
                .setInterpolator(mInterpolator)
                .start();
        mViewPagerHeaderHelper.setHeaderExpand(true);
    }

    @Override
    public void onFragmentAttached(ScrollableListener listener, int position) {
        mScrollableListenerArrays.put(position, listener);
    }

    @Override
    public void onFragmentDetached(ScrollableListener listener, int position) {
        mScrollableListenerArrays.remove(position);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            if (position == 3) {
                return ScrollViewFragment.newInstance(position);
            }
            return ListViewFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Profile";
                case 1:
                    return "Reviews";

            }

            return "";
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
    }
}
