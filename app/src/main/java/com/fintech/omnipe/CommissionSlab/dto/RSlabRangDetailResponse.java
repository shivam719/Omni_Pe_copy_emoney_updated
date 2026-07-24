package com.fintech.omnipe.CommissionSlab.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RSlabRangDetailResponse {

    @SerializedName("slabRangeDetail")
    @Expose
    private List<SlabRangeDetail> slabRangeDetail = null;
    @SerializedName("data")
    @Expose
    private List<SlabRangeDetail> data;
    @SerializedName("statuscode")
    @Expose
    private Integer statuscode;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("isVersionValid")
    @Expose
    private boolean isVersionValid;
    @SerializedName("isAppValid")
    @Expose
    private boolean isAppValid;
    @SerializedName("checkID")
    @Expose
    private Integer checkID;
    @SerializedName("isPasswordExpired")
    @Expose
    private boolean isPasswordExpired;

    @SerializedName("isOTPRequired")
    @Expose
    private boolean isOTPRequired;
    @SerializedName("isResendAvailable")
    @Expose
    private boolean isResendAvailable;


    public List<SlabRangeDetail> getData() {
        return data;
    }

    public List<SlabRangeDetail> getSlabRangeDetail() {
        return slabRangeDetail;
    }

    public Integer getStatuscode() {
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

    public Integer getCheckID() {
        return checkID;
    }

    public boolean isPasswordExpired() {
        return isPasswordExpired;
    }


    public boolean isOTPRequired() {
        return isOTPRequired;
    }

    public boolean isResendAvailable() {
        return isResendAvailable;
    }





}
