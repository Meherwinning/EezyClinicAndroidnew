
package com.vempower.eezyclinic.APICore;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DoctorProfileData {

    @SerializedName("doctor_logo")
    @Expose
    private String doctorLogo;
    @SerializedName("doctorfullname")
    @Expose
    private String doctorfullname;
    @SerializedName("doctorsdegrees")
    @Expose
    private String doctorsdegrees;
    @SerializedName("doctorspecalities")
    @Expose
    private String doctorspecalities;
    @SerializedName("doctorexperience")
    @Expose
    private String doctorexperience;
    @SerializedName("totalrecommend")
    @Expose
    private String totalrecommend;
    @SerializedName("doctoroverallrating")
    @Expose
    private String doctoroverallrating;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("clinicname")
    @Expose
    private String clinicname;
    @SerializedName("doctorlogolrg")
    @Expose
    private String doctorlogolrg;
    @SerializedName("interiorimages_clinic")
    @Expose
    private List<String> interiorimagesClinic = null;
    @SerializedName("doctors_profile")
    @Expose
    private DoctorsProfile doctorsProfile;
    @SerializedName("specializations")
    @Expose
    private List<String> specializations = null;
    @SerializedName("services")
    @Expose
    private List<String> services = null;
    @SerializedName("educational_qualifications")
    @Expose
    private String educationalQualifications;
    @SerializedName("experience")
    @Expose
    private List<String> experience = null;
    @SerializedName("awards_recognitions")
    @Expose
    private List<Object> awardsRecognitions = null;
    @SerializedName("memberships")
    @Expose
    private List<Object> memberships = null;
    @SerializedName("registrations")
    @Expose
    private Object registrations;
    @SerializedName("clinicdetails")
    @Expose
    private DoctorClinicdetails clinicdetails;
    @SerializedName("reviews")
    @Expose
    private List<DoctorReview> reviews = null;

    public String getDoctorLogo() {
        return doctorLogo;
    }

    public void setDoctorLogo(String doctorLogo) {
        this.doctorLogo = doctorLogo;
    }

    public String getDoctorfullname() {
        return doctorfullname;
    }

    public void setDoctorfullname(String doctorfullname) {
        this.doctorfullname = doctorfullname;
    }

    public String getDoctorsdegrees() {
        return doctorsdegrees;
    }

    public void setDoctorsdegrees(String doctorsdegrees) {
        this.doctorsdegrees = doctorsdegrees;
    }

    public String getDoctorspecalities() {
        return doctorspecalities;
    }

    public void setDoctorspecalities(String doctorspecalities) {
        this.doctorspecalities = doctorspecalities;
    }

    public String getDoctorexperience() {
        return doctorexperience;
    }

    public void setDoctorexperience(String doctorexperience) {
        this.doctorexperience = doctorexperience;
    }

    public String getTotalrecommend() {
        return totalrecommend;
    }

    public void setTotalrecommend(String totalrecommend) {
        this.totalrecommend = totalrecommend;
    }

    public String getDoctoroverallrating() {
        return doctoroverallrating;
    }

    public void setDoctoroverallrating(String doctoroverallrating) {
        this.doctoroverallrating = doctoroverallrating;
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

    public String getClinicname() {
        return clinicname;
    }

    public void setClinicname(String clinicname) {
        this.clinicname = clinicname;
    }

    public String getDoctorlogolrg() {
        return doctorlogolrg;
    }

    public void setDoctorlogolrg(String doctorlogolrg) {
        this.doctorlogolrg = doctorlogolrg;
    }

    public List<String> getInteriorimagesClinic() {
        return interiorimagesClinic;
    }

    public void setInteriorimagesClinic(List<String> interiorimagesClinic) {
        this.interiorimagesClinic = interiorimagesClinic;
    }

    public DoctorsProfile getDoctorsProfile() {
        return doctorsProfile;
    }

    public void setDoctorsProfile(DoctorsProfile doctorsProfile) {
        this.doctorsProfile = doctorsProfile;
    }

    public List<String> getSpecializations() {
        return specializations;
    }

    public void setSpecializations(List<String> specializations) {
        this.specializations = specializations;
    }

    public List<String> getServices() {
        return services;
    }

    public void setServices(List<String> services) {
        this.services = services;
    }

    public String getEducationalQualifications() {
        return educationalQualifications;
    }

    public void setEducationalQualifications(String educationalQualifications) {
        this.educationalQualifications = educationalQualifications;
    }

    public List<String> getExperience() {
        return experience;
    }

    public void setExperience(List<String> experience) {
        this.experience = experience;
    }

    public List<Object> getAwardsRecognitions() {
        return awardsRecognitions;
    }

    public void setAwardsRecognitions(List<Object> awardsRecognitions) {
        this.awardsRecognitions = awardsRecognitions;
    }

    public List<Object> getMemberships() {
        return memberships;
    }

    public void setMemberships(List<Object> memberships) {
        this.memberships = memberships;
    }

    public Object getRegistrations() {
        return registrations;
    }

    public void setRegistrations(Object registrations) {
        this.registrations = registrations;
    }

    public DoctorClinicdetails getClinicdetails() {
        return clinicdetails;
    }

    public void setClinicdetails(DoctorClinicdetails clinicdetails) {
        this.clinicdetails = clinicdetails;
    }

    public List<DoctorReview> getReviews() {
        return reviews;
    }

    public void setReviews(List<DoctorReview> reviews) {
        this.reviews = reviews;
    }

}
