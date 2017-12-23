
package com.vempower.eezyclinic.APICore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppointmentTimeSlotsData {

    @SerializedName("first")
    @Expose
    private TimeSlot first;
    @SerializedName("second")
    @Expose
    private TimeSlot second;
    @SerializedName("third")
    @Expose
    private TimeSlot third;

    public TimeSlot getFirst() {
        return first;
    }

    public void setFirst(TimeSlot first) {
        this.first = first;
    }

    public TimeSlot getSecond() {
        return second;
    }

    public void setSecond(TimeSlot second) {
        this.second = second;
    }

    public TimeSlot getThird() {
        return third;
    }

    public void setThird(TimeSlot third) {
        this.third = third;
    }

}
