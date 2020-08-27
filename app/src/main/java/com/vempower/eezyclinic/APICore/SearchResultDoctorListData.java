
package com.vempower.eezyclinic.APICore;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchResultDoctorListData {

    @SerializedName("main_display_image")
    @Expose
    private String mainDisplayImage;
    @SerializedName("cityName")
    @Expose
    private String cityName;
    @SerializedName("consult_timings")
    @Expose
    private String consultTimings;
    @SerializedName("branch_name")
    @Expose
    private String branchName;
    @SerializedName("insurances_accepted")
    @Expose
    private String insurancesAccepted;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("languagesKnown")
    @Expose
    private String languagesKnown;
    @SerializedName("nationality")
    @Expose
    private String nationality;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("locality")
    @Expose
    private String locality;
    @SerializedName("doctorLogo")
    @Expose
    private String doctorLogo;
    @SerializedName("briefProfile")
    @Expose
    private String briefProfile;
    @SerializedName("consultancyFeeCurrency")
    @Expose
    private String consultancyFeeCurrency;
    @SerializedName("instantBooking")
    @Expose
    private String instantBooking;
    @SerializedName("teleconsultation")
    @Expose
    private String teleconsultation;
    @SerializedName("requestBooking")
    @Expose
    private String requestBooking;
    @SerializedName("primaryMobileNo")
    @Expose
    private String primaryMobileNo;
    @SerializedName("counsultancyPrice")
    @Expose
    private String counsultancyPrice;
    @SerializedName("branch_timings")
    @Expose
    private String branchTimings;
    @SerializedName("specalities")
    @Expose
    private String specalities;
    @SerializedName("docId")
    @Expose
    private String docId;
    @SerializedName("branchId")
    @Expose
    private String branchId;
    @SerializedName("clinicId")
    @Expose
    private String clinicId;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("google_map_latitude")
    @Expose
    private String googleMapLatitude;
    @SerializedName("google_map_longitude")
    @Expose
    private String googleMapLongitude;
    @SerializedName("DoctorName")
    @Expose
    private String doctorName;
    @SerializedName("doctorRecommendedCount")
    @Expose
    private String doctorRecommendedCount;
    @SerializedName("doctorTotalReviews")
    @Expose
    private String doctorTotalReviews;
    @SerializedName("clinic_name")
    @Expose
    private String clinicName;
    @SerializedName("licence_expiry_date")
    @Expose
    private String licenceExpiryDate;



   /* @SerializedName("doctorRecommendedCount")
    @Expose
    private String recommendations_count;*/

  /*  @SerializedName("doctorTotalReviews")
    @Expose
    private String reviews_count;
*/
    public String getMainDisplayImage() {
        return mainDisplayImage;
    }

    public void setMainDisplayImage(String mainDisplayImage) {
        this.mainDisplayImage = mainDisplayImage;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getConsultTimings() {
        return consultTimings;
    }

    public void setConsultTimings(String consultTimings) {
        this.consultTimings = consultTimings;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getInsurancesAccepted() {
        return insurancesAccepted;
    }

    public void setInsurancesAccepted(String insurancesAccepted) {
        this.insurancesAccepted = insurancesAccepted;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLanguagesKnown() {
        return languagesKnown;
    }

    public void setLanguagesKnown(String languagesKnown) {
        this.languagesKnown = languagesKnown;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getDoctorLogo() {
        return doctorLogo;
    }

    public void setDoctorLogo(String doctorLogo) {
        this.doctorLogo = doctorLogo;
    }

    public String getBriefProfile() {
        return briefProfile;
    }

    public void setBriefProfile(String briefProfile) {
        this.briefProfile = briefProfile;
    }

    public String getConsultancyFeeCurrency() {
        return consultancyFeeCurrency;
    }

    public void setConsultancyFeeCurrency(String consultancyFeeCurrency) {
        this.consultancyFeeCurrency = consultancyFeeCurrency;
    }

    public String getInstantBooking() {
        return instantBooking;
    }

    public void setInstantBooking(String instantBooking) {
        this.instantBooking = instantBooking;
    }

    public String getTeleconsultation() {
        return teleconsultation;
    }

    public void setTeleconsultation(String teleconsultation) {
        this.teleconsultation = teleconsultation;
    }

    public String getRequestBooking() {
        return requestBooking;
    }
    public void setRequestBooking(String requestBooking) {
        this.requestBooking = requestBooking;
    }

    public String getPrimaryMobileNo() {
        return primaryMobileNo;
    }

    public void setPrimaryMobileNo(String primaryMobileNo) {
        this.primaryMobileNo = primaryMobileNo;
    }

    public String getCounsultancyPrice() {
        return counsultancyPrice;
    }

    public void setCounsultancyPrice(String counsultancyPrice) {
        this.counsultancyPrice = counsultancyPrice;
    }

    public String getBranchTimings() {
        return branchTimings;
    }

    public void setBranchTimings(String branchTimings) {
        this.branchTimings = branchTimings;
    }

    public String getSpecalities() {
        return specalities;
    }

    public void setSpecalities(String specalities) {
        this.specalities = specalities;
    }

    public String getDocId() {
        if(docId==null)
        {
            docId="";
        }
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getClinicId() {
        return clinicId;
    }

    public void setClinicId(String clinicId) {
        this.clinicId = clinicId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGoogleMapLatitude() {
        return googleMapLatitude;
    }

    public void setGoogleMapLatitude(String googleMapLatitude) {
        this.googleMapLatitude = googleMapLatitude;
    }

    public String getGoogleMapLongitude() {
        return googleMapLongitude;
    }

    public void setGoogleMapLongitude(String googleMapLongitude) {
        this.googleMapLongitude = googleMapLongitude;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorRecommendedCount() {
        return doctorRecommendedCount;
    }

    public void setDoctorRecommendedCount(String doctorRecommendedCount) {
        this.doctorRecommendedCount = doctorRecommendedCount;
    }

    public String getDoctorTotalReviews() {
        return doctorTotalReviews;
    }

    public void setDoctorTotalReviews(String doctorTotalReviews) {
        this.doctorTotalReviews = doctorTotalReviews;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getLicenceExpiryDate() {
        return licenceExpiryDate;
    }

    public void setLicenceExpiryDate(String licenceExpiryDate) {
        this.licenceExpiryDate = licenceExpiryDate;
    }

  /*  public String getRecommendations_count() {
        return recommendations_count;
    }

    public String getReviews_count() {
        return reviews_count;
    }*/


    @Override
    public boolean equals(Object obj) {

        if(obj!=null && obj instanceof  SearchResultDoctorListData)
        {
            SearchResultDoctorListData data= (SearchResultDoctorListData) obj;
           if(data.getDocId().equalsIgnoreCase(getDocId()))
            {
                return true;
            }
        }

        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "SearchResultDoctorListData{" +
                "mainDisplayImage='" + mainDisplayImage + '\'' +
                ", cityName='" + cityName + '\'' +
                ", consultTimings='" + consultTimings + '\'' +
                ", branchName='" + branchName + '\'' +
                ", insurancesAccepted='" + insurancesAccepted + '\'' +
                ", gender='" + gender + '\'' +
                ", languagesKnown='" + languagesKnown + '\'' +
                ", nationality='" + nationality + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", locality='" + locality + '\'' +
                ", doctorLogo='" + doctorLogo + '\'' +
                ", briefProfile='" + briefProfile + '\'' +
                ", consultancyFeeCurrency='" + consultancyFeeCurrency + '\'' +
                ", instantBooking='" + instantBooking + '\'' +
                ", teleconsultation='" + teleconsultation + '\'' +
                ", primaryMobileNo='" + primaryMobileNo + '\'' +
                ", counsultancyPrice='" + counsultancyPrice + '\'' +
                ", branchTimings='" + branchTimings + '\'' +
                ", specalities='" + specalities + '\'' +
                ", docId='" + docId + '\'' +
                ", branchId='" + branchId + '\'' +
                ", clinicId='" + clinicId + '\'' +
                ", address='" + address + '\'' +
                ", googleMapLatitude='" + googleMapLatitude + '\'' +
                ", googleMapLongitude='" + googleMapLongitude + '\'' +
                ", doctorName='" + doctorName + '\'' +
                ", doctorRecommendedCount='" + doctorRecommendedCount + '\'' +
                ", doctorTotalReviews='" + doctorTotalReviews + '\'' +
                ", clinicName='" + clinicName + '\'' +
                ", licenceExpiryDate='" + licenceExpiryDate + '\'' +
                '}';
    }
}
