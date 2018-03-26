
package com.vempower.eezyclinic.APICore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EditProfileViewCountry {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("countryName")
    @Expose
    private String countryName;
    @SerializedName("timeZone")
    @Expose
    private String timeZone;
    @SerializedName("currencyName")
    @Expose
    private String currencyName;
    @SerializedName("mobileCode")
    @Expose
    private String mobileCode;
    @SerializedName("mobileTotalDigits")
    @Expose
    private String mobileTotalDigits;
    @SerializedName("Country_ISO_3166")
    @Expose
    private String countryISO3166;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_date")
    @Expose
    private String createdDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getMobileCode() {
        return mobileCode;
    }

    public void setMobileCode(String mobileCode) {
        this.mobileCode = mobileCode;
    }

    public String getMobileTotalDigits() {
        return mobileTotalDigits;
    }

    public void setMobileTotalDigits(String mobileTotalDigits) {
        this.mobileTotalDigits = mobileTotalDigits;
    }

    public String getCountryISO3166() {
        return countryISO3166;
    }

    public void setCountryISO3166(String countryISO3166) {
        this.countryISO3166 = countryISO3166;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return countryName ;
    }
}
