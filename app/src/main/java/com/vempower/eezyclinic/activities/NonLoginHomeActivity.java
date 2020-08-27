package com.vempower.eezyclinic.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Messenger;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.callbacks.AbstractAppHandler;
import com.vempower.eezyclinic.callbacks.ListenerKey;
import com.vempower.eezyclinic.callbacks.NewHomeClickListener;
import com.vempower.eezyclinic.fragments.AbstractFragment;
import com.vempower.eezyclinic.fragments.DemoFragment;
import com.vempower.eezyclinic.fragments.NewHomeFragment;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.Utils;

public class NonLoginHomeActivity extends AbstractMenuActivity {





    private ImageView title_logo_iv;
    private DemoFragment demoFragment;

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

    protected void setMyContectntView()
    {
        getIntent().putExtra(ListenerKey.NEW_HOME_FEATURS_AND_BENIFITS_LISTENER_KEY, new Messenger(getNewHomeKeyListener()));
        super.setMyContectntView();
        //setContentView(R.layout.activity_home_menu_non_login_layout);


            if(getIntent().getBooleanExtra(Constants.Pref.IS_CALL_FEATURS_AND_BENIFITS,false))
            {
                showFeaturesAndBenifitsView();
                getIntent().putExtra(Constants.Pref.IS_CALL_FEATURS_AND_BENIFITS,false);
            }

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
    public void showFeaturesAndBenifitsView()
    {
        if(demoFragment!=null)
        {
            demoFragment.gotToFeaturesAndBenifitsView(true);
        }
    }


    @Override
    protected AbstractFragment getFragment() {
        demoFragment=new DemoFragment();
        return demoFragment;
    }

    public void onLoginClick(View view)
    {
        closeMenu();
       // showToastMessage("Login Click");
        Intent intent= new Intent(this,SigninActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    public void onSignupClick(View view)
    {
        closeMenu();
        Intent intent= new Intent(this,SignUpActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

        //showToastMessage("Signup Click");
    }
    public void  setSidemenuItemsListener()
    {
        findViewById(R.id.non_login_features_and_benefits_linear).setOnClickListener(this);
        findViewById(R.id.non_login_feedback_linear).setOnClickListener(this);
        findViewById(R.id.non_login_signup_tv).setOnClickListener(this);

    }

    public void  setSideMenuCutomerTitle()
    {

    }

}
