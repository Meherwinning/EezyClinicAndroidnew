package com.vempower.eezyclinic.mappers;

import android.text.TextUtils;
import android.util.Log;

import com.squareup.okhttp.RequestBody;
import com.vempower.eezyclinic.API.EezyClinicAPI;
import com.vempower.eezyclinic.APIResponce.ForgotPasswordAPI;
import com.vempower.eezyclinic.APIResponce.VerifyOTPAPI;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.activities.ForgotPasswordActivity;
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

public class ForgotPasswordMapper extends  AbstractMapper  implements Callback<ForgotPasswordAPI> {

//email(1) or mobile(1)
    private ForgotPasswordListener listener;
    private final String email,mobile;

    public ForgotPasswordMapper(String email, String mobile) {
        this.email=email;
        this.mobile=mobile;
    }

    public void setOnForgotPasswordListener(ForgotPasswordListener listener) {
        if (listener == null) {
            Log.i(MyApplication.getCurrentActivityContext().getClass().getName(), "Invalid ForgotPasswordListener instance.");
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
                listener.getForgotPasswordAPI(null, Utils.getStringFromResources(R.string.network_not_available_lbl));
            }
            return;
        }

        MyApplication.showTransparentDialog();
        EezyClinicAPI stashDealAPI = MyApplication.getInstance().getEezyClinicAPI();

        RequestBody requestBody = getMyRequestBody();
        if (requestBody == null) {
            MyApplication.hideTransaprentDialog();
            if (listener != null) {
                listener.getForgotPasswordAPI(null,null);
            }
            return;
        }

        Call<ForgotPasswordAPI> apiResponseCall = stashDealAPI.forgorPasswordAPI(requestBody);

        apiResponseCall.enqueue(this);
    }

    @Override
    public void onResponse(Response<ForgotPasswordAPI> response, Retrofit retrofit) {
        MyApplication.hideTransaprentDialog();

        getMyResponse(response, new MyResponse<ForgotPasswordAPI>() {
            @Override
            public void getMyResponse(ForgotPasswordAPI responseBody, String errorMsg) {
                listener.getForgotPasswordAPI(responseBody,errorMsg);
            }
        });

    }

    @Override
    public void onFailure(Throwable t) {
        MyApplication.hideTransaprentDialog();
      //  listener.getVerifyOTPAPI(null);
        onMyFailure(t, new MyResponse<ForgotPasswordAPI>() {
            @Override
            public void getMyResponse(ForgotPasswordAPI responseBody, String errorMsg) {
                listener.getForgotPasswordAPI(responseBody,errorMsg);
            }
        });

    }

    public RequestBody getMyRequestBody() {

        if (TextUtils.isEmpty(email) && TextUtils.isEmpty(mobile)) {
            return null;
        }
        //fname
               // email

        //{
        /*"patient_id" : "PT00296",
                "otp" :"249815"*/
   // }
        JSONObject jsonObject = new JSONObject();
        try {
            if(!TextUtils.isEmpty(email)) {
                jsonObject.put("email", email);
            }
            if(!TextUtils.isEmpty(mobile)) {
                jsonObject.put("mobile", mobile);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return getRequestBody(jsonObject);
    }

    public interface ForgotPasswordListener {
        public void getForgotPasswordAPI(ForgotPasswordAPI forgotPasswordAPI, String errorMessage);
    }
}
