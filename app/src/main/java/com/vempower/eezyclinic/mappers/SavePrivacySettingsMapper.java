package com.vempower.eezyclinic.mappers;

import android.text.TextUtils;
import android.util.Log;

import com.squareup.okhttp.RequestBody;
import com.vempower.eezyclinic.API.EezyClinicAPI;
import com.vempower.eezyclinic.APIResponce.AbstractResponse;
import com.vempower.eezyclinic.APIResponce.DashboardAPI;
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

public class SavePrivacySettingsMapper extends AbstractMapper implements Callback<AbstractResponse> {


    private SavePrivacySettingsListener listener;
    private final String moduleName;
    private final boolean isDoctorsISeek, isWithinDoctors, isFamilyMembers;

    public SavePrivacySettingsMapper(String moduleName, boolean isDoctorsISeek, boolean isWithinDoctors, boolean isFamilyMembers) {
        this.moduleName = moduleName;
        this.isDoctorsISeek = isDoctorsISeek;
        this.isFamilyMembers = isFamilyMembers;
        this.isWithinDoctors = isWithinDoctors;
    }

    public void setOnSavePrivacySettingsListener(SavePrivacySettingsListener listener) {
        if (listener == null) {
            Log.i(MyApplication.getCurrentActivityContext().getClass().getName(), "Invalid SavePrivacySettingsListener instance.");
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
                listener.savePrivacySettingsAPI(null, Utils.getStringFromResources(R.string.network_not_available_lbl));
            }
            return;
        }

        // MyApplication.showTransparentDialog();
        EezyClinicAPI stashDealAPI = MyApplication.getInstance().getEezyClinicAPI();

        RequestBody requestBody = getMyRequestBody();
        if (requestBody == null) {
            MyApplication.hideTransaprentDialog();
            if (listener != null) {
                listener.savePrivacySettingsAPI(null, null);
            }
            return;
        }

        Call<AbstractResponse> apiResponseCall = stashDealAPI.saveProfileSettings(requestBody);

        apiResponseCall.enqueue(this);
    }

    @Override
    public void onResponse(Response<AbstractResponse> response, Retrofit retrofit) {
        //MyApplication.hideTransaprentDialog();

        getMyResponse(response, new MyResponse<AbstractResponse>() {
            @Override
            public void getMyResponse(AbstractResponse responseBody, String errorMsg) {
                listener.savePrivacySettingsAPI(responseBody, errorMsg);
            }
        });


    }

    @Override
    public void onFailure(Throwable error) {
        //MyApplication.hideTransaprentDialog();

        onMyFailure(error, new MyResponse<AbstractResponse>() {
            @Override
            public void getMyResponse(AbstractResponse responseBody, String errorMsg) {
                listener.savePrivacySettingsAPI(responseBody, errorMsg);
            }
        });


    }

    public RequestBody getMyRequestBody() {

        String access_key = SharedPreferenceUtils.getStringValueFromSharedPrefarence(Constants.Pref.USER_VALIDATION_KEY, null);
        if (TextUtils.isEmpty(access_key)) {
            return null;
        }

        /*
         { "access_key": "360f19438d8cb884057f79a1a55df441",
  "module_name":"myuploadfiles" ,
   "myuploadfiles":
     {
      "onlyappointmentdoctor":"1" ,
       "doctorswithinsameclinic":"0" ,
       "family":"0"
     }
  }
         */
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("access_key", access_key);
            jsonObject.put("module_name", moduleName);
            JSONObject subObject = new JSONObject();
            {
                subObject.put("onlyappointmentdoctor", isDoctorsISeek ? "1" : "0");
                subObject.put("doctorswithinsameclinic", isWithinDoctors ? "1" : "0");
                subObject.put("family", isFamilyMembers ? "1" : "0");
            }
            jsonObject.put(moduleName, subObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return getRequestBody(jsonObject);
    }

    public interface SavePrivacySettingsListener {
        public void savePrivacySettingsAPI(AbstractResponse response, String errorMessage);
    }
}
