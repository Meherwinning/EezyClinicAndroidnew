package com.vempower.eezyclinic.core;

import java.util.List;

/**
 * Created by satish on 12/12/17.
 */

public class SearchRequest {

    private String searchtype;
    private String specality;
    private String country;
    private String city;
    private String longitude;
    private String latitude;
    private List<Integer> insurenceList = null;
    private List<Integer> nationalityList = null;
    private List<String> gendersearch = null;
    private String locality;
    private List<String> launguage = null;
    private String searchName;
    private String onlinebooking;
    private String amountRange;
    private String perpage;
    private String page;

    public String getSearchtype() {
        return searchtype;
    }

    public void setSearchtype(String searchtype) {
        this.searchtype = searchtype;
    }

    public String getSpecality() {
        return specality;
    }

    public void setSpecality(String specality) {
        this.specality = specality;
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

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public List<Integer> getInsurenceList() {
        return insurenceList;
    }

    public void setInsurenceList(List<Integer> insurenceList) {
        this.insurenceList = insurenceList;
    }

    public List<Integer> getNationalityList() {
        return nationalityList;
    }

    public void setNationalityList(List<Integer> nationalityList) {
        this.nationalityList = nationalityList;
    }

    public List<String> getGendersearch() {
        return gendersearch;
    }

    public void setGendersearch(List<String> gendersearch) {
        this.gendersearch = gendersearch;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public List<String> getLaunguage() {
        return launguage;
    }

    public void setLaunguage(List<String> launguage) {
        this.launguage = launguage;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public String getOnlinebooking() {
        return onlinebooking;
    }

    public void setOnlinebooking(String onlinebooking) {
        this.onlinebooking = onlinebooking;
    }

    public String getAmountRange() {
        return amountRange;
    }

    public void setAmountRange(String amountRange) {
        this.amountRange = amountRange;
    }

    public String getPerpage() {
        return perpage;
    }

    public void setPerpage(String perpage) {
        this.perpage = perpage;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
