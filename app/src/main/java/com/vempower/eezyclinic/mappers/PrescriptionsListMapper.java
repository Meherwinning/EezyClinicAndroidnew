package com.vempower.eezyclinic.mappers;

import android.text.TextUtils;
import android.util.Log;

import com.squareup.okhttp.RequestBody;
import com.vempower.eezyclinic.API.EezyClinicAPI;
import com.vempower.eezyclinic.APIResponce.InsuranceListAPI;
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

public class PrescriptionsListMapper extends  AbstractMapper  implements Callback<PrescriptionsListAPI> {


    private PrescriptionsListListener listener;


    /*

    "perpage":"20",
"page":"1"
     */

  /* private int page=1;

    public PrescriptionsListMapper(int page) {
        this.page=page;
    }*/

    public void setOnPrescriptionsListListener(PrescriptionsListListener listener) {
        if (listener == null) {
            Log.i(MyApplication.getCurrentActivityContext().getClass().getName(), "Invalid PrescriptionsListListener instance.");
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
                listener.getPrescriptionsListAPI(null, Utils.getStringFromResources(R.string.network_not_available_lbl));
            }
            return;
        }

        MyApplication.showTransparentDialog();
        EezyClinicAPI stashDealAPI = MyApplication.getInstance().getEezyClinicAPI();

        RequestBody requestBody = getMyRequestBody();
        if (requestBody == null) {
            MyApplication.hideTransaprentDialog();
            if (listener != null) {
                listener.getPrescriptionsListAPI(null,null);
            }
            return;
        }

        Call<PrescriptionsListAPI> apiResponseCall = stashDealAPI.getPrescriptionsList(requestBody);

        apiResponseCall.enqueue(this);
    }

    @Override
    public void onResponse(Response<PrescriptionsListAPI> response, Retrofit retrofit) {
        MyApplication.hideTransaprentDialog();

        getMyResponse(response, new MyResponse<PrescriptionsListAPI>() {
            @Override
            public void getMyResponse(PrescriptionsListAPI responseBody, String errorMsg) {
                listener.getPrescriptionsListAPI(responseBody,errorMsg);
            }
        });




    }

    @Override
    public void onFailure(Throwable error) {
        MyApplication.hideTransaprentDialog();

        onMyFailure(error, new MyResponse<PrescriptionsListAPI>() {
            @Override
            public void getMyResponse(PrescriptionsListAPI responseBody, String errorMsg) {
                listener.getPrescriptionsListAPI(responseBody,errorMsg);
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

    public interface PrescriptionsListListener {
        public void getPrescriptionsListAPI(PrescriptionsListAPI prescriptionsListAPI, String errorMessage);
    }
}
