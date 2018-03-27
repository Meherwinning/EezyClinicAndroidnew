package com.vempower.eezyclinic.core;

/**
 * Created by satish on 3/1/18.
 */

public class EditProfileDetails {
   /*
    { "access_key": "360f19438d8cb884057f79a1a55df441",
            "patientName" : "Swathi k", "gender" : "Female",
            "maritial_status" : "married", "dateofBirth" : "01-01-1986",
            "mobile" : "9876543210", "secondarymobile" :"1234567890",
            "residencemobile" :"8965321025", "country" : "ind",
            "city" : "Hyd", "locality":"airport area", "address" : "begumpet",
            "languagesKnown" : "Telugu,Hindi", "nationality" :"Indian" ,
            "idType": "Aadhar Card", "idNumber": "343434534534536468",
            "occupation" : "emp1", "organisation" : "abc", "bloodGroup" : "O-" ,
            "height" : "5.6", "knownAllergies" : "test knownAllergies", "tpa": "1" ,
            "tpaid" : "TPA002", "insurancePackage" : "Ace Life" , "insuranceNumber" : "98745632102154" ,
            "policy" : "1", "policy_number" : "1236987456", "memberid" : "845632569",
            "type" : "1", "scheme" : "1", "reason" : "1", "copay" : "3" , "maxlimit" : "5",
            "fromvalidity" : "2017-01-02", "tovalidity" : "2017-08-02", "secondaryinsurancePackage" : "LIC",
            "secondaryinsuranceNumber" : "45568877654566879", "emergencyContactName" : "suresh" ,
            "emergencyContactRelationship" : "Brother", "emergencyContactNumber" : "7894561236",
            "emergencyContactEmail" : "suresh@gmail.com"
    }
    */

    public String patientName;
    public String gender;
    public String maritialStatus;
    public String dateofBirth;
    public String mobile;
    public String secondarymobile;
    public String residencemobile;
    public String country;
    public String city;
    public String locality;
    public String address;
    public String languagesKnown;
    public String nationality;
    public String idType;
    public String idNumber;
    public String occupation;
    public String organisation;
    public String bloodGroup;
    public String height;
    public String knownAllergies;
    public String tpa;
    public String tpaid;
    public String insurancePackage;
    public String insuranceNumber;
    public String policy;
    //public String policyNumber;
    public String memberid;
    public String type;
    public String scheme;
    public String reason;
    public String copay;
    public String maxlimit;
    public String fromvalidity;
    public String tovalidity;
    public String secondaryinsurancePackage;
    public String secondaryinsuranceNumber;
    public String emergencyContactName;
    public String emergencyContactRelationship;
    public String emergencyContactNumber;
    public String emergencyContactEmail;

    public String  policy_number;

    public String age;


    @Override
    public String toString() {
        return "EditProfileDetails{" +
                "patientName='" + patientName + '\'' +
                ", gender='" + gender + '\'' +
                ", maritialStatus='" + maritialStatus + '\'' +
                ", dateofBirth='" + dateofBirth + '\'' +
                ", mobile='" + mobile + '\'' +
                ", secondarymobile='" + secondarymobile + '\'' +
                ", residencemobile='" + residencemobile + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", locality='" + locality + '\'' +
                ", address='" + address + '\'' +
                ", languagesKnown='" + languagesKnown + '\'' +
                ", nationality='" + nationality + '\'' +
                ", idType='" + idType + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", occupation='" + occupation + '\'' +
                ", organisation='" + organisation + '\'' +
                ", bloodGroup='" + bloodGroup + '\'' +
                ", height='" + height + '\'' +
                ", knownAllergies='" + knownAllergies + '\'' +
                ", tpa='" + tpa + '\'' +
                ", tpaid='" + tpaid + '\'' +
                ", insurancePackage='" + insurancePackage + '\'' +
                ", insuranceNumber='" + insuranceNumber + '\'' +
                ", policy='" + policy + '\'' +

                ", memberid='" + memberid + '\'' +
                ", type='" + type + '\'' +
                ", scheme='" + scheme + '\'' +
                ", reason='" + reason + '\'' +
                ", copay='" + copay + '\'' +
                ", maxlimit='" + maxlimit + '\'' +
                ", fromvalidity='" + fromvalidity + '\'' +
                ", tovalidity='" + tovalidity + '\'' +
                ", secondaryinsurancePackage='" + secondaryinsurancePackage + '\'' +
                ", secondaryinsuranceNumber='" + secondaryinsuranceNumber + '\'' +
                ", emergencyContactName='" + emergencyContactName + '\'' +
                ", emergencyContactRelationship='" + emergencyContactRelationship + '\'' +
                ", emergencyContactNumber='" + emergencyContactNumber + '\'' +
                ", emergencyContactEmail='" + emergencyContactEmail + '\'' +
                ", policy_number='" + policy_number + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}
