package com.vempower.eezyclinic.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.github.gorbin.asne.core.AccessToken;
import com.github.gorbin.asne.core.listener.OnRequestSocialPersonCompleteListener;
import com.github.gorbin.asne.core.persons.SocialPerson;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.fragments.FacebookFragment;
import com.vempower.eezyclinic.interfaces.FacebookLoginListener;

import static com.vempower.eezyclinic.utils.Utils.showToastMessage;

/**
 * Created by satish on 20/11/17.
 */

public class SigninActivity extends AbstractFragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        onShowFacebookButton();
    }


    public void onShowFacebookButton()
    {
        /*Intent intent= new Intent(this,FaceBookLoginActivity.class);
        startActivity(intent);*/
        final OnRequestSocialPersonCompleteListener listener= new OnRequestSocialPersonCompleteListener() {
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
       /* Picasso.with(getActivity())
                .load(socialPerson.avatarURL)
                .into(photo);*/
                String person_info="Email "+email+"\nName:"+name+"\nId"+id+"\ninfo"+info+"\nImage URL"+avatarURL;
                showToastMessage(person_info);

                //social_type=1(google)social_type=2(Facebook)
               /* SocialSignupMapper mapper = new SocialSignupMapper(email,id,2+"");
                mapper.setOnGoogleSignupListener(new SocialSignupMapper.SocialSignupListener() {
                    @Override
                    public void getUserAPI(UserAPI userAPI) {
                        validateUser(userAPI);
                    }
                });*/


            }

            @Override
            public void onError(int socialNetworkID, String requestID, String errorMessage, Object data) {
                //showProgress("ERROR: " + errorMessage);
            }
        };
        FacebookFragment facebookFragment= new FacebookFragment(new FacebookLoginListener(){

            @Override
            public OnRequestSocialPersonCompleteListener getFacebookListener() {
                return listener;
            }

            @Override
            public void setAccessToken(AccessToken accessToken) {
                // Log.i(MyApplication.getCurrentActivityContext().getPackageName(), accessToken.toString());
                showToastMessage("AccessToken :"+accessToken);
            }
        });



        getSupportFragmentManager().beginTransaction()
                .add(R.id.social_container, facebookFragment)
                .commit();
    }

    public void onSignupClick(View view)
    {
        startActivity(new Intent(this,SignUpActivity.class));
    }



    public void onForgotPasswordClick(View view)
    {
        startActivity(new Intent(this,ForgotPasswordActivity.class));
    }

}
