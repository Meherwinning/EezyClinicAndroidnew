package com.vempower.eezyclinic.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Messenger;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.vempower.eezyclinic.APICore.SearchResultDoctorListData;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.activities.DoctorProfileActivity;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.callbacks.ListenerKey;
import com.vempower.eezyclinic.interfaces.AbstractIBinder;
import com.vempower.eezyclinic.interfaces.IntentObjectListener;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.eezyclinic.views.MyCheckedTextViewRR;
import com.vempower.eezyclinic.views.MyTextViewRM;
import com.vempower.eezyclinic.views.MyTextViewRR;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Satishk on 9/7/2017.
 */

public class TimeSlotsListAdapter extends RecyclerView.Adapter<TimeSlotsListAdapter.OrdersListHolder> {

    private List<String> timeSlotsList;

    private LayoutInflater inflater;
    private  String preSelectedDate,preSelectedTimeSlot;
    private  String dateStr;


    public TimeSlotsListAdapter(String dateStr,List<String> timeSlotsList) {
        this.dateStr=dateStr;
        this.timeSlotsList = timeSlotsList;
        inflater = (LayoutInflater) MyApplication.getCurrentActivityContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public String getPreSelectedDateTime() {
        if(preSelectedDate==null|| preSelectedTimeSlot==null)
        {
            return null;
        }
        return preSelectedDate+" "+preSelectedTimeSlot;
    }


    @Override
    public OrdersListHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        final View convertView = inflater
                .inflate(R.layout.cal_time_slot_single_layout, parent, false);
        return new OrdersListHolder(convertView);
    }


    @Override
    public void onBindViewHolder(OrdersListHolder holder, int position) {


        holder.bindData(timeSlotsList.get(position), position);


    }

    @Override
    public int getItemCount() {
        return timeSlotsList == null ? 0 : timeSlotsList.size();
    }


    public void setUpdatedList(String dateStr,List<String> newList) {
        this.dateStr=dateStr;
        if (newList == null || newList.size() == 0) {
            if (this.timeSlotsList == null) {
                return;
            }
            this.timeSlotsList.clear();
            notifyDataSetChanged();

            return;
        }
        this.timeSlotsList = newList;
        notifyDataSetChanged();
    }



    public class OrdersListHolder extends RecyclerView.ViewHolder {

        private MyCheckedTextViewRR time_slot_ctv;

        public OrdersListHolder(View itemView) {
            super(itemView);
            time_slot_ctv = itemView.findViewById(R.id.time_slot_ctv);




        }

        public void bindData(final String data, final int position) {
            if (data == null) {
                return;
            }
            String[] split = data.split(Pattern.quote("|"));
            final String timeSlot=split[0];
            time_slot_ctv.setText(timeSlot);
            time_slot_ctv.setEnabled(false);
            if(split.length>1)
            {
                try
                {
                    int flag=Integer.parseInt(split[1].trim());
                    if(flag==1)
                    {
                        time_slot_ctv.setEnabled(true);
                    }
                }catch (Exception e)
                {

                }
            }

            if(preSelectedDate!=null && preSelectedTimeSlot!=null && dateStr!=null && timeSlot!=null)
            {
                if(preSelectedDate.equalsIgnoreCase(dateStr) && preSelectedTimeSlot.equalsIgnoreCase(timeSlot))
                {
                    time_slot_ctv.setChecked(true);
                }else
                {
                    time_slot_ctv.setChecked(false);
                }
            }else
            {
                time_slot_ctv.setChecked(false);
            }



                    time_slot_ctv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(TextUtils.isEmpty(dateStr))
                            {
                                Utils.showToastMessage("Please select a valid date");
                                return;

                            }

                            if( TextUtils.isEmpty(timeSlot)) {
                                Utils.showToastMessage("Invalid time slot");
                                return;

                            }
                                if(preSelectedDate!=null && preSelectedTimeSlot!=null) {
                                    if (preSelectedDate.equalsIgnoreCase(dateStr) && preSelectedTimeSlot.equalsIgnoreCase(timeSlot)) {
                                       // time_slot_ctv.setChecked(false);
                                        preSelectedTimeSlot=null;
                                        preSelectedDate=null;
                                        notifyDataSetChanged();
                                        return;
                                    }
                                }
                           // time_slot_ctv.setChecked(true);
                            preSelectedTimeSlot=timeSlot;
                            preSelectedDate=dateStr;
                            notifyDataSetChanged();
                            if(true)
                            {
                                return;
                            }



/*

                            if(pre_time_slot_ctv!=null && pre_time_slot_ctv.equals(time_slot_ctv))
                            {
                                time_slot_ctv.setChecked(false);
                                pre_time_slot_ctv=null;
                                preSelectedTimeSlot=null;
                                return;
                            }
                            // timeSlotsList.get(preSelectedPosition)
                            if(pre_time_slot_ctv!=null)
                            {
                                pre_time_slot_ctv.setChecked(false);
                                pre_time_slot_ctv=null;
                            }
                            //   Utils.showToastMessage("Click on "+data);
                            time_slot_ctv.setChecked(!time_slot_ctv.isChecked());
                            if(time_slot_ctv.isChecked())
                            {
                                pre_time_slot_ctv=time_slot_ctv;
                                preSelectedDate=dateStr;
                                preSelectedTimeSlot=timeSlot;
                            }else{
                                pre_time_slot_ctv=null;
                            }
*/



                   /* "doctorid":"46",
                            "clinicid":"21",
                            "branchid":40*/
   /*                Intent intent=  new Intent(MyApplication.getCurrentActivityContext(),DoctorProfileActivity.class);
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
 */               }
            });




        }
    }



}
