
package com.vempower.eezyclinic.APICore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchResultClinicData {

    @SerializedName("main_display_image")
    @Expose
    private String mainDisplayImage;
    @SerializedName("locality")
    @Expose
    private String locality;
    @SerializedName("branch_name")
    @Expose
    private String branchName;
    @SerializedName("country")
    @Expose
    private Object country;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("clinicName")
    @Expose
    private String clinicName;
    @SerializedName("clinic_services")
    @Expose
    private String clinicServices;
    @SerializedName("clinic_specalities")
    @Expose
    private String clinicSpecalities;
    @SerializedName("clncId")
    @Expose
    private String clncId;
    @SerializedName("brcId")
    @Expose
    private String brcId;
    @SerializedName("cityName")
    @Expose
    private String cityName;
    @SerializedName("totalreviews")
    @Expose
    private String totalreviews;
    @SerializedName("totalrecommend")
    @Expose
    private String totalrecommend;
    @SerializedName("clinic_service")
    @Expose
    private String clinicService;




    @SerializedName("google_map_latitude")
    @Expose
    private String google_map_latitude;


    @SerializedName("google_map_longitude")
    @Expose
    private String google_map_longitude;



    public String getMainDisplayImage() {
        return mainDisplayImage;
    }

    public void setMainDisplayImage(String mainDisplayImage) {
        this.mainDisplayImage = mainDisplayImage;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Object getCountry() {
        return country;
    }

    public void setCountry(Object country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getClinicServices() {
        return clinicServices;
    }

    public void setClinicServices(String clinicServices) {
        this.clinicServices = clinicServices;
    }

    public String getClinicSpecalities() {
        return clinicSpecalities;
    }

    public void setClinicSpecalities(String clinicSpecalities) {
        this.clinicSpecalities = clinicSpecalities;
    }

    public String getClncId() {
        return clncId;
    }

    public void setClncId(String clncId) {
        this.clncId = clncId;
    }

    public String getBrcId() {
        return brcId;
    }

    public void setBrcId(String brcId) {
        this.brcId = brcId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getTotalreviews() {
        return totalreviews;
    }

    public void setTotalreviews(String totalreviews) {
        this.totalreviews = totalreviews;
    }

    public String getTotalrecommend() {
        return totalrecommend;
    }

    public void setTotalrecommend(String totalrecommend) {
        this.totalrecommend = totalrecommend;
    }

    public String getClinicService() {
        return clinicService;
    }

    public void setClinicService(String clinicService) {
        this.clinicService = clinicService;
    }


    public String getGoogle_map_latitude() {
        return google_map_latitude;
    }

    public String getGoogle_map_longitude() {
        return google_map_longitude;
    }
}
