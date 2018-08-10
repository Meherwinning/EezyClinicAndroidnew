package com.vempower.eezyclinic.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Messenger;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vempower.eezyclinic.APICore.HelathReportsData;
import com.vempower.eezyclinic.APICore.PrescriptionAPIData;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.activities.CasesheetsDetailsActivity;
import com.vempower.eezyclinic.activities.PDFViewActivity;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.callbacks.ListenerKey;
import com.vempower.eezyclinic.interfaces.AbstractIBinder;
import com.vempower.eezyclinic.interfaces.IntentObjectListener;
import com.vempower.eezyclinic.utils.Utils;

 ;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Satishk on 9/7/2017.
 */


    public class ReportsListAdapter extends RecyclerView.Adapter<ReportsListAdapter.ReportsHolder> {

        private List<HelathReportsData> helathReportsList;

        private LayoutInflater inflater;


        public ReportsListAdapter(List<HelathReportsData> helathReportsList) {
            this.helathReportsList = helathReportsList;
            inflater = (LayoutInflater) MyApplication.getCurrentActivityContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }


        @Override
        public ReportsHolder onCreateViewHolder(ViewGroup parent, int viewType) {


            final View convertView = inflater
                    .inflate(R.layout.prescription_single_item, parent, false);
            return new ReportsHolder(convertView);
        }


        @Override
        public void onBindViewHolder(ReportsHolder holder, int position) {


            holder.bindData(helathReportsList.get(position), position);


        }

        @Override
        public int getItemCount() {
            return helathReportsList == null ? 0 : helathReportsList.size();
        }


        public void setUpdatedList(List<HelathReportsData> newList) {
            if (newList == null || newList.size() == 0) {
                if (this.helathReportsList == null) {
                    return;
                }
                this.helathReportsList.clear();
                notifyDataSetChanged();
                return;
            }
            this.helathReportsList = newList;
            notifyDataSetChanged();
        }


        public class ReportsHolder extends RecyclerView.ViewHolder {
            //19 August 2017, Saturday 01.08 PM
            // String DISPLAY_DATE="MMM d, yyyy";
            //  String DISPLAY_TIME="h:mm a 'on' EEEE";
            String DISPLAY_DATE_TIME="MMMM d, yyyy";
            String  SERVER_DATE_FORMAT_NEW="yyyy-MM-dd";//"2017-12-26 16:55:00"
            SimpleDateFormat DISPLAY_DATE_TIME_FORMATTER = new SimpleDateFormat(DISPLAY_DATE_TIME);
            // SimpleDateFormat DISPLAY_TIME_FORMATTER = new SimpleDateFormat(DISPLAY_TIME);

            private TextView doctor_name_tv;
            private TextView clinic_address_tv, date_and_time_tv;

            public ReportsHolder(View itemView) {
                super(itemView);
                doctor_name_tv  = itemView.findViewById(R.id.doctor_name_tv);
                clinic_address_tv  = itemView.findViewById(R.id.clinic_address_tv);
                date_and_time_tv  = itemView.findViewById(R.id.date_and_time_tv);

            }

            public void bindData(final HelathReportsData data, final int position) {
                if (data == null) {
                    return;
                }
                doctor_name_tv.setText(data.getDocumentName()+" Report");
                clinic_address_tv.setText(data.getDoctorName()+" of "+ data.getClinicName());


                //  TextView appointment_conform_tv = fragmentView.findViewById(R.id.appointment_conform_tv);
//19 August 2017, Saturday 01.08 PM
                try {
                    Date date = Utils.changeStringToDateFormat(data.getCreatedDate(), SERVER_DATE_FORMAT_NEW);
                    String dateStr= DISPLAY_DATE_TIME_FORMATTER.format(date);
                    // String timeStr= DISPLAY_TIME_FORMATTER.format(date);
                    //With Dr. First name Middle name Last Name at 07:00 PM on Tuesday, 26-12-2017
                    date_and_time_tv.setText("Dated :"+dateStr);
                }catch (Exception e)
                {
                    date_and_time_tv.setText("-");

                }

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // Utils.showToastMsg(data.getId()+"");
                        Intent intent = ((Activity)MyApplication.getCurrentActivityContext()).getIntent();
                        intent.setClass(MyApplication.getCurrentActivityContext(),PDFViewActivity.class);
                           ((Activity) MyApplication.getCurrentActivityContext()).getIntent();
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
                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        MyApplication.getCurrentActivityContext().startActivity(intent);



                    }
                });

            }
        }



    }