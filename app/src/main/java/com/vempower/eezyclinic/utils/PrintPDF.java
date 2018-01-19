package com.vempower.eezyclinic.utils;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.print.PrintAttributes;
import android.support.annotation.RequiresApi;

import com.hp.mss.hpprint.activity.PrintPluginManagerActivity;
import com.hp.mss.hpprint.model.PDFPrintItem;
import com.hp.mss.hpprint.model.PrintItem;
import com.hp.mss.hpprint.model.PrintJobData;
import com.hp.mss.hpprint.model.asset.PDFAsset;
import com.hp.mss.hpprint.util.PrintUtil;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.stashdealcustomer.activities.AbstractActivity;

import static com.hp.mss.hpprint.util.PrintUtil.mediaSize5x7;

/*import com.hp.mss.hpprint.activity.PrintPluginManagerActivity;
import com.hp.mss.hpprint.model.PDFPrintItem;
import com.hp.mss.hpprint.model.PrintItem;
import com.hp.mss.hpprint.model.PrintJobData;
import com.hp.mss.hpprint.model.asset.PDFAsset;
import com.hp.mss.hpprint.util.PrintUtil;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.stashdealcustomer.activities.AbstractActivity;

import static com.hp.mss.hpprint.util.PrintUtil.mediaSize5x7;*/

/**
 * Created by satish on 17/1/18.
 */

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class PrintPDF {

    PrintItem.ScaleType scaleType;
    PrintAttributes.Margins margins;
    PrintJobData printJobData;

    private AbstractActivity activity;
    private final Uri fileUri;

    public PrintPDF(Uri fileUri) {
        this.fileUri=fileUri;

        activity=((AbstractActivity) MyApplication.getCurrentActivityContext());
        if(fileUri==null)
        {
            activity.showAlertDialog("Alert","Invalid print file,Please try again",false);
             return;
        }

        init();



    }


    public void printPreview()
    {
        printURI(fileUri);
    }

    private void init() {
        scaleType=PrintItem.ScaleType.FIT;
        margins = new PrintAttributes.Margins(100, 100, 100, 100);
   }

   private void printURI(Uri path)
    {


        PDFAsset pdfAsset = new PDFAsset(path,false);

        PrintItem printItem4x6 = new PDFPrintItem(PrintAttributes.MediaSize.NA_INDEX_4X6, margins, scaleType, pdfAsset);
        PrintItem printItem5x7 = new PDFPrintItem(mediaSize5x7, margins, scaleType, pdfAsset);
        PrintItem printItemLetter = new PDFPrintItem(PrintAttributes.MediaSize.NA_LETTER, margins, scaleType, pdfAsset);

        printJobData = new PrintJobData(activity, printItem4x6);

        printJobData.addPrintItem(printItemLetter);
        printJobData.addPrintItem(printItem5x7);



        createPrintJobData("Example");
        PrintUtil.setPrintJobData(printJobData);
        PrintUtil.customData.clear();
//        PrintUtil.sendPrintMetrics = showMetricsDialog;

        Intent intent = new Intent(activity, PrintPluginManagerActivity.class);
        activity.startActivity(intent);
        //PrintUtil.print(activity);

    }

    private void createPrintJobData(String jobNameStr) {
        printJobData.setJobName(jobNameStr);

        //Optionally include print attributes.
        PrintAttributes printAttributes = new PrintAttributes.Builder()
                .setMediaSize(PrintAttributes.MediaSize.NA_LETTER)
                .build();
        printJobData.setPrintDialogOptions(printAttributes);
    }

   /* private void createCustomData() {
        PrintUtil.customData.clear();
        if (showCustomData)
            PrintUtil.customData.put(tagText.getText(), valueText.getText());
    }*/
}
