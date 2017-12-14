
package com.vempower.eezyclinic.APICore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OtherBrnach {

    @SerializedName("branch_name")
    @Expose
    private String branchName;
    @SerializedName("total_recommendations")
    @Expose
    private String totalRecommendations;
    @SerializedName("overal_rating")
    @Expose
    private Integer overalRating;
    @SerializedName("number_of_doctors")
    @Expose
    private String numberOfDoctors;
    @SerializedName("insurance_accepted")
    @Expose
    private String insuranceAccepted;
    @SerializedName("Timings")
    @Expose
    private String timings;
    @SerializedName("address1")
    @Expose
    private String address1;
    @SerializedName("address2")
    @Expose
    private String address2;
    @SerializedName("address3")
    @Expose
    private String address3;
    @SerializedName("contact_numer")
    @Expose
    private String contactNumer;

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getTotalRecommendations() {
        return totalRecommendations;
    }

    public void setTotalRecommendations(String totalRecommendations) {
        this.totalRecommendations = totalRecommendations;
    }

    public Integer getOveralRating() {
        return overalRating;
    }

    public void setOveralRating(Integer overalRating) {
        this.overalRating = overalRating;
    }

    public String getNumberOfDoctors() {
        return numberOfDoctors;
    }

    public void setNumberOfDoctors(String numberOfDoctors) {
        this.numberOfDoctors = numberOfDoctors;
    }

    public String getInsuranceAccepted() {
        return insuranceAccepted;
    }

    public void setInsuranceAccepted(String insuranceAccepted) {
        this.insuranceAccepted = insuranceAccepted;
    }

    public String getTimings() {
        return timings;
    }

    public void setTimings(String timings) {
        this.timings = timings;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getContactNumer() {
        return contactNumer;
    }

    public void setContactNumer(String contactNumer) {
        this.contactNumer = contactNumer;
    }

}
