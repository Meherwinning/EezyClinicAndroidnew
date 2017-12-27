package com.vempower.eezyclinic.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Messenger;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.vempower.eezyclinic.APICore.Appointment;
import com.vempower.eezyclinic.APICore.ReScheduleAppointmentRequestDetails;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.activities.AppointmentDetailsActivity;
import com.vempower.eezyclinic.activities.CancelAppointmentActivity;
import com.vempower.eezyclinic.activities.ReScheduleAppointmentActivity;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.callbacks.ListenerKey;
import com.vempower.eezyclinic.interfaces.AbstractIBinder;
import com.vempower.eezyclinic.interfaces.IntentObjectListener;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.eezyclinic.views.MyTextViewRB;
import com.vempower.eezyclinic.views.MyTextViewRR;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by satish on 6/12/17.
 */

public class AppointmentDetailsFragment extends AbstractFragment {

    private View fragmentView;
    private Appointment appointment;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.appointment_details_layout, container, false);



        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myInit();
    }



    private void myInit() {

        showGoogleMap();
        setHeaderDetails();
      setRescheduleListener();
      setCancelButtonListener();

    }

    private void setCancelButtonListener() {
        fragmentView.findViewById(R.id.cancel_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=null;

                if(MyApplication.getCurrentActivityContext() instanceof AppointmentDetailsActivity)
                {
                    AppointmentDetailsActivity activity=  (AppointmentDetailsActivity)(MyApplication.getCurrentActivityContext());
                     intent=  activity.getIntent();
                    intent.setClass(MyApplication.getCurrentActivityContext(),CancelAppointmentActivity.class);
                    intent.putExtra(Constants.Pref.APPOINTMENT_ID_KEY,appointment.getId());
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    activity.startActivityForResult(intent,AppointmentDetailsActivity.REQUESTCODE);

                }else {
                     intent=  new Intent(MyApplication.getCurrentActivityContext(),CancelAppointmentActivity.class);
                    intent.putExtra(Constants.Pref.APPOINTMENT_ID_KEY,appointment.getId());
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    MyApplication.getCurrentActivityContext().startActivity(intent);
                }




            }
        });
    }

    private void setRescheduleListener() {
        fragmentView.findViewById(R.id.re_schedule_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReScheduleAppointmentRequestDetails requestDetails=null;
                try {
                    requestDetails =
                            new ReScheduleAppointmentRequestDetails(Integer.parseInt(appointment.getDoctorId()), Integer.parseInt(appointment.getBranchId()), appointment.getPatientId(),
                                    appointment.getAppointmentDateTime(), appointment.getId());
                }catch (Exception e)
                {

                }
                if(requestDetails==null)
                {
                    return;
                }
                final ReScheduleAppointmentRequestDetails details=requestDetails;

                Intent intent=  new Intent(MyApplication.getCurrentActivityContext(),ReScheduleAppointmentActivity.class);
                           /*((Activity) MyApplication.getCurrentActivityContext()).getIntent();*/
                intent.putExtra(ListenerKey.ObjectKey.RESCHEDULE_APPOINTMENT_OBJECT_KEY,new Messenger(new AbstractIBinder(){
                    @Override
                    protected IntentObjectListener getMyObject() {
                        return new IntentObjectListener(){

                            @Override
                            public Object getObject() {
                                return details;
                            }
                        };
                    }
                }));
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);

                MyApplication.getCurrentActivityContext().startActivity(intent);
            }
        });
    }

    private void setHeaderDetails() {

        if(appointment==null || fragmentView==null)
        {
            return;
        }




        ImageView imageView= fragmentView.findViewById(R.id.profile_iv);

        MyApplication.getInstance().setBitmapToImageviewCircular(R.drawable.profile_icon,imageView,appointment.getDoctorLogo());


        ((MyTextViewRB)fragmentView.findViewById(R.id.doctor_name_tv)).setText(appointment.getDoctorName());
        ((MyTextViewRR)fragmentView.findViewById(R.id.doctor_designation_tv)).setText(appointment.getSpecalities());
        ((MyTextViewRR)fragmentView.findViewById(R.id.address_tv)).setText(appointment.getAddress());
       /* ((MyTextViewRB)fragmentView.findViewById(R.id.clinic_name_tv)).setText(searchResultDoctorListData.getClinicName());
        ((MyTextViewRR) fragmentView.findViewById(R.id.clinic_address_tv)).setText(searchResultDoctorListData.getAddress());
*/


        String  SERVER_DATE_FORMAT_NEW="yyyy-MM-dd h:mm a";//"2017-11-22 10:23 AM"

        String DISPLAY_DATE="dd-MM-yyyy";
        String DISPLAY_TIME="h:mm a , EEEE";


        SimpleDateFormat DISPLAY_DATE_FORMATTER = new SimpleDateFormat(DISPLAY_DATE);
        SimpleDateFormat DISPLAY_TIME_FORMATTER = new SimpleDateFormat(DISPLAY_TIME);


        MyTextViewRR time_date_display_tv = fragmentView.findViewById(R.id.date_and_time_tv);

        try {
            Date date = Utils.changeStringToDateFormat(appointment.getAppointmentDateTime(), SERVER_DATE_FORMAT_NEW);
            String dateStr= DISPLAY_DATE_FORMATTER.format(date);
            String timeStr= DISPLAY_TIME_FORMATTER.format(date);

            time_date_display_tv.setText(timeStr+",  "+dateStr);
        }catch (Exception e)
        {

        }

//at 12:15 PM on Friday,  Mar 27th, 2017

        //"h:mm a"
    }

    private void showGoogleMap()
    {
        AppointmentMapFragment fragment = new AppointmentMapFragment();
        // doctorsMapFragment.setDoctorsList( listData);
        fragment.setAppointment(appointment);

        setFragment(fragment);
    }


    protected void setFragment(AbstractFragment fragment) {
        if (fragment == null) {
            return;
        }


        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //sharedPreferences.edit().putInt("FRAGMENT", 0).apply();
        //abstractFragment = getFragment();//new FragmentAllSongs();
       /* if(abstractFragment instanceof ContactDetailsFragment)
        {
             contactDetailsFragment= (ContactDetailsFragment) abstractFragment;
        }*/
        Log.d("fragment",fragment.getClass().getSimpleName());
        //if(fragmentManager.findFragmentByTag(fragment.getClass().getSimpleName())==null) {
        fragmentTransaction.replace(R.id.my_fragment_layout, fragment, fragment.getClass().getSimpleName()/*"FRAGMENT"*/);
        //}else {
        //fragmentTransaction.show(fragmentManager.findFragmentByTag(fragment.getClass().getSimpleName()));
        // fragmentTransaction.attach(fragmentManager.findFragmentByTag(fragment.getClass().getSimpleName()));
        //}
        //Log.d("hidden","Show");
        //} /*else {
        //fragmentTransaction.hide(fragment);
        //Log.d("Shown","Hide");
        // }
        fragmentTransaction.commitAllowingStateLoss();
        //fragmentTransaction.commit();

                /*if(!TextUtils.isEmpty(getScreenTitle())) {
                    toolbar.setTitle(getScreenTitle());
                }*/

    }








    @Override
    public void onResume() {
        super.onResume();
        hideKeyBord(fragmentView);
    }

    @Override
    View getFragemtView() {
        return fragmentView;
    }


    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }
}
