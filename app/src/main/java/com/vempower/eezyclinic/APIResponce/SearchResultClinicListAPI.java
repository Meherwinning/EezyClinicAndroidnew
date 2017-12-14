
package com.vempower.eezyclinic.APIResponce;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vempower.eezyclinic.fragments.AbstractFragment;

public class SearchResultClickListAPI extends AbstractResponse {


    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

   // @SerializedName("meta_data")
   /* @Expose
    private MetaData metaData;*/



    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
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
