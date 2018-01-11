
package com.vempower.eezyclinic.APICore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CaseSheetsListMetaDeta {

    @SerializedName("per_page")
    @Expose
    private Integer perPage;
    @SerializedName("total_items")
    @Expose
    private Integer totalItems;
    @SerializedName("no_of_page")
    @Expose
    private Integer noOfPage;
    @SerializedName("current_page")
    @Expose
    private Integer currentPage;

    public Integer getPerPage() {
        return perPage;
    }

    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }

    public Integer getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }

    public Integer getNoOfPage() {
        return noOfPage;
    }

    public void setNoOfPage(Integer noOfPage) {
        this.noOfPage = noOfPage;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

}
