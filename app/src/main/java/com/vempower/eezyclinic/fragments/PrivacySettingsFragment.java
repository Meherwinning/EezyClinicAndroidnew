package com.vempower.eezyclinic.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.Utils;
import com.rey.material.widget.LinearLayout;
import com.rey.material.widget.Switch;
import com.vempower.eezyclinic.APICore.ProfileSettingReport;
import com.vempower.eezyclinic.APICore.ProfileSettingsData;
import com.vempower.eezyclinic.APIResponce.AbstractResponse;
import com.vempower.eezyclinic.APIResponce.ProfileSettingsAPI;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.interfaces.ApiErrorDialogInterface;
import com.vempower.eezyclinic.mappers.GetProfileAllSettingsMapper;
import com.vempower.eezyclinic.mappers.SavePrivacySettingsMapper;
import com.vempower.stashdealcustomer.activities.AbstractActivity;

/**
 * Created by satish on 6/12/17.
 */

public class PrivacySettingsFragment extends AbstractFragment {

    private View fragmentView;
    private android.widget.LinearLayout upload_files_linear, doctor_genarated_linear,
            diagnostic_report_linear, patient_profile_linear, medical_history_linear, patient_notes_linear;
    private ExpandableLinearLayout expandableLayout_upload_files_el, expandableLayout_doctor_genarated_el,
            expandableLayout_diagnostic_report_el, expandableLayout_patient_profile_el, expandableLayout_medical_history_el, expandableLayout_patient_notes_el;
    private ScrollView myScrollView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.privacy_settings, container, false);

        myInit();

        return fragmentView;
    }

    private void myInit() {
        myScrollView = getFragemtView().findViewById(R.id.settings_scroll);

        upload_files_linear = getFragemtView().findViewById(R.id.upload_files_linear);
        expandableLayout_upload_files_el = getFragemtView().findViewById(R.id.expandableLayout_upload_files_el);

        doctor_genarated_linear = getFragemtView().findViewById(R.id.doctor_genarated_linear);
        expandableLayout_doctor_genarated_el = getFragemtView().findViewById(R.id.expandableLayout_doctor_genarated_el);

        diagnostic_report_linear = getFragemtView().findViewById(R.id.diagnostic_report_linear);
        expandableLayout_diagnostic_report_el = getFragemtView().findViewById(R.id.expandableLayout_diagnostic_report_el);

        patient_profile_linear = getFragemtView().findViewById(R.id.patient_profile_linear);
        expandableLayout_patient_profile_el = getFragemtView().findViewById(R.id.expandableLayout_patient_profile_el);

        medical_history_linear = getFragemtView().findViewById(R.id.medical_history_linear);
        expandableLayout_medical_history_el = getFragemtView().findViewById(R.id.expandableLayout_medical_history_el);

        patient_notes_linear = getFragemtView().findViewById(R.id.patient_notes_linear);
        expandableLayout_patient_notes_el = getFragemtView().findViewById(R.id.expandableLayout_patient_notes_el);
    }

    public void setSwitchListeners(ExpandableLinearLayout expandable, String moduleName, ProfileSettingReport report) {
        if(expandable==null)
        {
            return;
        }
      /*  "onlyappointmentdoctor":"0" ,
                "doctorswithinsameclinic":"1" ,
                "family":"1"*/

        setSwitchCheckedListener(expandable,R.id.doctors_i_seek_switch,moduleName, report.getOnlyappointmentdoctor());
        setSwitchCheckedListener(expandable,R.id.doctors_within_switch,moduleName,report.getDoctorswithinsameclinic());
        setSwitchCheckedListener(expandable,R.id.family_members_switch,moduleName,report.getFamily());
        /*((Switch) expandable.findViewById(R.id.doctors_i_seek_switch)).setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(Switch view, boolean checked) {

            }
        });*/
    }
    private void setSwitchCheckedListener(final ExpandableLinearLayout expandable,int id,final String moduleName,String isCheckedStr)
    {
       final Switch mSwitch= ((Switch) expandable.findViewById(id));
       mSwitch.setChecked(isCheckedStr.trim().equalsIgnoreCase("1")?true:false);
       final Switch.OnCheckedChangeListener changeListener = new Switch.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(Switch view, boolean checked) {
              //  com.vempower.eezyclinic.utils.Utils.showToastMessage("Module" + moduleName + "\nsubType" + subType + " :" + checked);
                saveSigleModuleSettingsMapper(moduleName, expandable, mSwitch, this);
            }
        };
        mSwitch.setOnCheckedChangeListener(changeListener);
    }

    private void saveSigleModuleSettingsMapper(String moduleName, ExpandableLinearLayout expandable, final Switch mSwitch, final Switch.OnCheckedChangeListener changeListener) {
        SavePrivacySettingsMapper mapper = new SavePrivacySettingsMapper(moduleName,
                ((Switch) expandable.findViewById(R.id.doctors_i_seek_switch)).isChecked(),
                ((Switch) expandable.findViewById(R.id.doctors_within_switch)).isChecked(),
                ((Switch) expandable.findViewById(R.id.family_members_switch)).isChecked()
        );

        mapper.setOnSavePrivacySettingsListener(new SavePrivacySettingsMapper.SavePrivacySettingsListener() {
            @Override
            public void savePrivacySettingsAPI(AbstractResponse response, String errorMessage) {
                if (!isValidResponse(response, errorMessage)) {
                    mSwitch.setOnCheckedChangeListener(null);
                    mSwitch.setChecked(!mSwitch.isChecked());
                    mSwitch.setOnCheckedChangeListener(changeListener);

                    return;
                }
                com.vempower.eezyclinic.utils.Utils.showToastMessage(response.getStatusMessage());
            }
        });
    }


    private void compute(ProfileSettingsData settingsData) {

        {
            setExpandedViewListener(expandableLayout_patient_notes_el, patient_notes_linear, R.id.patient_notes_iv);
            patient_notes_linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    expandableLayout_patient_notes_el.toggle();
                }
            });
            setSwitchListeners(expandableLayout_patient_notes_el,"patient_notes",settingsData.getPatientNotes());
        }

        {
            setExpandedViewListener(expandableLayout_medical_history_el, medical_history_linear, R.id.medical_history_iv);
            medical_history_linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    expandableLayout_medical_history_el.toggle();
                }
            });
            setSwitchListeners(expandableLayout_medical_history_el,"medical_history",settingsData.getMedicalHistory());
        }

        {
            setExpandedViewListener(expandableLayout_patient_profile_el, patient_profile_linear, R.id.patient_profile_iv);
            patient_profile_linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    expandableLayout_patient_profile_el.toggle();
                }
            });
            setSwitchListeners(expandableLayout_patient_profile_el,"patient_profile",settingsData.getPatientProfile());
        }

        {
            setExpandedViewListener(expandableLayout_diagnostic_report_el, diagnostic_report_linear, R.id.diagnostic_report_iv);
            diagnostic_report_linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    expandableLayout_diagnostic_report_el.toggle();
                }
            });
            setSwitchListeners(expandableLayout_diagnostic_report_el,"diagnosticreport",settingsData.getDiagnosticreport());
        }
        {
            setExpandedViewListener(expandableLayout_upload_files_el, upload_files_linear, R.id.contact_iv);
            upload_files_linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    expandableLayout_upload_files_el.toggle();
                }
            });
            setSwitchListeners(expandableLayout_upload_files_el,"myuploadfiles",settingsData.getMyuploadfiles());
        }
        {
            setExpandedViewListener(expandableLayout_doctor_genarated_el, doctor_genarated_linear, R.id.doctor_genarated_iv);
            doctor_genarated_linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    expandableLayout_doctor_genarated_el.toggle();
                }
            });
            setSwitchListeners(expandableLayout_doctor_genarated_el,"doctorreport",settingsData.getDoctorreport());
        }

        expandableLayout_upload_files_el.expand();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        callGetAllPrivacySettingsMapper();




    }

    private void callGetAllPrivacySettingsMapper() {
        GetProfileAllSettingsMapper profileAllSettingsMapper= new GetProfileAllSettingsMapper();
        profileAllSettingsMapper.setOnProfileSettingsListener(new GetProfileAllSettingsMapper.ProfileSettingsListener() {
            @Override
            public void getProfileSettings(ProfileSettingsAPI settingsAPI, String errorMessage) {
                if((!isValidResponse(settingsAPI,errorMessage))|| (settingsAPI.getData()==null)) {
                    showMyDialog("Alert", com.vempower.eezyclinic.utils.Utils.getStringFromResources(R.string.unable_to_get_privacySettings_lbl), new ApiErrorDialogInterface() {
                        @Override
                        public void onCloseClick() {

                            ((AbstractActivity) MyApplication.getCurrentActivityContext()).finish();

                        }

                        @Override
                        public void retryClick() {
                            callGetAllPrivacySettingsMapper();
                        }
                    });
                    return;
                }

                compute(settingsAPI.getData());



            }

        });
    }


    private void setExpandedViewListener(final ExpandableLinearLayout expandableLayout, final View view, int imageId) {
        expandableLayout.setInRecyclerView(false);
        //expandableLayout.setBackgroundColor(ContextCompat.getColor(this, item.colorId2));
        expandableLayout.setInterpolator(com.github.aakira.expandablelayout.Utils.createInterpolator(com.github.aakira.expandablelayout.Utils.LINEAR_OUT_SLOW_IN_INTERPOLATOR));
        //expandableLayout.setExpanded(expandState.get(position));
        expandableLayout.setExpanded(false);
        //expandableLayout.
        final ImageView imageView = view.findViewById(imageId);
        imageView.setBackgroundResource(R.drawable.plus_icon_grey);
        expandableLayout.setListener(new ExpandableLayoutListenerAdapter() {
            @Override
            public void onPreOpen() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //expandableLayout.setFocusable(true);
                        if (imageView != null) {
                            imageView.setBackgroundResource(R.drawable.minus_icon_grey);
                            //expandableLayout.getParent().requestChildFocus(expandableLayout,expandableLayout);
                            scrollToView(myScrollView, view);
                            //((ScrollView)getFragemtView().findViewById(R.id.scroll)).fullScroll(View.FOCUS_DOWN);

                        }
                    }
                }, 300);

            }

            @Override
            public void onPreClose() {
                if (imageView != null) {
                    imageView.setBackgroundResource(R.drawable.plus_icon_grey);
                }
            }
        });
    }


    @Override
    View getFragemtView() {
        return fragmentView;
    }


}
