
package com.vempower.eezyclinic.APIResponce;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vempower.eezyclinic.APICore.SearchResultDoctorListData;
import com.vempower.eezyclinic.APICore.SearchResultDoctorListMetaData;

public class SearchResultDoctorListAPI extends  AbstractResponse {


    @SerializedName("data")
    @Expose
    private List<SearchResultDoctorListData> data = null;
    @SerializedName("meta_data")
    @Expose
    private SearchResultDoctorListMetaData metaData;



    public List<SearchResultDoctorListData> getData() {
        return data;
    }

    public void setData(List<SearchResultDoctorListData> data) {
        this.data = data;
    }

    public SearchResultDoctorListMetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(SearchResultDoctorListMetaData metaData) {
        this.metaData = metaData;
    }

}
