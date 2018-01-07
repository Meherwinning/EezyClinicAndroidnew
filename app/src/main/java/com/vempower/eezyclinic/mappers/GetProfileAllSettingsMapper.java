package com.vempower.eezyclinic.mappers;

import android.text.TextUtils;
import android.util.Log;

import com.squareup.okhttp.RequestBody;
import com.vempower.eezyclinic.API.EezyClinicAPI;
import com.vempower.eezyclinic.APIResponce.DashboardAPI;
import com.vempower.eezyclinic.APIResponce.ProfileSettingsAPI;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.SharedPreferenceUtils;
import com.vempower.eezyclinic.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Satishk on 4/10/2017.
 */

public class GetProfileAllSettingsMapper extends  AbstractMapper  implements Callback<ProfileSettingsAPI> {


    private ProfileSettingsListener listener;



    public void setOnProfileSettingsListener(ProfileSettingsListener listener) {
        if (listener == null) {
            Log.i(MyApplication.getCurrentActivityContext().getClass().getName(), "Invalid ProfileSettingsListener instance.");
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
                listener.getProfileSettings(null, Utils.getStringFromResources(R.string.network_not_available_lbl));
            }
            return;
        }

       // MyApplication.showTransparentDialog();
        EezyClinicAPI stashDealAPI = MyApplication.getInstance().getEezyClinicAPI();

        RequestBody requestBody = getMyRequestBody();
        if (requestBody == null) {
            MyApplication.hideTransaprentDialog();
            if (listener != null) {
                listener.getProfileSettings(null,null);
            }
            return;
        }

        Call<ProfileSettingsAPI> apiResponseCall = stashDealAPI.getProfileSettings(requestBody);

        apiResponseCall.enqueue(this);
    }

    @Override
    public void onResponse(Response<ProfileSettingsAPI> response, Retrofit retrofit) {
        getMyResponse(response, new MyResponse<ProfileSettingsAPI>() {
            @Override
            public void getMyResponse(ProfileSettingsAPI responseBody, String errorMsg) {
                listener.getProfileSettings(responseBody,errorMsg);
            }
        });




    }

    @Override
    public void onFailure(Throwable error) {
        onMyFailure(error, new MyResponse<ProfileSettingsAPI>() {
            @Override
            public void getMyResponse(ProfileSettingsAPI responseBody, String errorMsg) {
                listener.getProfileSettings(responseBody,errorMsg);
            }
        });


    }

    public RequestBody getMyRequestBody() {

       String access_key= SharedPreferenceUtils.getStringValueFromSharedPrefarence(Constants.Pref.USER_VALIDATION_KEY,null);
        if (TextUtils.isEmpty(access_key) ) {
            return null;
        }
        /*{
            "access_key": "360f19438d8cb884057f79a1a55df441",
                "module_name":"all"
        }*/
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("access_key", access_key);
            jsonObject.put("module_name", "all");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return getRequestBody(jsonObject);
    }

    public interface ProfileSettingsListener {
         void getProfileSettings(ProfileSettingsAPI  settingsAPI, String errorMessage);
    }
}
