package com.vempower.eezyclinic.mappers;

import android.text.TextUtils;
import android.util.Log;

import com.squareup.okhttp.RequestBody;
import com.vempower.eezyclinic.API.EezyClinicAPI;
import com.vempower.eezyclinic.APIResponce.SignupAPI;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;
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

public class ResendOTPMapper extends  AbstractMapper  implements Callback<SignupAPI> {


    private ResendOTPListener listener;
    //private final String userId,password;
    private final String id;
    public ResendOTPMapper(String id) {
        this.id=id;

    }


    public void setOnResendOTPListener(ResendOTPListener listener) {
        if (listener == null) {
            Log.i(MyApplication.getCurrentActivityContext().getClass().getName(), "Invalid ResendOTPListener instance.");
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
                listener.getSignupAPI(null, Utils.getStringFromResources(R.string.network_not_available_lbl));
            }
            return;
        }

        MyApplication.showTransparentDialog();
        EezyClinicAPI stashDealAPI = MyApplication.getInstance().getEezyClinicAPI();

        RequestBody requestBody = getMyRequestBody();
        if (requestBody == null) {
            MyApplication.hideTransaprentDialog();
            if (listener != null) {
                listener.getSignupAPI(null,null);
            }
            return;
        }

        Call<SignupAPI> apiResponseCall = stashDealAPI.resendOTPAPI(requestBody);

        apiResponseCall.enqueue(this);
    }

    @Override
    public void onResponse(Response<SignupAPI> response, Retrofit retrofit) {
        MyApplication.hideTransaprentDialog();
       // listener.getSignupAPI(response.body());
        getMyResponse(response, new MyResponse<SignupAPI>() {
            @Override
            public void getMyResponse(SignupAPI responseBody, String errorMsg) {
                listener.getSignupAPI(responseBody,errorMsg);
            }
        });

    }

    @Override
    public void onFailure(Throwable error) {
        MyApplication.hideTransaprentDialog();
        //listener.getSignupAPI(null);
        onMyFailure(error, new MyResponse<SignupAPI>() {
            @Override
            public void getMyResponse(SignupAPI responseBody, String errorMsg) {
                listener.getSignupAPI(responseBody,errorMsg);
            }
        });

    }

    public RequestBody getMyRequestBody() {

        if (TextUtils.isEmpty(id)) {
            return null;
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return getRequestBody(jsonObject);
    }

    public interface ResendOTPListener {
        public void getSignupAPI(SignupAPI signupAPI, String errorMessage);
    }
}
