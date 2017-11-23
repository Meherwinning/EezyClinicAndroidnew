package com.vempower.eezyclinic.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.github.gorbin.asne.core.AccessToken;
import com.github.gorbin.asne.core.listener.OnRequestSocialPersonCompleteListener;
import com.github.gorbin.asne.core.persons.SocialPerson;
import com.linkedin.platform.APIHelper;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIApiError;
import com.linkedin.platform.listeners.ApiListener;
import com.linkedin.platform.listeners.ApiResponse;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.core.SocialLoginDetails;
import com.vempower.eezyclinic.fragments.FacebookFragment;
import com.vempower.eezyclinic.interfaces.SocialLoginListener;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.Utils;

import org.json.JSONObject;

/**
 * Created by satish on 22/11/17.
 */

public class AbstractSocialLoginActivity extends AbstractFragmentActivity {
    public static final String SOCIAL_NETWORK_TAG = "SocialIntegrationMain.SOCIAL_NETWORK_TAG";

    //private OnRequestSocialPersonCompleteListener listener;
    public void onShowFacebookButton() {
        /*Intent intent= new Intent(this,FaceBookLoginActivity.class);
        startActivity(intent);*/
       /* listener = new OnRequestSocialPersonCompleteListener() {
            @Override
            public void onRequestSocialPersonSuccess(int socialNetworkId, SocialPerson socialPerson) {
                // AbstractSignUpInActivity.hideProgress();
                String name = socialPerson.name;
                String id = socialPerson.id;
                String email = socialPerson.email;
                String socialPersonString = socialPerson.toString();
                String infoString = socialPersonString.substring(socialPersonString.indexOf("{") + 1, socialPersonString.lastIndexOf("}"));
                String info = infoString.replace(", ", "\n");
                String avatarURL = socialPerson.avatarURL;
       *//* Picasso.with(getActivity())
                .load(socialPerson.avatarURL)
                .into(photo);*//*
                String person_info = "Email " + email + "\nName:" + name + "\nId" + id + "\ninfo" + info + "\nImage URL" + avatarURL;
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
        FacebookFragment facebookFragment = new FacebookFragment(socialLoginListener);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.social_container, facebookFragment)
                .commit();
    }

    private SocialLoginListener socialLoginListener= new SocialLoginListener() {
        @Override
        public void getLoginDetails(SocialLoginDetails details) {
            if(details!=null) {
                showToastMessage(details.toString());
            }
        }
    };


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode,  resultCode,  data);
        if(resultCode== RESULT_OK && requestCode == LISessionManager.LI_SDK_AUTH_REQUEST_CODE) {
            LISessionManager.getInstance(MyApplication.getCurrentActivityContext()).onActivityResult(this, requestCode, resultCode, data);

            //Intent intent = new Intent(this,UserProfile.class);
            //startActivity(intent);
            getLinkedinUserDetails();
        }


        Fragment fragment = getSupportFragmentManager().findFragmentByTag(SOCIAL_NETWORK_TAG);
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void getLinkedinUserDetails(){
        final String host = "api.linkedin.com";
        final String topCardUrl = "https://" + host + "/v1/people/~:(id,email-address,formatted-name,phone-numbers,public-profile-url,picture-url,picture-urls::(original))";

        APIHelper apiHelper = APIHelper.getInstance(MyApplication.getCurrentActivityContext());
        apiHelper.getRequest(MyApplication.getCurrentActivityContext(), topCardUrl, new ApiListener() {
            @Override
            public void onApiSuccess(ApiResponse result) {
                try {

                    setUserProfile(result.getResponseDataAsJson());

                } catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onApiError(LIApiError error) {
                if(error!=null) {
                    Utils.showToastMessage(error.toString());
                    //((TextView) findViewById(R.id.error)).setText(error.toString());
                }

            }
        });
    }

    public  void  setUserProfile(JSONObject response){

        try {
            String email=  response.get("emailAddress").toString();
            String name = response.get("formattedName").toString();
            String id = response.get("id").toString();
            String accesstoken= LISessionManager.getInstance(MyApplication.getCurrentActivityContext()).getSession().getAccessToken().getValue();

           // String linkedinDetail="id:"+id+"\nname:"+name+"\nemail:"+email+"\naccesstoken:"+accesstoken;
           /* user_email.setText(response.get("emailAddress").toString());
            user_name.setText(response.get("formattedName").toString());

            Picasso.with(this).load(response.getString("pictureUrl"))
                    .into(profile_pic);*/
           //showToastMessage(linkedinDetail);
            SocialLoginDetails details= new SocialLoginDetails(accesstoken, Constants.MediaType.LINKEDIN_TYPE,id,email,Utils.getDeviceId());
            if (socialLoginListener != null) {
                socialLoginListener.getLoginDetails(details);
            }
            LISessionManager.getInstance(getApplicationContext()).clearSession();
        } catch (Exception e){
            e.printStackTrace();
        }
    }


}
