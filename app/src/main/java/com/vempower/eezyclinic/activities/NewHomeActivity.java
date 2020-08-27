package com.vempower.eezyclinic.activities;

import android.os.Messenger;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.callbacks.AbstractAppHandler;
import com.vempower.eezyclinic.callbacks.HomeBottomItemClickListener;
import com.vempower.eezyclinic.callbacks.ListenerKey;
import com.vempower.eezyclinic.callbacks.NewHomeClickListener;
import com.vempower.eezyclinic.fragments.AbstractFragment;
import com.vempower.eezyclinic.fragments.DemoFragment;
import com.vempower.eezyclinic.fragments.NewHomeFragment;
import com.vempower.eezyclinic.utils.Utils;

public class NewHomeActivity extends AbstractMenuActivity {

    DemoFragment demoFragment;

    public void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView titleName = toolbar.findViewById(R.id.title_logo_tv);
        //((Toolbar) findViewById(R.id.toolbar)).setTitle(deal.getEntityName());
        titleName.setText("New Home");

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
    protected void setMyContectntView() {
        super.setMyContectntView();
       // getIntent().putExtra(ListenerKey.NEW_HOME_FEATURS_AND_BENIFITS_LISTENER_KEY, new Messenger(getNewHomeKeyListener()));

    }

    @Override
    protected AbstractFragment getFragment() {
        demoFragment=new DemoFragment();
        return demoFragment;
    }

    @Override
    public void showFeaturesAndBenifitsView()
    {
        if(demoFragment!=null)
        {
            demoFragment.gotToFeaturesAndBenifitsView();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @NonNull
    private AbstractAppHandler getNewHomeKeyListener() {
        return new AbstractAppHandler() {
            @Override
            public void getObject(Object obj) {
                if (obj != null && (obj instanceof NewHomeClickListener) ) {
                    NewHomeClickListener listener= (NewHomeClickListener) obj;
                    if(listener.isClicked())
                    {
                        showFeaturesAndBenifitsView();
                    }
                    //listener.getItemClicked();
                    //bottomItemChange(listener.getItemClicked());

                }
            }
        };
    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
    }
}
