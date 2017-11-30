package com.vempower.eezyclinic.API;

import com.squareup.okhttp.RequestBody;
import com.vempower.eezyclinic.APIResponce.AbstractResponce;
import com.vempower.eezyclinic.APIResponce.ForgotPasswordAPI;
import com.vempower.eezyclinic.APIResponce.ForgotPasswordOTPAPI;
import com.vempower.eezyclinic.APIResponce.LoginAPI;
import com.vempower.eezyclinic.APIResponce.RegisterAPI;
import com.vempower.eezyclinic.APIResponce.SignupAPI;
import com.vempower.eezyclinic.APIResponce.VerifyOTPAPI;

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


    //http://202.63.103.194:8008/api/patient/socialsignin
    @POST(data+"patient/socialsignin")
    Call<LoginAPI> getSocialLoginAPI(@Body RequestBody postBody);

    //http://202.63.103.194:8008/api/patient/signup
    @POST(data+"patient/signup")
    Call<SignupAPI> getSignupAPI(@Body RequestBody postBody);

    //http://202.63.103.194:8008/api/patient/verifyotp
    @POST(data+"patient/verifyotp")
    Call<VerifyOTPAPI> verifyOTP_API(@Body RequestBody postBody);

    //http://202.63.103.194:8008/api/patient/forgotpassword
    @POST(data+"patient/forgotpassword")
    Call<ForgotPasswordAPI> forgorPasswordAPI(@Body RequestBody postBody);

    //http://202.63.103.194:8008/api/patient/forgotpasswordsendotp
    @POST(data+"patient/forgotpasswordsendotp")
    Call<ForgotPasswordOTPAPI> forgotPasswordOTPAPI(@Body RequestBody postBody);


    //http://202.63.103.194:8008/api/patient/resetpassword
    @POST(data+"patient/resetpassword")
    Call<AbstractResponce> resetPasswordAPI(@Body RequestBody postBody);


}
