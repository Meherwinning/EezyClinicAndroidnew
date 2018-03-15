
package com.vempower.eezyclinic.APIResponce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vempower.eezyclinic.activities.complex.BaseModel;

public class SpecalitiyData implements BaseModel {

    public interface ViewType
    {
        int HEADER_TYPE=1;
        int SPECALITI_YTYPE=2;
    }

    public interface ButtonType
    {
        int NORMAL=0;
        int MORE=1;
        int LESS=2;
    }

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;

    private int dataType;
    private int buttonType;

    boolean isRemainigItem;

    public SpecalitiyData() {
        this.dataType = ViewType.SPECALITI_YTYPE;
    }

    public void setButtonType(int type) {
        buttonType = type;
    }


    public int getButtonType() {
        return buttonType;
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
