package com.vempower.eezyclinic.mappers;

import android.text.TextUtils;
import android.util.Log;

import com.squareup.okhttp.RequestBody;
import com.vempower.eezyclinic.API.EezyClinicAPI;
import com.vempower.eezyclinic.APIResponce.AppointmentHistoryListAPI;
import com.vempower.eezyclinic.APIResponce.TeleConsultationListAPI;
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

public class TeleConsultationListMapper extends  AbstractMapper implements Callback<TeleConsultationListAPI> {

    private TeleConsultationListMapper.TeleConsultationListListener listener;

    private final int pageNo;
    public TeleConsultationListMapper(int pageNo) {
        this.pageNo = pageNo;
    }

    public void setOnTeleConsultationListListener(TeleConsultationListMapper.TeleConsultationListListener listener) {
        if (listener == null) {
            Log.i(MyApplication.getCurrentActivityContext().getClass().getName(), "Invalid AppointmentHistoryListListener instance.");
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
                listener.getTeleconsultation(null, Utils.getStringFromResources(R.string.network_not_available_lbl));
            }
            return;
        }

        // MyApplication.showTransparentDialog();
        EezyClinicAPI stashDealAPI = MyApplication.getInstance().getEezyClinicAPI();

        RequestBody requestBody = getMyRequestBody();
        if (requestBody == null) {
            MyApplication.hideTransaprentDialog();
            if (listener != null) {
                listener.getTeleconsultation(null,null);
            }
            return;
        }

        Call<TeleConsultationListAPI> apiResponseCall = stashDealAPI.getteleconsultationList(requestBody);

        apiResponseCall.enqueue(this);
    }

    @Override
    public void onResponse(Response<TeleConsultationListAPI> response, Retrofit retrofit) {
        //MyApplication.hideTransaprentDialog();

        getMyResponse(response, new MyResponse<TeleConsultationListAPI>() {
            @Override
            public void getMyResponse(TeleConsultationListAPI responseBody, String errorMsg) {
                listener.getTeleconsultation(responseBody,errorMsg);
            }
        });




    }

    @Override
    public void onFailure(Throwable error) {
        //MyApplication.hideTransaprentDialog();

        onMyFailure(error, new MyResponse<TeleConsultationListAPI>() {
            @Override
            public void getMyResponse(TeleConsultationListAPI responseBody, String errorMsg) {
                listener.getTeleconsultation(responseBody,errorMsg);
            }
        });


    }

    public RequestBody getMyRequestBody() {

        String access_key= SharedPreferenceUtils.getStringValueFromSharedPrefarence(Constants.Pref.USER_VALIDATION_KEY,null);
        if (TextUtils.isEmpty(access_key) ) {
            return null;
        }

        /*
        access_key (1)
     perpage(0)
   page(0)
         */

        //fname
        // email

        //userid(1), password(1)
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("access_key", access_key);
            jsonObject.put("perpage", Constants.RESULT_PAGE_ITEMS_LIMIT);
            jsonObject.put("page", pageNo);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return getRequestBody(jsonObject);
    }

    public interface TeleConsultationListListener {
        public void getTeleconsultation(TeleConsultationListAPI teleConsultationListAPI, String errorMessage);
    }
}
