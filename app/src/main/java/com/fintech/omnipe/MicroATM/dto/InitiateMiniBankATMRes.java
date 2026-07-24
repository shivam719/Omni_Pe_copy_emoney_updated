package com.fintech.omnipe.MicroATM.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InitiateMiniBankATMRes {

    @SerializedName("tid")
    @Expose
    private String tid;
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
    private String checkID;
    @SerializedName("isPasswordExpired")
    @Expose
    private boolean isPasswordExpired;
    @SerializedName("mobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("emailID")
    @Expose
    private String emailID;
    @SerializedName("isLookUpFromAPI")
    @Expose
    private boolean isLookUpFromAPI;
    @SerializedName("isDTHInfoCall")
    @Expose
    private boolean isDTHInfoCall;
    @SerializedName("isShowPDFPlan")
    @Expose
    private boolean isShowPDFPlan;
    @SerializedName("sid")
    @Expose
    private String sid;
    @SerializedName("isOTPRequired")
    @Expose
    private boolean isOTPRequired;

    public String getTid() {
        return tid;
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

    public String getCheckID() {
        return checkID;
    }

    public boolean isPasswordExpired() {
        return isPasswordExpired;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public String getEmailID() {
        return emailID;
    }

    public boolean isLookUpFromAPI() {
        return isLookUpFromAPI;
    }

    public boolean isDTHInfoCall() {
        return isDTHInfoCall;
    }

    public boolean isShowPDFPlan() {
        return isShowPDFPlan;
    }

    public String getSid() {
        return sid;
    }

    public boolean isOTPRequired() {
        return isOTPRequired;
    }

    public boolean isResendAvailable() {
        return isResendAvailable;
    }

    public String getGetID() {
        return getID;
    }

    @SerializedName("isResendAvailable")
    @Expose
    private boolean isResendAvailable;
    @SerializedName("getID")
    @Expose
    private String getID;






}
