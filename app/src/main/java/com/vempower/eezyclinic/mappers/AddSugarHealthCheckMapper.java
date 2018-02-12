package com.vempower.eezyclinic.mappers;

import android.text.TextUtils;
import android.util.Log;

import com.squareup.okhttp.RequestBody;
import com.vempower.eezyclinic.API.EezyClinicAPI;
import com.vempower.eezyclinic.APIResponce.AbstractResponse;
import com.vempower.eezyclinic.APIResponce.AddhealthCheckAPI;
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

public class AddSugarHealthCheckMapper extends AbstractMapper implements Callback<AddhealthCheckAPI> {


    private AddSugarHealthCheckListener listener;

    /*
    {
  "access_key": "064644078000133a04da6ad11af9f78e",
  "test_type": "Sugar Level",
  "fasting":40,
  "lunch":140,
  "hba1c":240,
  "checkuptime":"2017-12-29 17:51:55"
}

     */

    private final String fasting, lunch, hba1c, checkuptime;

    public AddSugarHealthCheckMapper(String fasting, String lunch, String hba1c, String checkuptime) {
        this.fasting = fasting;
        this.lunch = lunch;
        this.hba1c = hba1c;
        this.checkuptime = checkuptime;
    }

    public void setOnAddSugarHealthCheckListener(AddSugarHealthCheckListener listener) {
        if (listener == null) {
            Log.i(MyApplication.getCurrentActivityContext().getClass().getName(), "Invalid AddSugarHealthCheckListener instance.");
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
                listener.addSugar(null, Utils.getStringFromResources(R.string.network_not_available_lbl));
            }
            return;
        }

        // MyApplication.showTransparentDialog();
        EezyClinicAPI stashDealAPI = MyApplication.getInstance().getEezyClinicAPI();

        RequestBody requestBody = getMyRequestBody();
        if (requestBody == null) {
            MyApplication.hideTransaprentDialog();
            if (listener != null) {
                listener.addSugar(null, null);
            }
            return;
        }

        Call<AddhealthCheckAPI> apiResponseCall = stashDealAPI.addHealthCheck(requestBody);

        apiResponseCall.enqueue(this);
    }

    @Override
    public void onResponse(Response<AddhealthCheckAPI> response, Retrofit retrofit) {
        //MyApplication.hideTransaprentDialog();

        getMyResponse(response, new MyResponse<AddhealthCheckAPI>() {
            @Override
            public void getMyResponse(AddhealthCheckAPI responseBody, String errorMsg) {
                listener.addSugar(responseBody, errorMsg);
            }
        });


    }

    @Override
    public void onFailure(Throwable error) {
        //MyApplication.hideTransaprentDialog();

        onMyFailure(error, new MyResponse<AddhealthCheckAPI>() {
            @Override
            public void getMyResponse(AddhealthCheckAPI responseBody, String errorMsg) {
                listener.addSugar(responseBody, errorMsg);
            }
        });


    }

    public RequestBody getMyRequestBody() {

        String access_key = SharedPreferenceUtils.getStringValueFromSharedPrefarence(Constants.Pref.USER_VALIDATION_KEY, null);
        if (TextUtils.isEmpty(access_key)) {
            return null;
        }

        if (TextUtils.isEmpty(fasting) || TextUtils.isEmpty(lunch)
                || TextUtils.isEmpty(hba1c) || TextUtils.isEmpty(checkuptime)
                ) {
            return null;
        }

        /*
    {
  "access_key": "064644078000133a04da6ad11af9f78e",
  "test_type": "Sugar Level",
  "fasting":40,
  "lunch":140,
  "hba1c":240,
  "checkuptime":"2017-12-29 17:51:55"
}
     */
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("access_key", access_key);

            jsonObject.put("test_type", "Sugar Level");
            jsonObject.put("fasting", fasting);
            jsonObject.put("lunch", lunch);
            jsonObject.put("hba1c", hba1c);
            jsonObject.put("checkuptime", checkuptime);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return getRequestBody(jsonObject);
    }

    public interface AddSugarHealthCheckListener {
        public void addSugar(AddhealthCheckAPI response, String errorMessage);
    }
}
