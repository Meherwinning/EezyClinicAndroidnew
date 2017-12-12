package com.vempower.eezyclinic.core;

import android.text.TextUtils;

import java.util.ArrayList;
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
    private ArrayList<String> insurenceList = null;
    private ArrayList<String> nationalityList = null;
    private ArrayList<String> gendersearch = null;
    private String locality;
    private ArrayList<String> launguage = null;
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

    public ArrayList<String> getInsurenceList() {
        return insurenceList;
    }

    public void setInsurenceList(String insurace) {
        if(insurenceList==null)
        {
            this.insurenceList= new ArrayList<>();
        }
        if(!TextUtils.isEmpty(insurace)) {
            this.insurenceList.add(insurace);
        }
    }

    public ArrayList<String> getNationalityList() {
        return nationalityList;
    }

    public void setNationalityList(String nationality) {
        if(this.nationalityList==null)
        {
            this.nationalityList= new ArrayList<>();
        }
        if(!TextUtils.isEmpty(nationality)) {
            this.nationalityList.add(nationality);
        }
    }

    public ArrayList<String> getGendersearch() {
        return gendersearch;
    }

    public void setGendersearch(String gender) {
        if(gendersearch==null)
        {
            gendersearch= new ArrayList<>();
        }
        if(!TextUtils.isEmpty(gender)) {
            this.gendersearch.add(gender);
        }
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public ArrayList<String> getLaunguage() {
        return launguage;
    }

    public void setLaunguage(String launStr) {
        if(launguage==null)
        {
            this.launguage= new ArrayList<>();
        }
        if(!TextUtils.isEmpty(launStr)) {
            this.launguage.add(launStr);
        }
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

    @Override
    public String toString() {
        return "SearchRequest{" +
                "searchtype='" + searchtype + '\'' +
                ", specality='" + specality + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", insurenceList=" + insurenceList +
                ", nationalityList=" + nationalityList +
                ", gendersearch=" + gendersearch +
                ", locality='" + locality + '\'' +
                ", launguage=" + launguage +
                ", searchName='" + searchName + '\'' +
                ", onlinebooking='" + onlinebooking + '\'' +
                ", amountRange='" + amountRange + '\'' +
                ", perpage='" + perpage + '\'' +
                ", page='" + page + '\'' +
                '}';
    }
}
