package com.vempower.eezyclinic.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate;
import com.appeaser.sublimepickerlibrary.helpers.SublimeOptions;
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;
import com.vempower.eezyclinic.APICore.HelathReportsData;
import com.vempower.eezyclinic.APICore.OtherImage;
import com.vempower.eezyclinic.APICore.PDFDetails;
import com.vempower.eezyclinic.APICore.PrescriptionAPIData;
import com.vempower.eezyclinic.APIResponce.AbstractResponse;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.interfaces.ApiErrorDialogInterface;
import com.vempower.eezyclinic.mappers.UpdateHealthRecordsMapper;
import com.vempower.eezyclinic.mappers.UploadHealthRecordsMapper;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.MyDownloadfFile;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.eezyclinic.views.MyEditTextBlackCursorRR;
import com.vempower.eezyclinic.views.MyTextViewRR;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by satish on 6/12/17.
 */

public class UpdatePrescriptionReportFragment extends ImageProcessFragment {

    private View fragmentView;
    private MyTextViewRR date_of_document_tv;
    private SelectedDate selectedDOBObj;
    private LinearLayout horigental_linear;
    private ArrayList<File> imagesList;
    private ArrayList<File> newImagesList;
    private ArrayList<File> imagesRemoveList;
    private LayoutInflater inflater;
    private AppCompatButton upload_document_bt;
    private MyEditTextBlackCursorRR doctor_name_et, clinic_name_et, other_details_et, document_name_et;
    private LinearLayout document_name_linear;
    private boolean isReport;
    private com.vempower.eezyclinic.APICore.PDFDetails PDFDetails;
    private String id;
    private SuccussUpdateListener succussUpdateListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.add_modify_prescription_layout, container, false);

        myInit();

        return fragmentView;
    }

    private void myInit() {
        imagesList = new ArrayList<>();
        imagesRemoveList= new ArrayList<>();
        newImagesList= new ArrayList<>();
        inflater = (LayoutInflater) MyApplication.getCurrentActivityContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        date_of_document_tv = getFragemtView().findViewById(R.id.date_of_document_tv);

        date_of_document_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDateOfBirthTextviewClick(mFragmentDOBCallback, selectedDOBObj, true);
            }
        });
        document_name_linear = getFragemtView().findViewById(R.id.document_name_linear);
        upload_document_bt = getFragemtView().findViewById(R.id.upload_document_bt);
        doctor_name_et = getFragemtView().findViewById(R.id.doctor_name_et);
        clinic_name_et = getFragemtView().findViewById(R.id.clinic_name_et);
        other_details_et = getFragemtView().findViewById(R.id.other_details_et);

        document_name_et = getFragemtView().findViewById(R.id.document_name_et);

        horigental_linear = getFragemtView().findViewById(R.id.horigental_linear);

        getFragemtView().findViewById(R.id.add_new_image_linear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageSourceDialogWithDrive(Constants.ImagePic.FROM_ADD_NEW_IMAGE);
            }
        });


        upload_document_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadButtonClick();
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (isReport && document_name_linear != null) {
            document_name_linear.setVisibility(View.VISIBLE);
        }

        setValuesToViews();
    }

    private void setValuesToViews() {
        if(PDFDetails!=null)
        {
            if(PDFDetails instanceof PrescriptionAPIData)
            {
                setValues((PrescriptionAPIData) PDFDetails);
              //  Utils.showToastMsg("PrescriptionAPIData");
            }else if(PDFDetails instanceof HelathReportsData)
            {
                setValues((HelathReportsData) PDFDetails);
               // Utils.showToastMsg("HelathReportsData");
            }
            return;
        }
    }

    private void uploadButtonClick() {
        if(TextUtils.isEmpty(id))
        {
            showAlertDialog("Alert", Utils.getStringFromResources(R.string.invalid_document_details_lbl), true);
            return;
        }

        String doctorName = doctor_name_et.getText().toString();
        if (TextUtils.isEmpty(doctorName)) {
            Utils.showToastMsg(Utils.getStringFromResources(R.string.please_enter_doctor_name_lbl));
            return;
        }

        String documentDate = null;//date_of_document_tv.getText().toString();
        if (selectedDOBObj != null) {
            SimpleDateFormat requestFormat = new SimpleDateFormat(Constants.REQUEST_DATE_FORMAT);
            documentDate = requestFormat.format(selectedDOBObj.getFirstDate().getTime());
        } else {
            Utils.showToastMsg(Utils.getStringFromResources(R.string.please_select_document_date_lbl));
            return;
        }
        if (TextUtils.isEmpty(documentDate)) {
            Utils.showToastMsg(Utils.getStringFromResources(R.string.please_select_document_date_lbl));
            return;
        }


        String clinicName = clinic_name_et.getText().toString();
        String otherDetails = other_details_et.getText().toString();
        String documentName = document_name_et.getText().toString();
        String removedFiles=getRemoveImagesFileNames();

        // int size = imagesList.size();

       /* ArrayList<File> fileList, String documentType,String documentDate,
                String doctorName, String clinicName, String otherDetails,
                String documentName*/
        UpdateHealthRecordsMapper recordsMapper= new UpdateHealthRecordsMapper(newImagesList, isReport ? "report" : "prescription", documentDate,
                doctorName, clinicName, otherDetails,
                documentName,id,removedFiles);

        recordsMapper.setOnUploadHealthRecordListener(new UpdateHealthRecordsMapper.UploadHealthRecordListener() {
            @Override
            public void uploadHealthRecord(AbstractResponse abstractResponse, String errorMessage) {
                if (!isValidResponse(abstractResponse, errorMessage)) {
                    String msg = errorMessage;
                    if (TextUtils.isEmpty(msg)) {
                        msg = Utils.getStringFromResources(R.string.unable_to_update_document_lbl);
                    }
                    showMyDialog("Alert", msg, new ApiErrorDialogInterface() {
                        @Override
                        public void onCloseClick() {
                            ((Activity) MyApplication.getCurrentActivityContext()).finish();
                        }

                        @Override
                        public void retryClick() {
                            uploadButtonClick();
                        }
                    });
                    return;
                }
                Utils.showToastMsg(abstractResponse.getStatusMessage());
                if(succussUpdateListener!=null)
                {
                    succussUpdateListener.status(true);
                }else {
                    getActivity().setResult(Activity.RESULT_OK);
                    getActivity().finish();
                }
            }
        });

    /*    UploadHealthRecordsMapper mapper = new UploadHealthRecordsMapper(imagesList, isReport ? "report" : "prescription", documentDate,
                doctorName, clinicName, otherDetails,
                documentName);

        mapper.setOnUploadHealthRecordListener(new UploadHealthRecordsMapper.UploadHealthRecordListener() {
            @Override
            public void uploadHealthRecord(AbstractResponse abstractResponse, String errorMessage) {
                if (!isValidResponse(abstractResponse, errorMessage)) {
                    String msg = errorMessage;
                    if (TextUtils.isEmpty(msg)) {
                        msg = Utils.getStringFromResources(R.string.unable_to_upload_document_lbl);
                    }
                    showMyDialog("Alert", msg, new ApiErrorDialogInterface() {
                        @Override
                        public void onCloseClick() {
                            ((Activity) MyApplication.getCurrentActivityContext()).finish();
                        }

                        @Override
                        public void retryClick() {
                            uploadButtonClick();
                        }
                    });
                    return;
                }
                Utils.showToastMsg(abstractResponse.getStatusMessage());
                ((Activity) MyApplication.getCurrentActivityContext()).finish();

            }
        });*/


    }

    public void setUploadDocumentType(boolean isReport) {
        this.isReport = isReport;
    }


    SublimePickerFragment.Callback mFragmentDOBCallback = new SublimePickerFragment.Callback() {
        @Override
        public void onCancelled() {
            // rlDateTimeRecurrenceInfo.setVisibility(View.GONE);
        }

        @Override
        public void onDateTimeRecurrenceSet(SelectedDate selectedDate1,
                                            int hourOfDay, int minute,
                                            SublimeRecurrencePicker.RecurrenceOption recurrenceOption,
                                            String recurrenceRule) {


            if (selectedDate1 == null || selectedDate1.getFirstDate() == null) {
                return;
            }
            selectedDOBObj = selectedDate1;
            Calendar selectedCal = selectedDate1.getFirstDate();

            //  String date = selectedCal.get(Calendar.YEAR) + "-" + (selectedCal.get(Calendar.MONTH) + 1) + "-" + selectedCal.get(Calendar.DAY_OF_MONTH);
            SimpleDateFormat format = new SimpleDateFormat(Constants.DISPLAY_DATE_FORMAT);


            date_of_document_tv.setText(format.format(selectedCal.getTime()));

        }
    };


    public void onDateOfBirthTextviewClick(final SublimePickerFragment.Callback callback, final SelectedDate selectedObj, boolean isDOB) {

        SublimePickerFragment pickerFrag = new SublimePickerFragment();
        pickerFrag.setCallback(callback);

        // Options
        Pair<Boolean, SublimeOptions> optionsPair = getOptions(selectedObj, isDOB);

        if (!optionsPair.first) { // If options are not valid
            // showToastMessage("No pickers activated");
            return;
        }

        // Valid options
        Bundle bundle = new Bundle();
        bundle.putParcelable("SUBLIME_OPTIONS", optionsPair.second);
        pickerFrag.setArguments(bundle);

        pickerFrag.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        pickerFrag.show(getChildFragmentManager(), "SUBLIME_PICKER");


    }


    Pair<Boolean, SublimeOptions> getOptions(SelectedDate selectedDate, boolean isDOB) {
        SublimeOptions options = new SublimeOptions();
        if (isDOB) {
            Calendar endCalendar = Calendar.getInstance();
            Calendar startCalendar = Calendar.getInstance();
            startCalendar.set(Calendar.YEAR, startCalendar.get(Calendar.YEAR) - 120);

            options.setDateRange(startCalendar.getTimeInMillis(), endCalendar.getTimeInMillis());
        }

        if (selectedDate != null) {
            options.setDateParams(selectedDate);
        }
        int displayOptions = SublimeOptions.ACTIVATE_DATE_PICKER;


        // if (rbDatePicker.getVisibility() == View.VISIBLE && rbDatePicker.isChecked()) {
        options.setPickerToShow(SublimeOptions.Picker.DATE_PICKER);
        /*} else if (rbTimePicker.getVisibility() == View.VISIBLE && rbTimePicker.isChecked()) {
            options.setPickerToShow(SublimeOptions.Picker.TIME_PICKER);
        } else if (rbRecurrencePicker.getVisibility() == View.VISIBLE && rbRecurrencePicker.isChecked()) {
            options.setPickerToShow(SublimeOptions.Picker.REPEAT_OPTION_PICKER);
        }
*/
        options.setDisplayOptions(displayOptions);

        // Enable/disable the date range selection feature
        //options.setCanPickDateRange(cbAllowDateRangeSelection.isChecked());

        // Example for setting date range:
        // Note that you can pass a date range as the initial date params
        // even if you have date-range selection disabled. In this case,
        // the user WILL be able to change date-range using the header
        // TextViews, but not using long-press.

        /*Calendar startCal = Calendar.getInstance();
        startCal.set(2016, 2, 4);
        Calendar endCal = Calendar.getInstance();
        endCal.set(2016, 2, 17);

        options.setDateParams(startCal, endCal);*/

        // If 'displayOptions' is zero, the chosen options are not valid
        return new Pair<>(displayOptions != 0 ? Boolean.TRUE : Boolean.FALSE, options);
    }


    @Override
    View getFragemtView() {
        return fragmentView;
    }

    private void setNewImageViewToList(final File file,boolean isExisting) {

        if (file == null) {
            return;
        }


        final View convertView = inflater
                .inflate(R.layout.add_image_to_linear_layout, null, false);

        ImageView profile_iv6 = convertView.findViewById(R.id.profile_iv6);
        ImageView close_iv = convertView.findViewById(R.id.close_iv);

        MyApplication.getInstance().setBitmapToImageview(R.drawable.profile_id_default_image, profile_iv6, file);

        close_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                horigental_linear.removeView(convertView);
                if(imagesList.remove(file))
                {
                    imagesRemoveList.add(file);
                }
            }
        });
        horigental_linear.addView(convertView);
        if(!isExisting) {
            newImagesList.add(file);
        }
        imagesList.add(file);
    }

    @Override
    protected void setImage(File file, int responseId) {
            setNewImageViewToList(file,false);

    }
    private void setNewPDFViewToList(final File file,boolean isExisting) {

        if (file == null) {
            return;
        }


        final View convertView = inflater
                .inflate(R.layout.add_image_to_linear_layout, null, false);

        ImageView profile_iv6 = convertView.findViewById(R.id.profile_iv6);
        ImageView close_iv = convertView.findViewById(R.id.close_iv);

        MyApplication.getInstance().setBitmapToImageviewBG(profile_iv6,R.drawable.pdf_icon);

        close_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                horigental_linear.removeView(convertView);
                imagesList.remove(file);
            }
        });
        horigental_linear.addView(convertView);

        if(!isExisting) {
            newImagesList.add(file);
        }
        //imagesList.add(file);
    }

    @Override
    protected void setPDFFile(File file, int responseId)
    {
        setNewPDFViewToList(file,false);
    }

    public void setPDFDetails(PDFDetails PDFDetails) {
        this.PDFDetails = PDFDetails;
    }

    private void setValues(HelathReportsData data) {

        if(data==null)
        {
            return;
        }
        this.id=data.getId();

        doctor_name_et.setText(data.getDoctorName());
        clinic_name_et.setText(data.getClinicName());
        other_details_et.setText(data.getOtherDetails());
        document_name_et.setText(data.getDocumentName());

        //   String dateStr=data.getDocumentDate();
        SimpleDateFormat format1 = new SimpleDateFormat(Constants.REQUEST_DATE_FORMAT);
        try {
            Calendar calendar= Calendar.getInstance();
            calendar.setTime(format1.parse(data.getDocumentDate()));
            selectedDOBObj= new SelectedDate(calendar);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat format = new SimpleDateFormat(Constants.DISPLAY_DATE_FORMAT);

        date_of_document_tv.setText(format.format(selectedDOBObj.getFirstDate().getTime()));



        if(data.getDocumentFile()!=null && data.getDocumentFile().getShowFile()!=null)
        {
            new DownloadPDF().execute(data.getDocumentFile().getShowFile());
        }

        if(data.getOtherImages()!=null && data.getOtherImages().size()>0)
        {
            for(OtherImage image:data.getOtherImages())
            {
                if(image==null)
                {
                    continue;
                }
                new DownloadPDF().execute(image.getShowFile());
            }
        }
        //setNewImageViewToList(final File file)
    }

    private void setValues(PrescriptionAPIData data) {

        if(data==null)
        {
            return;
        }
        this.id=data.getId();

       doctor_name_et.setText(data.getDoctorName());
       clinic_name_et.setText(data.getClinicName());
        other_details_et.setText(data.getOtherDetails());
        document_name_et.setText(data.getDocumentName());

     //   String dateStr=data.getDocumentDate();
        SimpleDateFormat format1 = new SimpleDateFormat(Constants.REQUEST_DATE_FORMAT);
        try {
            Calendar calendar= Calendar.getInstance();
            calendar.setTime(format1.parse(data.getDocumentDate()));
            selectedDOBObj= new SelectedDate(calendar);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat format = new SimpleDateFormat(Constants.DISPLAY_DATE_FORMAT);

        date_of_document_tv.setText(format.format(selectedDOBObj.getFirstDate().getTime()));



        if(data.getDocumentFile()!=null && data.getDocumentFile().getShowFile()!=null)
        {
            new DownloadPDF().execute(data.getDocumentFile().getShowFile());
        }

        if(data.getOtherImages()!=null && data.getOtherImages().size()>0)
        {
            for(OtherImage image:data.getOtherImages())
            {
                if(image==null)
                {
                    continue;
                }
                new DownloadPDF().execute(image.getShowFile());
            }
        }

    }

    public String getRemoveImagesFileNames() {
       String removeImages="";

       for(File file:imagesRemoveList)
       {
           if(file!=null)
           {
               removeImages=removeImages+file.getName()+",";
           }
       }

       return removeImages;
    }

    public interface SuccussUpdateListener
    {
        void status(boolean isSuccess);
    }

    public void setOnSuccuessUpdateListener(SuccussUpdateListener succussUpdateListener) {
        this.succussUpdateListener = succussUpdateListener;
    }

    private class DownloadPDF extends AsyncTask<String, Void, File> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            MyApplication.showTransparentDialog();
        }

        @Override
        protected File doInBackground(String... strings) {
            MyDownloadfFile myDownloadfFile = new MyDownloadfFile();
            //return Uri.fromFile(myDownloadfFile.downloadFile(strings[0]));
            return myDownloadfFile.downloadFile(strings[0]);
        }

        @Override
        protected void onPostExecute(File  file) {
            MyApplication.hideTransaprentDialog();
            super.onPostExecute(file);

            if(file==null)
            {
                return;
            }

           if(file.getName().contains(".pdf"))
           {
               setNewPDFViewToList(file,true) ;
           }else
           {
               setNewImageViewToList(file,true);
           }



            //showPDFInScreen(uri);
        }
    }
}
