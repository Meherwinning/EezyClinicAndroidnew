
package com.vempower.eezyclinic.APICore;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HealthChecksData {

    @SerializedName("bp")
    @Expose
    private List<HealthChecksBP> bp = null;
    @SerializedName("sugar")
    @Expose
    private List<HealthChecksSugar> sugar = null;
    @SerializedName("weight")
    @Expose
    private List<HealthChecksWeight> weight = null;
    @SerializedName("height")
    @Expose
    private List<HealthChecksHeight> height = null;
    @SerializedName("cholesterol")
    @Expose
    private List<HealthChecksCholesterol> cholesterol = null;
   /* @SerializedName("other_notes")
    @Expose
    private List<OtherNote> otherNotes = null;
*/
    public List<HealthChecksBP> getBp() {
        return bp;
    }

    public void setBp(List<HealthChecksBP> bp) {
        this.bp = bp;
    }

    public List<HealthChecksSugar> getSugar() {
        return sugar;
    }

    public void setSugar(List<HealthChecksSugar> sugar) {
        this.sugar = sugar;
    }

    public List<HealthChecksWeight> getWeight() {
        return weight;
    }

    public void setWeight(List<HealthChecksWeight> weight) {
        this.weight = weight;
    }

    public List<HealthChecksHeight> getHeight() {
        return height;
    }

    public void setHeight(List<HealthChecksHeight> height) {
        this.height = height;
    }

    public List<HealthChecksCholesterol> getCholesterol() {
        return cholesterol;
    }

    public void setCholesterol(List<HealthChecksCholesterol> cholesterol) {
        this.cholesterol = cholesterol;
    }

  /*  public List<OtherNote> getOtherNotes() {
        return otherNotes;
    }

    public void setOtherNotes(List<OtherNote> otherNotes) {
        this.otherNotes = otherNotes;
    }
*/
}
