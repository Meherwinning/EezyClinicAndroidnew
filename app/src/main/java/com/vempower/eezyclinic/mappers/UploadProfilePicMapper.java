package com.vempower.eezyclinic.mappers;

import android.text.TextUtils;
import android.util.Log;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;
import com.vempower.eezyclinic.API.EezyClinicAPI;
import com.vempower.eezyclinic.APIResponce.AbstractResponse;
import com.vempower.eezyclinic.APIResponce.ChangeMobileNumberAPI;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.SharedPreferenceUtils;
import com.vempower.eezyclinic.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by sathishkumar on 4/1/17.
 */

public class UploadProfilePicMapper extends AbstractMapper implements Callback<AbstractResponse> {

    private UpdateProfilePicListener listener;
    private final File profilePicFile;
    private final String keyName;

    public UploadProfilePicMapper(File profilePicFile,String keyName) {
        this.profilePicFile=profilePicFile;
        this.keyName=keyName;
    }


    public void setOnUpdateProfilePicListener(UpdateProfilePicListener listener) {
        if (listener == null) {
            Log.i(MyApplication.getCurrentActivityContext().getClass().getName(), "Invalid UpdateProfilePicListener instance.");
            //addressListListener.validateAddressList(null);
            return;

        }

        this.listener = listener;
        init();
    }

    private void init() {

        if (!Utils.isNetworkAvailable(MyApplication
                .getCurrentActivityContext())) {
            //Utils.showToastMsgForNetworkNotAvalable();
            if (listener != null) {
                listener.uploadProfilePic(null, Utils.getStringFromResources(R.string.network_not_available_lbl));
            }
            return;
        }

        MyApplication.showTransparentDialog();


        MyApplication.showTransparentDialog();
        EezyClinicAPI stashDealAPI = MyApplication.getInstance().getEezyClinicAPI();


       /*   RequestBody body=getMyRequestBody();
        if (body==null) {
            if (listener != null) {
                listener.uploadProfilePic(null, null);
            }
            return;
        }*/

       // RequestBody body=getMyRequestBody();
        String access_key = SharedPreferenceUtils.getStringValueFromSharedPrefarence(Constants.Pref.USER_VALIDATION_KEY, null);
        if (TextUtils.isEmpty(access_key)) {
            if (listener != null) {
                listener.uploadProfilePic(null, null);
            }
            return;
        }

        Map<String, RequestBody> map = new HashMap<>();
        RequestBody mybody = RequestBody.create(MediaType.parse("text/plain"), access_key);

        RequestBody imageBody = null;
        if (profilePicFile != null) {
            imageBody = RequestBody.create(MediaType.parse("image/jpeg"), profilePicFile);
            map.put(keyName+"\"; filename=\"patient_pic.png\" ", imageBody);
        }
        if (imageBody == null) {
            MyApplication.hideTransaprentDialog();
            if (listener != null) {
                listener.uploadProfilePic(null, null);
            }
            return;
        }


        //  Call<UserAPI> apiResponseCall = PreferencesAPI.updateUserProfile(getJsonObject(user).toString());

       // Call<AbstractResponse> apiResponseCall = stashDealAPI.uploadProfileImage(imageBody, mybody);
        Call<AbstractResponse> apiResponseCall = stashDealAPI.uploadProfileImage1(map, mybody);
        apiResponseCall.enqueue(this);
    }

    @Override
    public void onResponse(Response<AbstractResponse> response, Retrofit retrofit) {
        MyApplication.hideTransaprentDialog();

        getMyResponse(response, new MyResponse<AbstractResponse>() {
            @Override
            public void getMyResponse(AbstractResponse responseBody, String errorMsg) {
                listener.uploadProfilePic(responseBody, errorMsg);
            }
        });


    }

    @Override
    public void onFailure(Throwable error) {
        MyApplication.hideTransaprentDialog();

        onMyFailure(error, new MyResponse<AbstractResponse>() {
            @Override
            public void getMyResponse(AbstractResponse responseBody, String errorMsg) {
                listener.uploadProfilePic(responseBody, errorMsg);
            }
        });


    }



    //
//    @NonNull
//    private JSONObject getJsonObject(User user) {
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("PreferenceId", preferenceID);
//            jsonObject.put("AuthenticationToken", user.getAuthenticationToken());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return jsonObject;
//    }


    public RequestBody getMyRequestBody() {

        String access_key = SharedPreferenceUtils.getStringValueFromSharedPrefarence(Constants.Pref.USER_VALIDATION_KEY, null);
        if (TextUtils.isEmpty(access_key)) {
            return null;
        }



        /*
  /*

                 {
          "access_key": "360f19438d8cb884057f79a1a55df441",
           "newmobileno":"123456789"

}
     */

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("access_key", access_key);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return getRequestBody(jsonObject);
    }

    public interface UpdateProfilePicListener {
        public void uploadProfilePic(AbstractResponse PreferencesAPI,String errorMessage);
    }

}
