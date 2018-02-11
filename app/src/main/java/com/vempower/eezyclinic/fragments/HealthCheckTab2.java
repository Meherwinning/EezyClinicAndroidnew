package com.vempower.eezyclinic.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.vempower.eezyclinic.APICore.HealthChecksBP;
import com.vempower.eezyclinic.APICore.HealthChecksSugar;
import com.vempower.eezyclinic.APIResponce.AbstractResponse;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.interfaces.MyDialogInterface;
import com.vempower.eezyclinic.mappers.AddBPHealthCheckMapper;
import com.vempower.eezyclinic.mappers.AddSugarHealthCheckMapper;
import com.vempower.eezyclinic.mappers.DeleteSugarHealthCheckMapper;
import com.vempower.eezyclinic.mappers.UpdateBPHealthCheckMapper;
import com.vempower.eezyclinic.mappers.UpdateSugarHealthCheckMapper;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.eezyclinic.views.MyEditTextBlackCursorRR;
import com.vempower.eezyclinic.views.MyTextViewRR;

import java.util.List;

public class HealthCheckTab2 extends  AbstractHealthChecksTabFragment {


    public static final String TITLE = "Blood Pressure";
    private View fragmentView;
    private LinearLayout sugar_levels_linear, add_new_reocrd_linear, new_sugar_record_view_linear;
    private LayoutInflater inflater;
   // private List<HealthChecksSugar> sugarLevelsList;
    private MyEditTextBlackCursorRR new_diastolic_et,new_systolic_et;
    private MyTextViewRR new_date_tv;
    private ImageView new_delete_iv, new_ok_iv;
    private List<HealthChecksBP> BPList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.activity_checks_tab2, container, false);

        myInit();
        return fragmentView;
    }

    protected void myInit() {
        super.myInit();
        inflater = (LayoutInflater) MyApplication.getCurrentActivityContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        sugar_levels_linear = getFragemtView().findViewById(R.id.sugar_levels_linear);
        add_new_reocrd_linear = getFragemtView().findViewById(R.id.add_new_reocrd_linear);
        new_sugar_record_view_linear = getFragemtView().findViewById(R.id.new_sugar_record_view_linear);
        new_sugar_record_view_linear.setVisibility(View.GONE);
        add_new_reocrd_linear.setVisibility(View.VISIBLE);
        initForAddNewRecord();
        add_new_reocrd_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //calAddNewViewRecord();
                resetAddNewRecord();
                add_new_reocrd_linear.setVisibility(View.INVISIBLE);

                new_sugar_record_view_linear.setVisibility(View.VISIBLE);
              //  ImageView delete_iv = new_sugar_record_view_linear.findViewById(R.id.delete_iv);


            }
        });

    }

    private void initForAddNewRecord() {
        new_diastolic_et = getFragemtView().findViewById(R.id.diastolic_et);
        new_systolic_et = getFragemtView().findViewById(R.id.systolic_et);
        //new_hba1c_et = getFragemtView().findViewById(R.id.hba1c_et);
        new_date_tv = getFragemtView().findViewById(R.id.date_tv);

        new_ok_iv = getFragemtView().findViewById(R.id.ok_iv);
        new_delete_iv = getFragemtView().findViewById(R.id.delete_iv);


        new_ok_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String diastolic = new_diastolic_et.getText().toString();
                String systolic = new_systolic_et.getText().toString();
               // String hba1c = new_hba1c_et.getText().toString();
                String checkuptime = "2018-01-07";// new_date_tv.getText().toString();
                if (TextUtils.isEmpty(diastolic)) {
                    Utils.showToastMessage(R.string.please_enter_diastolic_value_lbl);
                    return;
                }
                if (TextUtils.isEmpty(systolic)) {
                    Utils.showToastMessage(R.string.please_enter_systolic_value_lbl);
                    return;
                }
               /* if (TextUtils.isEmpty(hba1c)) {
                    Utils.showToastMessage(R.string.please_enter_hba1c_value_lbl);
                    return;
                }*/
                // new_date_tv.getText().toString();
               /* String checkuptime="2018-01-07";
                HealthChecksSugar  sugar= new HealthChecksSugar();
                sugar.setCheckupValue(fasting+","+lunch+","+hba1c);
                sugar.setCheckupTime(checkuptime);
                sugar.setCheckupName("Sugar Level");

                return sugar;*/

                callAddNewBPHealthcheckRecord(diastolic, systolic, checkuptime);
            }
        });

        new_delete_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetAddNewRecord();
                new_sugar_record_view_linear.setVisibility(View.GONE);
                add_new_reocrd_linear.setVisibility(View.VISIBLE);
            }
        });
        resetAddNewRecord();
    }

    private void resetAddNewRecord() {
        new_diastolic_et.setText(null);
        new_systolic_et.setText(null);
       // new_hba1c_et.setText(null);
        new_date_tv.setText(null);
    }

    private void callAddNewBPHealthcheckRecord(final String diastolic, final String systolic,  final String checkuptime) {
        AddBPHealthCheckMapper mapper= new AddBPHealthCheckMapper(diastolic,systolic,checkuptime);

        mapper.setOnAddBPHealthCheckListener(new AddBPHealthCheckMapper.AddBPHealthCheckListener() {
            @Override
            public void addBP(AbstractResponse response, String errorMessage) {
                if (!isValidResponse(response, errorMessage, true, false)) {
                    return;
                }

                HealthChecksBP bp = new HealthChecksBP();
                bp.setCheckupName("BP");
                bp.setCheckupValue(diastolic + "," + systolic);
                bp.setCheckupTime(checkuptime);
                calAddViewRecord(bp, true);

                new_sugar_record_view_linear.setVisibility(View.GONE);
                add_new_reocrd_linear.setVisibility(View.VISIBLE);
            }
        });
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setSugarlevelViews();
    }

    private void setSugarlevelViews() {
        if (BPList == null || BPList.size() == 0) {
            return;
        }

        for (HealthChecksBP bp : BPList) {
            if (bp == null) {
                continue;
            }
            calAddViewRecord(bp, false);


        }

    }

    public void setBPList(List<HealthChecksBP> BPList) {
        this.BPList = BPList;
    }

    private class RecordHolder {
        MyEditTextBlackCursorRR diastolic_et, systolic_et;
        MyTextViewRR date_tv;
        ImageView ok_iv, edit_iv, delete_iv;
        private final HealthChecksBP bp;

        public RecordHolder(HealthChecksBP bp, View convertView) {
            this.bp = bp;

            diastolic_et = convertView.findViewById(R.id.diastolic_et);
            systolic_et = convertView.findViewById(R.id.systolic_et);
           // hba1c_et = convertView.findViewById(R.id.hba1c_et);
            date_tv = convertView.findViewById(R.id.date_tv);

            ok_iv = convertView.findViewById(R.id.ok_iv);
            edit_iv = convertView.findViewById(R.id.edit_iv);
            delete_iv = convertView.findViewById(R.id.delete_iv);

            init();
            setHealthChecksSugar();
        }

        private void init() {
            edit_iv.setVisibility(View.VISIBLE);
            ok_iv.setVisibility(View.GONE);
            delete_iv.setVisibility(View.VISIBLE);
        }

        public void setHealthChecksSugar() {
            if (bp == null) {
                return;
            }
            String[] splits = bp.getCheckupValue().split(",");
            String fasting = splits.length > 0 ? splits[0] : null;
            String postFasting = splits.length > 1 ? splits[1] : null;
            String hba1c = splits.length > 2 ? splits[2] : null;

            diastolic_et.setText(fasting);
            systolic_et.setText(postFasting);
           // hba1c_et.setText(hba1c);
            date_tv.setText(bp.getCheckupTime());
        }

        public void setViewState(boolean isEnabled) {
            diastolic_et.setEnabled(isEnabled);
            systolic_et.setEnabled(isEnabled);
           // hba1c_et.setEnabled(isEnabled);
            date_tv.setEnabled(isEnabled);

            if (isEnabled) {
                edit_iv.setVisibility(View.GONE);
                ok_iv.setVisibility(View.VISIBLE);
                delete_iv.setVisibility(View.VISIBLE);
            } else {
                edit_iv.setVisibility(View.VISIBLE);
                ok_iv.setVisibility(View.GONE);
                delete_iv.setVisibility(View.VISIBLE);
            }
        }

        public void onUpdateButtonClick()
        {
           /* fasting_et = convertView.findViewById(R.id.fasting_et);
            post_meal_et = convertView.findViewById(R.id.post_meal_et);
            hba1c_et = convertView.findViewById(R.id.hba1c_et);
            date_tv = convertView.findViewById(R.id.date_tv);
*/
            final String diastolic = diastolic_et.getText().toString();
            final String systolic = systolic_et.getText().toString();
            final String checkuptime = date_tv.getText().toString();
            if (TextUtils.isEmpty(diastolic)) {
                Utils.showToastMessage(R.string.please_enter_diastolic_value_lbl);
                return;
            }
            if (TextUtils.isEmpty(systolic)) {
                Utils.showToastMessage(R.string.please_enter_systolic_value_lbl);
                return;
            }

            if (TextUtils.isEmpty(checkuptime)) {
                Utils.showToastMessage(R.string.please_select_date_lbl);
                return;
            }

            UpdateBPHealthCheckMapper  mapper= new UpdateBPHealthCheckMapper(bp.getId(),  diastolic,  systolic,   checkuptime);

            mapper.setOnUpdateBPHealthCheckListener(new UpdateBPHealthCheckMapper.UpdateBPHealthCheckListener() {
                @Override
                public void updateBP(AbstractResponse response, String errorMessage) {
                    if(!isValidResponse(response,errorMessage,true,false))
                    {
                        return;
                    }
                    bp.setCheckupTime(checkuptime);
                    bp.setCheckupValue(diastolic + "," + systolic );
                    Utils.showToastMessage(response.getStatusMessage());
                    setViewState(false);
                }
            });
        }


    }

    private void calAddViewRecord(final HealthChecksBP bp, final boolean isfromAddNew) {

        final View convertView = inflater
                .inflate(R.layout.bp_single_item, null, false);
        final RecordHolder holder = new RecordHolder(bp, convertView);
        //holder.setHealthChecksSugar(sugar);
        holder.setViewState(false);


        holder.edit_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.setViewState(true);
            }
        });


        holder.ok_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // add_new_reocrd_linear.setVisibility(View.VISIBLE);
                holder.onUpdateButtonClick();

            }
        });
        holder.delete_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (holder.edit_iv.getVisibility() == View.GONE) {
                    holder.setViewState(false);
                    holder.setHealthChecksSugar();
                } else {
                    showMyCustomDialog("Alert", Utils.getStringFromResources(R.string.are_you_sure_to_delete_bp_record_lbl), "Yes", "No", new MyDialogInterface() {
                        @Override
                        public void onPossitiveClick()
                        {
                            int id=-1;
                            try
                            {
                                if(TextUtils.isEmpty(bp.getId()))
                                {
                                    Utils.showToastMessage(R.string.invalid_bp_record_details_lbl);
                                    return;
                                }
                                id= Integer.parseInt(bp.getId());
                            }catch (Exception e)
                            {
                                Utils.showToastMessage(R.string.invalid_bp_record_details_lbl);
                                return;
                            }
                            DeleteSugarHealthCheckMapper mapper= new DeleteSugarHealthCheckMapper(id);
                            mapper.setOnDeleteSugarHealthCheckListener(new DeleteSugarHealthCheckMapper.DeleteSugarHealthCheckListener() {
                                @Override
                                public void deleteSugar(AbstractResponse response, String errorMessage) {
                                    if(!isValidResponse(response,errorMessage,true,false))
                                    {
                                        return;
                                    }
                                    Utils.showToastMessage(response.getStatusMessage());
                                    sugar_levels_linear.removeView(convertView);
                                }
                            });
                        }

                        @Override
                        public void onNegetiveClick() {

                        }
                    });
                }


            }
        });

        if (isfromAddNew) {
            sugar_levels_linear.addView(convertView, 0);
        } else {
            sugar_levels_linear.addView(convertView);
        }


    }

    @Override
    int getHealthCheckType() {
        return BLOOD_PRESSURE_TYPE;
    }
    @Override
    String getHealthCheckTypeName() {
        return "Blood Pressure";
    }
    @Override
    View getFragemtView() {
        return fragmentView;
    }





}




