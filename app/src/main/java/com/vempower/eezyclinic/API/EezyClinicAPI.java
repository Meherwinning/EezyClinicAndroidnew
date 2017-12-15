package com.vempower.eezyclinic.API;

import com.squareup.okhttp.RequestBody;
import com.vempower.eezyclinic.APIResponce.AbstractResponse;
import com.vempower.eezyclinic.APIResponce.CityListAPI;
import com.vempower.eezyclinic.APIResponce.ClinicProfileAPI;
import com.vempower.eezyclinic.APIResponce.CountryListAPI;
import com.vempower.eezyclinic.APIResponce.DashboardAPI;
import com.vempower.eezyclinic.APIResponce.DoctorClinicNameListAPI;
import com.vempower.eezyclinic.APIResponce.DoctorProfileAPI;
import com.vempower.eezyclinic.APIResponce.ForgotPasswordAPI;
import com.vempower.eezyclinic.APIResponce.ForgotPasswordOTPAPI;
import com.vempower.eezyclinic.APIResponce.InsuranceListAPI;
import com.vempower.eezyclinic.APIResponce.LanguageListAPI;
import com.vempower.eezyclinic.APIResponce.LoginAPI;
import com.vempower.eezyclinic.APIResponce.NationalityListAPI;
import com.vempower.eezyclinic.APIResponce.SearchResultClinicListAPI;
import com.vempower.eezyclinic.APIResponce.SearchResultDoctorListAPI;
import com.vempower.eezyclinic.APIResponce.SignupAPI;
import com.vempower.eezyclinic.APIResponce.SpecalitiesAPI;
import com.vempower.eezyclinic.APIResponce.VerifyOTPAPI;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by Satish on 11/15/2017.
 */

public interface EezyClinicAPI {
    String data = "/api/";


    //http://202.63.103.194:8008/api/patient/signin
    @POST(data + "patient/signin")
    Call<LoginAPI> getLoginAPI(@Body RequestBody postBody);


    //http://202.63.103.194:8008/api/patient/socialsignin
    @POST(data + "patient/socialsignin")
    Call<LoginAPI> getSocialLoginAPI(@Body RequestBody postBody);

    //http://202.63.103.194:8008/api/patient/signup
    @POST(data + "patient/signup")
    Call<SignupAPI> getSignupAPI(@Body RequestBody postBody);

    //http://202.63.103.194:8008/api/patient/verifyotp
    @POST(data + "patient/verifyotp")
    Call<VerifyOTPAPI> verifyOTP_API(@Body RequestBody postBody);

    //http://202.63.103.194:8008/api/patient/forgotpassword
    @POST(data + "patient/forgotpassword")
    Call<ForgotPasswordAPI> forgorPasswordAPI(@Body RequestBody postBody);

    //http://202.63.103.194:8008/api/patient/forgotpasswordsendotp
    @POST(data + "patient/forgotpasswordsendotp")
    Call<ForgotPasswordOTPAPI> forgotPasswordOTPAPI(@Body RequestBody postBody);


    //http://202.63.103.194:8008/api/patient/resetpassword
    @POST(data + "patient/resetpassword")
    Call<AbstractResponse> resetPasswordAPI(@Body RequestBody postBody);


    //

    //http://202.63.103.194:8008/api/patient/resendotp
    @POST(data + "patient/resendotp")
    Call<SignupAPI> resendOTPAPI(@Body RequestBody postBody);


    //http://202.63.103.194:8008/api/patient/dashboard
    @POST(data + "patient/dashboard")
    Call<DashboardAPI> getDashboardAPI(@Body RequestBody postBody);


    //http://202.63.103.194:8008/api/search/specalities
    @GET(data + "search/specalities")
    Call<SpecalitiesAPI> getSpecalitiesAPI();

    //http://202.63.103.194:8008/api/search/cities?country=1
    @GET(data + "search/cities")
    Call<CityListAPI> getCityListAPI(@Query("country") String country);


    //http://202.63.103.194:8008/api/search/insurance
    @GET(data + "search/insurance")
    Call<InsuranceListAPI> getInsuranceListAPI();


    //http://202.63.103.194:8008/api/search/languages
    @GET(data + "search/languages")
    Call<LanguageListAPI> getLanguageListAPI();

    //http://202.63.103.194:8008/api/search/country
    @GET(data + "search/country")
    Call<CountryListAPI> getCountryListApi();


    //http://202.63.103.194:8008/api/search/nationality
    @GET(data + "search/nationality")
    Call<NationalityListAPI> getNationalityListApi();


    //http://202.63.103.194:8008/api/search/clinicdoctors
    @POST(data + "search/clinicdoctors")
    Call<DoctorClinicNameListAPI> getDoctorClinicNameListAPI(@Body RequestBody postBody);



    // http://202.63.103.194:8008/api/search/doctorsclinicsearch
     @POST(data + "search/doctorsclinicsearch")
     Call<SearchResultDoctorListAPI> getSearchResultDoctorListAPI(@Body RequestBody postBody);




    // http://202.63.103.194:8008/api/search/doctorsclinicsearch
    @POST(data + "search/doctorsclinicsearch")
    Call<SearchResultClinicListAPI> getSearchResultClinicListAPI(@Body RequestBody postBody);

    //http://202.63.103.194:8008/api/patient/doctorprofile
    @POST(data + "patient/doctorprofile")
    Call<DoctorProfileAPI> getDoctorProfileAPI(@Body RequestBody postBody);


    //http://202.63.103.194:8008/api/patient/clinicprofile
    @POST(data + "patient/clinicprofile")
    Call<ClinicProfileAPI> getClinicProfileAPI(@Body RequestBody postBody);


}