package com.vempower.eezyclinic.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.vempower.eezyclinic.APICore.CasesheetData;
import com.vempower.eezyclinic.APICore.CasesheetPrescriptionDetail;
import com.vempower.eezyclinic.APIResponce.CasesheetDetailsAPI;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.interfaces.ApiErrorDialogInterface;
import com.vempower.eezyclinic.mappers.CasesheetDetailsMapper;
import com.vempower.eezyclinic.utils.Utils;
 ;
import com.vempower.eezyclinic.activities.AbstractActivity;

/**
 * Created by satish on 6/12/17.
 */

public class CasesheetDetailFragment extends AbstractFragment {

    private View fragmentView;
    private String appointmentId;
    private LayoutInflater inflater;
    private  TextView casesheet_no_tv, create_date_tv, doctor_name_tv,
            doctor_designation_tv, clinic_name_tv, medical_history_tv, complaints_tv,
            history_of_presentilness_tv, temparature_tv, pulse_bpm_tv, rr_breath_unit_tv,
            bp_tv, weight_tv, systamatic_examination_tv, advised_investigation_tv, diagnosis_tv,
            treatment_plan_tv, followup_days_tv;
    private LinearLayout prescription_linear;

    private LinearLayout  download_bottom_linear,
            print_bottom_linear;

    private CasesheetData casesheetData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.casesheet_details_layout, container, false);

        myInit();

        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        compute();
    }

    private void compute() {
        callCasesheetsDetailsMapper();

    }

    private void myInit() {

        inflater = (LayoutInflater) MyApplication.getCurrentActivityContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        casesheet_no_tv = getFragemtView().findViewById(R.id.casesheet_no_tv);
        create_date_tv = getFragemtView().findViewById(R.id.create_date_tv);
        doctor_name_tv = getFragemtView().findViewById(R.id.doctor_name_tv);
        doctor_designation_tv = getFragemtView().findViewById(R.id.doctor_designation_tv);
        clinic_name_tv = getFragemtView().findViewById(R.id.clinic_name_tv);
        medical_history_tv = getFragemtView().findViewById(R.id.medical_history_tv);
        complaints_tv = getFragemtView().findViewById(R.id.complaints_tv);
        history_of_presentilness_tv = getFragemtView().findViewById(R.id.history_of_presentilness_tv);
        temparature_tv = getFragemtView().findViewById(R.id.temparature_tv);
        pulse_bpm_tv = getFragemtView().findViewById(R.id.pulse_bpm_tv);
        rr_breath_unit_tv = getFragemtView().findViewById(R.id.rr_breath_unit_tv);
        bp_tv = getFragemtView().findViewById(R.id.bp_tv);
        weight_tv = getFragemtView().findViewById(R.id.weight_tv);
        systamatic_examination_tv = getFragemtView().findViewById(R.id.systamatic_examination_tv);
        advised_investigation_tv = getFragemtView().findViewById(R.id.advised_investigation_tv);
        diagnosis_tv = getFragemtView().findViewById(R.id.diagnosis_tv);
        treatment_plan_tv = getFragemtView().findViewById(R.id.treatment_plan_tv);
        followup_days_tv = getFragemtView().findViewById(R.id.followup_days_tv);

        prescription_linear = getFragemtView().findViewById(R.id.prescription_linear);



        download_bottom_linear = getFragemtView().findViewById(R.id.download_bottom_linear);
        print_bottom_linear = getFragemtView().findViewById(R.id.print_bottom_linear);

        download_bottom_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(casesheetData==null || TextUtils.isEmpty(casesheetData.getPrintCopy()))
                {
                    Utils.showToastMsg(R.string.casesheet_print_copy_not_available_lbl);
                    return;
                }


                //if (casesheetData != null) {
                    downloadTaskStart(casesheetData.getPrintCopy());
               // }
            }
        });
        print_bottom_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(casesheetData==null || TextUtils.isEmpty(casesheetData.getPrintCopy()))
                {
                    Utils.showToastMsg(R.string.casesheet_print_copy_not_available_lbl);
                    return;
                }

                new DownloadPDFAsURI(new DownloadPDFAsURIListener() {
                    @Override
                    public void onComplete(Uri uri) {
                        if (uri != null) {
                            printPDF(uri);
                        }
                    }
                }).execute(casesheetData.getPrintCopy());

            }
        });

    }


    private View getPrescriptionView(CasesheetPrescriptionDetail  prescription) {

        if(prescription==null)
        {
            return null;
        }


        final View convertView = inflater
                .inflate(R.layout.casesheet_prescription_single_layout, null, false);

         TextView medicine_name_tv = convertView.findViewById(R.id.medicine_name_tv);
         TextView strength_tv = convertView.findViewById(R.id.strength_tv);
         TextView dosage_tv = convertView.findViewById(R.id.dosage_tv);
         TextView before_food_tv = convertView.findViewById(R.id.before_food_tv);
         TextView course_duration_tv = convertView.findViewById(R.id.course_duration_tv);

        medicine_name_tv.setText(prescription.getMedicineName());
        strength_tv.setText(prescription.getStrength());
        dosage_tv.setText(prescription.getDosage());
        before_food_tv.setText(prescription.getFood());
        course_duration_tv.setText(prescription.getDuration());


        return convertView;
    }


    private void callCasesheetsDetailsMapper() {
        if (TextUtils.isEmpty(appointmentId)) {
            return;
        }
        CasesheetDetailsMapper mapper = new CasesheetDetailsMapper(appointmentId);

        mapper.setOnCasesheetDetailsListener(new CasesheetDetailsMapper.CasesheetDetailsListener() {
            private CasesheetData casesheetDetails;


            @Override
            public void getCasesheetDetails(CasesheetDetailsAPI detailsAPI, String errorMessage) {
                if (!isValidResponse(detailsAPI, errorMessage)) {
                    showMyDialog("Alert", Utils.getStringFromResources(R.string.unable_to_get_casesheet_details_lbl), new ApiErrorDialogInterface() {
                        @Override
                        public void onCloseClick() {

                            ((AbstractActivity) MyApplication.getCurrentActivityContext()).finish();

                        }

                        @Override
                        public void retryClick() {
                            callCasesheetsDetailsMapper();
                        }
                    });
                    return;
                }

                if (detailsAPI.getData() == null) {
                    showAlertDialog("Alert", Utils.getStringFromResources(R.string.invalid_casesheet_details_), true);
                    return;
                }

                setCasesheetDetails(detailsAPI.getData());
            }
        });
    }

    public void setCasesheetDetails(CasesheetData details) {

        if (details == null) {
            return;
        }
        casesheetData=details;

        casesheet_no_tv.setText(details.getFactsheetUniqueId());
        create_date_tv.setText(details.getCreatedDate());
        doctor_name_tv .setText(details.getDoctorFullName());
        doctor_designation_tv .setText(details.getSpecalities());
        clinic_name_tv .setText(details.getClinicName());
        medical_history_tv .setText(details.getMedicalhistory());
        complaints_tv .setText(details.getComplaintDetails().getComplaint());
        history_of_presentilness_tv .setText(details.getHistoryofpresentillnessDetails().getHistoryofpresentillness());
        temparature_tv.setText(details.getTemparature());
        pulse_bpm_tv.setText(details.getPulse());
        rr_breath_unit_tv .setText(details.getRr());
        bp_tv.setText(details.getBp());
        weight_tv .setText(details.getWeight());
        systamatic_examination_tv.setText(details.getObservartions());
        advised_investigation_tv.setText(details.getInvestigationDetails().getInvestigations());
       // diagnosis_tv = getFragemtView().findViewById(R.id.diagnosis_tv);
        treatment_plan_tv .setText(details.getTreatmentDetails().getTreatment());
        followup_days_tv .setText(details.getFollowupDays());

        for(CasesheetPrescriptionDetail  prescription:details.getPrescriptionDetails())
        {
            if(prescription!=null)
            {
               View view= getPrescriptionView(prescription);

               if(view!=null)
               {
                   prescription_linear.addView(view);
               }
            }
        }


    }

    @Override
    View getFragemtView() {
        return fragmentView;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }
}
