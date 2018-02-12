package com.vempower.eezyclinic.mappers;

import android.text.TextUtils;
import android.util.Log;

import com.squareup.okhttp.RequestBody;
import com.vempower.eezyclinic.API.EezyClinicAPI;
import com.vempower.eezyclinic.APIResponce.AbstractResponse;
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

public class UpdateWeightHeightCheckMapper extends AbstractMapper implements Callback<AbstractResponse> {


    private UpdateWeightHeightCheckListener listener;

    /*
    {
  "access_key": "064644078000133a04da6ad11af9f78e",
  "checkupgroupid":76,
 "test_type": "WH",
  "weight":65,
  "height":5.4,
  "checkuptime":"2017-12-29 17:51:55"
}
     */


    private final String checkupgroupid, weight, height, checkuptime;

    public UpdateWeightHeightCheckMapper(String checkupgroupid, String weight,
                                         String height,String checkuptime) {
        this.checkupgroupid=checkupgroupid;
        this.weight = weight;
        this.height = height;
       // this.triglicerides = triglicerides;
       // this.totalcholestral = totalcholestral;
        this.checkuptime = checkuptime;
    }

    public void setOnUpdateWeightHeightCheckListener(UpdateWeightHeightCheckListener listener) {
        if (listener == null) {
            Log.i(MyApplication.getCurrentActivityContext().getClass().getName(), "Invalid UpdateWeightHeightCheckListener instance.");
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
                listener.updateWeightHeightCheck(null, Utils.getStringFromResources(R.string.network_not_available_lbl));
            }
            return;
        }

        // MyApplication.showTransparentDialog();
        EezyClinicAPI stashDealAPI = MyApplication.getInstance().getEezyClinicAPI();

        RequestBody requestBody = getMyRequestBody();
        if (requestBody == null) {
            MyApplication.hideTransaprentDialog();
            if (listener != null) {
                listener.updateWeightHeightCheck(null, null);
            }
            return;
        }

        Call<AbstractResponse> apiResponseCall = stashDealAPI.edithealthwpcheck(requestBody);

        apiResponseCall.enqueue(this);
    }

    @Override
    public void onResponse(Response<AbstractResponse> response, Retrofit retrofit) {
        //MyApplication.hideTransaprentDialog();

        getMyResponse(response, new MyResponse<AbstractResponse>() {
            @Override
            public void getMyResponse(AbstractResponse responseBody, String errorMsg) {
                listener.updateWeightHeightCheck(responseBody, errorMsg);
            }
        });


    }

    @Override
    public void onFailure(Throwable error) {
        //MyApplication.hideTransaprentDialog();

        onMyFailure(error, new MyResponse<AbstractResponse>() {
            @Override
            public void getMyResponse(AbstractResponse responseBody, String errorMsg) {
                listener.updateWeightHeightCheck(responseBody, errorMsg);
            }
        });


    }

    public RequestBody getMyRequestBody() {

        String access_key = SharedPreferenceUtils.getStringValueFromSharedPrefarence(Constants.Pref.USER_VALIDATION_KEY, null);
        if (TextUtils.isEmpty(access_key)) {
            return null;
        }
//checkupgroupid, weight, height
        if (TextUtils.isEmpty(checkupgroupid) || TextUtils.isEmpty(weight) || TextUtils.isEmpty(height)
                || TextUtils.isEmpty(checkuptime)
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
            jsonObject.put("checkupgroupid", checkupgroupid);
            jsonObject.put("test_type", "WH");
            jsonObject.put("weight", weight);
            jsonObject.put("height", height);
            //jsonObject.put("triglicerides", triglicerides);
           // jsonObject.put("totalcholestral", totalcholestral);
            jsonObject.put("checkuptime", checkuptime);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return getRequestBody(jsonObject);
    }

    public interface UpdateWeightHeightCheckListener {
        public void updateWeightHeightCheck(AbstractResponse response, String errorMessage);
    }
}
