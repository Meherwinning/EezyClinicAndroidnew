package com.vempower.eezyclinic.interfaces;

import com.github.gorbin.asne.core.AccessToken;
import com.github.gorbin.asne.core.listener.OnRequestSocialPersonCompleteListener;
import com.vempower.eezyclinic.core.SocialLoginDetails;

/**
 * Created by satish on 17/11/17.
 */

public interface SocialLoginListener {

   // OnRequestSocialPersonCompleteListener getFacebookListener();
    //void setAccessToken(int socialNetworkID,AccessToken accessToken);
   void getLoginDetails(SocialLoginDetails details);
}
