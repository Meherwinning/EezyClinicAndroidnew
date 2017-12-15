package com.vempower.eezyclinic.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.rey.material.widget.Switch;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.callbacks.FilterRefreshListListener;
import com.vempower.eezyclinic.callbacks.HomeBottomItemClickListener;
import com.vempower.eezyclinic.callbacks.ListenerKey;
import com.vempower.eezyclinic.core.SearchRequest;
import com.vempower.eezyclinic.fragments.AbstractFragment;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.eezyclinic.views.MySwitch;

public class FilterActivity extends AbstractFragmentActivity /*implements MySwitch.OnChangeAttemptListener, CompoundButton.OnCheckedChangeListener*/ {

    private MySwitch search_type_switch;
    //private TextView search_type_switch_tv;
    private SearchRequest requestParms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        setActionBar();
        myInit();
    }

    private void myInit() {


        requestParms = MyApplication.getInstance().getSearchRequestParms();
        if(requestParms==null)
        {
            requestParms= new SearchRequest(Constants.RESULT_PAGE_ITEMS_LIMIT);
        }


        search_type_switch = (MySwitch)findViewById(R.id.switch1);
        //search_type_switch_tv = findViewById(R.id.search_type_switch_tv);


        search_type_switch.setChecked(requestParms.getSearchtype().equalsIgnoreCase(SearchRequest.DOCTOR_TYPE)?false:true);
        //search_type_switch_tv.setText(requestParms.getSearchtype());

       // search_type_switch.setOnChangeAttemptListener(this);
        //search_type_switch.setOnCheckedChangeListener(this);
        search_type_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!b)
                {
                    requestParms.setSearchtype(SearchRequest.DOCTOR_TYPE);

                }else
                {
                    requestParms.setSearchtype(SearchRequest.CLINIC_TYPE);
                }
            }
        });


    }


    public void onApplyButtonClick(View view)
    {

        MyApplication.getInstance().setSearchRequestParms(requestParms);

        Intent intent= getIntent(); //new Intent(this,HomeActivity.class);
        intent.setClass(this,DoctorsListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |  Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        sendHandlerMessage(getIntent(), ListenerKey.FILTER_REFRESH_LIST_LISTENER_KEY, getFilterRefreshListListener(requestParms.getSearchtype()));


    }


    public void onClearAllButtonClick(View view)
    {

        Utils.showToastMsg("Coming soon");
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.close_iv:
                // ProjectsActivity is my 'home' activity
                 finish();
                return true;
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    public void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // getSupportActionBar().setHomeButtonEnabled(true);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView titleName = toolbar.findViewById(R.id.title_logo_tv);
        //((Toolbar) findViewById(R.id.toolbar)).setTitle(deal.getEntityName());
        if(titleName!=null)
        titleName.setText(Utils.getStringFromResources(R.string.title_activity_filter_title));

        ImageView imageView= toolbar.findViewById(R.id.close_iv);
        if(imageView!=null)
        {
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }

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

    private FilterRefreshListListener getFilterRefreshListListener(final String searchType) {
        return new FilterRefreshListListener() {
            public String refreshList() {
                return searchType;
            }
        };
    }



}
