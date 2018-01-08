package com.vempower.eezyclinic.mappers;

import android.text.TextUtils;
import android.util.Log;

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

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Satishk on 4/10/2017.
 */

public class ChangeMobileNumberMapper extends AbstractMapper implements Callback<ChangeMobileNumberAPI> {


    private ChangeMobileNumberListener listener;
    // private final String moduleName;
    private final String mobileNum;

    public ChangeMobileNumberMapper(String mobileNum) {
        this.mobileNum = mobileNum;
    }

    public void setOnChangeMobileNumberListener(ChangeMobileNumberListener listener) {
        if (listener == null) {
            Log.i(MyApplication.getCurrentActivityContext().getClass().getName(), "Invalid ChangeMobileNumberListener instance.");
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
                listener.changeMobileNumber(null, Utils.getStringFromResources(R.string.network_not_available_lbl));
            }
            return;
        }

         MyApplication.showTransparentDialog();
        EezyClinicAPI stashDealAPI = MyApplication.getInstance().getEezyClinicAPI();

        RequestBody requestBody = getMyRequestBody();
        if (requestBody == null) {
            MyApplication.hideTransaprentDialog();
            if (listener != null) {
                listener.changeMobileNumber(null, null);
            }
            return;
        }

        Call<ChangeMobileNumberAPI> apiResponseCall = stashDealAPI.changeMobileNumber(requestBody);

        apiResponseCall.enqueue(this);
    }

    @Override
    public void onResponse(Response<ChangeMobileNumberAPI> response, Retrofit retrofit) {
        MyApplication.hideTransaprentDialog();

        getMyResponse(response, new MyResponse<ChangeMobileNumberAPI>() {
            @Override
            public void getMyResponse(ChangeMobileNumberAPI responseBody, String errorMsg) {
                listener.changeMobileNumber(responseBody, errorMsg);
            }
        });


    }

    @Override
    public void onFailure(Throwable error) {
        MyApplication.hideTransaprentDialog();

        onMyFailure(error, new MyResponse<ChangeMobileNumberAPI>() {
            @Override
            public void getMyResponse(ChangeMobileNumberAPI responseBody, String errorMsg) {
                listener.changeMobileNumber(responseBody, errorMsg);
            }
        });


    }

    public RequestBody getMyRequestBody() {

        String access_key = SharedPreferenceUtils.getStringValueFromSharedPrefarence(Constants.Pref.USER_VALIDATION_KEY, null);
        if (TextUtils.isEmpty(access_key)) {
            return null;
        }

        if (TextUtils.isEmpty(mobileNum)) {
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
            jsonObject.put("newmobileno", mobileNum);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return getRequestBody(jsonObject);
    }

    public interface ChangeMobileNumberListener {
        public void changeMobileNumber(ChangeMobileNumberAPI response, String errorMessage);
    }
}
