package com.vempower.eezyclinic.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.vempower.eezyclinic.APICore.GetMyNotesData;
import com.vempower.eezyclinic.APIResponce.AbstractResponse;
import com.vempower.eezyclinic.APIResponce.GetMyNotesAPI;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.interfaces.ApiErrorDialogInterface;
import com.vempower.eezyclinic.mappers.GetMyNotesMapper;
import com.vempower.eezyclinic.mappers.SaveMyNotesMapper;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.eezyclinic.views.MyEditTextBlackCursor;

/**
 * Created by satish on 6/12/17.
 */

public class MyNotesListFragment extends AbstractFragment {

    private View fragmentView;
    private MyEditTextBlackCursor my_notes_et;
    private AppCompatButton save_note_bt;
    private GetMyNotesData myNotesData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.my_notes_list, container, false);


        myInit();
        return fragmentView;
    }

    private void myInit() {

        my_notes_et = getFragemtView().findViewById(R.id.my_notes_et);
        save_note_bt = getFragemtView().findViewById(R.id.save_note_bt);
        save_note_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myNotesData==null)
                {
                    showMyDialog("Alert", Utils.getStringFromResources(R.string.unable_to_get_my_notes_lbl), new ApiErrorDialogInterface() {
                        @Override
                        public void onCloseClick() {
                            ((Activity) MyApplication.getCurrentActivityContext()).finish();
                        }

                        @Override
                        public void retryClick() {
                            callGetMyNotesMapper();
                        }
                    });
                    return;
                }
                String myNewNotesStr = my_notes_et.getText().toString();
                callSaveMyNotesMapper(Integer.parseInt(myNotesData.getPatientId()),myNewNotesStr);


            }
        });

        callGetMyNotesMapper();
    }

    private void callSaveMyNotesMapper(final int patientId, final String myNewNotesStr) {

        SaveMyNotesMapper mapper= new SaveMyNotesMapper(patientId,myNewNotesStr);
        mapper.setOnSaveMyNotesAPIListener(new SaveMyNotesMapper.SaveMyNotesAPIListener() {
            @Override
            public void saveMyNotesAPI(AbstractResponse response, String errorMessage) {
                if ((!isValidResponse(response, errorMessage)) ) {
                    String msg = errorMessage;
                    if (TextUtils.isEmpty(msg)) {
                        msg = Utils.getStringFromResources(R.string.unable_to_save_my_notes_lbl);
                    }
                    showMyDialog("Alert", msg, new ApiErrorDialogInterface() {
                        @Override
                        public void onCloseClick() {
                            ((Activity) MyApplication.getCurrentActivityContext()).finish();
                        }

                        @Override
                        public void retryClick() {
                            callSaveMyNotesMapper( patientId,  myNewNotesStr);
                        }
                    });
                    return;
                }
                showAlertDialog("Success",R.string.success_save_notes_lbl,false);
            }
        });
    }

    private void callGetMyNotesMapper() {
        GetMyNotesMapper mapper = new GetMyNotesMapper();
        mapper.setOnGetMyNotesAPIListener(new GetMyNotesMapper.GetMyNotesAPIListener() {
            @Override
            public void getMyNotesAPI(GetMyNotesAPI myNotesAPI, String errorMessage) {
                if ((!isValidResponse(myNotesAPI, errorMessage)) ||  myNotesAPI.getData()==null) {
                    String msg = errorMessage;
                    if (TextUtils.isEmpty(msg)) {
                        msg = Utils.getStringFromResources(R.string.unable_to_get_my_notes_lbl);
                    }
                    showMyDialog("Alert", msg, new ApiErrorDialogInterface() {
                        @Override
                        public void onCloseClick() {
                            ((Activity) MyApplication.getCurrentActivityContext()).finish();
                        }

                        @Override
                        public void retryClick() {
                            callGetMyNotesMapper();
                        }
                    });
                    return;
                }
                 myNotesData= myNotesAPI.getData();
                if(myNotesData!=null) {
                    my_notes_et.setText(myNotesData.getMynotes());
                    my_notes_et.setSelection(my_notes_et.getText().toString().length());
                }


            }
        });
    }


    @Override
    View getFragemtView() {
        return fragmentView;
    }
}
