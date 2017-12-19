
package com.vempower.eezyclinic.APICore;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClinicProfileData {

    @SerializedName("clinic_image")
    @Expose
    private String clinicImage;
    @SerializedName("clinic_name")
    @Expose
    private String clinicName;
    @SerializedName("branch_name")
    @Expose
    private String branchName;
    @SerializedName("number_of_doctors")
    @Expose
    private String numberOfDoctors;
    @SerializedName("recommendations")
    @Expose
    private String recommendations;
    @SerializedName("tot_rating")
    @Expose
    private Integer totRating;
    @SerializedName("interiorimages_clinic")
    @Expose
    private List<String> interiorimagesClinic = null;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("clinic_name_details")
    @Expose
    private String clinicNameDetails;
    @SerializedName("clinic_types")
    @Expose
    private String clinicTypes;
    @SerializedName("clinic_timings")
    @Expose
    private String clinicTimings;
    @SerializedName("clinic_doctors_count")
    @Expose
    private String clinicDoctorsCount;
    @SerializedName("clinic_insurance_accepted")
    @Expose
    private String clinicInsuranceAccepted;
    @SerializedName("clinic_address1")
    @Expose
    private String clinicAddress1;
    @SerializedName("clinic_address2")
    @Expose
    private String clinicAddress2;
    @SerializedName("clinic_address3")
    @Expose
    private String clinicAddress3;
    @SerializedName("clinic_contact_number")
    @Expose
    private String clinicContactNumber;
    @SerializedName("specializations")
    @Expose
    private List<String> specializations = null;
    @SerializedName("services")
    @Expose
    private List<String> services = null;
    @SerializedName("other_brnaches")
    @Expose
    private List<OtherBrnach> otherBrnaches = null;
    @SerializedName("doctors_list")
    @Expose
    private ArrayList<SearchResultDoctorListData> doctorsList = null;

    public String getClinicImage() {
        return clinicImage;
    }

    public void setClinicImage(String clinicImage) {
        this.clinicImage = clinicImage;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getNumberOfDoctors() {
        return numberOfDoctors;
    }

    public void setNumberOfDoctors(String numberOfDoctors) {
        this.numberOfDoctors = numberOfDoctors;
    }

    public String getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(String recommendations) {
        this.recommendations = recommendations;
    }

    public Integer getTotRating() {
        return totRating;
    }

    public void setTotRating(Integer totRating) {
        this.totRating = totRating;
    }

    public List<String> getInteriorimagesClinic() {
        return interiorimagesClinic;
    }

    public void setInteriorimagesClinic(List<String> interiorimagesClinic) {
        this.interiorimagesClinic = interiorimagesClinic;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getClinicNameDetails() {
        return clinicNameDetails;
    }

    public void setClinicNameDetails(String clinicNameDetails) {
        this.clinicNameDetails = clinicNameDetails;
    }

    public String getClinicTypes() {
        return clinicTypes;
    }

    public void setClinicTypes(String clinicTypes) {
        this.clinicTypes = clinicTypes;
    }

    public String getClinicTimings() {
        return clinicTimings;
    }

    public void setClinicTimings(String clinicTimings) {
        this.clinicTimings = clinicTimings;
    }

    public String getClinicDoctorsCount() {
        return clinicDoctorsCount;
    }

    public void setClinicDoctorsCount(String clinicDoctorsCount) {
        this.clinicDoctorsCount = clinicDoctorsCount;
    }

    public String getClinicInsuranceAccepted() {
        return clinicInsuranceAccepted;
    }

    public void setClinicInsuranceAccepted(String clinicInsuranceAccepted) {
        this.clinicInsuranceAccepted = clinicInsuranceAccepted;
    }

    public String getClinicAddress1() {
        return clinicAddress1;
    }

    public void setClinicAddress1(String clinicAddress1) {
        this.clinicAddress1 = clinicAddress1;
    }

    public String getClinicAddress2() {
        return clinicAddress2;
    }

    public void setClinicAddress2(String clinicAddress2) {
        this.clinicAddress2 = clinicAddress2;
    }

    public String getClinicAddress3() {
        return clinicAddress3;
    }

    public void setClinicAddress3(String clinicAddress3) {
        this.clinicAddress3 = clinicAddress3;
    }

    public String getClinicContactNumber() {
        return clinicContactNumber;
    }

    public void setClinicContactNumber(String clinicContactNumber) {
        this.clinicContactNumber = clinicContactNumber;
    }

    public String getSpecializations() {
        String spe="";
        if(specializations==null)
        {
            return spe;
        }
        for(String str:specializations)
        {
            spe=spe+str+",";
        }
        return spe;
    }

    public void setSpecializations(List<String> specializations) {
        this.specializations = specializations;
    }

    public String getServices() {
        String ser="";
        if(services==null)
        {
            return ser;
        }
        for(String str:services)
        {
            ser=ser+str+",";
        }
        return ser;
    }

    public void setServices(List<String> services) {
        this.services = services;
    }

    public List<OtherBrnach> getOtherBrnaches() {
        return otherBrnaches;
    }

    public void setOtherBrnaches(List<OtherBrnach> otherBrnaches) {
        this.otherBrnaches = otherBrnaches;
    }

    public ArrayList<SearchResultDoctorListData> getDoctorsList() {
        return doctorsList;
    }

    public void setDoctorsList(ArrayList<SearchResultDoctorListData> doctorsList) {
        this.doctorsList = doctorsList;
    }

}
