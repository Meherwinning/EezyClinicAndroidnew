package com.vempower.eezyclinic.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Messenger;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vempower.eezyclinic.APICore.Appointment;
import com.vempower.eezyclinic.APICore.ReScheduleAppointmentRequestDetails;
import com.vempower.eezyclinic.APICore.SearchResultDoctorListData;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.activities.AppointmentDetailsActivity;
import com.vempower.eezyclinic.activities.CancelAppointmentActivity;
import com.vempower.eezyclinic.activities.DoctorProfileActivity;
import com.vempower.eezyclinic.activities.DoctorsClinicsListActivity;
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

public class UpcomingAppointmentListAdapter extends RecyclerView.Adapter<UpcomingAppointmentListAdapter.OrdersListHolder> {

    private List<Appointment> appointments;

    private LayoutInflater inflater;


    public UpcomingAppointmentListAdapter(List<Appointment> appointments) {
        this.appointments = appointments;
        inflater = (LayoutInflater) MyApplication.getCurrentActivityContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public OrdersListHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        final View convertView = inflater
                .inflate(R.layout.appointment_single_item, parent, false);
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
        String DISPLAY_DATE="MMM d, yyyy";
        String DISPLAY_TIME="h:mm a 'on' EEEE";
        String  SERVER_DATE_FORMAT_NEW="yyyy-MM-dd HH:mm:ss";//"2017-12-26 16:55:00"
        SimpleDateFormat DISPLAY_DATE_FORMATTER = new SimpleDateFormat(DISPLAY_DATE);
        SimpleDateFormat DISPLAY_TIME_FORMATTER = new SimpleDateFormat(DISPLAY_TIME);

        private TextView title_tv,date_tv,address_tv,clinic_name_tv, cancel_app_tv,re_schedule_tv;
      /*  private ImageView profile_iv;

        private TextView title_tv;
        private  TextView designation_tv, time_tv, address_tv,
                reviews_count_tv, recommendations_count_tv, book_appointment_tv;
*/
        public OrdersListHolder(View itemView) {
            super(itemView);
            title_tv  = itemView.findViewById(R.id.title_tv);
            clinic_name_tv  = itemView.findViewById(R.id.clinic_name_tv);
            date_tv  = itemView.findViewById(R.id.date_tv);
            address_tv  = itemView.findViewById(R.id.address_tv);
            cancel_app_tv  = itemView.findViewById(R.id.cancel_app_tv);
            re_schedule_tv  = itemView.findViewById(R.id.re_schedule_tv);

        }

        public void bindData(final Appointment data, final int position) {
            if (data == null) {
                return;
            }

           //  TextView appointment_conform_tv = fragmentView.findViewById(R.id.appointment_conform_tv);

            try {
                Date date = Utils.changeStringToDateFormat(data.getAppointmentDateTime(), SERVER_DATE_FORMAT_NEW);
                String dateStr= DISPLAY_DATE_FORMATTER.format(date);
                String timeStr= DISPLAY_TIME_FORMATTER.format(date);
                //With Dr. First name Middle name Last Name at 07:00 PM on Tuesday, 26-12-2017
                //appointment_details_tv.setText("With "+ data.getDoctorName() +" at "+ timeStr+", "+ dateStr+"\nat "+data.getAddress());
                title_tv.setText( data.getDoctorName());
                clinic_name_tv.setText( data.getClinicName());
                date_tv.setText( timeStr+", "+ dateStr);
                address_tv.setText( data.getAddress()+", "+data.getCity());

            }catch (Exception e)
            {
                //appointment_details_tv.setText("-");

            }

            cancel_app_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = ((Activity)MyApplication.getCurrentActivityContext()).getIntent();
                   // intent.setClass(MyApplication.getCurrentActivityContext(), DoctorsClinicsListActivity.class);
                    if(MyApplication.getCurrentActivityContext() instanceof UpComingAppointmentListActivity) {
                        //intent=  ((UpComingAppointmentListActivity)(MyApplication.getCurrentActivityContext())).getIntent();
                        intent.setClass(MyApplication.getCurrentActivityContext(),CancelAppointmentActivity.class);

                    }else
                    {
                        intent.setClass(MyApplication.getCurrentActivityContext(),CancelAppointmentActivity.class);

                    }
                   // Intent intent=  new Intent(MyApplication.getCurrentActivityContext(),CancelAppointmentActivity.class);

                    intent.putExtra(Constants.Pref.APPOINTMENT_ID_KEY,data.getId()+"");
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra(CancelAppointmentActivity.IS_FROM_APPOINTMENT_LIST_KEY,true);

                    if(MyApplication.getCurrentActivityContext() instanceof UpComingAppointmentListActivity)
                    {
                        UpComingAppointmentListActivity activity= (UpComingAppointmentListActivity) MyApplication.getCurrentActivityContext();
                        activity.startActivityForResult(intent,UpComingAppointmentListActivity.REQUESTCODE);
                    }else {
                        MyApplication.getCurrentActivityContext().startActivity(intent);
                    }


                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = ((Activity)MyApplication.getCurrentActivityContext()).getIntent();
                   //intent.setClass(MyApplication.getCurrentActivityContext(), DoctorsClinicsListActivity.class);
                    //Intent intent=  null;//new Intent(MyApplication.getCurrentActivityContext(),AppointmentDetailsActivity.class);
                           /*((Activity) MyApplication.getCurrentActivityContext()).getIntent();*/

                    if(MyApplication.getCurrentActivityContext() instanceof UpComingAppointmentListActivity) {
                         //intent=  ((UpComingAppointmentListActivity)(MyApplication.getCurrentActivityContext())).getIntent();
                        intent.setClass(MyApplication.getCurrentActivityContext(),AppointmentDetailsActivity.class);

                    }else
                    {
                         intent.setClass(MyApplication.getCurrentActivityContext(),AppointmentDetailsActivity.class);

                    }
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
                    if(MyApplication.getCurrentActivityContext() instanceof UpComingAppointmentListActivity)
                    {
                        UpComingAppointmentListActivity activity= (UpComingAppointmentListActivity) MyApplication.getCurrentActivityContext();
                        activity.startActivityForResult(intent,UpComingAppointmentListActivity.REQUESTCODE);
                    }else {
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
                    ReScheduleAppointmentRequestDetails requestDetails=null;
                    try {
                        requestDetails =
                                new ReScheduleAppointmentRequestDetails(Integer.parseInt(data.getDoctorId()), Integer.parseInt(data.getBranchId()), data.getPatientId(),
                                        data.getAppointmentDateTime(), data.getId());
                    }catch (Exception e)
                    {

                    }
                    if(requestDetails==null)
                    {
                        return;
                    }
                    final ReScheduleAppointmentRequestDetails details=requestDetails;


                    Intent intent = ((Activity)MyApplication.getCurrentActivityContext()).getIntent();
                    intent.setClass(MyApplication.getCurrentActivityContext(),ReScheduleAppointmentActivity.class);
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



            //With Dr. M.J Koruan at 04:00 PM on\nSaturday, 23-12-2017\nat Clinic 1, Al Karama, Dubai.

           /* itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                   *//* "doctorid":"46",
                            "clinicid":"21",
                            "branchid":40*//*
                   Intent intent=  new Intent(MyApplication.getCurrentActivityContext(),DoctorProfileActivity.class);
                           *//*((Activity) MyApplication.getCurrentActivityContext()).getIntent();*//*
                    intent.putExtra(ListenerKey.ObjectKey.SEARCH_RESULT_DOCTOR_LIST_DATA_KEY,new Messenger(new AbstractIBinder(){
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

            MyApplication.getInstance().setBitmapToImageviewCircular(R.drawable.profile_icon, profile_iv, data.getDoctorLogo());


            title_tv.setText(data.getDoctorName());
            designation_tv.setText(data.getSpecalities());
            String timings = data.getConsultTimings();
            if(timings!=null) {
                timings = timings.replaceAll("\\r\\n", " ");
                time_tv.setText(timings);
            }else
            {
                time_tv.setText("-");
            }


            String address = data.getAddress();
            if(address!=null) {
                address = address.replaceAll("\\r\\n", " ");
                address_tv.setText(address);
            }else
            {
                address_tv.setText("-");
            }


            reviews_count_tv.setText(TextUtils.isEmpty(data.getDoctorTotalReviews())?"0":data.getDoctorTotalReviews());

            recommendations_count_tv.setText(TextUtils.isEmpty(data.getDoctorRecommendedCount())?"0":data.getDoctorRecommendedCount());
            book_appointment_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utils.showToastMsg("Coming soon");

                    Intent intent=  new Intent(MyApplication.getCurrentActivityContext(),ScheduleAppointmentActivity.class);
                           *//*((Activity) MyApplication.getCurrentActivityContext()).getIntent();*//*
                    intent.putExtra(ListenerKey.ObjectKey.SEARCH_RESULT_DOCTOR_LIST_DATA_KEY,new Messenger(new AbstractIBinder(){
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
*/


           /* date_display_tv.setText(order.getDate()+"");
            total_amount_tv.setText(Utils.getStringFromResources(R.string.indian_currency_symbol)+order.getTotalAmount());
            order_status_tv.setText(order.getOrderstatus());

            if(!order.getOrder_status_code().equalsIgnoreCase(Constants.OrderStatus.PAYMENT_ACCEPTED))
            {
              //  write_a_review_linear.setVisibility(View.GONE);
               // rating_bar_relative.setVisibility(View.GONE);
               feedback_and_rating_bt.setVisibility(View.GONE);
                order_status_tv.setTextColor(MyApplication.getCurrentActivityContext().getResources().getColor(R.color.app_red));
            }else
            {
                //write_a_review_linear.setVisibility(View.VISIBLE);
               // rating_bar_relative.setVisibility(View.VISIBLE);
                feedback_and_rating_bt.setVisibility(View.VISIBLE);
                order_status_tv.setTextColor(MyApplication.getCurrentActivityContext().getResources().getColor(R.color.app_green));

            }

            feedback_and_rating_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent= new Intent(MyApplication.getCurrentActivityContext(), FeedbackAndRatingActivity.class);
                    intent.putExtra(Constants.Pref.ORDER_ID_KEY,order.getOrderId());
                    MyApplication.getCurrentActivityContext().startActivity(intent);

                   // Utils.showToastMessage(order.getOrderId()+"");
                }
            });*/
        }
    }

   /* protected void showMyDialog(String title,String message,final ApiErrorDialogInterface dialogInterface)
    {
        SimpleDialog.Builder builder = new SimpleDialog.Builder(R.style.SimpleDialogLight){
            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {
                //Toast.makeText(mActivity, "Agreed", Toast.LENGTH_SHORT).show();
                super.onPositiveActionClicked(fragment);
                if(dialogInterface!=null) {
                    dialogInterface.retryClick();
                }
            }

            @Override
            public void onNegativeActionClicked(DialogFragment fragment) {
                //Toast.makeText(mActivity, "Disagreed", Toast.LENGTH_SHORT).show();
                super.onNegativeActionClicked(fragment);
                if(dialogInterface!=null) {
                    dialogInterface.onCloseClick();
                }
            }
        };

        ((SimpleDialog.Builder)builder).message(message)
                .title(title)
                .positiveAction("Retry")
                .negativeAction("Close");
        DialogFragment fragment = DialogFragment.newInstance(builder);
        fragment.setCancelable(false);
        fragment.show(, null);
    }*/

}
