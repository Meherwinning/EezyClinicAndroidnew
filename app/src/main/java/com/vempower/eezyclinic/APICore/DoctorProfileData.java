
package com.vempower.eezyclinic.APICore;

import android.text.TextUtils;

import java.util.ArrayList;
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
    private List<String> awardsRecognitions = null;
    @SerializedName("memberships")
    @Expose
    private List<String> memberships = null;
    @SerializedName("registrations")
    @Expose
    private String registrations;
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

    public int getDoctoroverallrating() {

        if(TextUtils.isEmpty(doctoroverallrating))
        {
            return 0;
        }
        try
        {
         return Integer.parseInt(doctoroverallrating);
        }catch(Exception e)
        {
            return 0;
        }
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

    public String getSpecializations() {
        String spec="";
        if(specializations==null)
        {
            return spec;
        }
      /*  for(String str:specializations)
        {
            if(str!=null)
            {
                spec=spec+str+",";
            }
        }*/


        for(int i=0;i<specializations.size();i++)
        {
            String str=specializations.get(i);
            if(str==null)
            {
                continue;
            }
            if(i!=specializations.size()-1)
            {
                spec=spec+str+", ";
            }else
            {
                spec=spec+str;
            }
        }

        return spec;
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
        for(int i=0;i<services.size();i++)
        {
            String str=services.get(i);
            if(str==null)
            {
                continue;
            }
            if(i!=services.size()-1)
            {
                ser=ser+str+", ";
            }else
            {
                ser=ser+str;
            }
        }

        return ser;
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

    public String getExperience() {

        String exp="";
        if(experience==null)
        {
            return exp;
        }
       /* for(String str:experience)
        {
            if(str!=null)
            {
                exp=exp+str+",";
            }
        }*/

        for(int i=0;i<experience.size();i++)
        {
            String str=experience.get(i);
            if(str==null)
            {
                continue;
            }
            if(i!=experience.size()-1)
            {
                exp=exp+str+", ";
            }else
            {
                exp=exp+str;
            }
        }

        return exp;
    }

    public void setExperience(List<String> experience) {
        this.experience = experience;
    }

    public String getAwardsRecognitions() {

        String award="";
        if(awardsRecognitions==null)
        {
            return award;
        }
        for(String str:awardsRecognitions)
        {
            if(str!=null)
            {
                award=award+str+",";
            }
        }

        return award;
    }

    public void setAwardsRecognitions(List<String> awardsRecognitions) {
        this.awardsRecognitions = awardsRecognitions;
    }

    public String getMemberships() {
        String member="";
        if(memberships==null)
        {
            return member;
        }
        for(String str:memberships)
        {
            if(str!=null)
            {
                member=member+str+",";
            }
        }

        return member;

    }

    public void setMemberships(List<String> memberships) {
        this.memberships = memberships;
    }

    public String getRegistrations() {
        return registrations;
    }

    public void setRegistrations(String registrations) {
        this.registrations = registrations;
    }

    public String getClinicdetails() {
        String details="";

        if(clinicdetails==null)
        {
            return details;
        }
        details="\nClinic Name: "+clinicname+"\n\n";
        details=details+"Clinic Address: "+clinicdetails.getClinic_address()+"\n\n";
        details=details+"Clinic Time: "+clinicdetails.getClinictimings()+"\n\n";
        details=details+"Doctor Time: "+clinicdetails.getDoctortimings()+"\n";
        details=details+"Contact: "+clinicdetails.getContact()+"";


        return details;
    }

    public void setClinicdetails(DoctorClinicdetails clinicdetails) {
        this.clinicdetails = clinicdetails;
    }

    public List<DoctorReview> getReviews() {
        if(reviews==null)
        {
            reviews= new ArrayList<>();
        }
        return reviews;
    }

    public void setReviews(List<DoctorReview> reviews) {
        this.reviews = reviews;
    }

}
