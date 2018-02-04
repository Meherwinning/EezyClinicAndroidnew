
package com.vempower.eezyclinic.APIResponce;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vempower.eezyclinic.APICore.GetFamilyMemberData;

public class GetFamilyMembersAPI extends AbstractResponse {


    @SerializedName("data")
    @Expose
    private List<GetFamilyMemberData> data = null;


    public List<GetFamilyMemberData> getData() {
        return data;
    }

    public void setData(List<GetFamilyMemberData> data) {
        this.data = data;
    }

}
