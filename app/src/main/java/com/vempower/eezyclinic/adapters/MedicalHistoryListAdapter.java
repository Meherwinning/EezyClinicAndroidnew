package com.vempower.eezyclinic.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Messenger;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vempower.eezyclinic.APICore.MedicalHistoryData;
import com.vempower.eezyclinic.APICore.PrescriptionAPIData;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.activities.MedicalHistoryEditDialogActivity;
import com.vempower.eezyclinic.activities.PDFViewActivity;
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


    public class MedicalHistoryListAdapter extends RecyclerView.Adapter<MedicalHistoryListAdapter.MedicalHistoryHolder> {

        private List<MedicalHistoryData> medicalHistoryList;

        private LayoutInflater inflater;


        public MedicalHistoryListAdapter(List<MedicalHistoryData> medicalHistoryList) {
            this.medicalHistoryList = medicalHistoryList;
            inflater = (LayoutInflater) MyApplication.getCurrentActivityContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }


        @Override
        public MedicalHistoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final View convertView = inflater
                    .inflate(R.layout.medical_history_single_item, parent, false);
            return new MedicalHistoryHolder(convertView);
        }


        @Override
        public void onBindViewHolder(MedicalHistoryHolder holder, int position) {


            holder.bindData(medicalHistoryList.get(position), position);


        }

        @Override
        public int getItemCount() {
            return medicalHistoryList == null ? 0 : medicalHistoryList.size();
        }


        public void setUpdatedList(List<MedicalHistoryData> newList) {
            if (newList == null || newList.size() == 0) {
                if (this.medicalHistoryList == null) {
                    return;
                }
                this.medicalHistoryList.clear();
                notifyDataSetChanged();
                return;
            }
            this.medicalHistoryList = newList;
            notifyDataSetChanged();
        }


        public class MedicalHistoryHolder extends RecyclerView.ViewHolder {
            //19 August 2017, Saturday 01.08 PM
            // String DISPLAY_DATE="MMM d, yyyy";
            //  String DISPLAY_TIME="h:mm a 'on' EEEE";
            String DISPLAY_DATE_TIME="MMMM d, yyyy";
            String  SERVER_DATE_FORMAT_NEW="yyyy-MM-dd HH:mm:ss";//"2017-12-26 16:55:00"
            SimpleDateFormat DISPLAY_DATE_TIME_FORMATTER = new SimpleDateFormat(DISPLAY_DATE_TIME);
            // SimpleDateFormat DISPLAY_TIME_FORMATTER = new SimpleDateFormat(DISPLAY_TIME);

            private TextView content_tv;
            private  TextView date_tv, edited_by_tv;
            private ImageView menu_iv;

            public MedicalHistoryHolder(View itemView) {
                super(itemView);
                content_tv  = itemView.findViewById(R.id.content_tv);
                date_tv  = itemView.findViewById(R.id.date_tv);
                edited_by_tv  = itemView.findViewById(R.id.edited_by_tv);
                menu_iv    = itemView.findViewById(R.id.menu_iv);

            }

            public void bindData(final MedicalHistoryData data, final int position) {
                if (data == null) {
                    return;
                }

                content_tv.setText(data.getContent());
                edited_by_tv.setText(data.getEditedBy());
                try {
                    Date date = Utils.changeStringToDateFormat(data.getChangeDate(), SERVER_DATE_FORMAT_NEW);
                    String dateStr= DISPLAY_DATE_TIME_FORMATTER.format(date);
                    // String timeStr= DISPLAY_TIME_FORMATTER.format(date);
                    //With Dr. First name Middle name Last Name at 07:00 PM on Tuesday, 26-12-2017
                    date_tv.setText(dateStr);
                }catch (Exception e) {
                    date_tv.setText(null);

                }
                menu_iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onMenuClick(data,menu_iv);
                    }
                });

               /* itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                         Utils.showToastMsg(data.getId()+"");

                    }
                });*/

            }
        }



    protected void onMenuClick(final MedicalHistoryData data,View view)
    {

        try {

            //holder.mIndicator.setExpandedState(false,false);
            PopupMenu popup = new PopupMenu(MyApplication.getCurrentActivityContext(), view);
            popup.getMenuInflater().inflate(R.menu.edit_option, popup.getMenu());
            popup.show();
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {

                    switch (menuItem.getItemId()) {
                        case R.id.edit_item:
                           Utils.showToastMsg(data.toString());
                            Intent intent= new Intent(MyApplication.getCurrentActivityContext(),MedicalHistoryEditDialogActivity.class);
                            intent.putExtra(ListenerKey.ObjectKey.MEDICAL_HISTORY_OBJECT_KEY,new Messenger(new AbstractIBinder(){
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
                            break;
                       /* case R.id.audio_update:
                            audioInfoButtonClick(detail);
                            break;
                        case R.id.add_to_playlist:
                            addToPlaylistButtonClick(detail);
                            break;
                        case R.id.add_to_contact:
                            addToContactButtonClick(detail);
                            break;

                        case R.id.delete_audio:
                            if(playlistId==-1) {
                                deleteAudioFile(detail);
                            }else
                            {
                                deleteAudioFile(detail,playlistId);
                            }
                            break;*/
                    }

                    return true;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    }