
package com.vempower.eezyclinic.APICore;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PatientProfileData {

    @SerializedName("patientlogo")
    @Expose
    private String patientlogo;
    @SerializedName("patientname")
    @Expose
    private String patientname;
    @SerializedName("patient_id")
    @Expose
    private String patientId;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("marital_status")
    @Expose
    private String maritalStatus;
    @SerializedName("age")
    @Expose
    private String age;
    @SerializedName("dateofbirth")
    @Expose
    private String dateofbirth;
    @SerializedName("contact_no")
    @Expose
    private PatientProfileContactNo contactNo;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("address")
    @Expose
    private PatientProfileAddress address;
    @SerializedName("languagesknown")
    @Expose
    private String languagesknown;
    @SerializedName("nationality")
    @Expose
    private String nationality;
    @SerializedName("idtype")
    @Expose
    private String idtype;
    @SerializedName("idnumber")
    @Expose
    private String idnumber;
    @SerializedName("occupation")
    @Expose
    private String occupation;
    @SerializedName("organisation")
    @Expose
    private String organisation;
    @SerializedName("idcard_image_front")
    @Expose
    private String idcardImageFront;
    @SerializedName("idcard_image_back")
    @Expose
    private String idcardImageBack;
    @SerializedName("insurance_card_front")
    @Expose
    private String insuranceCardFront;
    @SerializedName("insurance_card_back")
    @Expose
    private String insuranceCardBack;
    @SerializedName("bloodgroup")
    @Expose
    private String bloodgroup;
    @SerializedName("height")
    @Expose
    private String height;
    @SerializedName("knownallergies")
    @Expose
    private String knownallergies;
    @SerializedName("tpa")
    @Expose
    private String tpa;
    @SerializedName("tpaid")
    @Expose
    private String tpaid;
    @SerializedName("insurance_provider")
    @Expose
    private String insuranceProvider;
    @SerializedName("insurance_id")
    @Expose
    private String insuranceId;
    @SerializedName("policy")
    @Expose
    private String policy;
    @SerializedName("policy_card_number")
    @Expose
    private String policyCardNumber;
    @SerializedName("memberid")
    @Expose
    private String memberid;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("scheme")
    @Expose
    private String scheme;
    @SerializedName("reason")
    @Expose
    private String reason;
    @SerializedName("copay")
    @Expose
    private String copay;
    @SerializedName("maxlimit")
    @Expose
    private String maxlimit;
    @SerializedName("validityfrom")
    @Expose
    private String validityfrom;
    @SerializedName("validityto")
    @Expose
    private String validityto;
    @SerializedName("secondaryinsurancepackage")
    @Expose
    private String secondaryinsurancepackage;
    @SerializedName("secondaryinsurancenumber")
    @Expose
    private String secondaryinsurancenumber;
    @SerializedName("emergencycontactname")
    @Expose
    private String emergencycontactname;
    @SerializedName("emergencycontactrelationship")
    @Expose
    private String emergencycontactrelationship;
    @SerializedName("emergencycontactemail")
    @Expose
    private String emergencycontactemail;
    @SerializedName("emergencycontactnumber")
    @Expose
    private String emergencycontactnumber;


    @SerializedName("patientinsurancedetails")
    @Expose
    private List<Patientinsurancedetail> patientinsurancedetails = null;








    @SerializedName("weight")
    @Expose
    private String weight;

    @SerializedName("currentweight")
    @Expose
    private String currentweight;


    public String getPatientlogo() {
        return patientlogo;
    }

    public void setPatientlogo(String patientlogo) {
        this.patientlogo = patientlogo;
    }

    public String getPatientname() {
        return patientname;
    }

    public void setPatientname(String patientname) {
        this.patientname = patientname;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(String dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public PatientProfileContactNo getContactNo() {
        if(contactNo==null)
        {
            contactNo= new PatientProfileContactNo();
        }
        return contactNo;
    }

    public void setContactNo(PatientProfileContactNo contactNo) {
        this.contactNo = contactNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public PatientProfileAddress getAddress() {
        if(address==null)
        {
            address= new PatientProfileAddress();
        }
        return address;
    }

    public void setAddress(PatientProfileAddress address) {
        this.address = address;
    }

    public String getLanguagesknown() {
        if(TextUtils.isEmpty(languagesknown))
        {
            return "";
        }
        languagesknown=languagesknown.replaceAll(",",", ");
        return languagesknown;
    }

    public void setLanguagesknown(String languagesknown) {
        this.languagesknown = languagesknown;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getIdtype() {
        return idtype;
    }

    public void setIdtype(String idtype) {
        this.idtype = idtype;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public String getIdcardImageFront() {
        return idcardImageFront;
    }

    public void setIdcardImageFront(String idcardImageFront) {
        this.idcardImageFront = idcardImageFront;
    }

    public String getIdcardImageBack() {
        return idcardImageBack;
    }

    public void setIdcardImageBack(String idcardImageBack) {
        this.idcardImageBack = idcardImageBack;
    }

    public String getInsuranceCardFront() {
        return insuranceCardFront;
    }

    public void setInsuranceCardFront(String insuranceCardFront) {
        this.insuranceCardFront = insuranceCardFront;
    }

    public String getInsuranceCardBack() {
        return insuranceCardBack;
    }

    public void setInsuranceCardBack(String insuranceCardBack) {
        this.insuranceCardBack = insuranceCardBack;
    }

    public String getBloodgroup() {
        return bloodgroup;
    }

    public void setBloodgroup(String bloodgroup) {
        this.bloodgroup = bloodgroup;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getKnownallergies() {
        return knownallergies;
    }

    public void setKnownallergies(String knownallergies) {
        this.knownallergies = knownallergies;
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

    public String getInsuranceProvider() {
        return insuranceProvider;
    }

    public void setInsuranceProvider(String insuranceProvider) {
        this.insuranceProvider = insuranceProvider;
    }

    public String getInsuranceId() {
        return insuranceId;
    }

    public void setInsuranceId(String insuranceId) {
        this.insuranceId = insuranceId;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public String getPolicyCardNumber() {
        return policyCardNumber;
    }

    public void setPolicyCardNumber(String policyCardNumber) {
        this.policyCardNumber = policyCardNumber;
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

    public String getCopay() {
        return copay;
    }

    public void setCopay(String copay) {
        this.copay = copay;
    }

    public String getMaxlimit() {
        return maxlimit;
    }

    public void setMaxlimit(String maxlimit) {
        this.maxlimit = maxlimit;
    }

    public String getValidityfrom() {
        return validityfrom;
    }

    public void setValidityfrom(String validityfrom) {
        this.validityfrom = validityfrom;
    }

    public String getValidityto() {
        return validityto;
    }

    public void setValidityto(String validityto) {
        this.validityto = validityto;
    }

    public String getSecondaryinsurancepackage() {
        return secondaryinsurancepackage;
    }

    public void setSecondaryinsurancepackage(String secondaryinsurancepackage) {
        this.secondaryinsurancepackage = secondaryinsurancepackage;
    }

    public String getSecondaryinsurancenumber() {
        return secondaryinsurancenumber;
    }

    public void setSecondaryinsurancenumber(String secondaryinsurancenumber) {
        this.secondaryinsurancenumber = secondaryinsurancenumber;
    }

    public String getEmergencycontactname() {
        return emergencycontactname;
    }

    public void setEmergencycontactname(String emergencycontactname) {
        this.emergencycontactname = emergencycontactname;
    }

    public String getEmergencycontactrelationship() {
        return emergencycontactrelationship;
    }

    public void setEmergencycontactrelationship(String emergencycontactrelationship) {
        this.emergencycontactrelationship = emergencycontactrelationship;
    }

    public String getEmergencycontactemail() {
        return emergencycontactemail;
    }

    public void setEmergencycontactemail(String emergencycontactemail) {
        this.emergencycontactemail = emergencycontactemail;
    }

    public String getEmergencycontactnumber() {
        return emergencycontactnumber;
    }

    public void setEmergencycontactnumber(String emergencycontactnumber) {
        this.emergencycontactnumber = emergencycontactnumber;
    }

    public String getWeight() {
        return weight;
    }

    public String getCurrentweight() {
        return currentweight;
    }


    public List<Patientinsurancedetail> getPatientinsurancedetails() {
        return patientinsurancedetails;
    }

    public void setPatientinsurancedetails(List<Patientinsurancedetail> patientinsurancedetails) {
        this.patientinsurancedetails = patientinsurancedetails;
    }

    @Override
    public String toString() {
        return "PatientProfileData{" +
                "patientlogo='" + patientlogo + '\'' +
                ", patientname='" + patientname + '\'' +
                ", patientId='" + patientId + '\'' +
                ", gender='" + gender + '\'' +
                ", maritalStatus='" + maritalStatus + '\'' +
                ", age='" + age + '\'' +
                ", dateofbirth='" + dateofbirth + '\'' +
                ", contactNo=" + contactNo +
                ", email='" + email + '\'' +
                ", address=" + address +
                ", languagesknown='" + languagesknown + '\'' +
                ", nationality='" + nationality + '\'' +
                ", idtype='" + idtype + '\'' +
                ", idnumber='" + idnumber + '\'' +
                ", occupation='" + occupation + '\'' +
                ", organisation='" + organisation + '\'' +
                ", idcardImageFront='" + idcardImageFront + '\'' +
                ", idcardImageBack='" + idcardImageBack + '\'' +
                ", insuranceCardFront='" + insuranceCardFront + '\'' +
                ", insuranceCardBack='" + insuranceCardBack + '\'' +
                ", bloodgroup='" + bloodgroup + '\'' +
                ", height='" + height + '\'' +
                ", knownallergies='" + knownallergies + '\'' +
                ", tpa='" + tpa + '\'' +
                ", tpaid='" + tpaid + '\'' +
                ", insuranceProvider='" + insuranceProvider + '\'' +
                ", insuranceId='" + insuranceId + '\'' +
                ", policy='" + policy + '\'' +
                ", policyCardNumber='" + policyCardNumber + '\'' +
                ", memberid='" + memberid + '\'' +
                ", type='" + type + '\'' +
                ", scheme='" + scheme + '\'' +
                ", reason='" + reason + '\'' +
                ", copay='" + copay + '\'' +
                ", maxlimit='" + maxlimit + '\'' +
                ", validityfrom='" + validityfrom + '\'' +
                ", validityto='" + validityto + '\'' +
                ", secondaryinsurancepackage='" + secondaryinsurancepackage + '\'' +
                ", secondaryinsurancenumber='" + secondaryinsurancenumber + '\'' +
                ", emergencycontactname='" + emergencycontactname + '\'' +
                ", emergencycontactrelationship='" + emergencycontactrelationship + '\'' +
                ", emergencycontactemail='" + emergencycontactemail + '\'' +
                ", emergencycontactnumber='" + emergencycontactnumber + '\'' +
                '}';
    }
}
