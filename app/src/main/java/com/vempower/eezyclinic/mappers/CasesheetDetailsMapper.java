package com.vempower.eezyclinic.mappers;

import android.text.TextUtils;
import android.util.Log;

import com.squareup.okhttp.RequestBody;
import com.vempower.eezyclinic.API.EezyClinicAPI;
import com.vempower.eezyclinic.APIResponce.CasesheetDetailsAPI;
import com.vempower.eezyclinic.APIResponce.DashboardAPI;
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

public class CasesheetDetailsMapper extends  AbstractMapper  implements Callback<CasesheetDetailsAPI> {


    private CasesheetDetailsListener listener;
    private final String  appt_id;

    public CasesheetDetailsMapper(String  appt_id) {
        this.appt_id=appt_id;
    }

    public void setOnCasesheetDetailsListener(CasesheetDetailsListener listener) {
        if (listener == null) {
            Log.i(MyApplication.getCurrentActivityContext().getClass().getName(), "Invalid CasesheetDetailsListener instance.");
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
                listener.getCasesheetDetails(null, Utils.getStringFromResources(R.string.network_not_available_lbl));
            }
            return;
        }

        MyApplication.showTransparentDialog();
        EezyClinicAPI stashDealAPI = MyApplication.getInstance().getEezyClinicAPI();

        RequestBody requestBody = getMyRequestBody();
        if (requestBody == null) {
            MyApplication.hideTransaprentDialog();
            if (listener != null) {
                listener.getCasesheetDetails(null,null);
            }
            return;
        }

        Call<CasesheetDetailsAPI> apiResponseCall = stashDealAPI.getCasesheetDetails(requestBody);

        apiResponseCall.enqueue(this);
    }

    @Override
    public void onResponse(Response<CasesheetDetailsAPI> response, Retrofit retrofit) {
        MyApplication.hideTransaprentDialog();

        getMyResponse(response, new MyResponse<CasesheetDetailsAPI>() {
            @Override
            public void getMyResponse(CasesheetDetailsAPI responseBody, String errorMsg) {
                listener.getCasesheetDetails(responseBody,errorMsg);
            }
        });

    }

    @Override
    public void onFailure(Throwable error) {
        MyApplication.hideTransaprentDialog();

        onMyFailure(error, new MyResponse<CasesheetDetailsAPI>() {
            @Override
            public void getMyResponse(CasesheetDetailsAPI responseBody, String errorMsg) {
                listener.getCasesheetDetails(responseBody,errorMsg);
            }
        });


    }

    public RequestBody getMyRequestBody() {

       String access_key= SharedPreferenceUtils.getStringValueFromSharedPrefarence(Constants.Pref.USER_VALIDATION_KEY,null);
        if (TextUtils.isEmpty(access_key) ) {
            return null;
        }
        //fname
               // email

        //userid(1), password(1)
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("access_key", access_key);
            jsonObject.put("appt_id", appt_id);
            //appt_id
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return getRequestBody(jsonObject);
    }

    public interface CasesheetDetailsListener {
        public void getCasesheetDetails(CasesheetDetailsAPI loginAPI, String errorMessage);
    }
}
