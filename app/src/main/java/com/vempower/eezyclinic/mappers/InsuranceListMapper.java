package com.vempower.eezyclinic.mappers;

import android.util.Log;

import com.squareup.okhttp.RequestBody;
import com.vempower.eezyclinic.API.EezyClinicAPI;
import com.vempower.eezyclinic.APIResponce.InsuranceListAPI;
import com.vempower.eezyclinic.APIResponce.SpecalitiesAPI;
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

public class InsuranceListMapper extends AbstractMapper implements Callback<InsuranceListAPI> {


    private InsuranceListListener listener;
    private final String country;
    //http://202.63.103.194:8003/api/search/insurance?country=1


    public InsuranceListMapper(String country) {
        this.country = country;
    }

    public void setOnInsuranceListListener(InsuranceListListener listener) {
        if (listener == null) {
            Log.i(MyApplication.getCurrentActivityContext().getClass().getName(), "Invalid InsuranceListListener instance.");
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
                listener.getInsuranceListAPI(null, Utils.getStringFromResources(R.string.network_not_available_lbl));
            }
            return;
        }

        //MyApplication.showTransparentDialog();
        EezyClinicAPI stashDealAPI = MyApplication.getInstance().getEezyClinicAPI();

       /* RequestBody requestBody = getMyRequestBody();
        if (requestBody == null) {
            MyApplication.hideTransaprentDialog();
            if (listener != null) {
                listener.getInsuranceListAPI(null,null);
            }
            return;
        }*/

        Call<InsuranceListAPI> apiResponseCall = stashDealAPI.getInsuranceListAPI(country);

        apiResponseCall.enqueue(this);
    }

    @Override
    public void onResponse(Response<InsuranceListAPI> response, Retrofit retrofit) {
        // MyApplication.hideTransaprentDialog();

        getMyResponse(response, new MyResponse<InsuranceListAPI>() {
            @Override
            public void getMyResponse(InsuranceListAPI responseBody, String errorMsg) {
                listener.getInsuranceListAPI(responseBody, errorMsg);
            }
        });


    }

    @Override
    public void onFailure(Throwable error) {
        //MyApplication.hideTransaprentDialog();

        onMyFailure(error, new MyResponse<InsuranceListAPI>() {
            @Override
            public void getMyResponse(InsuranceListAPI responseBody, String errorMsg) {
                listener.getInsuranceListAPI(responseBody, errorMsg);
            }
        });


    }

    public RequestBody getMyRequestBody() {

      /* String access_key= SharedPreferenceUtils.getStringValueFromSharedPrefarence(Constants.Pref.USER_VALIDATION_KEY,null);
        if (TextUtils.isEmpty(access_key) ) {
            return null;
        }*/
        //fname
        // email

        //userid(1), password(1)
        JSONObject jsonObject = new JSONObject();
         try {
            jsonObject.put("country", country);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return getRequestBody(jsonObject);
    }

    public interface InsuranceListListener {
        public void getInsuranceListAPI(InsuranceListAPI insuranceListAPI, String errorMessage);
    }
}
