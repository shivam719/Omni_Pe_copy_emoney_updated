package com.fintech.omnipe.UPIPayment.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class VPAListResponse {

    @SerializedName("isSenderNotExists")
    @Expose
    private boolean isSenderNotExists;
    @SerializedName("isEKYCAvailable")
    @Expose
    private boolean isEKYCAvailable;
    @SerializedName("isOTPGenerated")
    @Expose
    private boolean isOTPGenerated;
    @SerializedName("remainingLimit")
    @Expose
    private Double remainingLimit;
    @SerializedName("availbleLimit")
    @Expose
    private Double availbleLimit;
    @SerializedName("senderName")
    @Expose
    private String senderName;
    @SerializedName("senderBalance")
    @Expose
    private String senderBalance;
    @SerializedName("vpaList")
    @Expose
    private ArrayList<VPAList> vpaList = null;
    @SerializedName("statuscode")
    @Expose
    private int statuscode;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("isVersionValid")
    @Expose
    private boolean isVersionValid;
    @SerializedName("isAppValid")
    @Expose
    private boolean isAppValid;

    public boolean isSenderNotExists() {
        return isSenderNotExists;
    }

    public boolean isEKYCAvailable() {
        return isEKYCAvailable;
    }

    public boolean isOTPGenerated() {
        return isOTPGenerated;
    }

    public Double getRemainingLimit() {
        return remainingLimit;
    }

    public Double getAvailbleLimit() {
        return availbleLimit;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getSenderBalance() {
        return senderBalance;
    }

    public ArrayList<VPAList> getVpaList() {
        return vpaList;
    }

    public int getStatuscode() {
        return statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isVersionValid() {
        return isVersionValid;
    }

    public boolean isAppValid() {
        return isAppValid;
    }
}
