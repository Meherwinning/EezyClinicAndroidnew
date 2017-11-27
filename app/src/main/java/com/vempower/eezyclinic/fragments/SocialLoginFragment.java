package com.vempower.eezyclinic.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.gorbin.asne.core.AccessToken;
import com.github.gorbin.asne.core.SocialNetwork;
import com.github.gorbin.asne.core.SocialNetworkManager;
import com.github.gorbin.asne.core.listener.OnLoginCompleteListener;
import com.github.gorbin.asne.core.listener.OnRequestAccessTokenCompleteListener;
import com.github.gorbin.asne.core.listener.OnRequestSocialPersonCompleteListener;
import com.github.gorbin.asne.core.persons.SocialPerson;
import com.github.gorbin.asne.facebook.FacebookSocialNetwork;
//import com.github.gorbin.asne.linkedin.LinkedInSocialNetwork;
import com.github.gorbin.asne.twitter.TwitterSocialNetwork;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.activities.AbstractSocialLoginActivity;
import com.vempower.eezyclinic.activities.FaceBookLoginActivity;
import com.vempower.eezyclinic.core.SocialLoginDetails;
import com.vempower.eezyclinic.interfaces.SocialLoginListener;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.vempower.eezyclinic.utils.Utils.showToastMessage;


@SuppressLint("ValidFragment")
public class SocialLoginFragment extends LinkedinFragment implements SocialNetworkManager.OnInitializationCompleteListener, OnLoginCompleteListener {
    public static SocialNetworkManager mSocialNetworkManager;
    /**
     * SocialNetwork Ids in ASNE:
     * 1 - Twitter
     * 2 - LinkedIn
     * 3 - Google Plus
     * 4 - Facebook
     * 5 - Vkontakte
     * 6 - Odnoklassniki
     * 7 - Instagram
     */
    private ImageView facebook;
    private View rootView;
    //private OnRequestSocialPersonCompleteListener completeListener;
    private SocialLoginListener facebookLoginListener;
    private ImageView twitter;
    private ImageView linkedin;
    private ImageView googleplus;

    public SocialLoginFragment() {
    }

    @SuppressLint("ValidFragment")
    public SocialLoginFragment(SocialLoginListener facebookLoginListener) {
       // completeListener = facebookLoginListener.getFacebookListener();
        this.facebookLoginListener = facebookLoginListener;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.facebook_fragment, container, false);
        //((MainActivity)getActivity()).getSupportActionBar().setTitle(R.string.app_name);
        // init buttons and set Listener
        facebook = rootView.findViewById(R.id.facebook);
        facebook.setOnClickListener(loginClick);
        twitter = rootView.findViewById(R.id.twitter);
        twitter.setOnClickListener(loginClick);
        linkedin = rootView.findViewById(R.id.linkedin);
        linkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login_linkedin();
            }
        });
        googleplus = rootView.findViewById(R.id.googleplus);

        if (googleplus != null) {
            googleplus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    googleSignIn(facebookLoginListener);
                }
            });
        }


        //Get Keys for initiate SocialNetworks
        String TWITTER_CONSUMER_KEY = getActivity().getString(R.string.twitter_consumer_key);
        String TWITTER_CONSUMER_SECRET = getActivity().getString(R.string.twitter_consumer_secret);
        String TWITTER_CALLBACK_URL = "https://www.v-empower.com/";


        String LINKEDIN_CONSUMER_KEY = getActivity().getString(R.string.linkedin_consumer_key);
        String LINKEDIN_CONSUMER_SECRET = getActivity().getString(R.string.linkedin_consumer_secret);
        String LINKEDIN_CALLBACK_URL = "https://myapp.com/auth/linkedin/callback";

        //Chose permissions
        ArrayList<String> fbScope = new ArrayList<String>();
        fbScope.addAll(Arrays.asList("public_profile", "email"));/*, user_friends*/
        String linkedInScope = "r_basicprofile+r_fullprofile+rw_nus+r_network+w_messages+r_emailaddress+r_contactinfo";

        //Use manager to manage SocialNetworks
        mSocialNetworkManager = (SocialNetworkManager) getFragmentManager().findFragmentByTag(AbstractSocialLoginActivity.SOCIAL_NETWORK_TAG);

        //Check if manager exist
        if (mSocialNetworkManager == null) {
            mSocialNetworkManager = new SocialNetworkManager();

            //Init and add to manager FacebookSocialNetwork
            FacebookSocialNetwork fbNetwork = new FacebookSocialNetwork(this, fbScope);
            mSocialNetworkManager.addSocialNetwork(fbNetwork);

            //Init and add to manager TwitterSocialNetwork
            TwitterSocialNetwork twNetwork = new TwitterSocialNetwork(this, TWITTER_CONSUMER_KEY, TWITTER_CONSUMER_SECRET, TWITTER_CALLBACK_URL);
            mSocialNetworkManager.addSocialNetwork(twNetwork);

            //Init and add to manager LinkedInSocialNetwork
           /* LinkedInSocialNetwork liNetwork = new LinkedInSocialNetwork(this, LINKEDIN_CONSUMER_KEY, LINKEDIN_CONSUMER_SECRET, LINKEDIN_CALLBACK_URL, linkedInScope);
            mSocialNetworkManager.addSocialNetwork(liNetwork);
*/
            //Init and add to manager LinkedInSocialNetwork
   /*         GooglePlusSocialNetwork gpNetwork = new GooglePlusSocialNetwork(this);
            mSocialNetworkManager.addSocialNetwork(gpNetwork);
*/
            //Initiate every network from mSocialNetworkManager
            getFragmentManager().beginTransaction().add(mSocialNetworkManager, AbstractSocialLoginActivity.SOCIAL_NETWORK_TAG).commit();
            mSocialNetworkManager.setOnInitializationCompleteListener(this);
        } else {
            //if manager exist - get and setup login only for initialized SocialNetworks
            if (!mSocialNetworkManager.getInitializedSocialNetworks().isEmpty()) {
                List<SocialNetwork> socialNetworks = mSocialNetworkManager.getInitializedSocialNetworks();
                for (SocialNetwork socialNetwork : socialNetworks) {
                    socialNetwork.setOnLoginCompleteListener(this);
                    initSocialNetwork(socialNetwork);
                }
            }
        }
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        googleInit();
    }

    private void initSocialNetwork(SocialNetwork socialNetwork) {
        if (socialNetwork.isConnected()) {
            switch (socialNetwork.getID()) {
                case FacebookSocialNetwork.ID:
                    //facebook.setText("Show Facebook profile");
                    break;
                case TwitterSocialNetwork.ID:
                    // twitter.setText("Show Twitter profile");
                    break;
              /* case LinkedInSocialNetwork.ID:
                    ///linkedin.setText("Show LinkedIn profile");
                    break;*/
                 /*case GooglePlusSocialNetwork.ID:
                   // googleplus.setText("Show GooglePlus profile");
                    break;*/
            }
        }
    }

    @Override
    public void onSocialNetworkManagerInitialized() {
        //when init SocialNetworks - get and setup login only for initialized SocialNetworks
        for (SocialNetwork socialNetwork : mSocialNetworkManager.getInitializedSocialNetworks()) {
            socialNetwork.setOnLoginCompleteListener(this);
            initSocialNetwork(socialNetwork);
        }
    }

    //Login listener

    private SocialNetwork socialNetwork;
    private View.OnClickListener loginClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(!Utils.isNetworkAvailable())
            {
                Utils.showToastMsgForNetworkNotAvalable();
                return;
            }
            /*if(socialNetwork!=null) {
                socialNetwork.logout();
            }*/
            int networkId = 0;
            switch (view.getId()) {
                case R.id.facebook:
                    networkId = FacebookSocialNetwork.ID;
                    break;
                case R.id.twitter:
                    networkId = TwitterSocialNetwork.ID;
                    break;
                /* case R.id.linkedin:
                    networkId = LinkedInSocialNetwork.ID;
                    break;*/
               /* case R.id.googleplus:
                    networkId = GooglePlusSocialNetwork.ID;
                    break;*/
            }
            socialNetwork = mSocialNetworkManager.getSocialNetwork(networkId);
            try
            {
            if(socialNetwork!=null)
            {
                socialNetwork.cancelLoginRequest();
                socialNetwork.logout();
            }}
            catch (Exception e)
            {
                //TODO nothing
            }

            if (!socialNetwork.isConnected()) {
                if (networkId != 0) {
                    socialNetwork.requestLogin();
                    // AbstractSignUpInActivity.showProgress("Loading social person");
                } else {
                    Toast.makeText(getActivity(), "Wrong networkId", Toast.LENGTH_LONG).show();
                }
            } else {
                startProfile(socialNetwork.getID());
            }
        }
    };

    @Override
    public void onLoginSuccess(int networkId) {
        // AbstractSignUpInActivity.hideProgress();
        startProfile(networkId);
    }

    @Override
    public void onError(int networkId, String requestID, String errorMessage, Object data) {
        //AbstractSignUpInActivity.hideProgress();
        showToastMessage("ERROR: " + errorMessage);

    }

   /* private void startProfile(int networkId){
        FacebookProfileFragment profile = FacebookProfileFragment.newInstannce(networkId);
        getActivity().getSupportFragmentManager().beginTransaction()
                .addToBackStack("profile")
                .replace(R.id.container, profile)
                .commit();
    }
*/


    private void startProfile(int networkId) {
       /* FacebookProfileFragment profile = FacebookProfileFragment.newInstannce(networkId);
        getActivity().getSupportFragmentManager().beginTransaction()
                .addToBackStack("profile")
                .replace(R.id.container, profile)
                .commit();*/
        // socialNetwork = FacebookFragment.mSocialNetworkManager.getSocialNetwork(networkId);
        socialNetwork.setOnRequestAccessTokenCompleteListener(new OnRequestAccessTokenCompleteListener() {
            @Override
            public void onError(int socialNetworkID, String requestID, String errorMessage, Object data) {
                // showToastMessage("accessToken error "+errorMessage);
            }

            @Override
            public void onRequestAccessTokenComplete(final int socialNetworkID,final  AccessToken accessToken) {
                // showToastMessage("accessToken "+accessToken);

                socialNetwork.setOnRequestCurrentPersonCompleteListener(new OnRequestSocialPersonCompleteListener() {
                    @Override
                    public void onRequestSocialPersonSuccess(int socialNetworkId, SocialPerson socialPerson) {
                        String name = socialPerson.name;
                        String id = socialPerson.id;
                        String email = socialPerson.email;
                        String socialPersonString = socialPerson.toString();
                        String infoString = socialPersonString.substring(socialPersonString.indexOf("{") + 1, socialPersonString.lastIndexOf("}"));
                        String info = infoString.replace(", ", "\n");
                        String avatarURL = socialPerson.avatarURL;

                        //String ACCESS_TOKEN,String MEDIA_TYPE,String MEDIA_ID,String EMAIL,String DEVICE_ID
                        SocialLoginDetails details= new SocialLoginDetails(accessToken.token,getMediaType(socialNetworkId),id,email,Utils.getDeviceId());
                        if (facebookLoginListener != null) {
                            facebookLoginListener.getLoginDetails(details);
                        }
                    }

                    @Override
                    public void onError(int socialNetworkID, String requestID, String errorMessage, Object data) {

                    }
                });
                socialNetwork.requestCurrentPerson();
                // facebookLoginListener
            }
        });


        socialNetwork.requestAccessToken();
        // socialNetwork.getAccessToken();
        /*socialNetwork.requestAccessToken(new OnRequestAccessTokenCompleteListener() {
            @Override
            public void onRequestAccessTokenComplete(int socialNetworkID, AccessToken accessToken) {

            }

            @Override
            public void onError(int socialNetworkID, String requestID, String errorMessage, Object data) {

            }
        });*/
        //AbstractSignUpInActivity.showProgress("Loading social person");
    }


    private String getMediaType(int socialNetworkId)
    {
        switch (socialNetworkId)
        {
            case FacebookSocialNetwork.ID:
                return Constants.MediaType.FACEBOOK_TYPE;
            case TwitterSocialNetwork.ID:
                return Constants.MediaType.TWITTER_TYPE;
                default:
                    return "";
                /* case LinkedInSocialNetwork.ID:
                    networkId = LinkedInSocialNetwork.ID;
                    break;
                case R.id.googleplus:
                    networkId = GooglePlusSocialNetwork.ID;
                    break;*/
        }
    }

/*
    @Override
    public void onTitleRightButtonClick() {

    }

    @Override
    View getFragemtView() {
        return rootView;
    }

    @Override
    public void refreshBasicValues() {

    }*/



    /*public void onRequestSocialPersonSuccess(int i, SocialPerson socialPerson) {
        AbstractSignUpInActivity.hideProgress();
        String name= socialPerson.name;
        String id= socialPerson.id;
        String socialPersonString = socialPerson.toString();
        String infoString = socialPersonString.substring(socialPersonString.indexOf("{")+1, socialPersonString.lastIndexOf("}"));
        String  info=infoString.replace(", ", "\n");
        String  avatarURL=socialPerson.avatarURL;
       *//* Picasso.with(getActivity())
                .load(socialPerson.avatarURL)
                .into(photo);*//*
        String person_info="\nName:"+name+"\nId"+id+"\ninfo"+info+"\nImage URL"+avatarURL;
        showToastMessage(person_info);
        if(socialNetwork!=null) {
            socialNetwork.logout();
        }

    }*/

   /* public void setOnRequestSocialPersonCompleteListener(OnRequestSocialPersonCompleteListener completeListener)
    {
        this.completeListener=completeListener;
    }
*/

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (socialNetwork != null) {
            socialNetwork.logout();
        }
    }
}
