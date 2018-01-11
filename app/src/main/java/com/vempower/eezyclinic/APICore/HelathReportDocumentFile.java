
package com.vempower.eezyclinic.APICore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HelathReportDocumentFile {

    @SerializedName("show_file")
    @Expose
    private String showFile;
    @SerializedName("download_file")
    @Expose
    private String downloadFile;

    public String getShowFile() {
        return showFile;
    }

    public void setShowFile(String showFile) {
        this.showFile = showFile;
    }

    public String getDownloadFile() {
        return downloadFile;
    }

    public void setDownloadFile(String downloadFile) {
        this.downloadFile = downloadFile;
    }

}
