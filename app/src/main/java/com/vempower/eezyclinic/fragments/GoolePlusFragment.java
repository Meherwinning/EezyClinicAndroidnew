package com.vempower.eezyclinic.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.github.gorbin.asne.core.AccessToken;
import com.github.gorbin.asne.core.listener.OnRequestSocialPersonCompleteListener;
import com.github.gorbin.asne.core.persons.SocialPerson;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.activities.AbstractSocialLoginActivity;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.core.SocialLoginDetails;
import com.vempower.eezyclinic.interfaces.SocialLoginListener;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.Utils;

/**
 * Created by satish on 23/11/17.
 */

public class GoolePlusFragment extends AbstractFragment implements  GoogleApiClient.OnConnectionFailedListener{

    private static final String TAG = "SignInDemoActivity";
    private static final int RC_SIGN_IN = 9001;

    private GoogleApiClient mGoogleApiClient;



    //Facebook
    public static final String SOCIAL_NETWORK_TAG = "SocialIntegrationMain.SOCIAL_NETWORK_TAG";
    private SocialLoginListener socialLoginListener;

    protected void googleInit() {

        // Views
        //mStatusTextView = (TextView) findViewById(R.id.status);

        // Button listeners
                   // findViewById(R.id.sign_in_button).setOnClickListener(this);
        //findViewById(R.id.sign_out_button).setOnClickListener(this);
        //findViewById(R.id.disconnect_button).setOnClickListener(this);

        // [START configure_signin]
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(getString(R.string.default_web_client_id))
                //.requestIdToken("862964301966-5m3df93f18d7ragq4re539p8migpri04.apps.googleusercontent.com")
                //.requestScopes(new Scope(Scopes.DRIVE_APPFOLDER))
                //.requestServerAuthCode("862964301966-5m3df93f18d7ragq4re539p8migpri04.apps.googleusercontent.com")
                //.requestEmail()
                .build();
        // .requestServerAuthCode(serverClientId)
        //.requestEmail()
        // [END configure_signin]

        // [START build_client]
        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(MyApplication.getCurrentActivityContext())
                .enableAutoManage((AbstractSocialLoginActivity)MyApplication.getCurrentActivityContext() /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        // [END build_client]

        // [START customize_button]
        // Set the dimensions of the sign-in button.
       /* SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        signInButton.setColorScheme(SignInButton.COLOR_LIGHT);*/
        // [END customize_button]

        //onShowFacebookButton();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onResume() {
        super.onResume();
        hideProgressDialog();
    }

    // [START onActivityResult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }

        Fragment fragment = getFragmentManager().findFragmentByTag(SOCIAL_NETWORK_TAG);
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
    // [END onActivityResult]

    // [START handleSignInResult]
    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            //mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
            Log.d(TAG, "GoogleSignInAccount: " + acct.toString());
            Log.d(TAG, "Token : " + acct.getIdToken());

            //String authCode = acct.getServerAuthCode();
            //Log.d("Google", "Google Auth : " + authCode);
            // googleAccountDetails(acct);
            googleSignin(acct);
            signOut();
        }
    }

    // protected abstract void googleAccountDetails(GoogleSignInAccount acct);
    // [END handleSignInResult]

    // [START signIn]
    protected void googleSignIn(final SocialLoginListener socialLoginListener) {
        if(!Utils.isNetworkAvailable())
        {
            Utils.showToastMsgForNetworkNotAvalable();
            return;
        }
        this.socialLoginListener=socialLoginListener;
        //signOut();
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    // [END signIn]

    // [START signOut]
    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        //updateUI(false);
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END signOut]

    // [START revokeAccess]
    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        //updateUI(false);
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END revokeAccess]

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    @Override
    public void onStop() {
        super.onStop();
        MyApplication.hideTransaprentDialog();
    }

    private void showProgressDialog() {
        MyApplication.showTransparentDialog();
    }

    private void hideProgressDialog() {
        MyApplication.hideTransaprentDialog();
    }

    /*private void updateUI(boolean signedIn) {
        if (signedIn) {
            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
        } else {
            //mStatusTextView.setText(R.string.signed_out);

            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
        }
    }*/

    /*@Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            case R.id.sign_out_button:
                signOut();
                break;
            case R.id.disconnect_button:
                revokeAccess();
                break;
        }
    }*/



    private void googleSignin(GoogleSignInAccount acct) {
        //showToastMessage(acct.getDisplayName());

        String name = acct.getDisplayName();
        String email = acct.getEmail();
        String tokenId = acct.getIdToken();
        String id = acct.getId();
        String photoUrl=acct.getPhotoUrl().toString();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(tokenId))
        {
            Utils.showToastMessage(R.string.please_try_again);
            return;
        }



        //String msg = "Name:" + name + "\nEmail:" + email + "\nTokenId:" + tokenId + "\n Id:" + id;
        //Utils.showToastMessage(msg);

        SocialLoginDetails details= new SocialLoginDetails(tokenId, Constants.MediaType.GOOGLE_TYPE,id,email,Utils.getDeviceId(),name,photoUrl);
        //details.setName(name);
        if (socialLoginListener != null) {
            socialLoginListener.getLoginDetails(details);
        }
        //Log.d(TAG, "GoogleSignInAccount: " + msg);
        //social_type=1(google)social_type=2(Facebook)
        /*SocialSignupMapper mapper = new SocialSignupMapper(email,tokenId,1+"");
        mapper.setOnGoogleSignupListener(new SocialSignupMapper.SocialSignupListener() {
            @Override
            public void getUserAPI(UserAPI userAPI) {
                validateUser(userAPI);
            }
        });*/
    }

   /* @Override
    public void onDestroy() {
        super.onDestroy();
        signOut();
    }*/
}
