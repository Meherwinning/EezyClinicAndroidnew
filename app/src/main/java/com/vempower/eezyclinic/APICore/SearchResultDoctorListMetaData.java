
package com.vempower.eezyclinic.APICore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchResultDoctorListMetaData {

   /* @SerializedName("pricerange_search")
    @Expose
    private PricerangeSearch pricerangeSearch;*/
    @SerializedName("perpage")
    @Expose
    private Integer perpage;
    @SerializedName("totalrecords")
    @Expose
    private Integer totalrecords;
    @SerializedName("totalpages")
    @Expose
    private Integer totalpages;
    @SerializedName("page")
    @Expose
    private Integer page;

  /*  public PricerangeSearch getPricerangeSearch() {
        return pricerangeSearch;
    }

    public void setPricerangeSearch(PricerangeSearch pricerangeSearch) {
        this.pricerangeSearch = pricerangeSearch;
    }*/

    public Integer getPerpage() {
        return perpage;
    }

    public void setPerpage(Integer perpage) {
        this.perpage = perpage;
    }

    public Integer getTotalrecords() {
        return totalrecords;
    }

    public void setTotalrecords(Integer totalrecords) {
        this.totalrecords = totalrecords;
    }

    public Integer getTotalpages() {
        return totalpages;
    }

    public void setTotalpages(Integer totalpages) {
        this.totalpages = totalpages;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

}
