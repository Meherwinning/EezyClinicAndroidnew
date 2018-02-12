package com.vempower.eezyclinic.mappers;

import android.text.TextUtils;
import android.util.Log;

import com.squareup.okhttp.RequestBody;
import com.vempower.eezyclinic.API.EezyClinicAPI;
import com.vempower.eezyclinic.APIResponce.AbstractResponse;
import com.vempower.eezyclinic.APIResponce.AddWHhealthCheckAPI;
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

public class AddHeightAndWeightHealthCheckMapper extends AbstractMapper implements Callback<AddWHhealthCheckAPI> {


    private AddHeightAndWeightHealthCheckListener listener;

    private final String weight, height, checkuptime;

    public AddHeightAndWeightHealthCheckMapper(String weight, String height,  String checkuptime) {
        this.weight = weight;
        this.height = height;
        this.checkuptime = checkuptime;
    }

    public void setOnAddHeightAndWeightHealthCheckListener(AddHeightAndWeightHealthCheckListener listener) {
        if (listener == null) {
            Log.i(MyApplication.getCurrentActivityContext().getClass().getName(), "Invalid AddCholHealthCheckListener instance.");
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
                listener.addHeightAndWeigh(null, Utils.getStringFromResources(R.string.network_not_available_lbl));
            }
            return;
        }

        // MyApplication.showTransparentDialog();
        EezyClinicAPI stashDealAPI = MyApplication.getInstance().getEezyClinicAPI();

        RequestBody requestBody = getMyRequestBody();
        if (requestBody == null) {
            MyApplication.hideTransaprentDialog();
            if (listener != null) {
                listener.addHeightAndWeigh(null, null);
            }
            return;
        }

        Call<AddWHhealthCheckAPI> apiResponseCall = stashDealAPI.addHealthWHcheck(requestBody);

        apiResponseCall.enqueue(this);
    }

    @Override
    public void onResponse(Response<AddWHhealthCheckAPI> response, Retrofit retrofit) {
        //MyApplication.hideTransaprentDialog();

        getMyResponse(response, new MyResponse<AddWHhealthCheckAPI>() {
            @Override
            public void getMyResponse(AddWHhealthCheckAPI responseBody, String errorMsg) {
                listener.addHeightAndWeigh(responseBody, errorMsg);
            }
        });


    }

    @Override
    public void onFailure(Throwable error) {
        //MyApplication.hideTransaprentDialog();

        onMyFailure(error, new MyResponse<AddWHhealthCheckAPI>() {
            @Override
            public void getMyResponse(AddWHhealthCheckAPI responseBody, String errorMsg) {
                listener.addHeightAndWeigh(responseBody, errorMsg);
            }
        });


    }

    public RequestBody getMyRequestBody() {

        String access_key = SharedPreferenceUtils.getStringValueFromSharedPrefarence(Constants.Pref.USER_VALIDATION_KEY, null);
        if (TextUtils.isEmpty(access_key)) {
            return null;
        }

        //weight, height,
        if (TextUtils.isEmpty(weight) || TextUtils.isEmpty(height)
                ||  TextUtils.isEmpty(checkuptime)
                ) {
            return null;
        }


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("access_key", access_key);
            jsonObject.put("test_type", "WH");
            jsonObject.put("weight", weight);
            jsonObject.put("height", height);
            jsonObject.put("checkuptime", checkuptime);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return getRequestBody(jsonObject);
    }

    public interface AddHeightAndWeightHealthCheckListener {
        public void addHeightAndWeigh(AddWHhealthCheckAPI response, String errorMessage);
    }
}
