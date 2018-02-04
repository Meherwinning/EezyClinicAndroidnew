package com.vempower.eezyclinic.mappers;

import android.text.TextUtils;
import android.util.Log;

import com.squareup.okhttp.RequestBody;
import com.vempower.eezyclinic.API.EezyClinicAPI;
import com.vempower.eezyclinic.APIResponce.DashboardAPI;
import com.vempower.eezyclinic.APIResponce.SearchFamilyMemberAPI;
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

public class SearchFamilyMemberMapper extends  AbstractMapper  implements Callback<SearchFamilyMemberAPI> {


    private SearchFamilyMemberListener listener;
    /*
    "patient_id" : "PT00339"
     */
    private final String patient_id;

    public SearchFamilyMemberMapper(String patient_id) {
        this.patient_id = patient_id;
    }

    public void setOnSearchFamilyMemberListener(SearchFamilyMemberListener listener) {
        if (listener == null) {
            Log.i(MyApplication.getCurrentActivityContext().getClass().getName(), "Invalid SearchFamilyMemberListener instance.");
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
                listener.searchFamilyMember(null, Utils.getStringFromResources(R.string.network_not_available_lbl));
            }
            return;
        }

       // MyApplication.showTransparentDialog();
        EezyClinicAPI stashDealAPI = MyApplication.getInstance().getEezyClinicAPI();

        RequestBody requestBody = getMyRequestBody();
        if (requestBody == null) {
            MyApplication.hideTransaprentDialog();
            if (listener != null) {
                listener.searchFamilyMember(null,null);
            }
            return;
        }

        Call<SearchFamilyMemberAPI> apiResponseCall = stashDealAPI.searchFamilyMember(requestBody);

        apiResponseCall.enqueue(this);
    }

    @Override
    public void onResponse(Response<SearchFamilyMemberAPI> response, Retrofit retrofit) {
        //MyApplication.hideTransaprentDialog();

        getMyResponse(response, new MyResponse<SearchFamilyMemberAPI>() {
            @Override
            public void getMyResponse(SearchFamilyMemberAPI responseBody, String errorMsg) {
                listener.searchFamilyMember(responseBody,errorMsg);
            }
        });




    }

    @Override
    public void onFailure(Throwable error) {
        //MyApplication.hideTransaprentDialog();

        onMyFailure(error, new MyResponse<SearchFamilyMemberAPI>() {
            @Override
            public void getMyResponse(SearchFamilyMemberAPI responseBody, String errorMsg) {
                listener.searchFamilyMember(responseBody,errorMsg);
            }
        });


    }

    public RequestBody getMyRequestBody() {

       String access_key= SharedPreferenceUtils.getStringValueFromSharedPrefarence(Constants.Pref.USER_VALIDATION_KEY,null);
        if (TextUtils.isEmpty(access_key)  || TextUtils.isEmpty(patient_id)) {
            return null;
        }
        //fname
               // email

        //userid(1), password(1)
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("access_key", access_key);
            jsonObject.put("patient_id", patient_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return getRequestBody(jsonObject);
    }

    public interface SearchFamilyMemberListener {
        public void searchFamilyMember(SearchFamilyMemberAPI memberAPI, String errorMessage);
    }
}
