package com.vempower.eezyclinic.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Messenger;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vempower.eezyclinic.APICore.Appointment;
import com.vempower.eezyclinic.APICore.ReScheduleAppointmentRequestDetails;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.activities.AppointmentDetailsActivity;
import com.vempower.eezyclinic.activities.AppointmentHistoryActivity;
import com.vempower.eezyclinic.activities.CancelAppointmentActivity;
import com.vempower.eezyclinic.activities.DoctorProfileActivity;
import com.vempower.eezyclinic.activities.ReScheduleAppointmentActivity;
import com.vempower.eezyclinic.activities.UpComingAppointmentListActivity;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.callbacks.ListenerKey;
import com.vempower.eezyclinic.interfaces.AbstractIBinder;
import com.vempower.eezyclinic.interfaces.IntentObjectListener;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.eezyclinic.views.MyTextViewRM;
import com.vempower.eezyclinic.views.MyTextViewRR;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Satishk on 9/7/2017.
 */

public class AppointmentHistoryListAdapter extends RecyclerView.Adapter<AppointmentHistoryListAdapter.OrdersListHolder> {

    private List<Appointment> appointments;

    private LayoutInflater inflater;


    public AppointmentHistoryListAdapter(List<Appointment> appointments) {
        this.appointments = appointments;
        inflater = (LayoutInflater) MyApplication.getCurrentActivityContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public OrdersListHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        final View convertView = inflater
                .inflate(R.layout.appointment_history_single_item, parent, false);
        return new OrdersListHolder(convertView);
    }


    @Override
    public void onBindViewHolder(OrdersListHolder holder, int position) {


        holder.bindData(appointments.get(position), position);


    }

    @Override
    public int getItemCount() {
        return appointments == null ? 0 : appointments.size();
    }


    public void setUpdatedList(List<Appointment> newList) {
        if (newList == null || newList.size() == 0) {
            if (this.appointments == null) {
                return;
            }
            this.appointments.clear();
            notifyDataSetChanged();
            return;
        }
        this.appointments = newList;
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

        private MyTextViewRM doctor_name_tv;
        private MyTextViewRR clinic_address_tv, date_and_time_tv;

        public OrdersListHolder(View itemView) {
            super(itemView);
            doctor_name_tv  = itemView.findViewById(R.id.doctor_name_tv);
            clinic_address_tv  = itemView.findViewById(R.id.clinic_address_tv);
            date_and_time_tv  = itemView.findViewById(R.id.date_and_time_tv);

        }

        public void bindData(final Appointment data, final int position) {
            if (data == null) {
                return;
            }
            doctor_name_tv.setText(data.getDoctorName());
            clinic_address_tv.setText(data.getAddress());


           // MyTextViewRR appointment_conform_tv = fragmentView.findViewById(R.id.appointment_conform_tv);
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

                    Intent intent=  new Intent(MyApplication.getCurrentActivityContext(),AppointmentHistoryActivity.class);
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
