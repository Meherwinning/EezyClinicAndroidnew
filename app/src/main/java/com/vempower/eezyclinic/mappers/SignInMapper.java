package com.vempower.eezyclinic.mappers;

import android.text.TextUtils;
import android.util.Log;

import com.squareup.okhttp.RequestBody;
import com.vempower.eezyclinic.API.EezyClinicAPI;
import com.vempower.eezyclinic.APIResponce.LoginAPI;
import com.vempower.eezyclinic.APIResponce.RegisterAPI;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit.Call;
import retrofit.Callback;
import retrofit.HttpException;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Satishk on 4/10/2017.
 */

public class SignInMapper extends  AbstractMapper  implements Callback<LoginAPI> {


    private SignInListener listener;
    private final String userId,password;

    public SignInMapper(String userId, String password) {
        this.userId=userId;
        this.password=password;
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
           // Utils.showToastMsgForNetworkNotAvalable();
            if (listener != null) {
                listener.getLoginAPI(null, Utils.getStringFromResources(R.string.network_not_available_lbl));
            }
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

        Call<LoginAPI> apiResponseCall = stashDealAPI.getLoginAPI(requestBody);

        apiResponseCall.enqueue(this);
    }

    @Override
    public void onResponse(Response<LoginAPI> response, Retrofit retrofit) {
        MyApplication.hideTransaprentDialog();

        getMyResponse(response, new MyResponse<LoginAPI>() {
            @Override
            public void getMyResponse(LoginAPI responseBody, String errorMsg) {
                listener.getLoginAPI(responseBody,errorMsg);
            }
        });




    }

    @Override
    public void onFailure(Throwable error) {
        MyApplication.hideTransaprentDialog();

        onMyFailure(error, new MyResponse<LoginAPI>() {
            @Override
            public void getMyResponse(LoginAPI responseBody, String errorMsg) {
                listener.getLoginAPI(responseBody,errorMsg);
            }
        });


    }

    public RequestBody getMyRequestBody() {

        if (TextUtils.isEmpty(userId) || TextUtils.isEmpty(password)) {
            return null;
        }
        //fname
               // email

        //userid(1), password(1)
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userid", userId);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return getRequestBody(jsonObject);
    }

    public interface SignInListener {
        public void getLoginAPI(LoginAPI loginAPI,String errorMessage);
    }
}
