package com.vempower.eezyclinic.API;

import com.squareup.okhttp.RequestBody;
import com.vempower.eezyclinic.APIResponce.AbstractResponse;
import com.vempower.eezyclinic.APIResponce.AppointmentHistoryListAPI;
import com.vempower.eezyclinic.APIResponce.AppointmentTimeSlotsAPI;
import com.vempower.eezyclinic.APIResponce.CaseSheetsListAPI;
import com.vempower.eezyclinic.APIResponce.CasesheetDetailsAPI;
import com.vempower.eezyclinic.APIResponce.ChangeMobileNumberAPI;
import com.vempower.eezyclinic.APIResponce.CityListAPI;
import com.vempower.eezyclinic.APIResponce.ClinicProfileAPI;
import com.vempower.eezyclinic.APIResponce.CountryListAPI;
import com.vempower.eezyclinic.APIResponce.DashboardAPI;
import com.vempower.eezyclinic.APIResponce.DoctorClinicNameListAPI;
import com.vempower.eezyclinic.APIResponce.DoctorProfileAPI;
import com.vempower.eezyclinic.APIResponce.ForgotPasswordAPI;
import com.vempower.eezyclinic.APIResponce.ForgotPasswordOTPAPI;
import com.vempower.eezyclinic.APIResponce.GetFamilyMembersAPI;
import com.vempower.eezyclinic.APIResponce.GetMyNotesAPI;
import com.vempower.eezyclinic.APIResponce.GetPatientProfileAPI;
import com.vempower.eezyclinic.APIResponce.HealthChecksListAPI;
import com.vempower.eezyclinic.APIResponce.HelathReportsListAPI;
import com.vempower.eezyclinic.APIResponce.IdCardTypeAPI;
import com.vempower.eezyclinic.APIResponce.InsuranceListAPI;
import com.vempower.eezyclinic.APIResponce.LanguageListAPI;
import com.vempower.eezyclinic.APIResponce.LoginAPI;
import com.vempower.eezyclinic.APIResponce.MedicalHistoryListAPI;
import com.vempower.eezyclinic.APIResponce.NationalityListAPI;
import com.vempower.eezyclinic.APIResponce.NotificationsListAPI;
import com.vempower.eezyclinic.APIResponce.PendingFeedbackListAPI;
import com.vempower.eezyclinic.APIResponce.PrescriptionsListAPI;
import com.vempower.eezyclinic.APIResponce.ProfileSettingsAPI;
import com.vempower.eezyclinic.APIResponce.SearchFamilyMemberAPI;
import com.vempower.eezyclinic.APIResponce.SearchResultClinicListAPI;
import com.vempower.eezyclinic.APIResponce.SearchResultDoctorListAPI;
import com.vempower.eezyclinic.APIResponce.SignupAPI;
import com.vempower.eezyclinic.APIResponce.SpecalitiesAPI;
import com.vempower.eezyclinic.APIResponce.SubmitedFeedbackListAPI;
import com.vempower.eezyclinic.APIResponce.TPAListAPI;
import com.vempower.eezyclinic.APIResponce.UpcomingAppointmentListAPI;
import com.vempower.eezyclinic.APIResponce.UpcomingFollowupsAPI;
import com.vempower.eezyclinic.APIResponce.VerifyOTPAPI;

import java.util.Map;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.PartMap;
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


    //http://202.63.103.194:8003/api/appointment/appointmentslots
    @POST(data + "appointment/appointmentslots")
    Call<AppointmentTimeSlotsAPI> getAppointmentTimeSlots(@Body RequestBody postBody);

    //http://202.63.103.194:8003/api/appointment/booking
    @POST(data + "appointment/booking")
    Call<AbstractResponse> appointmentBooking(@Body RequestBody postBody);


    //http://202.63.103.194:8003/api/appointment/upcomingappointments
    @POST(data + "appointment/upcomingappointments")
    Call<UpcomingAppointmentListAPI> getAppointmentList(@Body RequestBody postBody);


    //http://202.63.103.194:8003/api/appointment/reschedule
    @POST(data + "appointment/reschedule")
    Call<AbstractResponse> rescheduleAppointment(@Body RequestBody postBody);

    //http://202.63.103.194:8003/api/appointment/cancel
    @POST(data + "appointment/cancel")
    Call<AbstractResponse> cancelAppointment(@Body RequestBody postBody);

    //http://202.63.103.194:8003/api/appointment/appointments
    @POST(data + "appointment/appointments")
    Call<AppointmentHistoryListAPI> getAppointmentHistoryList(@Body RequestBody postBody);


    //http://202.63.103.194:8003/api/search/idcardlist
    @GET(data + "search/idcardlist")
    Call<IdCardTypeAPI> getIdCardTypeAPI();

    //http://202.63.103.194:8003/api/search/tpalist
    @GET(data + "search/tpalist")
    Call<TPAListAPI> getTPAListAPI();


    //http://202.63.103.194:8003/api/patient/myprofile
    @POST(data + "patient/myprofile")
    Call<GetPatientProfileAPI> getPatientProfileAPI(@Body RequestBody postBody);


    //http://202.63.103.194:8003/api/patient/editprofilesave
    @POST(data + "patient/editprofilesave")
    Call<AbstractResponse> patientProfileSave(@Body RequestBody postBody);

    //http://202.63.103.194:8003/api/patient/privacysettings
    @POST(data + "patient/privacysettings")
    Call<ProfileSettingsAPI> getProfileSettings(@Body RequestBody postBody);


    //http://202.63.103.194:8003/api/patient/privacysettingssave
    @POST(data + "patient/privacysettingssave")
    Call<AbstractResponse> saveProfileSettings(@Body RequestBody postBody);


    //http://202.63.103.194:8003/api/patient/changepassword
    @POST(data + "patient/changepassword")
    Call<AbstractResponse> changePassword(@Body RequestBody postBody);


    //
//http://202.63.103.194:8003/api/patient/changemobile
    @POST(data + "patient/changemobile")
    Call<ChangeMobileNumberAPI> changeMobileNumber(@Body RequestBody postBody);


    //http://202.63.103.194:8003/api/patient/changemobileotpverify
    @POST(data + "patient/changemobileotpverify")
    Call<AbstractResponse> changeMobileVerifyOTP(@Body RequestBody postBody);


    //http://202.63.103.194:8003/api/patient/savepatientprofileimage
    @Multipart
    @POST(data + "patient/savepatientprofileimage")
    Call<AbstractResponse> uploadProfileImage(@Part("image_file\"; filename=\"patient_pic.png\" ") RequestBody profile_pic, @Part("access_key") RequestBody body);

    // http://202.63.103.194:8003/api/patient/getprescriptions
    @POST(data + "patient/getprescriptions")
    Call<PrescriptionsListAPI> getPrescriptionsList(@Body RequestBody postBody);


    //http://202.63.103.194:8003/api/patient/gethealthreports
    @POST(data + "patient/gethealthreports")
    Call<HelathReportsListAPI> getHelathReportsListAPI(@Body RequestBody postBody);


    //http://202.63.103.194:8003/api/patient/casesheets
    @POST(data + "patient/casesheets")
    Call<CaseSheetsListAPI> getCaseSheetsListAPI(@Body RequestBody postBody);


    //http://202.63.103.194:8003/api/patient/changeemail
    @POST(data + "patient/changeemail")
    Call<AbstractResponse> changeEmailOTP(@Body RequestBody postBody);

   /* @Multipart
    @POST("upload.php")
    Call<AbstractResponse> uploadImage(@Part("") MultipartBody.Part file);
*/

    //http://202.63.103.194:8003/api/patient/savepatientprofileimage
//    /
    //   (@Part("image_file\"; filename=\"patient_pic.png\" ") RequestBody profile_pic, @Part("access_key") RequestBody body);


    @Multipart
    @POST(data + "patient/savepatientprofileimage")
    Call<AbstractResponse> uploadProfileImage1
            (@PartMap Map<String, RequestBody> params);



    /* //https://github.com/square/retrofit/issues/1063#issuecomment-145920568
    @Multipart
    @POST ("/api/Events/editevent")
    Call<AbstractResponse> editEvent (@PartMap Map<String, RequestBody> params);

*/
    //
    //http://202.63.103.194:8003/api/patient/casesheetdetails
    @POST(data + "patient/casesheetdetails")
    Call<CasesheetDetailsAPI> getCasesheetDetails(@Body RequestBody postBody);

    //http://202.63.103.194:8003/api/patient/followupslist
    @POST(data + "patient/followupslist")
    Call<UpcomingFollowupsAPI> followupslist(@Body RequestBody postBody);


    //http://202.63.103.194:8003/api/patient/uploadhealthrecords
  /*  @POST(data + "patient/uploadhealthrecords")
    Call<AbstractResponse> uploadHealthRecords(@Body RequestBody postBody);

*/
    @Multipart
    @POST(data + "patient/uploadhealthrecords")
    Call<AbstractResponse> uploadHealthRecords(@PartMap Map<String, RequestBody> params);

  /*  @Multipart
    @POST(data + "patient/savepatientprofileimage")
    Call<AbstractResponse> uploadProfileImage2
            (@PartMap Map<String, RequestBody> params, @Part("access_key") RequestBody body);

*/


  //
  //http://202.63.103.194:8003/api/patient/mynotes
  @POST(data + "patient/mynotes")
  Call<GetMyNotesAPI> getMyNotes(@Body RequestBody postBody);

  //http://202.63.103.194:8003/api/patient/mynotessave
  @POST(data + "patient/mynotessave")
  Call<AbstractResponse> saveMyNotes(@Body RequestBody postBody);


  //http://202.63.103.194:8003/api/example/updatehealthrecordfile
  @Multipart
  @POST(data + "patient/updatehealthrecordfile")
  Call<AbstractResponse> updatehealthrecordfile(@PartMap Map<String, RequestBody> params);

  //http://202.63.103.194:8003/api/patient/removehealthreportfile
  @POST(data + "patient/removehealthreportfile")
  Call<AbstractResponse> removeHealthReportFile(@Body RequestBody postBody);

  //http://202.63.103.194:8003/api/patient/medicalhistory
  @POST(data + "patient/medicalhistory")
  Call<MedicalHistoryListAPI> getMedicalHistory(@Body RequestBody postBody);


  //http://202.63.103.194:8003/api/patient/savemedicalrecordself
  @POST(data + "patient/savemedicalrecordself")
  Call<AbstractResponse> saveMedicalRecordSelf(@Body RequestBody postBody);


  //http://202.63.103.194:8003/api/patient/pendingfeedback
  @POST(data + "patient/pendingfeedback")
  Call<PendingFeedbackListAPI> getPendingFeedback(@Body RequestBody postBody);



  //http://202.63.103.194:8003/api/patient/submittedfeedback
  @POST(data + "patient/submittedfeedback")
  Call<SubmitedFeedbackListAPI> getSubmittedfeedback(@Body RequestBody postBody);

  //http://202.63.103.194:8003/api/patient/submitfeedback
  @POST(data + "patient/submitfeedback")
  Call<AbstractResponse> submitfeedback(@Body RequestBody postBody);


  //http://202.63.103.194:8003/api/patient/getfamilymembers
  @POST(data + "patient/getfamilymembers")
  Call<GetFamilyMembersAPI> getFamilyMembers(@Body RequestBody postBody);

  //http://202.63.103.194:8003/api/patient/addfamilymember
  @POST(data + "patient/addfamilymember")
  Call<SearchFamilyMemberAPI> searchFamilyMember(@Body RequestBody postBody);

  //http://202.63.103.194:8003/api/patient/sendrequestfamilymember
  @POST(data + "patient/sendrequestfamilymember")
  Call<AbstractResponse> sendRequestFamilyMember(@Body RequestBody postBody);

  //http://202.63.103.194:8003/api/patient/familymemberdelete
  @POST(data + "patient/familymemberdelete")
  Call<AbstractResponse> familymemberdelete(@Body RequestBody postBody);


  //http://202.63.103.194:8003/api/patient/getnotifications
  @POST(data + "patient/getnotifications")
  Call<NotificationsListAPI> getNotifications(@Body RequestBody postBody);


  //http://202.63.103.194:8003/api/patient/notificationacceptrequest
  @POST(data + "patient/notificationacceptrequest")
  Call<AbstractResponse> notificationacceptrequest(@Body RequestBody postBody);


    //http://202.63.103.194:8003/api/patient/notificationrejectrequest
    @POST(data + "patient/notificationrejectrequest")
    Call<AbstractResponse> notificationrejectrequest(@Body RequestBody postBody);


//
//http://202.63.103.194:8003/api/patient/healthcheck
@POST(data + "patient/healthcheck")
Call<HealthChecksListAPI> getHealthCheckList(@Body RequestBody postBody);

    //http://202.63.103.194:8003/api/patient/addhealthcheck
    @POST(data + "patient/addhealthcheck")
    Call<AbstractResponse> addSugarHealthCheck(@Body RequestBody postBody);

}