package com.vempower.eezyclinic.mappers;

import android.text.TextUtils;
import android.util.Log;

import com.squareup.okhttp.RequestBody;
import com.vempower.eezyclinic.API.EezyClinicAPI;
import com.vempower.eezyclinic.APIResponce.LoginAPI;
import com.vempower.eezyclinic.APIResponce.SignupAPI;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.core.SocialLoginDetails;
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

public class SocialSignInMapper extends  AbstractMapper  implements Callback<LoginAPI> {


    private SignInListener listener;
    private final SocialLoginDetails details;

    public SocialSignInMapper(SocialLoginDetails details) {
        this.details=details;
    }

    public void setOnSignInListener(SignInListener listener) {
        if (listener == null) {
            Log.i(MyApplication.getCurrentActivityContext().getClass().getName(), "Invalid EmailSignInListener instance.");
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
                listener.getLoginAPI(null,null);
            }
            return;
        }

        Call<LoginAPI> apiResponseCall = stashDealAPI.getSocialLoginAPI(requestBody);

        apiResponseCall.enqueue(this);
    }

    @Override
    public void onResponse(Response<LoginAPI> response, Retrofit retrofit) {
        MyApplication.hideTransaprentDialog();
        //listener.getLoginAPI(response.body());

        getMyResponse(response, new MyResponse<LoginAPI>() {
            @Override
            public void getMyResponse(LoginAPI responseBody, String errorMsg) {
                listener.getLoginAPI(responseBody,errorMsg);
            }
        });


    }

    @Override
    public void onFailure(Throwable t) {
        MyApplication.hideTransaprentDialog();
        onMyFailure(t, new MyResponse<LoginAPI>() {
            @Override
            public void getMyResponse(LoginAPI responseBody, String errorMsg) {
                listener.getLoginAPI(responseBody,errorMsg);
            }
        });

    }

    public RequestBody getMyRequestBody() {


        if (details==null || TextUtils.isEmpty(details.ACCESS_TOKEN)
                || TextUtils.isEmpty(details.MEDIA_ID) || TextUtils.isEmpty(details.MEDIA_TYPE) ) {
            return null;
        }
        //fname
               // email

        //access_token(1), media_type(1), media_id(1), email(1), device_id(1)
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("access_token", details.ACCESS_TOKEN);
            jsonObject.put("media_type", details.MEDIA_TYPE);
            jsonObject.put("media_id", details.MEDIA_ID);

            if(!TextUtils.isEmpty(details.EMAIL)) {
                jsonObject.put("email", details.EMAIL);
            }
            if(!TextUtils.isEmpty(details.DEVICE_ID)) {
                jsonObject.put("device_id", details.DEVICE_ID);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return getRequestBody(jsonObject);
    }

    public interface SignInListener {
        public void getLoginAPI(LoginAPI loginAPI,String errorMessage);
    }
}
