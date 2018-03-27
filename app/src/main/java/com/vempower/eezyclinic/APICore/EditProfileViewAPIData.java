
package com.vempower.eezyclinic.APICore;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EditProfileViewAPIData {

    @SerializedName("patientdata")
    @Expose
    private EditProfileViewPatientdata patientdata;
    @SerializedName("patientinsurancedetails")
    @Expose
    private List<Patientinsurancedetail> patientinsurancedetails = null;
    @SerializedName("insurance")
    @Expose
    private List<EditProfileViewInsurance> insurance = null;
    @SerializedName("nationalities")
    @Expose
    private List<EditProfileViewNationality> nationalities = null;
    @SerializedName("countries")
    @Expose
    private List<EditProfileViewCountry> countries = null;
    /*@SerializedName("cities")
    @Expose
    private List<String> cities = null;*/
    @SerializedName("idcardlist")
    @Expose
    private List<EditProfileViewIdcardlist> idcardlist = null;
    @SerializedName("insurancedetailslist")
    @Expose
    private List<EditProfileViewInsurancedetailslist> insurancedetailslist = null;


    @SerializedName("tpalist")
    @Expose
    private List<Tpalist> tpalist = null;

    public List<Tpalist> getTpalist() {
        return tpalist;
    }

    public void setTpalist(List<Tpalist> tpalist) {
        this.tpalist = tpalist;
    }


    public EditProfileViewPatientdata getPatientdata() {
        return patientdata;
    }

    public void setPatientdata(EditProfileViewPatientdata patientdata) {
        this.patientdata = patientdata;
    }

    public List<Patientinsurancedetail> getPatientinsurancedetails() {
        return patientinsurancedetails;
    }

    public void setPatientinsurancedetails(List<Patientinsurancedetail> patientinsurancedetails) {
        this.patientinsurancedetails = patientinsurancedetails;
    }

    public List<EditProfileViewInsurance> getInsurance() {
        return insurance;
    }

    public void setInsurance(List<EditProfileViewInsurance> insurance) {
        this.insurance = insurance;
    }

    public List<EditProfileViewNationality> getNationalities() {
        return nationalities;
    }

    public void setNationalities(List<EditProfileViewNationality> nationalities) {
        this.nationalities = nationalities;
    }

    public List<EditProfileViewCountry> getCountries() {
        return countries;
    }

    public void setCountries(List<EditProfileViewCountry> countries) {
        this.countries = countries;
    }

  /*  public List<String> getCities() {
        return cities;
    }*/

   /* public void setCities(List<String> cities) {
        this.cities = cities;
    }*/

    public List<EditProfileViewIdcardlist> getIdcardlist() {
        return idcardlist;
    }

    public void setIdcardlist(List<EditProfileViewIdcardlist> idcardlist) {
        this.idcardlist = idcardlist;
    }

    public List<EditProfileViewInsurancedetailslist> getInsurancedetailslist() {
        return insurancedetailslist;
    }

    public void setInsurancedetailslist(List<EditProfileViewInsurancedetailslist> insurancedetailslist) {
        this.insurancedetailslist = insurancedetailslist;
    }

}
