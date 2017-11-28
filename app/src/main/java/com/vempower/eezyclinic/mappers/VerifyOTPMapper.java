package com.vempower.eezyclinic.mappers;

import android.text.TextUtils;
import android.util.Log;

import com.squareup.okhttp.RequestBody;
import com.vempower.eezyclinic.API.EezyClinicAPI;
import com.vempower.eezyclinic.APIResponce.LoginAPI;
import com.vempower.eezyclinic.APIResponce.VerifyOTPAPI;
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

public class VerifyOTPMapper extends  AbstractMapper  implements Callback<VerifyOTPAPI> {


    private VerifyOtpListener listener;
    private final String otp,pid;

    public VerifyOTPMapper(String otp, String pid) {
        this.otp=otp;
        this.pid=pid;
    }

    public void setOnVerifyOtpListener(VerifyOtpListener listener) {
        if (listener == null) {
            Log.i(MyApplication.getCurrentActivityContext().getClass().getName(), "Invalid VerifyOtpListener instance.");
            return;

        }

        this.listener = listener;
        init();
    }

    private void init() {

        if (!Utils.isNetworkAvailable(MyApplication
                .getCurrentActivityContext())) {
            Utils.showToastMsgForNetworkNotAvalable();
            return;
        }

        MyApplication.showTransparentDialog();
        EezyClinicAPI stashDealAPI = MyApplication.getInstance().getEezyClinicAPI();

        RequestBody requestBody = getMyRequestBody();
        if (requestBody == null) {
            MyApplication.hideTransaprentDialog();
            if (listener != null) {
                listener.getVerifyOTPAPI(null,null);
            }
            return;
        }

        Call<VerifyOTPAPI> apiResponseCall = stashDealAPI.verifyOTP_API(requestBody);

        apiResponseCall.enqueue(this);
    }

    @Override
    public void onResponse(Response<VerifyOTPAPI> response, Retrofit retrofit) {
        MyApplication.hideTransaprentDialog();

        getMyResponse(response, new MyResponse<VerifyOTPAPI>() {
            @Override
            public void getMyResponse(VerifyOTPAPI responseBody, String errorMsg) {
                listener.getVerifyOTPAPI(responseBody,errorMsg);
            }
        });

    }

    @Override
    public void onFailure(Throwable t) {
        MyApplication.hideTransaprentDialog();
      //  listener.getVerifyOTPAPI(null);
        onMyFailure(t, new MyResponse<VerifyOTPAPI>() {
            @Override
            public void getMyResponse(VerifyOTPAPI responseBody, String errorMsg) {
                listener.getVerifyOTPAPI(responseBody,errorMsg);
            }
        });

    }

    public RequestBody getMyRequestBody() {

        if (TextUtils.isEmpty(otp) || TextUtils.isEmpty(pid)) {
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
            jsonObject.put("patient_id", pid);
            jsonObject.put("otp", otp);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return getRequestBody(jsonObject);
    }

    public interface VerifyOtpListener {
        public void getVerifyOTPAPI(VerifyOTPAPI loginAPI,String errorMessage);
    }
}
