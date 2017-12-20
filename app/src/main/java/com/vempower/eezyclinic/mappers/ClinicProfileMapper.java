package com.vempower.eezyclinic.mappers;

import android.text.TextUtils;
import android.util.Log;

import com.squareup.okhttp.RequestBody;
import com.vempower.eezyclinic.API.EezyClinicAPI;
import com.vempower.eezyclinic.APIResponce.ClinicProfileAPI;
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

public class ClinicProfileMapper extends  AbstractMapper  implements Callback<ClinicProfileAPI> {


    private ClinicProfileAPIListener listener;
    private final String clinicid,branchid;

    public ClinicProfileMapper(String clinicid, String branchid) {
       // this.doctorid=doctorid;
        this.clinicid=clinicid;
        this.branchid=branchid;
    }

    public void setOnClinicProfileAPIListener(ClinicProfileAPIListener listener) {
        if (listener == null) {
            Log.i(MyApplication.getCurrentActivityContext().getClass().getName(), "Invalid ClinicProfileAPIListener instance.");
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
                listener.getClinicProfileAPI(null, Utils.getStringFromResources(R.string.network_not_available_lbl));
            }
            return;
        }

        MyApplication.showTransparentDialog();
        EezyClinicAPI stashDealAPI = MyApplication.getInstance().getEezyClinicAPI();

        RequestBody requestBody = getMyRequestBody();
        if (requestBody == null) {
            MyApplication.hideTransaprentDialog();
            if (listener != null) {
                listener.getClinicProfileAPI(null,null);
            }
            return;
        }

        Call<ClinicProfileAPI> apiResponseCall = stashDealAPI.getClinicProfileAPI(requestBody);

        apiResponseCall.enqueue(this);
    }

    @Override
    public void onResponse(Response<ClinicProfileAPI> response, Retrofit retrofit) {
        MyApplication.hideTransaprentDialog();

        getMyResponse(response, new MyResponse<ClinicProfileAPI>() {
            @Override
            public void getMyResponse(ClinicProfileAPI responseBody, String errorMsg) {
                listener.getClinicProfileAPI(responseBody,errorMsg);
            }
        });
    }

    @Override
    public void onFailure(Throwable error) {
        MyApplication.hideTransaprentDialog();

        onMyFailure(error, new MyResponse<ClinicProfileAPI>() {
            @Override
            public void getMyResponse(ClinicProfileAPI responseBody, String errorMsg) {
                listener.getClinicProfileAPI(responseBody,errorMsg);
            }
        });


    }

    public RequestBody getMyRequestBody() {

        if ( TextUtils.isEmpty(clinicid) || TextUtils.isEmpty(branchid)) {
            return null;
        }
        /*{
            "doctorid":"46",
                "clinicid":"21",
                "branchid":40

        }*/
        JSONObject jsonObject = new JSONObject();
        try {
           // jsonObject.put("doctorid", doctorid);
            jsonObject.put("clinicid", clinicid);
            jsonObject.put("branchid", branchid);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return getRequestBody(jsonObject);
    }

    public interface ClinicProfileAPIListener {
        public void getClinicProfileAPI(ClinicProfileAPI clinicProfileAPI, String errorMessage);
    }
}
