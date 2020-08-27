package com.vempower.eezyclinic.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Messenger;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.vempower.eezyclinic.APICore.Appointment;
import com.vempower.eezyclinic.APICore.Followup;
import com.vempower.eezyclinic.APICore.ReScheduleAppointmentRequestDetails;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.activities.AppointmentDetailsActivity;
import com.vempower.eezyclinic.activities.CancelAppointmentActivity;
import com.vempower.eezyclinic.activities.DoctorsClinicsListActivity;
import com.vempower.eezyclinic.activities.FollowupAppointmentActivity;
import com.vempower.eezyclinic.activities.ReScheduleAppointmentActivity;
import com.vempower.eezyclinic.activities.ScheduleAppointmentActivity;
import com.vempower.eezyclinic.activities.UpComingAppointmentListActivity;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.callbacks.ListenerKey;
import com.vempower.eezyclinic.interfaces.AbstractIBinder;
import com.vempower.eezyclinic.interfaces.IntentObjectListener;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.Utils;
 ;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Satishk on 9/7/2017.
 */

public class UpcomingFollowupsListAdapter extends RecyclerView.Adapter<UpcomingFollowupsListAdapter.OrdersListHolder> {

    private List<Followup> followupList;

    private LayoutInflater inflater;


    public UpcomingFollowupsListAdapter(List<Followup> followupList) {
        this.followupList = followupList;
        inflater = (LayoutInflater) MyApplication.getCurrentActivityContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public OrdersListHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        final View convertView = inflater
                .inflate(R.layout.followups_single_item, parent, false);
        return new OrdersListHolder(convertView);
    }


    @Override
    public void onBindViewHolder(OrdersListHolder holder, int position) {


        holder.bindData(followupList.get(position), position);


    }

    @Override
    public int getItemCount() {
        return followupList == null ? 0 : followupList.size();
    }


    public void setUpdatedList(List<Followup> newList) {
        if (newList == null || newList.size() == 0) {
            if (this.followupList == null) {
                return;
            }
            this.followupList.clear();
            notifyDataSetChanged();
            return;
        }
        this.followupList = newList;
        notifyDataSetChanged();
    }


    public class OrdersListHolder extends RecyclerView.ViewHolder {
        String DISPLAY_DATE = "MMM d, yyyy";
        String DISPLAY_TIME = "h:mm a 'on' EEEE";
        String SERVER_DATE_FORMAT_NEW = "yyyy-MM-dd";// /* HH:mm:ss*/"2017-12-26 16:55:00"
        SimpleDateFormat DISPLAY_DATE_FORMATTER = new SimpleDateFormat(DISPLAY_DATE);
        // SimpleDateFormat DISPLAY_TIME_FORMATTER = new SimpleDateFormat(DISPLAY_TIME);

        private TextView appointment_details_tv, cancel_app_tv1, re_schedule_tv;

        /*  private ImageView profile_iv;

          private TextView title_tv;
          private  TextView designation_tv, time_tv, address_tv,
                  reviews_count_tv, recommendations_count_tv, book_appointment_tv;
  */
        public OrdersListHolder(View itemView) {
            super(itemView);
            appointment_details_tv = itemView.findViewById(R.id.appointment_details_tv);
            //cancel_app_tv  = itemView.findViewById(R.id.cancel_app_tv);
            re_schedule_tv = itemView.findViewById(R.id.re_schedule_tv);

        }

        public void bindData(final Followup data, final int position) {
            if (data == null) {
                return;
            }

            //  TextView appointment_conform_tv = fragmentView.findViewById(R.id.appointment_conform_tv);

            try {
                Date date = Utils.changeStringToDateFormat(data.getUpcomingVist(), SERVER_DATE_FORMAT_NEW);
                String dateStr = DISPLAY_DATE_FORMATTER.format(date);
                //  String timeStr= DISPLAY_TIME_FORMATTER.format(date);
                //With Dr. First name Middle name Last Name at 07:00 PM on Tuesday, 26-12-2017
                appointment_details_tv.setText("With " + data.getDoctorName() + "\nby " +/* timeStr+", "+ */dateStr/*+"\nat "+data.get*/);
            } catch (Exception e) {
                appointment_details_tv.setText("-");

            }


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (true) {
                        callSheduleCalender(data);
                        return;
                    }
                    Intent intent = ((Activity)MyApplication.getCurrentActivityContext()).getIntent();
                   // intent.setClass(MyApplication.getCurrentActivityContext(), ScheduleAppointmentActivity.class);

                   // Intent intent = null;//new Intent(MyApplication.getCurrentActivityContext(),AppointmentDetailsActivity.class);
                    /*((Activity) MyApplication.getCurrentActivityContext()).getIntent();*/

                    if (MyApplication.getCurrentActivityContext() instanceof UpComingAppointmentListActivity) {
                       // intent = ((UpComingAppointmentListActivity) (MyApplication.getCurrentActivityContext())).getIntent();
                        intent.setClass(MyApplication.getCurrentActivityContext(), AppointmentDetailsActivity.class);

                    } else {
                        intent.setClass(MyApplication.getCurrentActivityContext(), AppointmentDetailsActivity.class);

                    }
                    intent.putExtra(ListenerKey.ObjectKey.APPOINTMENT_OBJECT_KEY, new Messenger(new AbstractIBinder() {
                        @Override
                        protected IntentObjectListener getMyObject() {
                            return new IntentObjectListener() {

                                @Override
                                public Object getObject() {
                                    return data;
                                }
                            };
                        }
                    }));
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    if (MyApplication.getCurrentActivityContext() instanceof UpComingAppointmentListActivity) {
                        UpComingAppointmentListActivity activity = (UpComingAppointmentListActivity) MyApplication.getCurrentActivityContext();
                        activity.startActivityForResult(intent, UpComingAppointmentListActivity.REQUESTCODE);
                    } else {
                        MyApplication.getCurrentActivityContext().startActivity(intent);
                    }

                }
            });

            re_schedule_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Utils.showToastMsg("Re schedule :"+data.getId());
//int doctor_id, int branch_id,
                    //String patientId,String appointmentDateTime,
                    // String oldAppointmentId
                    callSheduleCalender(data);


                }
            });

        }

        private void callSheduleCalender(final Followup data) {
            /*ReScheduleAppointmentRequestDetails requestDetails = null;
            try {
                requestDetails =
                        new ReScheduleAppointmentRequestDetails(Integer.parseInt(data.getDoctorId()), Integer.parseInt(data.getBranchId()), data.getPatientId(),
                                data.getUpcomingVist(), data.getId());
            } catch (Exception e) {

            }
            if (requestDetails == null) {
                return;
            }*/
            //final ReScheduleAppointmentRequestDetails details = requestDetails;

            //  Utils.showToastMsg(details.toString());

            Intent intent = ((Activity)MyApplication.getCurrentActivityContext()).getIntent();
            intent.setClass(MyApplication.getCurrentActivityContext(),FollowupAppointmentActivity.class);
            // ((Activity) MyApplication.getCurrentActivityContext()).getIntent();
            intent.putExtra(ListenerKey.ObjectKey.FOLLOWUP_APPOINTMENT_OBJECT_KEY,new Messenger(new AbstractIBinder(){
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
            intent.putExtra(Constants.Pref.IS_FROM_FOLLOWUPS,true);

            MyApplication.getCurrentActivityContext().startActivity(intent);
        }
    }
}
