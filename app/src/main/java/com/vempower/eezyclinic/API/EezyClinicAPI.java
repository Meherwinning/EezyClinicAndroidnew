package com.vempower.eezyclinic.API;

import com.squareup.okhttp.RequestBody;
import com.vempower.eezyclinic.APIResponce.LoginAPI;
import com.vempower.eezyclinic.APIResponce.RegisterAPI;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by Satish on 11/15/2017.
 */

public interface EezyClinicAPI {
    String data="/api/";


    //http://202.63.103.194:8008/api/patient/signin
    @POST(data+"patient/signin")
    Call<LoginAPI> getLoginAPI(@Body RequestBody postBody);

}
