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
import android.widget.TextView;

import com.vempower.eezyclinic.APICore.CaseSheetsListDate;
import com.vempower.eezyclinic.APICore.HelathReportsData;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.activities.CasesheetsDetailsActivity;
import com.vempower.eezyclinic.activities.DoctorsClinicsListActivity;
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


    public class CaseSheetsListAdapter extends RecyclerView.Adapter<CaseSheetsListAdapter.CaseSheetsHolder> {

        private List<CaseSheetsListDate> sheetsList;

        private LayoutInflater inflater;


        public CaseSheetsListAdapter(List<CaseSheetsListDate> sheetsList) {
            this.sheetsList = sheetsList;
            inflater = (LayoutInflater) MyApplication.getCurrentActivityContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }


        @Override
        public CaseSheetsHolder onCreateViewHolder(ViewGroup parent, int viewType) {


            final View convertView = inflater
                    .inflate(R.layout.prescription_single_item, parent, false);
            return new CaseSheetsHolder(convertView);
        }


        @Override
        public void onBindViewHolder(CaseSheetsHolder holder, int position) {


            holder.bindData(sheetsList.get(position), position);


        }

        @Override
        public int getItemCount() {
            return sheetsList == null ? 0 : sheetsList.size();
        }


        public void setUpdatedList(List<CaseSheetsListDate> newList) {
            if (newList == null || newList.size() == 0) {
                if (this.sheetsList == null) {
                    return;
                }
                this.sheetsList.clear();
                notifyDataSetChanged();
                return;
            }
            this.sheetsList = newList;
            notifyDataSetChanged();
        }


        public class CaseSheetsHolder extends RecyclerView.ViewHolder {
            //19 August 2017, Saturday 01.08 PM
            // String DISPLAY_DATE="MMM d, yyyy";
            //  String DISPLAY_TIME="h:mm a 'on' EEEE";
            String DISPLAY_DATE_TIME="MMMM d, yyyy";
            String  SERVER_DATE_FORMAT_NEW="yyyy-MM-dd HH:mm:ss";//"2017-12-26 16:55:00"
            SimpleDateFormat DISPLAY_DATE_TIME_FORMATTER = new SimpleDateFormat(DISPLAY_DATE_TIME);
            // SimpleDateFormat DISPLAY_TIME_FORMATTER = new SimpleDateFormat(DISPLAY_TIME);

            private TextView doctor_name_tv;
            private  TextView clinic_address_tv, date_and_time_tv;

            public CaseSheetsHolder(View itemView) {
                super(itemView);
                doctor_name_tv  = itemView.findViewById(R.id.doctor_name_tv);
                clinic_address_tv  = itemView.findViewById(R.id.clinic_address_tv);
                date_and_time_tv  = itemView.findViewById(R.id.date_and_time_tv);

            }

            public void bindData(final CaseSheetsListDate data, final int position) {
                if (data == null) {
                    return;
                }
                doctor_name_tv.setText(data.getDoctorfullname());
                clinic_address_tv.setText(data.getClinicName());


                //  TextView appointment_conform_tv = fragmentView.findViewById(R.id.appointment_conform_tv);
//19 August 2017, Saturday 01.08 PM
                try {
                    Date date = Utils.changeStringToDateFormat(data.getAppointmentDateTime(), SERVER_DATE_FORMAT_NEW);
                    String dateStr= DISPLAY_DATE_TIME_FORMATTER.format(date);
                    // String timeStr= DISPLAY_TIME_FORMATTER.format(date);
                    //With Dr. First name Middle name Last Name at 07:00 PM on Tuesday, 26-12-2017
                    date_and_time_tv.setText("Date of Appointment :"+dateStr);
                }catch (Exception e)
                {
                    date_and_time_tv.setText("-");

                }

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                         //Utils.showToastMsg(data.getAppointmentid()+"");

                        if(TextUtils.isEmpty(data.getCasesheetstatus()) || !data.getCasesheetstatus().equalsIgnoreCase("1"))
                        {
                            Utils.showToastMsg(R.string.no_casesheet_saved_lbl);
                            return;
                        }
                        Intent intent = ((Activity)MyApplication.getCurrentActivityContext()).getIntent();
                        intent.setClass(MyApplication.getCurrentActivityContext(),CasesheetsDetailsActivity.class);
                          // ((Activity) MyApplication.getCurrentActivityContext()).getIntent();
                        intent.putExtra(Constants.Pref.CASESHEET_APPOINTMENT_ID_KEY,data.getAppointmentid());
                       /* intent.putExtra(ListenerKey.ObjectKey.APPOINTMENT_OBJECT_KEY,new Messenger(new AbstractIBinder(){
                            @Override
                            protected IntentObjectListener getMyObject() {
                                return new IntentObjectListener(){

                                    @Override
                                    public Object getObject() {
                                        return data;
                                    }
                                };
                            }
                        }));*/
                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        MyApplication.getCurrentActivityContext().startActivity(intent);
                    }
                });

            }
        }



    }