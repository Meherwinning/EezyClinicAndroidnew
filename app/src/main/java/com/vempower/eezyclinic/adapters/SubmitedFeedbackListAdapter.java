package com.vempower.eezyclinic.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vempower.eezyclinic.APICore.PendingFeedbackData;
import com.vempower.eezyclinic.APICore.SubmitedFeedbackListData;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.eezyclinic.views.MyTextViewRM;
import com.vempower.eezyclinic.views.MyTextViewRR;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Satishk on 9/7/2017.
 */


    public class SubmitedFeedbackListAdapter extends RecyclerView.Adapter<SubmitedFeedbackListAdapter.PrescriptionHolder> {

        private List<SubmitedFeedbackListData> submitedFeedbackList;

        private LayoutInflater inflater;


        public SubmitedFeedbackListAdapter(List<SubmitedFeedbackListData> submitedFeedbackList) {
            this.submitedFeedbackList = submitedFeedbackList;
            inflater = (LayoutInflater) MyApplication.getCurrentActivityContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }


        @Override
        public PrescriptionHolder onCreateViewHolder(ViewGroup parent, int viewType) {


            final View convertView = inflater
                    .inflate(R.layout.feedback_view_single_item, parent, false);
            return new PrescriptionHolder(convertView);
        }


        @Override
        public void onBindViewHolder(PrescriptionHolder holder, int position) {


            holder.bindData(submitedFeedbackList.get(position), position);


        }

        @Override
        public int getItemCount() {
            return submitedFeedbackList == null ? 0 : submitedFeedbackList.size();
        }


        public void setUpdatedList(List<SubmitedFeedbackListData> newList) {
            if (newList == null || newList.size() == 0) {
                if (this.submitedFeedbackList == null) {
                    return;
                }
                this.submitedFeedbackList.clear();
                notifyDataSetChanged();
                return;
            }
            this.submitedFeedbackList = newList;
            notifyDataSetChanged();
        }


        public class PrescriptionHolder extends RecyclerView.ViewHolder {
            //19 August 2017, Saturday 01.08 PM
            // String DISPLAY_DATE="MMM d, yyyy";
            //  String DISPLAY_TIME="h:mm a 'on' EEEE";
            String DISPLAY_DATE_TIME="d MMMM yyyy EEEE h:mm a";
            String  SERVER_DATE_FORMAT_NEW="yyyy-MM-dd HH:mm:ss";//"2017-12-26 16:55:00"
            SimpleDateFormat DISPLAY_DATE_TIME_FORMATTER = new SimpleDateFormat(DISPLAY_DATE_TIME);
            // SimpleDateFormat DISPLAY_TIME_FORMATTER = new SimpleDateFormat(DISPLAY_TIME);

            private MyTextViewRM doctor_name_tv;
            private MyTextViewRR specialities_and_clinic_name_tv, date_and_time_tv;

            public PrescriptionHolder(View itemView) {
                super(itemView);
                doctor_name_tv  = itemView.findViewById(R.id.doctor_name_tv);
                specialities_and_clinic_name_tv  = itemView.findViewById(R.id.specialities_and_clinic_name_tv);
                date_and_time_tv  = itemView.findViewById(R.id.date_and_time_tv);

            }

            public void bindData(final SubmitedFeedbackListData data, final int position) {
                if (data == null) {
                    return;
                }
               doctor_name_tv.setText(data.getDoctorfullname());
                specialities_and_clinic_name_tv.setText(data.getSpecalities()+" "+ data.getClinicName());


                // MyTextViewRR appointment_conform_tv = fragmentView.findViewById(R.id.appointment_conform_tv);
//19 August 2017, Saturday 01.08 PM
                try {
                    Date date = Utils.changeStringToDateFormat(data.getAppointmentDateTime(), SERVER_DATE_FORMAT_NEW);
                    String dateStr= DISPLAY_DATE_TIME_FORMATTER.format(date);
                    // String timeStr= DISPLAY_TIME_FORMATTER.format(date);
                    //With Dr. First name Middle name Last Name at 07:00 PM on Tuesday, 26-12-2017
                    date_and_time_tv.setText(dateStr);
                }catch (Exception e) {
                    date_and_time_tv.setText(null);

                }

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                     /*    Utils.showToastMsg(data.getId()+"");



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
*/


                    }
                });

            }
        }



    }