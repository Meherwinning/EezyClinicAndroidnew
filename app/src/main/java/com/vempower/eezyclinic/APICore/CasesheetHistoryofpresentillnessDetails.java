
package com.vempower.eezyclinic.APICore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CasesheetHistoryofpresentillnessDetails {

    @SerializedName("historyofpresentillness")
    @Expose
    private String historyofpresentillness;

    public String getHistoryofpresentillness() {
        return historyofpresentillness;
    }

    public void setHistoryofpresentillness(String historyofpresentillness) {
        this.historyofpresentillness = historyofpresentillness;
    }

}
