package com.vempower.eezyclinic.activities;

import android.os.Build;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vempower.eezyclinic.APICore.DoctorProfileData;
import com.vempower.eezyclinic.APICore.SearchResultDoctorListData;
import com.vempower.eezyclinic.APIResponce.DoctorProfileAPI;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.adapters.DoctorProfileViewPagerAdapter;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.callbacks.ListenerKey;
import com.vempower.eezyclinic.fragments.AbstractFragment;
import com.vempower.eezyclinic.fragments.ListViewFragment;
import com.vempower.eezyclinic.fragments.ScrollViewFragment;
import com.vempower.eezyclinic.interfaces.ApiErrorDialogInterface;
import com.vempower.eezyclinic.mappers.DoctorProfileMapper;
import com.vempower.eezyclinic.tools.ScrollableFragmentListener;
import com.vempower.eezyclinic.tools.ScrollableListener;
import com.vempower.eezyclinic.tools.ViewPagerHeaderHelper;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.eezyclinic.widget.TouchCallbackLayout;

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

    @Override
    protected void setMyContectntView() {
        super.setMyContectntView();
        setContentView(R.layout.activity_menu_profile_layout);
        // myInit();


        Object obj = getObjectFromIntent(getIntent(), ListenerKey.ObjectKey.SEARCH_RESULT_DOCTOR_LIST_DATA_KEY);
        SearchResultDoctorListData data;
        if (obj != null && obj instanceof SearchResultDoctorListData) {
            data = (SearchResultDoctorListData) obj;

            // showToastMessage("Data :" + data);
        } else {
            showMyAlertDialog("Alert", "Invalid Doctor profile.Please try again", "Close", true);
            return;
        }

        if (data == null) {
            showMyAlertDialog("Alert", "Invalid Doctor profile.Please try again", "Close", true);
            return;

        }

          myView = findViewById(R.id.header_view_linear);
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
                    showMyDialog("Alert", "Unable to get Doctor profile,Please try again", new ApiErrorDialogInterface() {
                        @Override
                        public void onCloseClick() {
                            finish();
                        }

                        @Override
                        public void retryClick() {
                            callDoctorProfileMapper(data);
                        }
                    });
                    return;
                }
                if (doctorProfileAPI.getData() == null) {
                    showMyDialog("Alert", "Unable to get Doctor profile,Please try again", new ApiErrorDialogInterface() {
                        @Override
                        public void onCloseClick() {
                            finish();
                        }

                        @Override
                        public void retryClick() {
                            callDoctorProfileMapper(data);
                        }
                    });
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

        mTouchSlop = ViewConfiguration.get(this).getScaledTouchSlop();
        // mTabHeight = getResources().getDimensionPixelSize(R.dimen._50dp);
        mHeaderHeight = getResources().getDimensionPixelSize(R.dimen._190dp);
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
