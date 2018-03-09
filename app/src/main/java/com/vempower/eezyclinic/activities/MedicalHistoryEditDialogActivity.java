package com.vempower.eezyclinic.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.vempower.eezyclinic.APICore.MedicalHistoryData;
import com.vempower.eezyclinic.APICore.SearchResultDoctorListData;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.callbacks.ListenerKey;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.eezyclinic.views.MyEditTextBlackCursor;

import com.vempower.eezyclinic.activities.AbstractActivity;

import org.jetbrains.annotations.Nullable;

public class MedicalHistoryEditDialogActivity extends AbstractFragmentActivity {


    private MedicalHistoryData data;
    private MyEditTextBlackCursor medical_history_et;
    private TextView cancel_tv,save_tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medical_history_edit_layout);

        myInit();
    }

    private void myInit() {
        Object obj = getObjectFromIntent(getIntent(), ListenerKey.ObjectKey.MEDICAL_HISTORY_OBJECT_KEY);

        if (obj != null && obj instanceof MedicalHistoryData) {
            data = (MedicalHistoryData) obj;

            // showToastMessage("Data :" + data);
        } else {
            showMyAlertDialog("Alert", "Invalid Medical history details.Please try again", "Close", true);
            return;
        }

        if (data == null) {
            showMyAlertDialog("Alert", "Invalid Medical history details.Please try again", "Close", true);
            return;

        }

        medical_history_et = findViewById(R.id.medical_history_et);
        cancel_tv  = findViewById(R.id.cancel_tv);
        cancel_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        save_tv = findViewById(R.id.save_tv);

        medical_history_et.setText(data.getContent());
        medical_history_et.setSelection(medical_history_et.getText().toString().length());

        save_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.showToastMsg("Now click on Save");
            }
        });


    }
}
