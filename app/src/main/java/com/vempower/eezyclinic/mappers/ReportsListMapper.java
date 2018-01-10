package com.vempower.eezyclinic.mappers;

import android.text.TextUtils;
import android.util.Log;

import com.squareup.okhttp.RequestBody;
import com.vempower.eezyclinic.API.EezyClinicAPI;
import com.vempower.eezyclinic.APIResponce.HelathReportsListAPI;
import com.vempower.eezyclinic.APIResponce.PrescriptionsListAPI;
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

public class ReportsListMapper extends  AbstractMapper  implements Callback<HelathReportsListAPI> {


    private HelathReportsListListener listener;




    public void setOnHelathReportsListListener(HelathReportsListListener listener) {
        if (listener == null) {
            Log.i(MyApplication.getCurrentActivityContext().getClass().getName(), "Invalid HelathReportsListListener instance.");
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
                listener.getHelathReportsListAPI(null, Utils.getStringFromResources(R.string.network_not_available_lbl));
            }
            return;
        }

        MyApplication.showTransparentDialog();
        EezyClinicAPI stashDealAPI = MyApplication.getInstance().getEezyClinicAPI();

        RequestBody requestBody = getMyRequestBody();
        if (requestBody == null) {
            MyApplication.hideTransaprentDialog();
            if (listener != null) {
                listener.getHelathReportsListAPI(null,null);
            }
            return;
        }

        Call<HelathReportsListAPI> apiResponseCall = stashDealAPI.getHelathReportsListAPI(requestBody);

        apiResponseCall.enqueue(this);
    }

    @Override
    public void onResponse(Response<HelathReportsListAPI> response, Retrofit retrofit) {
        MyApplication.hideTransaprentDialog();

        getMyResponse(response, new MyResponse<HelathReportsListAPI>() {
            @Override
            public void getMyResponse(HelathReportsListAPI responseBody, String errorMsg) {
                listener.getHelathReportsListAPI(responseBody,errorMsg);
            }
        });




    }

    @Override
    public void onFailure(Throwable error) {
        MyApplication.hideTransaprentDialog();

        onMyFailure(error, new MyResponse<HelathReportsListAPI>() {
            @Override
            public void getMyResponse(HelathReportsListAPI responseBody, String errorMsg) {
                listener.getHelathReportsListAPI(responseBody,errorMsg);
            }
        });


    }

    public RequestBody getMyRequestBody() {

       String access_key= SharedPreferenceUtils.getStringValueFromSharedPrefarence(Constants.Pref.USER_VALIDATION_KEY,null);
        if (TextUtils.isEmpty(access_key) ) {
            return null;
        }
       /*
        "perpage":"20",
"page":"1"
        */
       JSONObject jsonObject = new JSONObject();
         try {
            jsonObject.put("access_key", access_key);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return getRequestBody(jsonObject);
    }

    public interface HelathReportsListListener {
        public void getHelathReportsListAPI(HelathReportsListAPI helathReportsListAPI, String errorMessage);
    }
}
