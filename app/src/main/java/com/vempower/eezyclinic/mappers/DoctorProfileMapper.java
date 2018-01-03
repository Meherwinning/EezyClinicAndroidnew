package com.vempower.eezyclinic.mappers;

import android.text.TextUtils;
import android.util.Log;

import com.squareup.okhttp.RequestBody;
import com.vempower.eezyclinic.API.EezyClinicAPI;
import com.vempower.eezyclinic.APIResponce.DoctorProfileAPI;
import com.vempower.eezyclinic.APIResponce.LoginAPI;
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

public class DoctorProfileMapper extends  AbstractMapper  implements Callback<DoctorProfileAPI> {


    private DoctorProfileListener listener;
    private  int clinicid,branchid;
    private   int doctorid;

    public DoctorProfileMapper(String doctorid, String clinicid,String branchid) {
        try {
            this.doctorid = Integer.parseInt(doctorid);
            this.clinicid= Integer.parseInt(clinicid);
            this.branchid= Integer.parseInt(branchid);
        }catch (Exception e)
        {
            this.doctorid=-1;
            this.clinicid= -1;
            this.branchid=-1;
        }

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
           // Utils.showToastMsgForNetworkNotAvalable();
            if (listener != null) {
                listener.getDoctorProfileAPI(null, Utils.getStringFromResources(R.string.network_not_available_lbl));
            }
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

        if (doctorid==-1 || clinicid==-1 || branchid==-1) {
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
