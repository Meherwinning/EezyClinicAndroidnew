
package com.vempower.eezyclinic.APICore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PatientData {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("patientName")
    @Expose
    private String patientName;
    @SerializedName("patientLogo")
    @Expose
    private String patientLogo;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("middleName")
    @Expose
    private String middleName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("patientUniqueId")
    @Expose
    private String patientUniqueId;
    @SerializedName("patientSecondaryId")
    @Expose
    private String patientSecondaryId;
    @SerializedName("patentEmail")
    @Expose
    private String patentEmail;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("temporaryMobile")
    @Expose
    private String temporaryMobile;
    @SerializedName("mobileotp")
    @Expose
    private String mobileotp;
    @SerializedName("dateofBirth")
    @Expose
    private String dateofBirth;
    @SerializedName("patientAge")
    @Expose
    private String patientAge;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("maritial_status")
    @Expose
    private String maritialStatus;
    @SerializedName("secondarymobile")
    @Expose
    private String secondarymobile;
    @SerializedName("residencemobile")
    @Expose
    private String residencemobile;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("locality")
    @Expose
    private String locality;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("languagesKnown")
    @Expose
    private String languagesKnown;
    @SerializedName("nationality")
    @Expose
    private String nationality;
    @SerializedName("idType")
    @Expose
    private String idType;
    @SerializedName("idNumber")
    @Expose
    private String idNumber;
    @SerializedName("insurancePackage")
    @Expose
    private String insurancePackage;
    @SerializedName("insuranceNumber")
    @Expose
    private String insuranceNumber;

    @SerializedName("secondaryinsurancePackage")
    @Expose
    private String secondaryinsurancePackage;

    @SerializedName("secondaryinsuranceNumber")
    @Expose
    private String secondaryinsuranceNumber;
    @SerializedName("tpa")
    @Expose
    private String tpa;
    @SerializedName("tpaid")
    @Expose
    private String tpaid;
    @SerializedName("policy")
    @Expose
    private String policy;
    @SerializedName("policy_number")
    @Expose
    private String policyNumber;
    @SerializedName("memberid")
    @Expose
    private String memberid;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("fromvalidity")
    @Expose
    private String fromvalidity;
    @SerializedName("tovalidity")
    @Expose
    private String tovalidity;
    @SerializedName("scheme")
    @Expose
    private String scheme;
    @SerializedName("reason")
    @Expose
    private String reason;
    @SerializedName("organisation")
    @Expose
    private String organisation;
    @SerializedName("occupation")
    @Expose
    private String occupation;
    @SerializedName("maxlimit")
    @Expose
    private String maxlimit;
    @SerializedName("copay")
    @Expose
    private String copay;
    @SerializedName("bloodGroup")
    @Expose
    private String bloodGroup;
    @SerializedName("weight")
    @Expose
    private String weight;
    @SerializedName("height")
    @Expose
    private String height;
    @SerializedName("knownAllergies")
    @Expose
    private String knownAllergies;
    @SerializedName("emergencyContactName")
    @Expose
    private String emergencyContactName;
    @SerializedName("emergencyContactRelationship")
    @Expose
    private String emergencyContactRelationship;
    @SerializedName("emergencyContactNumber")
    @Expose
    private String emergencyContactNumber;
    @SerializedName("emergencyContactEmail")
    @Expose
    private Object emergencyContactEmail;
    @SerializedName("myHealthGoal")
    @Expose
    private String myHealthGoal;
    @SerializedName("mynotes")
    @Expose
    private String mynotes;
    @SerializedName("facebook_id")
    @Expose
    private String facebookId;
    @SerializedName("google_id")
    @Expose
    private String googleId;
    @SerializedName("twitter_id")
    @Expose
    private String twitterId;
    @SerializedName("linked_id")
    @Expose
    private String linkedId;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientLogo() {
        return patientLogo;
    }

    public void setPatientLogo(String patientLogo) {
        this.patientLogo = patientLogo;
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

    public String getPatientUniqueId() {
        return patientUniqueId;
    }

    public void setPatientUniqueId(String patientUniqueId) {
        this.patientUniqueId = patientUniqueId;
    }

    public String getPatientSecondaryId() {
        return patientSecondaryId;
    }

    public void setPatientSecondaryId(String patientSecondaryId) {
        this.patientSecondaryId = patientSecondaryId;
    }

    public String getPatentEmail() {
        return patentEmail;
    }

    public void setPatentEmail(String patentEmail) {
        this.patentEmail = patentEmail;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTemporaryMobile() {
        return temporaryMobile;
    }

    public void setTemporaryMobile(String temporaryMobile) {
        this.temporaryMobile = temporaryMobile;
    }

    public String getMobileotp() {
        return mobileotp;
    }

    public void setMobileotp(String mobileotp) {
        this.mobileotp = mobileotp;
    }

    public String getDateofBirth() {
        return dateofBirth;
    }

    public void setDateofBirth(String dateofBirth) {
        this.dateofBirth = dateofBirth;
    }

    public String getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(String patientAge) {
        this.patientAge = patientAge;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMaritialStatus() {
        return maritialStatus;
    }

    public void setMaritialStatus(String maritialStatus) {
        this.maritialStatus = maritialStatus;
    }

    public String getSecondarymobile() {
        return secondarymobile;
    }

    public void setSecondarymobile(String secondarymobile) {
        this.secondarymobile = secondarymobile;
    }

    public String getResidencemobile() {
        return residencemobile;
    }

    public void setResidencemobile(String residencemobile) {
        this.residencemobile = residencemobile;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getInsurancePackage() {
        return insurancePackage;
    }

    public void setInsurancePackage(String insurancePackage) {
        this.insurancePackage = insurancePackage;
    }

    public String getInsuranceNumber() {
        return insuranceNumber;
    }

    public void setInsuranceNumber(String insuranceNumber) {
        this.insuranceNumber = insuranceNumber;
    }

    public String getSecondaryinsurancePackage() {
        return secondaryinsurancePackage;
    }

    public void setSecondaryinsurancePackage(String secondaryinsurancePackage) {
        this.secondaryinsurancePackage = secondaryinsurancePackage;
    }

    public String getSecondaryinsuranceNumber() {
        return secondaryinsuranceNumber;
    }

    public void setSecondaryinsuranceNumber(String secondaryinsuranceNumber) {
        this.secondaryinsuranceNumber = secondaryinsuranceNumber;
    }

    public String getTpa() {
        return tpa;
    }

    public void setTpa(String tpa) {
        this.tpa = tpa;
    }

    public String getTpaid() {
        return tpaid;
    }

    public void setTpaid(String tpaid) {
        this.tpaid = tpaid;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFromvalidity() {
        return fromvalidity;
    }

    public void setFromvalidity(String fromvalidity) {
        this.fromvalidity = fromvalidity;
    }

    public String getTovalidity() {
        return tovalidity;
    }

    public void setTovalidity(String tovalidity) {
        this.tovalidity = tovalidity;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getMaxlimit() {
        return maxlimit;
    }

    public void setMaxlimit(String maxlimit) {
        this.maxlimit = maxlimit;
    }

    public String getCopay() {
        return copay;
    }

    public void setCopay(String copay) {
        this.copay = copay;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getKnownAllergies() {
        return knownAllergies;
    }

    public void setKnownAllergies(String knownAllergies) {
        this.knownAllergies = knownAllergies;
    }

    public String getEmergencyContactName() {
        return emergencyContactName;
    }

    public void setEmergencyContactName(String emergencyContactName) {
        this.emergencyContactName = emergencyContactName;
    }

    public String getEmergencyContactRelationship() {
        return emergencyContactRelationship;
    }

    public void setEmergencyContactRelationship(String emergencyContactRelationship) {
        this.emergencyContactRelationship = emergencyContactRelationship;
    }

    public String getEmergencyContactNumber() {
        return emergencyContactNumber;
    }

    public void setEmergencyContactNumber(String emergencyContactNumber) {
        this.emergencyContactNumber = emergencyContactNumber;
    }

    public Object getEmergencyContactEmail() {
        return emergencyContactEmail;
    }

    public void setEmergencyContactEmail(Object emergencyContactEmail) {
        this.emergencyContactEmail = emergencyContactEmail;
    }

    public String getMyHealthGoal() {
        return myHealthGoal;
    }

    public void setMyHealthGoal(String myHealthGoal) {
        this.myHealthGoal = myHealthGoal;
    }

    public String getMynotes() {
        return mynotes;
    }

    public void setMynotes(String mynotes) {
        this.mynotes = mynotes;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getTwitterId() {
        return twitterId;
    }

    public void setTwitterId(String twitterId) {
        this.twitterId = twitterId;
    }

    public String getLinkedId() {
        return linkedId;
    }

    public void setLinkedId(String linkedId) {
        this.linkedId = linkedId;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
