package com.vempower.eezyclinic.fragments;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;
import com.vempower.eezyclinic.activities.AbstractSocialLoginActivity;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.interfaces.SocialLoginListener;
import com.vempower.eezyclinic.utils.Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LinkedinFragment extends GoolePlusFragment {
    private static final String TAG = LinkedinFragment.class.getSimpleName();
    public static final String PACKAGE = "com.vempower.eezyclinic";
    private SocialLoginListener socialLoginListener;

    //Button login_linkedin_btn,hask_key;
/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        login_linkedin_btn = (Button) findViewById(R.id.login_button);
        hask_key = (Button) findViewById(R.id.show_hash);
        login_linkedin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login_linkedin();
            }
        });

        //Compute application package and hash

        hask_key.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateHashkey();
            }
        });
    }*/

    // Authenticate with linkedin and intialize Session.

    public void login_linkedin(){
        if(!Utils.isNetworkAvailable())
        {
            Utils.showToastMsgForNetworkNotAvalable();
            return;
        }

        LISessionManager.getInstance(MyApplication.getCurrentActivityContext()).init((AbstractSocialLoginActivity)MyApplication.getCurrentActivityContext(), buildScope(), new AuthListener() {
            @Override
            public void onAuthSuccess() {

                // Toast.makeText(getApplicationContext(), "success" + LISessionManager.getInstance(getApplicationContext()).getSession().getAccessToken().toString(), Toast.LENGTH_LONG).show();
                //login_linkedin_btn.setVisibility(View.GONE);

            }

            @Override
            public void onAuthError(LIAuthError error) {
                if(error!=null) {
                    Utils.showToastMessage("Failed " + error.toString());
                }

               // Toast.makeText(getApplicationContext(), "failed " + error.toString(), Toast.LENGTH_LONG).show();
            }
        }, true);
    }

    // After complete authentication start new HomePage Activity



    // This method is used to make permissions to retrieve data from linkedin

    private static Scope buildScope() {
        return Scope.build(Scope.R_BASICPROFILE, Scope.R_EMAILADDRESS);
    }

    // This Method is used to generate "Android Package Name" hash key

    public void generateHashkey(){
        try {
            PackageInfo info = MyApplication.getCurrentActivityContext().getPackageManager().getPackageInfo(
                    PACKAGE,
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());

               // ((TextView) findViewById(R.id.package_name)).setText(info.packageName);
                String hashkey= Base64.encodeToString(md.digest(), Base64.NO_WRAP);
               // ((TextView) findViewById(R.id.hash_key)).setText(hashkey);

                Log.i("package_name",info.packageName);
                Log.i("Hashkey",hashkey);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.d(TAG, e.getMessage(), e);
        } catch (NoSuchAlgorithmException e) {
            Log.d(TAG, e.getMessage(), e);
        }
    }



}