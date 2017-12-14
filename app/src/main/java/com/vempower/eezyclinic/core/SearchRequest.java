package com.vempower.eezyclinic.core;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by satish on 12/12/17.
 */

public class SearchRequest {

    public static final String DOCTOR_TYPE="doctor",CLINIC_TYPE="clinic";
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


    public SearchRequest(int limit) {
        this.searchtype = DOCTOR_TYPE;
       // onlinebooking="0";
        perpage=limit+"";
        page="1";



       /* "search_name":"",  //doctor/clinic name
                "onlinebooking":"",  // 1 //0
                "amount_range":"",   // 520-1000
                "perpage":"",   //10 (Limit)
                "page":""       //1   (Page No)*/
    }

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

    public void addInsurence(String insurace) {
        if(insurenceList==null)
        {
            this.insurenceList= new ArrayList<>();
        }
        if(!TextUtils.isEmpty(insurace) && !insurenceList.contains(insurace)) {

            this.insurenceList.add(insurace);
        }
    }
    public void setInsurenceList(ArrayList<String> newInsuraceList) {
        if(insurenceList==null)
        {
            this.insurenceList= new ArrayList<>();
        }
        if(newInsuraceList==null)
        {
            insurenceList.clear();
            this.insurenceList= new ArrayList<>();
            return;
        }
        insurenceList.addAll(newInsuraceList);
    }

    public ArrayList<String> getNationalityList() {
        return nationalityList;
    }

    public void addNationality(String nationality) {
        if(this.nationalityList==null)
        {
            this.nationalityList= new ArrayList<>();
        }
        if(!TextUtils.isEmpty(nationality) && !nationalityList.contains(nationality)) {
            this.nationalityList.add(nationality);
        }
    }
    public void setNationalityList(ArrayList<String> newNationality) {
        if(this.nationalityList==null)
        {
            this.nationalityList= new ArrayList<>();
        }
        if(newNationality==null)
        {
            nationalityList.clear();
            this.nationalityList= new ArrayList<>();
            return;
        }
        nationalityList.addAll(newNationality);

    }

    public ArrayList<String> getGendersearch() {
        return gendersearch;
    }

    public void addGendersearch(String gender) {
        if(gendersearch==null)
        {
            gendersearch= new ArrayList<>();
        }

        if(!TextUtils.isEmpty(gender)&& !gendersearch.contains(gender)) {
            this.gendersearch.add(gender);
        }
    }

    public void setGendersearch(ArrayList<String> newGenderList) {
        if(gendersearch==null)
        {
            gendersearch= new ArrayList<>();
        }
        if(newGenderList==null)
        {
            gendersearch.clear();
            this.gendersearch= new ArrayList<>();
            return;
        }
        gendersearch.addAll(newGenderList);
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

    public void addLaunguage(String launStr) {
        if(launguage==null)
        {
            this.launguage= new ArrayList<>();
        }
        if(!TextUtils.isEmpty(launStr)&& !launguage.contains(launStr)) {
            this.launguage.add(launStr);
        }
    }


    public void setLaunguage(ArrayList<String> launStrList) {
        if(launguage==null)
        {
            this.launguage= new ArrayList<>();
        }
        if(launStrList==null)
        {
            launguage.clear();
            this.launguage= new ArrayList<>();
            return;
        }
        launguage.addAll(launStrList);
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

    /*public void setPerpage(String perpage) {
        this.perpage = perpage;
    }*/

    public String getPage() {
        if(TextUtils.isEmpty(page))
        {
            page="1";
        }
        return page;
    }

    public void setPage(String page) {
        if(TextUtils.isEmpty(page))
        {
            page="1";
        }
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
