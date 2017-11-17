package com.vempower.eezyclinic.interfaces;

import com.github.gorbin.asne.core.AccessToken;
import com.github.gorbin.asne.core.listener.OnRequestSocialPersonCompleteListener;

/**
 * Created by satish on 17/11/17.
 */

public interface FacebookLoginListener {

    OnRequestSocialPersonCompleteListener getFacebookListener();
    void setAccessToken(AccessToken accessToken);
}
