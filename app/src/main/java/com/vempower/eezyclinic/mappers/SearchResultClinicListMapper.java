package com.vempower.eezyclinic.mappers;

import android.text.TextUtils;
import android.util.Log;

import com.squareup.okhttp.RequestBody;
import com.vempower.eezyclinic.API.EezyClinicAPI;
import com.vempower.eezyclinic.APIResponce.SearchResultClinicListAPI;
import com.vempower.eezyclinic.APIResponce.SearchResultDoctorListAPI;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.core.SearchRequest;
import com.vempower.eezyclinic.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Satishk on 4/10/2017.
 */

public class SearchResultClinicListMapper extends  AbstractMapper  implements Callback<SearchResultClinicListAPI> {


    private SearchResultClinicListAPItListener listener;
    private final SearchRequest searchRequestParams;


    public SearchResultClinicListMapper(SearchRequest searchRequestParams) {
        this.searchRequestParams = searchRequestParams;
    }

    public void setOnSearchResultClinicListAPItListener(SearchResultClinicListAPItListener listener) {
        if (listener == null) {
            Log.i(MyApplication.getCurrentActivityContext().getClass().getName(), "Invalid SearchResultClinicListAPI instance.");
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
                listener.getSearchResultClinicListAPI(null, Utils.getStringFromResources(R.string.network_not_available_lbl));
            }
            return;
        }

        MyApplication.showTransparentDialog();
        EezyClinicAPI stashDealAPI = MyApplication.getInstance().getEezyClinicAPI();

       RequestBody requestBody = getMyRequestBody();
        if (requestBody == null) {
            MyApplication.hideTransaprentDialog();
            if (listener != null) {
                listener.getSearchResultClinicListAPI(null,null);
            }
            return;
        }

        Call<SearchResultClinicListAPI> apiResponseCall = stashDealAPI.getSearchResultClinicListAPI(requestBody);

        apiResponseCall.enqueue(this);
    }

    @Override
    public void onResponse(Response<SearchResultClinicListAPI> response, Retrofit retrofit) {
        MyApplication.hideTransaprentDialog();

        getMyResponse(response, new MyResponse<SearchResultClinicListAPI>() {
            @Override
            public void getMyResponse(SearchResultClinicListAPI responseBody, String errorMsg) {
                listener.getSearchResultClinicListAPI(responseBody,errorMsg);
            }
        });




    }

    @Override
    public void onFailure(Throwable error) {
        MyApplication.hideTransaprentDialog();

        onMyFailure(error, new MyResponse<SearchResultClinicListAPI>() {
            @Override
            public void getMyResponse(SearchResultClinicListAPI responseBody, String errorMsg) {
                listener.getSearchResultClinicListAPI(responseBody,errorMsg);
            }
        });


    }

    public RequestBody getMyRequestBody() {
//"searchtype(0) , country(0), specality
        //city(0), locality(0), latitude(0), longitude(0),
        // search_name(0), insurence_list(0), nationality_list(0),
        // gendersearch(0), launguage(0), searchtype(0),
        // onlinebooking(0), amount_range(0)
        // (e.g. 1-100)(0), perpage(0), page(0)"
        if(searchRequestParams==null || TextUtils.isEmpty(searchRequestParams.getSearchtype()))
        {
            return null;
        }

      /* String access_key= SharedPreferenceUtils.getStringValueFromSharedPrefarence(Constants.Pref.USER_VALIDATION_KEY,null);
        if (TextUtils.isEmpty(access_key) ) {
            return null;
        }*/
        //fname
               // email

        //"(0) , (0),
        //(0), (0), (0), (0),
        // (0), insurence_list(0), nationality_list(0),
        // gendersearch(0), launguage(0), ,
        // (0), (0)
        // (e.g. 1-100)(0), (0), (0)"
       JSONObject jsonObject = new JSONObject();
         try {
             if(!TextUtils.isEmpty(searchRequestParams.getSearchtype())) {
                 jsonObject.put("searchtype", searchRequestParams.getSearchtype());
             }

             if(!TextUtils.isEmpty(searchRequestParams.getCountry())) {
                 jsonObject.put("country", searchRequestParams.getCountry());
             }
             if(!TextUtils.isEmpty(searchRequestParams.getSpecality())) {
                 jsonObject.put("specality", searchRequestParams.getSpecality());
             }
             if(!TextUtils.isEmpty(searchRequestParams.getCity())) {
                 jsonObject.put("city", searchRequestParams.getCity());
             }
             if(!TextUtils.isEmpty(searchRequestParams.getLocality())) {
                 jsonObject.put("locality", searchRequestParams.getLocality());
             }
             if(!TextUtils.isEmpty(searchRequestParams.getLatitude())) {
                 jsonObject.put("latitude", searchRequestParams.getLatitude());
             }
             if(!TextUtils.isEmpty(searchRequestParams.getLongitude())) {
                 jsonObject.put("longitude", searchRequestParams.getLongitude());
             }

             if(!TextUtils.isEmpty(searchRequestParams.getSearchName())) {
                 jsonObject.put("search_name", searchRequestParams.getSearchName());
             }
             //if(!TextUtils.isEmpty(searchRequestParams.getOnlinebooking())) {
                 jsonObject.put("onlinebooking", searchRequestParams.getOnlinebooking());
            // }
             jsonObject.put("teleconsultation", searchRequestParams.getTeleconsultation());
             if(!TextUtils.isEmpty(searchRequestParams.getAmountRange())) {
                 jsonObject.put("amount_range", searchRequestParams.getAmountRange());
             }

             //if(!TextUtils.isEmpty(searchRequestParams.getPerpage())) {
                 jsonObject.put("perpage", searchRequestParams.getPerpage());
             //}

             if(!TextUtils.isEmpty(searchRequestParams.getPage())) {
                 jsonObject.put("page", searchRequestParams.getPage());
             }
             // (0), (0), (0),
             // (0), (0), ,
             if(searchRequestParams.getLaunguage()!=null && searchRequestParams.getLaunguage().size()>0)
             {
                 JSONArray jsonArray= new JSONArray();
                 for(String str:searchRequestParams.getLaunguage())
                 {
                     if(str!=null)
                     {
                         jsonArray.put(str);
                     }
                 }

                 jsonObject.put("launguage", jsonArray);
             }

             if(searchRequestParams.getGendersearch()!=null && searchRequestParams.getGendersearch().size()>0)
             {
                 JSONArray jsonArray= new JSONArray();
                 for(String str:searchRequestParams.getGendersearch())
                 {
                     if(str!=null)
                     {
                         jsonArray.put(str);
                     }
                 }

                 jsonObject.put("gendersearch", jsonArray);
             }

             if(searchRequestParams.getNationalityList()!=null && searchRequestParams.getNationalityList().size()>0)
             {
                 JSONArray jsonArray= new JSONArray();
                 for(String str:searchRequestParams.getNationalityList())
                 {
                     if(str!=null)
                     {
                         jsonArray.put(str);
                     }
                 }

                 jsonObject.put("nationality_list", jsonArray);
             }


             if(searchRequestParams.getInsurenceList()!=null && searchRequestParams.getInsurenceList().size()>0)
             {
                 JSONArray jsonArray= new JSONArray();
                 for(String str:searchRequestParams.getInsurenceList())
                 {
                     if(str!=null)
                     {
                         jsonArray.put(str);
                     }
                 }

                 jsonObject.put("insurence_list", jsonArray);
             }



        } catch (JSONException e) {
            e.printStackTrace();
        }

        return getRequestBody(jsonObject);
    }

    public interface SearchResultClinicListAPItListener {
        public void getSearchResultClinicListAPI(SearchResultClinicListAPI searchResultClinicListAPI, String errorMessage);
    }
}
