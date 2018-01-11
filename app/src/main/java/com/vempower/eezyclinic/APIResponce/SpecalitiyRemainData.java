
package com.vempower.eezyclinic.APIResponce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vempower.eezyclinic.activities.complex.BaseModel;

public class SpecalitiyRemainData implements BaseModel  {


    public SpecalitiyRemainData(SpecalitiyData data) {
       setDataType(data.getDataType());
       setId(data.getId());
       setName(data.getName());
    }


    public interface ViewType
    {
        int HEADER_TYPE=1;
        int SPECALITI_YTYPE=2;
    }

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;

    private int dataType;
    boolean isMoreButton;

    boolean isRemainigItem;

  /*  public SpecalitiyData() {
        this.dataType = SpecalitiyData.ViewType.SPECALITI_YTYPE;
    }*/

    public void setMoreButton(boolean moreButton) {
        isMoreButton = moreButton;
    }


    public boolean isMoreButton() {
        return isMoreButton;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public int getDataType() {
        return dataType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return  name ;
    }
}
