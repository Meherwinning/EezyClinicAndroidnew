package com.vempower.eezyclinic.mappers;

import android.text.TextUtils;
import android.util.Log;

import com.squareup.okhttp.RequestBody;
import com.vempower.eezyclinic.API.EezyClinicAPI;
import com.vempower.eezyclinic.APIResponce.DoctorProfileAPI;
import com.vempower.eezyclinic.APIResponce.LoginAPI;
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

public class DoctorProfileMapper extends  AbstractMapper  implements Callback<DoctorProfileAPI> {


    private DoctorProfileListener listener;
    private final String doctorid,clinicid,branchid;

    public DoctorProfileMapper(String doctorid, String clinicid,String branchid) {
        this.doctorid=doctorid;
        this.clinicid=clinicid;
        this.branchid=branchid;
    }

    public void setOnDoctorProfileListenerr(DoctorProfileListener listener) {
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
            Utils.showToastMsgForNetworkNotAvalable();
            return;
        }

        MyApplication.showTransparentDialog();
        EezyClinicAPI stashDealAPI = MyApplication.getInstance().getEezyClinicAPI();

        RequestBody requestBody = getMyRequestBody();
        if (requestBody == null) {
            MyApplication.hideTransaprentDialog();
            if (listener != null) {
                listener.getDoctorProfileAPI(null,null);
            }
            return;
        }

        Call<DoctorProfileAPI> apiResponseCall = stashDealAPI.getDoctorProfileAPI(requestBody);

        apiResponseCall.enqueue(this);
    }

    @Override
    public void onResponse(Response<DoctorProfileAPI> response, Retrofit retrofit) {
        MyApplication.hideTransaprentDialog();

        getMyResponse(response, new MyResponse<DoctorProfileAPI>() {
            @Override
            public void getMyResponse(DoctorProfileAPI responseBody, String errorMsg) {
                listener.getDoctorProfileAPI(responseBody,errorMsg);
            }
        });
    }

    @Override
    public void onFailure(Throwable error) {
        MyApplication.hideTransaprentDialog();

        onMyFailure(error, new MyResponse<DoctorProfileAPI>() {
            @Override
            public void getMyResponse(DoctorProfileAPI responseBody, String errorMsg) {
                listener.getDoctorProfileAPI(responseBody,errorMsg);
            }
        });


    }

    public RequestBody getMyRequestBody() {

        if (TextUtils.isEmpty(doctorid) || TextUtils.isEmpty(clinicid) || TextUtils.isEmpty(branchid)) {
            return null;
        }
        /*{
            "doctorid":"46",
                "clinicid":"21",
                "branchid":40

        }*/
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("doctorid", doctorid);
            jsonObject.put("clinicid", clinicid);
            jsonObject.put("branchid", branchid);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return getRequestBody(jsonObject);
    }

    public interface DoctorProfileListener {
        public void getDoctorProfileAPI(DoctorProfileAPI doctorProfileAPI, String errorMessage);
    }
}
