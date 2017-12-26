package com.vempower.eezyclinic.activities;

import android.os.Handler;
import android.os.Messenger;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.callbacks.AbstractAppHandler;
import com.vempower.eezyclinic.callbacks.FromActivityListener;
import com.vempower.eezyclinic.callbacks.HomeBottomItemClickListener;
import com.vempower.eezyclinic.callbacks.ListenerKey;
import com.vempower.eezyclinic.fragments.AbstractFragment;
import com.vempower.eezyclinic.fragments.FollowupsFragment;
import com.vempower.eezyclinic.fragments.SearchFragment;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.Utils;

public class SearchActivity extends AbstractMenuActivity {


    private SearchFragment searchFragment;
    private TextView titleName;
    private String title;

    @Override
    protected void setMyContectntView() {
        getIntent().putExtra(ListenerKey.FROM_ACTIVITY_LISTENER_KEY, new Messenger(getTitleChangeKeyListener()));

        super.setMyContectntView();
        boolean isFromDashboard=false;
        title="-";
        if(getIntent()!=null && getIntent().hasExtra(Constants.Pref.IS_FROM_DASH_BOARD))
        {
             isFromDashboard=getIntent().getBooleanExtra(Constants.Pref.IS_FROM_DASH_BOARD,false);


            if(isFromDashboard)
            {
                title="Search";
            }else
            {
                title="New Appointment";
            }

            /*if(!TextUtils.isEmpty(titleNameStr))
            {
                getIntent().removeExtra(Constants.Pref.TITLE_BAR_NAME_KEY);
               // getIntent().putExtra(Constants.Pref.TITLE_BAR_NAME_KEY,"");
                title=titleNameStr;
                invalidateOptionsMenu();
            }*/
        }

        if(searchFragment==null) {
            searchFragment = new SearchFragment();
            searchFragment.isFromDashBoard(isFromDashboard);
        }else {
            searchFragment.isFromDashBoard(isFromDashboard);
            searchFragment.refreshUI();
        }



        setFragment( searchFragment);



    }



    public void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(titleName==null)
         titleName = toolbar.findViewById(R.id.title_logo_tv);
        //((Toolbar) findViewById(R.id.toolbar)).setTitle(deal.getEntityName());
       // titleName.setText(Utils.getStringFromResources(R.string.title_activity_search));
        titleName.setText(title);

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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //hideKeyBord(dateofBirth_tv);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideKeyBord(titleName);
            }
        },500);


    }



    @NonNull
    private AbstractAppHandler getTitleChangeKeyListener() {
        return new AbstractAppHandler() {
            @Override
            public void getObject(Object obj) {
                if (obj != null && (obj instanceof FromActivityListener) ) {
                    FromActivityListener listener= (FromActivityListener) obj;
                    //listener.getItemClicked();
                    if(listener.isFromSearchActivity())
                    {
                        title="Search";
                    }else
                    {
                        title="New Appointment";
                    }
                    if(titleName!=null)
                    {
                        titleName.setText(title);
                    }
                    invalidateOptionsMenu();
                    supportInvalidateOptionsMenu();


                }
            }
        };
    }
}
