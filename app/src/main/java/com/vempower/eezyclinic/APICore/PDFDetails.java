package com.vempower.eezyclinic.APICore;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by satish on 17/1/18.
 */

public class PDFDetails {

    @SerializedName("documentType")
    @Expose
    private String documentType;

    @SerializedName("dowloadzip")
    @Expose
    private String dowloadzip;
    @SerializedName("printpdf")
    @Expose
    private String printpdf;


    public String getDowloadzip() {
        return dowloadzip;
    }

    public void setDowloadzip(String dowloadzip) {
        this.dowloadzip = dowloadzip;
    }

    public String getPrintpdf() {
        if(TextUtils.isEmpty(printpdf))
        {
            return "http://unec.edu.az/application/uploads/2014/12/pdf-sample.pdf";
        }
        return printpdf;
    }

    public void setPrintpdf(String printpdf) {
        this.printpdf = printpdf;
    }


    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }


}
