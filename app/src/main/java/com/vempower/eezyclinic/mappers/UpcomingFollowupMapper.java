package com.vempower.eezyclinic.mappers;

import android.text.TextUtils;
import android.util.Log;

import com.squareup.okhttp.RequestBody;
import com.vempower.eezyclinic.API.EezyClinicAPI;
import com.vempower.eezyclinic.APIResponce.DashboardAPI;
import com.vempower.eezyclinic.APIResponce.UpcomingFollowupsAPI;
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

public class UpcomingFollowupMapper extends AbstractMapper implements Callback<UpcomingFollowupsAPI> {


    private UpcomingFollowupsListener listener;


    public void setOnUpcomingFollowupsListener(UpcomingFollowupsListener listener) {
        if (listener == null) {
            Log.i(MyApplication.getCurrentActivityContext().getClass().getName(), "Invalid UpcomingFollowupsListener instance.");
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
                listener.getUpcomingFollowupsAPI(null, Utils.getStringFromResources(R.string.network_not_available_lbl));
            }
            return;
        }

        // MyApplication.showTransparentDialog();
        EezyClinicAPI stashDealAPI = MyApplication.getInstance().getEezyClinicAPI();

        RequestBody requestBody = getMyRequestBody();
        if (requestBody == null) {
            MyApplication.hideTransaprentDialog();
            if (listener != null) {
                listener.getUpcomingFollowupsAPI(null, null);
            }
            return;
        }

        Call<UpcomingFollowupsAPI> apiResponseCall = stashDealAPI.followupslist(requestBody);

        apiResponseCall.enqueue(this);
    }

    @Override
    public void onResponse(Response<UpcomingFollowupsAPI> response, Retrofit retrofit) {
        //MyApplication.hideTransaprentDialog();

        getMyResponse(response, new MyResponse<UpcomingFollowupsAPI>() {
            @Override
            public void getMyResponse(UpcomingFollowupsAPI responseBody, String errorMsg) {
                listener.getUpcomingFollowupsAPI(responseBody, errorMsg);
            }
        });


    }

    @Override
    public void onFailure(Throwable error) {
        //MyApplication.hideTransaprentDialog();

        onMyFailure(error, new MyResponse<UpcomingFollowupsAPI>() {
            @Override
            public void getMyResponse(UpcomingFollowupsAPI responseBody, String errorMsg) {
                listener.getUpcomingFollowupsAPI(responseBody, errorMsg);
            }
        });


    }

    public RequestBody getMyRequestBody() {

        String access_key = SharedPreferenceUtils.getStringValueFromSharedPrefarence(Constants.Pref.USER_VALIDATION_KEY, null);
        if (TextUtils.isEmpty(access_key)) {
            return null;
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("access_key", access_key);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return getRequestBody(jsonObject);
    }

    public interface UpcomingFollowupsListener {
        public void getUpcomingFollowupsAPI(UpcomingFollowupsAPI followupsAPI, String errorMessage);
    }
}
