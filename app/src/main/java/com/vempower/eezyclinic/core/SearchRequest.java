package com.vempower.eezyclinic.core;

import android.text.TextUtils;

import com.vempower.eezyclinic.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by satish on 12/12/17.
 */

public class SearchRequest implements Cloneable {

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
    private int onlinebooking;
    private String amountRange;
    private int perpage;
    private String page;

    private float amountRangeMin,amountRangeMax;


    public SearchRequest(int limit) {
        this.searchtype = DOCTOR_TYPE;
       // onlinebooking="0";
        perpage=limit;
        amountRangeMin=Constants.RangeBarValues.MIN_VALUE;
        amountRangeMax=Constants.RangeBarValues.MAX_VALUE;


        page="1";



       /* "search_name":"",  //doctor/clinic name
                "onlinebooking":"",  // 1 //0
                "amount_range":"",   // 520-1000
                "perpage":"",   //10 (Limit)
                "page":""       //1   (Page No)*/
    }

    public SearchRequest(String searchtype, String specality, String country,
                         String city, String longitude, String latitude,
                         ArrayList<String> insurenceList,
                         ArrayList<String> nationalityList,
                         ArrayList<String> gendersearch, String locality,
                         ArrayList<String> launguage, String searchName,
                         int onlinebooking, String amountRange, int perpage,
                         String page,float amountRangeMin,float amountRangeMax) {
        this.searchtype = searchtype;
        this.specality = specality;
        this.country = country;
        this.city = city;
        this.longitude = longitude;
        this.latitude = latitude;
        this.insurenceList = insurenceList;
        this.nationalityList = nationalityList;
        this.gendersearch = gendersearch;
        this.locality = locality;
        this.launguage = launguage;
        this.searchName = searchName;
        this.onlinebooking = onlinebooking;
        this.amountRange = amountRange;
        this.perpage = perpage;
        this.page = page;
        this.amountRangeMin=amountRangeMin;
                this.amountRangeMax=amountRangeMax;

    }

    public String getSearchtype() {
        return searchtype;
    }

    public void setSearchtype(String searchType) {

        if(!TextUtils.isEmpty(searchType) && searchType.equalsIgnoreCase(CLINIC_TYPE) )
        {
            this.searchtype = CLINIC_TYPE;

        }else
        {

            this.searchtype = DOCTOR_TYPE;
        }

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
        if(insurenceList==null)
        {
            this.insurenceList= new ArrayList<>();
        }
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


    public void removeInsurence(String insurance) {
        if(insurenceList==null)
        {
            insurenceList= new ArrayList<>();
            return;
        }

        if(!TextUtils.isEmpty(insurance)&& insurenceList.contains(insurance)) {
            this.insurenceList.remove(insurance);
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
        if(this.nationalityList==null)
        {
            this.nationalityList= new ArrayList<>();
        }
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


    public void removeNationality(String natinality) {
        if(nationalityList==null)
        {
            nationalityList= new ArrayList<>();
            return;
        }

        if(!TextUtils.isEmpty(natinality)&& nationalityList.contains(natinality)) {
            this.nationalityList.remove(natinality);
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
        if(gendersearch==null)
        {
            gendersearch= new ArrayList<>();
        }
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

    public void removeGendersearch(String gender) {
        if(gendersearch==null)
        {
            gendersearch= new ArrayList<>();
            return;
        }

        if(!TextUtils.isEmpty(gender)&& gendersearch.contains(gender)) {
            this.gendersearch.remove(gender);
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
        if(launguage==null)
        {
            this.launguage= new ArrayList<>();
        }
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

    public void removeLanguage(String language) {
        if(launguage==null)
        {
            launguage= new ArrayList<>();
            return;
        }

        if(!TextUtils.isEmpty(language)&& launguage.contains(language)) {
            this.launguage.remove(language);
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

    public int getOnlinebooking() {
        if(onlinebooking<0)
        {
            onlinebooking=0;
        }
        return onlinebooking;
    }

    public void setOnlinebooking(int onlinebooking) {
        this.onlinebooking = onlinebooking;
    }

    public String getAmountRange() {
        try {
            amountRange= Math.round(amountRangeMin)+"-"+ Math.round(amountRangeMax);

        }catch (Exception e)
        {
            amountRange="0-10000";
        }
         return amountRange;
    }

    public float getAmountRangeMin() {
        return amountRangeMin;
    }

    public float getAmountRangeMax() {
        return amountRangeMax;
    }

    public void setAmountRangeMin(float amountRangeMin) {
        this.amountRangeMin = amountRangeMin;
    }

    public void setAmountRangeMax(float amountRangeMax) {
        this.amountRangeMax = amountRangeMax;
    }

    public void setAmountRange(String amountRange) {
        this.amountRange = amountRange;
    }

    public int getPerpage() {
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

    public SearchRequest getCloneObject()
    {
        return new SearchRequest( searchtype,  specality,  country,  city,
                longitude,  latitude,  insurenceList,  nationalityList,
                gendersearch,  locality,  launguage,  searchName,
                onlinebooking,  amountRange,  perpage,  page,amountRangeMin,amountRangeMax);
    }
}
