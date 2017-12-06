package com.vempower.eezyclinic.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.linkedin.platform.APIHelper;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIApiError;
import com.linkedin.platform.listeners.ApiListener;
import com.linkedin.platform.listeners.ApiResponse;
import com.vempower.eezyclinic.APIResponce.LoginAPI;
import com.vempower.eezyclinic.APIResponce.SignupAPI;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.core.SocialLoginDetails;
import com.vempower.eezyclinic.fragments.SocialLoginFragment;
import com.vempower.eezyclinic.interfaces.ApiErrorDialogInterface;
import com.vempower.eezyclinic.interfaces.SocialLoginListener;
import com.vempower.eezyclinic.mappers.ResendOTPMapper;
import com.vempower.eezyclinic.mappers.SocialSignInMapper;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.SharedPreferenceUtils;
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
        SocialLoginFragment socialLoginFragment = new SocialLoginFragment(socialLoginListener);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.social_container, socialLoginFragment)
                .commit();
    }

    private SocialLoginListener socialLoginListener= new SocialLoginListener() {
        @Override
        public void getLoginDetails(final SocialLoginDetails details) {
            if(details!=null) {
                //showToastMessage(details.toString());
                SocialSignInMapper mapper= new SocialSignInMapper(details);
                mapper.setOnSignInListener(new SocialSignInMapper.SignInListener() {
                    @Override
                    public void getLoginAPI(LoginAPI loginAPI,String errorMessage) {
                        //showToastMessage(loginAPI.toString());
                        validateSocialLoginUserDetails(loginAPI,details,errorMessage);


                    }
                });
            }
        }
    };

    private void validateSocialLoginUserDetails(final LoginAPI loginAPI,final SocialLoginDetails details,String errorMessage) {


        if(loginAPI!=null && loginAPI.getIs_account_activated().equalsIgnoreCase(Constants.ACCOUNT_NOT_ACTIVATE_STATUS_CODE))
        {
            if(true)
            {
                ResendOTPMapper otpMapper= new ResendOTPMapper(loginAPI.getId());
                otpMapper.setOnResendOTPListener(new ResendOTPMapper.ResendOTPListener() {
                    @Override
                    public void getSignupAPI(SignupAPI signupAPI, String errorMessage) {
                        if(!isValidResponse(signupAPI,errorMessage))
                        {
                            return;
                        }
                        Intent intent= new Intent(MyApplication.getCurrentActivityContext(),VerifyOTPActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra(Constants.Pref.OTP_KEY,signupAPI.getOtp());
                        intent.putExtra(VerifyOTPActivity.IS_FROM_RESEND_OTP_KEY,true);
                        intent.putExtra(Constants.Pref.PATIENT_ID_KEY,signupAPI.getData().patientId);

                        startActivity(intent);
                        finish();
                    }
                });
                return;
            }


            //title: String, message: String,  positiveBtnName: String="Retry",negativeBtnName: String="Close", dialogInterface: ApiErrorDialogInterface?
            showMyDialog("Alert", loginAPI.getStatusMessage(), Utils.getStringFromResources(R.string.continue_lbl), Utils.getStringFromResources(R.string.close_lbl), new ApiErrorDialogInterface() {
                @Override
                public void onCloseClick() {
                    //TODO nothing
                }

                @Override
                public void retryClick() {

                    //TODO call
                    ResendOTPMapper otpMapper= new ResendOTPMapper(loginAPI.getId());
                    otpMapper.setOnResendOTPListener(new ResendOTPMapper.ResendOTPListener() {
                        @Override
                        public void getSignupAPI(SignupAPI signupAPI, String errorMessage) {
                            if(!isValidResponse(signupAPI,errorMessage))
                            {
                                return;
                            }
                            Intent intent= new Intent(MyApplication.getCurrentActivityContext(),VerifyOTPActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra(Constants.Pref.OTP_KEY,signupAPI.getOtp());
                            intent.putExtra(VerifyOTPActivity.IS_FROM_RESEND_OTP_KEY,true);
                            intent.putExtra(Constants.Pref.PATIENT_ID_KEY,signupAPI.getData().patientId);

                            startActivity(intent);
                            finish();
                        }
                    });
                }
            });

            return;
        }
        if(!isValidResponse(loginAPI,errorMessage))
        {
            return;
        }

        if(TextUtils.isEmpty(loginAPI.getAccessToken()))
        {
           // showMyAlertDialog("Alert",loginAPI.getStatusMessage() ,"Ok",false);
            Intent intent= new Intent(MyApplication.getCurrentActivityContext(),SocialSignUpActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(Constants.SocialLoginPref.LOGIN_DETAILS_OBJ_KEY,details);
            intent.putExtra(Constants.SocialLoginPref.FORMID_KEY,loginAPI.getPatientData().getForm_id());
            intent.putExtra(Constants.SocialLoginPref.SOCIAL_LOGIN_ID_KEY,loginAPI.getPatientData().getSocial_media_id());
            intent.putExtra(Constants.SocialLoginPref.SOCIAL_MEDIA_TYPE,loginAPI.getPatientData().getSocial_media());

            startActivity(intent);
            finish();

            return;

        }

        if(loginAPI.getPatientData()==null || TextUtils.isEmpty(loginAPI.getAccessToken()))
        {
            showMyAlertDialog("Alert", Utils.getStringFromResources(R.string.invalid_service_response_lbl),"Ok",false);
            return;
        }
        MyApplication.getInstance().setLoggedUserDetailsToSharedPref(loginAPI.getPatientData());
        SharedPreferenceUtils.setStringValueToSharedPrefarence(Constants.Pref.USER_VALIDATION_KEY,loginAPI.getAccessToken());
        Intent intent= new Intent(MyApplication.getCurrentActivityContext(),HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        showToastMessage(Utils.getStringFromResources(R.string.social_signup_msg_lbl)+" "+details.MEDIA_TYPE);
    }


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
            SocialLoginDetails details= new SocialLoginDetails(accesstoken, Constants.MediaType.LINKEDIN_TYPE,id,email,Utils.getDeviceId(),name,response.getString("pictureUrl"));
           // details.setName(name);
            if (socialLoginListener != null) {
                socialLoginListener.getLoginDetails(details);
            }
            LISessionManager.getInstance(getApplicationContext()).clearSession();
        } catch (Exception e){
            e.printStackTrace();
        }
    }


}
