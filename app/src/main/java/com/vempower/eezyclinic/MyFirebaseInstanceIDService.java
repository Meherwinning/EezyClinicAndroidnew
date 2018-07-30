package com.vempower.eezyclinic;

import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.SharedPreferenceUtils;
import com.vempower.eezyclinic.utils.Utils;

/**
 * Created by satish on 28/11/17.
 */

//https://github.com/firebase/quickstart-android/blob/master/messaging/app/src/main/java/com/google/firebase/quickstart/fcm/MyFirebaseMessagingService.java
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "MyFirebaseIIDService";
    public static final String FIRE_BASE_TOKEN_ID="fire_base_token_id";
    public static final String IS_SEND_TO_SERVER="is_send_to_server";


    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }
    // [END refresh_token]

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        SharedPreferenceUtils.setStringValueToSharedPrefarence(FIRE_BASE_TOKEN_ID,token);
        SharedPreferenceUtils.setBooleanValueToSharedPrefarence(IS_SEND_TO_SERVER,false);


        Log.v("FCM ID :", "FCM :"+ Utils.getFireBaseCloudMessageId());
       /* String access_key = SharedPreferenceUtils.getStringValueFromSharedPrefarence(Constants.Pref.USER_VALIDATION_KEY, null);
        if (TextUtils.isEmpty(access_key)) {

            Log.v("FCM ID 1:", "NO access_key");
            return;
        }

        String deviceId = Utils.getDeviceId();
        String deviceType = "android";
        Log.i("FCM_Register", "access_key :" + access_key + "\ndevice_type:" + deviceType + "\nDevice id" + deviceId + "\n FCM_Id: " + Utils.getFireBaseCloudMessageId() + "\n");
*/
        /*
access_key(1)
device_type(1)
device_id(1)
device_token(1)
         */

    }
}
