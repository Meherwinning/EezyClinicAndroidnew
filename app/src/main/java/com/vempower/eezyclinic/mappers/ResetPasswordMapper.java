package com.vempower.eezyclinic.mappers;

import android.text.TextUtils;
import android.util.Log;

import com.squareup.okhttp.RequestBody;
import com.vempower.eezyclinic.API.EezyClinicAPI;
import com.vempower.eezyclinic.APIResponce.AbstractResponse;
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

public class ResetPasswordMapper extends  AbstractMapper  implements Callback<AbstractResponse> {


    private ResetPasswordListener listener;
    //private final String userId,password;
    //mobileotp(1),id(1),newpw(1),confirmnewpw(1)
    private final String otp,newpw,confirmnewpw;
    private final int id;

    public ResetPasswordMapper(String otp, int id,String newpw,String confirmnewpw) {
        this.otp=otp;
        this.id=id;
        this.newpw=newpw;
        this.confirmnewpw=confirmnewpw;
    }

    public void setOnResetPasswordListener(ResetPasswordListener listener) {
        if (listener == null) {
            Log.i(MyApplication.getCurrentActivityContext().getClass().getName(), "Invalid ResetPasswordListener instance.");
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
                listener.getResetPasswordAPI(null, Utils.getStringFromResources(R.string.network_not_available_lbl));
            }
            return;
        }

        MyApplication.showTransparentDialog();
        EezyClinicAPI stashDealAPI = MyApplication.getInstance().getEezyClinicAPI();

        RequestBody requestBody = getMyRequestBody();
        if (requestBody == null) {
            MyApplication.hideTransaprentDialog();
            if (listener != null) {
                listener.getResetPasswordAPI(null,null);
            }
            return;
        }

        Call<AbstractResponse> apiResponseCall = stashDealAPI.resetPasswordAPI(requestBody);

        apiResponseCall.enqueue(this);
    }

    @Override
    public void onResponse(Response<AbstractResponse> response, Retrofit retrofit) {
        MyApplication.hideTransaprentDialog();

        getMyResponse(response, new MyResponse<AbstractResponse>() {
            @Override
            public void getMyResponse(AbstractResponse responseBody, String errorMsg) {
                listener.getResetPasswordAPI(responseBody,errorMsg);
            }
        });




    }

    @Override
    public void onFailure(Throwable error) {
        MyApplication.hideTransaprentDialog();

        onMyFailure(error, new MyResponse<AbstractResponse>() {
            @Override
            public void getMyResponse(AbstractResponse responseBody, String errorMsg) {
                listener.getResetPasswordAPI(responseBody,errorMsg);
            }
        });


    }

    public RequestBody getMyRequestBody() {

        if (TextUtils.isEmpty(otp) || /*TextUtils.isEmpty(id)||*/
        TextUtils.isEmpty(newpw) || TextUtils.isEmpty(confirmnewpw)
                ) {
            return null;
        }
        //fname
               // email

        ////mobileotp(1),id(1),newpw(1),confirmnewpw(1)
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mobileotp", otp);
            jsonObject.put("id", id);
            jsonObject.put("newpw", newpw);
            jsonObject.put("confirmnewpw", confirmnewpw);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return getRequestBody(jsonObject);
    }

    public interface ResetPasswordListener {
        public void getResetPasswordAPI(AbstractResponse responce, String errorMessage);
    }
}
