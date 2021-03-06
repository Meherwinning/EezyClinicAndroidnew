package com.vempower.eezyclinic.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Messenger;

import androidx.collection.SparseArrayCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.gc.materialdesign.views.ButtonFloat;
import com.google.android.material.tabs.TabLayout;
import com.vempower.eezyclinic.APICore.ClinicProfileData;
import com.vempower.eezyclinic.APICore.SearchResultClinicData;
import com.vempower.eezyclinic.APIResponce.ClinicProfileAPI;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.adapters.ClinicProfileViewPagerAdapter;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.callbacks.ListenerKey;
import com.vempower.eezyclinic.fragments.AbstractFragment;
import com.vempower.eezyclinic.fragments.ListViewFragment;
import com.vempower.eezyclinic.fragments.ScrollViewFragment;
import com.vempower.eezyclinic.interfaces.AbstractIBinder;
import com.vempower.eezyclinic.interfaces.ApiErrorDialogInterface;
import com.vempower.eezyclinic.interfaces.IntentObjectListener;
import com.vempower.eezyclinic.mappers.ClinicProfileMapper;
import com.vempower.eezyclinic.tools.ScrollableFragmentListener;
import com.vempower.eezyclinic.tools.ScrollableListener;
import com.vempower.eezyclinic.tools.ViewPagerHeaderHelper;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.Utils;
 ;
import com.vempower.eezyclinic.widget.TouchCallbackLayout;

public class ClinicProfileActivity extends AbstractMenuActivity
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
    private ClinicProfileData clinicProfileData;

    private Interpolator mInterpolator = new DecelerateInterpolator();
    private SearchResultClinicData data;

    @Override
    protected void setMyContectntView() {
        super.setMyContectntView();
        setContentView(R.layout.activity_menu_clinic_profile_layout);
        // myInit();


        Object obj = getObjectFromIntent(getIntent(), ListenerKey.ObjectKey.SEARCH_RESULT_CLINIC_LIST_DATA_KEY);

        if (obj!=null && obj instanceof SearchResultClinicData) {
            data = (SearchResultClinicData) obj;
           // showToastMessage("Data :" + data);
        }else
        {
            showMyAlertDialog("Alert","Invalid Clinic profile.Please try again","Close",true);
           return;
        }

        if(data==null)
        {
            showMyAlertDialog("Alert","Invalid Clinic profile.Please try again","Close",true);
            return;

        }
       // computeHeaderHeight();

        callClinicProfileMapper(data);


    }

    private void callClinicProfileMapper(final SearchResultClinicData data) {
        //String clinicid, String branchid
        ClinicProfileMapper mapper= new ClinicProfileMapper(data.getClncId(),data.getBrcId());
        mapper.setOnClinicProfileAPIListener(new ClinicProfileMapper.ClinicProfileAPIListener() {
            @Override
            public void getClinicProfileAPI(ClinicProfileAPI clinicProfileAPI, String errorMessage) {
                if (!isValidResponse(clinicProfileAPI, errorMessage) || clinicProfileAPI.getData() == null) {
                    showMyDialog("Alert", "Unable to get Clinic profile, Please try again", new ApiErrorDialogInterface() {
                        @Override
                        public void onCloseClick() {
                            finish();
                        }

                        @Override
                        public void retryClick() {
                            callClinicProfileMapper(data);
                        }
                    });
                    return;
                }
                if (clinicProfileAPI.getData() == null) {
                    showMyDialog("Alert", "Unable to get Clinic profile,Please try again", new ApiErrorDialogInterface() {
                        @Override
                        public void onCloseClick() {
                            finish();
                        }

                        @Override
                        public void retryClick() {
                            callClinicProfileMapper(data);
                        }
                    });
                    return;
                }
                clinicProfileData=clinicProfileAPI.getData();
                myInit(clinicProfileData);
            }
        });
    }

    public void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView titleName = toolbar.findViewById(R.id.title_logo_tv);
        //((Toolbar) findViewById(R.id.toolbar)).setTitle(deal.getEntityName());
        // titleName.setText(Utils.getStringFromResources(R.string.title_activity_appointments));
        titleName.setText("Clinic Profile");
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

   /* private void computeHeaderHeight() {
        final LinearLayout view = findViewById(R.id.header_view_linear);
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mHeaderHeight = view.getHeight(); //height is ready
                myInit();
            }
        });
    }*/

    private void myInit(final ClinicProfileData  clinicProfileData) {
        ButtonFloat fab = findViewById(R.id.fab_all);

        fab.setOnMyClickListener(new ButtonFloat.MyClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = ((Activity)MyApplication.getCurrentActivityContext()).getIntent();
                intent.setClass(MyApplication.getCurrentActivityContext(), SingleClinicMapActivity.class);
                /*((Activity) MyApplication.getCurrentActivityContext()).getIntent();*/
                intent.putExtra(ListenerKey.ObjectKey.SEARCH_RESULT_CLINIC_LIST_DATA_KEY, new Messenger(new AbstractIBinder() {
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
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                MyApplication.getCurrentActivityContext().startActivity(intent);
            }
        });


        final AppCompatButton view_contact_number_bt = findViewById(R.id.view_contact_number_bt);

        view_contact_number_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(clinicProfileData.getClinicContactNumber())) {
                    view_contact_number_bt.setText("Not Available");
                    view_contact_number_bt.setOnClickListener(null);
                } else {
                    view_contact_number_bt.setText(clinicProfileData.getClinicContactNumber());
                    view_contact_number_bt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Utils.openPhoneDialScreen(clinicProfileData.getClinicContactNumber());
                        }
                    });

                }

            }
        });


        ImageView imageView = findViewById(R.id.clinic_profile_iv);
        if (imageView != null) {
            MyApplication.getInstance().setBitmapToImageview(R.drawable.profile_icon, imageView, clinicProfileData.getClinicImage());
            //START
            if (!TextUtils.isEmpty(clinicProfileData.getClinicImage())) {
                MyApplication.getInstance().setBitmapToImageview(R.drawable.profile_icon, imageView, clinicProfileData.getClinicImage());
            } else {
                    MyApplication.getInstance().setBitmapToImageview(R.drawable.profile_icon, imageView, Constants.DefaultImage.CLINIC_URL);
            }
        }
        //END


        ((TextView)findViewById(R.id.clinic_name_tv)).setText(clinicProfileData.getClinicName());
        ((TextView)findViewById(R.id.branch_name_tv)).setText(clinicProfileData.getBranchName());



        mTouchSlop = ViewConfiguration.get(this).getScaledTouchSlop();
        // mTabHeight = getResources().getDimensionPixelSize(R.dimen._50dp);
        mHeaderHeight = getResources().getDimensionPixelSize(R.dimen._160dp);

        mViewPagerHeaderHelper = new ViewPagerHeaderHelper(this, this);

        TouchCallbackLayout touchCallbackLayout = (TouchCallbackLayout) findViewById(R.id.layout);
        touchCallbackLayout.setTouchEventListener(this);

        mHeaderLayoutView = findViewById(R.id.header);
        //final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabHost);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(new ClinicProfileViewPagerAdapter(clinicProfileData,getSupportFragmentManager()));

        //slidingTabLayout.setViewPager(mViewPager);
        ((TabLayout) findViewById(R.id.tabHost)).setupWithViewPager(mViewPager);

        mViewPager.setTranslationY(mHeaderHeight);
        if(getIntent()!=null && getIntent().getBooleanExtra(Constants.Pref.IS_FROM_VIEW_DOCTORS_CLICK_KEY,false)) {
             mViewPager.setCurrentItem(1);
        }
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
