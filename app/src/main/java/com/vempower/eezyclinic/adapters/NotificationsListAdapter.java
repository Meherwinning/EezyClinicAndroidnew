package com.vempower.eezyclinic.adapters;

import android.content.Context;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vempower.eezyclinic.APICore.AbstractNotification;
import com.vempower.eezyclinic.APICore.Notification;
import com.vempower.eezyclinic.APICore.NotificationsComingappointment;
import com.vempower.eezyclinic.APICore.NotificationsSendrequest;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

;

/**
 * Created by Satishk on 9/7/2017.
 */


public class NotificationsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<AbstractNotification> notificationsList;

    private LayoutInflater inflater;
    private SendRequestListener sendRequestListener;
    private DeleteListener deleteListener;


    public NotificationsListAdapter(List<AbstractNotification> notificationsList) {
        this.notificationsList = notificationsList;
        inflater = (LayoutInflater) MyApplication.getCurrentActivityContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        if (viewType == AbstractNotification.COMING_APPOINTMENT_TYPE) {
            final View convertView = inflater
                    .inflate(R.layout.notification_appointment_single_layout, parent, false);
            return new AppointmentHolder(convertView);
        } else if (viewType == AbstractNotification.SEND_REQUEST_TYPE) {
            final View convertView = inflater
                    .inflate(R.layout.notification_send_request_single_layout, parent, false);
            return new SendRequestHolder(convertView);
        } else {


            final View convertView = inflater
                    .inflate(R.layout.notification_normal_msz_single_layout, parent, false);
            return new MessageHolder(convertView);

        }

    }

    @Override
    public int getItemViewType(int position) {
        return notificationsList.get(position).getNotificationType();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof SendRequestHolder) {

            SendRequestHolder requestHolder = (SendRequestHolder) holder;
            requestHolder.bindData(notificationsList.get(position), position);

        } else if (holder instanceof AppointmentHolder) {

            AppointmentHolder holder1 = (AppointmentHolder) holder;
            holder1.bindData(notificationsList.get(position), position);

        }
        if (holder instanceof MessageHolder) {

            MessageHolder holder1 = (MessageHolder) holder;
            holder1.bindData(notificationsList.get(position), position);
        }
    }

    @Override
    public int getItemCount() {
        return notificationsList == null ? 0 : notificationsList.size();
    }


    public void setUpdatedList(List<AbstractNotification> newList) {
        if (newList == null || newList.size() == 0) {
            if (this.notificationsList == null) {
                return;
            }
            this.notificationsList.clear();
            notifyDataSetChanged();
            return;
        }
        this.notificationsList = newList;
        notifyDataSetChanged();
    }


    public class SendRequestHolder extends RecyclerView.ViewHolder {
        private AppCompatButton accept_bt, reject_bt;
        private LinearLayout rejected_linear;
        private TextView message_tv;

        public SendRequestHolder(View itemView) {
            super(itemView);
            message_tv = itemView.findViewById(R.id.message_tv);
            accept_bt = itemView.findViewById(R.id.accept_bt);
            reject_bt = itemView.findViewById(R.id.reject_bt);

            rejected_linear = itemView.findViewById(R.id.rejected_linear);

        }

        public void bindData(final AbstractNotification data, final int position) {
            if (data == null || !(data instanceof NotificationsSendrequest)) {
                return;
            }
            final NotificationsSendrequest sendrequest = (NotificationsSendrequest) data;

            String message = sendrequest.getPatientName() + ", with User ID " + sendrequest.getPatientId() + " and Email " + sendrequest.getPatentEmail() + " has requested to add you as a family member.";
            message_tv.setText(message);

            accept_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Utils.showToastMsg("Now accept button click");
                    if (sendRequestListener != null) {
                        sendRequestListener.status(true, sendrequest);
                    }
                }
            });
            reject_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //  Utils.showToastMsg("Now reject button click");
                    if (sendRequestListener != null) {
                        sendRequestListener.status(false, sendrequest);
                    }
                }
            });

            accept_bt.setEnabled(false);
            reject_bt.setEnabled(false);

            accept_bt.setVisibility(View.GONE);
            reject_bt.setVisibility(View.GONE);
            rejected_linear.setVisibility(View.GONE);

            if (!TextUtils.isEmpty(sendrequest.getStatus())) {
                if (sendrequest.getStatus().equalsIgnoreCase("2")) {
                    rejected_linear.setVisibility(View.VISIBLE);
                } else if (sendrequest.getStatus().equalsIgnoreCase("0")) {
                    reject_bt.setVisibility(View.VISIBLE);
                    accept_bt.setVisibility(View.VISIBLE);
                    accept_bt.setEnabled(true);
                    reject_bt.setEnabled(true);
                }
            }


        }
    }


    public class AppointmentHolder extends RecyclerView.ViewHolder {
        //19 August 2017, Saturday 01.08 PM
        // String DISPLAY_DATE="MMM d, yyyy";
        //  String DISPLAY_TIME="h:mm a 'on' EEEE";
        //10:00 AM on Wednesday, 07 February 2018
        String DISPLAY_DATE_TIME = "h:mm a 'on' EEEE, d MMMM yyyy";
        String SERVER_DATE_FORMAT_NEW = "yyyy-MM-dd HH:mm:ss";//"2017-12-26 16:55:00"
        SimpleDateFormat DISPLAY_DATE_TIME_FORMATTER = new SimpleDateFormat(DISPLAY_DATE_TIME);
        // SimpleDateFormat DISPLAY_TIME_FORMATTER = new SimpleDateFormat(DISPLAY_TIME);

        private TextView message_tv;

        public AppointmentHolder(View itemView) {
            super(itemView);
            message_tv = itemView.findViewById(R.id.message_tv);
            //  clinic_address_tv  = itemView.findViewById(R.id.clinic_address_tv);
            //date_and_time_tv  = itemView.findViewById(R.id.date_and_time_tv);

        }

        public void bindData(final AbstractNotification data, final int position) {
            if (data == null || !(data instanceof NotificationsComingappointment)) {
                return;
            }
            NotificationsComingappointment comingappointment = (NotificationsComingappointment) data;
            String dateStr = "";
            try {
                Date date = Utils.changeStringToDateFormat(comingappointment.getAppointmentDateTime(), SERVER_DATE_FORMAT_NEW);
                dateStr = DISPLAY_DATE_TIME_FORMATTER.format(date);
                // String timeStr= DISPLAY_TIME_FORMATTER.format(date);
                //With Dr. First name Middle name Last Name at 07:00 PM on Tuesday, 26-12-2017
                // date_and_time_tv.setText("Dated :"+dateStr);
            } catch (Exception e) {

            }
            /*
            "clinicName": "Al Shifa Hospital",
                "locality": "Dubai",
                "city": "Abu Dhabi"
             */
            String message = "Appointment for Mr/Mrs." + comingappointment.getPatientName() + " is confirmed with " + comingappointment.getDoctorName()
                    + " at " + dateStr + " at " + comingappointment.getClinicName() + " , "
                    + comingappointment.getLocality() + " , "
                    + comingappointment.getCity();
            message_tv.setText(message);
              /*  doctor_name_tv.setText(data.getDoctorName());
                clinic_address_tv.setText(data.getClinicName());


                //  TextView appointment_conform_tv = fragmentView.findViewById(R.id.appointment_conform_tv);
//19 August 2017, Saturday 01.08 PM
                try {
                    Date date = Utils.changeStringToDateFormat(data.getCreatedDate(), SERVER_DATE_FORMAT_NEW);
                    String dateStr= DISPLAY_DATE_TIME_FORMATTER.format(date);
                    // String timeStr= DISPLAY_TIME_FORMATTER.format(date);
                    //With Dr. First name Middle name Last Name at 07:00 PM on Tuesday, 26-12-2017
                    date_and_time_tv.setText("Dated :"+dateStr);
                }catch (Exception e) {
                    date_and_time_tv.setText("-");

                }

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                         Utils.showToastMsg(data.getId()+"");



                        Intent intent=  new Intent(MyApplication.getCurrentActivityContext(),PDFViewActivity.class);
                           //((Activity) MyApplication.getCurrentActivityContext()).getIntent();
                        intent.putExtra(ListenerKey.ObjectKey.PDF_DETAILS_OBJECT_KEY,new Messenger(new AbstractIBinder(){
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
                        intent.putExtra(Constants.Pref.IS_FROM_UPDATE_PRESCRIPTION_KEY,true);

                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        MyApplication.getCurrentActivityContext().startActivity(intent);



                    }
                });*/

        }
    }

    public class MessageHolder extends RecyclerView.ViewHolder {
        private TextView message_tv, from_tv;
        private AppCompatButton delete_bt;

        public MessageHolder(View itemView) {
            super(itemView);
            message_tv = itemView.findViewById(R.id.message_tv1);
            from_tv = itemView.findViewById(R.id.from_tv1);
            delete_bt= itemView.findViewById(R.id.delete_bt);
            //  clinic_address_tv  = itemView.findViewById(R.id.clinic_address_tv);
            //date_and_time_tv  = itemView.findViewById(R.id.date_and_time_tv);

        }

        public void bindData(final AbstractNotification data, final int position) {
            if (data == null || !(data instanceof Notification)) {
                return;
            }
           final Notification notification = (Notification) data;

            from_tv.setText(notification.getSendFrom());

            message_tv.setText(notification.getMessage());
            delete_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(deleteListener!=null)
                    {
                        deleteListener.delete(notification);
                    }
                }
            });
              /*  doctor_name_tv.setText(data.getDoctorName());
                clinic_address_tv.setText(data.getClinicName());


                //  TextView appointment_conform_tv = fragmentView.findViewById(R.id.appointment_conform_tv);
//19 August 2017, Saturday 01.08 PM
                try {
                    Date date = Utils.changeStringToDateFormat(data.getCreatedDate(), SERVER_DATE_FORMAT_NEW);
                    String dateStr= DISPLAY_DATE_TIME_FORMATTER.format(date);
                    // String timeStr= DISPLAY_TIME_FORMATTER.format(date);
                    //With Dr. First name Middle name Last Name at 07:00 PM on Tuesday, 26-12-2017
                    date_and_time_tv.setText("Dated :"+dateStr);
                }catch (Exception e) {
                    date_and_time_tv.setText("-");

                }

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                         Utils.showToastMsg(data.getId()+"");



                        Intent intent=  new Intent(MyApplication.getCurrentActivityContext(),PDFViewActivity.class);
                           //((Activity) MyApplication.getCurrentActivityContext()).getIntent();
                        intent.putExtra(ListenerKey.ObjectKey.PDF_DETAILS_OBJECT_KEY,new Messenger(new AbstractIBinder(){
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
                        intent.putExtra(Constants.Pref.IS_FROM_UPDATE_PRESCRIPTION_KEY,true);

                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        MyApplication.getCurrentActivityContext().startActivity(intent);



                    }
                });*/

        }
    }


    public void setOnSendRequestListener(SendRequestListener sendRequestListener) {
        this.sendRequestListener = sendRequestListener;
    }

    public interface SendRequestListener {
        void status(boolean isAcceptClick, NotificationsSendrequest sendrequest);
    }


    public interface DeleteListener
    {
        void delete(Notification notification );
    }

    public void setOnDeleteListener(DeleteListener deleteListener)
    {
        this.deleteListener=deleteListener;
    }

}