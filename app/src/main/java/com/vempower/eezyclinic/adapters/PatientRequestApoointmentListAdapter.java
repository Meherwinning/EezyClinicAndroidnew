package com.vempower.eezyclinic.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Messenger;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vempower.eezyclinic.APICore.PatientRequestAppointment;
import com.vempower.eezyclinic.APICore.TeleConsultation;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.activities.PatientRequestAppointmentListActivity;
import com.vempower.eezyclinic.activities.TeleConsultationListActivity;
import com.vempower.eezyclinic.activities.TeleConsultationPaymentReviewActivity;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.callbacks.ListenerKey;
import com.vempower.eezyclinic.interfaces.AbstractIBinder;
import com.vempower.eezyclinic.interfaces.IntentObjectListener;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PatientRequestApoointmentListAdapter extends RecyclerView.Adapter<PatientRequestApoointmentListAdapter.OrdersListHolder> {

    private List<PatientRequestAppointment> patientRequestAppointments;

    private LayoutInflater inflater;

    public PatientRequestApoointmentListAdapter(List<PatientRequestAppointment> patientRequestAppointments) {
        this.patientRequestAppointments = patientRequestAppointments;
        inflater = (LayoutInflater) MyApplication.getCurrentActivityContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public PatientRequestApoointmentListAdapter.OrdersListHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        final View convertView = inflater
                .inflate(R.layout.patient_request_appointment_single_item, parent, false);
        return new PatientRequestApoointmentListAdapter.OrdersListHolder(convertView);
    }


    @Override
    public void onBindViewHolder(PatientRequestApoointmentListAdapter.OrdersListHolder holder, int position) {


        holder.bindData(patientRequestAppointments.get(position), position);


    }

    @Override
    public int getItemCount() {
        return patientRequestAppointments == null ? 0 : patientRequestAppointments.size();
    }


    public void setUpdatedList(List<PatientRequestAppointment> newList) {
        if (newList == null || newList.size() == 0) {
            if (this.patientRequestAppointments == null) {
                return;
            }
            this.patientRequestAppointments.clear();
            notifyDataSetChanged();
            return;
        }
        this.patientRequestAppointments = newList;
        notifyDataSetChanged();
    }


    public class OrdersListHolder extends RecyclerView.ViewHolder {
        //19 August 2017, Saturday 01.08 PM
        // String DISPLAY_DATE="MMM d, yyyy";
        //  String DISPLAY_TIME="h:mm a 'on' EEEE";
        String DISPLAY_DATE_TIME="d MMM yyyy, EEEE h:mm a";
        String  SERVER_DATE_FORMAT_NEW="yyyy-MM-dd HH:mm:ss";//"2017-12-26 16:55:00"
        SimpleDateFormat DISPLAY_DATE_TIME_FORMATTER = new SimpleDateFormat(DISPLAY_DATE_TIME);
        // SimpleDateFormat DISPLAY_TIME_FORMATTER = new SimpleDateFormat(DISPLAY_TIME);

        private TextView doctor_name_tv,clinic_name_tv;
        private TextView clinic_address_tv, date_and_time_tv;

        public OrdersListHolder(View itemView) {
            super(itemView);
            doctor_name_tv  = itemView.findViewById(R.id.doctor_name_tv);
            clinic_name_tv  = itemView.findViewById(R.id.clinic_name_tv);
            clinic_address_tv  = itemView.findViewById(R.id.clinic_address_tv);
            date_and_time_tv  = itemView.findViewById(R.id.date_and_time_tv);

        }

        @SuppressLint("SetTextI18n")
        public void bindData(final PatientRequestAppointment data, final int position) {
            if (data == null) {
                return;
            }
            doctor_name_tv.setText(data.getDoctorName());
            clinic_address_tv.setText(data.getAddress() +","+data.getLocality()+","+data.getCity());
            clinic_name_tv.setText(data.getClinicName());


            //  TextView appointment_conform_tv = fragmentView.findViewById(R.id.appointment_conform_tv);
//19 August 2017, Saturday 01.08 PM
            try {
                Date date = Utils.changeStringToDateFormat(data.getAppointmentDateTime(), SERVER_DATE_FORMAT_NEW);
                String dateStr= DISPLAY_DATE_TIME_FORMATTER.format(date);
                // String timeStr= DISPLAY_TIME_FORMATTER.format(date);
                //With Dr. First name Middle name Last Name at 07:00 PM on Tuesday, 26-12-2017
                date_and_time_tv.setText(dateStr);
            }catch (Exception e)
            {
                date_and_time_tv.setText("-");

            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Utils.showToastMsg(data.getId()+"");
                    Intent intent = ((Activity)MyApplication.getCurrentActivityContext()).getIntent();
                    intent.setClass(MyApplication.getCurrentActivityContext(), TeleConsultationListActivity.class);
                    /*((Activity) MyApplication.getCurrentActivityContext()).getIntent();*/
                    intent.putExtra(ListenerKey.ObjectKey.APPOINTMENT_OBJECT_KEY,new Messenger(new AbstractIBinder(){
                        @Override
                        protected IntentObjectListener getMyObject() {
                            return new IntentObjectListener(){

                                @Override
                                public Object getObject() {
                                    return data;
                                }
                            };
                        }
                    }));
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    MyApplication.getCurrentActivityContext().startActivity(intent);




                }
            });

        }
    }



}
