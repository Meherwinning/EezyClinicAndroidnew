package com.vempower.eezyclinic.mappers;

import android.text.TextUtils;
import android.util.Log;

import com.squareup.okhttp.RequestBody;
import com.vempower.eezyclinic.API.EezyClinicAPI;
import com.vempower.eezyclinic.APIResponce.AbstractResponse;
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

public class SubmitFeedbackMapper extends  AbstractMapper  implements Callback<AbstractResponse> {

    private SubmitFeedbackListener listener;

    private final int appointmentId,doctorId,clinicId,
            waitingTime,diagnasis,customerservice,recommend,displayName;
    private final String description;


    /*
    {
         "access_key":"360f19438d8cb884057f79a1a55df441",
                "appointmentId" : 1416,
                "doctorId" : 122,
                "clinicId" : 31,
                "description":"my first review",
                "waitingTime" : 2,
                "diagnasis" : 3,
                "customerservice":        4,
                "recommend":1,
                "displayName":1
}
     */
    public SubmitFeedbackMapper(int appointmentId,int doctorId,int clinicId,
                                String description,int waitingTime,int diagnasis,
                                int customerservice,int recommend,int displayName
    ) {
        this.appointmentId = appointmentId;
        this.doctorId=doctorId;
        this.clinicId=clinicId;
        this.description=description;
        this.waitingTime=waitingTime;
        this.diagnasis=diagnasis;
        this.customerservice=customerservice;
        this.recommend=recommend;
        this.displayName=displayName;
    }

    public void setOnSubmitFeedbackListener(SubmitFeedbackListener listener) {
        if (listener == null) {
            Log.i(MyApplication.getCurrentActivityContext().getClass().getName(), "Invalid DashboardListener instance.");
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
                listener.submitFeedback(null, Utils.getStringFromResources(R.string.network_not_available_lbl));
            }
            return;
        }

       // MyApplication.showTransparentDialog();
        EezyClinicAPI stashDealAPI = MyApplication.getInstance().getEezyClinicAPI();

        RequestBody requestBody = getMyRequestBody();
        if (requestBody == null) {
            MyApplication.hideTransaprentDialog();
            if (listener != null) {
                listener.submitFeedback(null,null);
            }
            return;
        }

        Call<AbstractResponse> apiResponseCall = stashDealAPI.submitfeedback(requestBody);

        apiResponseCall.enqueue(this);
    }

    @Override
    public void onResponse(Response<AbstractResponse> response, Retrofit retrofit) {
        //MyApplication.hideTransaprentDialog();

        getMyResponse(response, new MyResponse<AbstractResponse>() {
            @Override
            public void getMyResponse(AbstractResponse responseBody, String errorMsg) {
                listener.submitFeedback(responseBody,errorMsg);
            }
        });




    }

    @Override
    public void onFailure(Throwable error) {
        //MyApplication.hideTransaprentDialog();

        onMyFailure(error, new MyResponse<AbstractResponse>() {
            @Override
            public void getMyResponse(AbstractResponse responseBody, String errorMsg) {
                listener.submitFeedback(responseBody,errorMsg);
            }
        });


    }

    public RequestBody getMyRequestBody() {

       String access_key= SharedPreferenceUtils.getStringValueFromSharedPrefarence(Constants.Pref.USER_VALIDATION_KEY,null);
        if (TextUtils.isEmpty(access_key)  ) {
            return null;
        }
        /*
    {
         "access_key":"360f19438d8cb884057f79a1a55df441",
                "appointmentId" : 1416,
                "doctorId" : 122,
                "clinicId" : 31,
                "description":"my first review",
                "waitingTime" : 2,
                "diagnasis" : 3,
                "customerservice":        4,
                "recommend":1,
                "displayName":1
}
     */
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("access_key", access_key);
            jsonObject.put("appointmentId", appointmentId);
            jsonObject.put("doctorId", doctorId);
            jsonObject.put("clinicId", clinicId);
            jsonObject.put("description", description);
            jsonObject.put("waitingTime", waitingTime);
            jsonObject.put("diagnasis", diagnasis);
            jsonObject.put("customerservice", customerservice);
            jsonObject.put("recommend", recommend);
            jsonObject.put("appointmentId", appointmentId);
            jsonObject.put("displayName", displayName);



        } catch (JSONException e) {
            e.printStackTrace();
        }

        return getRequestBody(jsonObject);
    }

    public interface SubmitFeedbackListener {
        public void submitFeedback(AbstractResponse abstractResponse, String errorMessage);
    }
}
