package com.vempower.eezyclinic.mappers;

import android.text.TextUtils;
import android.util.Log;

import com.squareup.okhttp.RequestBody;
import com.vempower.eezyclinic.API.EezyClinicAPI;
import com.vempower.eezyclinic.APIResponce.AppointmentTimeSlotsAPI;
import com.vempower.eezyclinic.APIResponce.DoctorProfileAPI;
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
 * Created by Satishk on 14/11/2017.
 */

public class DoctorAppointmentTimeSlotsListMapper extends AbstractMapper implements Callback<AppointmentTimeSlotsAPI> {

    /*
    {
        "doc_id": 46,
        "branch_id": 40,
        "appt_date": "2018-01-16"
}
     */

    private AppointmentTimeSlotsListener listener;
    private final int doc_id, branch_id;
    private final String  appt_date;

    public DoctorAppointmentTimeSlotsListMapper(int doc_id, int branch_id, String appt_date) {
        this.doc_id = doc_id;
        this.branch_id = branch_id;
        this.appt_date = appt_date;
    }

    public void setOnAppointmentTimeSlotsListenerr(AppointmentTimeSlotsListener listener) {
        if (listener == null) {
            Log.i(MyApplication.getCurrentActivityContext().getClass().getName(), "Invalid DoctorProfileListener instance.");
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
                listener.getAppointmentTimeSlotsListAPI(null, Utils.getStringFromResources(R.string.network_not_available_lbl));

                return;
            }
        }

        MyApplication.showTransparentDialog();
        EezyClinicAPI stashDealAPI = MyApplication.getInstance().getEezyClinicAPI();

        RequestBody requestBody = getMyRequestBody();
        if (requestBody == null) {
            MyApplication.hideTransaprentDialog();
            if (listener != null) {
                listener.getAppointmentTimeSlotsListAPI(null, null);
            }
            return;
        }

        Call<AppointmentTimeSlotsAPI> apiResponseCall = stashDealAPI.getAppointmentTimeSlots(requestBody);

        apiResponseCall.enqueue(this);
    }



    public RequestBody getMyRequestBody() {



        if (TextUtils.isEmpty(appt_date) || doc_id<=0 || branch_id <=0) {
            return null;
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("doc_id", doc_id);
            jsonObject.put("branch_id", branch_id);
            jsonObject.put("appt_date", appt_date);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return getRequestBody(jsonObject);
    }

    /**
     * Successful HTTP response.
     *
     * @param response
     * @param retrofit
     */
    @Override
    public void onResponse(Response<AppointmentTimeSlotsAPI> response, Retrofit retrofit) {
        MyApplication.hideTransaprentDialog();

        getMyResponse(response, new MyResponse<AppointmentTimeSlotsAPI>() {
            @Override
            public void getMyResponse(AppointmentTimeSlotsAPI responseBody, String errorMsg) {
                listener.getAppointmentTimeSlotsListAPI(responseBody, errorMsg);
            }
        });
    }


    @Override
    public void onFailure(Throwable error) {
        MyApplication.hideTransaprentDialog();

        onMyFailure(error, new MyResponse<AppointmentTimeSlotsAPI>() {
            @Override
            public void getMyResponse(AppointmentTimeSlotsAPI responseBody, String errorMsg) {
                listener.getAppointmentTimeSlotsListAPI(responseBody, errorMsg);
            }
        });

    }

    public interface AppointmentTimeSlotsListener {
        public void getAppointmentTimeSlotsListAPI(AppointmentTimeSlotsAPI doctorProfileAPI, String errorMessage);
    }


}

