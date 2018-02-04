
package com.vempower.eezyclinic.APIResponce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vempower.eezyclinic.APICore.SearchFamilyMemberData;

public class SearchFamilyMemberAPI extends AbstractResponse {


    @SerializedName("data")
    @Expose
    private SearchFamilyMemberData data;


    public SearchFamilyMemberData getData() {
        return data;
    }

    public void setData(SearchFamilyMemberData data) {
        this.data = data;
    }

}
