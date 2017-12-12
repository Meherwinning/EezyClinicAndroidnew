package com.vempower.eezyclinic.core;

/**
 * Created by satish on 12/12/17.
 */

public class DoctorClinicNamesSearch {
  private  String search_text, countryId, speciality, city , locality;


    public void setSearch_text(String search_text) {
        this.search_text = search_text;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getSearch_text() {
        return search_text;
    }

    public String getCountryId() {
        return countryId;
    }

    public String getSpeciality() {
        return speciality;
    }

    public String getCity() {
        return city;
    }

    public String getLocality() {
        return locality;
    }
}
