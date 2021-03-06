package com.vempower.eezyclinic.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.core.SocialLoginDetails;
import com.vempower.eezyclinic.fragments.SocialLoginFragment;
import com.vempower.eezyclinic.interfaces.SocialLoginListener;

import static com.vempower.eezyclinic.utils.Utils.showToastMessage;


/**
 * Created by Satish on 11/6/2017.
 */

public class FaceBookLoginActivity extends AbstractFragmentActivity /*implements FragmentManager.OnBackStackChangedListener*/ {
    private static final String SOCIAL_NETWORK_TAG = "SocialIntegrationMain.SOCIAL_NETWORK_TAG";
    private static ProgressDialog pd;
    //static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_main);
      //  context = this;
        //getSupportFragmentManager().addOnBackStackChangedListener(this);
        //homeAsUpByBackStack();

       /* if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new FacebookFragment())
                    .commit();
        }*/
        onShowFacebookButton();
    }


    public void onShowFacebookButton()
    {
        /*Intent intent= new Intent(this,FaceBookLoginActivity.class);
        startActivity(intent);*/
      /*  final OnRequestSocialPersonCompleteListener listener= new OnRequestSocialPersonCompleteListener() {
            @Override
            public void onRequestSocialPersonSuccess(int socialNetworkId, SocialPerson socialPerson) {
               // AbstractSignUpInActivity.hideProgress();
                String name= socialPerson.name;
                String id= socialPerson.id;
                String email = socialPerson.email;
                String socialPersonString = socialPerson.toString();
                String infoString = socialPersonString.substring(socialPersonString.indexOf("{")+1, socialPersonString.lastIndexOf("}"));
                String  info=infoString.replace(", ", "\n");
                String  avatarURL=socialPerson.avatarURL;
       *//* Picasso.with(getActivity())
                .load(socialPerson.avatarURL)
                .into(photo);*//*
                 String person_info="Email "+email+"\nName:"+name+"\nId"+id+"\ninfo"+info+"\nImage URL"+avatarURL;
                 showToastMessage(person_info);

                //social_type=1(google)social_type=2(Facebook)
               *//* SocialSignupMapper mapper = new SocialSignupMapper(email,id,2+"");
                mapper.setOnGoogleSignupListener(new SocialSignupMapper.SocialSignupListener() {
                    @Override
                    public void getUserAPI(UserAPI userAPI) {
                        validateUser(userAPI);
                    }
                });*//*


            }

            @Override
            public void onError(int socialNetworkID, String requestID, String errorMessage, Object data) {
                //showProgress("ERROR: " + errorMessage);
            }
        };*/
        SocialLoginFragment socialLoginFragment = new SocialLoginFragment(new SocialLoginListener() {
            @Override
            public void getLoginDetails(SocialLoginDetails details) {
                if(details!=null)
                {
                    showToastMessage(details.toString());
                }
            }
        });



        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, socialLoginFragment)
                .commit();
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public void onBackStackChanged() {
        homeAsUpByBackStack();
    }
*/
    private void homeAsUpByBackStack() {
       /* int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        if (backStackEntryCount > 0) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }*/
    }

  /*  @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getSupportFragmentManager().popBackStack();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }*/

    /*public static void showProgress(String message) {
        pd = new ProgressDialog(context);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage(message);
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
        pd.show();
    }

    public static void hideProgress() {
        pd.dismiss();
    }
*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(SOCIAL_NETWORK_TAG);
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
