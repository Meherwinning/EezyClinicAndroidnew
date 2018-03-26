
package com.vempower.eezyclinic.APICore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Patientinsurancedetail {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("patient_id")
    @Expose
    private String patientId;
    @SerializedName("is_primary")
    @Expose
    private String isPrimary;
    @SerializedName("insurancePackage")
    @Expose
    private String insurancePackage;
    @SerializedName("insuranceNumber")
    @Expose
    private String insuranceNumber;
    @SerializedName("tpa")
    @Expose
    private String tpa;
    @SerializedName("tpaid")
    @Expose
    private String tpaid;
    @SerializedName("policy")
    @Expose
    private String policy;
    @SerializedName("policy_number")
    @Expose
    private String policyNumber;
    @SerializedName("memberid")
    @Expose
    private String memberid;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("scheme")
    @Expose
    private String scheme;
    @SerializedName("reason")
    @Expose
    private String reason;
    @SerializedName("copay")
    @Expose
    private String copay;
    @SerializedName("maxlimit")
    @Expose
    private String maxlimit;
    @SerializedName("fromvalidity")
    @Expose
    private String fromvalidity;
    @SerializedName("tovalidity")
    @Expose
    private String tovalidity;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("insurance_card_front")
    @Expose
    private String insuranceCardFront;
    @SerializedName("insurance_card_rear")
    @Expose
    private String insuranceCardRear;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(String isPrimary) {
        this.isPrimary = isPrimary;
    }

    public String getInsurancePackage() {
        return insurancePackage;
    }

    public void setInsurancePackage(String insurancePackage) {
        this.insurancePackage = insurancePackage;
    }

    public String getInsuranceNumber() {
        return insuranceNumber;
    }

    public void setInsuranceNumber(String insuranceNumber) {
        this.insuranceNumber = insuranceNumber;
    }

    public String getTpa() {
        return tpa;
    }

    public void setTpa(String tpa) {
        this.tpa = tpa;
    }

    public String getTpaid() {
        return tpaid;
    }

    public void setTpaid(String tpaid) {
        this.tpaid = tpaid;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getCopay() {
        return copay;
    }

    public void setCopay(String copay) {
        this.copay = copay;
    }

    public String getMaxlimit() {
        return maxlimit;
    }

    public void setMaxlimit(String maxlimit) {
        this.maxlimit = maxlimit;
    }

    public String getFromvalidity() {
        return fromvalidity;
    }

    public void setFromvalidity(String fromvalidity) {
        this.fromvalidity = fromvalidity;
    }

    public String getTovalidity() {
        return tovalidity;
    }

    public void setTovalidity(String tovalidity) {
        this.tovalidity = tovalidity;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInsuranceCardFront() {
        return insuranceCardFront;
    }

    public void setInsuranceCardFront(String insuranceCardFront) {
        this.insuranceCardFront = insuranceCardFront;
    }

    public String getInsuranceCardRear() {
        return insuranceCardRear;
    }

    public void setInsuranceCardRear(String insuranceCardRear) {
        this.insuranceCardRear = insuranceCardRear;
    }

}
