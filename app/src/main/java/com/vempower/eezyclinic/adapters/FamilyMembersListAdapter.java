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

import com.vempower.eezyclinic.APICore.GetFamilyMemberData;
import com.vempower.eezyclinic.APICore.MedicalHistoryData;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.activities.MedicalHistoryEditDialogActivity;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.callbacks.ListenerKey;
import com.vempower.eezyclinic.interfaces.AbstractIBinder;
import com.vempower.eezyclinic.interfaces.IntentObjectListener;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.eezyclinic.views.MyTextViewRM;
import com.vempower.eezyclinic.views.MyTextViewRR;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Satishk on 9/7/2017.
 */


    public class FamilyMembersListAdapter extends RecyclerView.Adapter<FamilyMembersListAdapter.MedicalHistoryHolder> {

        private List<GetFamilyMemberData> familyMembersList;

        private LayoutInflater inflater;


        public FamilyMembersListAdapter(List<GetFamilyMemberData> familyMembersList) {
            this.familyMembersList = familyMembersList;
            inflater = (LayoutInflater) MyApplication.getCurrentActivityContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }


        @Override
        public MedicalHistoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final View convertView = inflater
                    .inflate(R.layout.family_member_single_item, parent, false);
            return new MedicalHistoryHolder(convertView);
        }


        @Override
        public void onBindViewHolder(MedicalHistoryHolder holder, int position) {
            holder.bindData(familyMembersList.get(position), position);
        }

        @Override
        public int getItemCount() {
            return familyMembersList == null ? 0 : familyMembersList.size();
        }


        public void setUpdatedList(List<GetFamilyMemberData> newList) {
            if (newList == null || newList.size() == 0) {
                if (this.familyMembersList == null) {
                    return;
                }
                this.familyMembersList.clear();
                notifyDataSetChanged();
                return;
            }
            this.familyMembersList = newList;
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

            private MyTextViewRM member_name_tv;
            private MyTextViewRR patient_id_tv, relation_tv;
            private ImageView menu_iv;

            public MedicalHistoryHolder(View itemView) {
                super(itemView);
                member_name_tv  = itemView.findViewById(R.id.member_name_tv);
                patient_id_tv  = itemView.findViewById(R.id.patient_id_tv);
                relation_tv  = itemView.findViewById(R.id.relation_tv);
                menu_iv    = itemView.findViewById(R.id.menu_iv);

            }

            public void bindData(final GetFamilyMemberData data, final int position) {
                if (data == null) {
                    return;
                }

                member_name_tv.setText(data.getPatientName());
                patient_id_tv.setText(data.getFamilyMemberpatientId());
                relation_tv.setText(data.getRelation());
               /* try {
                    Date date = Utils.changeStringToDateFormat(data.getChangeDate(), SERVER_DATE_FORMAT_NEW);
                    String dateStr= DISPLAY_DATE_TIME_FORMATTER.format(date);
                    // String timeStr= DISPLAY_TIME_FORMATTER.format(date);
                    //With Dr. First name Middle name Last Name at 07:00 PM on Tuesday, 26-12-2017
                    date_tv.setText(dateStr);
                }catch (Exception e) {
                    date_tv.setText(null);

                }*/
                menu_iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onMenuClick(data,menu_iv);
                    }
                });

                /*itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                         Utils.showToastMsg(data.getId()+"");

                    }
                });*/

            }
        }



    protected void onMenuClick(final GetFamilyMemberData data,View view)
    {

        try {

            //holder.mIndicator.setExpandedState(false,false);
            PopupMenu popup = new PopupMenu(MyApplication.getCurrentActivityContext(), view);
            popup.getMenuInflater().inflate(R.menu.delete_option, popup.getMenu());
            popup.show();
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {

                    switch (menuItem.getItemId()) {
                        case R.id.delete_item:
                           Utils.showToastMsg("Noe delete");
                            /*Intent intent= new Intent(MyApplication.getCurrentActivityContext(),MedicalHistoryEditDialogActivity.class);
                            intent.putExtra(ListenerKey.ObjectKey.MEDICAL_HISTORY_OBJECT_KEY,new Messenger(new AbstractIBinder(){
                                @Override
                                protected IntentObjectListener getMyObject() {
                                    return new IntentObjectListener(){

                                        @Override
                                        public Object getObject() {
                                            return data;
                            ,L9KKJ             }
                                    };
                                }
                            }));
                            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);

                            MyApplication.getCurrentActivityContext().startActivity(intent);
                          */  break;
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