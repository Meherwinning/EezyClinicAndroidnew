
package com.vempower.eezyclinic.APICore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewHomeDoctorsList  extends SearchResultDoctorListData {

    @SerializedName("branch_id")
    @Expose
    private String branchId;

    @SerializedName("id")
    @Expose
    public String id;

    @SerializedName("firstName")
    @Expose
    public String firstName;
    @SerializedName("middleName")
    @Expose
    public String middleName;
    @SerializedName("lastName")
    @Expose
    public String lastName;

    @SerializedName("dateOfBirth")
    @Expose
    public String dateOfBirth;


    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("password")
    @Expose
    public String password;
    @SerializedName("activation_code")
    @Expose
    public Object activationCode;

    @SerializedName("secondaryMobileNo")
    @Expose
    public String secondaryMobileNo;
    @SerializedName("residencePhoneNo")
    @Expose
    public String residencePhoneNo;

    @SerializedName("pincode")
    @Expose
    public String pincode;
    @SerializedName("houseNumber")
    @Expose
    public String houseNumber;
    @SerializedName("awardsAndAchivements")
    @Expose
    public String awardsAndAchivements;
    @SerializedName("experience")
    @Expose
    public String experience;
    @SerializedName("experienceDetails")
    @Expose
    public String experienceDetails;
     @SerializedName("servicesOffered")
    @Expose
    public String servicesOffered;
     @SerializedName("completeProfile")
    @Expose
    public String completeProfile;
     @SerializedName("allocated_branches")
    @Expose
    public String allocatedBranches;
    @SerializedName("doctor_identification_color")
    @Expose
    public String doctorIdentificationColor;

    @SerializedName("asFeatured")
    @Expose
    public String asFeatured;
    @SerializedName("first_login")
    @Expose
    public Object firstLogin;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("meta_title")
    @Expose
    public Object metaTitle;
    @SerializedName("meta_description")
    @Expose
    public Object metaDescription;
    @SerializedName("meta_keywords")
    @Expose
    public Object metaKeywords;
    @SerializedName("meta_image1")
    @Expose
    public Object metaImage1;
    @SerializedName("meta_image2")
    @Expose
    public Object metaImage2;
    @SerializedName("meta_image3")
    @Expose
    public Object metaImage3;
    @SerializedName("last_login")
    @Expose
    public String lastLogin;
    @SerializedName("dateAdeed")
    @Expose
    public String dateAdeed;
    @SerializedName("degreelist")
    @Expose
    public String degreelist;


    @Override
    public String getBranchId() {
        return branchId;
    }

    @Override
    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }



    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Object getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(Object activationCode) {
        this.activationCode = activationCode;
    }



    public String getSecondaryMobileNo() {
        return secondaryMobileNo;
    }

    public void setSecondaryMobileNo(String secondaryMobileNo) {
        this.secondaryMobileNo = secondaryMobileNo;
    }

    public String getResidencePhoneNo() {
        return residencePhoneNo;
    }

    public void setResidencePhoneNo(String residencePhoneNo) {
        this.residencePhoneNo = residencePhoneNo;
    }





    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getAwardsAndAchivements() {
        return awardsAndAchivements;
    }

    public void setAwardsAndAchivements(String awardsAndAchivements) {
        this.awardsAndAchivements = awardsAndAchivements;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getExperienceDetails() {
        return experienceDetails;
    }

    public void setExperienceDetails(String experienceDetails) {
        this.experienceDetails = experienceDetails;
    }


    public String getServicesOffered() {
        return servicesOffered;
    }

    public void setServicesOffered(String servicesOffered) {
        this.servicesOffered = servicesOffered;
    }


    public String getCompleteProfile() {
        return completeProfile;
    }

    public void setCompleteProfile(String completeProfile) {
        this.completeProfile = completeProfile;
    }


    public String getAllocatedBranches() {
        return allocatedBranches;
    }

    public void setAllocatedBranches(String allocatedBranches) {
        this.allocatedBranches = allocatedBranches;
    }

    public String getDoctorIdentificationColor() {
        return doctorIdentificationColor;
    }

    public void setDoctorIdentificationColor(String doctorIdentificationColor) {
        this.doctorIdentificationColor = doctorIdentificationColor;
    }


    public String getAsFeatured() {
        return asFeatured;
    }

    public void setAsFeatured(String asFeatured) {
        this.asFeatured = asFeatured;
    }

    public Object getFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(Object firstLogin) {
        this.firstLogin = firstLogin;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getMetaTitle() {
        return metaTitle;
    }

    public void setMetaTitle(Object metaTitle) {
        this.metaTitle = metaTitle;
    }

    public Object getMetaDescription() {
        return metaDescription;
    }

    public void setMetaDescription(Object metaDescription) {
        this.metaDescription = metaDescription;
    }

    public Object getMetaKeywords() {
        return metaKeywords;
    }

    public void setMetaKeywords(Object metaKeywords) {
        this.metaKeywords = metaKeywords;
    }

    public Object getMetaImage1() {
        return metaImage1;
    }

    public void setMetaImage1(Object metaImage1) {
        this.metaImage1 = metaImage1;
    }

    public Object getMetaImage2() {
        return metaImage2;
    }

    public void setMetaImage2(Object metaImage2) {
        this.metaImage2 = metaImage2;
    }

    public Object getMetaImage3() {
        return metaImage3;
    }

    public void setMetaImage3(Object metaImage3) {
        this.metaImage3 = metaImage3;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getDateAdeed() {
        return dateAdeed;
    }

    public void setDateAdeed(String dateAdeed) {
        this.dateAdeed = dateAdeed;
    }

    public String getDegreelist() {
        return degreelist;
    }

    public void setDegreelist(String degreelist) {
        this.degreelist = degreelist;
    }


}
