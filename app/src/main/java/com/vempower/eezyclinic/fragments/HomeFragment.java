package com.vempower.eezyclinic.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.vempower.eezyclinic.APICore.Appointment;
import com.vempower.eezyclinic.APICore.DashboardData;
import com.vempower.eezyclinic.APICore.Followup;
import com.vempower.eezyclinic.APICore.PatientData;
import com.vempower.eezyclinic.APIResponce.DashboardAPI;
import com.vempower.eezyclinic.APIResponce.UpcomingAppointmentListAPI;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.activities.SigninActivity;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.interfaces.ApiErrorDialogInterface;
import com.vempower.eezyclinic.interfaces.HomeListener;
import com.vempower.eezyclinic.mappers.DashboardMapper;
import com.vempower.eezyclinic.mappers.UpcomingAppointmentListMapper;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.SharedPreferenceUtils;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.eezyclinic.views.MyButtonRectangleRM;
import com.vempower.eezyclinic.views.MyTextViewRM;
import com.vempower.eezyclinic.views.MyTextViewRR;
import com.vempower.stashdealcustomer.activities.AbstractActivity;

/**
 * Created by satish on 4/12/17.
 */

public class HomeFragment extends AbstractFragment {

    private View fragmentView;
    //private TextView welcome_tv;
    private MyTextViewRM patient_name_tv,patient_id_tv;
    private MyTextViewRR health_tips_tv,health_goal_tv,upcoming_appointment_tv,upcoming_followups_tv;
    private ImageView profile_iv;
    private MyButtonRectangleRM search_doctors_bt;
    private MyTextViewRR upcoming_appointment_name_tv;

    private CardView upcoming_appointment_cardview,upcoming_followups_cardview;
    //private HomeListener homeListener;

    // private MyButtonRectangleRM
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.home_layout, container, false);

        //showProgressView();

        init();
        return fragmentView;
    }

    @Override
    View getFragemtView() {
        return fragmentView;
    }

    private void init() {
        patient_name_tv = fragmentView.findViewById(R.id.patient_name_tv);
        patient_id_tv = fragmentView.findViewById(R.id.patient_id_tv);
        profile_iv = fragmentView.findViewById(R.id.profile_iv);
        health_tips_tv = fragmentView.findViewById(R.id.health_tips_tv);
        health_goal_tv = fragmentView.findViewById(R.id. health_goal_tv);
        upcoming_appointment_tv  = fragmentView.findViewById(R.id.upcoming_appointment_tv);
        upcoming_followups_tv = fragmentView.findViewById(R.id.upcoming_followups_tv);
        upcoming_followups_cardview  = fragmentView.findViewById(R.id.upcoming_followups_cardview);

        search_doctors_bt = fragmentView.findViewById(R.id.search_doctors_bt);

        upcoming_appointment_name_tv = fragmentView.findViewById(R.id.upcoming_appointment_name_tv);
        upcoming_appointment_cardview = fragmentView.findViewById(R.id.upcoming_appointment_cardview);

       // String str = "Welcome to Eezyclinic\n";
        refreshPatientData(null);
        callDashboardMapper();
       // welcome_tv.setText(str);

        search_doctors_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Utils.showToastMsg("coming soon");
                if(myListener!=null && myListener instanceof HomeListener)
                {
                    HomeListener homeListener= (HomeListener) myListener;
                    homeListener.onSearchDocatorsClick();
                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        callUpcomingAppointmentsMapper();
    }

    private void callDashboardMapper()
    {
        String access_key= SharedPreferenceUtils.getStringValueFromSharedPrefarence(Constants.Pref.USER_VALIDATION_KEY,null);
        if (TextUtils.isEmpty(access_key) ) {
            //TODO something
            return;
        }
        DashboardMapper mapper= new DashboardMapper();
        mapper.setOnDashboardListener(new DashboardMapper.DashboardListener() {
            @Override
            public void getDashboardAPI(DashboardAPI dashboardAPI, String errorMessage) {

                if(!isValidResponse(dashboardAPI,errorMessage))
                {
                    showMyDialog("Alert", "Something wrong to fetch data from server.Please try again", new ApiErrorDialogInterface() {
                        @Override
                        public void onCloseClick() {
                            logout();
                        }

                        @Override
                        public void retryClick() {
                            callDashboardMapper();
                        }
                    });

                    return;
                }
                if(dashboardAPI.getData()!=null)
                {
                    if(dashboardAPI.getData().getPatientProfile()!=null)
                    {

                        MyApplication.getInstance().setLoggedUserDetailsToSharedPref(dashboardAPI.getData().getPatientProfile());
                        refreshPatientData(dashboardAPI.getData().getPatientProfile());

                        refreshDashBoard(dashboardAPI.getData());

                    }
                }




            }
        });

    }

    private void callUpcomingAppointmentsMapper() {
        UpcomingAppointmentListMapper appointmentListMapper= new UpcomingAppointmentListMapper();
        appointmentListMapper.setOnUpcomingAppointmentListListener(new UpcomingAppointmentListMapper.UpcomingAppointmentListListener() {
            @Override
            public void getAppointments(final UpcomingAppointmentListAPI upcomingAppointmentListAPI, String errorMessage) {

                if(!isValidResponse(upcomingAppointmentListAPI,errorMessage))
                {
                    upcoming_appointment_name_tv.setText(null);
                    upcoming_appointment_name_tv.setVisibility(View.GONE);
                    upcoming_appointment_tv.setText(Utils.getStringFromResources(R.string.upcoming_empty_appointment_list_msg));
                    upcoming_appointment_cardview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //Utils.showToastMsg("No upcoming appointment(s).");
                            Utils.showToastMsg(R.string.upcoming_empty_appointment_list_msg);
                        }
                    });
                    return;
                }


                  if(upcomingAppointmentListAPI.getData()!=null && upcomingAppointmentListAPI.getData().size()>0)
        {
            Appointment appointment = upcomingAppointmentListAPI.getData().get(0);
            if(appointment!=null)
            {
                //WithDr. M J Korian at08:45 AMonFriday, 08-12-2017
                //atClinic 1, Al Karama, Dubai
                upcoming_appointment_name_tv.setText(appointment.getDoctorName());
                upcoming_appointment_name_tv.setVisibility(View.VISIBLE);
                upcoming_appointment_tv.setText(appointment.getSpecalities()+
                        "\nat "+appointment.getAppointmentDateTime()+"\n"+appointment.getLocality()+","+appointment.getCity());
                upcoming_appointment_cardview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(myListener!=null && myListener instanceof HomeListener)
                        {
                            HomeListener homeListener= (HomeListener) myListener;
                            homeListener.onUpcomingAppointmentClick(upcomingAppointmentListAPI.getData());

                            // homeListener.onUpcomingAppointmentClick(dashboardData.getComingappointments());
                        }
                    }
                });
                }

                }else
                  {
                      upcoming_appointment_name_tv.setText(null);
                      upcoming_appointment_name_tv.setVisibility(View.GONE);
                      upcoming_appointment_tv.setText(Utils.getStringFromResources(R.string.upcoming_empty_appointment_list_msg));
                      upcoming_appointment_cardview.setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View view) {
                              //Utils.showToastMsg("No upcoming appointment(s).");
                              Utils.showToastMsg(R.string.upcoming_empty_appointment_list_msg);
                          }
                      });
                  }
            }
        });

    }

    private void logout() {
        MyApplication.getInstance().setLoggedUserDetailsToSharedPref(null);
        SharedPreferenceUtils.setStringValueToSharedPrefarence(Constants.Pref.USER_VALIDATION_KEY,"");
        Intent intent= new Intent(MyApplication.getCurrentActivityContext(),SigninActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        ((AbstractActivity)MyApplication.getCurrentActivityContext()).finish();
    }


    private void refreshDashBoard(final DashboardData dashboardData)
    {
        if(dashboardData==null)
        {
            return;
        }
        health_tips_tv.setText(dashboardData.getHealthtips()==null?"-":dashboardData.getHealthtips());
        health_goal_tv.setText(dashboardData.getHealthgoals()==null?Utils.getStringFromResources(R.string.health_goal_empty_msg):dashboardData.getHealthgoals());



        upcoming_followups_tv.setText(Utils.getStringFromResources(R.string.upcoming_empty_followups_list_msg));
        upcoming_followups_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.showToastMsg(R.string.upcoming_empty_followups_list_msg);
            }
        });
        if(dashboardData.getFollowups()!=null && dashboardData.getFollowups().size()>0)
        {
            //With Dr. Farhat Haya on Thursday 25-01-2018
            Followup followup = dashboardData.getFollowups().get(0);
            if(followup!=null)
            {
                upcoming_followups_tv.setText("With "+ followup.getDoctorName()+" on "+followup.getUpcomingVist());
            }
            upcoming_followups_cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(myListener!=null && myListener instanceof HomeListener)
                    {
                        HomeListener homeListener= (HomeListener) myListener;
                        homeListener.onUpcomingFollowupsClick(dashboardData.getFollowups());
                    }
                }
            });

        }

       // upcoming_appointment_tv  = fragmentView.findViewById(R.id.upcoming_appointment_tv);
       // upcoming_followups_tv = fragmentView.findViewById(R.id.upcoming_followups_tv);
    }

    private void refreshPatientData(PatientData patientData) {
        if(patientData==null)
        {
            patientData = MyApplication.getInstance().getLoggedUserDetailsFromSharedPref();

        }
         if(patientData!=null)
        {

            patient_name_tv.setText(patientData.getPatientName());
            patient_id_tv.setText(patientData.getPatientUniqueId());
            if (profile_iv != null) {
                //int defaultImageId, ImageView imageView,String imageUrl
                MyApplication.getInstance().setBitmapToImageviewCircular(R.drawable.profile_icon, profile_iv, patientData.getPatientLogo());
            }

        }


    }

    public void refresh() {
        refreshPatientData(null);
        callDashboardMapper();
        callUpcomingAppointmentsMapper();
    }



   /* public interface HomeListener
    {
        void onUpcomingAppointmentClick(List<Appointment> list);
        void onUpcomingFollowupsClick(List<Followup> followups);
        void onSearchDocatorsClick();
    }*/

}
