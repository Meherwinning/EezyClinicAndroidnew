
package com.vempower.eezyclinic.APIResponce;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vempower.eezyclinic.APICore.SearchResultClinicData;
import com.vempower.eezyclinic.fragments.AbstractFragment;

public class SearchResultClinicListAPI extends AbstractResponse {


    @SerializedName("data")
    @Expose
    private List<SearchResultClinicData> data = null;

   // @SerializedName("meta_data")
   /* @Expose
    private MetaData metaData;*/



    public List<SearchResultClinicData> getData() {
        return data;
    }

    public void setData(List<SearchResultClinicData> data) {
        this.data = data;
    }

    /*public MetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }
*/
}
